package org.onem2m.sdt;

import java.util.Collection;

public class Element {
	
	protected String name;
	
	private Doc doc;

	public Element(final String name) {
		if ((name == null) || name.equals(""))
			throw new IllegalArgumentException("Name cannot be null or empty");
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(final Doc doc) {
		this.doc = doc;
	}

	public void setDoc(final String text) {
		this.doc = new Doc(text);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		return ((obj == null) || (getClass() != obj.getClass())) ? false 
				: name.equals(((Element) obj).name);
	}
	
	@Override
	public String toString() {
		return "<" + getClass().getSimpleName() + " name=\"" + name + "\"/>";
	}
	
	public String prettyPrint() {
		return prettyPrint("");
	}
	
	protected String prettyPrint(String tab) {
		return tab + "<" + getClass().getSimpleName() + " name=\"" + name + "\"/>";
	}
	
	protected void prettyPrint(StringBuffer ret, Collection<? extends Element> items, 
			String title, String t1) {
		if (! items.isEmpty()) {
			String t2 = t1 + "\t";
			ret.append("\n").append(t1).append("<" + title + ">");
			for (Element e : items) {
				ret.append("\n");
				ret.append(e.prettyPrint(t2));
			}
			ret.append("\n").append(t1).append("</" + title + ">");
		}
	}
	
}
