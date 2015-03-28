/*******************************************************************************
 * Copyright (c) 2013-2015 LAAS-CNRS (www.laas.fr)
 * 7 Colonel Roche 31077 Toulouse - France
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Thierry Monteil (Project co-founder) - Management and initial specification,
 *         conception and documentation.
 *     Mahdi Ben Alaya (Project co-founder) - Management and initial specification,
 *         conception, implementation, test and documentation.
 *     Christophe Chassot - Management and initial specification.
 *     Khalil Drira - Management and initial specification.
 *     Yassine Banouar - Initial specification, conception, implementation, test
 *         and documentation.
 *     Guillaume Garzone - Conception, implementation, test and documentation.
 *     Francois Aissaoui - Conception, implementation, test and documentation.
 ******************************************************************************/
package org.eclipse.om2m.core.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.eclipse.om2m.commons.resource.DBEntities;
import org.eclipse.om2m.commons.resource.MgmtObjs;
import org.eclipse.om2m.commons.resource.ReferenceToNamedResource;
import org.eclipse.om2m.commons.resource.Refs;
import org.eclipse.om2m.commons.resource.Scl;
import org.eclipse.om2m.commons.resource.Scls;
import org.eclipse.om2m.commons.resource.Subscriptions;

/**
 * Implements CRUD Methods for {@link Scls} collection resource persistence.
 *
 * @author <ul>
 *         <li>Yessine Feki < yfeki@laas.fr > < yessine.feki@ieee.org ></li>
 *         <li>Mahdi Ben Alaya < ben.alaya@laas.fr > < benalaya.mahdi@gmail.com ></li>
 *         <li>Yassine Banouar < ybanouar@laas.fr > < yassine.banouar@gmail.com ></li>
 *         </ul>
 */
public class SclsDAO extends DAO<Scls>{

    /**
     * Creates an {@link Scls} collection resource in the DataBase.
     * @param resource - The {@link Scls} collection resource to create
     */
	@Override
    public void create(Scls resource, EntityManager em) {
		// NOT ALLOWED
    }

    /**
     * Retrieves the {@link Scls} collection resource based on its uri with sub-resources references
     * @param uri - uri of the {@link Scls} collection resource
     * @return The requested {@link Scls} collection resource otherwise null
     */
    public Scls find(String uri, EntityManager em){
    	Scls scls = new Scls();
    	scls.setUri(uri);

    	scls.getSclCollection().getNamedReference().clear();

    	String q = DBUtil.generateLikeRequest(DBEntities.SCL_ENTITY, uri);
    	javax.persistence.Query query = em.createQuery(q);
    	@SuppressWarnings("unchecked")
    	List<Scl> result = query.getResultList();
    	for (Scl s : result){
    		ReferenceToNamedResource ref = new ReferenceToNamedResource();
    		ref.setId(s.getSclId());
    		ref.setValue(s.getUri());
    		scls.getSclCollection().getNamedReference().add(ref);
    	}

        return scls;
    }

    /**
     * Updates an existing {@link Scls} collection resource in the DataBase
     * @param resource - The {@link Scls} the updated resource
     */
    @Override
    public void update(Scls resource, EntityManager em) {
    	// NOT ALLOWED
    }

    /**
     * Deletes the {@link Scls} collection resource from the DataBase without validating the transaction
     * @Param the {@link Scls} collection resource to delete
     */
    public void delete(Scls resource, EntityManager em) {
    	Subscriptions subscriptions = new Subscriptions();
    	subscriptions.setUri(resource.getSubscriptionsReference());
    	
    	MgmtObjs mgmtObjs = new MgmtObjs();
    	mgmtObjs.setUri(resource.getMgmtObjsReference());
    	mgmtObjs.setSubscriptionsReference(mgmtObjs.getUri()+Refs.SUBSCRIPTIONS_REF);
    	
        // Delete subscriptions
        DAOFactory.getSubscriptionsDAO().delete(subscriptions, em);
        // Delete mgmtObjs
        DAOFactory.getMgmtObjsDAO().delete(mgmtObjs, em);

        // Delete scl sub-resources
    	String q = DBUtil.generateLikeRequest(DBEntities.SCL_ENTITY, resource.getUri());
    	javax.persistence.Query query = em.createQuery(q);
    	@SuppressWarnings("unchecked")
		List<Scl> result = query.getResultList();
    	for (Scl s : result){
    		s.setContainersReference(s.getUri()+Refs.CONTAINERS_REF);
    		s.setGroupsReference(s.getUri()+Refs.GROUPS_REF);
    		s.setApplicationsReference(s.getUri()+Refs.APPLICATIONS_REF);
    		s.setAccessRightsReference(s.getUri()+Refs.ACCESSRIGHTS_REF);
    		s.setSubscriptionsReference(s.getUri()+Refs.SUBSCRIPTIONS_REF);
    		s.setMgmtObjsReference(s.getUri()+Refs.MGMTOBJS_REF);
    		s.setNotificationChannelsReference(s.getUri()+Refs.NOTIFICATIONCHANNELS_REF);
    		s.setM2MPocsReference(s.getUri()+Refs.M2MPOCS_REF);
    		s.setAttachedDevicesReference(s.getUri()+Refs.ATTACHEDDEVICES_REF);
    		DAOFactory.getSclDAO().delete(s, em);
    	}
        
    }
}
