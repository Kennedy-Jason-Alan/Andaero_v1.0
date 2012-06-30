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

package com.andaero.app;

import com.andaero.app.R;
import com.andaero.app.regulatory.RegulatoryDiscription;
import com.andaero.app.utili.FragmentAnimator;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * @author Andaero
 *
 */
public class DashBoard extends Fragment implements View.OnClickListener {

	String DNS = "http://192.168.1.17/";

	ImageButton btn1, btn2, btn3, btn4, btn5, btn6;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dashboard, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		btn1 = (ImageButton) getView().findViewById(R.id.regulatoryBtn);
		btn2 = (ImageButton) getView().findViewById(R.id.formsBtn);
		btn3 = (ImageButton) getView().findViewById(R.id.manualsBtn);
		btn4 = (ImageButton) getView().findViewById(R.id.logsBtn);
		btn5 = (ImageButton) getView().findViewById(R.id.toolsBtn);
		btn6 = (ImageButton) getView().findViewById(R.id.bookmarksBtn);

		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		btn6.setOnClickListener(this);

		setHasOptionsMenu(true);

	}

	public void onClick(View v) {

		Bundle args = new Bundle();

		FragmentManager fm = getFragmentManager();
		final FragmentTransaction vcFT = fm.beginTransaction();
		FragmentAnimator.animateOut(vcFT);

		switch (v.getId()) {

			case R.id.regulatoryBtn :

				String keyDiscriptionTitle = "Regulatory Guidance Library (RGL)";
				args.putString("KEY_DISCRIPTION_TITLE", keyDiscriptionTitle);

				RegulatoryDiscription rd = new RegulatoryDiscription();
				vcFT.replace(R.id.viewContainer, rd).addToBackStack(null).commit();
				rd.setArguments(args);
				break;

			case R.id.formsBtn :
				// TODO
				break;

			case R.id.manualsBtn :
				// TODO
				break;

			case R.id.logsBtn :
				// TODO
				break;

			case R.id.toolsBtn :
				// TODO
				break;

			case R.id.bookmarksBtn :
				// TODO
				break;
		};
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		
		FragmentManager fm = getFragmentManager();
		Fragment lC = fm.findFragmentById(R.id.listContainer);

		menu.removeItem(1);// DashBoard Item

		if (lC == null) {
			menu.removeItem(2);// MenuToggle Item
		}
	}
}
