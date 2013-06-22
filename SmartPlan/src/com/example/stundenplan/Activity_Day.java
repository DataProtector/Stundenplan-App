package com.example.stundenplan;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

/*
 * Day Activity 
 * 
 */
public class Activity_Day extends Activity {

	/*	OnCreate
	 *
	 *	Wird aufgerufen wenn Activity erzeugt wird 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_day);
	}//OnCreate>

	
	/*	onCreateOptionsMenu
	 *
	 *	Erstellt Menü 
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.day, menu);
		return true;
	}//onCreateOptionsMenu>

}