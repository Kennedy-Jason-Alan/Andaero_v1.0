package com.andaero.app.fragments;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.andaero.app.MainActivity;
import com.andaero.app.R;
import com.andaero.app.utili.FragmentAnimator;
import com.andaero.app.utili.JSONParser;
import com.androidquery.AQuery;

public class ListViewFragmentOLD extends ListFragment {
	Context context;
	private Activity c;
	final AQuery aq = new AQuery(c);

	// Bundle Key names
	private static final String KEY_URL = "KEY_URL";
	private static final String KEY_RES_FILE = "KEY_RES_FILE";
	private static final String KEY_IS_RAW_RES = "KEY_IS_RAW_RES";

	private static final int MENU_TOGGLE = 0;

	// JSON Node names
	private static final String TAG_ID = "_id";
	private static final String TAG_LABEL = "label";
	private static final String TAG_TITLE = "title";
	private static final String TAG_DISCR = "description";
	private static final String TAG_CONT_ID = "containerID";
	private static final String TAG_IS_RAW_RES = "isRawRes";
	private static final String TAG_RES_FILE = "resFile";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.listview, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Get the string to query from last Fragment and pass it to this
		// Fragment
		Bundle args = this.getArguments();

		boolean rawRes = args.getBoolean(KEY_IS_RAW_RES);
		String url = args.getString(KEY_URL);
		int fileName = args.getInt(KEY_RES_FILE);

		this.getJsonFile(url, rawRes, fileName);

	}

	public void getJsonFile(String url, boolean rawRes, int fileName) {

		if (rawRes == true) {
			getFromRawRes(fileName);
		} else {
			getFromURL(url);
		}
	}

	public void getFromRawRes(int fileName) {
		InputStream file = getResources().openRawResource(fileName);
		JSONParser jParser = new JSONParser();
		JSONArray json = jParser.getJSONFromRes(file);
		callback(json);
	}

	private void getFromURL(String url) {
		// TODO
	}

	public void callback(JSONArray json) {
		if (json != null) {
			// Hashmap for ListView
			final ArrayList<HashMap<String, String>> mList = new ArrayList<HashMap<String, String>>();
			try {
				// Parse the string to a JSON object
				for (int i = 0; i < json.length(); i++) {
					JSONObject json_data = json.getJSONObject(i);

					// Storing each json item in variable
					String id = json_data.getString(TAG_ID);
					String label = json_data.getString(TAG_LABEL);
					String title = json_data.getString(TAG_TITLE);
					String description = json_data.getString(TAG_DISCR);
					String containerID = json_data.getString(TAG_CONT_ID);
					String isRawRes = json_data.getString(TAG_IS_RAW_RES);
					String resFile = json_data.getString(TAG_RES_FILE);

					// creating new HashMap
					HashMap<String, String> map = new HashMap<String, String>();

					// adding each child node to HashMap key => value
					map.put(TAG_ID, id);
					map.put(TAG_LABEL, label);
					map.put(TAG_TITLE, title);
					map.put(TAG_DISCR, description);
					map.put(TAG_CONT_ID, containerID);
					map.put(TAG_IS_RAW_RES, isRawRes);
					map.put(TAG_RES_FILE, resFile);

					// adding HashList to ArrayList
					mList.add(map);
				}
			} catch (JSONException e) {
				Log.e("log_tag", "Error parsing data " + e.toString());
			}

			// create the list item mapping
			String[] from = new String[]{TAG_LABEL, TAG_TITLE, TAG_DISCR, TAG_RES_FILE, TAG_IS_RAW_RES, TAG_CONT_ID};
			int[] to = new int[]{R.id.listLabel, R.id.listTitle, R.id.listDiscription, R.id.listURI, R.id.listAdapterID, R.id.listContID};
			
			// Updating parsed JSON data into ListView
			SimpleAdapter adapter = new SimpleAdapter(getActivity(), mList, R.layout.list_item, from, to);
			setListAdapter(adapter);

			// selecting single ListView item
			final ListView lv = getListView();

			// Launching new screen on Selecting Single ListItem
			lv.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
					MainActivity.mLayout.toggleSidebar();
					setHasOptionsMenu(true);

					FragmentManager fm = getFragmentManager();
					final FragmentTransaction lcFT = fm.beginTransaction();
					FragmentAnimator.animateOut(lcFT);

					final Bundle args = new Bundle();

					String resFile = ((TextView) view.findViewById(R.id.listURI)).getText().toString();
					int passResFile = getResources().getIdentifier(resFile, "raw", "com.andaero.app");
					args.putInt("KEY_RES_FILE", passResFile);

//					String isRawRes = ((TextView) view.findViewById(R.id.listIsRawRes)).getText().toString();
					boolean isRawRes = true;
					args.putBoolean("KEY_IS_RAW_RES", isRawRes);

					// Delayed to improve animations
					final Handler handler = new Handler();
					handler.postDelayed(new Runnable() {
						public void run() {
							ListViewFragmentOLD lvf = new ListViewFragmentOLD();
							lcFT.replace(R.id.listContainer, lvf).commit();
							lvf.setArguments(args);
						}
					}, 300);
				}
			});
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// TODO
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		menu.add(0, 2, 0, "Menu toggle").setIcon(R.drawable.ic_nav_toggle)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
	}
}
