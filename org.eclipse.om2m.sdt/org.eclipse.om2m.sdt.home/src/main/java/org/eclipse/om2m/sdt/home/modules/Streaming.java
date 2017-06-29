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
		super(name, domain, ModuleType.streaming.getDefinition(),
				ModuleType.streaming.getLongDefinitionName(),
				ModuleType.streaming.getShortDefinitionName());
		this.url = urlDP;
		this.url.setLongDefinitionType("url");
		this.url.setShortDefinitionType("url");
		this.url.setWritable(false);
		addDataPoint(url);
		
		this.login = loginDP;
		this.login.setLongDefinitionType("login");
		this.login.setShortDefinitionType("login");
		this.login.setWritable(false);
		addDataPoint(login);
		
		this.password = passwordDP;
		this.password.setLongDefinitionType("password");
		this.password.setShortDefinitionType("passd");
		this.password.setWritable(false);
		addDataPoint(password);
		
		this.format = formatDP;
		this.format.setLongDefinitionType("format");
		this.format.setShortDefinitionType("format");
		this.format.setWritable(false);
		addDataPoint(format);
		
	}

	public Streaming(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, (StringDataPoint) dps.get("url"),
				(StringDataPoint) dps.get("login"),
				(StringDataPoint) dps.get("passd"),
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
	
	public String getFormatValue()  throws DataPointException, AccessException {
		return format.getValue();
	}

}
