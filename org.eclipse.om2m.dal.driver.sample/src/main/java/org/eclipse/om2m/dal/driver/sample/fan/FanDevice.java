/*******************************************************************************
 * Copyright (c) 2017 Huawei Technologies Co., Ltd (http://www.huawei.com)
 * Huawei Base, Bantian,Longgang District ,Shenzhen ,Guangdong ,China
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial Contributors:
 *     Seven Ganlu : Developer
 *
 * New contributors :
 *******************************************************************************/


package org.eclipse.om2m.dal.driver.sample.fan;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.dal.Device;
import org.osgi.service.dal.DeviceException;
import org.osgi.service.dal.Function;
import org.osgi.service.dal.FunctionEvent;
import org.osgi.service.dal.functions.data.AlarmData;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.eclipse.om2m.dal.driver.sample.Activator;
import org.eclipse.om2m.dal.driver.sample.fan.function.Countdown;
import org.eclipse.om2m.dal.driver.sample.fan.function.FaultDetection;
import org.eclipse.om2m.dal.driver.sample.fan.function.Speed;
import org.eclipse.om2m.dal.driver.sample.fan.function.Switch;

/**
 * Fan device (UI interface and control logic)
 */
public class FanDevice extends JFrame implements Device {

	private static final long serialVersionUID = -3749351505120115293L;

	/** Icons of the emulated device interface */
	private final ImageIcon iconFan1 = new ImageIcon(FrameworkUtil.getBundle(
			FanDevice.class).getResource("images/fan1.png"));;
	private final ImageIcon iconFan2 = new ImageIcon(FrameworkUtil.getBundle(
			FanDevice.class).getResource("images/fan2.png"));;
	private final ImageIcon iconFan3 = new ImageIcon(FrameworkUtil.getBundle(
			FanDevice.class).getResource("images/fan3.png"));;
	private final ImageIcon iconFan4 = new ImageIcon(FrameworkUtil.getBundle(
			FanDevice.class).getResource("images/fan4.png"));;
	private final ImageIcon iconFan5 = new ImageIcon(FrameworkUtil.getBundle(
			FanDevice.class).getResource("images/fan5.png"));;
	private final ImageIcon iconFan6 = new ImageIcon(FrameworkUtil.getBundle(
			FanDevice.class).getResource("images/fan6.png"));;
	private final ImageIcon iconFan7 = new ImageIcon(FrameworkUtil.getBundle(
			FanDevice.class).getResource("images/fan7.png"));;
	private final ImageIcon iconFan8 = new ImageIcon(FrameworkUtil.getBundle(
			FanDevice.class).getResource("images/fan8.png"));;
	private final ImageIcon iconFan9 = new ImageIcon(FrameworkUtil.getBundle(
			FanDevice.class).getResource("images/fan9.png"));;
	private final ImageIcon iconFan10 = new ImageIcon(FrameworkUtil.getBundle(
			FanDevice.class).getResource("images/fan10.png"));;
	private final ImageIcon iconFan11 = new ImageIcon(FrameworkUtil.getBundle(
			FanDevice.class).getResource("images/fan11.png"));;
	private final ImageIcon iconFan12 = new ImageIcon(FrameworkUtil.getBundle(
			FanDevice.class).getResource("images/fan12.png"));;
	private final ImageIcon iconFan13 = new ImageIcon(FrameworkUtil.getBundle(
			FanDevice.class).getResource("images/fan13.png"));;

	private final ImageIcon[] iconFans = new ImageIcon[] { iconFan1, iconFan2,
			iconFan3, iconFan4, iconFan5, iconFan6, iconFan7, iconFan8,
			iconFan9, iconFan10, iconFan11, iconFan12, iconFan13 };

	private final ImageIcon iconButton0_normal = new ImageIcon(FrameworkUtil
			.getBundle(FanDevice.class)
			.getResource("images/button0_normal.png"));;

	private final ImageIcon iconButton0_clicking = new ImageIcon(FrameworkUtil
			.getBundle(FanDevice.class).getResource(
					"images/button0_clicking.png"));;

	private final ImageIcon iconButton1_normal = new ImageIcon(FrameworkUtil
			.getBundle(FanDevice.class)
			.getResource("images/button1_normal.png"));;
	private final ImageIcon iconButton1_clicking = new ImageIcon(FrameworkUtil
			.getBundle(FanDevice.class).getResource(
					"images/button1_clicking.png"));;
	private final ImageIcon iconButton1_clicked = new ImageIcon(FrameworkUtil
			.getBundle(FanDevice.class).getResource(
					"images/button1_clicked.png"));;

