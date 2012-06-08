package com.matej.opomnik;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Opomnik extends Activity {
	
	private SQLiteAdapter mySQLiteAdapter;
	private TextView vnosnopoljeNaziv;
	//private MediaPlayer mp_file=null;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opomnik1);
        
        //Uri pot = Uri.parse("");
        //mp_file = MediaPlayer.create(this, pot);
        //mp_file.start();
        
        //Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        //long[] pattern = { 500, 300 };  
        //v.vibrate(pattern, 5);

        
        try
        {
	        mySQLiteAdapter = new SQLiteAdapter(this);
	 	    mySQLiteAdapter.openToRead();
	 	    
	 	    
	 	   Date datumZdaj = new Date();
		   Cursor cursor = mySQLiteAdapter.vrniCaseNaDatum(String.valueOf(datumZdaj.getDate()), String.valueOf(datumZdaj.getMonth()+1), "2012");
		   
		   cursor.moveToFirst();
		    
		    ArrayList<String> mArrayList = new ArrayList<String>();
		    ArrayList<String> podatki = new ArrayList<String>();
		    for(int i=0; i<cursor.getCount();i++) {
		        // The Cursor is now set to the right positi
		    	mArrayList.add(cursor.getString(0));
		    	podatki.add(cursor.getString(3)+"\nKolièina: "+cursor.getString(1)+"\n"+cursor.getString(2));
		        cursor.moveToNext();
		    }
		    Log.d("b:", mArrayList.get(0));
		    int najblizjiCas = 0;
		    int razlika=1000;
		    
		    int cas = (datumZdaj.getHours()*60)+datumZdaj.getMinutes();
		    Log.d("cas: ", String.valueOf(cas));
		    for(int i=0; i<mArrayList.size(); i++)
		    {
		    	String []tt = mArrayList.get(i).split(":");
		    	Log.d("cas: ", String.valueOf(cas));
		    	int u = Integer.parseInt(tt[0]);
		    	Log.d("cas: ", String.valueOf(cas));
		    	int m = Integer.parseInt(tt[1]);
		    	int c = (u*60)+m;
		    	Log.d("moj cas:",  String.valueOf(c));
		    	if(cas-c<=razlika)
		    	{
		    		najblizjiCas = i;
		    		razlika = cas-c;
		    	}
		    }
		    vnosnopoljeNaziv= (TextView) findViewById(R.id.textView2);
		    vnosnopoljeNaziv.setText(podatki.get(najblizjiCas).toString());
		    
	
			    
			cursor.moveToPosition(najblizjiCas);
			String naziv = cursor.getString(cursor.getColumnIndex("tabela_stolpec_naziv_zdravila"));
			String datum = cursor.getString(cursor.getColumnIndex("tabela_stolpec_datum"));
				
			mySQLiteAdapter.deleteRow(datum, naziv);
				 
			mySQLiteAdapter.close();
        }
        catch(Exception e)
        {}
			
	    
        final ImageButton dodajGumb = (ImageButton) findViewById(R.id.imageButton1);
        dodajGumb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	//mp_file.pause();

            	Intent s = new Intent(Opomnik.this, OpomnikJemanjaZdravil4Activity.class);
    			startActivity(s);
            }
            });
	}

}
