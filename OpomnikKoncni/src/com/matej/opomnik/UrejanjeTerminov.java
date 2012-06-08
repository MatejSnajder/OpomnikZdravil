package com.matej.opomnik;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class UrejanjeTerminov extends OpomnikJemanjaZdravil4Activity  {
	
	private EditText vnosnopoljeNaziv;
	private EditText datumJemnja;
	private EditText vnosnopoljeUraJemanja;
	private EditText vnosnopoljeKolicina;
	private EditText vnosnopoljeUraOpomba;
	
	private SQLiteAdapter mySQLiteAdapter;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ureditermin);
        
        String naziv = getIntent().getStringExtra("naziv");
        String datum = getIntent().getStringExtra("datum");
        String ura = getIntent().getStringExtra("ura");
        String kolicina = getIntent().getStringExtra("kolicina");
        String opomba = getIntent().getStringExtra("opomba");
        
        
        mySQLiteAdapter = new SQLiteAdapter(this);
        
        vnosnopoljeNaziv= (EditText) findViewById(R.id.editText1);
        datumJemnja= (EditText) findViewById(R.id.editText2);
        vnosnopoljeUraJemanja= (EditText) findViewById(R.id.editText3);
        vnosnopoljeKolicina= (EditText) findViewById(R.id.editText4);
        vnosnopoljeUraOpomba= (EditText) findViewById(R.id.editText5);
       
        
        vnosnopoljeKolicina.setText(kolicina);
        vnosnopoljeNaziv.setText(naziv);
        vnosnopoljeUraOpomba.setText(opomba);
        datumJemnja.setText(datum);
        vnosnopoljeUraJemanja.setText(ura);
        
        final ImageButton dodajGumb = (ImageButton) findViewById(R.id.imageButton1);
        dodajGumb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	try
            	{
	            	if(vnosnopoljeNaziv.getText().toString()!="")
	            	{
		            	mySQLiteAdapter.openToWrite();
		    			
		    			mySQLiteAdapter.dodajNoviTermin("1", "1", vnosnopoljeNaziv.getText().toString(),
		    					datumJemnja.getText().toString(), vnosnopoljeUraJemanja.getText().toString(),
		    					vnosnopoljeKolicina.getText().toString(), vnosnopoljeUraOpomba.getText().toString());
		    			
		    			 mySQLiteAdapter.close();
	            	
		    			 Context context1 = getApplicationContext();
		    			 Toast.makeText(context1, "Termin USPEŠNO vnešen", Toast.LENGTH_SHORT).show();
	             	
		    			 Intent j = new Intent(UrejanjeTerminov.this, OpomnikJemanjaZdravil4Activity.class);
		    			 startActivity(j);
	            	}
	            	else
	            	{
	            		Context context = getApplicationContext();
	                  	Toast.makeText(context, "Preverite vnosna polja!", Toast.LENGTH_SHORT).show();
	            	}
            }
         	catch(Exception e)
         	{
         		 Context context = getApplicationContext();
              	Toast.makeText(context, "Prišlo je do napake pri vpisu podatkov v bazo!", Toast.LENGTH_SHORT).show();
         	}
            	
            }
        });
        
	}
}
