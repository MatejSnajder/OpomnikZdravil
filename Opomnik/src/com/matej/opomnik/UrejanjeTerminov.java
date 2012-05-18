package com.matej.opomnik;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class UrejanjeTerminov extends OpomnikJemanjaZdravil4Activity{
	
	private EditText vnosnopoljeNaziv;
	private EditText vnosnopoljeZacetekJemnja;
	private EditText vnosnopoljeKonecJemnja;
	private EditText vnosnopoljeUraJemanja;
	private EditText vnosnopoljePerioda;
	private EditText vnosnopoljeKolicina;
	private EditText vnosnopoljeUraOpomba;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dodajtermin);
        
        String podatek = getIntent().getStringExtra("podatki");
        String [] podatki = podatek.split(",");
        
        vnosnopoljeNaziv= (EditText) findViewById(R.id.editText1);
        vnosnopoljeZacetekJemnja= (EditText) findViewById(R.id.editText2);
        vnosnopoljeKonecJemnja= (EditText) findViewById(R.id.editText3);
        vnosnopoljeUraJemanja= (EditText) findViewById(R.id.editText4);
        vnosnopoljePerioda= (EditText) findViewById(R.id.editText5);
        vnosnopoljeKolicina= (EditText) findViewById(R.id.editText6);
        vnosnopoljeUraOpomba= (EditText) findViewById(R.id.editText7);
        
        vnosnopoljeNaziv.setText(podatki[0]);
        vnosnopoljeZacetekJemnja.setText(podatki[1]);
        vnosnopoljeKonecJemnja.setText(podatki[2]);
        vnosnopoljeUraJemanja.setText(podatki[3]);
        vnosnopoljePerioda.setText(podatki[4]);
        vnosnopoljeKolicina.setText(podatki[5]);
        try
        {
        	vnosnopoljeUraOpomba.setText(podatki[6]);
        }
        catch(Exception e)
        {}
        
        final ImageButton dodajGumb = (ImageButton) findViewById(R.id.imageButton1);
        dodajGumb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	Context context = getApplicationContext();
            	Toast.makeText(context, "Termin USPEŠNO Urejen", Toast.LENGTH_SHORT).show();
            	
            	Intent j = new Intent(UrejanjeTerminov.this, OpomnikJemanjaZdravil4Activity.class);
				startActivity(j);
            
            }
        });
	}

}
