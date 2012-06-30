/**
 * Copyright ©2012 Andaero, Inc.
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

package com.andaero.app.regulatory;

import com.andaero.app.R;
import com.andaero.app.fragments.ListViewFragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author Andaero
 *
 */
public class RegulatoryDiscription extends Fragment {

	Bundle args = new Bundle();

	String KEY_DISCRIPTION_TITLE = "KEY_DISCRIPTION_TITLE";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.discription_view, container, false);

		args = getArguments();

		TextView title = (TextView) view.findViewById(R.id.discriptionTitle);
		String keyDiscriptionTitle = args.getString(KEY_DISCRIPTION_TITLE);
		title.setText(keyDiscriptionTitle);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		FragmentManager fm = getFragmentManager();
//		final FragmentTransaction vcFT = fm.beginTransaction();
		final FragmentTransaction lcFT = fm.beginTransaction();
//		FragmentAnimator.animateOut(lcFT);

		// RegulatoryDiscription rd = new RegulatoryDiscription();
		// vcFT.replace(R.id.discriptionContainer,
		// rd).addToBackStack(null).commit();

//		String uri = "http://192.168.1.17/andaero/php/regulatory_list.json";
		String uri = "html/json/regulatory_list.json";
		args.putString("KEY_URI", uri);
		
		String adptrID = "0";
		args.putString("KEY_ADPTR_ID", adptrID);

		// Delayed to improve animations
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				ListViewFragment lvf = new ListViewFragment();
				lcFT.replace(R.id.discriptionListContainer, lvf).commit();
				lvf.setArguments(args);
			}
		}, 600);

		setHasOptionsMenu(true);

	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "Dashboard").setIcon(R.drawable.ic_dashboard)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
	}

}
