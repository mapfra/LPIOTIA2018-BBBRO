/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.core.flexcontainer;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.exceptions.BadRequestException;
import org.xml.sax.SAXException;

/**
 * This class is used to validate FlexContainer XML payload based on XSD files.
 * XSD files location is set through org.eclipse.om2m.flexcontainer.xsd.folder
 * Java property. The value is a string and must be a folder. It could be either
 * an absolute path (from root) or a path related to the execution folder.
 * 
 * @author Gregory BONNARDEL <gbonnardel.ext@orange.com>
 *         <gregory.bonnardel@mythalesgroup.com>
 *
 */
public class FlexContainerXMLValidator {

	/** Logger */
	private static final Log LOGGER = LogFactory.getLog(FlexContainerXMLValidator.class);

	/** FLEXCONTAINER XSD FOLDER PROPERTY NAME */
	private static final String FLEXCONTAINER_XSD_FOLDER_PROPERTY_NAME = "org.eclipse.om2m.flexcontainer.xsd.folder";


	/**
	 * Validate XML payload based on the ContainerDefinition value. This method
	 * loads the XSD file related to the ContainerDefinition value and validates
	 * the XML payload using this XSD file.
	 * 
	 * @param request
	 *            request contains the XML payload
	 * @param flexContainer
	 *            unmarshalled flexContainer object
	 * @throws BadRequestException
	 *             in case of the validation failed.
	 */
	public static void validateXMLPayload(String xmlPayload, String containerDefinition) throws BadRequestException {
		// validate the XML payload
		// need to perform a post validation due to the fact we need
		// to know the value of the containerDefinition.

		/** FLEXCONTAINER XSD FOLDER PROPERTY VALUE */
		String FLEXCONTAINER_XSD_FOLDER_PROPERTY_VALUE = System.getProperty(FLEXCONTAINER_XSD_FOLDER_PROPERTY_NAME, null);

		// ensure there is a / at the end
		if (FLEXCONTAINER_XSD_FOLDER_PROPERTY_VALUE != null) {
			if (!FLEXCONTAINER_XSD_FOLDER_PROPERTY_VALUE.endsWith("/")) {
				FLEXCONTAINER_XSD_FOLDER_PROPERTY_VALUE += "/";
			}
		}

		if (FLEXCONTAINER_XSD_FOLDER_PROPERTY_VALUE == null) {
			// nothing to do as there is no XSD file.
			LOGGER.warn("No XSD folder set.");
			return;
		}

		File xsdFile = new File(FLEXCONTAINER_XSD_FOLDER_PROPERTY_VALUE + containerDefinition.toLowerCase() + ".xsd");
		if (xsdFile.exists()) {
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			// XSD schema found!
			try {
				Schema schema = schemaFactory.newSchema(xsdFile);
				Validator validator = schema.newValidator();
				StreamSource ss = new StreamSource(new StringReader(xmlPayload));
				validator.validate(ss);
			} catch (SAXException e) {
				throw new BadRequestException("Unable to validate XML payload: " + e.getMessage());
			} catch (IOException e) {
				throw new BadRequestException("Unable to validate XML payload: " + e.getMessage());
			} catch (IllegalArgumentException e) {
				throw new BadRequestException("Unable to validate XML payload: " + e.getMessage());
			} catch (NullPointerException e) {
				throw new BadRequestException("Unable to validate XML payload: " + e.getMessage());
			}

		} else {
			// XSD not found
			throw new BadRequestException("No XSD schema found for " + containerDefinition);
		}
	}

}
