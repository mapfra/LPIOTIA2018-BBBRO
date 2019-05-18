package org.eclipse.om2m.commons.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

import org.eclipse.om2m.commons.resource.CustomAttribute;

public class CustomAttributList extends ArrayList<CustomAttribute> {
	
	@Override
	public boolean add(CustomAttribute e) {
		
		// check if customAttribute does not exist
		CustomAttribute existing = getFirstByShortName(e.getShortName());
		if (existing != null) {
			existing.setValue(e.getValue());
			return true;
		} else {
			return super.add(e);
		}
		
		
	}
	
	private CustomAttribute getFirstByShortName(String shortName) {
		// check provided shortName
		if (shortName == null) {
			// no need to iterate over the list
			return null;
		}
		for(CustomAttribute ca : this) {
			if (shortName.equals(ca.getShortName())) {
				return ca;
			}
		}
		
		// custom attribute not found
		return null;
	}

}
