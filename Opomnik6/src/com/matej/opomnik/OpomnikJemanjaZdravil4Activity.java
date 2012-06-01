package com.matej.opomnik;




import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class OpomnikJemanjaZdravil4Activity extends Activity {
    /** Called when the activity is first created. */
	private SQLiteAdapter mySQLiteAdapter;
	
	private CalendarView calendar;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        calendar = (CalendarView) findViewById(R.id.calendarView1);
        calendar.setOnDateChangeListener(_DateSetListener);
        
        //calendar.setOnClickListener(_OnClickListener);
        //setContentView(calendar);
        
        //LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 200);
        //calendar.setLayoutParams(lp);
        //Date d = new Date(2012, 31, 5);
        /*calendar.setDate(d.getTime());*/
        //Log.d("current time", String.valueOf(System.currentTimeMillis()));
        //Log.d("moj time", String.valueOf(d.getTime()));
        //calendar.setDate(System.currentTimeMillis(), true, true);
        //calendar.setActivated(true);
        //setContentView(calendar);
        
        ListView sezznamZdravilZaDanes = (ListView) findViewById(R.id.listView1);
        
        mySQLiteAdapter = new SQLiteAdapter(this);
	    mySQLiteAdapter.openToRead();
	    
	    Date datumZdaj = new Date();
	    Log.d("dan", String.valueOf(datumZdaj.getDate()));
	    Log.d("mesec", String.valueOf(String.valueOf(datumZdaj.getMonth()+1)));
	    Cursor cursor = mySQLiteAdapter.dogodkiNaDatum(String.valueOf(datumZdaj.getDate()), String.valueOf(datumZdaj.getMonth()+1), "2012");
	    //Cursor cursor = mySQLiteAdapter.dogodkiNaDatum("31","5", "2012");
	    
	    cursor.moveToFirst();
	    
	    ArrayList<String> mArrayList = new ArrayList<String>();
	    for(int i=0; i<cursor.getCount();i++) {
	        // The Cursor is now set to the right positi
	    	mArrayList.add(cursor.getString(0)+"             Ura: "+cursor.getString(1)+"\nKolièina: "+cursor.getString(2)+ "\n"+cursor.getString(3));
	        cursor.moveToNext();
	    }
	    
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mArrayList);
	    sezznamZdravilZaDanes.setAdapter(adapter);
	    
	    mySQLiteAdapter.close();


        
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