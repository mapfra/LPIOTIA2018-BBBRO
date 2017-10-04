/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.lifx.impl.lan;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.om2m.sdt.home.lifx.impl.Logger;
import org.eclipse.om2m.sdt.home.lifx.impl.lan.frame.LIFXGlobalFrame;
import org.eclipse.om2m.sdt.home.lifx.impl.lan.frame.LIFXPayloadState;
import org.eclipse.om2m.sdt.home.lifx.impl.lan.frame.LIFXPayloadStatePower;
import org.eclipse.om2m.sdt.home.lifx.impl.lan.frame.LIFXPayloadStateService;
import org.eclipse.om2m.sdt.home.lifx.listener.LIFXDeviceListener;
import org.eclipse.om2m.sdt.home.lifx.listener.LIFXDiscoveryListener;

public class Server implements Runnable {

	private static final int DEFAULT_PORT = 56700;

	private static Server INSTANCE;

	public static final Server getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Server();
		}
		return INSTANCE;
	}

	private DatagramSocket datagramSocket;

	private Thread serverThread;

	private boolean toBeStopped = false;
	private boolean isStarted = false;
	private InetAddress localInetAddress;

	private final Map<Integer, List<LIFXGlobalFrame>> receivedFrames = new HashMap<>();
	private final Map<String, List<LIFXDeviceListener>> deviceListeners = new HashMap<>();
	private final List<LIFXDiscoveryListener> discoveryListeners = new ArrayList<>();
	
	private final List<DatagramPacket> receivedDatagrams = new ArrayList<>();

	private Server() {
	}
	
	public void init(InetAddress pLocalInetAddress) {
		try {
			toBeStopped = false;
			localInetAddress = pLocalInetAddress;
			datagramSocket = new DatagramSocket(56700, localInetAddress);
			datagramSocket.setReuseAddress(true);
			datagramSocket.setSoTimeout(1000); // 1 s timeout on the datagram socket

		} catch (Exception e) {
			e.printStackTrace();

		}
		serverThread = new Thread(this);
	}

	public void startServer() {
		if (!isStarted) {
			serverThread.start();
		}
	}

	public void stopServer() {
		if (toBeStopped == false) {
			toBeStopped = true;
			datagramSocket.disconnect();
			datagramSocket.close();
			serverThread.interrupt();
			try {
				serverThread.join();
			} catch (InterruptedException e) {
			}
			Logger.getInstance().info(Server.class, "DatagramSocket closed");
		}
		
		
	}

	@Override
	public void run() {
		byte[] buffer = new byte[1024];
		while (!toBeStopped) {

			DatagramPacket data = new DatagramPacket(buffer, buffer.length);
			try {
				datagramSocket.receive(data);
				
				ReceivedPacketHandler rph = new ReceivedPacketHandler(data);
				Thread t = new Thread(rph);
				t.start();
				
			} catch (SocketTimeoutException e) {
				// ignore
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	protected void notify(LIFXGlobalFrame globalFrame) {
		if (globalFrame.getPayload() instanceof LIFXPayloadStateService) {
			synchronized (discoveryListeners) {
				for (LIFXDiscoveryListener listener : discoveryListeners) {
					try {
						listener.notifyStateService(globalFrame);
					} catch (Exception e) {
					}
				}
			}
			return;
		}

		String deviceMacAddress = globalFrame.getFrameAddress().getTargetAsString();
		List<LIFXDeviceListener> listeners = null;
		synchronized (deviceListeners) {
			listeners = deviceListeners.get(deviceMacAddress);
		}
		if (listeners != null) {

			if (globalFrame.getPayload() instanceof LIFXPayloadState) {
				LIFXPayloadState state = (LIFXPayloadState) globalFrame.getPayload();
				synchronized (listeners) {
					for (LIFXDeviceListener listener : listeners) {
						try {
							listener.notifyState(state);
						} catch (Exception e) {
						}
					}
				}
			} else if (globalFrame.getPayload() instanceof LIFXPayloadStatePower) {
				LIFXPayloadStatePower statePower = (LIFXPayloadStatePower) globalFrame.getPayload();
				synchronized (listeners) {
					for (LIFXDeviceListener listener : listeners) {
						try {
							listener.notifyStatePower(statePower);
						} catch (Exception e) {
						}
					}
				}
			}
		}

	}

	public void sendLIFXGlobalFrame(LIFXGlobalFrame toBeSent, boolean registerSeqNumber) throws IOException {
		Logger.getInstance().info(Server.class, "sendLIFXGlobalFrame(frame=" + toBeSent.toString() + ", registerSeqNumber=" + registerSeqNumber + ")");
		InetAddress broadcastAddress = null;
		if (toBeSent.getRemoteHost() == null) {

			try {
				broadcastAddress = InetAddress
						.getByAddress(new byte[] { (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff });
			} catch (UnknownHostException e3) {
				e3.printStackTrace();
			}
		} else {
			broadcastAddress = toBeSent.getRemoteHost();
		}

		int portToBeUsed;
		if (toBeSent.getRemotePort() != -1) {
			portToBeUsed = toBeSent.getRemotePort();
		} else {
			portToBeUsed = DEFAULT_PORT;
		}

		byte[] packetBytes = toBeSent.getBytes();

		// create sequenceNumber in received
		if (registerSeqNumber) {
			synchronized (receivedFrames) {
				Logger.getInstance().info(Server.class, "sendLIFXGlobalFrame(frame=" + toBeSent.toString() + ", registerSeqNumber=" + registerSeqNumber + ") - add to receiveFrames seq=" + toBeSent.getFrameAddress().getSequenceNumber());
				receivedFrames.put(toBeSent.getFrameAddress().getSequenceNumber(), new ArrayList<LIFXGlobalFrame>());
			}
		}

		DatagramPacket packet = new DatagramPacket(packetBytes, packetBytes.length, broadcastAddress, portToBeUsed);
		datagramSocket.send(packet);
		Logger.getInstance().info(Server.class, "sendLIFXGlobalFrame(frame=" + toBeSent.toString() + ", registerSeqNumber=" + registerSeqNumber + ") - packet SENT");
	}

	public List<LIFXGlobalFrame> getResponse(int sequenceNumber, int timeout) {
		Logger.getInstance().info(Server.class, "getResponse(sequenceNumber=" + sequenceNumber + ", timeout=" + timeout + ")");
		List<LIFXGlobalFrame> globalFrames = null;

		long expirationTime = System.currentTimeMillis() + timeout;

		synchronized (receivedFrames) {
			globalFrames = receivedFrames.get(sequenceNumber);
		}

		boolean toBeWait = true;
		
		while(toBeWait) {
			synchronized (globalFrames) {
				if ((!globalFrames.isEmpty()) || (System.currentTimeMillis() > expirationTime)) {
					toBeWait = false;
				}
				
			}
			try {
				Logger.getInstance().info(Server.class, "getResponse(sequenceNumber=" + sequenceNumber + ", timeout=" + timeout + ") - sleep 500");
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		return globalFrames;

	}

	public List<LIFXGlobalFrame> sendLIFXGlobalFrameAndWaitForResponse(LIFXGlobalFrame lifxGlobalFrame, int timeout)
			throws IOException {
		sendLIFXGlobalFrame(lifxGlobalFrame, true);

		return getResponse(lifxGlobalFrame.getFrameAddress().getSequenceNumber(), timeout);
	}

	public void addLIFXDeviceListener(String macAddress, LIFXDeviceListener listener) {
		List<LIFXDeviceListener> listeners = null;
		synchronized (deviceListeners) {
			listeners = deviceListeners.get(macAddress);
			if (listeners == null) {
				listeners = new ArrayList<>();
				deviceListeners.put(macAddress, listeners);
			}
		}

		synchronized (listeners) {
			listeners.add(listener);
		}
	}

	public void removeLIFXDeviceListener(String macAddress, LIFXDeviceListener listener) {
		List<LIFXDeviceListener> listeners = null;
		synchronized (deviceListeners) {
			listeners = deviceListeners.get(macAddress);
		}

		if (listeners != null) {
			synchronized (listeners) {
				listeners.remove(listener);
			}
		}
	}

	public void addLIFXDiscoveryListener(LIFXDiscoveryListener listener) {
		synchronized (discoveryListeners) {
			discoveryListeners.add(listener);
		}
	}

	public void removeLIFXDiscoveryListener(LIFXDiscoveryListener listener) {
		synchronized (discoveryListeners) {
			discoveryListeners.remove(listener);
		}
	}
	
	
	private class ReceivedPacketHandler implements Runnable {
		
		private final DatagramPacket datagramPacket;

		public ReceivedPacketHandler(final DatagramPacket packet) {
			this.datagramPacket = packet;
		}
		
		@Override
		public void run() {
			byte[] dataByte = datagramPacket.getData();

			if (!localInetAddress.equals(datagramPacket.getAddress())) {
				
				

				LIFXGlobalFrame globalFrame = new LIFXGlobalFrame();
				try {
					globalFrame.setBytes(dataByte);
					globalFrame.setRemoteHost(datagramPacket.getAddress());
					Logger.getInstance().info(Server.class, "receive globalFrame=" + globalFrame.toString());
					
					int sequenceNumber = globalFrame.getFrameAddress().getSequenceNumber();
					List<LIFXGlobalFrame> frames = null;
					synchronized (receivedFrames) {
						frames = receivedFrames.get(sequenceNumber);
					}
					if (frames != null) {
						Logger.getInstance().info(Server.class, "frame expected");
						synchronized (frames) {
							frames.add(globalFrame);
							frames.notifyAll();
						}
					} else {
						Logger.getInstance().info(Server.class, "NOTIFY case");
						Server.this.notify(globalFrame);
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
