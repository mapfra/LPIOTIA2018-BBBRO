package org.eclipse.om2m.sdt.home.modules;

import java.util.Map;

import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.datapoints.StringDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.types.ModuleType;

public class Streaming extends Module {

	private final StringDataPoint url;
	private final StringDataPoint login;
	private final StringDataPoint password;
	private final StringDataPoint format;

	public Streaming(String name, Domain domain, final StringDataPoint urlDP,
			final StringDataPoint loginDP, final StringDataPoint passwordDP, 
			final StringDataPoint formatDP) {
		super(name, domain, ModuleType.streaming.getDefinition());
		this.url = urlDP;
		addDataPoint(url);
		this.login = loginDP;
		addDataPoint(login);
		this.password = passwordDP;
		addDataPoint(password);
		this.format = formatDP;
		addDataPoint(format);
		
		url.setWritable(false);
		login.setWritable(false);
		password.setWritable(false);
		format.setWritable(false);
	}

	public Streaming(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, (StringDataPoint) dps.get("url"),
				(StringDataPoint) dps.get("login"),
				(StringDataPoint) dps.get("password"),
				(StringDataPoint) dps.get("format"));
	}
	
	public String getUrlValue() throws DataPointException, AccessException {
		return url.getValue();
	}
	
	public String getLoginValue() throws DataPointException, AccessException {
		return login.getValue();
	}
	
	public String getPasswordValue() throws DataPointException, AccessException {
		return password.getValue();
	}

}
