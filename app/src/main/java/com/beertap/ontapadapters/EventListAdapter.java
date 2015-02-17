package com.beertap.ontapadapters;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

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

import com.beertap.ontapdatamodels.BeerEvent;
import com.beertap.ontapstl.R;

@SuppressLint({ "InflateParams", "SimpleDateFormat" })
public class EventListAdapter extends BaseAdapter {
	Context context;
	List<BeerEvent> rowItem;
	
	public EventListAdapter(Context context, List<BeerEvent> rowItem){
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
	public View getView(int position, View convertView, ViewGroup parent) {
    	if(convertView == null){
    		LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    		convertView = mInflater.inflate(R.layout.eventlist_item, null);
    	}
    	
    	TextView title = (TextView)convertView.findViewById(R.id.textEventTitle);
    	TextView start = (TextView)convertView.findViewById(R.id.textEventStartDate);
    	
    	if((title != null) && (start != null))
    	{
    		Typeface chalky = Typeface.createFromAsset(this.context.getAssets(),"font/chalkdust.ttf");
        	
    		title.setTypeface(chalky);    	
        	title.setTextColor(Color.WHITE);      	
        	
        	//start.setTypeface(chalky);
        	start.setTextColor(Color.parseColor("#FF8800"));
        	    	
        	BeerEvent eventItem = rowItem.get(position);
        	title.setText(eventItem.getName());
        	
        	SimpleDateFormat monthDay = new SimpleDateFormat("MMM d");
        	SimpleDateFormat startTime = new SimpleDateFormat("h:mm a");
        	startTime.setTimeZone(TimeZone.getTimeZone("UTC"));
        	String utcTime = startTime.format(eventItem.getStartDate());     
        	
        	StringBuilder timeanddateBuilder = new StringBuilder(monthDay.format(eventItem.getStartDate()));
        	timeanddateBuilder.append("  @  ");
        	timeanddateBuilder.append(utcTime);
        	        	
        	String details = timeanddateBuilder.toString();
        	start.setText(details);
    	}
    	    	
		return convertView;
	}
	
}