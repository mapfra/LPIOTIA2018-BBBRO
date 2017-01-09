/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.home.smartercoffee.communication;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.onem2m.home.smartercoffee.Activator;

public class TCPConnection {
	
	private String address = "";

	private int port = 2081;

	private Socket socket;

	private boolean waitForResponse = true;  // set true if ACK or Status expected
	
	private boolean printResponse = false; // only for debug purposes
	
	public TCPConnection(String address, int port){
		
		this.address = address;
		this.port = port;
		
	}
	

	public byte[] sendTCPPacket(byte[] bytes2send){ //TODO add return the status 
		return sendTCPPacket(bytes2send, this.address, this.port);
		
	}
	
	
	public byte[] sendTCPPacket(byte[] bytes2send, String address, int port){ // TODO add return status
			byte[] toRet = null;
		try {
			socket = new Socket(address, port);
			sendBytes(bytes2send, socket);
			
			if(!waitForResponse ){
				socket.close();
				return null;
			}else{
				toRet = readBytes();
				socket.close();
				return toRet;
			}
			
		} catch (UnknownHostException e) {

			e.printStackTrace();
		} catch (IOException e) {
	
			e.printStackTrace();
		}
		return toRet;
	}



	public void sendBytes(byte[] bytes2send, Socket socket) throws IOException {
		sendBytes(bytes2send, 0, bytes2send.length, socket);
	}

	public void sendBytes(byte[] bytes2send, int start, int len, Socket socket) throws IOException {
		if (len < 0)
			throw new IllegalArgumentException("Negative length not allowed");
		if (start < 0 || start >= bytes2send.length)
			throw new IndexOutOfBoundsException("Out of bounds: " + start);

		if(socket != null){
			OutputStream out = socket.getOutputStream(); 
			DataOutputStream dos = new DataOutputStream(out);

    		//dos.writeInt(len);
			if (len > 0) {
    			dos.write(bytes2send, start, len);
    		}
    	
    	//dos.close();
    	//out.close();
		}
    
	}

	public byte[] readBytes() throws IOException {
		boolean statusMsg = false;
		Activator.logger.debug("Read bytes ");
		byte[] buffer = new byte[1024];
	    int charsRead = 0;
	    if(socket!= null){
	    	InputStream in = this.socket.getInputStream();
	    	while(!statusMsg){
	    		while ((charsRead = in.read(buffer)) != -1)
	    		{	 
	    			if(charsRead == 3) break; //ACK received
	    			if(charsRead > 3) statusMsg = true; //Status received
	    			if(buffer[charsRead-1] == SmarterCoffeeCommands.END_OF_MESSAGE) break; 
	    		   
	    		}
	    	}
	    		in.close();
	    }
	    byte[] data = new byte[charsRead];
	    System.arraycopy(buffer, 0, data, 0, charsRead);
	    	 
	    return data;
	}

	public byte[] checkStatus(){
		byte[] toRet = null;
		try {
			socket = new Socket(address, port);
			toRet = readBytes();
			socket.close();
		
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return toRet;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public boolean isWaitForResponse() {
		return waitForResponse;
	}


	public void setWaitForResponse(boolean waitForResponse) {
		this.waitForResponse = waitForResponse;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}