	private final ImageIcon iconButton2_normal = new ImageIcon(FrameworkUtil
			.getBundle(FanDevice.class)
			.getResource("images/button2_normal.png"));;
	private final ImageIcon iconButton2_clicking = new ImageIcon(FrameworkUtil
			.getBundle(FanDevice.class).getResource(
					"images/button2_clicking.png"));;
	private final ImageIcon iconButton2_clicked = new ImageIcon(FrameworkUtil
			.getBundle(FanDevice.class).getResource(
					"images/button2_clicked.png"));;

	private final ImageIcon iconTimer0 = new ImageIcon(FrameworkUtil.getBundle(
			FanDevice.class).getResource("images/timer0.png"));;
	private final ImageIcon iconTimer1 = new ImageIcon(FrameworkUtil.getBundle(
			FanDevice.class).getResource("images/timer1.png"));;
	private final ImageIcon iconTimer2 = new ImageIcon(FrameworkUtil.getBundle(
			FanDevice.class).getResource("images/timer2.png"));;
	private final ImageIcon iconTimer3 = new ImageIcon(FrameworkUtil.getBundle(
			FanDevice.class).getResource("images/timer3.png"));;
	private final ImageIcon iconTimer4 = new ImageIcon(FrameworkUtil.getBundle(
			FanDevice.class).getResource("images/timer4.png"));;
	private final ImageIcon iconTimer5 = new ImageIcon(FrameworkUtil.getBundle(
			FanDevice.class).getResource("images/timer5.png"));;

	private final ImageIcon[] iconTimers = new ImageIcon[] { iconTimer0,
			iconTimer1, iconTimer2, iconTimer3, iconTimer4, iconTimer5 };

	private final ImageIcon iconReduce_normal = new ImageIcon(FrameworkUtil
			.getBundle(FanDevice.class).getResource("images/reduce_normal.png"));;
	private final ImageIcon iconReduce_clicked = new ImageIcon(FrameworkUtil
			.getBundle(FanDevice.class)
			.getResource("images/reduce_clicked.png"));;

	private final ImageIcon iconPlus_normal = new ImageIcon(FrameworkUtil
			.getBundle(FanDevice.class).getResource("images/plus_normal.png"));;
	private final ImageIcon iconPlus_clicked = new ImageIcon(FrameworkUtil
			.getBundle(FanDevice.class).getResource("images/plus_clicked.png"));;

	/** Logger */
	private static Log LOGGER = LogFactory.getLog(FanDevice.class);

	/** Device driver, id and uid */
	private final String DRIVER = "fan";
	private String devId = Integer.toHexString(1);
	private String uid = DRIVER + ":" + devId;

	/** Device properties */
	private Dictionary<String, Object> props = null;

	/** Service registration and reference of this device */
	private ServiceRegistration<?> devReg = null;
	private ServiceReference<?> devRef = null;

	/** Animation refresh interval */
	private static int LEVEL_0_INTERVAL = 1000;
	private static int LEVEL_1_INTERVAL = 40;
	private static int LEVEL_2_INTERVAL = 25;

	private int interval = LEVEL_0_INTERVAL;

	/** Device function services */
	private Switch powerService = null;
	private Speed speedService = null;
	private Countdown countdownService = null;
	private FaultDetection faultDetectionService = null;

	private JPanel contentPane;
	private JPanel fanPane;
	private JPanel controlPane;
	private JLabel labelFan;
	private JLabel labelMinus;
	private JLabel labelTimer;
	private JLabel labelPlus;
	private JLabel labelButton0;
	private JLabel labelButton1;
	private JLabel labelButton2;

	/** Static Fan Device object */
	private static FanDevice fanDevice = null;

	/**
	 * Create the Device and set it visible.
	 * 
	 * @param
	 * @return
	 */
	public static void init() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					fanDevice = new FanDevice();
					fanDevice.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * remove device service from service registry.
	 * 
	 * @param
	 * @return
	 */
	public static void stop() {
		try {
			fanDevice.remove();
		} catch (DeviceException e) {
			e.printStackTrace();
		}
	}

