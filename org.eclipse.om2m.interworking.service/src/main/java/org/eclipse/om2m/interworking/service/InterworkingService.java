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
 *     Khalil Drira - Management and initial specification.
 *     Guillaume Garzone - Initial specification, conception, implementation, test
 *         and documentation.
 *     François Aïssaoui - Initial specification, conception, implementation, test
 *         and documentation.
 *******************************************************************************/
package org.eclipse.om2m.interworking.service;

import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
/**
 * Interworking Proxy Unit (IPU) interface.
 * @author <ul>
 *         <li>Mahdi Ben Alaya < ben.alaya@laas.fr > < benalaya.mahdi@gmail.com ></li>
 *         <li>Yassine Banouar < ybanouar@laas.fr > < yassine.banouar@gmail.com ></li>
 *         </ul>
 */
public interface InterworkingService {
    /**
     * Executes a resource via a specific Interworking Proxy Unit.
     * @param RequestPrimitive - The generic request to handle.
     * @return The generic returned response.
     */
    public ResponsePrimitive doExecute(RequestPrimitive RequestPrimitive);

//    /**
//    * Retrieves a resource via a specific Interworking Proxy Unit.
//    * @param RequestPrimitive - The generic request to handle.
//    * @return The generic returned response.
//    */
//    public ResponsePrimitive doRetrieve(RequestPrimitive RequestPrimitive);
//
//    /**
//    * Updates a resource via a specific Interworking Proxy Unit.
//    * @param RequestPrimitive - The generic request to handle.
//    * @return The generic returned response.
//    */
//    public ResponsePrimitive doUpdate(RequestPrimitive RequestPrimitive);
//
//    /**
//    * Deletes a resource via a specific Interworking Proxy Unit.
//    * @param RequestPrimitive - The generic request to handle.
//    * @return The generic returned response.
//    */
//    public ResponsePrimitive doDelete(RequestPrimitive RequestPrimitive);
//
//    /**
//    * Creates a resource via a specific Interworking Proxy Unit.
//    * @param RequestPrimitive - The generic request to handle.
//    * @return The generic returned response.
//    */
//    public ResponsePrimitive doCreate(RequestPrimitive RequestPrimitive);

    /***
     * Returns the ApocPath id required for the {@link InterworkingProxyController} to dispatch
     * a received request to the correct specific Interworking Proxy Unit (IPU).
     * @return Application point of contact
     */
    public String getAPOCPath();
}
