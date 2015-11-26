package org.eclipse.om2m.ipe.sample.controller;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.obix.Bool;
import org.eclipse.om2m.commons.obix.Obj;
import org.eclipse.om2m.commons.obix.io.ObixDecoder;
import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.ipe.sample.RequestSender;
import org.eclipse.om2m.ipe.sample.constants.SampleConstants;
import org.eclipse.om2m.ipe.sample.gui.GUI;
import org.eclipse.om2m.ipe.sample.model.Lamp;
import org.eclipse.om2m.ipe.sample.model.SampleModel;
import org.eclipse.om2m.ipe.sample.util.ObixUtil;

public class LifeCycleManager {

	private static Log LOGGER = LogFactory.getLog(LifeCycleManager.class); 

	/**
	 * Handle the start of the plugin with the resource representation and the GUI
	 */
	public static void start(){
		Map<String, Lamp> lamps = new HashMap<String, Lamp>();
		for(int i=0; i<2; i++) {
			String lampId = Lamp.TYPE+"_"+i;
			lamps.put(lampId, new Lamp(lampId, false));
		}
		SampleModel.setModel(lamps);

		// register the IPE
		if(registerAE()){
			// Case when the AE has just been created
			// Create initial resources for the 2 lamps
			for(int i=0; i<2; i++) {
				String lampId = Lamp.TYPE+"_"+i;
				createLampResources(lampId, false, SampleConstants.POA);
			}
			createLampAll(SampleConstants.POA);			
		} else {
			// AE and other resources already created
			ResponsePrimitive response = RequestSender.getRequest("/" + Constants.CSE_ID + "/" +Constants.CSE_NAME + "/" + SampleConstants.AE_NAME + "/"+ SampleConstants.LAMP_0 +"/DATA/la");
			if(response.getResponseStatusCode().equals(ResponseStatusCode.OK) && response.getContent() instanceof ContentInstance){
				ContentInstance lamp0Value = (ContentInstance) response.getContent();
				Obj lamp0 = ObixDecoder.fromString(lamp0Value.getContent());
				for(Object obj : lamp0.getObjGroup()){
					if(obj instanceof Bool 
							&& ((Bool) obj).getName().equals("state")){
						SampleModel.setLampState(SampleConstants.LAMP_0, ((Bool) obj).getVal());
						break;
					}
				}
			}
			response = RequestSender.getRequest("/" + Constants.CSE_ID + "/" +Constants.CSE_NAME + "/" + SampleConstants.AE_NAME + "/"+ SampleConstants.LAMP_1 +"/DATA/la");
			if(response.getResponseStatusCode().equals(ResponseStatusCode.OK) && response.getContent() instanceof ContentInstance){
				ContentInstance lamp1Value = (ContentInstance) response.getContent();
				Obj lamp1 = ObixDecoder.fromString(lamp1Value.getContent());
				for(Object obj : lamp1.getObjGroup()){
					if(obj instanceof Bool 
							&& ((Bool) obj).getName().equals("state")){
						SampleModel.setLampState(SampleConstants.LAMP_1, ((Bool) obj).getVal());
						break;
					}
				}
			}
		}
		
		// Start the GUI
		if(SampleConstants.GUI){
			GUI.init();
		}
		
	}

	/**
	 * Stop the GUI if it is present
	 */
	public static void stop(){
		if(SampleConstants.GUI){
			GUI.stop();
		}
	}

	/**
	 * Register the IPE as an AE in the platform
	 * @return 
	 * if the registration has been performed, otherwise, there is an error or it's already created
	 */
	private static boolean registerAE(){
		AE ae = new AE();
		ae.setRequestReachability(true);
		ae.getPointOfAccess().add(SampleConstants.POA);
		ae.setAppID("IPE_" + SampleConstants.POA.toUpperCase());
		//		ResponsePrimitive response = SampleController.CSE.doRequest(request);
		ResponsePrimitive response = RequestSender.createAE(ae, SampleConstants.AE_NAME);
		LOGGER.info("Response for IPE registration : " + response);
		if(response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)
				&& response.getContent() instanceof AE){
			AE createdAe = (AE) response.getContent();
			SampleController.AE_ID = createdAe.getAEID();
			LOGGER.info("AEID of the createed AE: " + SampleController.AE_ID);
			return true;
		}
		return false;
	}

	/**
	 * Creates all required resources.
	 * @param cntId - Application ID
	 * @param initValue - initial lamp value
	 * @param aPoCPath - lamp aPocPath
	 */
	private static void createLampResources(String cntId, boolean initValue, String aPoCPath) {
		// Create the Application resource
		Container container = new Container();
		container.getLabels().add("lamp");
		container.setMaxNrOfInstances(BigInteger.valueOf(0));

		ResponsePrimitive response = RequestSender.
				createContainer("/" + Constants.CSE_ID + "/" +Constants.CSE_NAME + "/" + SampleConstants.AE_NAME, cntId, container);

		// Create Application sub-resources only if application not yet created
		if(response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			container = new Container();
			container.setMaxNrOfInstances(BigInteger.valueOf(10));
			// Create DESCRIPTOR container sub-resource
			LOGGER.info(RequestSender.createContainer(response.getLocation(), SampleConstants.DESC, container));
			// Create STATE container sub-resource
			LOGGER.info(RequestSender.createContainer(response.getLocation(), SampleConstants.DATA, container));

			String content;
			// Create DESCRIPTION contentInstance on the DESCRIPTOR container resource
			content = ObixUtil.getDescriptorRep(SampleConstants.CSE_ID, cntId, SampleConstants.DATA);
			ContentInstance contentInstance = new ContentInstance();
			contentInstance.setContent(content);
			contentInstance.setContentInfo(MimeMediaType.OBIX);
			RequestSender.createContentInstance(
					SampleConstants.CSE_ID + "/" +Constants.CSE_NAME + "/" + SampleConstants.AE_NAME + "/" + cntId + "/" + SampleConstants.DESC, null, contentInstance);

			// Create initial contentInstance on the STATE container resource
			content = ObixUtil.getStateRep(cntId, initValue);
			contentInstance.setContent(content);
			RequestSender.createContentInstance(
					SampleConstants.CSE_ID + "/" +Constants.CSE_NAME + "/" + SampleConstants.AE_NAME + "/" + cntId + "/" + SampleConstants.DATA, null, contentInstance);
		}
	}

	/**
	 * Create the LAMP_ALL container
	 * @param apocpath2
	 */
	private static void createLampAll(String apocpath2) {
		// Creation of the LAMP_ALL container
		Container cnt = new Container();
		cnt.getLabels().add("lamp");
		String targetId = SampleConstants.CSE_ID + "/" +Constants.CSE_NAME + "/" + SampleConstants.AE_NAME;
		RequestSender.createContainer(targetId, "LAMP_ALL",	cnt);

		// Creation of the DESCRIPTOR container
		cnt.setMaxNrOfInstances(BigInteger.valueOf(10));
		RequestSender.createContainer(targetId + "/" + "LAMP_ALL", SampleConstants.DESC, cnt);
		
		// Create the description
		ContentInstance cin = new ContentInstance();
		cin.setContent(ObixUtil.createLampAllDescriptor());
		cin.setContentInfo(MimeMediaType.OBIX);
		RequestSender.createContentInstance(targetId + "/" + "LAMP_ALL" + "/" + SampleConstants.DESC, null, cin);
	}

}
