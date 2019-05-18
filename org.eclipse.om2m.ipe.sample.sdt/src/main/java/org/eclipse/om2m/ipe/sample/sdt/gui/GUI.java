/*******************************************************************************
 * Copyright (c) 2013-2016 LAAS-CNRS (www.laas.fr)
 * 7 Colonel Roche 31077 Toulouse - France
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial Contributors:
 *     Thierry Monteil : Project manager, technical co-manager
 *     Mahdi Ben Alaya : Technical co-manager
 *     Samir Medjiah : Technical co-manager
 *     Khalil Drira : Strategy expert
 *     Guillaume Garzone : Developer
 *     François Aïssaoui : Developer
 *
 * New contributors :
 *******************************************************************************/
package org.eclipse.om2m.ipe.sample.sdt.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.ipe.sample.sdt.monitor.SampleMonitor;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.events.SDTEventListener;
import org.eclipse.om2m.sdt.events.SDTNotification;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.types.DeviceType;
import org.eclipse.om2m.sdt.home.types.ModuleType;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * The Graphical User Interface of the IPE sample.
 */
public class GUI extends JFrame implements SDTEventListener {
	
    /** Logger */
    static Log LOGGER = LogFactory.getLog(GUI.class);
    /** Serial Version UID */
    private static final long serialVersionUID = 1L;
    /** LAMP_ON Icon */
    static ImageIcon iconLampON = new ImageIcon(FrameworkUtil.getBundle(GUI.class).getResource("images/Lamp_ON.png"));
    /** LAMP_OFF Icon */
    static ImageIcon iconLampOFF = new ImageIcon(FrameworkUtil.getBundle(GUI.class).getResource("images/Lamp_OFF.png"));
    /** BUTTON_ON Icon */
    static ImageIcon iconButtonON = new ImageIcon(FrameworkUtil.getBundle(GUI.class).getResource("images/Btn_ON.png"));
    /** BUTTON_OFF Icon */
    static ImageIcon iconButtonOFF = new ImageIcon(FrameworkUtil.getBundle(GUI.class).getResource("images/Btn_OFF.png"));
    /** GUI Frame */
    static GUI frame;
    /** LAMP_0 LABEL */
    static JLabel LABEL_LAMP_0 = new JLabel("");
    /** LAMP_1 LABEL */
    static JLabel LABEL_LAMP_1 = new JLabel("");
    /** LAMP_0 ID */
    static String LAMP_0 = "LAMP_0";
    /** LAMP_1 ID */
    static String LAMP_1 = "LAMP_1";
    
    static private final int B = 10;
    static private final int W0 = 320;
    static private final int W1 = 200;
    static private final int H0 = 250;
    static private final int WL = 150;
    static private final int HL = 230;
    static private final int WB = 120;
    static private final int HB = 150;
    
    /** GUI Content Panel */
    private JPanel contentPanel;
	private JTextArea redArea, greenArea, blueArea;
	// Default color: yellow (rgb: 255,255,0)
	private int red = 255, green = 255, blue = 0;

