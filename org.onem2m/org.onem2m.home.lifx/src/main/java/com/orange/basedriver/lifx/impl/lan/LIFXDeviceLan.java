/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.orange.basedriver.lifx.impl.lan;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

import com.orange.basedriver.lifx.LIFXDevice;
import com.orange.basedriver.lifx.impl.Logger;
import com.orange.basedriver.lifx.impl.lan.frame.LIFXGlobalFrame;
import com.orange.basedriver.lifx.impl.lan.frame.LIFXPayloadAcknowkledgement;
import com.orange.basedriver.lifx.impl.lan.frame.LIFXPayloadGetPower;
import com.orange.basedriver.lifx.impl.lan.frame.LIFXPayloadSetColor;
import com.orange.basedriver.lifx.impl.lan.frame.LIFXPayloadSetPower;
import com.orange.basedriver.lifx.impl.lan.frame.LIFXPayloadState;
import com.orange.basedriver.lifx.impl.lan.frame.LIFXPayloadStatePower;
import com.orange.basedriver.lifx.lan.LIFXDeviceListener;

public class LIFXDeviceLan extends LIFXDevice implements LIFXDeviceListener {

	final String macAddress;
	final byte[] macAddressAsArray;

	private final InetAddress inetAddress;
	int port = 56700;

	
	
	
	
	public LIFXDeviceLan(final String pMacAddress, final byte[] pMacAddressAsArray, final InetAddress pInetAddress) {
		super(pMacAddress);
		this.macAddress = pMacAddress;
		this.macAddressAsArray = pMacAddressAsArray;
		this.inetAddress = pInetAddress;
		
		Server.getInstance().addLIFXDeviceListener(macAddress, this);
	}
	
	
	/* (non-Javadoc)
	 * @see com.orange.basedriver.lifx.LIFXDevice#deleteDevice()
	 */
	public void deleteDevice() {
		Server.getInstance().removeLIFXDeviceListener(macAddress, this);
	}
	
	
	public String getMacAddress() {
		return macAddress;
	}

	public byte[] getMacAddressAsArray() {
		return macAddressAsArray;
	}

	public int getPort() {
		return port;
	}
	
	
	public double getHueRemotely() throws Exception {
		executeGetLightMessage();
		return super.getHue();
	}
	
	@Override
	public void setHue(double hue, double duration) throws Exception {
	}
	
	public double getSaturationRemotely() throws Exception {
		executeGetLightMessage();
		return super.getSaturation();
	}
	
	public double getBrightnessRemotely() throws Exception {
		executeGetLightMessage();
		return super.getBrightness();
	}
	
	public long getKelvinRemotely() throws Exception {
		executeGetLightMessage();
		return super.getKelvin();
	}
	
	public int getPowerRemotely() throws Exception {
		executeGetPowerMessage();
		return super.getPower();
	}
	
	public void setPower(int newPower, int duration) throws Exception {
		executeSetPowerMessage(newPower, duration);
	}
	
	@Override
	public void setLightState(int newPower, double newHue, double newSaturation, long newKelvin, double newBrightness, int duration)
			throws Exception {
		executeSetPowerMessage(newPower, duration);
		executeSetColorMessage((int)newHue, (int)newSaturation, (int)newBrightness, (int) newKelvin, duration);
	}

	public String getLabel() throws Exception {
		executeGetLightMessage();
		Logger.getInstance().info(LIFXDeviceLan.class, "getLabel() returns " + super.getLabel());
		return super.getLabel();
	}

	public void executeGetLightMessage() throws SocketException, UnknownHostException, IOException {

		LIFXGlobalFrame getFrame = new LIFXGlobalFrame();
		getFrame.setRemoteHost(inetAddress);
		getFrame.setRemotePort(port);
		getFrame.getFrame().setTagged(true);
		getFrame.getFrame().setSource(8);
		getFrame.getFrame().setPayloadSize(0);
		getFrame.getFrameAddress().setResRequired(true);
		getFrame.getProtocolHeader().setType(101);

		List<LIFXGlobalFrame> responses = Server.getInstance().sendLIFXGlobalFrameAndWaitForResponse(getFrame, 20000);

		if (!responses.isEmpty()) {
			Logger.getInstance().info(LIFXDeviceLan.class, "executeGetLightMessage() - response.size= " + responses.size() );
			LIFXGlobalFrame gb = responses.get(0);
			LIFXPayloadState lps = (LIFXPayloadState) gb.getPayload();
			notifyState(lps);
		} else {
			Logger.getInstance().info(LIFXDeviceLan.class, "executeGetLightMessage() - response is empty" ); 
		}

	}

