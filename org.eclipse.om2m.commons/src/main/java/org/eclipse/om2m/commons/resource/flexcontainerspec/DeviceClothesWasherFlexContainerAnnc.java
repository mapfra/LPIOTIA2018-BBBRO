/*
Device : DeviceClothesWasherAnnc



A clothes washer is a home appliance that is used to wash laundry, such as clothing and sheets. This information model provides capabilities to interact with specific functions and resources of clothes washers.

Created: 2017-08-09 15:38:05
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;


@XmlRootElement(name = DeviceClothesWasherFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceClothesWasherFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceClothesWasherFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceClothesWasherAnnc";
	public static final String SHORT_NAME = "deCWrAnnc";
	
	public DeviceClothesWasherFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceClothesWasherFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
		getBinarySwitch();
		getBinarySwitchAnnc();
		getTimer();
		getTimerAnnc();
		getRunMode();
		getRunModeAnnc();
		getTemperature();
		getTemperatureAnnc();
		getWaterLevel();
		getWaterLevelAnnc();
		getRinseLevel();
		getRinseLevelAnnc();
		getWaterFlow();
		getWaterFlowAnnc();
		getSpinLevel();
		getSpinLevelAnnc();
	}
	
	@XmlElement(name="binSh", required=true, type=BinarySwitchFlexContainerAnnc.class)
	private BinarySwitchFlexContainer binarySwitch;
	
	
	public void setBinarySwitch(BinarySwitchFlexContainer binarySwitch) {
		this.binarySwitch = binarySwitch;
		getFlexContainerOrContainerOrSubscription().add(binarySwitch);
	}
	
	public BinarySwitchFlexContainer getBinarySwitch() {
		this.binarySwitch = (BinarySwitchFlexContainer) getResourceByName(BinarySwitchFlexContainer.SHORT_NAME);
		return binarySwitch;
	}
	
	@XmlElement(name="binShAnnc", required=true, type=BinarySwitchFlexContainerAnnc.class)
	private BinarySwitchFlexContainerAnnc binarySwitchAnnc;
	
	
	public void setBinarySwitch(BinarySwitchFlexContainerAnnc binarySwitchAnnc) {
		this.binarySwitchAnnc = binarySwitchAnnc;
		getFlexContainerOrContainerOrSubscription().add(binarySwitchAnnc);
	}
	
	public BinarySwitchFlexContainerAnnc getBinarySwitchAnnc() {
		this.binarySwitchAnnc = (BinarySwitchFlexContainerAnnc) getResourceByName(BinarySwitchFlexContainerAnnc.SHORT_NAME);
		return binarySwitchAnnc;
	}
	
	@XmlElement(name="timer", required=true, type=TimerFlexContainerAnnc.class)
	private TimerFlexContainer timer;
	
	
	public void setTimer(TimerFlexContainer timer) {
		this.timer = timer;
		getFlexContainerOrContainerOrSubscription().add(timer);
	}
	
	public TimerFlexContainer getTimer() {
		this.timer = (TimerFlexContainer) getResourceByName(TimerFlexContainer.SHORT_NAME);
		return timer;
	}
	
	@XmlElement(name="timerAnnc", required=true, type=TimerFlexContainerAnnc.class)
	private TimerFlexContainerAnnc timerAnnc;
	
	
	public void setTimer(TimerFlexContainerAnnc timerAnnc) {
		this.timerAnnc = timerAnnc;
		getFlexContainerOrContainerOrSubscription().add(timerAnnc);
	}
	
	public TimerFlexContainerAnnc getTimerAnnc() {
		this.timerAnnc = (TimerFlexContainerAnnc) getResourceByName(TimerFlexContainerAnnc.SHORT_NAME);
		return timerAnnc;
	}
	
	@XmlElement(name="runMe", required=true, type=RunModeFlexContainerAnnc.class)
	private RunModeFlexContainer runMode;
	
	
	public void setRunMode(RunModeFlexContainer runMode) {
		this.runMode = runMode;
		getFlexContainerOrContainerOrSubscription().add(runMode);
	}
	
	public RunModeFlexContainer getRunMode() {
		this.runMode = (RunModeFlexContainer) getResourceByName(RunModeFlexContainer.SHORT_NAME);
		return runMode;
	}
	
	@XmlElement(name="runMeAnnc", required=true, type=RunModeFlexContainerAnnc.class)
	private RunModeFlexContainerAnnc runModeAnnc;
	
	
	public void setRunMode(RunModeFlexContainerAnnc runModeAnnc) {
		this.runModeAnnc = runModeAnnc;
		getFlexContainerOrContainerOrSubscription().add(runModeAnnc);
	}
	
	public RunModeFlexContainerAnnc getRunModeAnnc() {
		this.runModeAnnc = (RunModeFlexContainerAnnc) getResourceByName(RunModeFlexContainerAnnc.SHORT_NAME);
		return runModeAnnc;
	}
	
	@XmlElement(name="tempe", required=true, type=TemperatureFlexContainerAnnc.class)
	private TemperatureFlexContainer temperature;
	
	
	public void setTemperature(TemperatureFlexContainer temperature) {
		this.temperature = temperature;
		getFlexContainerOrContainerOrSubscription().add(temperature);
	}
	
	public TemperatureFlexContainer getTemperature() {
		this.temperature = (TemperatureFlexContainer) getResourceByName(TemperatureFlexContainer.SHORT_NAME);
		return temperature;
	}
	
	@XmlElement(name="tempeAnnc", required=true, type=TemperatureFlexContainerAnnc.class)
	private TemperatureFlexContainerAnnc temperatureAnnc;
	
	
	public void setTemperature(TemperatureFlexContainerAnnc temperatureAnnc) {
		this.temperatureAnnc = temperatureAnnc;
		getFlexContainerOrContainerOrSubscription().add(temperatureAnnc);
	}
	
	public TemperatureFlexContainerAnnc getTemperatureAnnc() {
		this.temperatureAnnc = (TemperatureFlexContainerAnnc) getResourceByName(TemperatureFlexContainerAnnc.SHORT_NAME);
		return temperatureAnnc;
	}
	
	@XmlElement(name="watLl", required=true, type=WaterLevelFlexContainerAnnc.class)
	private WaterLevelFlexContainer waterLevel;
	
	
	public void setWaterLevel(WaterLevelFlexContainer waterLevel) {
		this.waterLevel = waterLevel;
		getFlexContainerOrContainerOrSubscription().add(waterLevel);
	}
	
	public WaterLevelFlexContainer getWaterLevel() {
		this.waterLevel = (WaterLevelFlexContainer) getResourceByName(WaterLevelFlexContainer.SHORT_NAME);
		return waterLevel;
	}
	
	@XmlElement(name="watLlAnnc", required=true, type=WaterLevelFlexContainerAnnc.class)
	private WaterLevelFlexContainerAnnc waterLevelAnnc;
	
	
	public void setWaterLevel(WaterLevelFlexContainerAnnc waterLevelAnnc) {
		this.waterLevelAnnc = waterLevelAnnc;
		getFlexContainerOrContainerOrSubscription().add(waterLevelAnnc);
	}
	
	public WaterLevelFlexContainerAnnc getWaterLevelAnnc() {
		this.waterLevelAnnc = (WaterLevelFlexContainerAnnc) getResourceByName(WaterLevelFlexContainerAnnc.SHORT_NAME);
		return waterLevelAnnc;
	}
	
	@XmlElement(name="rinLl", required=true, type=RinseLevelFlexContainerAnnc.class)
	private RinseLevelFlexContainer rinseLevel;
	
	
	public void setRinseLevel(RinseLevelFlexContainer rinseLevel) {
		this.rinseLevel = rinseLevel;
		getFlexContainerOrContainerOrSubscription().add(rinseLevel);
	}
	
	public RinseLevelFlexContainer getRinseLevel() {
		this.rinseLevel = (RinseLevelFlexContainer) getResourceByName(RinseLevelFlexContainer.SHORT_NAME);
		return rinseLevel;
	}
	
	@XmlElement(name="rinLlAnnc", required=true, type=RinseLevelFlexContainerAnnc.class)
	private RinseLevelFlexContainerAnnc rinseLevelAnnc;
	
	
	public void setRinseLevel(RinseLevelFlexContainerAnnc rinseLevelAnnc) {
		this.rinseLevelAnnc = rinseLevelAnnc;
		getFlexContainerOrContainerOrSubscription().add(rinseLevelAnnc);
	}
	
	public RinseLevelFlexContainerAnnc getRinseLevelAnnc() {
		this.rinseLevelAnnc = (RinseLevelFlexContainerAnnc) getResourceByName(RinseLevelFlexContainerAnnc.SHORT_NAME);
		return rinseLevelAnnc;
	}
	
	@XmlElement(name="watFw", required=true, type=WaterFlowFlexContainerAnnc.class)
	private WaterFlowFlexContainer waterFlow;
	
	
	public void setWaterFlow(WaterFlowFlexContainer waterFlow) {
		this.waterFlow = waterFlow;
		getFlexContainerOrContainerOrSubscription().add(waterFlow);
	}
	
	public WaterFlowFlexContainer getWaterFlow() {
		this.waterFlow = (WaterFlowFlexContainer) getResourceByName(WaterFlowFlexContainer.SHORT_NAME);
		return waterFlow;
	}
	
	@XmlElement(name="watFwAnnc", required=true, type=WaterFlowFlexContainerAnnc.class)
	private WaterFlowFlexContainerAnnc waterFlowAnnc;
	
	
	public void setWaterFlow(WaterFlowFlexContainerAnnc waterFlowAnnc) {
		this.waterFlowAnnc = waterFlowAnnc;
		getFlexContainerOrContainerOrSubscription().add(waterFlowAnnc);
	}
	
	public WaterFlowFlexContainerAnnc getWaterFlowAnnc() {
		this.waterFlowAnnc = (WaterFlowFlexContainerAnnc) getResourceByName(WaterFlowFlexContainerAnnc.SHORT_NAME);
		return waterFlowAnnc;
	}
	
	@XmlElement(name="spiLl", required=true, type=SpinLevelFlexContainerAnnc.class)
	private SpinLevelFlexContainer spinLevel;
	
	
	public void setSpinLevel(SpinLevelFlexContainer spinLevel) {
		this.spinLevel = spinLevel;
		getFlexContainerOrContainerOrSubscription().add(spinLevel);
	}
	
	public SpinLevelFlexContainer getSpinLevel() {
		this.spinLevel = (SpinLevelFlexContainer) getResourceByName(SpinLevelFlexContainer.SHORT_NAME);
		return spinLevel;
	}
	
	@XmlElement(name="spiLlAnnc", required=true, type=SpinLevelFlexContainerAnnc.class)
	private SpinLevelFlexContainerAnnc spinLevelAnnc;
	
	
	public void setSpinLevel(SpinLevelFlexContainerAnnc spinLevelAnnc) {
		this.spinLevelAnnc = spinLevelAnnc;
		getFlexContainerOrContainerOrSubscription().add(spinLevelAnnc);
	}
	
	public SpinLevelFlexContainerAnnc getSpinLevelAnnc() {
		this.spinLevelAnnc = (SpinLevelFlexContainerAnnc) getResourceByName(SpinLevelFlexContainerAnnc.SHORT_NAME);
		return spinLevelAnnc;
	}
	
}