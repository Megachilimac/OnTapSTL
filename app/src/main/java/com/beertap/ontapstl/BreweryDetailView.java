package com.beertap.ontapstl;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.beertap.ontapadapters.BreweryOnTapListAdapter;
import com.beertap.ontapdatamodels.Beer;
import com.beertap.ontapdatamodels.Brewery;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class BreweryDetailView extends Fragment implements OnClickListener{
	
	public static final String ARG_ITEM_ID = "selected_brewery";
	private String breweryID;
	private Brewery theBrewery;

	private ArrayList<String> beerNames = new ArrayList<String>();

	private List<Beer> beerItems;
	private BreweryOnTapListAdapter ontapAdapter;
	
	
	public BreweryDetailView(){}
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            breweryID = getArguments().getString(ARG_ITEM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    	
    	final View rootView = inflater.inflate(R.layout.fragment_brewery_detail, container, false);
    	
    	 rootView.findViewById(R.id.webButton).setOnClickListener(this);
    	 rootView.findViewById(R.id.locateButton).setOnClickListener(this);
    	 rootView.findViewById(R.id.phoneButton).setOnClickListener(this);
    	 rootView.findViewById(R.id.facebookButton).setOnClickListener(this);
    	
    	if(breweryID != null){
    		
    		ListView beers = (ListView) rootView.findViewById(R.id.breweryBeerList);

    		beerItems = new ArrayList<Beer>();

    		Parse.initialize(rootView.getContext(), "1LHQvaXkHfxOYx0snQeOTiZ5TP7kp9rmIAePjfDi",
    				"2HyiX7Cq7QTTBTsYSywTdV0dtgl1yxWFTCoLazOw");
    		ParseObject.registerSubclass(Brewery.class);
    		ParseQuery<Brewery> queryBrewery = ParseQuery.getQuery(Brewery.class);

    		try {
    			theBrewery = queryBrewery.get(breweryID);
    			getActivity().setTitle("");

    			TextView breweryName = (TextView) rootView.findViewById(R.id.breweryName);
    			breweryName.setText(theBrewery.getFullName() + "\n"
    					+ theBrewery.getAddress());
    			breweryName.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
    					"font/chalkdust.ttf"));

    			TextView breweryDescription = (TextView) rootView.findViewById(R.id.breweryDescription);
    			breweryDescription.setText(theBrewery.getDescription());
    			breweryDescription.setMovementMethod(new ScrollingMovementMethod());

    			breweryDescription.setTypeface(Typeface.createFromAsset(
    					getActivity().getAssets(), "font/chalkdust.ttf"));

    			ParseFile file = (ParseFile) theBrewery.get("Brewery_logo");
    			file.getDataInBackground(new GetDataCallback() {

    				public void done(byte[] data, ParseException e) {
    					if (e == null) {

    						Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
    								data.length);
    						ImageView theLogo = (ImageView) rootView.findViewById(R.id.breweryLogo);

    						theLogo.setImageBitmap(bitmap);

    					} else {
    						// something went wrong
    					}
    				}
    			});

    			ParseObject.registerSubclass(Beer.class);
    			ParseQuery<Beer> queryBeerList = ParseQuery.getQuery(Beer.class);
    			queryBeerList.whereEqualTo("ontap_now", (Boolean) true);
    			queryBeerList
    					.whereEqualTo("breweryName", theBrewery.getShortName());

    			beerNames.clear();
    			List<Beer> beerList = queryBeerList.find();
    			for (Beer theBeer : beerList) {
    				beerNames.add(theBeer.getName());
    				beerItems.add(theBeer);
    			}

    			ontapAdapter = new BreweryOnTapListAdapter(rootView.getContext(), beerItems);
    			beers.setAdapter(ontapAdapter);
    			beers.setOnItemClickListener(new BeerListItemClickListener());

    		} catch (ParseException e1) {

    			e1.printStackTrace();
    		}
    		
    		
    		
    	}
    	
    	return rootView;
    }
    
    
    private void goToWeb(View view) {
    	Uri uriUrl = Uri.parse(theBrewery.getWebAddress());
    	Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
    	startActivity(launchBrowser);
    }

    private void goToMap(View view) {
    	ParseGeoPoint theLocation = theBrewery.getLocation();
    	if (theLocation != null) {
    		String latitude = String.valueOf(theLocation.getLatitude());
    		String longitude = String.valueOf(theLocation.getLongitude());
    		String theName = theBrewery.getShortName();
    		Intent navigation = new Intent(Intent.ACTION_VIEW,
    				Uri.parse("http://maps.google.com/maps?q=loc:" + latitude
    						+ "," + longitude + "(" + theName + ")"));
    		startActivity(navigation);
    	}

    }

    private void goToPhone(View view) {
    	String phone = theBrewery.getPhone();
    	if (!phone.isEmpty()) {
    		Intent callIntent = new Intent(Intent.ACTION_CALL);
    		
    		callIntent.setData(Uri.parse("tel:" + phone));
    		startActivity(callIntent);
    	}

    }

    private void goToFacebook(View view) {
    	String facebookID = theBrewery.getFacebookID();
    	if (!facebookID.isEmpty()) {
    		Intent facebook = openFacebook(facebookID);
    		startActivity(facebook);
    	}

    }

    private Intent openFacebook(String theID) {
    	try {
    		getActivity().getPackageManager().getPackageInfo("com.facebook.katana", 0);
    		return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/"
    				+ theID));
    	} catch (Exception e) {
    		return new Intent(Intent.ACTION_VIEW,
    				Uri.parse("https://www.facebook.com/" + theID));
    	}
    }

    
    
    private class BeerListItemClickListener implements
	ListView.OnItemClickListener {
@Override
public void onItemClick(AdapterView<?> parent, View view, int position,
		long id) {
	ListView theList = (ListView)parent;
	Beer selectedBeer = (Beer) theList.getItemAtPosition(position);	
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



	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.webButton:
			goToWeb(v);
			break;
		case R.id.facebookButton:
			goToFacebook(v);
			break;
		case R.id.phoneButton:
			goToPhone(v);
			break;
		case R.id.locateButton:
			goToMap(v);
			break;
		default:
			break;
				
		}
	
	}
    
   
}