    /**
     * Initiate The GUI.
     * @param bundleContext 
     */
    public static void init(final BundleContext bundleContext) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame = new GUI(bundleContext);
                    frame.setVisible(true);
                } catch (Exception e) {
                    LOGGER.error("GUI init Error", e);
                }
            }
        });
    }

    /**
     * Stop the GUI.
     */
    public static void stop() {
        frame.setVisible(false);
        frame.dispose();
    }

    /**
     * Creates the frame.
     * @param bundleContext 
     */
    private GUI(BundleContext bundleContext) {
        setLocationByPlatform(true);
        setVisible(false);
        setResizable(false);
        setTitle("Sample Simulated IPE");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-500)/2, (screenSize.height-570)/2, W0+W1+4*B, 2*H0+3*B+30);

        contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPanel);
        contentPanel.setLayout(null);

        // Lamp0 Switcher0
        JPanel panel_Lamp0 = new JPanel();
        panel_Lamp0.setBounds(B, B, W0, H0);
        contentPanel.add(panel_Lamp0);
        panel_Lamp0.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        panel_Lamp0.setLayout(null);
        LABEL_LAMP_0.setIcon(iconLampOFF);
        LABEL_LAMP_0.setHorizontalTextPosition(SwingConstants.CENTER);
        LABEL_LAMP_0.setHorizontalAlignment(SwingConstants.CENTER);
        LABEL_LAMP_0.setBounds(B, B, WL, HL);
		LABEL_LAMP_0.setBorder(new LineBorder(new Color(red, green, blue), 5, true));
        panel_Lamp0.add(LABEL_LAMP_0);

        // Lamp0 Switch Button
        JButton button_Lamp0 = new JButton();
        button_Lamp0.setOpaque(false);
        button_Lamp0.setPressedIcon(iconButtonON);
        button_Lamp0.setIcon(iconButtonOFF);
        button_Lamp0.setBounds(2*B+WL, 50, WB, HB);
        panel_Lamp0.add(button_Lamp0);
        button_Lamp0.setMinimumSize(new Dimension(30, 23));
        button_Lamp0.setMaximumSize(new Dimension(30, 23));
        button_Lamp0.setPreferredSize(new Dimension(30, 23));

        JLabel labelSwitcher0 = new JLabel("Switch LAMP_0");
        labelSwitcher0.setFont(new Font("Vani", Font.BOLD | Font.ITALIC, 14));
        labelSwitcher0.setFocusCycleRoot(true);
        labelSwitcher0.setBorder(null);
        labelSwitcher0.setAutoscrolls(true);
        labelSwitcher0.setBounds(2*B+WL, 50+HB, WB, 30);
        panel_Lamp0.add(labelSwitcher0);
        // Listener for Lamp0 Switch Button
        button_Lamp0.addActionListener(new ActionListener() {
            // Button Clicked
            public void actionPerformed(ActionEvent evt) {
                // Change Lamp0 State
                new Thread() {
                    public void run() {
                        // Send switch request to switch lamp0 state
                    	try {
							SampleMonitor.switchLamp(LAMP_0);
						} catch (DataPointException e) {
							LOGGER.warn("Data error", e);
						} catch (AccessException e) {
							LOGGER.warn("Access error", e);
						}
                    }
                }.start();
            }
        });

        // Lamp1 Switcher 1
        JPanel panel_Lamp1 = new JPanel();
        panel_Lamp1.setBounds(B, 2*B+H0, W0, H0);
        contentPanel.add(panel_Lamp1);
        panel_Lamp1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        panel_Lamp1.setLayout(null);

        LABEL_LAMP_1.setIcon(iconLampOFF);
        LABEL_LAMP_1.setHorizontalTextPosition(SwingConstants.CENTER);
        LABEL_LAMP_1.setHorizontalAlignment(SwingConstants.CENTER);
        LABEL_LAMP_1.setBounds(B, B, WL, HL);
		LABEL_LAMP_1.setBorder(new LineBorder(new Color(red, green, blue), 5, true));
        panel_Lamp1.add(LABEL_LAMP_1);

        // Lamp1 Switch Button
        JButton button_Lamp1 = new JButton();
        button_Lamp1.setOpaque(false);
        button_Lamp1.setPressedIcon(iconButtonON);
        button_Lamp1.setIcon(iconButtonOFF);
//        button_Lamp1.setBounds(187, 44, 122, 156);
        button_Lamp1.setBounds(2*B+WL, 50, WB, HB);
        panel_Lamp1.add(button_Lamp1);
        button_Lamp1.setMinimumSize(new Dimension(30, 23));
        button_Lamp1.setMaximumSize(new Dimension(30, 23));
        button_Lamp1.setPreferredSize(new Dimension(30, 23));

        JLabel labelSwitcher1 = new JLabel("Switch LAMP_1");
        labelSwitcher1.setFont(new Font("Vani", Font.BOLD | Font.ITALIC, 14));
        labelSwitcher1.setFocusCycleRoot(true);
        labelSwitcher1.setBorder(null);
        labelSwitcher1.setAutoscrolls(true);
        labelSwitcher1.setBounds(2*B+WL, 50+HB, WB, 30);
