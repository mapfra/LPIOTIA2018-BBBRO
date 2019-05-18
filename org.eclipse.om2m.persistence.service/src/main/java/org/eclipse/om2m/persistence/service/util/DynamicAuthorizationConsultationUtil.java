package org.eclipse.om2m.persistence.service.util;

import java.util.List;

import org.eclipse.om2m.commons.entities.DynamicAuthorizationConsultationEntity;

public interface DynamicAuthorizationConsultationUtil {

	/**
	 * Retrieves the list of {@link DynamicAuthorizationConsultationEntity}
	 * objects of the entity provided in argument
	 * 
	 * @param nonHierarchicalUri
	 *            non hierarchical uri of the entity
	 * @return list of {@link DynamicAuthorizationConsultationEntity}. This list
	 *         may be empty.
	 */
	public List<DynamicAuthorizationConsultationEntity> getDynamicAuthorizationConsultations(String nonHierarchicalUri);

}
