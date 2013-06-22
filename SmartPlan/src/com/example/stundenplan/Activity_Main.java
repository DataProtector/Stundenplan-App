package com.example.stundenplan;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/*
 * Main Activity 
 * 
 * Stellt Stundenplan dar 
 */
public class Activity_Main extends Activity {

	/*	OnCreate
	 *
	 *	Wird aufgerufen wenn Activity erzeugt wird 
	 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }//OnCreate>

    /*	onCreateOptionsMenu
	 *
	 *	Erstellt Menü 
	 */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }//onCreateOptionsMenu>
    
}
