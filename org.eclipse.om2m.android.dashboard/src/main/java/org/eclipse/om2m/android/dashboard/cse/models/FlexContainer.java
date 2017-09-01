/*******************************************************************************
 * Copyright (c) 2013, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    BAREAU Cyrille <cyrille.bareau@orange.com>, 
 *    BONNARDEL Gregory <gbonnardel.ext@orange.com>, 
 *    BOLLE Sebastien <sebastien.bolle@orange.com>.
 *******************************************************************************/
package org.eclipse.om2m.android.dashboard.cse.models;

import java.util.List;

public abstract class FlexContainer extends Resource {
	
	private Integer ty;
	private String pi;
	private String ct;
	private String lt;
	private List<String> lbl;
	private String et;
	private String cnd;

	public Integer getTy() {
		return ty;
	}
	public void setTy(Integer ty) {
	
		this.ty = ty;
	}
	
	public String getPi() {
		return pi;
	}
	
	public void setPi(String pi) {
		this.pi = pi;
	}
	
	public String getCt() {
		return ct;
	}
	
	public void setCt(String ct) {
		this.ct = ct;
	}
	
	public String getLt() {
		return lt;
	}
	
	public void setLt(String lt) {
		this.lt = lt;
	}
	
	public List<String> getLbl() {
		return lbl;
	}
	
	public void setLbl(List<String> lbl) {
		this.lbl = lbl;
	}
	
	public String getEt() {
		return et;
	}
	
	public void setEt(String et) {
		this.et = et;
	}
	
	public String getCnd() {
		return cnd;
	}
	
	public void setCnd(String cnd) {
		this.cnd = cnd;
	}
	
}
