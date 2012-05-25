package com.matej.opomnik;

import android.os.Bundle;
import android.widget.TextView;

public class PrikazPodrobnostiNaDatum extends OpomnikJemanjaZdravil4Activity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prikazpodrobnostinadatum);
        
        String leto = getIntent().getStringExtra("leto");
        String mesec = getIntent().getStringExtra("mesec");
        String dan = getIntent().getStringExtra("dan");
        
        TextView naslov = (TextView) findViewById(R.id.textView1);
        naslov.setText("Zdravila, ki je potrebno vzeti na dan: "+dan+"."+mesec+"."+leto);
        
        
        
    }

}
