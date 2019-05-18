package org.eclipse.om2m.commons.resource;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.constants.ShortName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = ShortName.DYNAMIC_AUTHORIZATION_TOKEN_REQ_INFO, namespace = "http://www.onem2m.org/xml/protocols", propOrder = {
		"dasInfo" })
@XmlRootElement(name = ShortName.DYNAMIC_AUTHORIZATION_TOKEN_REQ_INFO, namespace = "http://www.onem2m.org/xml/protocols")
public class DynAuthTokenReqInfo {

	@XmlElements({
			@XmlElement(name = ShortName.DAS_INFO, type = DasInfo.class) })
	private List<DasInfo> dasInfo;

	public List<DasInfo> getDasInfo() {
		if (dasInfo == null) {
			dasInfo = new ArrayList<>();
		}
		return dasInfo;
	}

	public void setDasInfo(List<DasInfo> dasInfo) {
		this.dasInfo = dasInfo;
	}

}
