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
package org.eclipse.om2m.android.dashboard.applications;

import java.util.Locale;

import org.eclipse.om2m.android.dashboard.cse.OneM2MListener;
import org.eclipse.om2m.android.dashboard.cse.OneM2MRequester;
import org.eclipse.om2m.android.dashboard.cse.OneM2MRequest.OneM2MReqType;
import org.eclipse.om2m.android.dashboard.cse.models.OneM2MApplication;
import org.eclipse.om2m.android.dashboard.cse.requests.OneM2MRequestParams;
import org.eclipse.om2m.android.dashboard.tools.SettingsManager;

import org.eclipse.om2m.android.dashboard.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.webkit.HttpAuthHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SetJavaScriptEnabled")
public class ApplicationConfigActivity extends Activity implements OneM2MListener {
	
	static private final String PARAM = "___RES___";
	static private final String MSG_TEMPLATE = "<html><head><style type=\"text/css\">" + 
			"#maindiv {width:100%; height:100%; text-align:center; vertical-align:middle; border:1px solid #be1f2e;}" +
			"#content {width:80%; height:80p%; margin-left:auto; margin-right:auto; position:relative; top:40%;}" +	
			"h3 {color:#be1f2e; text-align:center; vertical-align:middle}" + 
			"p {color:black; text-align:center; vertical-align:middle}" + 
			"</style></head>" +
			"<body><div id=\"maindiv\"><div id=\"content\"><h3>" + 
			PARAM + 
			"</h3></div></div></body></html>";

	private WebView configContentView;
	private TextView configTitle;

	private String applicationId;
	private OneM2MApplication application;
	
	// =====================================================================================
	// Lifecycle
	// =====================================================================================

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			applicationId = extras.getString("applicationId");				
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		String language = SettingsManager.getInstance(this).getLanguage();
		Resources res = getApplication().getResources();
		Configuration config = res.getConfiguration();
		config.locale = new Locale(language);
		res.updateConfiguration(config, null);

		setContentView(R.layout.application_config_activity);
		
		configTitle = (TextView)findViewById(R.id.tv_application_config_title);
		configContentView = (WebView)findViewById(R.id.wv_application_config_container);

		// Retrieve the application
		OneM2MRequester.getInstance().sendRequest(OneM2MReqType.APPLICATION, 
				new OneM2MRequestParams(applicationId), getApplicationContext(), this);

		setBanner("");
	}

	private void createWebView() {
		setBanner(application.getApn());
		final String name = SettingsManager.getInstance(this).getCSELogin();
		final String password = SettingsManager.getInstance(this).getCSEPwd();
		final String url = application.getPresentationUrl() 
				+ "?name=" + name + "&password=" + password;

		configContentView.setWebViewClient(new WebViewClient () {
			public void onReceivedHttpAuthRequest(WebView view,
					HttpAuthHandler handler, String host, String realm) {
				try {
					if (handler.useHttpAuthUsernamePassword()) {
						handler.proceed(name, password);
					} else {
	            		setBanner("Error authentication.");
						String content = MSG_TEMPLATE.replaceFirst(PARAM, 
								getResources().getString(R.string.application_details_no_access_rights));
						view.loadData(content, "text/html", "UTF-8");
					}
            	} catch (Exception e) {
            		setBanner("Error authentication: " + e.getMessage());
					String content = MSG_TEMPLATE.replaceFirst(PARAM, 
							getResources().getString(R.string.application_details_no_access_rights)) + ":" + e.getMessage();
					view.loadData(content, "text/html", "UTF-8");
				}
			}
		});
		configContentView.getSettings().setJavaScriptEnabled(true);
		configContentView.resumeTimers();

		configContentView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
				if (title.contains("404")) {
					String content = MSG_TEMPLATE.replaceFirst(PARAM, 
							getResources().getString(R.string.application_details_no_config));
					view.loadData(content, "text/html", "UTF-8");
				} else if (title.contains("401")) {
					if (title.contains("_OTB_reset_User__")) {
						view.loadUrl(url);
					} else {
						String content = MSG_TEMPLATE.replaceFirst(PARAM, 
								getResources().getString(R.string.application_details_no_access_rights));
						view.loadData(content, "text/html", "UTF-8");
					}
				}
			}
		});
		configContentView.loadUrl(url);
	}
	
	private final void setBanner(String msg) {
		configTitle.setText(getResources().getString(R.string.application_config_title)
				.replace("%app_name%", msg));
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i(getClass().getName(), "onDestroy");
		configContentView.clearHistory();
		configContentView.clearCache(true);
		configContentView.loadUrl("about:blank");
		configContentView.freeMemory(); 
		configContentView.pauseTimers();
		configContentView = null;
	}

	@Override
	public void onOneM2MResponse(OneM2MReqType requestType, Object response) {
		if (requestType == OneM2MReqType.APPLICATION) {
			application = (OneM2MApplication)response;
			if (application != null)
				createWebView();
		}
	}

	@Override
	public void onOneM2MError(OneM2MReqType requestType, String msg) {
		Toast.makeText(this, "getApplication " + msg, Toast.LENGTH_LONG).show();
	}
	
}
