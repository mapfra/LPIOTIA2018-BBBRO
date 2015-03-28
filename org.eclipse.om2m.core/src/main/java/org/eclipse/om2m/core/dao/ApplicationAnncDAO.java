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

import javax.persistence.EntityManager;

import org.eclipse.om2m.commons.resource.AccessRights;
import org.eclipse.om2m.commons.resource.ApplicationAnnc;
import org.eclipse.om2m.commons.resource.Containers;
import org.eclipse.om2m.commons.resource.Groups;

/**
 * Implements CRUD Methods for {@link ApplicationAnnc} resource persistence.
 *
 * @author <ul>
 *         <li>Yessine Feki < yfeki@laas.fr > < yessine.feki@ieee.org ></li>
 *         <li>Mahdi Ben Alaya < ben.alaya@laas.fr > < benalaya.mahdi@gmail.com ></li>  
 *         <li>Yassine Banouar < ybanouar@laas.fr > < yassine.banouar@gmail.com ></li>
 *         </ul>
 */

public class ApplicationAnncDAO extends DAO<ApplicationAnnc> {

    /**
     * Retrieves the {@link ApplicationAnnc} resource from the Database based on its uri
     * @param uri - uri of the {@link ApplicationAnnc} resource to retrieve
     * @return The requested {@link ApplicationAnnc} resource otherwise null
     */
    public ApplicationAnnc find(String uri, EntityManager em) {
        if (uri == null) {
        	return null;
        }
		return em.find(ApplicationAnnc.class, uri);       
    }

    /**
     * Deletes the {@link ApplicationAnnc} resource from the DataBase without validating the transaction
     * @param resource - The {@link ApplicationAnnc} resource to delete
     */
    public void delete(ApplicationAnnc resource, EntityManager em) {
        // Delete accessRights
    	AccessRights accessRights = new AccessRights();
    	accessRights.setUri(resource.getAccessRightsReference());
        DAOFactory.getAccessRightsDAO().delete(accessRights, em);
        // Delete containers
        Containers containers = new Containers();
        containers.setUri(resource.getContainersReference());
        DAOFactory.getContainersDAO().delete(containers, em);
        // Delete groups
        Groups groups = new Groups();
        groups.setUri(resource.getGroupsReference());
        DAOFactory.getGroupsDAO().delete(groups, em);
        // Delete the resource
        em.remove(resource);
    }
}
