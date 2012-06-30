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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;

/**
 * @author Andarero
 *
 */
public class JSONArrayAdapter extends BaseAdapter implements Filterable {

	private JSONArray data;
	private String idField;
	private InternalSimpleAdapter internalAdapter;

	@SuppressWarnings({"rawtypes", "unchecked"})
	public JSONArrayAdapter(Context context, JSONArray data, int resource, String[] from, int[] to, String idField) {

		this.data = data;
		this.idField = idField;
		List simpleData = jsonToMapList(data, from);
		internalAdapter = new InternalSimpleAdapter(context, simpleData, resource, from, to);
	}

	public JSONArrayAdapter(Context context, JSONArray data, int resource, String[] from, int[] to) {
		this(context, data, resource, from, to, null);
	}

	@SuppressWarnings("rawtypes")
	private List jsonToMapList(JSONArray data, String[] fields) {

		List<Map> listData = new ArrayList<Map>();
		HashMap<String, String> item;

		for (int i = 0; i < data.length(); i++) {
			JSONObject o = data.optJSONObject(i);
			if (o != null) {
				item = new HashMap<String, String>();
				for (int j = 0; j < fields.length; j++) {
					String fname = fields[j];
					item.put(fname, optString(o, fname));
				}
				listData.add(item);
			}
		}
		return listData;

	}

	private String optString(JSONObject o, String key) {
		String s = o.optString(key);
		if (s == null || s.equals("null")) {
			return null;
		} else {
			return s;
		}
	}

	public SimpleAdapter unwrap() {
		return internalAdapter;
	}

	public int getCount() {
		return internalAdapter.getCount();
	}

	public Object getItem(int position) {
		return data.optJSONObject(position);
	}

	public long getItemId(int position) {
		if (idField != null) {
			JSONObject o = data.optJSONObject(position);
			if (o != null) {
				return o.optLong(idField, 0);
			}
		}
		return internalAdapter.getItemId(position);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		return internalAdapter.getView(position, convertView, parent);
	}

	public ViewBinder getViewBinder() {
		return internalAdapter.getViewBinder();
	}

	public void setViewBinder(ViewBinder viewBinder) {
		internalAdapter.setViewBinder(viewBinder);
	}

	public void setViewImage(ImageView v, int value) {
		v.setImageResource(value);
	}

	public void setViewImage(ImageView v, String value) {
		try {
			v.setImageResource(Integer.parseInt(value));
		} catch (NumberFormatException nfe) {
			v.setImageURI(Uri.parse(value));
		}
	}

	public void setViewText(TextView v, String text) {
		v.setText(text);
	}

	public Filter getFilter() {
		return internalAdapter.getFilter();
	}

	class InternalSimpleAdapter extends SimpleAdapter {

		public InternalSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
			super(context, data, resource, from, to);
		}

		@Override
		public void setViewImage(ImageView v, int value) {
			JSONArrayAdapter.this.setViewImage(v, value);
		}

		@Override
		public void setViewImage(ImageView v, String value) {
			JSONArrayAdapter.this.setViewImage(v, value);
		}

		@Override
		public void setViewText(TextView v, String text) {
			JSONArrayAdapter.this.setViewText(v, text);
		}

	}

}
