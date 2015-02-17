package com.beertap.ontapdatamodels;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

@ParseClassName("Breweries")
public class Brewery extends ParseObject {

	public String getShortName() {
		return this.getString("Brewery_name");
	}

	public String getFullName() {
		return getString("Brewery_full_name");
	}

	public String getAddress() {
		return getString("Brewery_address");
	}

	public String getDescription() {
		return getString("brewerDescription");
	}

	public String getID() {
		return getString("Brewery_ID");
	}

	public String getPhone() {
		return getString("Brewery_phone");
	}
	
	public String getWebAddress() {
		return getString("Brewery_url");
	}
	
	public String getFacebookID()
	{
		return getString("facebookProfile");
	}
	
	public ParseGeoPoint getLocation()
	{
		return getParseGeoPoint("breweryMapcoordinates");
	}
}
