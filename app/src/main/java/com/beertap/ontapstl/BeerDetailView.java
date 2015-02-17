package com.beertap.ontapstl;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beertap.ontapdatamodels.Beer;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class BeerDetailView extends Fragment {

	public static final String ARG_ITEM_ID = "selected_beer";
	private String beerID;
	
	public BeerDetailView()
	{
		
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            beerID = getArguments().getString(ARG_ITEM_ID);
        }        
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    	
    	final View rootView = inflater.inflate(R.layout.fragment_beer_detail, container, false);
    	
    	if(beerID != null)
    	{
    		TextView labelBrewer = (TextView) rootView.findViewById(R.id.labelBrewer);
    		labelBrewer.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
    				"font/chalkdust.ttf"));

    		TextView labelStyle = (TextView) rootView.findViewById(R.id.labelStyle);
    		labelStyle.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
    				"font/chalkdust.ttf"));

    		TextView labelABV = (TextView) rootView.findViewById(R.id.labelABV);
    		labelABV.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
    				"font/chalkdust.ttf"));

    		TextView labelIBU = (TextView) rootView.findViewById(R.id.labelIBU);
    		labelIBU.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
    				"font/chalkdust.ttf"));

    		TextView labelSRM = (TextView) rootView.findViewById(R.id.labelSRM);
    		labelSRM.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
    				"font/chalkdust.ttf"));

    		Parse.initialize(rootView.getContext(), "1LHQvaXkHfxOYx0snQeOTiZ5TP7kp9rmIAePjfDi",
    				"2HyiX7Cq7QTTBTsYSywTdV0dtgl1yxWFTCoLazOw");
    		ParseObject.registerSubclass(Beer.class);
    		ParseQuery<Beer> queryBrewery = ParseQuery.getQuery(Beer.class);

    		queryBrewery.getInBackground(beerID, new GetCallback<Beer>() {
    			public void done(Beer beerObject, ParseException e) {
    				if (e == null) {
    					updateDetails(beerObject);

    				} else {
    					Log.d("brewery", "Error: " + e.getMessage());
    				}
    			}

    			private void updateDetails(Beer beerObject) {

    				getActivity().setTitle("");

    				TextView beerName = (TextView) rootView.findViewById(R.id.textBeerName);
    				setTextField(beerName, beerObject.getName());

    				TextView theBrewer = (TextView) rootView.findViewById(R.id.textBrewer);
    				setTextField(theBrewer, beerObject.getBreweryName());

    				TextView theStyle = (TextView) rootView.findViewById(R.id.textStyle);
    				setTextField(theStyle, beerObject.getStyle());

    				TextView theABV = (TextView) rootView.findViewById(R.id.textABV);
    				setTextField(theABV, beerObject.getABV());

    				TextView theIBU = (TextView) rootView.findViewById(R.id.textIBU);
    				setTextField(theIBU, beerObject.getIBU());

    				TextView theSRM = (TextView) rootView.findViewById(R.id.textSRM);
    				setTextField(theSRM, beerObject.getSRM());

    			}

    			private void setTextField(TextView theField, String theText) {
    				theField.setText(theText);
    				theField.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
    						"font/chalkdust.ttf"));
    			}

    		});
    	}
    	
    	return rootView;
    }
}
