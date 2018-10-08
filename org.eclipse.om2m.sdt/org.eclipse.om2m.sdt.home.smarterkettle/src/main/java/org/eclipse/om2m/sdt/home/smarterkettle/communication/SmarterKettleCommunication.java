/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.smarterkettle.communication;

import static org.eclipse.om2m.sdt.home.smarterkettle.Activator.LOGGER;

public class SmarterKettleCommunication {
	
	private SmarterKettleStatus kettleStatus;
	private String ip;
	private int port;
	
	public SmarterKettleCommunication(String ip, int port) {
		this.ip = ip;
		this.port = port;
		kettleStatus = new SmarterKettleStatus();
	}

	public void startKettle(int temperature){
		byte[] request = new byte[4];
		request[0] = SmarterKettleCommands.START_KETTLE;
		request[1] = (byte)temperature;
		request[2] = (byte)0;
		request[3] = SmarterKettleCommands.END_OF_MESSAGE;
		send(request);
	}
	
	public void startKettle() {
		startKettle(kettleStatus.getTargetTemperature());
	}
	
	public void stopKettle() {
		byte[] request = new byte[2];
		request[0] = SmarterKettleCommands.STOP_KETTLE;
		request[1] = SmarterKettleCommands.END_OF_MESSAGE;
		send(request);
	}
	
	public void checkDeviceInfo() {
		byte[] request = new byte[1];
		//request[0] = SmarterKettleCommands.CHECK_STATUS;
		request[0] = SmarterKettleCommands.END_OF_MESSAGE;
		send(request);
	}
	
	public void sheduleTest() {
		byte[] request = new byte[1];
		request[0] = SmarterKettleCommands.SHEDULE_TEST;
		send(request);
	}
	
	public void checkStatus() {
		//Water level and current temperature are available only when kettle isPlugged.
		TCPConnection tcp = new TCPConnection(ip, port);
		byte[] statusAnswer = tcp.checkStatus();	
		
		kettleStatus.setCurrentTemperature(Byte.toUnsignedInt(statusAnswer[2]));
		kettleStatus.setBoiling(statusAnswer[1] != 0);
		kettleStatus.setPlugged(statusAnswer[3] == 8);
		kettleStatus.setWaterLevel(Byte.toUnsignedInt(statusAnswer[4]));
	}

	public Boolean getFaultDetection() {
		return kettleStatus.getFaultDetection();
	}

	public Integer getCode() {
		return kettleStatus.getCode();
	}

	public String getDescription() {
		return kettleStatus.getDescription();
	}
	
	public SmarterKettleStatus getStatus() {
		return kettleStatus;
	}

	private void send(byte[] request) {
		TCPConnection tcp = new TCPConnection(ip, port);
		tcp.setWaitForResponse(true);
		LOGGER.info("send: 0:" + request[0] + "| 1: " + request[1] + "| 2: " + request[2] + "| 3: " + request[3] + "|");
		tcp.sendTCPPacket(request);	
	}
	
}
