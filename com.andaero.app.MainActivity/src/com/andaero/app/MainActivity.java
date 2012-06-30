package com.andaero.app;

import com.andaero.app.utili.BaseActivity;
import com.andaero.app.utili.MyGestureDetector;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.*;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends BaseActivity {

	FragmentManager fm = getFragmentManager();
	Fragment vC = fm.findFragmentById(R.id.viewContainer);
	Fragment lC = fm.findFragmentById(R.id.listContainer);

	String DNS = "http://192.168.1.17/";
	public final static String TAG = "Demo";
	protected ListView mList;
	public static AnimationLayout mLayout;

	// this constants are used for onFling
	private GestureDetector gestureDetector;

	/**
	 * -- ON CREATE
	 * ================================================================>
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		populateActionBar();
		addFragments();
		animateLayout();
	}

	/**
	 * -- ACTIONBAR
	 * ================================================================>
	 * 
	 * Inflate the Action Bar Menu -------------------------------------
	 */
	public void populateActionBar() {
		ActionBar ab = getActionBar();
		ab.setDisplayShowTitleEnabled(false);

		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.action_list, android.R.layout.simple_spinner_dropdown_item);
		ab.setListNavigationCallbacks(mSpinnerAdapter, null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionbar_menu, menu);
		return true;
	}

	/**
	 * -- ADD FRAGMENTS
	 * ================================================================>
	 */
	public void addFragments() {

		if (vC == null) {
			FragmentTransaction ft = fm.beginTransaction();
			ft.add(R.id.viewContainer, new DashBoard()).commit();
		}
	}

	/**
	 * -- GESTURE ANIMATE THE VIEW
	 * ================================================================>
	 */
	public void animateLayout() {
		mLayout = (AnimationLayout) findViewById(R.id.animation_layout);

		gestureDetector = new GestureDetector(this.getApplicationContext(), new MyGestureDetector());
		View swipeView = (View) findViewById(R.id.main_layout_content);
		swipeView.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (gestureDetector.onTouchEvent(event) && lC != null) {
					return true;//Only if there is data in the lC container!!
				}
				return false;
			}
		});
	}

}
