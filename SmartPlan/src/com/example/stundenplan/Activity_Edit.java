package com.example.stundenplan;

import java.util.LinkedList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Activity_Edit extends Activity {
	
	
	public Spinner sp_day, sp_lesson; 
	public Button bt_submit; 
	public EditText et_teacher, et_subject;
	
	
	//On Create
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		// Show the Up button in the action bar.

		inits(); 	//initialisiert die variablen
		
		
		Timetable.DebugTest(this);
		
		
	}//OnCreate>

	//Variablen initialisieren
	private void inits() {
		sp_day = (Spinner) findViewById(R.id.Tagwahl);
		sp_lesson = (Spinner) findViewById(R.id.Stundenwahl);
		bt_submit = (Button) findViewById(R.id.button_Edit);
		bt_submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Toast.makeText(Activity_Edit.this,"Die eingegebenen Daten wurden in den Stundenplan eingetragen!" ,Toast.LENGTH_SHORT).show();
				
				
			}
		});
		
		setupActionBar();
		addItemsOnSpinnerDay();  
		addItemsOnSpinnerLesson();
	}//inits>

	//Actionbar Anpassen
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
		
		return true;
	}

	//?
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	//Implementierung der Methoden der Spinner
	public void addItemsOnSpinnerDay(){
		LinkedList<String> LL_ = new LinkedList<String>();
		LL_.add("Montag");
		LL_.add("Dienstag");
		LL_.add("Mittwoch");
		LL_.add("Donnerstag");
		LL_.add("Freitag");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, LL_);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_day.setAdapter(dataAdapter);
	}
	
	public void addItemsOnSpinnerLesson(){
		LinkedList<String> list = new LinkedList<String>();
		for(int i =1;i<13;i++){
			list.add(i+". Stunde");
		}
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_lesson.setAdapter(dataAdapter);
	}
	
	
	

}
