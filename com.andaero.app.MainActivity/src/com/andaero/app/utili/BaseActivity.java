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
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import com.andaero.app.DashBoard;
import com.andaero.app.MainActivity;
import com.andaero.app.R;

/**
 * @author Andarero
 *
 */
public class BaseActivity extends Activity {

	/**
	 * ClickHandlers for the ActionBar -----------------------------
	 */

	private static final int DASHBOARD = 1;
	private static final int MENU_TOGGLE = 2;

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		FragmentManager fm = getFragmentManager();
		final FragmentTransaction vcFT = fm.beginTransaction();
		vcFT.setCustomAnimations(R.anim.hyperspace_in, R.anim.slide_out);
		switch (item.getItemId()) {

			case android.R.id.home :
				// app icon in action bar clicked;
				return true;

			case DASHBOARD :
				MainActivity.mLayout.closeSidebar();
				DashBoard db = new DashBoard();
				vcFT.replace(R.id.viewContainer, db).addToBackStack(null).commit();
				return true;

			case MENU_TOGGLE :
				MainActivity.mLayout.toggleSidebar();
				return true;

			default :
				return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * -- Set the Device Keys
	 * ======================================================================
	 **/

	public boolean dispatchKeyEvent(KeyEvent event) {
		boolean sideBarOpen = MainActivity.mLayout.isOpening();
		int backCount = getFragmentManager().getBackStackEntryCount();
		int action = event.getAction();
		int keyCode = event.getKeyCode();

		FragmentManager fm = getFragmentManager();

		WebView scrollView = (WebView) findViewById(R.id.mWebView);

		switch (keyCode) {
			case KeyEvent.KEYCODE_VOLUME_UP :
				if (action == KeyEvent.ACTION_DOWN) {
					scrollView.pageUp(false);
				}
				return true;

			case KeyEvent.KEYCODE_VOLUME_DOWN :
				if (action == KeyEvent.ACTION_DOWN) {
					scrollView.pageDown(false);
				}
				return true;

			case KeyEvent.KEYCODE_BACK :
				if (action == KeyEvent.ACTION_DOWN && backCount == 0 && sideBarOpen == false) {
					onexitNotify();
				} else {
					fm.popBackStack();
				}
				if (action == KeyEvent.ACTION_DOWN && sideBarOpen == true) {
					MainActivity.mLayout.closeSidebar();
				} else {
					// fm.popBackStack();
				}
				return true;

			default :
				return super.dispatchKeyEvent(event);
		}
	}

	/**
	 * Prevent accidental app exit by requiring users to press back twice b4
	 * exiting w/in 4sec
	 */
	private Toast toast;
	private long lastBackPressTime = 0;

	public void onexitNotify() {
		if (this.lastBackPressTime < System.currentTimeMillis() - 4000) {
			toast = Toast.makeText(this, "Press back again within 4sec to close this app", 4000);
			toast.show();
			this.lastBackPressTime = System.currentTimeMillis();
		} else {
			if (toast != null) {
				toast.cancel();
			}
			super.onBackPressed();
		}
	}
}
