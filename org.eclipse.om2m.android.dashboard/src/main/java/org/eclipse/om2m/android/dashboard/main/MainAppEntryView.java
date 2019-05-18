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

import java.io.InputStream;

import org.eclipse.om2m.android.dashboard.applications.ApplicationConfigActivity;
import org.eclipse.om2m.android.dashboard.cse.models.OneM2MApplication;

import org.eclipse.om2m.android.dashboard.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainAppEntryView extends LinearLayout {

	private TextView tvAppName;
	private ImageView ivAppIcon;
	private RelativeLayout rlUnactivable;

	private boolean canOpenDetails = true;

	public OneM2MApplication app;

	private static Context context;

	public MainAppEntryView(Context context, AttributeSet attrs) {
		super(context, attrs);

		MainAppEntryView.context = context;

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.main_app_entry_view, this, true);

		rlUnactivable = (RelativeLayout) findViewById(R.id.rl_main_app_entry_stopped);
		tvAppName = (TextView) findViewById(R.id.tv_main_app_entry_name);
		ivAppIcon = (ImageView) findViewById(R.id.iv_main_app_entry_icon);
	}

	public OneM2MApplication getApplication() {
		return app;
	}

	public void setData(final OneM2MApplication app, boolean canOpenDetails) {
		this.canOpenDetails = canOpenDetails;
		setData(app);
	}

	public void setData(final OneM2MApplication app) {
		this.app = app;

		if (tvAppName != null) {
			tvAppName.setText(this.app.getApn());
		}
		if (ivAppIcon != null) {
			// ivAppIcon.setImageDrawable(OTBIconManager.getInstance().getIconForApp(app.getRi(),
			// getResources()));
			new DownloadImageTask(ivAppIcon).execute(app.getIconUrl());
		}

		this.setClickable(true);

		if (canOpenDetails /* && app.isAccessible() */) {
			this.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View arg0) {
					// Intent detailsActivity = new Intent(OTBMainAppEntryView.context,
					// OTBApplicationDetailsActivity.class);
					// detailsActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					// detailsActivity.putExtra("applicationId", app.getId());
					// OTBMainAppEntryView.context.startActivity(detailsActivity);
					return true;
				}
			});
//			if (OTBConstants.ACTIVE.equals("Active"/* app.getStatus() */)) {
				this.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						Intent configActivity = new Intent(MainAppEntryView.context,
								ApplicationConfigActivity.class);
						configActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						configActivity.putExtra("applicationId", app.getRi());
						MainAppEntryView.context.startActivity(configActivity);
					}
				});
//			}
		}

		rlUnactivable.setVisibility(View.INVISIBLE);
	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			Bitmap mIcon11 = null;
			String urldisplay = urls[0];
			if (urldisplay != null) {
				try {
					InputStream in = new java.net.URL(urldisplay).openStream();
					mIcon11 = BitmapFactory.decodeStream(in);
				} catch (Exception e) {
					Log.e("Error", e.getMessage());
					e.printStackTrace();
				}
			}
			
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			if (result != null) {
				bmImage.setImageBitmap(result);	
			} else {
				bmImage.setImageDrawable(getResources().getDrawable(R.drawable.otb_appicon_default));
			}
			
			
			
		}
	}

}