	/**
	 * FanDevice constructor, create function service and initial UI interface
	 * 
	 * @param
	 * @return
	 */
	private FanDevice() {

		// create function service
		powerService = new Switch(uid, "powerswitch");
		speedService = new Speed(uid, "speed");
		countdownService = new Countdown(uid, "countdown");
		faultDetectionService = new FaultDetection(uid, "faultdetection");

		// Setup the device properties
		setupDeviceProperties();

		// Register device service
		LOGGER.info("Register FanDevice (" + uid + ") ...");
		devReg = Activator.getContext().registerService(Device.class.getName(),
				this, props);
		devRef = devReg.getReference();
		LOGGER.info("FanDevice (" + uid + ") is registered.");

		// initial UI
		setTitle("Simulated DAL Device");
		setLocationByPlatform(true);
		setVisible(false);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(10, 10, 650, 500);

		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(null);
		setContentPane(contentPane);

		fanPane = new JPanel();
		fanPane.setBounds(10, 10, 382, 429);
		fanPane.setLayout(null);

		controlPane = new JPanel();
		controlPane.setLayout(null);
		controlPane.setBounds(402, 10, 200, 429);
		contentPane.setLayout(null);

		contentPane.add(fanPane);
		contentPane.add(controlPane);

		labelFan = new JLabel("");
		labelFan.setIcon(iconFans[0]);
		labelFan.setBounds(0, 0, 382, 429);
		fanPane.add(labelFan);

		labelMinus = new JLabel("");
		labelMinus.setBounds(20, 85, 29, 120);
		labelMinus.setIcon(iconReduce_normal);
		labelMinus.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {

				// determine if the input is valid
				if (validKey(e) != true) {
					return;
				}

				// reduce the count down value
				try {
					if (countdownService.getData().getLevel()
							.compareTo(Countdown.MIN_VALUE) > 0) {

						countdownService.reduce();
					}
				} catch (DeviceException devException) {
					devException.printStackTrace();
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				labelMinus.setIcon(iconReduce_clicked);
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				labelMinus.setIcon(iconReduce_normal);
			}
		});

		labelTimer = new JLabel("");
		labelTimer.setBounds(30, 85, 120, 120);
		labelTimer.setIcon(iconTimers[0]);

		labelPlus = new JLabel("");
		labelPlus.setBounds(142, 85, 29, 120);
		labelPlus.setIcon(iconPlus_normal);

