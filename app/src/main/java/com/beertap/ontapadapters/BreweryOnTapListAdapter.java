package com.beertap.ontapadapters;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.beertap.ontapdatamodels.Beer;
import com.beertap.ontapstl.R;

@SuppressLint("InflateParams")
public class BreweryOnTapListAdapter extends ArrayAdapter<Beer> {

	Context context;
	List<Beer> theBeers;

	public BreweryOnTapListAdapter(Context context, List<Beer> beerItems) {
		super(context, R.layout.control_beer, beerItems);

		this.context = context;
		this.theBeers = beerItems;
	}

	@Override
	public int getCount() {

		return theBeers.size();
	}

	@Override
	public Beer getItem(int position) {

		return theBeers.get(position);
	}

	@Override
	public long getItemId(int position) {

		return theBeers.indexOf(getItem(position));

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = super.getView(position, convertView, parent);

		TextView txtTitle = (TextView) view
				.findViewById(R.id.textBeer);

		txtTitle.setTypeface(Typeface.createFromAsset(context.getAssets(),
				"font/chalkdust.ttf"));
		txtTitle.setTextColor(Color.WHITE);

		if(theBeers != null){
			
		
		Beer beerItem = theBeers.get(position);
		txtTitle.setText(beerItem.getName());

		float theSRM = 0;

		
		if(beerItem.getSRM() != null){
		try {
			theSRM = Float.parseFloat(beerItem.getSRM());		    
		} catch(NumberFormatException nfe) {
		   System.out.println("Could not parse " + nfe);
		} 
		}
		if(theSRM >=22 )
		txtTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.beerglass_dark, 0, 0, 0);
		else if (theSRM >= 17)
			txtTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.beerglass_lowdark, 0, 0, 0);
		else if(theSRM >=11)
			txtTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.beerglass_med, 0, 0, 0);
		else if(theSRM >= 7)
			txtTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.beerglass_lowcopper, 0, 0, 0);
		else if(theSRM > 0)
			txtTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.beerglass_light, 0, 0, 0);
		else
			txtTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.beerglass_empty, 0, 0, 0);
		}
		
		return view;
	}

}
