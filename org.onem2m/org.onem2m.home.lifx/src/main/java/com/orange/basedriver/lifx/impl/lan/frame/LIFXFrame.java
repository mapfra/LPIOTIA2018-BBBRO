/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.orange.basedriver.lifx.impl.lan.frame;

public class LIFXFrame {

	private int payloadSize;
	private boolean tagged;
	private int source;
	private int size;
	
	
	
	public int getSize() {
		return size;
	}
	public int getPayloadSize() {
		return payloadSize;
	}
	public void setPayloadSize(int size) {
		this.payloadSize = size;
	}
	
	public boolean isTagged() {
		return tagged;
	}
	public void setTagged(boolean tagged) {
		this.tagged = tagged;
	}

	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	
	public byte[] getBytes() {
		byte[] frame = new byte[8];
		
		int finalSize = payloadSize + 8 /* frame */ + 16 /* frame address */ + 12 /* protocol header */;
		
		// size
		frame[1] = (byte) ((finalSize >> 8) & 0xFF) ;
		frame[0] = (byte) (finalSize & 0xFF);
		
		// origin (value = 0, 2 bits) + tagged (1 bit) + addressable (1 bit) + protocol  (4 bits)
		frame[3] = (byte) (((tagged ? 1 : 0) << 5) | (1 << 4) | (1024 >> 8));
		
		// protocol (end - 8 bits)
		frame[2] = 1024 & 0xf;
		
		// source (32 bits - 4 bytes)
		frame[4] = (byte) ((source >> 24) & 0xff) ;
		frame[5] = (byte) ((source >> 16) & 0xff);
		frame[6] = (byte) ((source >> 8) & 0xff);
		frame[7] = (byte) (source & 0xff);
		
		return frame;
		
	}
	
	public void setBytes(byte[] frame) throws Exception {
		
		if (frame.length != 8) {
			throw new Exception("expecting 8 bytes, found " + frame.length + " bytes");
		}
		
		size =  frame[0] + (frame[1] << 8);
		payloadSize = size - 8 - 16 -12;
		
		tagged = (((frame[3] >> 5) & 0x1) == 1) ;
		
		source = (frame[4] << 24) + (frame[5] << 16) + (frame[6] << 8) + frame[7];
		
		
	}
	
	@Override
	public String toString() {
		return "frame(size= " + size + ", payloadSize=" + payloadSize + ", isTagged=" + tagged + ", source=" + source + ")";
	}
}
