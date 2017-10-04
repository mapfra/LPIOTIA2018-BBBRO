package org.eclipse.om2m.commons.resource;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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
