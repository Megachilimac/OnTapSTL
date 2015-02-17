package com.beertap.ontapstl;

import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.beertap.ontapadapters.EventListAdapter;
import com.beertap.ontapdatamodels.BeerEvent;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class EventsFragment extends ListFragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_events, container,
				false);
		
		
		ParseObject.registerSubclass(BeerEvent.class);
		ParseQuery<BeerEvent> queryEventList = ParseQuery.getQuery(BeerEvent.class);

		Date today = new Date();
		queryEventList.whereGreaterThanOrEqualTo("eventStop",today);
		queryEventList.orderByAscending("eventStart");
		queryEventList.findInBackground(new FindCallback<BeerEvent>() {
			public void done(List<BeerEvent> eventList, ParseException e) {
				if (e == null) {
					Log.d("events", "Retrieved " + eventList.size() + " events");
					setListAdapter(new EventListAdapter(getActivity(),eventList));
				}
			}
		});
				
		return rootView;
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		BeerEvent selectedEvent = (BeerEvent) getListAdapter().getItem(position);
		Intent intent = new Intent(getActivity(), EventDetailActivity.class)
				.putExtra("selected_event", selectedEvent.getObjectId());
		startActivity(intent);
	}
}
