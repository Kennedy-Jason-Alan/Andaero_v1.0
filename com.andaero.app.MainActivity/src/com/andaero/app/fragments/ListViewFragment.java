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

package com.andaero.app.fragments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.andaero.app.MainActivity;
import com.andaero.app.R;
import com.andaero.app.utili.FragmentAnimator;
import com.andaero.app.utili.JSONArrayAdapter;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Andaero
 *
 */
public class ListViewFragment extends ListFragment implements OnItemClickListener {

	static String json = "";
	static String line = "";
	Context context;

	// Bundle Key names
	String KEY_URI = "KEY_URI";
	String KEY_ADPTR_ID = "KEY_ADPTR_ID";
	String KEY_NAVIGATION_TITLE = "KEY_NAVIGATION_TITLE";
	String KEY_CONTAINER_ID = "KEY_CONTAINER_ID";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.listview, container, false);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Bundle getArgs = this.getArguments();
		String URI = getArgs.getString(KEY_URI);

		new GetJSONTask().execute(URI);
	}

	class GetJSONTask extends AsyncTask<String, Integer, String> {

		protected String doInBackground(String... arg0) {

			String uri = arg0[0];

			InputStream is = null;

			if (uri.contains("http") == true) {// Get JSON from URL
				try {
					DefaultHttpClient httpClient = new DefaultHttpClient();
					HttpPost httpPost = new HttpPost(uri);
					HttpResponse httpResponse = httpClient.execute(httpPost);
					HttpEntity httpEntity = httpResponse.getEntity();
					is = httpEntity.getContent();

					BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
					while ((line = rd.readLine()) != null) {
						json += line;
					}
					rd.close();
					return json;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			} else {// Get JSON from Assets

				Writer writer = new StringWriter();
				char[] buffer = new char[1024];

				try {
					InputStream jsonFile = getActivity().getAssets().open(uri);
					Reader reader = new BufferedReader(new InputStreamReader(jsonFile, "UTF-8"));
					int n;
					while ((n = reader.read(buffer)) != -1) {
						writer.write(buffer, 0, n);
					}
					jsonFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				json = writer.toString();
				// return JSON String
				return json;
			}
		}

		@Override
		protected void onPostExecute(String result) {
			try {
				showData(result);
			} catch (JSONException e) {
				e.printStackTrace();
				Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void showData(String json) throws JSONException {
		JSONObject o = new JSONObject(json);
		JSONArray data = o.getJSONArray("results");

		Bundle getArgs = this.getArguments();
		String adptrID = getArgs.getString(KEY_ADPTR_ID);

		Toast.makeText(getActivity(), adptrID, Toast.LENGTH_LONG).show();

		// if (adptrID == "2") {
		// String[] from = new String[]{"label", "title", "uri", "adapterID",
		// "containerID"};
		// int[] to = new int[]{R.id.listLabel, R.id.listTitle, R.id.listURI,
		// R.id.listAdapterID, R.id.listContID};
		// ListAdapter adapter = new JSONArrayAdapter(getActivity(), data,
		// R.layout.list_item, from, to, null);
		// getListView().setOnItemClickListener(this);
		// getListView().setAdapter(adapter);
		// }
		if (adptrID != "3") { // Adatper for 3 items
			String[] from = new String[]{"label", "title", "description", "uri", "adapterID", "containerID"};
			int[] to = new int[]{R.id.listLabel, R.id.listTitle, R.id.listDiscription, R.id.listURI, R.id.listAdapterID, R.id.listContID};
			ListAdapter adapter = new JSONArrayAdapter(getActivity(), data, R.layout.list_item, from, to, null);
			getListView().setOnItemClickListener(this);
			getListView().setAdapter(adapter);
		}

	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		final Bundle args = new Bundle();

		Bundle getArgs = this.getArguments();
		String containerID = getArgs.getString(KEY_CONTAINER_ID);

		TextView title = (TextView) getActivity().findViewById(R.id.navigationTitle);
		String navTitle = ((TextView) view.findViewById(R.id.listTitle)).getText().toString();
		title.setText(navTitle);

		String uri = ((TextView) view.findViewById(R.id.listURI)).getText().toString();
		args.putString("KEY_URI", uri);

		String adptrID = ((TextView) view.findViewById(R.id.listAdapterID)).getText().toString();
		args.putString("KEY_ADPTR_ID", adptrID);

		String contID = ((TextView) view.findViewById(R.id.listContID)).getText().toString();
		args.putString("KEY_CONTAINER_ID", contID);

		FragmentManager fm = getFragmentManager();
		final FragmentTransaction lcFT = fm.beginTransaction();
		FragmentAnimator.animateOut(lcFT);
		ListViewFragment lvf = new ListViewFragment();

		lcFT.replace(R.id.listContainer, lvf).commit();
		lvf.setArguments(args);

		// if (containerID == "listContainer") {
		// lcFT.replace(R.id.listContainer, lvf).commit();
		// lvf.setArguments(args);
		// } else {
		// lcFT.replace(R.id.discriptionListContainer, lvf).commit();
		// lvf.setArguments(args);
		// }

		MainActivity.mLayout.toggleSidebar();
		setHasOptionsMenu(true);

	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		menu.add(0, 2, 0, "Menu toggle").setIcon(R.drawable.ic_nav_toggle).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
	}

}
