package org.eclipse.om2m.ipe.sample.constants;

import org.eclipse.om2m.commons.constants.Constants;

public class SampleConstants {
	
	private SampleConstants(){}
	
	public static final String POA = "sample";
	public static final String DATA = "DATA";
	public static final String DESC = "DESCRIPTOR";
	public static final String AE_NAME = "AE_IPE_SAMPLE";
	public static final String LAMP_0 = "LAMP_0";
	public static final String LAMP_1 = "LAMP_1";
	public static final String QUERY_STRING_OP = "op";
	public static final String QUERY_STRING_LAMP_ID = "lampid";
	public static final boolean GUI = Boolean.valueOf(System.getProperty("org.eclipse.om2m.ipe.sample.gui", "true"));
	
	public static String CSE_ID = "/" + Constants.CSE_ID;
}
