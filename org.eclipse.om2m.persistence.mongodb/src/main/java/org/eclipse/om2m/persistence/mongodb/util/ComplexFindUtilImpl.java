package org.eclipse.om2m.persistence.mongodb.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.om2m.commons.entities.UriMapperEntity;
import org.eclipse.om2m.commons.resource.FilterCriteria;
import org.eclipse.om2m.persistence.service.util.ComplexFindUtil;

public class ComplexFindUtilImpl implements ComplexFindUtil {

	private static final ComplexFindUtilImpl INSTANCE = new ComplexFindUtilImpl();
	
	private ComplexFindUtilImpl() {
	}
	
	
	public static ComplexFindUtilImpl getInstance() {
		return INSTANCE;
	}

	@Override
	public List<UriMapperEntity> getChildUrisDis(String rootUri, FilterCriteria filter) {
		return new ArrayList<>();
	}

}
