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
