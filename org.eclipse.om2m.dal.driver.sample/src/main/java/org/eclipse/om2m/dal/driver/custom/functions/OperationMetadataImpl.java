/*******************************************************************************
 * Copyright (c) 2017 Huawei Technologies Co., Ltd (http://www.huawei.com)
 * Huawei Base, Bantian,Longgang District ,Shenzhen ,Guangdong ,China
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial Contributors:
 *     Seven Ganlu : Developer
 *
 * New contributors :
 *******************************************************************************/


package org.eclipse.om2m.dal.driver.custom.functions;

import java.util.Hashtable;
import java.util.Map;

import org.osgi.service.dal.OperationMetadata;
import org.osgi.service.dal.PropertyMetadata;

/**
 * Operation meta data implementation.
 */
public class OperationMetadataImpl implements OperationMetadata {

	/** Operation meta data */
	private Map<String, Object> metadatas = null;

	/** Return value and */
	private PropertyMetadata returnValuetMetadata = null;
	private PropertyMetadata[] parametersMetadata = null;

	/**
	 * Constructor
	 * @param description - description of this operation
	 * 		  parametersMetadata - meta data array of the operation's parameters
	 * 		  returnValueMetadata - meta data of the operation's return value
	 */
	public OperationMetadataImpl(String description,
			PropertyMetadata[] parametersMetadata,
			PropertyMetadata returnValueMetadata) {
		metadatas = new Hashtable<String, Object>();
		metadatas.put(DESCRIPTION, description);
		this.returnValuetMetadata = returnValueMetadata;
		this.parametersMetadata = parametersMetadata;
	}

	/**
	 * Return the operation meta data.
	 * 
	 * @param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Map getMetadata() {
		return metadatas;
	}

	/**
	 * Return the meta data of operation's return value.
	 * 
	 * @return PropertyMetadata
	 */
	@Override
	public PropertyMetadata getReturnValueMetadata() {
		return returnValuetMetadata;
	}

	/**
	 * Return the meta data array of operation's parameters.
	 * 
	 * @return PropertyMetadata[]
	 */
	@Override
	public PropertyMetadata[] getParametersMetadata() {
		return parametersMetadata;
	}

}
