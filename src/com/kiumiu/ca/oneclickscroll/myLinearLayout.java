package com.kiumiu.ca.oneclickscroll;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class myLinearLayout extends LinearLayout {
	
	private Context mContext;
	private EventHandler evt;
	private boolean isUp = false;
	private Vibrator feedback;

	public myLinearLayout(Context context) {
		super(context);
		mContext = context;
		feedback = (Vibrator) mContext
				.getSystemService(Context.VIBRATOR_SERVICE);
	}
	
	public myLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		feedback = (Vibrator) mContext
				.getSystemService(Context.VIBRATOR_SERVICE);
	}

	
	public myLinearLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		feedback = (Vibrator) mContext
				.getSystemService(Context.VIBRATOR_SERVICE);
	}
	
	protected void onConfigurationChanged (Configuration newConfig) {
		Intent intent = new Intent("com.kiumiu.ca.ocs.set");
		mContext.sendBroadcast(intent);
	}
	
	public void setEventHandler(EventHandler event) {
		evt = event;
	}
	
	public void setFunction(boolean pgUp) {
		isUp = pgUp;
	}
	
	public boolean onTouchEvent(MotionEvent ev) {
		Log.d("ocs",
				"Entering touch event x=" + ev.getX() + " y=" + ev.getY());
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			if(OneClickService.vibration)
				feedback.vibrate(30);
		} else if (ev.getAction() == MotionEvent.ACTION_UP) {
			postDelayed(mDoPrepare,0);
			if(isUp) {
				for(int x=0;x<15;x++)
					postDelayed(mDoScrollAUp,x);
				for(int x=0;x<50;x++)
					postDelayed(mDoScrollUp,50+x);
			}
			else {
				for(int x=0;x<15;x++)
					postDelayed(mDoScrollABottom,x);
				for(int x=0;x<50;x++)
					postDelayed(mDoScrollBottom,50+x);
			}
		}
		return true;
	}
	
	 Runnable mDoPrepare = new Runnable() {
	    	public void run() {
	    		//evt.sendKeys(19);
		    	evt.sendKeys(19);
		    	evt.sendKeys(20);
		    	//evt.sendKeys(19);
	    	}
	    };	
	 Runnable mDoScrollUp = new Runnable() {
	    	public void run() {
	    		//evt.sendKeys(19);
		    	evt.sendKeys(92);
		    	//evt.sendKeys(19);
	    	}
	    };
	    
	    Runnable mDoScrollBottom = new Runnable() {
	    	public void run() {
	    		//evt.sendKeys(20);
		    	evt.sendKeys(93);
		    	//evt.sendKeys(20);
	    	}
	    };
	    
	    Runnable mDoScrollAUp = new Runnable() {
	    	public void run() {
	    		evt.sendKeys(19);
		    	//evt.sendKeys(92);
		    	//evt.sendKeys(19);
	    	}
	    };
	    
	    Runnable mDoScrollABottom = new Runnable() {
	    	public void run() {
	    		evt.sendKeys(20);
		    	//evt.sendKeys(93);
		    	//evt.sendKeys(20);
	    	}
	    };
}
