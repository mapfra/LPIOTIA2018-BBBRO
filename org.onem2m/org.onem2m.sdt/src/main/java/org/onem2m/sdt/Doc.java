package org.onem2m.sdt;

public class Doc {
	
	private String text;

	public Doc(final String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
		return "<Doc>" + text + "</Doc>";
	}

}
