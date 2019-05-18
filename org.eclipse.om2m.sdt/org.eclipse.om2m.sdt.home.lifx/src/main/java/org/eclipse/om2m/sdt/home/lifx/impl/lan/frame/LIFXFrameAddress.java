/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.lifx.impl.lan.frame;

import java.util.Arrays;

public class LIFXFrameAddress {

	private static int CURRENT_SEQUENCE_NUMBER = 0;

	private static synchronized int getNextSequenceNumber() {
		int toBeReturned = CURRENT_SEQUENCE_NUMBER;
		CURRENT_SEQUENCE_NUMBER++;
		if (CURRENT_SEQUENCE_NUMBER == 256) {
			CURRENT_SEQUENCE_NUMBER = 0;	
		}
		return toBeReturned;
	}

	private byte[] target;
	private boolean ackRequired = false;

	public boolean isAckRequired() {
		return ackRequired;
	}

	public void setAckRequired(boolean ackRequired) {
		this.ackRequired = ackRequired;
	}

	public boolean isResRequired() {
		return resRequired;
	}

	public void setResRequired(boolean resRequired) {
		this.resRequired = resRequired;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	private boolean resRequired = false;
	private int sequenceNumber = 0;

	public byte[] getTarget() {
		return target;
	}

	public String getTargetAsString() {
		if (target != null) {
			StringBuffer sb = new StringBuffer();
			for (byte b : target) {
				sb.append(Integer.toHexString((int) b & 0xff));
				sb.append(":");
			}
			sb.setLength(sb.length() - 1);
			return sb.toString();
		}
		return null;
	}

	public void setTarget(byte[] target) {
		this.target = target;
	}

	public byte[] getBytes() {
		byte[] frameAddress = new byte[16];
		Arrays.fill(frameAddress, (byte) 0);

		// target (6 bits)
		if (target != null) {
			frameAddress[0] = target[0];
			frameAddress[1] = target[1];
			frameAddress[2] = target[2];
			frameAddress[3] = target[3];
			frameAddress[4] = target[4];
			frameAddress[5] = target[5];

		}

		// 000000 bits + ack_required (1 bit) + res_required (1 bit)
		frameAddress[14] = (byte) ((ackRequired ? 1 : 0) << 1 | (resRequired ? 1 : 0));

		sequenceNumber = getNextSequenceNumber();
		frameAddress[15] = (byte) (sequenceNumber & 0xff);

		return frameAddress;
	}

	public void setBytes(byte[] frameAddress) throws Exception {
		if (frameAddress.length != 16) {
			throw new Exception("expecting 16 bytes, found " + frameAddress.length + " bytes");
		}

		target = Arrays.copyOfRange(frameAddress, 0, 6);

		// ack_required
		ackRequired = ((frameAddress[14] >> 1) & 0x1) == 1;
		resRequired = (frameAddress[14] & 0x1) == 1;

		// sequence number
		sequenceNumber = (frameAddress[15] & 0xff);
	}

	@Override
	public String toString() {
		return "frameAddress(target=" + getTargetAsString() + ", ack_required=" + ackRequired + ", res_required="
				+ resRequired + ", sequenceNumber=" + sequenceNumber + ")";
	}

}
