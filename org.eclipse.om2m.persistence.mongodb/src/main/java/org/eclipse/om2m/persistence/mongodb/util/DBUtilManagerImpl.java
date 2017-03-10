package org.eclipse.om2m.persistence.mongodb.util;

import org.eclipse.om2m.persistence.service.util.AnnouncedResourceUtil;
import org.eclipse.om2m.persistence.service.util.ComplexFindUtil;
import org.eclipse.om2m.persistence.service.util.DBUtilManager;

public class DBUtilManagerImpl implements DBUtilManager {

	@Override
	public ComplexFindUtil getComplexFindUtil() {
		return new ComplexFindUtilImpl();
	}

	@Override
	public AnnouncedResourceUtil getAnnouncedResourceUtil() {
		return new AnnouncedResourceUtilImpl();
	}

}