		labelPlus.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {

				// determine if the input is valid
				if (validKey(e) != true) {
					return;
				}

				// Increase the count down value
				try {
					if (countdownService.getData().getLevel()
							.compareTo(Countdown.MAX_VALUE) < 0) {

						countdownService.increase();
					}
				} catch (DeviceException devException) {
					devException.printStackTrace();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				labelPlus.setIcon(iconPlus_clicked);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				labelPlus.setIcon(iconPlus_normal);
			}
		});

		labelButton0 = new JLabel("");
		labelButton0.setIcon(iconButton0_normal);
		labelButton0.setBounds(30, 215, 50, 120);

		labelButton1 = new JLabel("");
		labelButton1.setIcon(iconButton1_normal);
		labelButton1.setBounds(80, 215, 50, 120);

		labelButton2 = new JLabel("");
		labelButton2.setIcon(iconButton2_normal);
		labelButton2.setBounds(130, 215, 50, 120);

		labelButton0.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {

				// determine if the input is valid
				if (validKey(arg0) != true) {
					return;
				}

				// Button 0 is pressed
				try {
					if (speedService.getData().getLevel()
							.compareTo(Speed.LEVEL_0) != 0) {
						speedService.setData(Speed.LEVEL_0, "");
						labelButton1.setIcon(iconButton1_normal);
						labelButton2.setIcon(iconButton2_normal);
					}

					// Turn off the power
					if (true == powerService.getData().getValue()) {
						powerService.setFalse();
					}

				} catch (DeviceException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				labelButton0.setIcon(iconButton0_clicking);
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				labelButton0.setIcon(iconButton0_normal);
			}
		});

		labelButton1.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {

				// determine if the input is valid
				if (validKey(arg0) != true) {
					return;
				}

				// Button 1 is pressed
				try {

					// Turn on the power
					if (true != powerService.getData().getValue()) {
						powerService.setTrue();
					}

					// Set the speed to level 1
					if (speedService.getData().getLevel()
							.compareTo(Speed.LEVEL_1) != 0) {
						speedService.setData(Speed.LEVEL_1, "");
						labelButton1.setIcon(iconButton1_clicked);
						labelButton2.setIcon(iconButton2_normal);
						interval = LEVEL_1_INTERVAL;
					}
				} catch (DeviceException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				labelButton1.setIcon(iconButton1_clicking);
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				labelButton1.setIcon(iconButton1_normal);
			}
		});

		labelButton2.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {

				// determine if the input is valid
				if (validKey(arg0) != true) {
					return;
				}

				// Button 2 is pressed
				try {

					// Turn on the power
					if (true != powerService.getData().getValue()) {
						powerService.setTrue();
					}

					// Set the speed to level 2
					if (speedService.getData().getLevel()
							.compareTo(Speed.LEVEL_2) != 0) {
						speedService.setData(Speed.LEVEL_2, "");
						labelButton2.setIcon(iconButton2_clicked);
						labelButton1.setIcon(iconButton1_normal);
						interval = LEVEL_2_INTERVAL;
					}

				} catch (DeviceException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				labelButton2.setIcon(iconButton2_clicking);
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				labelButton2.setIcon(iconButton2_normal);
			}
		});

		controlPane.add(labelMinus);
		controlPane.add(labelTimer);
		controlPane.add(labelPlus);
		controlPane.add(labelButton0);
		controlPane.add(labelButton1);
		controlPane.add(labelButton2);

		// Animation playing thread
		new Thread() {
			public void run() {
				try {

					int pos = 0;
					while (true) {

						// Periodic refresh the picture when the power is on and
						// speed level is not 0
						if (true == powerService.getData().getValue()
								&& speedService.getData().getLevel()
										.compareTo(Speed.LEVEL_0) != 0) {
							if (pos >= iconFans.length) {
								pos = 0;
							}
							labelFan.setIcon(iconFans[pos++]);
							Thread.sleep(interval);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();

		
		// Register the listener of Count down function service
		Dictionary<String, Object> dict = new Hashtable<String, Object>();
		dict.put(EventConstants.EVENT_TOPIC,
				FunctionEvent.TOPIC_PROPERTY_CHANGED);
		dict.put(EventConstants.EVENT_FILTER, "(" + Function.SERVICE_UID + "="
				+ countdownService.getServiceProperty(Function.SERVICE_UID)
				+ ")");

		ServiceRegistration<?> register = Activator.getContext()
				.registerService(EventHandler.class.getName(),
						new EventHandler() {

							@Override
							public void handleEvent(Event arg0) {
								try {
									
									// Refresh the count down button picture
									labelTimer
											.setIcon(iconTimers[countdownService
													.getData().getLevel()
													.intValue()]);
									
									// When count down clock hits 0, turn off the power								
									if (countdownService.getData().getLevel()
											.compareTo(Countdown.MIN_VALUE) == 0
											&& powerService.getData()
													.getValue() == true) {
										powerService.setFalse();
									} 
									// When reset count down clock, turn on the power
									else if (countdownService.getData()
											.getLevel()
											.compareTo(Countdown.MIN_VALUE) > 0
											&& powerService.getData()
													.getValue() == false
											&& speedService.getData()
													.getLevel()
													.compareTo(Speed.LEVEL_0) != 0) {
										powerService.setTrue();
									}

								} catch (DeviceException e) {
									e.printStackTrace();
								}
							}
						}, dict);
		
		if (register == null) {
			LOGGER.error(String.format("Event handler (%s) register failed!",
					countdownService.getServiceProperty(Function.SERVICE_UID)));
		}
	}

	/**
	 * determine if there is a key fault and generate a alarm
	 * 
	 * @param
	 * @return boolean
	 */
	private boolean validKey(MouseEvent event) {

		if (event.getClickCount() > 1) {
			faultDetectionService.setAlarm(new AlarmData(System
					.currentTimeMillis(), null, AlarmData.SEVERITY_MINOR,
					AlarmData.TYPE_HARDWARE_FAIL));

			return false;
		}
		return true;
	}

	/**
	 * Setup the device properties
	 * 
	 * @param
	 * @return
	 */
	private void setupDeviceProperties() {
		props = new Hashtable<String, Object>();

		props.put(SERVICE_UID, uid);
		props.put(SERVICE_DRIVER, DRIVER);
		props.put(SERVICE_NAME, uid);
		props.put(SERVICE_STATUS, STATUS_PROCESSING);
	}

	/**
	 * Return the service property by specifying the key
	 * 
	 * @param
	 * @return Object
	 */
	@Override
	public Object getServiceProperty(String propKey) {
		if (null != devRef) {
			return devRef.getProperty(propKey);
		}
		return props.get(propKey);
	}

	/**
	 * Return all service property keys
	 * 
	 * @param
	 * @return String[]
	 */
	@Override
	public String[] getServicePropertyKeys() {
		if (null != devRef) {
			return devRef.getPropertyKeys();
		}
		
		String[] keys = new String[props.size()];

		int count = 0;
		
		for (Enumeration<String> key = props.keys(); key.hasMoreElements();) {
			keys[count++] = key.nextElement();
		}

		return keys;
	}

	/**
	 * set the device status
	 * 
	 * @param status - device status
	 * @return
	 */
	public void setStatus(Integer status) {
		props.put(SERVICE_STATUS, status);
		devReg.setProperties(props);
	}

	/**
	 * Remove device, is to unregister the device service from the registry
	 * 
	 * @param
	 * @return
	 */
	@Override
	public void remove() throws DeviceException {
		
		LOGGER.info("Remove FanDevice (" + uid + ") ...");
		// Update the device status
		setStatus(STATUS_DETAIL_REMOVING);
		
		// Remove all device functions
		LOGGER.info("Remove functions of FanDevice (" + uid + ") ...");
		powerService.remove();
		speedService.remove();
		countdownService.remove();
		faultDetectionService.remove();

		// Unregister the device service
		LOGGER.info("Functions of FanDevice (" + uid + ") is removed!");
		devReg.unregister();
		
		// dispose the UI interface
		setVisible(false);
		dispose();
		LOGGER.info("FanDevice (" + uid + ") is removed!");
	}
}
