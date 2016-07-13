package org.onem2m.sdt;

public class Extended {

	private String domain;
	private String clazz;
	
	Extended(final String domain, final String clazz) {
		if ((domain == null) || (clazz == null)) 
			throw new IllegalArgumentException();
		this.clazz = clazz;
		this.domain = domain;
	}

	public String getDomain() {
		return domain;
	}

	public String getClazz() {
		return clazz;
	}

}
