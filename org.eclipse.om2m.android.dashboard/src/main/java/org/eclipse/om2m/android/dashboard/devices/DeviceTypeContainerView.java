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
import java.util.SortedSet;

import org.eclipse.om2m.android.dashboard.cse.models.SDTDevice;
import org.eclipse.om2m.android.dashboard.tools.OneM2MDeviceType;
import org.eclipse.om2m.android.dashboard.tools.Sorter;

import org.eclipse.om2m.android.dashboard.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DeviceTypeContainerView extends LinearLayout implements OnClickListener {

	private LinearLayout container;
	private ImageView ivDevCatIcon;
	private ImageView ivDevCatMoreLess;
	private TextView tvDevCatCount;
	private TextView tvDevLocale;
	private Context context;

	private OneM2MDeviceType type;
	private List<String> visibleDevIds = new ArrayList<String>();


	public DeviceTypeContainerView(Context context, AttributeSet attrs) {
		super(context, attrs);

		this.context = context;

		visibleDevIds.clear();

		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.device_type_container_view, this, true);

		container = (LinearLayout)findViewById(R.id.ll_devType_container);
		ivDevCatIcon = (ImageView)findViewById(R.id.iv_devType_icon);
		ivDevCatMoreLess = (ImageView)findViewById(R.id.iv_devType_moreLess);
		tvDevCatCount = (TextView)findViewById(R.id.tv_devType_count);
		tvDevLocale = (TextView)findViewById(R.id.tv_devType_locale);

		this.setOnClickListener(this);
	}

	public OneM2MDeviceType getDeviceType() {
		return type;
	}

	public String getTypeName() {
		return (type == null) ? "" : type.getType();
	}

	public void setDeviceType(OneM2MDeviceType type) {
		this.type = type;
		if (ivDevCatIcon != null) {		
			ivDevCatIcon.setImageDrawable(getResources().getDrawable(type.getIcon()));
		}
	}

	public void setDevices(List<SDTDevice> devs) {
		SortedSet<SDTDevice> devices = Sorter.getSortedDevices(devs);
		if (tvDevCatCount != null) {		
			tvDevCatCount.setText("" + devices.size());						
		}
		if (tvDevLocale != null) {		
			tvDevLocale.setText(type.getLocale());
		}
		
		// 1- add/update devices
		for (SDTDevice device : devices) {
			if (visibleDevIds.contains(device.getRi())) {
				// this device already in container => just find his view and refresh it
				for (int i = 0; i < container.getChildCount(); i++) {
					DeviceSimpleView devView = (DeviceSimpleView)container.getChildAt(i);
					if ((devView.getDevice() != null) 
							&& devView.getDevice().getRi().equals(device.getRi())) {
						devView.setDevice(device);
						break;
					}
				}
			}
			else {
				DeviceSimpleView devView = new DeviceSimpleView(this.context, null);
				devView.setDevice(device);

				// add container
				container.addView(devView);	

				// memorize it
				visibleDevIds.add(device.getRi());
			}			
		}

		// remove old devices
		List<String> devIdsToRemove = new ArrayList<String>();
		for (String devId : visibleDevIds) {
			boolean devFound = false;
			for (SDTDevice dev : devices) {
				if (dev.getRi().equals(devId)) {
					devFound = true;
					break;
				}
			}
			if (! devFound) {
				devIdsToRemove.add(devId);
			}
		}
		for (String devId : devIdsToRemove) {
			DeviceSimpleView devView = null;
			for (int i = 0; i < container.getChildCount(); i++) {
				DeviceSimpleView view = (DeviceSimpleView)container.getChildAt(i);
				if ((view.getDevice() != null)
						&& view.getDevice().getRi().equals(devId)) {
					devView = view;
					break;
				}
			}
			if (devView != null) {
				container.removeView(devView);
			}
			visibleDevIds.remove(devId);
		}
	}

	@Override
	public void onClick(View v) {
		if (container.getVisibility() == View.VISIBLE) {
			close();
		} else {
			open();
		}
	}
	
	public void open() {
		ivDevCatMoreLess.setImageDrawable(getResources().getDrawable(R.drawable.otb_picto_close_blue));
		container.setVisibility(View.VISIBLE);
	}
	
	public void close() {
		ivDevCatMoreLess.setImageDrawable(getResources().getDrawable(R.drawable.otb_picto_open_blue));
		container.setVisibility(View.GONE);
	}
	
}
