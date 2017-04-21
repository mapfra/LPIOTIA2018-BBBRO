package org.eclipse.om2m.sdt.home.smarterkettle.communication;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class TCPConnection {
	
	private static String address = "";

	private static int port = 2081;

	private Socket socket;

	private boolean waitForResponse = true;  // should be set true if ACK or Status expected
	
	/*public TCPConnection(String address, int port){
		
		this.address = address;
		this.port = port;
		
	}*/
	

	public void sendTCPPacket(byte[] bytes2send){
		sendTCPPacket(bytes2send, this.address, this.port);
		
	}
	
	
	public void sendTCPPacket(byte[] bytes2send, String address, int port){
		try {
			socket = new Socket(address, port);
			
			sendBytes(bytes2send, socket);
			
			if(!waitForResponse ){
				socket.close();
			}else{
				/*if(bytes2send[0] == SmarterCoffeeCommands.HEADER_START){
					
				}
				else{*/
					readBytes();
				//}
				//socket.close();
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	 byte[] buffer = new byte[1024];
	    int charsRead = 0;
	    if(socket!= null){
	    	InputStream in = this.socket.getInputStream();
	    	
	    	
	    	while(!statusMsg){
	    		int k = 0;
	    		int iterator = 0;
	    		while ((charsRead = in.read(buffer)) != -1)
	    			
	    		
	    
	    
	    		{	
	    			
	    			iterator++;
	    			
	    			if(charsRead == 3) break;  //ACK received
	    			if(charsRead > 3) statusMsg = true; //Status received
	    		
	    				for(int i = 0; i < charsRead; i++){
	    					if(i == 2)
	    						System.out.print( "Temp ( " + i + ")" +": " + Byte.toUnsignedInt(buffer[i]) + " | ");
	    					else
	    						System.out.print( i +": " + Byte.toUnsignedInt(buffer[i]) + " | ");	        
	    				}
	    				System.out.println(" ");
	    		
	    			if(buffer[charsRead-1] == SmarterKettleCommands.END_OF_MESSAGE) break; 
	    		   
	    			
	    		
	    		}
	    		
	    	}
	    		in.close();
	    }
	    byte[] data = new byte[charsRead];
	    System.arraycopy(buffer, 0, data, 0, charsRead);
	    	 
    return data;
}

public byte[] checkStatus(){
	
	byte[] result = new byte[7];
	
	try {
		socket = new Socket(address, port);
		result = readBytes();
		return result;
		//socket.close();
		
	} catch (UnknownHostException e) {

		e.printStackTrace();
	} catch (IOException e) {

		e.printStackTrace();
	}
	
	return result;
	
	
}


public static String getAddress() {
	return address;
}


public static void setAddress(String address) {
	TCPConnection.address = address;
}


public boolean isWaitForResponse() {
	return waitForResponse;
}


public void setWaitForResponse(boolean waitForResponse) {
	this.waitForResponse = waitForResponse;
}

public static int getPort() {
	return port;
}


public static void setPort(int port) {
	TCPConnection.port = port;
}



}
