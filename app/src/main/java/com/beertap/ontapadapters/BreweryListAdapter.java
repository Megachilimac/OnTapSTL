package com.beertap.ontapadapters;

import java.util.List;

import com.beertap.ontapdatamodels.Brewery;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BreweryListAdapter extends ArrayAdapter<Brewery> {

	public BreweryListAdapter(Context context, List<Brewery> items) {

		super(context, android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, items);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = super.getView(position, convertView, parent);
		Brewery currentItem = getItem(position);
		TextView textView = (TextView) view.findViewById(android.R.id.text1);
		textView.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
				"font/chalkdust.ttf"));
		textView.setTextColor(Color.WHITE);
		textView.setText(currentItem.getShortName());
		return view;
	}
}
