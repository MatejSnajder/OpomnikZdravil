package com.matej.opomnik;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;

public class OpomnikJemanjaZdravil4Activity extends Activity {
    /** Called when the activity is first created. */
	
	private CalendarView calendar;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        
        calendar = new CalendarView(this);
        calendar.setOnDateChangeListener(_DateSetListener);
        //calendar.setOnClickListener(_OnClickListener);
        setContentView(calendar);
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
    /*private OnClickListener  _OnClickListener = new OnClickListener() {

        public void onClick(View v) {
            Date date = new Date(calendar.getDate());
            Toast.makeText(OpomnikJemanjaZdravil4Activity.this, "Prosim napišite besedilo!", Toast.LENGTH_SHORT).show();
        }
    };*/
    private OnDateChangeListener _DateSetListener = new OnDateChangeListener() 
    {
        public void onSelectedDayChange(CalendarView view, int year, int month,
                int dayOfMonth) 
        {
        	String datum = Integer.toString(dayOfMonth)+"."+Integer.toString(month+1)+"."+Integer.toString(year);
        	Toast.makeText(OpomnikJemanjaZdravil4Activity.this, datum, Toast.LENGTH_SHORT).show();
        	
        	Intent s = new Intent(OpomnikJemanjaZdravil4Activity.this, PrikazPodrobnostiNaDatum.class);
        	s.putExtra("leto", Integer.toString(year));
        	s.putExtra("mesec", Integer.toString(month+1));
        	s.putExtra("dan", Integer.toString(dayOfMonth));
			startActivity(s);
        }
    }; 
}