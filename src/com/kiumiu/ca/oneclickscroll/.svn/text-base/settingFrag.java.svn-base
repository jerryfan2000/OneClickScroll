package com.kiumiu.ca.oneclickscroll;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class settingFrag extends PreferenceFragment implements OnSharedPreferenceChangeListener{
	
	private seekbarListener seek = new seekbarListener();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.preferences);
		SwitchPreference start = (SwitchPreference) findPreference("start");
		if(OneClickService.isRunning)
			start.setChecked(true);
		else
			start.setChecked(false);
	}
	
	@Override
	public void onResume () {
		super.onResume();
		getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
		Intent intent = new Intent("com.kiumiu.ca.ocs.normal");
    	getActivity().sendBroadcast(intent);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if(key.equals("start")) {
			//Service start toggle
			if (OneClickService.isRunning)
                getActivity().stopService(new Intent(getActivity().getApplicationContext(), OneClickService.class));
            else
                getActivity().startService(new Intent(getActivity().getApplicationContext(), OneClickService.class));
		} else if(key.equals("noti") || key.equals("vibration")) {
			getActivity().stopService(new Intent(getActivity().getApplicationContext(), OneClickService.class));
			getActivity().startService(new Intent(getActivity().getApplicationContext(), OneClickService.class));
		}
	}
	
	@Override
	public boolean onPreferenceTreeClick (PreferenceScreen preferenceScreen, final Preference preference) {
		LayoutInflater inflater = getActivity().getLayoutInflater();
		if(preference.getTitle().equals(getResources().getString(R.string.title_area))) {
			View view = inflater.inflate(R.layout.sensitivity, null);
			final SeekBar seeker = (SeekBar) view.findViewById(R.id.seekBar1);
			seeker.setProgress(OneClickService.area);
			seeker.setOnSeekBarChangeListener(seek);
			AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
			dialog.setView(view);
			dialog.setPositiveButton(R.string.save, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					handler.sendEmptyMessage(1);
					Editor editor = preference.getEditor();
					editor.putInt("value", seeker.getProgress());
					OneClickService.area = seeker.getProgress();
					editor.commit();
				}
			});
			dialog.setNegativeButton(R.string.cancel, new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					handler.sendEmptyMessage(1);
				}
			});
			Intent intent = new Intent("com.kiumiu.ca.ocs.red");
			getActivity().sendBroadcast(intent);
			dialog.setCancelable(false);
			dialog.show();
		}
		return false;
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
            switch (msg.what) {
            case 1:
            	Intent intent = new Intent("com.kiumiu.ca.ocs.normal");
            	getActivity().sendBroadcast(intent);
            	break;
            }
		}
	};
	
	private class seekbarListener implements OnSeekBarChangeListener {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {		
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			int value = seekBar.getProgress();
			Intent intent = new Intent("com.kiumiu.ca.ocs.set");
			intent.putExtra("value", value);
			getActivity().sendBroadcast(intent);
		}
		
	}
}
