/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.ipe.sdt.testsuite.module;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.DiscoveryResult;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.resource.URIList;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.ipe.sdt.testsuite.CSEUtil;
import org.eclipse.om2m.ipe.sdt.testsuite.TestReport;
import org.eclipse.om2m.ipe.sdt.testsuite.TestReport.State;
import org.eclipse.om2m.ipe.sdt.testsuite.module.exception.FlexContainerNotFound;
import org.onem2m.sdt.Module;

public abstract class AbstractModuleTest {

	private final CseService cseService;
	private final Module module;
	private String moduleUrl = null;

	public static List<String> getListFromStringArray(final String array) {
		// remove first and last character
		String toBeTransformed = array;
		toBeTransformed = toBeTransformed.substring(1, toBeTransformed.length() - 1);

		if (toBeTransformed.length() > 0) {

			String[] values = toBeTransformed.split(",");
			for(int i = 0; i < values.length; i++) {
				values[i] = values[i].trim(); 
			}
			return Arrays.asList(values);
		} else {
			return new ArrayList<String>();
		}
	}

	public AbstractModuleTest(final CseService pCseService, final Module pModule) {
		this.cseService = pCseService;
		this.module = pModule;
		try {
			this.moduleUrl = getModuleFlexContainerUrl();
		} catch (FlexContainerNotFound e) {
		}
	}

	protected boolean checkModuleUrl(TestReport report) {
		if (moduleUrl == null) {
			report.setErrorMessage("unable to find out flexContainer for module " + module.getName());
			report.setState(State.KO);
			return false;
		}
		return true;
	}

	protected String getModuleUrl() {
		return moduleUrl;
	}

	protected Module getModule() {
		return module;
	}

	protected CseService getCseService() {
		return cseService;
	}

	protected String getModuleFlexContainerUrl() throws FlexContainerNotFound {
		System.out.println("getModuleFlexContainerUrl() - module=" + module.getName());
		String url = null;

		List<String> labels = new ArrayList<>();
		labels.add("name/" + module.getName());
		// labels.add("device.id/" + module.getOwner().getId());

		ResponsePrimitive responsePrimitive = CSEUtil.discovery(cseService, labels,
				"/" + Constants.CSE_ID + "/" + Constants.CSE_NAME);
		if (responsePrimitive.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
			URIList discoveryResult = (URIList) responsePrimitive.getContent();
			if (discoveryResult.getListOfUri().size() == 1) {
				url = discoveryResult.getListOfUri().get(0);
			} else {
				System.out.println("too much discoveryResult = " + discoveryResult.getListOfUri().size());
			}
		}

		if (url == null) {
			System.out.println("getModuleFlexContainerUrl() - module=" + module.getName() + " - NOT FOUND");

			throw new FlexContainerNotFound();
		}

		System.out.println("getModuleFlexContainerUrl() - module=" + module.getName() + " - url =" + url);
		return url;
	}

	/**
	 * Test the module.
	 * 
	 * @return true if test is ok
	 */
	public List<TestReport> launchTests() {
		List<TestReport> reports = new ArrayList<>();

		// retrieve all test method and execute it
		Method[] methods = this.getClass().getMethods();
		for (Method method : methods) {
			if ((method.getName().startsWith("test")) && (method.getReturnType() == TestReport.class)) {
				TestReport report = null;
				try {
					report = (TestReport) method.invoke(this, null);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					report = new TestReport("Test module " + module.getName());
					report.setErrorMessage(
							"exception when invoking method " + method.getName() + " : " + e.getMessage());
					report.setState(State.KO);
					report.setException(e);
				}
				reports.add(report);
			}
		}

		return reports;
	}

	/**
	 * Check if two object are equal
	 * 
	 * @param o1
	 *            first object
	 * @param o2
	 *            second obkect
	 * @param report
	 *            TestReport object to fulfill
	 * @param attributeName
	 *            name of the tested attribute
	 * @return true if equal else false
	 */
	protected boolean checkObject(final Object o1, final Object o2, TestReport report, String attributeName) {

		if (o1 == null) {
			if (o2 != null) {
				report.setErrorMessage("attribute " + attributeName + ": o1 is null and o2 is not null");
				report.setState(State.KO);
				return false;
			}
		} else {

			if (o1 instanceof List<?>) {
				// list case
				if (o2 != null) {
					if (((List) o1).size() != ((List) o2).size()) {
						report.setErrorMessage("attribute " + attributeName + ": o1 list  (" + o1 + ", o1.size="
								+ ((List) o1).size() + ")  has a different size from o2 (" + o2 + ", o2.size="
								+ ((List) o2).size() + ")");
						report.setState(State.KO);
						return false;
					}
					
					for(Object o : ((List) o1)) {
						if (!((List) o2).contains(o)) {
							report.setErrorMessage("attribute " + attributeName + ": o1 list  (" + o1 + ", o1.size="
									+ ((List) o1).size() + ")  missing element compared to o2 (" + o2 + ", o1.size="
									+ ((List) o2).size() + ")");
							report.setState(State.KO);
							return false;
						}
					}
				} else {
					// o2 == null
					report.setErrorMessage("attribute " + attributeName + ": o1 is a list (" + o1 + ", o1.class="
							+ o1.getClass() + ")  and o2 is null");
					report.setState(State.KO);
					return false;
				}

			} else {

				// o1 is not null
				if (!o1.equals(o2)) {
					report.setErrorMessage("attribute " + attributeName + ": o1 (" + o1 + ", o1.class=" + o1.getClass()
							+ ") is not equal to o2(" + o2 + ", o2.class=" + o2.getClass() + ")");
					report.setState(State.KO);
					return false;
				}
			}
		}

		return true;
	}

}