//        labelSwitcher1.setBounds(187, 199, 118, 29);
        panel_Lamp1.add(labelSwitcher1);
        // Listener for Lamp1 Switch Button
        button_Lamp1.addActionListener(new ActionListener() {
            //Switch Button clicked
            public void actionPerformed(ActionEvent evt) {
                // Change Lamp1 State
                new Thread() {
                    public void run() {
                        // Send switch request to switch lamp1 state
                    	try {
							SampleMonitor.switchLamp(LAMP_1);
						} catch (DataPointException e) {
							LOGGER.warn("Data error", e);
						} catch (AccessException e) {
							LOGGER.warn("Access error", e);
						}
                    }
                }.start();
            }
        });

        // Switcher All lamps
        JPanel switchAllPanel = new JPanel();
        switchAllPanel.setBounds(2*B+W0, B, W1, H0);
        contentPanel.add(switchAllPanel);
        switchAllPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        switchAllPanel.setLayout(null);

        JButton buttonAllLamp = new JButton();
        buttonAllLamp.setOpaque(false);
        buttonAllLamp.setPressedIcon(iconButtonON);
        buttonAllLamp.setIcon(iconButtonOFF);
        buttonAllLamp.setBounds(40, 50, WB, HB);
        switchAllPanel.add(buttonAllLamp);
        buttonAllLamp.setMinimumSize(new Dimension(30, 23));
        buttonAllLamp.setMaximumSize(new Dimension(30, 23));
        buttonAllLamp.setPreferredSize(new Dimension(30, 23));

        JLabel labelSwitchAll = new JLabel("Switch All");
        labelSwitchAll.setAutoscrolls(true);
        labelSwitchAll.setFont(new Font("Vani", Font.BOLD | Font.ITALIC, 14));
        labelSwitchAll.setFocusCycleRoot(true);
        labelSwitchAll.setBorder(null);
        labelSwitchAll.setBounds(60, 60+HB, WB, 30);
        switchAllPanel.add(labelSwitchAll);
        // Listener of Switch all Button
        buttonAllLamp.addActionListener(new ActionListener() {
            // Switch Button Clicked
            public void actionPerformed(ActionEvent evt) {
                // Change all lamps states
                new Thread() {
                    public void run() {
                        // Send switch all request to create a content with the current State
                    	try {
							SampleMonitor.switchAll();
						} catch (DataPointException e) {
							LOGGER.warn("Data error", e);
						} catch (AccessException e) {
							LOGGER.warn("Access error", e);
						}
                    }
                }.start();
            }
        });
        
        JPanel colorPanel = new JPanel();
        colorPanel.setBounds(2*B+W0, 2*B+H0, W1, H0);
        contentPanel.add(colorPanel);
        colorPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        colorPanel.setLayout(null);
		
        JLabel colorLabel = new JLabel("Set Color");
        colorLabel.setFont(new Font("Vani", Font.BOLD | Font.ITALIC, 14));
        colorLabel.setFocusCycleRoot(true);
        colorLabel.setBorder(null);
        colorLabel.setAutoscrolls(true);
        colorLabel.setBounds(60, 20, 200, 30);
        colorPanel.add(colorLabel);
		
        JLabel redLabel = new JLabel("Red (0-255)");
        redLabel.setFont(new Font("Vani", Font.ITALIC, 14));
        redLabel.setFocusCycleRoot(true);
        redLabel.setBorder(null);
        redLabel.setAutoscrolls(true);
        redLabel.setBounds(20, 60, 100, 30);
        colorPanel.add(redLabel);
        
        redArea = new JTextArea();
        redArea.setBorder(null);
        redArea.setAutoscrolls(true);
        redArea.setBounds(120, 60, 30, 20);
        redArea.setText("" + red);
        colorPanel.add(redArea);
		
        JLabel greenLabel = new JLabel("Green (0-255)");
        greenLabel.setFont(new Font("Vani", Font.ITALIC, 14));
        greenLabel.setFocusCycleRoot(true);
        greenLabel.setBorder(null);
        greenLabel.setAutoscrolls(true);
        greenLabel.setBounds(20, 100, 100, 30);
        colorPanel.add(greenLabel);
        
        greenArea = new JTextArea();
        greenArea.setBorder(null);
        greenArea.setAutoscrolls(true);
        greenArea.setBounds(120, 100, 30, 20);
        greenArea.setText("" + green);
        colorPanel.add(greenArea);
		
        JLabel blueLabel = new JLabel("Blue (0-255)");
        blueLabel.setFont(new Font("Vani", Font.ITALIC, 14));
        blueLabel.setFocusCycleRoot(true);
        blueLabel.setBorder(null);
        blueLabel.setAutoscrolls(true);
        blueLabel.setBounds(20, 140, 100, 30);
        colorPanel.add(blueLabel);
        
        blueArea = new JTextArea();
        blueArea.setBorder(null);
        blueArea.setAutoscrolls(true);
        blueArea.setBounds(120, 140, 30, 20);
        blueArea.setText("" + blue);
        colorPanel.add(blueArea);

        JButton colorButton = new JButton();
        colorButton.setOpaque(false);
        colorButton.setText("OK");
        colorButton.setBounds(60, 190, 60, 30);
        colorPanel.add(colorButton);
        colorButton.setMinimumSize(new Dimension(30, 23));
        colorButton.setMaximumSize(new Dimension(30, 23));
        colorButton.setPreferredSize(new Dimension(30, 23));
        // Listener of Switch all Button
        colorButton.addActionListener(new ActionListener() {
            // Switch Button Clicked
            public void actionPerformed(ActionEvent evt) {
                // Change all lamps states
                new Thread() {
                    public void run() {
                        // Send switch all request to create a content with the current State
                    	try {
                    		LOGGER.info("Set color");
							SampleMonitor.setColor(Integer.parseInt(redArea.getText().trim()),
									Integer.parseInt(greenArea.getText().trim()),
									Integer.parseInt(blueArea.getText().trim()));
						} catch (DataPointException e) {
							LOGGER.warn("Data error", e);
						} catch (AccessException e) {
							LOGGER.warn("Access error", e);
						} catch (Exception e) {
							LOGGER.warn("Unknown error", e);
						}
                    }
                }.start();
            }
        });

		Dictionary<String, String> props = new Hashtable();
		props.put(SDTEventListener.DEVICES_DEFS, DeviceType.deviceLight.getDefinition());
		bundleContext.registerService(SDTEventListener.class.getName(),
				this, props);
    }

	@Override
	public void handleNotification(SDTNotification notif) {
		if (notif != null) {
			String moduleDef = ((Module)notif.getModule()).getDefinition();
			if (moduleDef.equals(ModuleType.binarySwitch.getDefinition())) {
				LOGGER.info("Received switch notif " + notif.getValue() 
						+ " for " + notif.getDevice().getId());
		        JLabel label = notif.getDevice().getId().endsWith("0")
		        		? LABEL_LAMP_0 : LABEL_LAMP_1;
		        label.setIcon((boolean) notif.getValue() ? iconLampON : iconLampOFF);
			} 
			else if (moduleDef.equals(ModuleType.colour.getDefinition())
					&& notif.getDevice().getId().endsWith("0")) {
				String rgb = notif.getDataPoint().getName();
				int val = (int) notif.getValue();
				LOGGER.info("Received color notif " + val + " for " + rgb);
				if (rgb.equals("red"))
					red = val;
				else if (rgb.equals("green"))
					green = val;
				else if (rgb.equals("blue"))
					blue = val;
				LABEL_LAMP_0.setBorder(new LineBorder(new Color(red, green, blue), 5, true));
				LABEL_LAMP_1.setBorder(new LineBorder(new Color(red, green, blue), 5, true));
			}
		}
	}

	@Override
	public void setAuthenticationThreadGroup(ThreadGroup group) {
	}

}
