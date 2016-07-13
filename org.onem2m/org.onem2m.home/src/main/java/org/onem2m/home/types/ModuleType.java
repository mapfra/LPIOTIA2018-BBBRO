package org.onem2m.home.types;

public enum ModuleType {
	
	alarmSpeaker(1, "alarmSpeaker"),
	audioVideoInput(2, "audioVideoInput"),
	audioVolume(3, "audioVolume"),
	battery(4, "battery"),
	binarySwitch(5, "binarySwitch"),
	bioElectricalImpedanceAnalysis(6, "bioElectricalImpedanceAnalysis"),
	boiler(7, "boiler"),
	brightness(8, "brightness"),
	clock(9, "clock"),
	colour(10, "colour"),
	colourSaturation(11, "colourSaturation"),
	doorStatus(12, "doorStatus"),
	electricVehicleConnector(13, "electricVehicleConnector"),
	energyConsumption(14, "energyConsumption"),
	energyGeneration(15, "energyGeneration"),
	faultDetection(16, "faultDetection"),
	height(17, "height"),
	hotWaterSupply(18, "hotWaterSupply"),
	keypad(19, "keypad"),
	motionSensor(20, "motionSensor"),
	oximeter(21, "oximeter"),
	powerSave(22, "powerSave"),
	pushButton(23, "pushButton"),
	recorder(24, "recorder"),
	refrigeration(25, "refrigeration"),
	relativeHumidity(26, "relativeHumidity"),
	rinseLevel(27, "rinseLevel"),
	runMode(28, "runMode"),
	signalStrength(29, "signalStrength"),
	smokeSensor(30, "smokeSensor"),
	spinLevel(31, "spinLevel"),
	televisionChannel(32, "televisionChannel"),
	temperature(33, "temperature"),
	temperatureAlarm(34, "temperatureAlarm"),
	timer(35, "timer"),
	turbo(36, "turbo"),
	waterFlow(37, "waterFlow"),
	waterLevel(38, "waterLevel"),
	waterSensor(39, "waterSensor"),
	weight(40, "weight"),
	wind(41, "wind"),

	atmosphericPressureSensor(100, "atmosphericPressureSensor"),
	carbonDioxideSensor(101, "carbonDioxideSensor"),
	carbonMonoxideSensor(102, "carbonMonoxideSensor"),
	contactSensor(103, "contactSensor"),
	dimming(104, "dimming"),
	energyOverloadCircuitBreaker(105, "energyOverloadCircuitBreaker"),
	genericSensor(106, "genericSensor"),
	glassBreakSensor(107, "glassBreakSensor"),
	presenceSensor(108, "presenceSensor"),
	touchSensor(109, "touchSensor"),
	lock(110, "lock"),

	abstractAlarmSensor(200, "abstractAlarmSensor");
	
	static private final String PATH = "org.onem2m.home.moduleclass.";
	
	private int value;
	private String def;
	
	ModuleType(int v, String s) {
		value = v;
		def = s;
	}

    public int getValue() {
        return value;
    }
    
    public String getDefinition() {
    	return PATH + def;
    }

    public static ModuleType fromValue(int v) {
        for (ModuleType c: ModuleType.values()) {
            if (c.value == v) {
                return c;
            }
        }
        throw new IllegalArgumentException("Undefined value " + v);
    }

}
