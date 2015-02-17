package com.beertap.ontapadapters;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beertap.ontapdatamodels.Beer;
import com.beertap.ontapstl.R;

@SuppressLint("InflateParams")
public class BeerItemAdapter extends BaseAdapter {
	Context context;
	List<Beer> rowItem;
	
	public BeerItemAdapter(Context context, List<Beer> rowItem){
		this.context = context;
		this.rowItem = rowItem;
	}
	
    @Override
    public int getCount() {

        return rowItem.size();
    }

    @Override
    public Object getItem(int position) {

        return rowItem.get(position);
    }

    @Override
    public long getItemId(int position) {

        return rowItem.indexOf(getItem(position));
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
    	if(convertView == null){
    		LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    		convertView = mInflater.inflate(R.layout.fragment_beer_item, null);
    	}
    	
    	TextView topText = (TextView)convertView.findViewById(R.id.textBeerTitle);
    	TextView bottomText = (TextView)convertView.findViewById(R.id.textBeerBrewery);
    	Beer row_position = rowItem.get(position);

    	if(topText != null){
        	topText.setTypeface(Typeface.createFromAsset(context.getAssets(),
    				"font/chalkdust.ttf"));
        	topText.setTextColor(Color.WHITE);
    		topText.setText(row_position.getName());  		
    		
    		float theSRM = 0;
    		if(row_position.getSRM() != null)
    		{
    			theSRM =  Float.parseFloat(row_position.getSRM().toString());
    		}
    		if(theSRM >=22 )
    			topText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.beerglass_dark, 0, 0, 0);
    			else if (theSRM >= 17)
    				topText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.beerglass_lowdark, 0, 0, 0);
    			else if(theSRM >=11)
    				topText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.beerglass_med, 0, 0, 0);
    			else if(theSRM >= 7)
    				topText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.beerglass_lowcopper, 0, 0, 0);
    			else if(theSRM > 0)
    				topText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.beerglass_light, 0, 0, 0);
    			else
    				topText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.beerglass_empty, 0, 0, 0);
    	}
    	if(bottomText != null){
        	bottomText.setTypeface(Typeface.createFromAsset(context.getAssets(),
    				"font/chalkdust.ttf"));
        	bottomText.setTextColor(Color.parseColor("#FF8800"));
    		bottomText.setText(row_position.getBreweryName());
    	}
    	    	
    	return convertView;
    	
    }

}
