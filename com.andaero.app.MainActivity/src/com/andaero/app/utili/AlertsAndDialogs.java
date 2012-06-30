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

package com.andaero.app.utili;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * @author Andaero
 *
 */
public class AlertsAndDialogs extends Activity {
	public static void sdCardMissing(Context context) {
		// Pass context to AlertDialog.Builder
		AlertDialog sdCardMissingDialog = new AlertDialog.Builder(context).create();
		sdCardMissingDialog.setTitle("External Storage State");
		sdCardMissingDialog.setMessage("Your SD-Card is not mounted!  If the device is plugged into a computer via the USB, please disconect the device.");
		// alertDialog.setIcon(R.drawable.icon);
		sdCardMissingDialog.show();
	}

//	public static void dashBoard(Context mContext) {
//		AlertDialog.Builder builder;
//		Dialog dbDialog;
//
//		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
//		View layout = inflater.inflate(R.layout.dashboard_dialog, null);
//
//		builder = new AlertDialog.Builder(mContext);
//		builder.setView(layout);
//		dbDialog = builder.create();
//		dbDialog.show();
//	}

}
