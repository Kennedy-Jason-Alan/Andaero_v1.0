package com.andaero.app.utili;

import com.andaero.app.R;

import android.app.Activity;
import android.app.FragmentTransaction;

public class FragmentAnimator extends Activity{
	
	public static void animateOut(FragmentTransaction ft) {
	    ft.setCustomAnimations(R.anim.slide_in, R.anim.hyperspace_out, R.anim.hyperspace_in,  R.anim.slide_out);
	  }

}
