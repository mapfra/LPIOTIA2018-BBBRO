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
package org.eclipse.om2m.android.dashboard.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class DashboardWebViewClient extends WebViewClient {

	private Context context;
	
	private int loadCount = 0;

	public DashboardWebViewClient(Context context) {
		super();
		this.context = context;
	}

	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		Log.d("OTBWebViewClient", "onPageStarted(url=" + url + ")");
		view.setVisibility(View.VISIBLE);
	}

	@Override
	public void onPageFinished(WebView view, String url) {
		Log.d("OTBWebViewClient", "onPageFinished(url=" + url + ")");
		if (loadCount == 0) {
			Log.d("OTBWebViewClient", "loadCount == 0 ==> execute customJavascript");
			view.loadUrl("javascript:document.getElementsByName('j_username')[0].value = '"
//					+ OTBSettingsManager.getInstance(this.context).getOASLogin() + "';"
					+ "javascript:document.getElementsByName('j_password')[0].value = '"
//					+ OTBSettingsManager.getInstance(this.context).getOASPwd() + "';"
					+ "javascript:document.getElementsByName('logon')[0].click();");
			loadCount++;
		}
		
		view.setVisibility(View.VISIBLE);
		final Animation fade = new AlphaAnimation(0.0f, 1.0f);
		fade.setDuration(200);
		view.startAnimation(fade);
		view.setVisibility(View.VISIBLE);
	}

	@Override
	public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
		Toast.makeText(view.getContext(), "Authentication Error", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onLoadResource(WebView view, String url) {
		Log.d("OTBWebViewClient", "onLoadResource(url=" + url + ")");
	}

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		return super.shouldOverrideUrlLoading(view, url);
	}

	@Override
	public void onReceivedHttpAuthRequest(WebView view,
			final HttpAuthHandler handler, final String host, final String realm) {
		Log.d("OTBWebViewClient", "onReceivedHttpAuthRequest");
//		handler.proceed(OTBSettingsManager.getInstance(this.context).getOASLogin(), 
//				OTBSettingsManager.getInstance(this.context).getOASPwd());
	}

	@Override
	public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
		handler.proceed();
	}

}
