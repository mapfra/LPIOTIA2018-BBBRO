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
package org.eclipse.om2m.hue.impl.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.hue.api.Group;
import org.eclipse.om2m.hue.api.types.HueException;
import org.eclipse.om2m.hue.api.types.UnknownHueGatewayException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Represents a Hue gateway that allows to command the Hue lights {@link Light}
 * and light groups {@link LightGroup}.
 */
public class Controller implements HueConstants {

    /**
     * Logger
     */
    private static Log Logger = LogFactory.getLog(Controller.class);

    /**
     * IP address of Hue gateway
     */
    private String IP;

    /**
     * authorized userName previously peered with Hue gateway
     */
    private String userName;

    /**
     * list of Hue groups contains {@link LightGroup} objects
     */
    private List<LightGroup> groups;

    /**
     * list of Hue lights contains {@link Light} objects
     */
    private List<Light> lights;
    
    /**
     * indicates if pairing should be continued
     */
    private boolean pairWithBridge = true;

    // ====================================================================================
    /**
     * @param IP Hue gateway IP address
     * @param usr Hue gateway authorized user
     * @throws HueException signaling that group or light discovery failed
     * @throws UnknownHueGatewayException
     */
    public Controller(final String IP, final String usr) 
    		throws HueException, UnknownHueGatewayException {
        this.IP = IP;
        this.userName = usr;
        this.lights = new ArrayList<Light>();
        this.groups = new ArrayList<LightGroup>();
        
        if (userName == null || userName.equals("")) {
            // Pair and create a new user before connecting
            pairWithBridge();
        }
        // Connect to the bridge and discover resources
        discoverBridgeResources();             
    }

