package org.eclipse.om2m.commons.obix.io;

import java.io.StringReader;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.obix.Obj;

/**
 * Obix decoder to obtain the javaobject corresponding to an oBIX XML
 * representation.
 * 
 * @author Francois Aissaoui
 *
 */
public class ObixDecoder {

	private static final Log LOG = LogFactory.getLog(ObixDecoder.class);
	
	/**
	 * Method not allowed
	 */
	private ObixDecoder(){
		// Not allowed
	}

	/**
	 * Get the java object from the oBIX XML representation
	 * 
	 * @param representation
	 *            Sting from the oBIX object
	 * @return the oBIX object decoded
	 */
	public static Obj fromString(String representation) {
		StringReader stringReader = new StringReader(representation);
		try {
			Unmarshaller unmarshaller = ObixMapper.getInstance()
					.getJAXBContext().createUnmarshaller();
			return (Obj) unmarshaller.unmarshal(stringReader);
		} catch (JAXBException e) {
			LOG.error("Error in decoding oBIX object", e);
		}
		return null;
	}
}
