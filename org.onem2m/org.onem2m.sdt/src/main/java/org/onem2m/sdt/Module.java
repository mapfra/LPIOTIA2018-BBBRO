package org.onem2m.sdt;

public class Module extends ModuleClass {
	
	private String definition;

	public Module(final String name, final Domain domain, final String definition) {
		super(definition + "__" + name, domain);
		this.definition = definition;
	}
	
	public String getDefinition() {
		return definition;
	}

}
