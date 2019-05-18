package org.eclipse.om2m.commons.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.constants.ShortName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "URI", "dasRequest" })
public class DasInfo {

	@XmlElement(required = true, name=ShortName.URI)
	@XmlSchemaType(name = "anyURI")
	private String URI;

	@XmlElement(nillable = true, type = DynAuthDasRequest.class, name=ShortName.DAS_REQUEST)
	private DynAuthDasRequest dasRequest;
	// private DynAuthJWT securedDasRequest;

	public String getURI() {
		return URI;
	}

	public void setURI(String uRI) {
		URI = uRI;
	}

	public DynAuthDasRequest getDasRequest() {
		return dasRequest;
	}

	public void setDasRequest(DynAuthDasRequest dasRequest) {
		this.dasRequest = dasRequest;
	}
}
