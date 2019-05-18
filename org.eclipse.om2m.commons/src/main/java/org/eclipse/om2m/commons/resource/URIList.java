package org.eclipse.om2m.commons.resource;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.constants.ShortName;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = ShortName.URI_LIST, namespace="http://www.onem2m.org/xml/protocols")
@XmlType(name="uril")
public class URIList {

	protected List<String> listOfUri;
	
	/**
	 * @return the listOfUri
	 */
	public List<String> getListOfUri() {
		if(listOfUri == null){
			listOfUri = new ArrayList<String>();
		}
		return listOfUri;
	}

	/**
	 * @param listOfUri the listOfUri to set
	 */
	public void setListOfUri(List<String> listOfUri) {
		this.listOfUri = listOfUri;
	}
	
	
	
}
