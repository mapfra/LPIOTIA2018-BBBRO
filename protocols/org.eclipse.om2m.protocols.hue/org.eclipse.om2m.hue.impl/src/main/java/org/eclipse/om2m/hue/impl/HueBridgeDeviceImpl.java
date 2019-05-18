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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.hue.api.Group;
import org.eclipse.om2m.hue.api.HueBridgeDevice;
import org.eclipse.om2m.hue.api.HueLightDevice;
import org.eclipse.om2m.hue.api.types.AlertMode;
import org.eclipse.om2m.hue.api.types.HueException;
import org.eclipse.om2m.hue.api.types.LightEffect;
import org.eclipse.om2m.hue.api.types.LightState;
import org.eclipse.om2m.hue.api.types.UnknownHueGatewayException;
import org.eclipse.om2m.hue.impl.controller.Controller;
import org.eclipse.om2m.hue.impl.controller.Light;
import org.eclipse.om2m.hue.impl.controller.LightGroup;
import org.osgi.service.upnp.UPnPDevice;

/**
 * Implementation of the {@link HueBridgeDevice}
 */
public class HueBridgeDeviceImpl extends HueDeviceImpl implements HueBridgeDevice {

    /**
     * Logger
     */
    private static Log Logger = LogFactory.getLog(HueBridgeDeviceImpl.class);

    /**
     * location (UPNP) of Hue gateway
     */
    private String location;

    /**
     * indicates that the Hue bridge's IP address has been discovered
     */
    private boolean controllerAvailable;

    /**
     * indicates if there is a request of type state changed to be sent to Hue
     * gateway
     */
    private PendingStateChangeRequest request;

    /**
     * authorized userName previously peered with Hue gateway
     */
    private String userName;

    /**
     * controller that will communicate with Hue light gateway
     */
    private Controller controller;

    /**
     * connected lights on the bridge
     */
    private List<HueLightDevice> lights;

    // ====================================================================================
    @SuppressWarnings("unchecked")
	public HueBridgeDeviceImpl(final String location, final String description, Optional<String> userId) {
        super(description);
        Logger.info("HueBridgeDeviceImpl installation. User name: " + userId.get());
        this.location = location;
        this.lights = new ArrayList<HueLightDevice>();
        this.controllerAvailable = false;
        this.controller = null;
        this.userName = userId.get();
     
        String IP = "";
        try {
            IP = new URL((String) getProperties().get(UPnPDevice.PRESENTATION_URL))
                    .getHost();
        } catch (MalformedURLException e) {
            Logger.warn("", e);
        }

        try {
            Logger.info("Connecting with Hue bridge at: " + IP);
            // create controller - after successful pairinng with Hue Bridge onControllerPairedWithBridge method will be called
            this.controller = new Controller(IP, this.userName); 
            getLights();
            controllerAvailable = true;
            this.userName = this.controller.getUserName();
            if (request != null) {
                setStateToGroup(request.getLightState(), request.getRequestGroup());
            }  
        } catch (HueException e) {
            Logger.warn("", e);
        } catch (UnknownHueGatewayException e) {
            Logger.warn("", e);
        }
        
        properties.put("export.api.Read", "getUDN,getLights");
        properties.put("export.api.Action", "setWakeUp,setMeal,setNight,setHomeCinema,setParty,setOn,setOff");
    }

    /* (non-Javadoc)
	 * @see com.orange.basedrivers.hue.HueBrigdeDevice#getUDN()
     */
    public String getUDN() {
        return (String) properties.get(UPnPDevice.UDN);
    }

    /* (non-Javadoc)
	 * @see com.orange.basedrivers.hue.HueBrigdeDevice#getLights()
     */
    public List<HueLightDevice> getLights() throws HueException, UnknownHueGatewayException {
        lights.clear();
        for (Light light : controller.getLights()) {
            if (light.isReachable()) {
                lights.add(new HueLightDeviceImpl(this, light));
            }
        }
        return lights;
    }

    // ====================================================================================

