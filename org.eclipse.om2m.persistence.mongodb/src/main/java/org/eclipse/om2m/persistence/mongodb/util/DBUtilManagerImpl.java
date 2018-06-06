/*******************************************************************************
 * Copyright (c) 2014 - 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    BAREAU Cyrille <cyrille.bareau@orange.com>, 
 *    BONNARDEL Gregory <gbonnardel.ext@orange.com>, 
 *******************************************************************************/
package org.eclipse.om2m.persistence.mongodb.util;

import org.eclipse.om2m.persistence.service.util.AnnouncedResourceUtil;
import org.eclipse.om2m.persistence.service.util.ComplexFindUtil;
import org.eclipse.om2m.persistence.service.util.DBUtilManager;
import org.eclipse.om2m.persistence.service.util.DynamicAuthorizationConsultationUtil;

public class DBUtilManagerImpl implements DBUtilManager {

	@Override
	public ComplexFindUtil getComplexFindUtil() {
		return ComplexFindUtilImpl.getInstance();
	}

	@Override
	public AnnouncedResourceUtil getAnnouncedResourceUtil() {
		return new AnnouncedResourceUtilImpl();
	}

	@Override
	public DynamicAuthorizationConsultationUtil getDynamicAuthorizationConsultationUtil() {
		return DynamicAuthorizationConsultationUtilImpl.getInstance();
	}

}
