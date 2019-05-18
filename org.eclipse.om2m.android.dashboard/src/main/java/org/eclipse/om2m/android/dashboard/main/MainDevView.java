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
package org.eclipse.om2m.android.dashboard.main;

import org.eclipse.om2m.android.dashboard.cse.models.SDTDevice;
import org.eclipse.om2m.android.dashboard.devices.DeviceDetailsActivity;
import org.eclipse.om2m.android.dashboard.tools.OneM2MDeviceType;
import org.eclipse.om2m.android.dashboard.tools.SettingsManager;

import org.eclipse.om2m.android.dashboard.R;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainDevView extends LinearLayout {

	private TextView tvName;
	private ImageView ivIcon;
	private OneM2MDeviceType type;
	private SDTDevice sdtDevice;
	
	private static Context context;
	
	public MainDevView(Context context, AttributeSet attrs) {
		super(context, attrs);

		MainDevView.context = context;
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.main_dev_view, this, true);
		
		ivIcon = (ImageView)findViewById(R.id.iv_main_dev_category_icon);
		tvName = (TextView)findViewById(R.id.tv_devType_name);
		tvName.setLines(2);
		tvName.setWidth(250);
		tvName.setGravity(Gravity.CENTER_HORIZONTAL);
	}

	public void setData(final SDTDevice sdtDevice) {
		this.sdtDevice = sdtDevice;
		String cnd = sdtDevice.getCnd();
		String text = null;
		if (SettingsManager.DEVICE_NAME_WITH_ALIAS.equals(SettingsManager.getInstance(context).getDeviceName())) {
			text = sdtDevice.getDeviceAliasName();
		} else {
			text = sdtDevice.getDeviceName();
		}
		
		sdtDevice.getDeviceAliasName();
		if (text == null) {
			text = sdtDevice.getDeviceName();
		}
		if (text == null)
			text = sdtDevice.getRn();
		if (text == null) {
			// compute text from container def
			text = cnd.substring(cnd.lastIndexOf(".") + 7)
					+ " " + sdtDevice.getSerialNumber();
		}
		if (text.indexOf(cnd) >= 0) {
			text.replaceAll(cnd, cnd.substring(cnd.lastIndexOf(".") + 7));
		}
		tvName.setText(text);
		
		type = OneM2MDeviceType.fromValue(cnd);
		if (ivIcon != null) {
			ivIcon.setImageDrawable(getResources().getDrawable(type.getIcon()));
		}
		
		this.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent detailsActivity = new Intent(MainDevView.context, 
						DeviceDetailsActivity.class);	
				detailsActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // because we are not in activity context
				detailsActivity.putExtra("deviceRi", sdtDevice.getRi());
				MainDevView.context.startActivity(detailsActivity);
			}
		});
	}
	
	public SDTDevice getSDTDevice() {
		return sdtDevice;
	}

}
