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
*    OSKO Tomasz <tomasz.osko@orange.com>
*    RATUSZEK Przemyslaw <przemyslaw.ratuszek@orange.com>
*******************************************************************************/
package org.eclipse.om2m.hue.impl;

import java.io.StringReader;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.hue.api.HueDevice;
import org.eclipse.om2m.hue.impl.controller.HueConstants;
import org.osgi.service.device.Constants;
import org.osgi.service.upnp.UPnPDevice;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Implementation of the {@link HueDevice}. Parent class for both
 * ({@link HueBridgeDeviceImpl}) and Light ({@link HueLightDeviceImpl}) Hue
 * devices.
 */
abstract public class HueDeviceImpl implements HueDevice, HueConstants {
	
	/**
	 * Logger
	 */
    private static Log Logger = LogFactory.getLog(HueDeviceImpl.class);

	/**
	 * OSGi service properties
	 */
	@SuppressWarnings("rawtypes")
	protected Dictionary properties;

	/**
	 * Xml description of Hue gateway
	 */
	private String xmlDescription;

	// ----------------------------------------------------------------------

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HueDeviceImpl(final String xml_description) {
		this.xmlDescription = xml_description;
		this.properties = new Hashtable();

		/* DEVICE CATEGORY */
		properties.put(Constants.DEVICE_CATEGORY, new String[] { HUE_CATEGORY });

		// get a parser for device description
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			Document document = builder.parse(new InputSource(new StringReader(xml_description)));

			NodeList nodeList = document.getChildNodes();
			// node list contains the child node of the "root" tag

			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					if (node.getNodeName().equals(ROOT)) {
						// root node
						// root contains the following nodes
						// - specVersion
						// - URLBase
						// - device

						NodeList rootNodeList = node.getChildNodes();
						for (int j = 0; j < rootNodeList.getLength(); j++) {
							Node rootChildNode = rootNodeList.item(j);
							
							if (rootChildNode.getNodeName().equals(HUE_URL_BASE)) {
								properties.put(UPnPDevice.PRESENTATION_URL, rootChildNode.getTextContent()); // UPnP
							} else if (rootChildNode.getNodeName().equals(DEVICE)) {
								NodeList deviceChildNodeList = rootChildNode.getChildNodes();
								// device children :
								// - deviceType
								// - friendlyName
								// - manufacturer
								// - manufacturerURL
								// - modelDescription
								// - modelName
								// - modelNumber
								// - modelURL
								// - serialNumber
								// - UDN
								// - presentationURL
								for (int k = 0; k < deviceChildNodeList.getLength(); k++) {
									Node deviceChildNode = deviceChildNodeList.item(k);
									String tag = deviceChildNode.getNodeName();
									String value = deviceChildNode.getTextContent();
									
									if (tag.equalsIgnoreCase(HUE_FRIENDLY_NAME)) {
										properties.put(UPnPDevice.FRIENDLY_NAME, value); // UPnP
										properties.put(OSGI_DEVICE_FRIENDLY_NAME, value); // OTB
									} else if (tag.equalsIgnoreCase(HUE_MANUFACTURER)) {
										properties.put(UPnPDevice.MANUFACTURER, value); // UPnP
										properties.put(OSGI_DEVICE_MANUFACTURER, value); // OTB
									} else if (tag.equalsIgnoreCase(HUE_DEVICE_TYPE)) {
										properties.put(UPnPDevice.TYPE, value); // UPnP
									} else if (tag.equalsIgnoreCase("UDN")) {
										properties.put(UPnPDevice.UDN, value); // UPnP
										properties.put(org.osgi.framework.Constants.SERVICE_PID, value); // OTB
									} else if (tag.equalsIgnoreCase(HUE_MODEL_DESCRIPTION)) {
										properties.put(UPnPDevice.MODEL_DESCRIPTION, value); // UPnP
										properties.put(Constants.DEVICE_DESCRIPTION, value); // OTB
									} else if (tag.equalsIgnoreCase(HUE_MODEL_NUMBER)) {
										properties.put(UPnPDevice.MODEL_NUMBER, value); // UPnP
									} else if (tag.equalsIgnoreCase(HUE_MODEL_NAME)) {
										properties.put(UPnPDevice.MODEL_NAME, value); // UPnP
										properties.put(OSGI_DEVICE_PRODUCT_CLASS, value); // OTB
									} else if (tag.equalsIgnoreCase(HUE_MODEL_URL)) {
										properties.put(UPnPDevice.MODEL_URL, value); // UPnP
									} else if (tag.equalsIgnoreCase(HUE_SERIAL_NUMBER)) {
										properties.put(UPnPDevice.SERIAL_NUMBER, value); // UPnP
										properties.put(Constants.DEVICE_SERIAL, value); // OTB
									} else if (tag.equalsIgnoreCase(HUE_MANUFACTURER_URL)) {
										properties.put(UPnPDevice.MANUFACTURER_URL, value); // UPnP
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			Logger.warn("Unknown Error", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.orange.basedrivers.hue.HueDevice#getDescriptions()
	 */
	@SuppressWarnings("rawtypes")
	public Dictionary getProperties() {
		return properties;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.orange.basedrivers.hue.HueDevice#getXmlDescription()
	 */
	public String getXmlDescription() {
		return xmlDescription;
	}

}
