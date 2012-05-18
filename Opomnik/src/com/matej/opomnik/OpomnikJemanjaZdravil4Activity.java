package com.matej.opomnik;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

public class OpomnikJemanjaZdravil4Activity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        //startActivity(new Intent(Intent.ACTION_VIEW).setDataAndType(null, CalendarActivity.MIME_TYPE));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dodaj: 	Intent j = new Intent(OpomnikJemanjaZdravil4Activity.this, DodajTermin.class);
								startActivity(j);
                            	break;
                            	
            case R.id.seznam:	Intent s = new Intent(OpomnikJemanjaZdravil4Activity.this, SeznamVsehVnosovTerminov.class);
								startActivity(s);
								break;
                        
            /*case R.id.brisi:    Intent j = new Intent(LuckaClientActivity.this, Statistika.class);
								startActivity(j);
                                break;
            case R.id.uredi: 	Toast.makeText(this, "POMOÈ", Toast.LENGTH_LONG).show();
                                break;*/
        }
        return true;
        
    }
    
    
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK) {
            int year = data.getIntExtra("year", 0);   // get number of year
            int month = data.getIntExtra("month", 0); // get number of month 0..11
            int day = data.getIntExtra("day", 0);     // get number of day 0..31

            // format date and display on screen
            final Calendar dat = Calendar.getInstance();
            dat.set(Calendar.YEAR, year);
            dat.set(Calendar.MONTH, month);
            dat.set(Calendar.DAY_OF_MONTH, day);
            
            SimpleDateFormat format = new SimpleDateFormat("yyyy MMM dd");
            Toast.makeText(OpomnikJemanjaZdravil4Activity.this, format.format(dat.getTime()), Toast.LENGTH_LONG).show();
                    
        }
    }
}