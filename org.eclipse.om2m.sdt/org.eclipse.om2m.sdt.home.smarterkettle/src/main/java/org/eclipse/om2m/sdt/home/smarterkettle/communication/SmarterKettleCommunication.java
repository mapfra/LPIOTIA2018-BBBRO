/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.smarterkettle.communication;

import javax.net.ssl.SSLEngineResult.Status;

public class SmarterKettleCommunication {
	
	
	public SmarterKettleStatus kettleStatus;
	
	public SmarterKettleCommunication(String ip, int port) {
		TCPConnection.setAddress(ip);
		TCPConnection.setPort(port);
		
		kettleStatus = new SmarterKettleStatus();
	}


	public void startKettle(int temperature){
		TCPConnection tcp = new TCPConnection();
		tcp.setWaitForResponse(true);;
		
		byte[] request = new byte[4];
		
		request[0] = SmarterKettleCommands.START_KETTLE;
		request[1] = (byte)temperature;
		request[2] = (byte)0;
		request[3] = SmarterKettleCommands.END_OF_MESSAGE;
		
		System.out.println("sent: " + "0:" + request[0] + "| 1: " + request[1] + "| 2: " + request[2] + "| 3: " + request[3] + "|");
		
		tcp.sendTCPPacket(request);	
		
		
		
	}
	
	public void startKettle(){
		TCPConnection tcp = new TCPConnection();
		tcp.setWaitForResponse(true);;
		
		byte[] request = new byte[4];
		
		int temperature = kettleStatus.getTargetTemperature();
		
		request[0] = SmarterKettleCommands.START_KETTLE;
		request[1] = (byte)temperature;
		request[2] = (byte)0;
		request[3] = SmarterKettleCommands.END_OF_MESSAGE;
		
		System.out.println("sent: " + "0:" + request[0] + "| 1: " + request[1] + "| 2: " + request[2] + "| 3: " + request[3] + "|");
		
		tcp.sendTCPPacket(request);	
		
		
		
	}
	
	public void stopKettle(){
		TCPConnection tcp = new TCPConnection();
		tcp.setWaitForResponse(true);;
		
		byte[] request = new byte[2];
		
		request[0] = SmarterKettleCommands.STOP_KETTLE;
		request[1] = SmarterKettleCommands.END_OF_MESSAGE;
		
		
		System.out.println("sent: " + "0:" + request[0] + "| 1: " + request[1] + "|");
		tcp.sendTCPPacket(request);
		
		
			
	}
	
	public void checkDeviceInfo(){
		TCPConnection tcp = new TCPConnection();
		tcp.setWaitForResponse(true);
		
		byte[] request = new byte[1];
		//request[0] = SmarterKettleCommands.CHECK_STATUS;
		request[0] = SmarterKettleCommands.END_OF_MESSAGE;
		
		System.out.println("sent: " + "0:" + request[0] + "|");
		
		tcp.sendTCPPacket(request);
		
		
	}
	
	public void sheduleTest(){
		TCPConnection tcp = new TCPConnection();
		tcp.setWaitForResponse(true);
		
		byte[] request = new byte[1];
		request[0] = SmarterKettleCommands.SHEDULE_TEST;
		System.out.println("sent: " + "0:" + request[0] + "|");
		tcp.sendTCPPacket(request);
	}
	
	
	
	
	public void checkStatus(){//Water level and current temperature are available only when kettle isPlugged.
		TCPConnection tcp = new TCPConnection();
		byte[] statusAnswer = new byte[7];
		statusAnswer = tcp.checkStatus();	
		
		kettleStatus.setCurrentTemperature(Byte.toUnsignedInt(statusAnswer[2]));
		kettleStatus.setWaterLevel(Byte.toUnsignedInt(statusAnswer[4]));	
		
		
		
		
		if(statusAnswer[1] == 0)
			kettleStatus.setBoiling(false);
		else 
			kettleStatus.setBoiling(true);
		
		if(statusAnswer[3] == (int)8)
			kettleStatus.setPlugged(true);
		else
			kettleStatus.setPlugged(false);
		
		kettleStatus.setWaterLevel(Byte.toUnsignedInt(statusAnswer[4]));
		kettleStatus.setWaterLevelEnum(Byte.toUnsignedInt(statusAnswer[4]));
		
		
		System.out.println("STATUS -------- Czy gotuje: " + kettleStatus.isBoiling());
		System.out.println("Czy stoi na podstawie: " + kettleStatus.isPlugged());
		System.out.println("Ile wody: " + kettleStatus.getWaterLevel());
		System.out.println("Aktualna temperatura: " + kettleStatus.getCurrentTemperature());
		System.out.println("Ile wody nazwa: " + kettleStatus.getWaterLevelName());
		
		
		
		
		
		
		
		
		
		
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

}
