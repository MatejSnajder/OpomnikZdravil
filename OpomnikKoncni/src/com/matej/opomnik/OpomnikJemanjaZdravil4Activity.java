package com.matej.opomnik;




import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;

import android.content.Context;
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
import android.widget.TextView;
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
	    Cursor cursor = mySQLiteAdapter.dogodkiNaDatum(String.valueOf(datumZdaj.getDate()), String.valueOf(datumZdaj.getMonth()+1), "2012");
	    
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
	    
	    

	    
	    int casZaAkcijo = -1;
	    int najblizjiCas = 0;
	    
	    Log.d("SPREDAJi", "");
	    try
        {
	    	mySQLiteAdapter = new SQLiteAdapter(this);
		    mySQLiteAdapter.openToRead();
	 	    Log.d("se ne zgodi", "");
		   Cursor cursor1 = mySQLiteAdapter.vrniCaseNaDatum(String.valueOf(datumZdaj.getDate()), String.valueOf(datumZdaj.getMonth()+1), "2012");
		   
		   cursor1.moveToFirst();
		    
		    ArrayList<String> mArrayList1 = new ArrayList<String>();
		    for(int i=0; i<cursor1.getCount();i++) {
		        // The Cursor is now set to the right positi
		    	mArrayList1.add(cursor1.getString(0));
		        cursor1.moveToNext();
		    }
		    
		    int razlika=1000;
		    
		    int cas = (datumZdaj.getHours()*60)+datumZdaj.getMinutes();
		    Log.d("cas: ", String.valueOf(cas));
		    for(int i=0; i<mArrayList1.size(); i++)
		    {
		    	String []tt = mArrayList1.get(i).split(":");
		    	int u = Integer.parseInt(tt[0]);
		    	int m = Integer.parseInt(tt[1]);
		    	int c = (u*60)+m;
		    	Log.d("casmoj", String.valueOf(c));
		    	if(c-cas<=razlika)
		    	{
		    		najblizjiCas = i;
		    		razlika = c-cas;
		    	}
		    }
		    
		    casZaAkcijo = razlika;
			
		    mySQLiteAdapter.close();
			Log.d("razlika", String.valueOf(casZaAkcijo));
        }
        catch(Exception e)
        {}
	    
	    Log.d("ZADAJ", "");
	    
	    if((casZaAkcijo!=1000)&&(casZaAkcijo>0))
	    {
	    AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		    Intent intent = new Intent(this, MyAlarmReceiver.class);
		    PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
		    Calendar time = Calendar.getInstance();
		    time.setTimeInMillis(System.currentTimeMillis());
		    time.add(Calendar.SECOND, casZaAkcijo*60);
		    alarmMgr.set(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(), pendingIntent);
	    }
	    
	    /*AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
	    Intent intent = new Intent(this, MyAlarmReceiver.class);
	    PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
	    Calendar time = Calendar.getInstance();
	    time.setTimeInMillis(System.currentTimeMillis());
	    time.add(Calendar.SECOND, 5);
	    alarmMgr.set(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(), pendingIntent);*/
	    

        
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