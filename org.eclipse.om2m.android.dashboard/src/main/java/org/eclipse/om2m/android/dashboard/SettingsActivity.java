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
package org.eclipse.om2m.android.dashboard;

import java.util.Locale;

import org.eclipse.om2m.android.dashboard.tools.SettingsManager;

import org.eclipse.om2m.android.dashboard.R;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class SettingsActivity extends CustomSecondaryActivity 
	implements OnClickListener, OnCheckedChangeListener {

	private Button btnValidate;
	private Button btnCancel;
	private CheckBox cbWizards;
	private RadioButton rbLanguageEn;
	private RadioButton rbLanguageFr;	
	private TextView tvTitle;
	private TextView tvVersion;
	private EditText etCSEHostname;
	private EditText etCSEPort;
	private EditText etCSEId;
	private EditText etCSEName;
	private EditText etCSELogin;
	private EditText etCSEPwd;
	
	private RadioButton rbDeviceNameWithAlias;
	private RadioButton rbDeviceName;

	private String language = "";
	private String currentLanguage = "";

	// ================================================================================

	// =====================================================================================
	// Lifecycle
	// =====================================================================================

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.settings_activity);
		SettingsManager settings = SettingsManager.getInstance(this);
		currentLanguage = settings.getLanguage();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		tvTitle = (TextView)findViewById(R.id.tv_title);
		tvVersion = (TextView)findViewById(R.id.tv_version);
		// Custom input filter to validate IP adress 
		InputFilter[] filters = new InputFilter[1];
	    filters[0] = new InputFilter() {
	        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
	            if (end > start) {
	                String destTxt = dest.toString();
	                String resultingTxt = destTxt.substring(0, dstart) 
	                		+ source.subSequence(start, end) 
	                		+ destTxt.substring(dend);
	                if (! resultingTxt.matches ("^\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3})?)?)?)?)?)?")) { 
	                    return "";
	                }
	                String[] splits = resultingTxt.split("\\.");
	                for (int i = 0; i < splits.length; i++) {
	                	if (Integer.valueOf(splits[i]) > 255) {
	                		return "";
	                	}
	                }
	            }
	            return null;
	        }
	    };
	    
		btnValidate = (Button)findViewById(R.id.btn_settings_validate);
		btnCancel = (Button)findViewById(R.id.btn_settings_cancel);
		cbWizards = (CheckBox)findViewById(R.id.cb_settings_wizards);
		rbLanguageEn = (RadioButton)findViewById(R.id.rd_language_en);
		rbLanguageFr = (RadioButton)findViewById(R.id.rd_language_fr);
		
		etCSEHostname = (EditText) findViewById(R.id.et_settings_cse_hostname);
		etCSEHostname.setFilters(filters);
		etCSEPort = (EditText) findViewById(R.id.et_settings_cse_port);
		etCSEId = (EditText) findViewById(R.id.et_settings_cse_cseId);
		etCSEName = (EditText) findViewById(R.id.et_settings_cse_cseName);
		etCSELogin = (EditText) findViewById(R.id.et_settings_cse_login);
		etCSEPwd = (EditText) findViewById(R.id.et_settings_cse_pwd);

		btnValidate.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		rbLanguageEn.setOnCheckedChangeListener(this);	
		rbLanguageFr.setOnCheckedChangeListener(this);	
		
		rbDeviceName = (RadioButton) findViewById(R.id.rb_device_name_with_name);
		rbDeviceNameWithAlias = (RadioButton) findViewById(R.id.rb_device_name_with_alias);
		rbDeviceName.setOnCheckedChangeListener(this);
		rbDeviceNameWithAlias.setOnCheckedChangeListener(this);
		
		if (settings.getDeviceName().equals(SettingsManager.DEVICE_NAME_WITH_ALIAS)) {
			rbDeviceNameWithAlias.setChecked(true);
		} else {
			rbDeviceName.setChecked(true);
		}
		

		if (currentLanguage.equals("fr")) {
			rbLanguageFr.setChecked(true);
		} else {
			rbLanguageEn.setChecked(true);
		}	
		
		etCSEHostname.setText(settings.getCSEHostname());
		etCSEPort.setText(settings.getCSEPort());
		etCSEId.setText(settings.getCSEId());
		etCSEName.setText(settings.getCSEName());
		etCSELogin.setText(settings.getCSELogin());
		etCSEPwd.setText(settings.getCSEPwd());
	}

	//        ||
	//        ||
	//        ||
	//        \/

	@Override
	protected void onStart() {
		super.onStart();
	}

	//        ||
	//        ||
	//        ||
	//        \/

	@Override
	protected void onResume() {
		super.onResume();

		language = SettingsManager.getInstance(this).getLanguage();
		Resources res = getApplication().getResources();
		Configuration config = res.getConfiguration();
		config.locale = new Locale(language);
		res.updateConfiguration(config, null);

		tvTitle.setText(R.string.settings_title);
		cbWizards.setText(R.string.settings_activateWizard);

		btnValidate.setText(R.string.dialog_btn_validate);
		btnCancel.setText(R.string.dialog_btn_cancel);

		try {
			String versionName = getApplicationContext().getPackageManager()
				.getPackageInfo(getApplicationContext().getPackageName(), 0).versionName;
			String versionLabel = getResources().getString(R.string.settings_version)
				.replace("%version%", versionName);
			tvVersion.setText(versionLabel);
		} catch(Exception ignored) {
		}
	}

	//        ||
	//        ||
	//        ||
	//        \/

	@Override
	protected void onPause() {
		super.onPause();
	}

	//        ||
	//        ||
	//        ||
	//        \/

	@Override
	protected void onStop() {
		super.onStop();
	}

	//        ||
	//        ||
	//        ||
	//        \/

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}


	// =====================================================================================
	// User actions
	// =====================================================================================

	@Override
	public void onClick(View v) {
		Button butClicked = (Button)v;
		final SettingsManager settings = SettingsManager.getInstance(SettingsActivity.this);

		if (butClicked == btnValidate) {
			if (cbWizards.isChecked()) {
				settings.setWizardApplicationSimple(true);
				settings.setWizardApplicationDetails(true);
				settings.setWizardDeviceDetails(true);
				settings.setWizardDeviceSimple(true);
				settings.setWizardConfig(true);
			}

			if (! language.isEmpty()) {
				settings.setLanguage(language);
			}
			
			settings.setCSEHostname(etCSEHostname.getText().toString());
			settings.setCSEPort(etCSEPort.getText().toString());
			settings.setCSEId(etCSEId.getText().toString());
			settings.setCSEName(etCSEName.getText().toString());
			settings.setCSELogin(etCSELogin.getText().toString());
			settings.setCSEPwd(etCSEPwd.getText().toString());
		}
		else if (butClicked == btnCancel) {
			if (! currentLanguage.isEmpty()) {
				settings.setLanguage(currentLanguage);
			}
		}

		if ((butClicked == btnCancel) || (butClicked == btnValidate)) {
			finish();
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (! isChecked)
			return;
		RadioButton butClicked = (RadioButton)buttonView;
		if ((butClicked == rbLanguageEn) || (butClicked == rbLanguageFr)) {
			language = (butClicked == rbLanguageEn) ? "en" : "fr";
			SettingsManager.getInstance(SettingsActivity.this).setLanguage(language);
			onResume();
		}
		
		if ((butClicked == rbDeviceName) || (butClicked == rbDeviceNameWithAlias)) {
			String configDeviceName = (butClicked == rbDeviceName) ? SettingsManager.DEVICE_NAME_WITH_NAME  : SettingsManager.DEVICE_NAME_WITH_ALIAS;
			SettingsManager.getInstance(SettingsActivity.this).setDeviceName(configDeviceName);
			onResume();
		}
	}
	
}
