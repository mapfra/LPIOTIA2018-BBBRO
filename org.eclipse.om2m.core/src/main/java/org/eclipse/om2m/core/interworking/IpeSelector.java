package org.eclipse.om2m.core.interworking;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.om2m.interworking.service.InterworkingService;

/**
 * Contains the list of IPE registered to the core.
 * 
 */
public class IpeSelector {
	
	/** Map of Data Mapper Services */
	private static Map<String, InterworkingService> interworkingList = new HashMap<String, InterworkingService>();

	/**
	 * Return the interworking list of IPE
	 * @return list of IPE
	 */
	public static Map<String, InterworkingService> getInterworkingList(){
		return interworkingList;
	}
	
}
