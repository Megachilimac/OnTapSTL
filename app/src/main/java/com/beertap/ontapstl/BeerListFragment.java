package com.beertap.ontapstl;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.beertap.ontapadapters.BeerItemAdapter;
import com.beertap.ontapdatamodels.Beer;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class BeerListFragment extends ListFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_beerlist, container,
				false);

		rootView.setBackgroundResource(R.drawable.background);

		getActivity().getActionBar().setTitle("Beers");
		ParseObject.registerSubclass(Beer.class);
		ParseQuery<Beer> queryBeerList = ParseQuery.getQuery(Beer.class);
		queryBeerList.whereEqualTo("ontap_now", (Boolean) true);
		queryBeerList.whereNotEqualTo("heritageFest", (Boolean) true);
		queryBeerList.orderByAscending("beerName");

		queryBeerList.setLimit(1000);
		final Context parentContext = getActivity();
		queryBeerList.findInBackground(new FindCallback<Beer>() {
			public void done(List<Beer> beerList, ParseException e) {
				if (e == null) {
					Log.d("beers", "Retrieved " + beerList.size() + " beers");
					setListAdapter(new BeerItemAdapter(parentContext, beerList));
				}
			}
		});

		return rootView;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Beer selectedBeer = (Beer) getListAdapter().getItem(position);
		Bundle arguments = new Bundle();
		arguments.putString("selected_beer", selectedBeer.getObjectId());
		
		BeerDetailView beerView = new BeerDetailView();
		beerView.setArguments(arguments);		
		
		FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.content_frame,beerView);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.addToBackStack(null);
		ft.commit();
	}
}
