package org.eclipse.om2m.sdt.home.types;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ObjectFactory;

public class FlexContainers {
	
	private static final String PREFIX = "create";
	private static final String SUFFIX = "Annc";
	
	static private ObjectFactory factory = ObjectFactory.getInstance();
	
	private static Map<String, String[]> flexNames;
	private static Map<String, String[]> dpNames;

	static {
		flexNames = new HashMap<String, String[]>();
		dpNames = new HashMap<String, String[]>();
		for (Method m : ObjectFactory.class.getMethods()) {
			String method = m.getName();
			if (method.endsWith(SUFFIX) || ! method.startsWith(PREFIX))
				continue;
			try {
				AbstractFlexContainer flex = (AbstractFlexContainer) m.invoke(factory, new Object[0]);
				flexNames.put(flex.getLongName(), new String[] { flex.getShortName(),
						flex.getContainerDefinition() });
				for (CustomAttribute ca : flex.getCustomAttributes()) {
					dpNames.put(ca.getLongName(), new String[] { ca.getShortName(), ca.getType() });
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static String getFlexShortName(String longName) {
		String[] ret = flexNames.get(longName);
		if (ret == null)
			throw new UnknownError("No valid flexContainer for " + longName);
		return ret[0];
	}
	
	public static String getContainerDefinition(String longName) {
		String[] ret = flexNames.get(longName);
		if (ret == null)
			throw new UnknownError("No valid flexContainer for " + longName);
		return ret[1];
	}
	
	public static String[] getDataPoint(String longName) {
		String[] ret = dpNames.get(longName);
		if (ret == null)
			throw new UnknownError("No valid flexContainer for " + longName);
		return ret;
	}

}
