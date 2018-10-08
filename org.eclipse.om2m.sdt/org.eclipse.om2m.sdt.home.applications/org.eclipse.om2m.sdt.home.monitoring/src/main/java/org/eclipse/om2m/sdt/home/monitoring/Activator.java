/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.monitoring;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.interworking.service.InterworkingService;
import org.eclipse.om2m.sdt.home.monitoring.authentication.service.AuthenticationService;
import org.eclipse.om2m.sdt.home.monitoring.servlet.AuthenticationServiceServlet;
import org.eclipse.om2m.sdt.home.monitoring.servlet.CredentialsServlet;
import org.eclipse.om2m.sdt.home.monitoring.servlet.HomeServlet;
import org.eclipse.om2m.sdt.home.monitoring.servlet.InCseContextServlet;
import org.eclipse.om2m.sdt.home.monitoring.servlet.LoginServlet;
import org.eclipse.om2m.sdt.home.monitoring.servlet.LogoutServlet;
import org.eclipse.om2m.sdt.home.monitoring.servlet.MyHttpContext;
import org.eclipse.om2m.sdt.home.monitoring.util.AeRegistration;
import org.eclipse.om2m.sdt.home.monitoring.util.Constants;
import org.eclipse.om2m.sdt.home.monitoring.util.ResourceDiscovery;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;


@Component(name="home.monitoring.application", immediate=true,enabled=true)
public class Activator  {
	/** logger */
	private static Log LOGGER = LogFactory.getLog(Activator.class);
	public String globalContext = System.getProperty("org.eclipse.om2m.globalContext","");
	public static String uiContext = /*System.getProperty("org.eclipse.om2m.webInterfaceContext","/")*/"";
	public static String sep ="/";
	
	private ServiceRegistration<InterworkingService> sr;
	
	private HttpService httpService;
	private CseService sclService;
	private List<AuthenticationService> authenticationServices = new ArrayList<AuthenticationService>();
	

	@Activate()
	protected void activate(ComponentContext componentContext) {
		// at this point, we are sure to have an HTTPService & a CseService 
		// as they are both mandatory services
		
		if (uiContext.equals("/")) {
			sep="";
		}
		
		// register AE
		AeRegistration.getInstance().setCseService(sclService);
    	AeRegistration.getInstance().createAe();
    	sr = componentContext.getBundleContext().registerService(InterworkingService.class, AeRegistration.getInstance(), null);
		ResourceDiscovery.initCseService(sclService);
		
		// expose HomeMonitoring service through HTTP service
		try {
			LOGGER.info("Register test " + uiContext + sep + "Home_Monitoring_Application http context");
			HttpContext httpContext = new MyHttpContext(httpService.createDefaultHttpContext());
			
			httpService.registerServlet(uiContext + sep + Constants.APPNAME, 
					new HomeServlet(), null, httpContext);
			httpService.registerServlet(uiContext + sep + Constants.APPNAME + AuthenticationServiceServlet.SERVLET_PATH, 
					new AuthenticationServiceServlet(this), null, httpContext);
			httpService.registerServlet(uiContext + sep + Constants.APPNAME + "/in-cse/context", 
					new InCseContextServlet(), null, httpContext);
			httpService.registerServlet(uiContext + sep + Constants.APPNAME + "/security/login", 
					new LoginServlet(this), null, httpContext);
			httpService.registerServlet(uiContext + sep + Constants.APPNAME + "/security/cred", 
					new CredentialsServlet(), null, httpContext);
			httpService.registerServlet(uiContext + sep + Constants.APPNAME + "/security/logout", 
					new LogoutServlet(), null, httpContext);
			httpService.registerResources(uiContext + sep + Constants.APPNAME + "/webapps", 
					uiContext + sep + "webapps", httpContext);
		} catch (Exception e) {
			LOGGER.error("Error registring webapp servlet",e);
		}
	}
	
	@Deactivate
	protected void deactivate() {
		// unregister http servlet
		this.httpService.unregister(uiContext + sep + Constants.APPNAME);
		// delete ae
		sr.unregister();
		AeRegistration.getInstance().deleteAe();
		AeRegistration.getInstance().setCseService(null);
	}
	
	@Reference(cardinality=ReferenceCardinality.MANDATORY, policy=ReferencePolicy.DYNAMIC, unbind="unbindHttpService")
	protected void bindHttpService(HttpService pHttpService) {
		this.httpService = pHttpService;
	}
	protected void unbindHttpService(HttpService pHttpService) {
		this.httpService = null;
	}
	
	@Reference(cardinality=ReferenceCardinality.MANDATORY, policy=ReferencePolicy.DYNAMIC, unbind="unbindCseService")
	protected void bindCseService(CseService pCseService) {
		this.sclService = pCseService;
	}
	protected void unbindCseService(CseService pCseService) {
		this.sclService = null;
	}
	
	@Reference(cardinality=ReferenceCardinality.MULTIPLE, policy=ReferencePolicy.DYNAMIC, unbind="unbindAuthenticationService")
	protected void bindAuthenticationService(AuthenticationService authenticationService) {
		synchronized (authenticationServices) {
			authenticationServices.add(authenticationService);	
		}
	}
	protected void unbindAuthenticationService(AuthenticationService authenticationService) {
		synchronized (authenticationServices) {
			authenticationServices.remove(authenticationService);	
		}
	}
	
	public List<AuthenticationService> getAuthenticationServices() {
		List<AuthenticationService> toBeReturned = new ArrayList<>();
		synchronized (authenticationServices) {
			toBeReturned.addAll(authenticationServices);
		}
		return toBeReturned;
	}
	
}
