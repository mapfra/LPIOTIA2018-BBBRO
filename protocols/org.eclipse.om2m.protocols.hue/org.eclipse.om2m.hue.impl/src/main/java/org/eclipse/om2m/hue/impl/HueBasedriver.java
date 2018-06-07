/*******************************************************************************
* Copyright (c) 2014-2018 Orange.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*    BAREAU Cyrille <cyrille.bareau@orange.com>
*    BONNARDEL Gregory <gbonnardel.ext@orange.com>
*    BORAWSKI Pawel <pawel.borawski@orange.com>
*    RATUSZEK Przemyslaw <przemyslaw.ratuszek@orange.com>
*    WIERZBOWSKI Jacek <jacek.wierzbowski@orange.com>
*******************************************************************************/
package org.eclipse.om2m.hue.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Deactivate;

/**
 * Implementation class of the OSGi component HueBasedriver. Registers
 * discovered Hue Bridge and Lights as OSGi services.
 */
public class HueBasedriver implements ManagedService {

	// ------------------------------------------------------------------------

	/** Logger */
	private static Log Logger = LogFactory.getLog(HueBasedriver.class);

	/*
	 * SSDP port number
	 */
	private static final int SSDP_PORT = 1900;

	private static final int MTIMER_PERIOD = 3 * 60 * 1000;

	/*
	 * Broadcast address for Upnp. Defined by normalization.
	 */
	private static final String UPNP_BROADCAST_IP = "239.255.255.250";

	/*
	 * UDP packet size
	 */
	private static final int UDP_PACKET_SIZE = 1024;

	/**
	 * ENABLE_SSDP_DISCOVERY is a boolean configAdmin property. If true, the hue
	 * base driver must perform SSDP discovery in order to discover the Hue
	 * Bridge(s).
	 */
	private static final String ENABLE_SSDP_DISCOVERY = "enable.ssdp.discovery";

	/**
	 * HUE_BRIDGE_DESCRIPTION_URL
	 */
	private static final String HUE_BRIDGE_DESCRIPTION_URL = "hue.bridge.description.url";

	/**
	 * HUE_BRIDGE_USER_ID
	 */
	private static final String HUE_BRIDGE_USER_ID = "hue.bridge.userid";
	
	private static final String SERVICE_ID = "service.pid";

	// ------------------------------------------------------------------------

	/*
	 * Timer for M-SEARCH
	 */
	private Timer mSearchTimer = new Timer();

	/*
	 * M-SEARCH thread
	 */
	private Thread mSearchThread;

	/*
	 * ALIVE thread
	 */
	private Thread aliveThread;
	
	/**
	 * Thread for registration process handling
	 */
	private Thread bridgeRegistrationThread;

	// ------------------------------------------------------------------------

	private MulticastSocket mSearchSocket;
	private MulticastSocket aliveSocket;

	private boolean mSearchInitialized;
	private boolean aliveInitialized;
	private boolean mSearchActivated;
	private boolean newMSearchRequired;

	private List<NetworkInterface> networkInterfaces;

	private HueBridgeDiscovery discovery;

	// ------------------------------------------------------------------------

	// ------------------------------------------------------------------------

	/**
	 * Constructor
	 * 
	 */
	public HueBasedriver() {
		networkInterfaces = new ArrayList<NetworkInterface>();
		mSearchInitialized = false;
		aliveInitialized = false;
		mSearchActivated = false;
		newMSearchRequired = true;
		Logger.info("HueBasedriver ctor");
	}

	// ------------------------------------------------------------------------
	// Declarative Service injection methods
	// ------------------------------------------------------------------------

	/**
	 * Activate method from Declarative Service
	 */
	@Activate
	protected void activate(final ComponentContext context) {
		Logger.info("Activating");
//		modified(properties);
		discovery = new HueBridgeDiscovery(context.getBundleContext());
		// initMSearchTimer();
		// discovery.registerBridge(new FakeBridge("xml"));
	}

	/**
	 * Deactivate method from Declarative Service
	 */
	@Deactivate
	protected void deactivate(ComponentContext cc) {
		Logger.info("Deactivating");
		closeSockets();
	}

