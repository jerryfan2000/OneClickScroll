package com.kiumiu.ca.oneclickscroll;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class ocsLauncher extends BroadcastReceiver {
	private static final String TAG = "TaskbarLauncher";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
			Log.d(TAG, "Receive message when boot complated");
			/* Start MainUI activity */
			SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
			if (settings.getBoolean("reboot", true)) {
				Intent mIntent = new Intent(context, OneClickService.class);
				mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startService(mIntent);
			}
			settings = null;
		}
	}
}
