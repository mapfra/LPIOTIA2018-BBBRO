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
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.hue.api.HueBridgeDevice;
import org.eclipse.om2m.hue.api.HueLightDevice;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * Utility class to discover Hue bridges.
 */
public class HueBridgeDiscovery {

     private static Log Logger = LogFactory.getLog(HueBridgeDiscovery.class);

    private BundleContext context;

    private Map<String, HueBridgeDevice> hueBridges;
    private Map<String, ServiceRegistration<?>> hueBridgeServices;
    private Map<String, Timer> hueBridgeLightsTimer;
    private Map<String, Map<String, ServiceRegistration<?>>> registeredHueLights;

    private List<String> downloadedDescriptions;

    private String userId = null;
    private Timer hueLightsTimer = null;
//    private String hueNetworkLocation;

    abstract class HueLightsTimerTask extends TimerTask {

        protected HueBridgeDevice taskBridge = null;
        protected Map<String, ServiceRegistration<?>> taskLights = null;

        public HueLightsTimerTask(final HueBridgeDevice bridge, 
        		final Map<String, ServiceRegistration<?>> lights) {
            this.taskBridge = bridge;
            taskLights = lights;
        }

    }

    public HueBridgeDiscovery(final BundleContext context) {
        this.context = context;
        this.hueBridges = new HashMap<String, HueBridgeDevice>();
        this.hueBridgeServices = new HashMap<String, ServiceRegistration<?>>();
        this.hueBridgeLightsTimer = new HashMap<String, Timer>();
        this.registeredHueLights = new HashMap<String, Map<String, ServiceRegistration<?>>>();
        this.downloadedDescriptions = new ArrayList<String>();
    }

    /**
     * Process a hue bridge location description.
     *
     * @param location
     * @throws IOException
     */
    public void processNewHueBridge(final String location) throws IOException {
        if (location.endsWith("description.xml")
                && !downloadedDescriptions.contains(location)) {
            processHueBridge(location, null);
            downloadedDescriptions.add(location);
        }
    }

    public String processHueBridge(final String location) throws IOException {
        boolean hueBridgeFound = false;
        StringBuffer xmlDescription = new StringBuffer();
        BufferedReader in = null;
        try {
            URL url = new URL(location);
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = in.readLine()) != null) {
                xmlDescription.append(line);
                hueBridgeFound |= (line.toLowerCase().indexOf("<modelname>philips hue bridge") >= 0);
            }
        } finally {
            try {
                in.close();
            } catch (Exception ignored) {
            }
        }
        if (!hueBridgeFound) {
            Logger.warn("No Hue bridge found");
            return null;
        }

        // --------------------------------
        // New Hue Bridge arrived
        // --------------------------------
        Logger.info("Hue bridge found: " + location + " with userId: " + this.userId);
        HueBridgeDeviceImpl hueBridge = new HueBridgeDeviceImpl(location, xmlDescription.toString(), Optional.of(this.userId));
        if (hueBridge.isBridgeConnected())
            registerBridge(hueBridge);
        else
            Logger.warn("Cannot register Hue bridge: driver is not connected to the bridge");
        return hueBridge.getUserName();
    }

    // Called when user id was specified in config file
    public String processHueBridge(final String location, final String id) throws IOException {
        try {
			Logger.info("ProcessHueBridge with userId: " + userId);
			this.userId = (id == null) ? "" : id;
			return processHueBridge(location);
		} catch (Throwable e) {
			Logger.warn("", e);
			throw new IOException(e);
		}        
    }

    @SuppressWarnings("unchecked")
    public void registerBridge(final HueBridgeDevice bridge) {
        String bridgeUDN = bridge.getUDN();
        if (hueBridges.containsKey(bridgeUDN)) {
            return;
        }
        hueBridges.put(bridgeUDN, bridge);

        // Register the Hue Bridge as a device !
        ServiceRegistration<?> rg = context.registerService(
                HueBridgeDevice.class.getName(),
                bridge, bridge.getProperties());

        hueBridgeServices.put(bridgeUDN, rg);
        registeredHueLights.put(bridgeUDN, 
        		new HashMap<String, ServiceRegistration<?>>());

        if (hueBridgeLightsTimer.containsKey(bridgeUDN)) {
            // already registered
            return;
        }

        // get lights periodically and register services		    				        			
        hueLightsTimer = new Timer();
        hueBridgeLightsTimer.put(bridgeUDN, hueLightsTimer);
        hueLightsTimer.scheduleAtFixedRate(new HueLightsTimerTask(bridge,
                registeredHueLights.get(bridgeUDN)) {
			public void run() {
                try {
                    // get lights
                    List<HueLightDevice> lights = taskBridge.getLights();

                    // unregister old lights
                    if (taskLights.size() > 0) {
                        List<String> idsToRemove = new ArrayList<String>();

                        for (Map.Entry<String, ServiceRegistration<?>> entry : taskLights.entrySet()) {
                            String id = entry.getKey();

                            boolean lightFound = false;
                            for (HueLightDevice light : lights) {
                                if (light.getId().equals(id)) {
                                    lightFound = true;
                                    break;
                                }
                            }
                            if (!lightFound) {
                                entry.getValue().unregister();
                                idsToRemove.add(id);
                            }
                        }
                        for (String id : idsToRemove) {
                            taskLights.remove(id);
                        }
                    }

                    // register new lights
                    for (HueLightDevice light : lights) {
                        if (!taskLights.containsKey(light.getId())) {
                            ServiceRegistration<?> reg = context.registerService(
                                    HueLightDevice.class.getName(),
                                    light, light.getProperties());
                            taskLights.put(light.getId(), reg);
                        }
                    }
                } catch (Exception e) {
                    Logger.warn("Looking for Hue Lights IOException : ", e);
                    // getLights failed => bridge left		    													
                    String bridgeUDN = taskBridge.getUDN();

                    // unregister lights of this bridge
                    for (ServiceRegistration<?> sr : taskLights.values()) {
                        sr.unregister();
                    }
                    taskLights.clear();
                    registeredHueLights.remove(bridgeUDN);

                    // remove description 
                    try {
                        downloadedDescriptions.remove(((HueBridgeDeviceImpl) taskBridge).getLocation());
                    } catch (Exception igonred) {
                    }

                    // unregister the bridge												
                    hueBridgeServices.get(bridgeUDN).unregister();
                    hueBridgeServices.remove(bridgeUDN);
                    hueBridges.remove(bridgeUDN);

                    //hueBridgeTimestamp.remove(bridgeUDN);
                    hueBridgeLightsTimer.get(bridgeUDN).cancel();
                    hueBridgeLightsTimer.remove(bridgeUDN);
                }
            }
        },
                0, //Set how long before to start calling the TimerTask (in milliseconds)
                10000);	//Set the amount of time between each execution (in milliseconds)
    }

    public void clearDescriptions() {
        downloadedDescriptions.clear();
    }

    public void stopBackgroundProcesses() {
        if(this.hueLightsTimer != null)
            this.hueLightsTimer.cancel();
    }
    
}