	private void initMSearchTimer() {
		mSearchTimer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				if (mSearchInitialized) {
					newMSearchRequired = true;
					mSearchSocket.close();
				} else {
					mSearchActivated = true;
					searchHueBridge();
				}

				if (!aliveInitialized) {
					listenAliveHueBridge();
				}
			}
		}, 10, MTIMER_PERIOD); // send first M-SEARCH now
		// + new M-SEARCH every 3 min
	}

	private void cancelMSearchTimer() {
		mSearchTimer.cancel();
	}

	private void closeSockets() {
		if (discovery != null) {
			discovery.stopBackgroundProcesses();
		}
		if (bridgeRegistrationThread != null) {
			bridgeRegistrationThread.interrupt();
		}
		if (mSearchThread != null) {
			mSearchThread.interrupt();
		}
		if (aliveThread != null) {
			aliveThread.interrupt();
		}
		mSearchActivated = false;
		if (mSearchSocket != null) {
			mSearchSocket.close();
		}
		if (aliveSocket != null) {
			aliveSocket.close();
		}
	}

	// ------------------------------------------------------------------------
	// Private utility methods
	// ------------------------------------------------------------------------

	private void initSearch() {
		try {
			mSearchSocket = new MulticastSocket();
			mSearchSocket.setReuseAddress(true);
			// Keep a backlog of incoming datagrams if we are not fast enough
			mSearchSocket.setReceiveBufferSize(32768);

			InetSocketAddress multicastAddress = new InetSocketAddress(UPNP_BROADCAST_IP, SSDP_PORT);

			// discover network interface
			findNetworkInterfaces();

			for (NetworkInterface itf : networkInterfaces) {
				try {
					mSearchSocket.joinGroup(multicastAddress, itf);
				} catch (Exception ignored) {
				}
			}
		} catch (IOException e) {
			Logger.warn("Init Search IOException : " + e.getMessage());
		}
	}

	private void initAlive() {
		try {
			aliveSocket = new MulticastSocket(SSDP_PORT);
			aliveSocket.setReuseAddress(true);
			// Keep a backlog of incoming datagrams if we are not fast enough
			aliveSocket.setReceiveBufferSize(32768);

			InetSocketAddress multicastAddress = new InetSocketAddress(UPNP_BROADCAST_IP, SSDP_PORT);

			// discover network interface
			findNetworkInterfaces();

			for (NetworkInterface itf : networkInterfaces) {
				try {
					aliveSocket.joinGroup(multicastAddress, itf);
				} catch (Exception ignored) {
				}
			}
		} catch (IOException e) {
			Logger.warn("Init Alive IOException : " + e.getMessage());
		}
	}

	private void searchHueBridge() {
		mSearchThread = new Thread() {
			public void run() {
				while (mSearchActivated) {
					initSearch();

					mSearchInitialized = true;

					// -------------------------------------------------------
					// send M-SEARCh message
					discovery.clearDescriptions();
					sendMSearchMessage();
					newMSearchRequired = false;

					// -------------------------------------------------------
					// waiting for responses
					byte[] data = new byte[UDP_PACKET_SIZE];
					while (!newMSearchRequired) {
						DatagramPacket receivePacket = new DatagramPacket(data, data.length);
						try {
							mSearchSocket.receive(receivePacket);
						} catch (IOException e) {
							// socket closed to interrupt receive blocking and launch a new M-SEARCH
							break;
						}
						lookingForHueBridge(receivePacket);
					}
				}
			}
		};
		mSearchThread.start();
	}

	private void listenAliveHueBridge() {
		aliveThread = new Thread() {
			public void run() {
				initAlive();
				aliveInitialized = true;
				byte[] data = new byte[UDP_PACKET_SIZE];

				while (true) {
					DatagramPacket receivePacket = new DatagramPacket(data, data.length);
					try {
						aliveSocket.receive(receivePacket);
					} catch (IOException e) {
						// socket closed to interrupt receive blocking and launch a new M-SEARCH
					}
					lookingForHueBridge(receivePacket);
				}
			}
		};
		aliveThread.start();
	}

	private void lookingForHueBridge(final DatagramPacket receivePacket) {
		String message = new String(receivePacket.getData());
		try {
			String tag = "location:";
			int startIndex = message.toLowerCase().indexOf(tag) + tag.length();
			if (startIndex >= tag.length()) {
				int endIndex = message.indexOf("\r\n", startIndex);
				String location = message.substring(startIndex, endIndex).trim();
				discovery.processNewHueBridge(location);
			}
		} catch (IOException e) {
			Logger.warn("Looking for Hue Bridge IOException : ", e);
		}
	}

	@SuppressWarnings("rawtypes")
	private void findNetworkInterfaces() {
		try {
			for (Enumeration itfs = NetworkInterface.getNetworkInterfaces(); itfs.hasMoreElements();) {
				NetworkInterface itf = (NetworkInterface) itfs.nextElement();
				if (isUsableNetworkInterface(itf)) {
					synchronized (networkInterfaces) {
						networkInterfaces.add(itf);
					}
				}
			}
		} catch (Exception ignored) {
		}
	}

	private List<InetAddress> getInetAddresses(final NetworkInterface networkInterface) {
		return Collections.list(networkInterface.getInetAddresses());
	}

	private boolean isUsableNetworkInterface(final NetworkInterface iface) throws Exception {
		if (getInetAddresses(iface).isEmpty()) {
			return false;
		}
		if ((iface.getDisplayName() != null)
				&& (iface.getDisplayName().toLowerCase(Locale.ENGLISH).indexOf("vmnet") >= 0)) {
			return false;
		}
		String lower = iface.getName().toLowerCase(Locale.ENGLISH);
		if (lower.startsWith("vmnet") || lower.startsWith("vnic") || lower.startsWith("ppp") 
				|| lower.startsWith("lo") || (lower.indexOf("virtual") != -1)) {
			return false;
		}
		return true;
	}

	protected boolean isUsableAddress(final NetworkInterface networkInterface, 
			final InetAddress address) {
		return (address instanceof Inet4Address) || address.isLoopbackAddress();
	}

	private void sendMSearchMessage() {
		StringBuffer msearch = new StringBuffer();
		msearch.append("M-SEARCH * HTTP/1.1\r\n");
		msearch.append("Man: \"ssdp:discover\"\r\n");
		msearch.append("Mx: 3\r\n");
		msearch.append("Host: " + UPNP_BROADCAST_IP + ":" + SSDP_PORT + "\r\n");
		msearch.append("St: ssdp:all\r\n");
		msearch.append("\r\n");

		try {
			InetAddress group = InetAddress.getByName(UPNP_BROADCAST_IP);
			int msearch_length = msearch.length();
			byte[] msearch_bytes = msearch.toString().getBytes();
			DatagramPacket packet = new DatagramPacket(msearch_bytes, msearch_length, group, SSDP_PORT);
			mSearchSocket.send(packet);
		} catch (SocketException e) {
			Logger.warn("M-SEARCH SocketException : ", e);
		} catch (IOException e) {
			Logger.warn("M-SEARCH IOException : ", e);
		} catch (Exception e) {
			Logger.warn("M-SEARCH Exception : ", e);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void updated(final Dictionary properties) throws ConfigurationException {
		closeSockets();
		Logger.info("Updating Hue Bridge connection upon the config file");
		try {
			cancelMSearchTimer();
		} catch (Exception e) {
		}

		if (properties == null) {
			// enable SSDP discovery
			Logger.info("No found properties... Try SSDP discovery");
			initMSearchTimer();
		} else {
			try {
				if (Boolean.valueOf(properties.get(ENABLE_SSDP_DISCOVERY).toString())) {
					initMSearchTimer();
					return;
				}
			} catch (Exception ignored) {
			}
			final String descriptionUrl = (String) properties.get(HUE_BRIDGE_DESCRIPTION_URL);
			final String userId = (String) properties.get(HUE_BRIDGE_USER_ID);
			Logger.info("Config file description url: " + descriptionUrl 
					+ ", config file user id: " + userId);
			if (userId == null || userId == "") {
				Logger.warn("No userId for Hue Bridge update...");
			}
			if (descriptionUrl == null) {
				Logger.warn("No description...");
			} else {
				bridgeRegistrationThread = new Thread() {
					public void run() {
						try {
							String newUserId = discovery.processHueBridge(descriptionUrl, userId);
							if (newUserId != null && !newUserId.equals(userId)) {
								updatePropertiesFile(properties.get(SERVICE_ID) + ".properties",
										HUE_BRIDGE_USER_ID, newUserId);
							}
						} catch (IOException e) {
							Logger.warn("Error processing new bridge", e);
						}
					}
				};
				bridgeRegistrationThread.run();
			}	
		}
	}
	
	/**
	 * Set property and save in (config) file
	 * 
	 * @param filename: name of config file from configurations/services/ directory 
	 * @param key: name of the property to update
	 * @param value: value to update
	 */
	protected void updatePropertiesFile(String filename, String key, String value) {
        try {
            File file = new File("configurations/services/" + filename);
            Logger.info("Updating the: " + filename + " file, to save the: " + key + " property");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            ArrayList<String> lines = new ArrayList<String>();
            String line = br.readLine();
            while (line != null) {
            	if (line.contains(key)) {
                    line = key + " " + value;
                }
                lines.add(line);
                line = br.readLine();
            }
            fr.close();
            FileWriter fw = new FileWriter(file);
            BufferedWriter out = new BufferedWriter(fw);
            out.write(lines.toString().replace("[", "").replace("]", "").replace(", ", "\n"));
            out.close();
        }
        catch (IOException e) {
            Logger.error("Error while updating the: " + filename + " file, to save the: " + key + " property", e);
        }
    }

}
