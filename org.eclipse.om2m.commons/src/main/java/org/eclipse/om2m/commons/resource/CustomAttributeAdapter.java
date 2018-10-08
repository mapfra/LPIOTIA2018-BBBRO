/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.commons.resource;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class CustomAttributeAdapter extends XmlAdapter<Element, CustomAttribute> {

	private static Log LOGGER = LogFactory.getLog(CustomAttributeAdapter.class);

	private DocumentBuilder documentBuilder;

	@Override
	public CustomAttribute unmarshal(Element elt) throws Exception {
		CustomAttribute ca = new CustomAttribute();
		Attr attr = elt.getAttributeNode("val");
		String value = (attr == null) ? elt.getTextContent() // xml case
			: elt.getAttribute("val"); // json case
		ca.setShortName(elt.getTagName());
		ca.setValue(value);
//		try {
//			ca.setType(elt.getAttributeNode("type").getValue());
//		} catch (Exception e) {
//			LOGGER.info("error getting type " + elt, e);
//		}
		LOGGER.info("unmarshal: " + print(elt) + " -> " + ca);
		return ca;
	}

	@Override
	public Element marshal(CustomAttribute ca) throws Exception {
		try {
			Document document = getDocumentBuilder().newDocument();
			Element elt = document.createElement(ca.getShortName());
			String value = ca.getValue();
//			elt.setAttribute("type", ca.getType());
			elt.setTextContent((value != null ? value : ""));
			return elt;
		} catch (Throwable t) {
			LOGGER.info("error marshalling " + ca, t);
			return null;
		}
	}

	private DocumentBuilder getDocumentBuilder() throws Exception {
		// Lazy load the DocumentBuilder as it is not used for unmarshalling.
		if (null == documentBuilder) {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			documentBuilder = dbf.newDocumentBuilder();
		}
		return documentBuilder;
	}
	
	private String print(Element elt) {
		String ret = "<Elt " + elt.getTagName();
		NamedNodeMap map = elt.getAttributes();
		for (int i = 0; i < map.getLength(); i++) {
			Node n = map.item(i);
			ret += " " + ((n instanceof Element) ? print((Element)n) : n);
		}
		return ret + ">" + elt.getTextContent() + "</Elt>";
	}

}
