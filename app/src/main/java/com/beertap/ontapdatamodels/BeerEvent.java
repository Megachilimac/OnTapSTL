package com.beertap.ontapdatamodels;

import java.util.Date;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Events")
public class BeerEvent extends ParseObject{

	public String getName() {
		return getString("eventName");
	}
	
	public String getDescription() {
		return getString("eventDescription");
	}
	
	public Date getStartDate(){
		return getDate("eventStart");
	}
	
	public Date getEndDate(){
		return getDate("eventStop");
	}
	
	public String getAddress(){
		return getString("eventAddress");		
	}
	
	public String getURL(){
		return getString("eventUrl");
	}
	
	public String getBreweryName(){
		return getString("breweryName");
	}
	
	public Date getCreatedDate(){
		return getDate("createdAt");
	}
	
	public Date getUpdatedDate(){
		return getDate("updatedAt");
	}
}
