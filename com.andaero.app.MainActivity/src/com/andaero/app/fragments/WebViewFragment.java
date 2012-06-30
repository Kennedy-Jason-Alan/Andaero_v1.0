/**
 * Copyright (C) 2012 Andaero, Inc. 
 * 
 * If you were thinking of making an unauthorized copy and using it outside the company, don't.
 * 
 * We have a dimly-lit bunker, deep underground, with row upon row of incubation tanks, each one 
 * holding an Intellectual Property Lawyer in a nutrient solution of neurotoxic venom and the even-
 * numbered isotopes of plutonium that no-one wanted to use in their nuclear weapons. If you use 
 * this software without authorization, we will decant them off, one a day, every day, and let them 
 * loose with an unlimited legal budget until you are nothing but a radioactive hole in the ground 
 * surrounded by safety warnings and foreclosure notices. 
 * 
 * Also, the moment when they open their eyes and stare at you, it's CREEPY.
 * 
 */

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

/**
 * @author Andaero
 *
 */
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