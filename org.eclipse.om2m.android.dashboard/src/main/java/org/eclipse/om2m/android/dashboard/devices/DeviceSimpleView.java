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

import org.eclipse.om2m.android.dashboard.cse.models.SDTDevice;

import org.eclipse.om2m.android.dashboard.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DeviceSimpleView extends LinearLayout implements OnClickListener {

	private TextView tvDeviceName;
	private TextView tvDeviceDescription;
	private LinearLayout llDetails;
	private SDTDevice device;
	private Context context;

	public DeviceSimpleView(Context context, AttributeSet attrs) {
		super(context, attrs);

		this.context = context;

		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.device_simple_view, this, true);

		tvDeviceName = (TextView)findViewById(R.id.tv_device_simple_name);
		tvDeviceDescription = (TextView)findViewById(R.id.tv_device_simple_decription);
		llDetails = (LinearLayout)findViewById(R.id.ll_device_simple);

		llDetails.setOnClickListener(this);
	}

	public SDTDevice getDevice() {
		return this.device;
	}
	
	public void setDevice(SDTDevice device) {
		this.device = device;
		if (device != null) {
			if (tvDeviceName != null) {
//				String name = device.getFriendlyName();
//				String model = device.getSubModel();
//				if (! model.isEmpty())
//					name += " (" + model + ")";
				tvDeviceName.setText(device.getDeviceName());
				tvDeviceName.setTypeface(null, Typeface.BOLD);
			}
			if (tvDeviceDescription != null) {
				tvDeviceDescription.setText(device.getDeviceAliasName());
				tvDeviceDescription.setTypeface(null, Typeface.NORMAL);
			}
		}
	}

	// =====================================================================================
	// User actions
	// =====================================================================================

	@Override
	public void onClick(View v) {
		if (v instanceof LinearLayout) {
			LinearLayout layoutClicked = (LinearLayout)v;
			if (layoutClicked == llDetails) {
				if (device != null) {
					Intent detailsActivity = new Intent(this.context, DeviceDetailsActivity.class);	
					detailsActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // because we are not in activity context
					detailsActivity.putExtra("deviceRi", this.device.getRi());
					this.context.startActivity(detailsActivity);
				} else {
					AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DeviceSimpleView.this.getContext());
					dialogBuilder.setTitle(R.string.device_details_no_access_rights_title);
					dialogBuilder.setMessage(getResources().getString(R.string.device_details_no_access_rights));
					dialogBuilder.setPositiveButton(R.string.dialog_btn_ok, 
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
							}
						});
					try {
						dialogBuilder.create().show();
					} catch (Exception e) {
						Toast.makeText(context, getResources().getString(R.string.device_details_no_access_rights), Toast.LENGTH_LONG).show();
					}
				}
			}
		}
	}
	
}
