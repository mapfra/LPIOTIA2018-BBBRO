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

import org.eclipse.om2m.android.dashboard.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/*
 * pour utiliser ce control dans le xml, 
 * ne pas oublier d'ajouter un custom namespace dans l'entête pour pouvoir utiliser les attributs
 * exemple :
 * 
 * xmlns:custom="http://schemas.android.com/apk/res/com.orange.openthebox.dashboard"
 * 
 * <com.orange.openthebox.dashboard.OTBCustomLineView
        custom:dashed="true"
        android:layout_width="match_parent"
        android:layout_height="2dp"
        custom:dashGap="2"
        custom:dashWidth="2"
        custom:thickness="1"
        custom:orientation="horizontal"
        custom:color="@color/otb_red" />
 */

public class CustomLineView extends ImageView {

	private Paint paint = new Paint();

	private int color = -1;
	private int thickness = -1;
	private boolean dashed = false;
	private int dashWidth = -1;
	private int dashGap = -1;
	private int orientation = 0; // 0 = horizontal, 1 = vertical
	private boolean shadow = false;


	public CustomLineView(Context context, AttributeSet attrs) {
		super(context, attrs);	

		TypedArray myAttrs = context.obtainStyledAttributes(attrs, R.styleable.CustomLine);

		color = myAttrs.getResourceId(R.styleable.CustomLine_color, R.color.soft_gray);
		thickness = myAttrs.getInt(R.styleable.CustomLine_thickness, 2);
		dashWidth = myAttrs.getInt(R.styleable.CustomLine_dashWidth, 10);
		dashGap = myAttrs.getInt(R.styleable.CustomLine_dashGap, 5);
		dashed = myAttrs.getBoolean(R.styleable.CustomLine_dashed, false);
		orientation = myAttrs.getInt(R.styleable.CustomLine_orientation, 0);
		shadow = myAttrs.getBoolean(R.styleable.CustomLine_shadow, false);
		
		myAttrs.recycle();
		
		// patch pour android > 4.0
		// pour réussir à dessiner des ligne en pointillés, 
		// il faut désactiver l'accélération matérielle sur la vue 
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			setLayerType(View.LAYER_TYPE_SOFTWARE, null);			
		}		
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		try {
			paint.setColor(getResources().getColor(color));
		} catch(Exception ignored) {}
		
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(thickness);

		if (dashed) {
			paint.setPathEffect(new DashPathEffect(new float[]{dashWidth, dashGap}, 0));
		}
		if (shadow) {
			paint.setShadowLayer(8, 0, 0, getResources().getColor(color));
		}
		if (orientation == 0) { // horizontal
			canvas.drawLine(0, 0, getWidth(), 0, paint);
		} else if (orientation == 1) { // vertical
			canvas.drawLine(0, getHeight(), 0 , 0, paint);
		}
	}
	
}
