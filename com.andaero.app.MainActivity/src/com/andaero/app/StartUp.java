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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.andaero.app.utili.AlertsAndDialogs;
import com.andaero.app.utili.DeviceUuidFactory;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

/**
 * @author Andaero
 *
 */
public class StartUp extends Activity {

	/**
	 * -- Called when the activity is first created.
	 * ==============================================================
	 **/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Run the UuidFactory Class to write the device ID to an XML file.
		DeviceUuidFactory duf = new DeviceUuidFactory(this);
		duf.getDeviceUuid();

		// // Get the device ID from the device_id.xml & pass it to a string
		// SharedPreferences dID = this.getSharedPreferences("device_id", 0);
		// String id = dID.getString("device_id", null);
		FirstRun();
	}

	private void FirstRun() {
		SharedPreferences settings = this.getSharedPreferences("Andaero", 0);
		boolean firstrun = settings.getBoolean("firstrun", true);
		if (firstrun) { // Checks to see if we've ran the application b4
			SharedPreferences.Editor e = settings.edit();
			e.putBoolean("firstrun", false);
			e.commit();
			// If not, run these methods:
			SetDirectory();
			Intent home = new Intent(StartUp.this, MainActivity.class);
			startActivity(home);

		} else { // Otherwise start the application here:

			Intent home = new Intent(StartUp.this, MainActivity.class);
			startActivity(home);
		}
	}

	/**
	 * -- Check to see if the sdCard is mounted and create a directory w/in it
	 * ========================================================================
	 **/
	private void SetDirectory() {
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {

			extStorageDirectory = Environment.getExternalStorageDirectory().toString();

			File dbDirectory = new File(extStorageDirectory + "/Andaero/dB/");
			File ssDirectory = new File(extStorageDirectory + "/Andaero/screenShots/");
			// Create
			// a
			// File
			// object
			// for
			// the
			// parent
			// directory
			dbDirectory.mkdirs();// Have the object build the directory
			// structure, if needed.
			ssDirectory.mkdirs();
			CopyAssets(); // Then run the method to copy the db.

		} else if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED_READ_ONLY)) {

			AlertsAndDialogs.sdCardMissing(this);
		}

	}

	/**
	 * -- Copy the db from the assets folder to the sdCard
	 * ===========================================================
	 **/
	private void CopyAssets() {
		AssetManager assetManager = getAssets();
		String[] files = null;
		try {
			files = assetManager.list("");
		} catch (IOException e) {
			Log.e("tag", e.getMessage());
		}
		for (int i = 0; i < files.length; i++) {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = assetManager.open(files[i]);
				out = new FileOutputStream(extStorageDirectory + "/Andaero/dB/" + files[i]);
				copyFile(in, out);
				in.close();
				in = null;
				out.flush();
				out.close();
				out = null;
			} catch (Exception e) {
				Log.e("tag", e.getMessage());
			}
		}
	}

	private void copyFile(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
	}

	/**
	 * -- Variables -- ====================================================
	 **/
	boolean mExternalStorageAvailable = false;
	boolean mExternalStorageWriteable = false;
	String extStorageDirectory = Environment.getExternalStorageDirectory().toString();

}
