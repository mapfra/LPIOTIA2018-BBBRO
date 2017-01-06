package org.onem2m.home.types;

import org.onem2m.sdt.types.BasicType;
import org.onem2m.sdt.types.SimpleType;

public class HomeSimpleType extends SimpleType {

	static public final HomeSimpleType AlertColourCode = new HomeSimpleType(HomeBasicType.ALERTCOLOURCODE);
	static public final HomeSimpleType DoorState = new HomeSimpleType(HomeBasicType.DOORSTATE);
	static public final HomeSimpleType Level = new HomeSimpleType(HomeBasicType.LEVEL);
	static public final HomeSimpleType LockState = new HomeSimpleType(HomeBasicType.LOCKSTATE);
	static public final HomeSimpleType SupportedMode = new HomeSimpleType(HomeBasicType.SUPPORTEDMODE);
	static public final HomeSimpleType Tone = new HomeSimpleType(HomeBasicType.TONE);
	static public final HomeSimpleType FoamStrength = new HomeSimpleType(HomeBasicType.FOAMSTRENGTH);
	static public final HomeSimpleType TasteStrength = new HomeSimpleType(HomeBasicType.TASTESTRENGTH);
	
	protected HomeSimpleType(final BasicType type) {
		super(type);
	}
	
	public String getOneM2MType() {
		return "hd:" + getType().getValue();
	}

}
