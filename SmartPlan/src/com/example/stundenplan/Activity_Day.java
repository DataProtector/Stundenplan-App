package com.example.stundenplan;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

/*
 * Day Activity 
 * 
 */
public class Activity_Day extends Activity {
	
	TextView[][] lessons = new TextView[5][12];

	/*	OnCreate
	 *
	 *	Wird aufgerufen wenn Activity erzeugt wird 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_day);
		
		initLessons();
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
	
	
	
	public void initLessons(){
		
		// textView-Array wird gefüllt
		
		lessons[0][0] = (TextView) findViewById(R.id.textView2a);
		lessons[0][1] = (TextView) findViewById(R.id.textView3a);
		lessons[0][2] = (TextView) findViewById(R.id.textView4a);
		lessons[0][3] = (TextView) findViewById(R.id.textView5a);
		lessons[0][4] = (TextView) findViewById(R.id.textView6a);
		lessons[0][5] = (TextView) findViewById(R.id.textView7a);
		lessons[0][6] = (TextView) findViewById(R.id.textView8a);
		lessons[0][7] = (TextView) findViewById(R.id.textView9a);
		lessons[0][8] = (TextView) findViewById(R.id.textView10a);
		lessons[0][9] = (TextView) findViewById(R.id.textView11a);
		lessons[0][10] = (TextView) findViewById(R.id.textView12a);
		lessons[0][11] = (TextView) findViewById(R.id.textView13a);
		
		lessons[1][0] = (TextView) findViewById(R.id.textView2b);
		lessons[1][1] = (TextView) findViewById(R.id.textView3b);
		lessons[1][2] = (TextView) findViewById(R.id.textView4b);
		lessons[1][3] = (TextView) findViewById(R.id.textView5b);
		lessons[1][4] = (TextView) findViewById(R.id.textView6b);
		lessons[1][5] = (TextView) findViewById(R.id.textView7b);
		lessons[1][6] = (TextView) findViewById(R.id.textView8b);
		lessons[1][7] = (TextView) findViewById(R.id.textView9b);
		lessons[1][8] = (TextView) findViewById(R.id.textView10b);
		lessons[1][9] = (TextView) findViewById(R.id.textView11b);
		lessons[1][10] = (TextView) findViewById(R.id.textView12b);
		lessons[1][11] = (TextView) findViewById(R.id.textView13b);
		
		lessons[2][0] = (TextView) findViewById(R.id.textView2c);
		lessons[2][1] = (TextView) findViewById(R.id.textView3c);
		lessons[2][2] = (TextView) findViewById(R.id.textView4c);
		lessons[2][3] = (TextView) findViewById(R.id.textView5c);
		lessons[2][4] = (TextView) findViewById(R.id.textView6c);
		lessons[2][5] = (TextView) findViewById(R.id.textView7c);
		lessons[2][6] = (TextView) findViewById(R.id.textView8c);
		lessons[2][7] = (TextView) findViewById(R.id.textView9c);
		lessons[2][8] = (TextView) findViewById(R.id.textView10c);
		lessons[2][9] = (TextView) findViewById(R.id.textView11c);
		lessons[2][10] = (TextView) findViewById(R.id.textView12c);
		lessons[2][11] = (TextView) findViewById(R.id.textView13c);
		
		lessons[3][0] = (TextView) findViewById(R.id.textView2d);
		lessons[3][1] = (TextView) findViewById(R.id.textView3d);
		lessons[3][2] = (TextView) findViewById(R.id.textView4d);
		lessons[3][3] = (TextView) findViewById(R.id.textView5d);
		lessons[3][4] = (TextView) findViewById(R.id.textView6d);
		lessons[3][5] = (TextView) findViewById(R.id.textView7d);
		lessons[3][6] = (TextView) findViewById(R.id.textView8d);
		lessons[3][7] = (TextView) findViewById(R.id.textView9d);
		lessons[3][8] = (TextView) findViewById(R.id.textView10d);
		lessons[3][9] = (TextView) findViewById(R.id.textView11d);
		lessons[3][10] = (TextView) findViewById(R.id.textView12d);
		lessons[3][11] = (TextView) findViewById(R.id.textView13d);
		
		lessons[4][0] = (TextView) findViewById(R.id.textView2e);
		lessons[4][1] = (TextView) findViewById(R.id.textView3e);
		lessons[4][2] = (TextView) findViewById(R.id.textView4e);
		lessons[4][3] = (TextView) findViewById(R.id.textView5e);
		lessons[4][4] = (TextView) findViewById(R.id.textView6e);
		lessons[4][5] = (TextView) findViewById(R.id.textView7e);
		lessons[4][6] = (TextView) findViewById(R.id.textView8e);
		lessons[4][7] = (TextView) findViewById(R.id.textView9e);
		lessons[4][8] = (TextView) findViewById(R.id.textView10e);
		lessons[4][9] = (TextView) findViewById(R.id.textView11e);
		lessons[4][10] = (TextView) findViewById(R.id.textView12e);
		lessons[4][11] = (TextView) findViewById(R.id.textView13e);
		
		
		
		insertSubjects();
		
	}
	
	public void insertSubjects(){
		// Fächer aus der Timetable werden eingesetzt
		
		DataHandler.FillFromDatabase(this);
		Subject[][] tmpTimetable = DataHandler.getTimetable();
		
		
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 12; j++){
				lessons[i][j].setText(tmpTimetable[i][j].getName() + "\n" + tmpTimetable[i][j].getTeacher());
			}
		}
	}
	
	

}