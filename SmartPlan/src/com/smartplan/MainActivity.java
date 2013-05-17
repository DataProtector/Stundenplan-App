package com.smartplan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.smartplan.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    
    // F�llen des Men�s mit den in /res/menu/main angegebenen Men�punkten
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    // �berpr�fung, welches MenuItem gedr�ckt wurde & Reaktion darauf
    @Override
    public boolean onOptionsItemSelected (MenuItem menuitem){
    	
    	if (menuitem.toString().equals("Bearbeiten"))
    	{
    		Intent intent = new Intent(this, EditActivity.class);
    		startActivity(intent); 
    		
    	}
    	return true;
    }
}
