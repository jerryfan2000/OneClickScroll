package com.kiumiu.ca.oneclickscroll;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class aboutFrag extends ListFragment {
	
	Context mContext;

	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mContext = getActivity().getApplicationContext();
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		String[] header = getResources().getStringArray(R.array.about_header);
		String[] detail = getResources().getStringArray(R.array.about_detail);
		
		for(int x=0; x<header.length; x++) {
			HashMap<String, String> b = new HashMap<String, String>();
			b.put("header", header[x]);
			b.put("detail", detail[x]);
			list.add(b);
		}
		
		SimpleAdapter adapter = new SimpleAdapter(getActivity(), list, android.R.layout.simple_list_item_2, new String[] {"header", "detail"}, new int[] { android.R.id.text1, android.R.id.text2 });
		setListAdapter(adapter);
	}
	
	@Override
	public void onListItemClick (ListView l, View v, int position, long id) {
		if(id == 0) {
			Intent intent = new Intent(Intent.ACTION_VIEW );
            intent.setData(Uri.parse("market://details?id=com.kiumiu.ca.oneclickscroll"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity( intent );
		} else if(id == 1) {
			Intent intent = new Intent(Intent.ACTION_VIEW );
            intent.setData(Uri.parse("market://details?id=com.kiumiu.ca.oneclickscroll"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity( intent );
		} else if(id == 2) {
			String message = getResources().getString(R.string.share_body);
        	Intent i = new Intent(Intent.ACTION_SEND);
			i.setType("text/plain");
			i.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.share_topic));
			i.putExtra(Intent.EXTRA_TEXT, message);
			startActivity(Intent.createChooser(i, getResources().getString(R.string.share_title)));
		} else if(id == 3) {
			Intent intent = new Intent(Intent.ACTION_VIEW );
            intent.setData(Uri.parse("http://www.kiumiu.com"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity( intent );
		} else if(id == 4) {
			//Toast.makeText(mContext, getResources().getString(R.string.notready), Toast.LENGTH_SHORT).show();
			final TextView message = new TextView(getActivity());
			message.setTextSize(18);
			  // i.e.: R.string.dialog_message =>
			            // "Test this dialog following the link to dtmilano.blogspot.com"
			final SpannableString s = 
			               new SpannableString(mContext.getText(R.string.translation_msg));
			Linkify.addLinks(s, Linkify.EMAIL_ADDRESSES);
			message.setText(s);
			message.setMovementMethod(LinkMovementMethod.getInstance());
			
			AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        	dialog.setTitle(getResources().getString(R.string.translation_title));
        	dialog.setView(message);
        	dialog.setPositiveButton(getResources().getString(R.string.translation_ok), new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(Intent.ACTION_VIEW );
		            intent.setData(Uri.parse("http://www.kiumiu.com/translation"));
		            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		            startActivity( intent );
				}
        		
        	});
        	dialog.setNegativeButton(getResources().getString(R.string.dismiss), null);
        	dialog.show();
		}
	}
}
