package org.onem2m.home.devices;

import org.onem2m.home.modules.MotionSensor;
import org.onem2m.home.modules.PersonSensor;
import org.onem2m.home.modules.Streaming;
import org.onem2m.home.types.DeviceType;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.Module;

public class Camera extends GenericDevice {

	private MotionSensor motionSensor;
	private PersonSensor personSensor;
	private Streaming streaming;
	
	public Camera(String id, String serial, Domain domain) {
		super(id, serial, DeviceType.deviceCamera, domain);
	}
	
	public void addModule(Module module) {
		if (module instanceof MotionSensor)
			setMotionSensor((MotionSensor)module);
		else if (module instanceof PersonSensor)
			setPersonSensor((PersonSensor)module);
		else if (module instanceof Streaming)
			setStreaming((Streaming)module);
		else 
			super.addModule(module);
	}

	public void setMotionSensor(MotionSensor pMotionSensor) {
		this.motionSensor = pMotionSensor;
		super.addModule(motionSensor);
	}
	
	public MotionSensor getMotionSensor() {
		return motionSensor;
	}
	
	public void setPersonSensor(PersonSensor pPersonSensor) {
		this.personSensor = pPersonSensor;
		super.addModule(personSensor);
	}
	
	public PersonSensor getPersonSensor() {
		return personSensor;
	}
	
	public void setStreaming(Streaming pStreaming) {
		this.streaming = pStreaming;
		super.addModule(streaming);
	}
		
	public Streaming getStreaming() {
		return streaming;
	}

}
