package com.beertap.ontapstl;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beertap.ontapdatamodels.BeerEvent;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@SuppressLint("SimpleDateFormat")
public class EventDetailActivity extends Activity {

	private BeerEvent theEvent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.event_item_detail);

		Bundle extras = getIntent().getExtras();
		String eventID = extras.getString("selected_event");

		Parse.initialize(this, "1LHQvaXkHfxOYx0snQeOTiZ5TP7kp9rmIAePjfDi",
				"2HyiX7Cq7QTTBTsYSywTdV0dtgl1yxWFTCoLazOw");
		ParseObject.registerSubclass(BeerEvent.class);

		ParseQuery<BeerEvent> queryEvent = ParseQuery.getQuery(BeerEvent.class);

		queryEvent.getInBackground(eventID, new GetCallback<BeerEvent>() {
			public void done(BeerEvent eventObject, ParseException e) {
				if (e == null) {
					updateDetails(eventObject);

				} else {
					Log.d("events", "Error: " + e.getMessage());
				}
			}

			private void updateDetails(BeerEvent eventObject) {

				theEvent = eventObject;
				getActionBar().setTitle("");

				Typeface chalky = Typeface.createFromAsset(getAssets(),
						"font/chalkdust.ttf");
				SimpleDateFormat monthDay = new SimpleDateFormat("MMM d");
				SimpleDateFormat theTime = new SimpleDateFormat("h:mm a");
				theTime.setTimeZone(TimeZone.getTimeZone("UTC"));

				StringBuilder startDateStringBuilder = new StringBuilder(
						monthDay.format(eventObject.getStartDate()));
				StringBuilder startTimeStringBuilder = new StringBuilder(
						theTime.format(eventObject.getStartDate()));

				StringBuilder endDateStringBuilder = new StringBuilder(monthDay
						.format(eventObject.getEndDate()));
				StringBuilder endTimeStringBuilder = new StringBuilder(theTime
						.format(eventObject.getEndDate()));

				TextView labelStart = (TextView) findViewById(R.id.labelStart);
				labelStart.setTypeface(chalky);
				TextView labelEnd = (TextView) findViewById(R.id.labelEnd);
				labelEnd.setTypeface(chalky);

				TextView eventTitle = (TextView) findViewById(R.id.eventTitle);
				eventTitle.setText(eventObject.getName());
				eventTitle.setTypeface(chalky);
				eventTitle.setTextColor(Color.WHITE);

				TextView eventBrewer = (TextView) findViewById(R.id.eventBrewery);
				eventBrewer.setText(eventObject.getBreweryName());
				eventBrewer.setTypeface(chalky);

				TextView eventStart = (TextView) findViewById(R.id.eventStart);
				eventStart.setText(startDateStringBuilder.toString());
				eventStart.setTypeface(chalky);
				eventStart.setTextColor(Color.WHITE);

				TextView eventStartTime = (TextView) findViewById(R.id.eventStartTime);
				eventStartTime.setText(startTimeStringBuilder.toString());
				eventStartTime.setTypeface(chalky);
				eventStartTime.setTextColor(Color.WHITE);

				TextView eventStop = (TextView) findViewById(R.id.eventEnd);
				eventStop.setText(endDateStringBuilder.toString());
				eventStop.setTypeface(chalky);
				eventStop.setTextColor(Color.WHITE);

				TextView eventStopTime = (TextView) findViewById(R.id.eventEndTime);
				eventStopTime.setText(endTimeStringBuilder.toString());
				eventStopTime.setTypeface(chalky);
				eventStopTime.setTextColor(Color.WHITE);

				TextView eventDescription = (TextView) findViewById(R.id.eventDescription);
				eventDescription.setText(eventObject.getDescription());
				eventDescription.setTypeface(chalky);
				eventDescription.setTextColor(Color.WHITE);

				ParseFile file = (ParseFile) eventObject.get("eventImage");
				file.getDataInBackground(new GetDataCallback() {

					public void done(byte[] data, ParseException e) {
						if (e == null) {

							Bitmap bitmap = BitmapFactory.decodeByteArray(data,
									0, data.length);
							ImageView theLogo = (ImageView) findViewById(R.id.eventLogo);

							theLogo.setImageBitmap(bitmap);

						} else {
							// something went wrong
						}
					}
				});

			}

		});

	}

	public void goToWeb(View view) {
		try {
			Uri uriUrl = Uri.parse(theEvent.getURL());
			Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
			startActivity(launchBrowser);
		} catch (NullPointerException nullpointerex) {
			nullpointerex.printStackTrace();
			displayError();

		} catch (ActivityNotFoundException notfoundex) {
			notfoundex.printStackTrace();
			displayError();
		}
	}

	public void goToMap(View view) {
		String address = theEvent.getAddress();
		Intent searchAddress = new Intent(Intent.ACTION_VIEW,
				Uri.parse("http://maps.google.com/maps?q=" + address));
		startActivity(searchAddress);

	}
	
	public void addToCalendar(View view){
		  Intent intent = new Intent(Intent.ACTION_EDIT);
	        intent.setType("vnd.android.cursor.item/event");
	        intent.putExtra("dtstart", theEvent.getStartDate().getTime());	        	       
	        intent.putExtra("dtend", theEvent.getEndDate().getTime());
	        intent.putExtra("title", theEvent.getName());	        
	        startActivity(intent);
	}

	private void displayError() {
		AlertDialog.Builder alertUser = new AlertDialog.Builder(this)
				.setTitle("Browser Error")
				.setMessage(
						"Sorry. Could not launch the browser for this link.")
				.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// continue with delete
							}
						});
		alertUser.show();
	}

}
