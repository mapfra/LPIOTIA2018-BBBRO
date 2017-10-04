/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.persistence.mongodb.util;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.eclipse.om2m.commons.entities.DynamicAuthorizationConsultationEntity;
import org.eclipse.om2m.persistence.mongodb.DBServiceImpl;
import org.eclipse.om2m.persistence.service.util.DynamicAuthorizationConsultationUtil;

public class DynamicAuthorizationConsultationUtilImpl implements DynamicAuthorizationConsultationUtil {
	
	private static final DynamicAuthorizationConsultationUtilImpl INSTANCE = new DynamicAuthorizationConsultationUtilImpl();
	
	public static DynamicAuthorizationConsultationUtilImpl getInstance() {
		return INSTANCE;
	}
	
	private DynamicAuthorizationConsultationUtilImpl() {
	}

	@Override
	public List<DynamicAuthorizationConsultationEntity> getDynamicAuthorizationConsultations(
			String nonHierarchicalUri) {
		List<DynamicAuthorizationConsultationEntity> daces = new ArrayList<>();
		
		// find resource
		Document doc = DBServiceImpl.getInstance().getResourceCollection().find(eq("ResourceID", nonHierarchicalUri)).first();
		
		if (doc != null) {
			// retrieve dynamicAuthorizationConsultationIDs
			List<Document> dacesDoc = doc.get("#DynamicAuthorizationConsultations", ArrayList.class);
			for(Object daceRef : dacesDoc) {
				
				Document daceRefDoc = (Document) daceRef;
				
				// load doc for each dace ref
				Document daceDoc = DBServiceImpl.getInstance().getResourceCollection().find(eq("ResourceID", daceRefDoc.get("ResourceID"))).first();
				
				daces.add(DBServiceImpl.getInstance().getGson().fromJson(daceDoc.toJson(), DynamicAuthorizationConsultationEntity.class));
			}
			
			if (daces.isEmpty()) {
				// load DACES from its parents
				daces.addAll(getDynamicAuthorizationConsultations(doc.getString("ParentID")));
			}
		}
		
		
		
		return daces;
	}

}
