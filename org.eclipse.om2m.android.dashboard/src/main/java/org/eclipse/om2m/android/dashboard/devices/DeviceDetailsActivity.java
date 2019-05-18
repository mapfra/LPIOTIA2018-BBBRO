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
package org.eclipse.om2m.android.dashboard.devices;

import java.util.Locale;
import java.util.Map;

import org.eclipse.om2m.android.dashboard.CustomSecondaryActivity;
import org.eclipse.om2m.android.dashboard.cse.OneM2MListener;
import org.eclipse.om2m.android.dashboard.cse.OneM2MRequester;
import org.eclipse.om2m.android.dashboard.cse.OneM2MRequest.OneM2MReqType;
import org.eclipse.om2m.android.dashboard.cse.models.SDTDevice;
import org.eclipse.om2m.android.dashboard.cse.models.SDTModule;
import org.eclipse.om2m.android.dashboard.cse.requests.OneM2MRequestParams;
import org.eclipse.om2m.android.dashboard.tools.SettingsManager;
import org.eclipse.om2m.android.dashboard.utils.OTBUtils;

import org.eclipse.om2m.android.dashboard.R;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class DeviceDetailsActivity extends CustomSecondaryActivity 
	implements OnClickListener, DialogInterface.OnClickListener, OneM2MListener {

	private TextView tvDeviceDetailsTitle;
	private TextView tvDeviceDetailsModel;
	private TextView tvDeviceDetailsProtocol;
	private TextView tvDeviceDetailsCnd;
	private TextView tvDeviceDetailsManufacturer;
	private TextView tvDeviceDetailsLocation;
	private TextView tvDeviceDetailsSerialNumber;
	private TextView tvDeviceDetailsDeviceName;

	private LinearLayout llDevDetailsModules;
	private LinearLayout llDeviceDetailsModel;
	private LinearLayout llDeviceDetailsLocation;
	
	private SDTDevice device;
	private String deviceRi;
		
//	private String newLocation;

	// =====================================================================================
	// Lifecycle
	// =====================================================================================

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			deviceRi = extras.getString("deviceRi");			
		}		
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

		String language = SettingsManager.getInstance(this).getLanguage();
		Resources res = getApplication().getResources();
		Configuration config = res.getConfiguration();
		config.locale = new Locale(language);
		res.updateConfiguration(config, null);

		setContentView(R.layout.device_details_activity);

		tvDeviceDetailsSerialNumber = (TextView) findViewById(R.id.tv_device_details_serialNumber);
		tvDeviceDetailsDeviceName = (TextView) findViewById(R.id.tv_device_details_deviceName);
		tvDeviceDetailsTitle = (TextView)findViewById(R.id.tv_device_details_title);
		tvDeviceDetailsModel = (TextView)findViewById(R.id.tv_device_details_model);
		tvDeviceDetailsProtocol = (TextView)findViewById(R.id.tv_device_details_protocol);
		tvDeviceDetailsManufacturer = (TextView)findViewById(R.id.tv_device_details_manufacturer);
		tvDeviceDetailsCnd = (TextView) findViewById(R.id.tv_device_details_cnd);
		tvDeviceDetailsLocation = (TextView)findViewById(R.id.tv_device_details_location);
		llDevDetailsModules = (LinearLayout) findViewById(R.id.ll_device_details_modules);
		llDeviceDetailsModel = (LinearLayout) findViewById(R.id.ll_device_details_model);
		llDeviceDetailsLocation = (LinearLayout) findViewById(R.id.ll_device_details_location);


		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		if (deviceRi != null) {
			OneM2MRequester.getInstance().sendRequest(OneM2MReqType.DEVICE, 
					new OneM2MRequestParams(deviceRi), 
					getApplicationContext(), this);
			// Retrieve the current rights
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch(item.getItemId()) {
		case R.id.menu_help:
			if (SettingsManager.getInstance(this).isWizardDeviceDetailsActivated()) {
				SettingsManager.getInstance(this).setWizardDeviceDetails(false);
			} else {
				SettingsManager.getInstance(this).setWizardDeviceDetails(true);
			}
			break;
		}
		return true;
	}


	
	// =====================================================================================
	// User actions
	// =====================================================================================

	@Override
	public void onClick(View v) {
//		Button btnClicked = (Button)v;
//		if (btnClicked == btnLocation) {
//			if (etDeviceDetailsLocation.isActivated()) {
//				newLocation = etDeviceDetailsLocation.getText().toString();
//			} else {
//				etDeviceDetailsLocation.setActivated(true);
//				etDeviceDetailsLocation.setVisibility(View.VISIBLE);
//				etDeviceDetailsLocation.setText(device.getLocation());
//				btnLocation.setText(getResources().getString(R.string.dialog_btn_ok));
//			}
//		}
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
//		if (which == DialogInterface.BUTTON_POSITIVE) {						
//			// send update
//			for (String appId : appIdsToSet) {
//				OTBHabRequestParam_PostPermissionsUpdate params = new OTBHabRequestParam_PostPermissionsUpdate(appId);
//				params.setDeviceIdsToSet(Collections.singletonList(this.deviceId));
//			}
//			
//			for (String appId : appIdsToRemove) {
//				OTBHabRequestParam_PostPermissionsUpdate params = new OTBHabRequestParam_PostPermissionsUpdate(appId);
//				params.setDeviceIdsToRemove(Collections.singletonList(this.deviceId));
//			}
//		}	
	}

	// =====================================================================================
	// CSE listener
	// =====================================================================================

	@Override
	public void onOneM2MResponse(OneM2MReqType requestType, Object response) {
		if (requestType == OneM2MReqType.DEVICE) {
			device = (SDTDevice)response;
			tvDeviceDetailsTitle.setText(device.getDeviceAliasName());
			String m = null;
			Object model = device.getModel();
			if (model != null) {
				if (model instanceof Map<?,?>) {
					if (! ((Map<?,?>)model).isEmpty()) {
						m = model.toString();
					}
				} else {
					m = model.toString();
				}
			}
			if (OTBUtils.isEmpty(m))
				((LinearLayout)llDeviceDetailsModel.getParent()).removeView(llDeviceDetailsModel);
			else
				tvDeviceDetailsModel.setText(m);
			
			tvDeviceDetailsProtocol.setText(device.getProtocol());
			tvDeviceDetailsCnd.setText(device.getCnd());
			tvDeviceDetailsManufacturer.setText(device.getManufacturer());
			tvDeviceDetailsSerialNumber.setText(device.getSerialNumber());
			tvDeviceDetailsDeviceName.setText(OTBUtils.isEmpty(device.getDeviceName()) 
					? device.getRn() : device.getDeviceName());

			String location = device.getLocation();
			if (OTBUtils.isEmpty(location))
				((LinearLayout)llDeviceDetailsLocation.getParent()).removeView(llDeviceDetailsLocation);
			else
				tvDeviceDetailsLocation.setText(location);

			for (SDTModule module : device.getModules().values()) {
				LinearLayout ll = new LinearLayout(this);
				ll.setOrientation(LinearLayout.HORIZONTAL);
				LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
				        LayoutParams.WRAP_CONTENT,
				        LayoutParams.WRAP_CONTENT);
				    ll.setLayoutParams(linearParams);
				
				TextView moduleTv = new TextView(this);
				String dps = null;
				for (Map.Entry<String, Object> entry : module.getDatapoints().entrySet()) {
					if (dps == null) dps = " (";
					else dps += ", ";
					dps += entry.getKey() + "=" + entry.getValue();
				}
				dps += ")";
				moduleTv.setText(module.getCnd() + dps);	
				moduleTv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT));
				ll.addView(moduleTv);
				llDevDetailsModules.addView(ll);
			}
		}
	}

	@Override
	public void onOneM2MError(OneM2MReqType requestType, String msg) {
		Toast.makeText(this, "getDevice " + msg, Toast.LENGTH_LONG).show();
	}

}
