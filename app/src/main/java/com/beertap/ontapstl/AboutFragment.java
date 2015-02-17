package com.beertap.ontapstl;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class AboutFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_about, container,
				false);

		TextView textView = (TextView) rootView.findViewById(R.id.textAbout);
		Typeface chalk = Typeface.createFromAsset(getActivity().getAssets(), "font/chalkdust.ttf");
		textView.setTypeface(chalk);
		
		ImageButton button5 = (ImageButton)rootView.findViewById(R.id.buttonPayPal5);		
		button5.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v)
			{
				Uri uriUrl = Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=MSVNNYNKQS6NW");
				Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
				startActivity(launchBrowser);
			}
		});
		
		TextView text5 = (TextView) rootView.findViewById(R.id.textBuyBeer);
		text5.setTypeface(chalk);
				
		ImageButton button25 = (ImageButton)rootView.findViewById(R.id.buttonPayPal25);		
		button25.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v)
			{
				Uri uriUrl = Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=B8MRFU89JTQJW");
				Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
				startActivity(launchBrowser);
			}
		});
		
		TextView text25 = (TextView) rootView.findViewById(R.id.textBuyCase);
		text25.setTypeface(chalk);
		
		ImageButton buttonAny = (ImageButton)rootView.findViewById(R.id.buttonPayPalAny);		
		buttonAny.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v)
			{
				Uri uriUrl = Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=D29T8ZSK7P3SL");
				Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
				startActivity(launchBrowser);
			}
		});
		
		TextView textAny= (TextView) rootView.findViewById(R.id.textBuyWhatever);
		textAny.setTypeface(chalk);
		
		TextView textContact = (TextView)rootView.findViewById(R.id.textViewContact);
		textContact.setTypeface(chalk);
		
		ImageButton contactWeb = (ImageButton)rootView.findViewById(R.id.onTapWebButton);
		contactWeb.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v)
			{
				Uri uriUrl = Uri.parse("http://ontapstl.com");
				Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
				startActivity(launchBrowser);	
			}
		});
		
		ImageButton contactTwitter = (ImageButton)rootView.findViewById(R.id.onTapTwitterButton);
		contactTwitter.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v)
			{
				Uri uriUrl = Uri.parse("https://twitter.com/ontapstl");
				Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
				startActivity(launchBrowser);	
			}
		});
		
		ImageButton contactFacebook = (ImageButton)rootView.findViewById(R.id.onTapFacebookButton);
		contactFacebook.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v)
			{
				Uri uriUrl = Uri.parse("https://www.facebook.com/188054504688300");
				Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
				startActivity(launchBrowser);	
			}
		});
		
		ImageButton contactEmail = (ImageButton)rootView.findViewById(R.id.onTapEmailButton);
		contactEmail.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v)
			{
				Intent emailIntent = new Intent(Intent.ACTION_SEND);
			    emailIntent.setData(Uri.parse("mailto:"));
			    emailIntent.setType("text/plain");
			    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@ontapstl.com"});			      
			    startActivity(emailIntent);  
			}
		});
		

		TextView textDonate = (TextView)rootView.findViewById(R.id.textViewDonate);
		textDonate.setTypeface(chalk);

		return rootView;
	}
	

	
	
}
