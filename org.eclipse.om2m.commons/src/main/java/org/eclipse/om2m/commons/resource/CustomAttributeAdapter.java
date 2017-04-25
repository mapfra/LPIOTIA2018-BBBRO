/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.commons.resource;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CustomAttributeAdapter extends XmlAdapter<Element, CustomAttribute> {

	private DocumentBuilder documentBuilder;

	@Override
	public CustomAttribute unmarshal(Element v) throws Exception {
		CustomAttribute customAttribute = new CustomAttribute();

		String value = null;
		Attr att = v.getAttributeNode("value");
		if (att != null) {
			// json case
			value = v.getAttribute("value");
		} else {
			// xml case
			value = v.getTextContent();
		}
		customAttribute.setCustomAttributeName(v.getTagName());
		customAttribute.setCustomAttributeValue(value);
		customAttribute.setCustomAttributeType(v.getAttribute("type"));
		

		return customAttribute;
	}

	@Override
	public Element marshal(CustomAttribute v) throws Exception {
		
		if (null == v) {
			return null;
		}

		Object value = v.getCustomAttributeValue();

		Element e = null;
		try {
			Document document = getDocumentBuilder().newDocument();
			e = document.createElement(v.getCustomAttributeName());
			e.setTextContent((value != null ? value.toString() : ""));
			e.setAttribute("type", v.getCustomAttributeType());
			
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		return e;
	}

	private DocumentBuilder getDocumentBuilder() throws Exception {
		// Lazy load the DocumentBuilder as it is not used for unmarshalling.
		if (null == documentBuilder) {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			documentBuilder = dbf.newDocumentBuilder();
		}
		return documentBuilder;
	}


}
