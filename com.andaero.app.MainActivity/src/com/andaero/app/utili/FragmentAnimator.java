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

import com.andaero.app.R;

import android.app.Activity;
import android.app.FragmentTransaction;

/**
 * @author Andaero
 *
 */
public class FragmentAnimator extends Activity{
	
	public static void animateOut(FragmentTransaction ft) {
	    ft.setCustomAnimations(R.anim.slide_in, R.anim.hyperspace_out, R.anim.hyperspace_in,  R.anim.slide_out);
	  }

}
