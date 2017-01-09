/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.orange.basedriver.lifx.impl.lan.frame;

import java.net.InetAddress;
import java.util.Arrays;

public class LIFXGlobalFrame {

	private LIFXFrame frame;
	private LIFXFrameAddress frameAddress;
	private LIFXProtocolHeader protocolHeader;
	private LIFXPayload payload;
	private InetAddress remoteHost;
	private int remotePort = -1;
	
	

	public int getRemotePort() {
		return remotePort;
	}

	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}

	public InetAddress getRemoteHost() {
		return remoteHost;
	}

	public void setRemoteHost(InetAddress remoteHost) {
		this.remoteHost = remoteHost;
	}

	public LIFXPayload getPayload() {
		return payload;
	}
	
	

	public void setPayload(LIFXPayload payload) {
		this.payload = payload;
		getProtocolHeader().setType(payload.getType());
	}

	public LIFXFrame getFrame() {
		if (frame == null) {
			frame = new LIFXFrame();
		}
		return frame;
	}

	public void setFrame(LIFXFrame frame) {

		this.frame = frame;
	}

	public LIFXFrameAddress getFrameAddress() {
		if (frameAddress == null) {
			frameAddress = new LIFXFrameAddress();
		}
		return frameAddress;
	}

	public void setFrameAddress(LIFXFrameAddress frameAddress) {
		this.frameAddress = frameAddress;
	}

	public LIFXProtocolHeader getProtocolHeader() {
		if (protocolHeader == null) {
			protocolHeader = new LIFXProtocolHeader();
		}
		return protocolHeader;
	}

	public void setProtocolHeader(LIFXProtocolHeader protocolHeader) {
		this.protocolHeader = protocolHeader;
	}

	public byte[] getBytes() {
		
		int payloadSize = 0;
		if (payload != null) {
			payload.marshal();
			payloadSize = payload.getPayloadSize();
			getFrame().setPayloadSize(payloadSize);
		}
		byte[] globalFrame = new byte[36 + payloadSize];
		
		
		Arrays.fill(globalFrame, (byte) 0);
		System.arraycopy(frame.getBytes(), 0, globalFrame, 0, 8);
		System.arraycopy(frameAddress.getBytes(), 0, globalFrame, 8, 16);
		System.arraycopy(protocolHeader.getBytes(), 0, globalFrame, 24, 12);
		
		if (payloadSize != 0) {
			System.arraycopy(payload.getPayload(), 0, globalFrame, 36, payloadSize);
		}

		return globalFrame;
	}

	public void setBytes(byte[] globalFrame) throws Exception {

		getFrame().setBytes(Arrays.copyOfRange(globalFrame, 0, 8));
		getFrameAddress().setBytes(Arrays.copyOfRange(globalFrame, 8, 24));
		getProtocolHeader().setBytes(Arrays.copyOfRange(globalFrame, 24, 36));
		payload = LIFXPayloadFactory.getLIFXPayload(getProtocolHeader().getType(), Arrays.copyOfRange(globalFrame, 36, 36 + getFrame().getPayloadSize()));

	}

	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("LIFXGlobalFrame={");
		sb.append("\n");
		sb.append("\t remoteHost=");
		sb.append(getRemoteHost());
		sb.append("\n");
		sb.append("\t frame=");
		sb.append(getFrame());
		sb.append("\n");
		sb.append("\t frameAddress=");
		sb.append(getFrameAddress());
		sb.append("\n");
		sb.append("\t protocolHeader=");
		sb.append(getProtocolHeader());
		sb.append("\n");
		sb.append("\t payload=");
		sb.append(getPayload());
		sb.append("\n");
		sb.append("}");
		
		return sb.toString();
	}
}
