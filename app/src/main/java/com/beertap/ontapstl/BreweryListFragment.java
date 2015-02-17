package com.beertap.ontapstl;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.beertap.ontapadapters.BreweryListAdapter;
import com.beertap.ontapdatamodels.Brewery;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class BreweryListFragment extends ListFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_brewerylist,
				container, false);

		rootView.setBackgroundResource(R.drawable.background);
		getActivity().getActionBar().setTitle("Breweries");

		ParseObject.registerSubclass(Brewery.class);
		ParseQuery<Brewery> queryBreweryList = ParseQuery
				.getQuery(Brewery.class);

		queryBreweryList.whereEqualTo("breweryOpen", (Boolean) true);
		queryBreweryList.orderByAscending("Brewery_name");
		queryBreweryList.findInBackground(new FindCallback<Brewery>() {
			public void done(List<Brewery> breweryList, ParseException e) {
				if (e == null) {
					Log.d("breweries", "Retrieved " + breweryList.size()
							+ " breweries");

					setListAdapter(new BreweryListAdapter(getActivity(),
							breweryList));
				}
			}
		});

		return rootView;

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Brewery selectedBrewery = (Brewery) getListAdapter().getItem(position);
		
		Bundle arguments = new Bundle();
		arguments.putString("selected_brewery", selectedBrewery.getObjectId());
		
		BreweryDetailView breweryView = new BreweryDetailView();
		breweryView.setArguments(arguments);		
		
		FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.content_frame,breweryView);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.addToBackStack(null);
		ft.commit();

	}

}
