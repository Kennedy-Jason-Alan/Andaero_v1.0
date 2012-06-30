package com.andaero.app.fragments;

import com.andaero.app.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewFragment extends Fragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.detail_fragment, container, false);

		WebView myWebView = (WebView) view.findViewById(R.id.mWebView);
		WebSettings webSettings = myWebView.getSettings();
		webSettings.setJavaScriptEnabled(true); /*
												 * == Links Java to JavaScript
												 * ==
												 */
		// myWebView.addJavascriptInterface(new JavaScriptInterface(this),
		// "Android"); /* == Keeps on web links w/in the webViewer == */
		myWebView.setWebViewClient(new WebViewClient());
		myWebView.loadUrl("http://rgl.faa.gov/Regulatory_and_Guidance_Library/rgWebcomponents.nsf/RGL%20Homepage!OpenForm");
		return view;
	}


}