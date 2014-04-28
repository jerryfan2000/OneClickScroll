package com.kiumiu.ca.oneclickscroll;

import com.kiumiu.ca.oneclickscroll.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.view.View;
import android.view.WindowManager;

public class OneClickService extends Service {
	
	private Context context;
	private LinearLayout pgUp;
	private LinearLayout pgDn;
	
	private BroadcastReceiver listener;
	
	//Preference stores
	private SharedPreferences settings;
	public static boolean reboot = true;
	public static boolean noti = true;
	public static boolean vibration = true;
	public static int area =  0;
	
	
	public static boolean isRunning = false;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	 @Override
	    public void onCreate() {
		 Log.d("ocs", "service running");
		 context=getApplicationContext();
		 OneClickService.isRunning = true;
		 registerStatusListener();
		 
		 //Prepare preference data
		 settings = PreferenceManager.getDefaultSharedPreferences(context);
		 reboot = settings.getBoolean("reboot", false);
		 noti = settings.getBoolean("noti", false);
		 vibration = settings.getBoolean("vibration", false);
		 area = settings.getInt("value", 0);
		 
		 LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 pgUp = (LinearLayout) inflater.inflate(R.layout.touch, null);
		 pgDn = (LinearLayout) inflater.inflate(R.layout.touch, null);
		 
		 //Setup window param here pg up at left
		 WindowManager mTest = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	     WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
	     WindowManager.LayoutParams params = mParams;
	     params.gravity = Gravity.LEFT | Gravity.TOP;
	     params.height = WindowManager.LayoutParams.WRAP_CONTENT;
	     params.width = WindowManager.LayoutParams.WRAP_CONTENT;
	     params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
	     params.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
	     params.format = PixelFormat.TRANSLUCENT;
	     mTest.addView(pgUp, params);
	     
	     //Setup window param for pg dn at right
	     params.gravity = Gravity.RIGHT | Gravity.TOP;
	     mTest.addView(pgDn, params);
	     
	     //Setup pgup and pgdn functions
	     EventHandler event = new EventHandler(context);
	     myLinearLayout up, down;
	     up = (myLinearLayout) pgUp.findViewById(R.id.swipe);
	     down = (myLinearLayout) pgDn.findViewById(R.id.swipe);
	     setArea(OneClickService.area);
	     up.setFunction(true);
	     up.setEventHandler(event);
	     down.setFunction(false);
	     down.setEventHandler(event);
	     Toast.makeText(context, getResources().getString(R.string.starting), Toast.LENGTH_SHORT).show();
	     if(OneClickService.noti) {
	    	 Notification mNotifyForground = new Notification();
	    	 startForeground(100, mNotifyForground);
	     }
	 }
	 
	 @Override
	 public void onDestroy() {
		 Toast.makeText(context, getResources().getString(R.string.finishing), Toast.LENGTH_SHORT).show();
		 WindowManager mTest = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		 if(listener != null)
			 this.unregisterReceiver(listener);
		 mTest.removeView(pgUp);
		 mTest.removeView(pgDn);
		 NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	     mNotificationManager.cancelAll();
	     mNotificationManager = null;
		 OneClickService.isRunning = false;
	 }
	 
	 private void registerStatusListener() {
		 listener = new statusbar();
		 IntentFilter filter = new IntentFilter();
		 filter.addAction("com.kiumiu.ca.ocs.red");
		 filter.addAction("com.kiumiu.ca.ocs.normal");
		 filter.addAction("com.kiumiu.ca.ocs.set");
		 this.registerReceiver(listener, filter);
	 }
	 
	 private class statusbar extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if(action.equals("com.kiumiu.ca.ocs.red")) {
				pgUp.setBackgroundColor(0xa0ff0000);
				pgDn.setBackgroundColor(0xa0ff0000);
			} else if(action.equals("com.kiumiu.ca.ocs.normal")) {
				pgUp.setBackgroundColor(Color.TRANSPARENT);
				pgDn.setBackgroundColor(Color.TRANSPARENT);
			} else if(action.equals("com.kiumiu.ca.ocs.set")) {
				Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		        DisplayMetrics metrics = new DisplayMetrics();
		        display.getMetrics(metrics);
		        int max = display.getWidth()/4;
		        int min = (int) (30 * metrics.density);
		        int newValue;
		        if(intent.hasExtra("value"))
		        	newValue = intent.getIntExtra("value", 0);
		        else
		        	newValue = OneClickService.area;
		        
		        int height = (int) (25 * metrics.density);
		        int weight = min + ((max - min)/100 * newValue);
		        Log.d("ocs","new width is:" + weight);
		        pgUp.findViewById(R.id.swipe).setLayoutParams(new LinearLayout.LayoutParams(weight,height));
		        pgDn.findViewById(R.id.swipe).setLayoutParams(new LinearLayout.LayoutParams(weight,height));
			}
		} 
	 }
	 
	 private void setArea(int value) {
		 Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
	        DisplayMetrics metrics = new DisplayMetrics();
	        display.getMetrics(metrics);
	        int max = display.getWidth()/4;
	        int min = (int) (30 * metrics.density);
	        int height = (int) (25 * metrics.density);
	        int weight = min + ((max - min)/100 * value);
	        pgUp.findViewById(R.id.swipe).setLayoutParams(new LinearLayout.LayoutParams(weight,height));
	        pgDn.findViewById(R.id.swipe).setLayoutParams(new LinearLayout.LayoutParams(weight,height));
	 }
}
