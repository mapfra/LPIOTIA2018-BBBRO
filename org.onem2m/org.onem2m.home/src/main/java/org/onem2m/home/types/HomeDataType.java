package org.onem2m.home.types;

import org.onem2m.sdt.types.DataType;

public class HomeDataType extends DataType {

	static public final HomeDataType AlertColourCode = new HomeDataType("alertColourCode", HomeSimpleType.AlertColourCode);
	static public final HomeDataType DoorState = new HomeDataType("doorState", HomeSimpleType.DoorState);
	static public final HomeDataType Level = new HomeDataType("level", HomeSimpleType.Level);
	static public final HomeDataType LockState = new HomeDataType("lockState", HomeSimpleType.LockState);
	static public final HomeDataType SupportedMode = new HomeDataType("supportedMode", HomeSimpleType.SupportedMode);
	static public final HomeDataType Tone = new HomeDataType("tone", HomeSimpleType.Tone);
	static public final HomeDataType FoamStrength = new HomeDataType("foamStrength", HomeSimpleType.FoamStrength);
	static public final HomeDataType TasteStrength = new HomeDataType("tasteStrength", HomeSimpleType.TasteStrength);

	public HomeDataType(final String name, final TypeChoice type) {
		super(name, type);
	}
	
}
