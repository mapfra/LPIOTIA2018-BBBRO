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
package org.eclipse.om2m.datamapping.jaxb;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.datamapping.service.DataMapperService;
/**
 * 
 *
 */
public class Mapper implements DataMapperService {

	/** XML Mapper logger */
	private static Log LOGGER = LogFactory.getLog(Mapper.class);
	/** JAXB Context, entry point to the JAXB API */
	private static JAXBContext context;
	/** Resource package name used for JAXBContext instantiation */
	private String resourcePackage = "org.eclipse.om2m.commons.resource";
	private String mediaType;

	/**
	 * Private constructor that will create the JAXB context.
	 */
	public Mapper(String mediaType) {
		this.mediaType=mediaType;
		try {
			if(context==null){
				context = JAXBContext.newInstance(resourcePackage);
			}
		} catch (JAXBException e) {
			LOGGER.error("Create JAXBContext error", e);
		}
	}

	/**
	 * Converts a resource Java object into resource XML representation.
	 * 
	 * @param object
	 *            - resource Java object
	 * @return resource XML representation
	 */
	@Override
	public String objToString(Object obj) {
		try {
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			OutputStream outputStream = new ByteArrayOutputStream();
			marshaller.marshal(obj, outputStream);
			return outputStream.toString();
		} catch (JAXBException e) {
			LOGGER.error("JAXB marshalling error!", e);
		}
		return null;
	}

	/**
	 * Converts a resource XML representation data into resource Java object.
	 * 
	 * @param representation
	 *            - resource XML representation
	 * @return resource Java object
	 */
	@Override
	public Object stringToObj(String representation) {
		StringReader stringReader = new StringReader(representation);
		try {
			Unmarshaller unmarshaller = context.createUnmarshaller();
			return unmarshaller.unmarshal(stringReader);
		} catch (JAXBException e) {
			LOGGER.error("JAXB unmarshalling error!", e);
		}
		return null;
	}

	@Override
	public String getServiceDataType() {
		return mediaType;
	}

}