	public void executeSetPowerMessage(int power, int duration) throws SocketException, UnknownHostException, IOException {

		LIFXGlobalFrame getFrame = new LIFXGlobalFrame();
		getFrame.setRemoteHost(inetAddress);
		getFrame.setRemotePort(port);
		getFrame.setPayload(new LIFXPayloadSetPower(power, duration));
		getFrame.getFrame().setPayloadSize(getFrame.getPayload().getPayloadSize());
		getFrame.getFrame().setTagged(true);
		getFrame.getFrame().setSource(8);
		getFrame.getFrameAddress().setAckRequired(true);
		
		getFrame.getProtocolHeader().setType(117);

		List<LIFXGlobalFrame> responses = Server.getInstance().sendLIFXGlobalFrameAndWaitForResponse(getFrame, 10000);

		if (!responses.isEmpty()) {
			LIFXGlobalFrame gb = responses.get(0);
			LIFXPayloadAcknowkledgement ack = (LIFXPayloadAcknowkledgement) gb.getPayload();
			if (ack != null) {
				super.setPower(power);
			}
		}

	}
	
	public void executeSetColorMessage(int hue, int saturation, int brightness, int kelvin, int duration) throws SocketException, UnknownHostException, IOException {

		LIFXGlobalFrame getFrame = new LIFXGlobalFrame();
		getFrame.setRemoteHost(inetAddress);
		getFrame.setRemotePort(port);
		getFrame.setPayload(new LIFXPayloadSetColor(hue, saturation, brightness, kelvin, duration));
		getFrame.getFrame().setTagged(true);
		getFrame.getFrame().setSource(8);
		getFrame.getFrameAddress().setAckRequired(true);
		

		List<LIFXGlobalFrame> responses = Server.getInstance().sendLIFXGlobalFrameAndWaitForResponse(getFrame, 10000);

		if (!responses.isEmpty()) {
			LIFXGlobalFrame gb = responses.get(0);
			LIFXPayloadAcknowkledgement ack = (LIFXPayloadAcknowkledgement) gb.getPayload();
			if (ack != null) {
				super.setHue(hue);
				super.setSaturation(saturation);
				super.setBrightness(brightness);
				super.setKelvin(kelvin);
			}
		}

	}
	
	public void executeGetPowerMessage() throws SocketException, UnknownHostException, IOException {

		LIFXGlobalFrame getFrame = new LIFXGlobalFrame();
		getFrame.setRemoteHost(inetAddress);
		getFrame.setRemotePort(port);
		getFrame.setPayload(new LIFXPayloadGetPower());
		getFrame.getFrame().setPayloadSize(getFrame.getPayload().getPayloadSize());
		getFrame.getFrame().setTagged(true);
		getFrame.getFrame().setSource(8);
		getFrame.getFrameAddress().setResRequired(true);

		List<LIFXGlobalFrame> responses = Server.getInstance().sendLIFXGlobalFrameAndWaitForResponse(getFrame, 10000);

		if (!responses.isEmpty()) {
			LIFXGlobalFrame gb = responses.get(0);
			LIFXPayloadStatePower statePower = (LIFXPayloadStatePower) gb.getPayload();
			notifyStatePower(statePower);
		}

	}

	/* (non-Javadoc)
	 * @see com.orange.basedriver.lifx.LIFXDevice#getInetAddress()
	 */
	public InetAddress getInetAddress() {
		return inetAddress;
	}

	@Override
	public void notifyStatePower(LIFXPayloadStatePower statePower) {
		super.setPower(statePower.getLevel());
	}

	@Override
	public void notifyState(LIFXPayloadState state) {
		super.setBrightness(state.getBrightness());
		super.setHue(state.getHue());
		super.setKelvin(state.getKelvin());
		super.setPower(state.getPower());
		super.setSaturation(state.getSaturation());
		super.setLabel(state.getLabel().trim());
		
	}

}
