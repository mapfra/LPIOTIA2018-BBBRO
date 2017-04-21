/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.smartercoffee.communication;

import java.util.Date;

import org.eclipse.om2m.sdt.home.smartercoffee.Activator;
import org.eclipse.om2m.sdt.home.types.TasteStrength;

public class SmarterCoffeeCommunication {
	
	private static final String IP = "10.0.1.25";
	
	private static final int PORT = 2081;
	
	private String ip;
	
	private int port = 0;
	
	//TODO status
	
	private SmarterCoffeeStatus status;
	
	
	
	public SmarterCoffeeCommunication (String ip, int port){
		if(ip != null && port != 0){
			this.ip = ip;
			this.port = port;		
		}
		else{
			this.ip = IP;
			this.port = PORT;
		}
		status = new SmarterCoffeeStatus();
	}
	
	public boolean getFaultDetection(){
		return status.getFaultDetection();
	}
	
	public int getCode(){
		return status.getCode();
	}
	
	public String getDescription(){
		return status.getDescription();
	}
	
	public void start(boolean useGrinder, int numberOfCups, int sdtStrength, boolean keepWarm){ 
		
		byte strength = 0;
		if(sdtStrength >= TasteStrength.zero && sdtStrength < TasteStrength.medium){
			strength = SmarterCoffeeCommands.BREW_STRENGTH_0;
		}
		else if(sdtStrength == TasteStrength.medium){
			strength = SmarterCoffeeCommands.BREW_STRENGTH_1;
		}
		else if (sdtStrength > TasteStrength.medium && sdtStrength <= TasteStrength.maximum){
			strength = SmarterCoffeeCommands.BREW_STRENGTH_2;
		}
		
		TCPConnection tcp = new TCPConnection(this.ip, this.port);
		tcp.setWaitForResponse(true);
		byte[] request = new byte[6];
		request[0] = SmarterCoffeeCommands.HEADER_START;
		request[1] = (byte) numberOfCups;
		request[2] = (byte) strength;
		request[3] = (byte) (keepWarm?5:0);
		request[4] = (byte) (useGrinder?1:0);
		request[5] = (byte) SmarterCoffeeCommands.END_OF_MESSAGE;
		detectCoffeeReady(tcp.sendTCPPacket(request)); 
	}
	
	public synchronized void detectCoffeeReady(final byte[] dataToParse){
		
		
		new Thread(new Runnable() {
			boolean brewingInProgress = true;
			boolean isFirst = true;
			
			@Override
			public void run() {
				while(brewingInProgress){
					Activator.logger.debug("Check coffee ready Thread...");
					if(isFirst){
						status.parseStatus(dataToParse);
						isFirst = false;
					}
					if(status.getFaultDetection()){
						brewingInProgress = false;
					}
					else{
						TCPConnection tcp = new TCPConnection(ip, port);
						status.parseStatus(tcp.checkStatus());
					}
			
					if(status.isCoffeeReady()) {
						brewingInProgress = false; 
						Activator.logger.debug("Coffee is ready!");
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		
		
	}
	
	public int getCoffeeReadyStatus(){
		
		return status.getCoffeePreparationStatus();
	}
	
	public int getWaterStatus(){
		return status.getWaterLevel();
	}
	
	public boolean getKeepWarmStatus(){
		return status.getKeepWarm();
	}
	
	
	public void start(){ 
		TCPConnection tcp = new TCPConnection(this.ip, this.port);
		tcp.setWaitForResponse(true);
		byte[] request = new byte[2];
		request[0] = SmarterCoffeeCommands.HEADER_STARTX;
		request[1] = (byte)SmarterCoffeeCommands.END_OF_MESSAGE;
		status.parseStatus(tcp.sendTCPPacket(request));
	}
	
	public void getStatus(){ 
		TCPConnection tcp = new TCPConnection(this.ip, this.port);
		tcp.setWaitForResponse(true);
		byte[] request = new byte[2];
		request[0] = SmarterCoffeeCommands.HEADER_STATUS;
		request[1] = (byte)SmarterCoffeeCommands.END_OF_MESSAGE;
		status.parseStatus(tcp.sendTCPPacket(request));
	}
	
	public void stop(){		
	}
	
	public void setBrewStrength(int strength){
		TCPConnection tcp = new TCPConnection(this.ip, this.port);
		tcp.setWaitForResponse(true);
		byte[] request = new byte[3];
		request[0] = SmarterCoffeeCommands.HEADER_SETSTR;
		request[1] = (byte)strength;
		request[2] = (byte)SmarterCoffeeCommands.END_OF_MESSAGE;
		status.parseStatus(tcp.sendTCPPacket(request));
		
	}
	
	public void setNumberOfCups(int number){
		TCPConnection tcp = new TCPConnection(this.ip, this.port);
		tcp.setWaitForResponse(true);
		byte[] request = new byte[3];
		request[0] = SmarterCoffeeCommands.HEADER_SETCUPS;
		request[1] = (byte)number;
		request[2] = (byte)SmarterCoffeeCommands.END_OF_MESSAGE;
		status.parseStatus(tcp.sendTCPPacket(request));
		
	}
	
	
	public void tooggleGrinder(){
		TCPConnection tcp = new TCPConnection(this.ip, this.port);
		tcp.setWaitForResponse(true);
		byte[] request = new byte[2];
		request[0] = SmarterCoffeeCommands.HEADER_GRINDTGGL;
		request[1] = (byte)SmarterCoffeeCommands.END_OF_MESSAGE;
		status.parseStatus(tcp.sendTCPPacket(request));
		
	}
	
	public void setHotPlateOn(int minutes){
		TCPConnection tcp = new TCPConnection(this.ip, this.port);
		tcp.setWaitForResponse(true);
		byte[] request = new byte[3];
		request[0] = SmarterCoffeeCommands.HEADER_HOTPLATEON;
		request[1] = (byte) minutes;
		request[2] = (byte)SmarterCoffeeCommands.END_OF_MESSAGE;
		status.parseStatus(tcp.sendTCPPacket(request));
	}
	
	
	public void setHotPlateOff(){
		

		TCPConnection tcp = new TCPConnection(this.ip, this.port);
		tcp.setWaitForResponse(true);
		byte[] request = new byte[2];
		request[0] = SmarterCoffeeCommands.HEADER_HOTPLATEOFF;
		request[1] = (byte)SmarterCoffeeCommands.END_OF_MESSAGE;
		status.parseStatus(tcp.sendTCPPacket(request));
	}
	
	public void setTime(Date date){  //Calendar??
		
		
	}
	
	public void reset(){

		TCPConnection tcp = new TCPConnection(this.ip, this.port);
		tcp.setWaitForResponse(true);
		byte[] request = new byte[2];
		request[0] = SmarterCoffeeCommands.HEADER_RESET;
		request[1] = (byte)SmarterCoffeeCommands.END_OF_MESSAGE;
		status.parseStatus(tcp.sendTCPPacket(request));
		
	}
	
	public void checkStatus(){
		TCPConnection tcp = new TCPConnection(this.ip, this.port);
		tcp.checkStatus();
	}

	

}
