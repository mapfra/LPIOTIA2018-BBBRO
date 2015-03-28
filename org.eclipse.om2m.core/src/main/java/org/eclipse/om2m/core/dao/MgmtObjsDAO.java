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
 * 		conception and documentation.
 *     Mahdi Ben Alaya (Project co-founder) - Management and initial specification, 
 * 		conception, implementation, test and documentation.
 *     Christophe Chassot - Management and initial specification.
 *     Khalil Drira - Management and initial specification.
 *     Yassine Banouar - Initial specification, conception, implementation, test 
 * 		and documentation.
 *     Guillaume Garzone - Conception, implementation, test and documentation.
 *     Francois Aissaoui - Conception, implementation, test and documentation.
 ******************************************************************************/
package org.eclipse.om2m.core.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.eclipse.om2m.commons.resource.DBEntities;
import org.eclipse.om2m.commons.resource.MgmtCmd;
import org.eclipse.om2m.commons.resource.MgmtObj;
import org.eclipse.om2m.commons.resource.MgmtObjs;
import org.eclipse.om2m.commons.resource.ReferenceToNamedResource;
import org.eclipse.om2m.commons.resource.Subscriptions;
import org.eclipse.om2m.commons.resource.Refs;

/**
 * Implements CRUD Methods for {@link MgmtObjs} collection resource persistence.
 *
 * @author <ul>
 *         <li>Yessine Feki < yfeki@laas.fr > < yessine.feki@ieee.org ></li>
 *         <li>Mahdi Ben Alaya < ben.alaya@laas.fr > < benalaya.mahdi@gmail.com ></li>  
 *         <li>Yassine Banouar < ybanouar@laas.fr > < yassine.banouar@gmail.com ></li>
 *         </ul>
 */
public class MgmtObjsDAO extends DAO<MgmtObjs> {

    /**
     * Creates an {@link MgmtObjs} collection resource in the DataBase.
     * @param resource - The {@link MgmtObjs} collection resource to create
     */
	@Override
    public void create(MgmtObjs resource, EntityManager em) {
		// NOT ALLOWED
    }

    /**
     * Retrieves the {@link MgmtObjs} collection resource based on its uri with sub-resources references
     * @param uri - uri of the {@link MgmtObjs} collection resource
     * @return The requested {@link MgmtObjs} collection resource otherwise null
     */
    public MgmtObjs find(String uri, EntityManager em) {
        MgmtObjs mgmtObjs = new MgmtObjs();
        mgmtObjs.setUri(uri);
        
        if(mgmtObjs != null){
        	mgmtObjs.getMgmtObjCollection().getNamedReference().clear();
            mgmtObjs.getMgmtCmdCollection().getNamedReference().clear();

            // Find mgmtObj sub-resources and add their references
            String q = DBUtil.generateLikeRequest(DBEntities.MGMTOBJ_ENTITY, uri);
        	javax.persistence.Query query = em.createQuery(q);
        	@SuppressWarnings("unchecked")
			List<MgmtObj> result = query.getResultList();
            
            for (MgmtObj obj : result) {
                ReferenceToNamedResource reference = new ReferenceToNamedResource();
                reference.setId(obj.getId());
                reference.setValue(obj.getUri());
                mgmtObjs.getMgmtObjCollection().getNamedReference().add(reference);
            }

            // Find mgmtCmd sub-resources and add their references
        	String q2 = DBUtil.generateLikeRequest(DBEntities.MGMTCMD_ENTITY, uri);
        	javax.persistence.Query query2 = em.createQuery(q2);
        	@SuppressWarnings("unchecked")
			List<MgmtCmd> result2 = query2.getResultList();

            for (MgmtCmd cmd : result2) {
                ReferenceToNamedResource reference = new ReferenceToNamedResource();
                reference.setId(cmd.getId());
                reference.setValue(cmd.getUri());
                mgmtObjs.getMgmtObjCollection().getNamedReference().add(reference);
            }
        }
        return mgmtObjs;
    }

    /**
     * Updates an existing {@link MgmtObjs} collection resource in the DataBase
     * @param resource - The {@link MgmtObjs} the updated resource
     */
    @Override
    public void update(MgmtObjs resource, EntityManager em) {
    	// NOT ALLOWED
    }

    /**
     * Deletes the {@link MgmtObjs} collection resource from the DataBase without validating the transaction
     * @Param the {@link MgmtObjs} collection resource to delete
     */
    public void delete(MgmtObjs resource, EntityManager em) {
    	Subscriptions subscriptions = new Subscriptions();
    	subscriptions.setUri(resource.getSubscriptionsReference());
    	
        // Delete subscriptions
        DAOFactory.getSubscriptionsDAO().delete(subscriptions, em);
        // Delete mgmtObj sub-resources
    	String q = DBUtil.generateLikeRequest(DBEntities.MGMTOBJ_ENTITY, resource.getUri());
    	javax.persistence.Query query = em.createQuery(q);
    	@SuppressWarnings("unchecked")
		List<MgmtObj> result = query.getResultList();

        for (MgmtObj obj : result) {
        	obj.setSubscriptionsReference(obj.getUri() + Refs.SUBSCRIPTIONS_REF);
            DAOFactory.getMgmtObjDAO().delete(obj, em);
        }

        // Delete mgmtCmd sub-resource
    	String q2 = DBUtil.generateLikeRequest(DBEntities.MGMTCMD_ENTITY, resource.getUri());
    	javax.persistence.Query query2 = em.createQuery(q2);
    	@SuppressWarnings("unchecked")
		List<MgmtCmd> result2 = query2.getResultList();

        for (MgmtCmd cmd : result2) {
        	cmd.setExecInstancesReference(cmd.getUri() + Refs.EXECINSTANCES_REF);
        	cmd.setSubscriptionsReference(cmd.getUri() + Refs.SUBSCRIPTIONS_REF);
            DAOFactory.getMgmtCmdDAO().delete(cmd, em);
        }
    }
}
