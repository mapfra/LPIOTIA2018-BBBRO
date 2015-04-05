package org.eclipse.om2m.commons.obix.io;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Mapper class to convert oBIX XML representation to java object or vice-versa
 * 
 * @author Francois Aissaoui
 *
 */
public class ObixMapper {
	/** Logger */
	private static final Log LOGGER = LogFactory.getLog(ObixMapper.class);
	/** Current instance of the mapper */
	private static ObixMapper obixMapper = new ObixMapper();
	/** JAXB Context of oBIX objects */
	private JAXBContext context;
	
	/** Package containing oBIX objects */
	private static final String OBIX_PACKAGE = "org.eclipse.om2m.commons.obix";

	private ObixMapper() {
		try {
			context = JAXBContext.newInstance(OBIX_PACKAGE);
		} catch (JAXBException e) {
			LOGGER.error("Error creating the JAXB context for Obix objects", e);
		}
	}

	protected JAXBContext getJAXBContext() {
		return context;
	}

	/**
	 * Return the current instance of the oBIX mapper
	 * 
	 * @return current instance of ObixMapper
	 */
	public static ObixMapper getInstance() {
		return obixMapper;
	}

}