    /* (non-Javadoc)
	 * @see com.orange.basedrivers.hue.HueBrigdeDevice#setWakeUp(int)
     */
    public void setWakeUp(final int group) throws HueException {
        // specify state of the light for WakeUp mode
        // white color => sat = 0
        LightState s = new LightState(true);
        s.setSat(0);
        s.setBri(100);
        s.setAlert(AlertMode.NONE);
        s.setEffect(LightEffect.NONE);
        setStateToGroup(s, group);
    }

    /* (non-Javadoc)
	 * @see com.orange.basedrivers.hue.HueBrigdeDevice#setMeal(int)
     */
    public void setMeal(final int group) throws HueException {
        // specify state of the light for Meal mode
        // orange color => hue = 13000
        LightState s = new LightState(true);
        s.setSat(255);
        s.setBri(100);
        s.setHue(13000);
        s.setAlert(AlertMode.NONE);
        s.setEffect(LightEffect.NONE);
        setStateToGroup(s, group);
    }

    /* (non-Javadoc)
	 * @see com.orange.basedrivers.hue.HueBrigdeDevice#setNight(int)
     */
    public void setNight(final int group) throws HueException {
        // specify state of the light for Meal mode
        LightState s = new LightState(false);
        s.setAlert(AlertMode.NONE);
        s.setEffect(LightEffect.NONE);
        setStateToGroup(s, group);
    }

    /* (non-Javadoc)
	 * @see com.orange.basedrivers.hue.HueBrigdeDevice#setHomeCinema(int)
     */
    public void setHomeCinema(final int group) throws HueException {
        // specify state of the light for Meal mode
        // red color => hue = 63000
        LightState s = new LightState(true);
        s.setBri(10);
        s.setSat(255);
        s.setHue(63000);
        s.setEffect(LightEffect.NONE);
        s.setAlert(AlertMode.NONE);
        setStateToGroup(s, group);
    }

    /* (non-Javadoc)
	 * @see com.orange.basedrivers.hue.HueBrigdeDevice#setParty(int)
     */
    public void setParty(final int group) throws HueException {
        // specify state of the light for Meal mode
        // light effect = colorloop
        LightState s = new LightState(true);
        s.setBri(50);
        s.setSat(255);
        s.setEffect(LightEffect.COLORLOOP);
        s.setAlert(AlertMode.NONE);
        setStateToGroup(s, group);
    }

    /* (non-Javadoc)
	 * @see com.orange.basedrivers.hue.HueBrigdeDevice#setOnOff(int, boolean)
     */
    public void setOnOff(final int group, final boolean on) throws HueException {
        // specify state of the light for Meal mode
        // white color => sat = 0
        LightState s = new LightState(on);
        if (on) {
            s.setBri(255);
            s.setSat(0);
        }
        s.setEffect(LightEffect.NONE);
        s.setAlert(AlertMode.NONE);
        setStateToGroup(s, group);
    }

    // ====================================================================================
    private void setStateToGroup(final LightState state, final int group) throws HueException {
        if (!controllerAvailable) {
            request = new PendingStateChangeRequest(group, state);
            return;
        }
        try {
            // turn all off before activating the new mode
            LightState offState = new LightState(false);
            offState.setEffect(LightEffect.NONE);
            offState.setAlert(AlertMode.NONE);
            LightGroup all = controller.getGroup(Group.getGroup(Group.ALL));
            // set group state
            if (all != null) {
                all.setState(offState);
            }
            // get group by name
            LightGroup lg = controller.getGroup(Group.getGroup(group));
            // set group state
            if (lg != null) {
                lg.setState(state);
            }
            request = null;
        } catch (UnknownHueGatewayException e) {
            request = new PendingStateChangeRequest(group, state);
        }
    }

    public String getLocation() {
        return location;
    }
    
    public boolean isBridgeConnected(){
        return controllerAvailable;
    } 
    
    public void stopPairingProcess(){
        controller.setPairWithBridge(false);
    }
    
    public String getUserName() {
    	return userName;
    }
}
