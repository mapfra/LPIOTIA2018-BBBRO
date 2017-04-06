package org.eclipse.om2m.persistence.mongodb.util;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.eclipse.om2m.commons.entities.UriMapperEntity;
import org.eclipse.om2m.commons.resource.FilterCriteria;
import org.eclipse.om2m.persistence.mongodb.DBServiceImpl;
import org.eclipse.om2m.persistence.service.util.ComplexFindUtil;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

public class ComplexFindUtilImpl implements ComplexFindUtil {

	private static final ComplexFindUtilImpl INSTANCE = new ComplexFindUtilImpl();

	private static final String RESOURCE_ID = "ResourceID";
	private static final String HIERARCHICAL_URI = "HierarchicalURI";
	private static final String RESOURCE_TYPE = "ResourceType";

	private ComplexFindUtilImpl() {
	}

	public static ComplexFindUtilImpl getInstance() {
		return INSTANCE;
	}

	@Override
	public List<UriMapperEntity> getChildUrisDis(String rootUri, FilterCriteria filter) {

		List<UriMapperEntity> uris = new ArrayList<>();

		// retrieve rootUri document
		Document rootDocument = DBServiceImpl.getInstance().getResourceCollection().find(eq(RESOURCE_ID, rootUri))
				.first();

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
			requestFilter = and(requestFilter, eq(RESOURCE_TYPE, filter.getResourceType().intValue()));
		}
		
		FindIterable<Document> elements = DBServiceImpl.getInstance().getResourceCollection()
				.find(requestFilter);
		for (MongoCursor<Document> cursor = elements.iterator(); cursor.hasNext();) {
			Document element = cursor.next();

			String hUri = element.getString(HIERARCHICAL_URI);
			String resourceId = element.getString(RESOURCE_ID);
			Integer resourceType = element.getInteger(RESOURCE_TYPE);

			UriMapperEntity uriMapperEntity = new UriMapperEntity();
			uriMapperEntity.setHierarchicalUri(hUri);
			uriMapperEntity.setNonHierarchicalUri(resourceId);
			uriMapperEntity.setResourceType(resourceType);

			uris.add(uriMapperEntity);
		}

		return uris;
	}

}
