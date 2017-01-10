package org.eclipse.om2m.sdt.home.types;

import org.eclipse.om2m.sdt.types.BasicType;

public class HomeBasicType extends BasicType {

	static public final HomeBasicType ALERTCOLOURCODE = new HomeBasicType("alertColourCode", Integer.class);
	static public final HomeBasicType DOORSTATE = new HomeBasicType("doorState", Integer.class);
	static public final HomeBasicType LEVEL = new HomeBasicType("level", Integer.class);
	static public final HomeBasicType LOCKSTATE = new HomeBasicType("lockState", Integer.class);
	static public final HomeBasicType SUPPORTEDMODE = new HomeBasicType("supportedMode", Integer.class);
	static public final HomeBasicType TONE = new HomeBasicType("tone", Integer.class);
	static public final HomeBasicType FOAMSTRENGTH =  new HomeBasicType("foamStrength", Integer.class);
	static public final HomeBasicType TASTESTRENGTH =  new HomeBasicType("tasteStrength", Integer.class);

    protected HomeBasicType(String v, Class<?> c) {
    	super(v, c);
    }
    
}
