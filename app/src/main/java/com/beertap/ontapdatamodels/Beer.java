package com.beertap.ontapdatamodels;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Beers")
public class Beer extends ParseObject {

	public String getName() {
		return getString("beerName");
	}

	public String getBreweryName() {
		return getString("breweryName");
	}

	public String getSRM() {
		return getString("Beer_srm");
	}

	public String getABV() {
		return getString("Beer_abv");
	}

	public String getIBU() {
		return getString("Beer_ibu");
	}

	public String getDescription() {
		return getString("Beer_description");
	}

	public String getStyle() {
		return getString("Beer_style");
	}

	public Boolean getOnTapNow() {
		return getBoolean("ontap_now");
	}
}