    // ====================================================================================
    /**
     * Pairs with the Hue Bridge and creates a new user (sets this.userName field)
     * 
     * @throws com.orange.service.hue.types.HueException
     * @throws com.orange.service.hue.types.UnknownHueGatewayException
     */
    private void pairWithBridge() throws UnknownHueGatewayException {
        while (pairWithBridge && !Thread.currentThread().isInterrupted()) {
            Logger.warn("Press the button on the Hue bridge. Next pairing attempt in 10 seconds...");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Logger.warn("Pairing interrupted", e);
            }
            try {
                JSONParser parser = new JSONParser();
                String resp = sendPairRequest("", HUE_PAIRING_MESSAGE);
                if (!resp.substring(3, 8).equals(ERROR)) {
                    JSONArray response = (JSONArray) parser.parse(resp);
                    this.userName = String.valueOf(((JSONObject) ((JSONObject) response.get(0)).get("success")).get("username"));
                    pairWithBridge = false;
                    Logger.info("Bridge paired for user: " + userName);
                    return;
                }
                Logger.warn("Pairing failed, retrying...");
            } catch (HueException e) {
                Logger.warn("Pairing failed, retrying...");
            } catch (Exception e) {
                Logger.error("Unexpected communication error with Hue Bridge", e);
                throw new UnknownHueGatewayException(e.getMessage());
            }
        }
    }
    
    /**
     * Discovers lights and groups connected to the bridge
     * 
     * @throws com.orange.service.hue.types.HueException
     * @throws com.orange.service.hue.types.UnknownHueGatewayException
     */
    private void discoverBridgeResources() throws HueException, UnknownHueGatewayException{
        groupDiscovery();
        lightDiscovery();
    }
    
    /**
     * gets existing groups on hue gateway
     *
     * @throws HueException
     * @throws UnknownHueGatewayException
     */
    @SuppressWarnings("rawtypes")
	private void groupDiscovery() throws HueException, UnknownHueGatewayException {
        // make GET request to retrieve list of groups with id and name
        String resp = sendRequest(GROUPS);
        if (resp.equals(EMPTY)) {
            return;
        }
        if (resp.substring(3, 8).equals(ERROR)) {
            throw new HueException("ERROR while getting Hue groups! Check if username : "
                    + this.userName + " is correct" + "\n\t" + resp);
        }

        try {
            // define the expected JSON format with ContainerFactory
            // we expect something like {"2":{"name":"Office"}, "3":{"name":"kitchen"}}
            // and we need the id (2 and 3) and the name (Office and kitchen)
            // so "2":{"name":"Office"} will represent a Map (<key,value> pair)
            // the entire JSON expected format is a List which contains Map objects
            // from a Map object we extract the id which is the key
            // and the name which is contained in the value
            ContainerFactory containerFactory = new ContainerFactory() {
                public List creatArrayContainer() {
                    return new ArrayList();
                }

                public Map createObjectContainer() {
                    return new HashMap();
                }
            };

            // get entire JSON object according to the indicated format
            Map objs = (Map) new JSONParser().parse(resp, containerFactory);
            // iterator for the Map objects from the List
            for (Iterator it = objs.entrySet().iterator(); it.hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();
                String groupId = entry.getKey().toString();
                String groupName = entry.getValue().toString();// we get {name=groupName}
                // we need only groupName
                groupName = groupName.substring(6, groupName.indexOf("}"));
                groups.add(new LightGroup(IP, userName, groupId, groupName));
            }
        } catch (ParseException e) {
            throw new HueException("ERROR while getting Hue groups! Error while parsing response : "
                    + "\n\t" + resp);
        }

        // for each discovered group, discover the lights from the group
        for (Iterator it = groups.iterator(); it.hasNext();) {
            LightGroup group = (LightGroup) it.next();
            // make GET request to retrieve list of lights from the group
            resp = sendRequest(GROUPS + "/" + group.getId());
            if (resp.equals(EMPTY)) {
                continue;
            }
            if (resp.substring(3, 8).equals(ERROR)) {
                throw new HueException("ERROR! could not get lights for group : " + group.getName()
                        + "\n\t" + resp);
            }

            try {
                JSONObject obj = (JSONObject) new JSONParser().parse(resp);
                // the attribute that contains the list of the group's lights is "lights" : ["1","2","3".....]
                JSONArray jsonLights = (JSONArray) obj.get(LIGHTS);
                // for each light in the lights list
                for (Iterator it2 = jsonLights.iterator(); it2.hasNext();) {
                    group.addLight(new Light(IP, userName, it2.next().toString()));
                }
            } catch (ParseException e) {
                throw new HueException("ERROR while getting Hue lights for groups! Error while parsing response : "
                        + "\n\t" + resp);
            }
        }
    }

    /**
     * gets lights existent on hue gateway
     *
     * @throws HueException
     * @throws UnknownHueGatewayException
     */
    @SuppressWarnings("rawtypes")
	private void lightDiscovery() throws HueException, UnknownHueGatewayException {
        // make GET request to retrieve list of lights with id and name
        String resp = sendRequest(LIGHTS);
        if (resp.equals("") || resp.equals(EMPTY)) {
            return;
        }
        if (resp.substring(3, 8).equals(ERROR)) {
            throw new HueException("ERROR wile getting lights! Check if username : "
                    + this.userName + " is correct" + "\n\t" + resp);
        }
        try {
            // define the expected JSON format with ContainerFactory
            // we expect something like {"2":{"name":"Office"}, "3":{"name":"kitchen"}}
            // and we need the id (2 and 3) and the name (Office and kitchen)
            // so "2":{"name":"Office"} will represent a Map (<key,value> pair)
            // the entire JSON expected format is a List which contains Map objects
            // from a Map object we extract the id which is the key
            // and the name which is contained in the value
            ContainerFactory containerFactory = new ContainerFactory() {
                public List creatArrayContainer() {
                    return new ArrayList();
                }

                public Map createObjectContainer() {
                    return new HashMap();
                }
            };
            // get entire JSON object according to the indicated format
            Map obj = (Map) new JSONParser().parse(resp, containerFactory);
            // iterator for the Map objects from the List
            for (Iterator it = obj.keySet().iterator(); it.hasNext();) {
                String lightId = it.next().toString();
                // we need only lightId				
                lights.add(new Light(IP, userName, lightId));
            }

            // add discovered lights to group "all", 
            // a default group of Hue light gateway
            int all = Group.ALL;
            LightGroup lg = new LightGroup(IP, userName, "" + all, Group.getGroup(all));
            lg.setLightList(this.lights);
            this.groups.add(lg);
        } catch (ParseException e) {
            throw new HueException("ERROR while getting Hue lights for groups! Error while parsing response : "
                    + "\n\t" + resp);
        }
    }

    /**
     * @return all lights {@link List<{@link Light}>} of hue controller
     * @throws UnknownHueGatewayException
     * @throws HueException
     */
    public List<Light> getLights() throws HueException, UnknownHueGatewayException {;
        lights.clear();
        // refresh light discovery
        lightDiscovery();
        return lights;
    }

    /**
     * @param lightName
     * @return light {@link Light} of hue controller, null if not existent
     * @throws HueException
     */
    public Light getLightByName(final String lightName) throws HueException {
        for (Light l : lights) {
            if (l.getName().equals(lightName)) {
                return l;
            }
        }
        throw new HueException("Not found Light element for name : " + lightName);
    }

    /**
     * @param lightId
     * @return light {@link Light} of hue controller, null if not existent
     * @throws HueException
     */
    public Light getLightById(final String lightId) throws HueException {
        for (Light l : lights) {
            if (l.getId().equals(lightId)) {
                return l;
            }
        }
        throw new HueException("Not found Light element for id : " + lightId);
    }

    /**
     * @return all groups {@link List<{@link LightGroup}>} of hue controller
     */
    public List<LightGroup> getGroups() {
        return groups;
    }

    /**
     * @param groupName
     * @return all lights {@link List<{@link Light}>} from groupName,
     */
    public List<Light> getLights(final String groupName) {
        try {
            return getGroup(groupName).getLights();
        } catch (HueException e) {
            return new ArrayList<Light>();
        }
    }

    /**
     * @param groupName
     * @return group {@link LightGroup} of hue controller, null if not existent
     * @throws HueException
     */
    public LightGroup getGroup(final String groupName) throws HueException {
        for (LightGroup g : groups) {
            if (g.getName().equals(groupName)) {
                return g;
            }
        }
        throw new HueException("Not found LightGroup element for name : " + groupName);
    }

    /**
     * @return IP address of Hue gateway
     */
    public String getIP() {
        return IP;
    }

    /**
     * @param iP the IP address of Hue gateway to set not yet implemented
     *//*
	public void setIP(String iP) {
		IP = iP;
		// TODO rediscover groups and lights
		// revwrite lists
	}*/

    /**
     * @return the userName peered with Hue gateway
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName peered with Hue gateway to set
     *//*
	public void setUserName(String userName) {
		this.userName = userName;
		// TODO rediscover groups and lights
		// revwrite lists
	}*/
    
    public void setPairWithBridge(boolean pairWithBridge) {
        this.pairWithBridge = pairWithBridge;
    }

    private final String sendRequest(final String req) throws HueException, UnknownHueGatewayException {
        Logger.debug("Sending request to: " + IP + "/api/" + userName + "/" + req);
        return Utils.sendGetRequest(IP + "/api/" + userName, req);
    }
    
    private final String sendPairRequest(final String req, final String body) throws HueException, UnknownHueGatewayException {
        Logger.debug("Sending request to: " + IP + "/api/ with body: " + body);
        return Utils.sendPostRequest(IP + "/api", req, body);
    }

}
