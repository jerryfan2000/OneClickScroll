package com.kiumiu.ca.oneclickscroll;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class mainFrag extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.ocs_fragment, container, false);
		TextView text = (TextView) rootView.findViewById(R.id.textView5);
		text.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW );
	            intent.setData(Uri.parse("http://youtu.be/fxwl8wM-Pkg"));
	            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	            startActivity( intent );
			}
			
		});
		return rootView;
	}
}
