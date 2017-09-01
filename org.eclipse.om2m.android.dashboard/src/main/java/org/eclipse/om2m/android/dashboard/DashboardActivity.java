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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.SortedSet;

import org.eclipse.om2m.android.dashboard.cse.OneM2MListener;
import org.eclipse.om2m.android.dashboard.cse.OneM2MRequest.OneM2MReqType;
import org.eclipse.om2m.android.dashboard.cse.OneM2MRequester;
import org.eclipse.om2m.android.dashboard.cse.models.OneM2MApplication;
import org.eclipse.om2m.android.dashboard.cse.models.SDTDevice;
import org.eclipse.om2m.android.dashboard.devices.DevicesActivity;
import org.eclipse.om2m.android.dashboard.main.MainAppEntryView;
import org.eclipse.om2m.android.dashboard.main.MainDevView;
import org.eclipse.om2m.android.dashboard.tools.OneM2MDeviceType;
import org.eclipse.om2m.android.dashboard.tools.SettingsManager;
import org.eclipse.om2m.android.dashboard.tools.Sorter;
import org.eclipse.om2m.android.dashboard.utils.OTBUtils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class DashboardActivity extends CustomMainActivity 
		implements OnClickListener, OnLongClickListener, OneM2MListener {

	private RelativeLayout rlDevs;

	private GridLayout glApps;
	private GridLayout glDevices;

	private ImageView ivCSEState;

	private ScrollView svDynamicCardScroller;
	
	private TextView tvCounterApp;
	private TextView tvCounterDev;
	private TextView tvStatusTitle;

	private List<SDTDevice> devices;
	private SortedSet<OneM2MApplication> applications;
	
	private boolean isInitialized = false;
	
	private boolean cseDetected;
	private boolean cseConnected;
	private int width = 0;
	private int appCol = -1;
	private int devCol = -1;
	
	// ================================================================================

	Handler mDicoveryHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			boolean discovery_succeed = msg.getData().getBoolean("DISCOVERY_SUCCEED");
			if (discovery_succeed) {
				init();
			} else {
				reset();							
			}	
		}
	};

	// =====================================================================================
	// Lifecycle
	// =====================================================================================

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		try {
		super.onResume();
		
		Log.i(getClass().getName(), "entering onResume");
		
		isInitialized = false;

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		
		String language = SettingsManager.getInstance(this).getLanguage();
		Resources res = getApplication().getResources();
		Configuration config = res.getConfiguration();
		config.locale = new Locale(language);
		res.updateConfiguration(config, null);
		try {
			OneM2MDeviceType.initialize(res);
		} catch (Exception e) {
			Toast.makeText(this, "Error " + e, Toast.LENGTH_LONG).show();
		}

		setContentView(R.layout.main_activity);

		rlDevs = (RelativeLayout)findViewById(R.id.rl_main_zone_devs);

		tvCounterApp = (TextView)findViewById(R.id.tv_main_counter_app);
		tvCounterDev = (TextView)findViewById(R.id.tv_main_counter_dev);
		
		tvStatusTitle = (TextView)findViewById(R.id.tv_cse_status_title);

		glApps = (GridLayout)findViewById(R.id.gl_main_cse_app);			
		glDevices = (GridLayout)findViewById(R.id.gl_main_cse_devices);		
		
		glApps.removeAllViews();
		glDevices.removeAllViews();
		
		ivCSEState = (ImageView)findViewById(R.id.iv_cse_state_icon);	
		
		svDynamicCardScroller = (ScrollView)findViewById(R.id.sv_main_dynamic_card_scroller);
		
		ivCSEState.setOnLongClickListener(this);

		refreshAll();
		
		// add an observer to be sure that the layout is created before getting size
		// and resize dynamically the card to take full screen size
		tvStatusTitle.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
            	//Remove it here unless you want to get this callback for EVERY
                //layout pass, which can get you into infinite loops if you ever
                //modify the layout from within this method.
            	tvStatusTitle.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            	
            	// set dynamic card container size  
            	int svHeight = svDynamicCardScroller.getMeasuredHeight();
            	int svWidth = svDynamicCardScroller.getMeasuredWidth();
        		int display_mode = getResources().getConfiguration().orientation;
        		if (display_mode == 1) { // portrait
        			LinearLayout llDynamicCardContainer = (LinearLayout)findViewById(R.id.ll_main_dynamic_card_container);        			        			
        			llDynamicCardContainer.setMinimumHeight(svHeight);   
        			int appCol = svWidth / (165 + 70);	// 165 = app view width
					  									// 70 = margin between app
        			int devCol = svWidth / (150 + 70); 	// 150 = dev view width
														// 70 = margin between dev
           			glApps.setColumnCount(appCol);  
           			glDevices.setColumnCount(devCol);
        		} 
        		else { 	// landscape
        			resize();
        			LinearLayout llLeftCardContainer = (LinearLayout)findViewById(R.id.ll_cse_left);
        			LayoutParams params = llLeftCardContainer.getLayoutParams();
        			
        			double px = (double) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 175, llLeftCardContainer.getResources().getDisplayMetrics());
        			
        			params.width = (width * 4) / 9;  // Left  (apps) weight = 5
        											 // Right (devs) weight = 4
        			int cappCol = (int) Math.floor( ((double) width) * 4 / 9 / px);	// 165 = app view width
																	// 70 = margin between app
					appCol = cappCol;
					devCol = ((width * 4) / 9) / (160 + 70);	// 150 = dev view width
																// 70 = margin between dev
					try {
						glApps.removeAllViews();
						glApps.setColumnCount(appCol);  
						Log.d(getClass().getName(), "number of app col=" + appCol);
					} catch(Exception e) {
//    						glApps.setColumnCount(appCol+1);  
					}
					try {
						glDevices.removeAllViews();
						glDevices.setColumnCount(devCol);
						Log.d(getClass().getName(), "number of devices col=" + devCol);
					} catch(Exception e) {
//    						glDevices.setColumnCount(devCol+1);
					}
        		}  
        		
        		// init data with last informations
        		refreshAll();
            }
        });				

			init();
			Log.i(getClass().getName(), "leaving onResume");
		} catch(Exception e) {
			e.printStackTrace();
			try {
				AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
				dialogBuilder.setTitle("Error " + e);
				dialogBuilder.setMessage(e.getMessage());
				dialogBuilder.setPositiveButton(R.string.dialog_btn_ok, 
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
						}
					});
				dialogBuilder.create().show();
			} catch (Exception e2) {
				Toast.makeText(this, "Error " + e, Toast.LENGTH_LONG).show();
			}
			
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
		OneM2MRequester.getInstance().cancelMyPollingRequests();
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

	private void reset() {		
		refreshCSE(false, false);

		if (devices != null) {
			devices.clear();
		}
		glDevices.removeAllViews();
		applications = null;
		glApps.removeAllViews();
		
		refreshAll();
	}

	private void init() {	
		if (! isInitialized) {
			OneM2MRequester.getInstance().sendPollingRequest(OneM2MReqType.INCSE_STATUS, null, 
					getApplicationContext(), this, OneM2MRequester.DEFAULT_POLLING_DELAY);
			OneM2MRequester.getInstance().sendPollingRequest(OneM2MReqType.DEVICES, null, 
					getApplicationContext(), this, OneM2MRequester.DEFAULT_POLLING_DELAY);
			OneM2MRequester.getInstance().sendPollingRequest(OneM2MReqType.APPLICATIONS, null, 
					getApplicationContext(), this, OneM2MRequester.DEFAULT_POLLING_DELAY);
			isInitialized = true;
		}
	}
	
	private void refreshAll() {
		Log.i(getClass().getName(), "entering refreshAll");
		refreshCSE(cseDetected, cseConnected);
		refreshDevices();
		refreshApplications();
	}
	
	private synchronized void refreshCSE(boolean detected, boolean connected) {
		Log.i(getClass().getName(), "entering refreshHab");
		try {
			if (! detected) {
				ivCSEState.setImageDrawable(getResources().getDrawable(R.drawable.otb_picto_led_notconnected));
				tvStatusTitle.setText(getResources().getString(R.string.main_cse_state_not_detected));
				cseDetected = false;
				cseConnected = false;
			} else if (! connected) {
				ivCSEState.setImageDrawable(getResources().getDrawable(R.drawable.otb_picto_led_notconnected));
				tvStatusTitle.setText(getResources().getString(R.string.main_cse_state_not_connected));
				cseDetected = true;
				cseConnected = false;
			} else {
				ivCSEState.setImageDrawable(getResources().getDrawable(R.drawable.otb_picto_led_ok));
				tvStatusTitle.setText(getResources().getString(R.string.main_cse_state_ok));
				cseDetected = true;
				cseConnected = true;
			}
		} catch (Exception e) {
		}
	}

	private synchronized void refreshDevices() {
		Log.i(getClass().getName(), "entering refreshDevices");
		if (devices == null) {
			if (tvCounterDev != null) {
				tvCounterDev.setText("0");
			}
			glDevices.removeAllViews();
			return;
		}
		
		int deviceCounter = 0;
		// add/update devices
		List<MainDevView> newViews = new ArrayList<MainDevView>();
		for (SDTDevice sdtDevice : devices) {
			
			deviceCounter += 1;
			boolean deviceAlreadyKnown = false;
			for (int i = 0; i < glDevices.getChildCount(); i++) {
				Object obj = glDevices.getChildAt(i);
				if (obj instanceof MainDevView) {
					MainDevView devView = (MainDevView)obj;
					if ((devView != null) && (sdtDevice.getRi().equals(devView.getSDTDevice().getRi()))) {
						// same entry => refresh count
						deviceAlreadyKnown = true;
						devView.setData(sdtDevice);
						break;
					}
				}
			}

			if (! deviceAlreadyKnown) {
				// create a new device
				MainDevView devView = new MainDevView(getApplicationContext(), null);
				devView.setData(sdtDevice);
				// add device
				newViews.add(devView);
			}
		}
		try {
			glDevices.setColumnCount(devCol);
		} catch(Exception e) {
		}
		for (MainDevView v : newViews)
			glDevices.addView(v, 0);	
		
		// remove old devices
		List<Integer> indexToRemove = new ArrayList<Integer>();
		for (int i = 0; i < glDevices.getChildCount(); i++) {
			Object obj = glDevices.getChildAt(i);
			if (obj instanceof MainDevView) {
				boolean deviceFound = false;
				SDTDevice renderedDevice = ((MainDevView)obj).getSDTDevice();
				if (devices.contains(renderedDevice)) {
					deviceFound = true;
				}
				if (! deviceFound) {
					indexToRemove.add(i);
				}
			}
		}
		for (Integer i : indexToRemove) {
			try {
				glDevices.removeViewAt(i);
			}
			catch(Exception e){}
		}

		if (tvCounterDev != null) {
			tvCounterDev.setText("" + deviceCounter);
		}
		resize();
	}

	private synchronized void refreshApplications() {
		Log.i(getClass().getName(), "entering refreshApplications");
		if (OTBUtils.isEmpty(applications)) {
			if (tvCounterApp != null) {
				tvCounterApp.setText("0");
			}
			glApps.removeAllViews();
			return;
		}
		if (tvCounterApp != null) {
			tvCounterApp.setText("" + applications.size());
		}			

		// add/update applications
		int idx = 0;
		for (OneM2MApplication app : applications) {
			boolean found = false;
			for (int i = 0; i < glApps.getChildCount(); i++) {
				Object obj = glApps.getChildAt(i);
				if (obj instanceof MainAppEntryView) {
					MainAppEntryView appView = (MainAppEntryView)obj;
					if ((appView != null) && appView.app.getRi().equals(app.getRi())) {
						// same entry => refresh count
						found = true;
						appView.setData(app);
						break;
					}
				}
			}

			if (! found) {
				// create a new entry
				MainAppEntryView appView = 
					new MainAppEntryView(getApplicationContext(), null);
				appView.setData(app);
				// add entry
				glApps.addView(appView, idx);
			}
			idx += 1;
		}

		// remove old entries
		List<View> indexToRemove = new ArrayList<View>();
		for (int i = 0; i < glApps.getChildCount(); i++) {
			Object obj = glApps.getChildAt(i);
			if (obj instanceof MainAppEntryView) {
				MainAppEntryView appEntryView = (MainAppEntryView)obj;
				boolean found = false;
				for (OneM2MApplication application : applications) {
					if (appEntryView.app.getRi().equals(application.getRi())) {
						found = true;
						break;
					}
				}
				if (! found) {
					indexToRemove.add((View) obj);
				}
			}
		}
		for (View v : indexToRemove) {
			glApps.removeView(v);
		}	

		resize();
	}

	
	private void resize() {
		// refresh container height
		int display_mode = getResources().getConfiguration().orientation;
		if (display_mode != 1) { 						
			// landscape

			int margin10dp = Math.round(10 * 
					(getApplicationContext().getResources().getDisplayMetrics().xdpi 
							/ DisplayMetrics.DENSITY_DEFAULT));       
		    
			int svMainHeight = svDynamicCardScroller.getMeasuredHeight();

			LinearLayout llRightCardContainer = (LinearLayout)findViewById(R.id.ll_cse_right);

			RelativeLayout rlHabCardContainer = (RelativeLayout)findViewById(R.id.rl_main_zone_cse); 
			RelativeLayout rlAppsCardContainer = (RelativeLayout)findViewById(R.id.rl_main_zone_apps); 
			RelativeLayout rlDevsCardContainer = (RelativeLayout)findViewById(R.id.rl_main_zone_devs);
			
			int svHabHeight = rlHabCardContainer.getMeasuredHeight();
			int svAppHeight = rlAppsCardContainer.getMeasuredHeight();
			int svDevHeight = (svHabHeight + svAppHeight); 

			int svHeight = 0, appMinHeight = 0, devMinHeight = 0;

			svHeight = Math.max(svAppHeight + svHabHeight, svDevHeight /*+ svUsrHeight*/);
			svHeight = Math.max(svHeight, svMainHeight);
			appMinHeight = svHeight - svHabHeight - margin10dp;
			devMinHeight = svHeight ;

			rlAppsCardContainer.setMinimumHeight(appMinHeight);        			        			
			rlDevsCardContainer.setMinimumHeight(devMinHeight); 
			
			rlAppsCardContainer.invalidate();
			rlDevsCardContainer.invalidate();
		}	
	}
	
	// =====================================================================================
	// User actions
	// =====================================================================================

	@Override
	public void onClick(View v) {
		if (v instanceof RelativeLayout) {
			RelativeLayout layoutClicked = (RelativeLayout)v;
			if (layoutClicked == rlDevs) {
				startActivity(new Intent(DashboardActivity.this,
					DevicesActivity.class));
			}
		}
	}

	@Override
	public boolean onLongClick(View v) {
		if (v instanceof ImageView) {
			ImageView viewClicked = (ImageView)v;
			if (viewClicked == ivCSEState) {
//				OTBHabRequester.getInstance().sendRequest(OTBHabReqType.DEBUG,
//						null, getApplicationContext(), this);							
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onOneM2MResponse(OneM2MReqType requestType, Object response) {
		if (requestType == OneM2MReqType.APPLICATIONS) {
			List<OneM2MApplication> apps = (List<OneM2MApplication>)response;
			applications = Sorter.getSortedApplications(apps);
			refreshApplications();
		} else if (requestType == OneM2MReqType.DEVICES) {
			devices = (List<SDTDevice>) response;
			refreshDevices();
		} else if (requestType == OneM2MReqType.INCSE_STATUS) {
			boolean isConnected = (Boolean) response;
			refreshCSE(isConnected, isConnected);
		}
	}

	@Override
	public void onOneM2MError(OneM2MReqType requestType, String msg) {
		Toast.makeText(this, "Error " + msg, Toast.LENGTH_LONG).show();
	}
	
}
