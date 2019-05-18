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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.om2m.android.dashboard.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

public class IconManager {

	private static final IconManager INSTANCE = new IconManager();

	// ================================================================================

	private IconManager() {
		if (INSTANCE != null)  {
			throw new IllegalStateException("Already instantiated");
		}		
	}

	public static IconManager getInstance() {			
		return INSTANCE;
	}

	// ================================================================================

	private Map<String, Bitmap> appIcons = new HashMap<String, Bitmap>();
	private Map<String, Bitmap> userIcons = new HashMap<String, Bitmap>();
	
	public void setIconForApp(String app, Bitmap bitmap) {
		appIcons.put(app, bitmap);
	}
	
	public boolean hasIconForApp(String app) {
		return appIcons.get(app) != null;
	}
	
	public BitmapDrawable getIconForApp(String app, Resources res) {
		Bitmap bitmap = appIcons.get(app);
		return (bitmap != null) ? new BitmapDrawable(res, bitmap)
			: (BitmapDrawable) res.getDrawable(R.drawable.otb_appicon_default);
	}
	
	public void setIconForUser(String user, Bitmap bitmap) {
		userIcons.put(user, bitmap);
	}
	
	public boolean hasIconForUser(String user) {
		return userIcons.get(user) != null;
	}
	
	public BitmapDrawable getIconForUser(String user, Resources res) {
		Bitmap bitmap = userIcons.get(user);
		return (bitmap != null) ? new BitmapDrawable(res, bitmap)
			: (BitmapDrawable) res.getDrawable(R.drawable.otb_user_avatar_default);
	}
	
}
