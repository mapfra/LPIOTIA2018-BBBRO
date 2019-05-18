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

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.eclipse.om2m.commons.entities.UriMapperEntity;
import org.eclipse.om2m.commons.resource.FilterCriteria;
import org.eclipse.om2m.persistence.mongodb.Constants;
import org.eclipse.om2m.persistence.mongodb.DBServiceImpl;
import org.eclipse.om2m.persistence.service.util.ComplexFindUtil;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

public class ComplexFindUtilImpl implements ComplexFindUtil, Constants {

	private static final ComplexFindUtilImpl INSTANCE = new ComplexFindUtilImpl();

	private ComplexFindUtilImpl() {
	}

	public static ComplexFindUtilImpl getInstance() {
		return INSTANCE;
	}

	@Override
	public List<UriMapperEntity> getChildUrisDis(String rootUri, FilterCriteria filter) {
		List<UriMapperEntity> uris = new ArrayList<>();
		// retrieve rootUri document
		Document rootDocument = DBServiceImpl.getInstance().getResourceCollection()
				.find(eq(RES_ID, rootUri)).first();

		// rootUri exists ?
		if (rootDocument == null) {
			return uris;
		}
		// at this point, we are sure that rootDocument is not null

		// retrieve hierarchicalUri
		String hierarchicalUri = rootDocument.getString(HIERARCHICAL_URI);
		// check hierarchical uri
		if (hierarchicalUri == null) {
			return uris;
		}

		Bson requestFilter = regex(HIERARCHICAL_URI, Pattern.compile(hierarchicalUri + "*"));
		if (filter.getResourceType() != null) {
			requestFilter = and(requestFilter, eq(RES_TYPE, filter.getResourceType().intValue()));
		}
		
		FindIterable<Document> elements = DBServiceImpl.getInstance().getResourceCollection()
				.find(requestFilter);
		for (MongoCursor<Document> cursor = elements.iterator(); cursor.hasNext();) {
			Document element = cursor.next();
			UriMapperEntity uriMapperEntity = new UriMapperEntity();
			uriMapperEntity.setHierarchicalUri(element.getString(HIERARCHICAL_URI));
			uriMapperEntity.setNonHierarchicalUri(element.getString(RES_ID));
			uriMapperEntity.setResourceType(element.getInteger(RES_TYPE));
			uris.add(uriMapperEntity);
		}
		return uris;
	}

}
