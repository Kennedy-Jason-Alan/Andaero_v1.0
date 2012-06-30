package com.andaero.app.utili;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

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
