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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.eclipse.om2m.android.dashboard.CustomSecondaryActivity;
import org.eclipse.om2m.android.dashboard.cse.models.SDTDevice;
import org.eclipse.om2m.android.dashboard.tools.OneM2MDeviceType;
import org.eclipse.om2m.android.dashboard.tools.SettingsManager;
import org.eclipse.om2m.android.dashboard.utils.OTBUtils;

import org.eclipse.om2m.android.dashboard.R;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class DevicesActivity extends CustomSecondaryActivity 
	implements OnClickListener {

	// =====================================================================================

	private LinearLayout llDevicesContainer;
	private List<String> visibleDevCat = new ArrayList<String>();

	private Map<String, List<SDTDevice>> deviceCategories;
	private List<String> basedrivers; 
			
	private String focusedCategory;

	// =====================================================================================
	// Lifecycle
	// =====================================================================================

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			focusedCategory = extras.getString("focusedCategory");			
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

		setContentView(R.layout.devices_activity);

		visibleDevCat.clear();

		llDevicesContainer = (LinearLayout)findViewById(R.id.ll_devices_container);

		// init data with last informations
		refreshCategories();
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

	private void refreshCategories() {
		if ((deviceCategories == null) || (basedrivers == null)) {
			return;
		}
		// 1- add/update device categories
		for (Map.Entry<String, List<SDTDevice>> entry : deviceCategories.entrySet()) {
			OneM2MDeviceType dt = OneM2MDeviceType.fromValue(entry.getKey());
			if (! visibleDevCat.contains(dt.getType())) {
				addDeviceCategoryContainer(dt);
			} else {
				// this device category already in container => just find her view and refresh it
				for (int i = 0; i < llDevicesContainer.getChildCount(); i++) {
					DeviceTypeContainerView devCatView = 
							(DeviceTypeContainerView)llDevicesContainer.getChildAt(i);
					if (devCatView.getDeviceType() == dt) {

						// device for this type
						List<SDTDevice>  devicesForType = entry.getValue();
						devCatView.setDevices(devicesForType);
						break;
					}
				}
			}
		}
		// add categories from basedrivers
		// this will only add cat for basedrivers with no device currently detected
		// otherwise the cat should have already been created by previous bloc
		for (String basedriverType : basedrivers) {
			OneM2MDeviceType dt = OneM2MDeviceType.fromValue(basedriverType);
			if ((dt != null) && ! visibleDevCat.contains(dt.getType())) {
				addDeviceCategoryContainer(dt);	
			}					
		}	
		// remove old device categories
		List<String> devCatToRemove = new ArrayList<String>();
		for (String catName : visibleDevCat) {
			boolean catFound = false;
			for (String key : deviceCategories.keySet()) {
				if (catName.equals(key)) {
					catFound = true;
					break;
				}
			}
			if (! catFound) {
				for (String key : basedrivers) {
					if (catName.equals(key)) {
						catFound = true;
						break;
					}
				}
			}
			if (! catFound) {
				devCatToRemove.add(catName);
			}								
		}
		for (String devCat : devCatToRemove) {
			DeviceTypeContainerView devCatView = null;
			for (int i = 0; i < llDevicesContainer.getChildCount(); i++) {
				DeviceTypeContainerView view = 
						(DeviceTypeContainerView)llDevicesContainer.getChildAt(i);
				if (view.getTypeName().equals(devCat)) {
					devCatView = view;
					break;
				}
			}
			if (devCatView != null) {
				llDevicesContainer.removeView(devCatView);
			}
			visibleDevCat.remove(devCat);
		}
	}

	private void addDeviceCategoryContainer(OneM2MDeviceType type) {
		DeviceTypeContainerView typeContainerView = 
				new DeviceTypeContainerView(getApplicationContext(), null);
		typeContainerView.setDeviceType(type);
		String catName = type.getType();
		if (OTBUtils.equals(focusedCategory, catName)) {
			typeContainerView.open();
		} else {
			typeContainerView.close();
		}

		List<SDTDevice>  devicesForType = null;
		// devices for this type
		for (Map.Entry<String, List<SDTDevice>> entry : deviceCategories.entrySet()) {
			if (OneM2MDeviceType.fromValue(entry.getKey()) == type) {
				devicesForType = entry.getValue();
				break;
			}
		}				
		if (devicesForType == null) {
			devicesForType= new ArrayList<SDTDevice>();
		}																	
		typeContainerView.setDevices(devicesForType);
		
		// add container
		llDevicesContainer.addView(typeContainerView);	

		// memorize it
		visibleDevCat.add(catName);
	}

	// =====================================================================================
	// User actions
	// =====================================================================================

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch(item.getItemId()) {
		case R.id.menu_help:
			if (SettingsManager.getInstance(this).isWizardDeviceSimpleActivated()) {
				SettingsManager.getInstance(this).setWizardDeviceSimple(false);
			} else {
				SettingsManager.getInstance(this).setWizardDeviceSimple(true);
			}
			break;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
//		if (v == btnAdd) {
//		}
	}	
	
}
