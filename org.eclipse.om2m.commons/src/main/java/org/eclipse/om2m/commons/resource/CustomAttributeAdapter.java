/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.commons.resource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class CustomAttributeAdapter extends XmlAdapter<Element, CustomAttribute> {

	private DocumentBuilder documentBuilder;
	private JAXBContext jaxbContext;

	@Override
	public CustomAttribute unmarshal(Element v) throws Exception {
		CustomAttribute customAttribute = new CustomAttribute();

		customAttribute.setCustomAttributeName(v.getTagName());
		customAttribute.setCustomAttributeValue(v.getTextContent());
		customAttribute.setCustomAttributeType(v.getAttribute("type"));
		

		return customAttribute;
	}

	@Override
	public Element marshal(CustomAttribute v) throws Exception {
		
		if (null == v) {
			return null;
		}

		// 1. Build the JAXBElement to wrap the instance of Parameter.
		QName rootElement = new QName(v.getCustomAttributeName());
		Object value = v.getCustomAttributeValue();
		Class<?> type = null;
		if (value != null) {
			type = value.getClass();
		} else {
			type = String.class;
		}
		
		JAXBElement jaxbElement = new JAXBElement(rootElement, type, value);

		Element e = null;
		try {
			Document document = getDocumentBuilder().newDocument();
		Marshaller marshaller = getJAXBContext(type).createMarshaller();
		marshaller.marshal(jaxbElement, document);
		/*Element*/ e = document.getDocumentElement();
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

	private JAXBContext getJAXBContext(Class<?> type) throws Exception {
		if (null == jaxbContext) {
			// A JAXBContext was not set, so create a new one based on the type.
//			return JAXBContext.newInstance(type);
			jaxbContext = JAXBContext.newInstance("org.eclipse.om2m.commons.resource", this.getClass().getClassLoader());
		}
		return jaxbContext;
	}

}
