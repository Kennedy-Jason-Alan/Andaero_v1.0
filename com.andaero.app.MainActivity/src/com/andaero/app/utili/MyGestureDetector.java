package com.andaero.app.utili;

import com.andaero.app.MainActivity;

import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;

public class MyGestureDetector extends SimpleOnGestureListener {

	// these constants are used for onFling
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

		if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
			return false;
		}
		// right to left swipe
		if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
			MainActivity.mLayout.closeSidebar();
			// left to right swipe
		} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
			MainActivity.mLayout.openSidebar();
		}

		return false;
	}

	// Return true from onDown for the onFling event to register
	@Override
	public boolean onDown(MotionEvent e) {
		return true;
	}
}
