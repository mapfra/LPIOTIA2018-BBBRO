package org.eclipse.om2m.adn.tests;

public class TestConfig {
	
	public final static String originator="admin:admin";
	public final static String cseProtocol="http";
	
	public final static String cseIp = "127.0.0.1";
	public final static int csePort = 8080;
	
	public final static String cseId = "in-cse";
	public final static String cseName = "in-name";
	
	public final static String remoteCseIp = "127.0.0.1";
	public final static int remoteCSEPort = 8282;
	
	public final static String remoteCseId = "mn-cse";
	public final static String remoteCseName = "mn-name";
	
	public final static String csePoa = cseProtocol+"://"+cseIp+":"+csePort;
	public final static String remoteCsePoa = cseProtocol+"://"+remoteCseIp+":"+remoteCSEPort; 
}
