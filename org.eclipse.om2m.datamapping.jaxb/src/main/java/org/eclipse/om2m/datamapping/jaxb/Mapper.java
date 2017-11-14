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
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Unmarshaller.Listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.URIList;
import org.eclipse.om2m.datamapping.service.DataMapperService;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;

/**
 * Datamapper (JAXB) implementing DataMapper service
 */
public class Mapper implements DataMapperService {

	/** XML Mapper logger */
	private static Log LOGGER = LogFactory.getLog(Mapper.class);
	/** JAXB Context, entry point to the JAXB API */
	private JAXBContext context;
	/** Resource package name used for JAXBContext instantiation */
	// org.eclipse.om2m.commons.resource:
	private String resourcePackage = "org.eclipse.om2m.commons.resource:org.eclipse.om2m.commons.resource.flexcontainerspec";
	private String mediaType;

	/**
	 * Private constructor that will create the JAXB context.
	 */
	public Mapper(String mediaType) {
		this.mediaType=mediaType;
		try {
			if(context==null){
				if(mediaType.equals(MimeMediaType.JSON)){
					// JSON
					ClassLoader classLoader = Mapper.class.getClassLoader(); 
					InputStream iStreamJsonBinding = classLoader.getResourceAsStream("json-binding.json");
					InputStream iStreamJsonBindingFlexcontainer = classLoader.getResourceAsStream("json-binding-flexcontainer.json");
					List<Object> iStreamList = new ArrayList<>();
					iStreamList.add(iStreamJsonBinding);
					iStreamList.add(iStreamJsonBindingFlexcontainer);
					Map<String, Object> properties = new HashMap<String, Object>(); 
					properties.put("eclipselink-oxm-xml", iStreamList); 
					properties.put("eclipselink.media-type", "application/json");;
					context = JAXBContext.newInstance(resourcePackage, classLoader , properties);
				} else if (mediaType.equals(MimeMediaType.XML)) {
					// XML
					ClassLoader classLoader = Mapper.class.getClassLoader(); 
					InputStream iStream = classLoader.getResourceAsStream("xml-binding.xml"); 
					Map<String, Object> properties = new HashMap<String, Object>(); 
					properties.put(JAXBContextProperties.OXM_METADATA_SOURCE, iStream);
					context = JAXBContext.newInstance(resourcePackage, classLoader , properties);
				} else {
					// other
					context = JAXBContext.newInstance(resourcePackage, Mapper.class.getClassLoader());
				}
			}
		} catch (Throwable e) { 
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
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			marshaller.setProperty(MarshallerProperties.MEDIA_TYPE,mediaType);
			if (obj instanceof URIList) {
				marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, false);
				marshaller.setProperty(MarshallerProperties.JSON_MARSHAL_EMPTY_COLLECTIONS, true);
				marshaller.setProperty(MarshallerProperties.JSON_REDUCE_ANY_ARRAYS, false);
			} else {
				marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
				marshaller.setProperty(MarshallerProperties.JSON_MARSHAL_EMPTY_COLLECTIONS, false);
				marshaller.setProperty(MarshallerProperties.JSON_REDUCE_ANY_ARRAYS, true);
			}
			marshaller.setProperty(MarshallerProperties.JSON_VALUE_WRAPPER, "val");
			
			
			Map<String, String> namespaces = new HashMap<String, String>(); 
			namespaces.put("http://www.onem2m.org/xml/protocols/homedomain", "hd"); 
			namespaces.put("http://www.onem2m.org/xml/protocols", "m2m"); 
			marshaller.setProperty(MarshallerProperties.NAMESPACE_PREFIX_MAPPER, namespaces);
			marshaller.setProperty(MarshallerProperties.JSON_NAMESPACE_SEPARATOR, ':');
			marshaller.marshal(obj, outputStream);
			
			return outputStream.toString("UTF-8");
		} catch (JAXBException e) {
			LOGGER.error("JAXB marshalling error!", e);
		} catch (UnsupportedEncodingException e) {
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
		if(representation.isEmpty()){
			return null;
		}
		StringReader stringReader = new StringReader(representation);
		try {
			Unmarshaller unmarshaller = context.createUnmarshaller();
			unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, mediaType);
			if (representation.contains("m2m:uril")) {
				unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, true);
				unmarshaller.setProperty(UnmarshallerProperties.JSON_WRAPPER_AS_ARRAY_NAME , true);
			} else {
				unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, true);
				unmarshaller.setProperty(UnmarshallerProperties.JSON_WRAPPER_AS_ARRAY_NAME , false);
			}
			unmarshaller.setProperty(UnmarshallerProperties.JSON_VALUE_WRAPPER , "val");
			Map<String, String> namespaces = new HashMap<String, String>(); 
			namespaces.put("http://www.onem2m.org/xml/protocols/homedomain", "hd"); 
			namespaces.put("http://www.onem2m.org/xml/protocols", "m2m"); 
			unmarshaller.setProperty(MarshallerProperties.NAMESPACE_PREFIX_MAPPER, namespaces);
			unmarshaller.setProperty(MarshallerProperties.JSON_NAMESPACE_SEPARATOR, ':');
			
			unmarshaller.setListener(new Listener() {
				
				@Override
				public void afterUnmarshal(Object target, Object parent) {
					LOGGER.debug("afterUnmarshal (target=" + target + ", parent=" + parent + ")");
					super.afterUnmarshal(target, parent);
					
					if (target instanceof AbstractFlexContainer) {
						((AbstractFlexContainer) target).finalizeDeserialization();
					}
				}
			});
			
			Object unmarshaledObject = unmarshaller.unmarshal(stringReader);
			Object toBeReturned = null;
			toBeReturned = unmarshaledObject;

			return toBeReturned;
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
