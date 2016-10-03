package org.eclipse.om2m.binding.mqtt.util;

import org.eclipse.om2m.commons.resource.ResponsePrimitive;

public final class Utils {

	public static void fillPrimitiveContent(){
		
	}
	
	public static void fillContent(ResponsePrimitive requestPrimitive){
		if(requestPrimitive.getPrimitiveContent() != null && 
				!requestPrimitive.getPrimitiveContent().getAny().isEmpty() && 
				requestPrimitive.getContent() == null){
			requestPrimitive.setContent(requestPrimitive.getPrimitiveContent().getAny().get(0));
		}
	}
	
}
