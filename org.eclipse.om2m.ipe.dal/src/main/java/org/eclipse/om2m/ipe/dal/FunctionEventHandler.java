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

package org.eclipse.om2m.ipe.dal;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.dal.Function;
import org.osgi.service.dal.FunctionData;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.obix.Obj;
import org.eclipse.om2m.commons.obix.Str;
import org.eclipse.om2m.commons.obix.io.ObixEncoder;
import org.eclipse.om2m.commons.resource.ContentInstance;

/**
 * Device function event handler.
 */
public class FunctionEventHandler implements EventHandler {

	/** Logger */
	private static Log LOGGER = LogFactory.getLog(FunctionEventHandler.class);

	private static final String FUNCTION_PROPERTY_VALUE = "dal.function.property.value";
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/** Function UID */
	private String funcUid = "";
	/** Function resource ID */
	private String funcResourceId = "";

	/**
	 * Constructor
	 * 
	 * @param funcUid
	 *            funcResourceId
	 * @return
	 */
	public FunctionEventHandler(String funcUid, String funcResourceId) {
		this.funcUid = funcUid;
		this.funcResourceId = funcResourceId;
	}

	/**
	 * Handle the function event
	 * 
	 * @param event
	 * @return
	 */
	@Override
	public void handleEvent(Event event) {
		LOGGER.info(String.format("Recv Func Event %s (%s) (%s)",
				event.getTopic(), funcUid,
				event.getProperty(Function.SERVICE_UID)));

		// Create a content instance resource
		ContentInstance cin = new ContentInstance();
		FunctionData data = (FunctionData) event
				.getProperty(FUNCTION_PROPERTY_VALUE);

		Obj obj = new Obj();
		obj.add(new Str("DateTime", dateFormat.format(new Date(data
				.getTimestamp()))));
		obj.add(new Str("Class", data.getClass().getName()));
		String[] propertyNames = event.getPropertyNames();

		if (propertyNames != null) {
			for (String properName : propertyNames) {
				obj.add(new Str(properName, event.getProperty(properName)
						.toString()));
			}
		}

		obj.add(new Str("Event", data.toString()));
		cin.setContent(ObixEncoder.toString(obj));
		cin.setContentInfo(MimeMediaType.OBIX + ":" + MimeMediaType.ENCOD_PLAIN);
		InterworkingServiceImpl.createResource(funcResourceId,  cin,
				ResourceType.CONTENT_INSTANCE);
	}

}
