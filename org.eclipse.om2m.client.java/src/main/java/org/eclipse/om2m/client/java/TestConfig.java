/*******************************************************************************
 * Copyright (c) 2016- 2017 SENSINOV (www.sensinov.com)
 * 41 Rue de la découverte 31676 Labège - France 
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.om2m.client.java;

public class TestConfig {
	
	public final static String originator="admin:admin";
	public final static String cseProtocol="http";
	
	public final static String cseIp = "192.168.0.2";
	public final static int csePort = 8080;
	
	public final static String cseId = "server";
	public final static String cseName = "server";
	
	public final static String remoteCseIp = "192.168.0.5";
	public final static int remoteCSEPort = 8282;
	
	public final static String remoteCseId = "gateway-1";
	public final static String remoteCseName = "gateway-1";
	
	public final static String csePoa = cseProtocol+"://"+cseIp+":"+csePort;
	public final static String remoteCsePoa = cseProtocol+"://"+remoteCseIp+":"+remoteCSEPort; 
}
