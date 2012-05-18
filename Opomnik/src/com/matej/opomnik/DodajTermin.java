package com.matej.opomnik;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

public class DodajTermin extends OpomnikJemanjaZdravil4Activity {
	
	//private EditText mDateDisplay;
	private EditText vnosnopoljeNaziv;
	private EditText vnosnopoljeZacetekJemnja;
	private EditText vnosnopoljeKonecJemnja;
	private EditText vnosnopoljeUraJemanja;
	private EditText vnosnopoljePerioda;
	private EditText vnosnopoljeKolicina;
	private EditText vnosnopoljeUraOpomba;
	
	private String vnosnopoljeNazivString = null;
	private String vnosnopoljeZacetekJemnjaString= null;
	private String vnosnopoljeKonecJemnjaString= null;
	private String vnosnopoljeUraJemanjaString= null;
	private String vnosnopoljePeriodaString= null;
	private String vnosnopoljeKolicinaString= null;
	private String vnosnopoljeUraOpombaString= null ;
	
	private int kateriDatumVnasam = 3;
    private int mYear;  
    private int mMonth;  
    private int mDay; 
    private int mUhr;
    private int mMin;
	
	static final int DATE_DIALOG_ID = 0;  
	static final int TIME_DIALOG_ID = 1;  
	
	private SQLiteAdapter mySQLiteAdapter;
	
	private Datum zacetekJemanja;
	private Datum konecJemanja;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dodajtermin);
        
        mySQLiteAdapter = new SQLiteAdapter(this);
        
        vnosnopoljeNaziv= (EditText) findViewById(R.id.editText1);
        vnosnopoljePerioda= (EditText) findViewById(R.id.editText5);
        vnosnopoljeKolicina= (EditText) findViewById(R.id.editText6);
        vnosnopoljeUraOpomba= (EditText) findViewById(R.id.editText7);
        
        
        vnosnopoljePerioda.setText("1");
        vnosnopoljeKolicina.setText("1");
        
        vnosnopoljeZacetekJemnja = (EditText) findViewById(R.id.editText2);
        vnosnopoljeZacetekJemnja.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				kateriDatumVnasam = 1;
				showDialog(DATE_DIALOG_ID);
            	//Context context = getApplicationContext();
            	//Toast.makeText(context, "Prosim napišite besedilo!", Toast.LENGTH_SHORT).show();
				
			}
		}); 
        vnosnopoljeKonecJemnja = (EditText) findViewById(R.id.editText3);
        vnosnopoljeKonecJemnja.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				kateriDatumVnasam = 2;
				showDialog(DATE_DIALOG_ID);
				
			}
		}); 
        
        vnosnopoljeUraJemanja = (EditText) findViewById(R.id.editText4);
        vnosnopoljeUraJemanja.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				showDialog(TIME_DIALOG_ID);
				
			}
		});
        final ImageButton dodajGumb = (ImageButton) findViewById(R.id.imageButton1);
        dodajGumb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	//polje 1 -naziv
            	//polje 2 -datum prièetka
            	//polje 3 -datum konca
            	//polje 4 -ura
            	//polje 5 -perioda
            	//polje 6 -kolicina
            	//polje 7 -opomba
            	
            	int aliVsePravilno=0;
            	for(int i=1; i<=7; i++)
            	{
            		aliVsePravilno = preveriPodatkeVnosnihPolj(i);
            		if(aliVsePravilno!=0)
            			break;
            	}
                if(aliVsePravilno == 0)
                {
                	vnosnopoljeUraOpombaString = vnosnopoljeUraOpomba.getText().toString();
                	
                	//preden dodam moream še preveriti, ali je zaèetek jemnaj zdarvila manjši ali enak koncu jemnaj zdarvil
                	boolean alipravilno = zacetekJemanja.AliDatumVecji(konecJemanja);
                	if(alipravilno)
                	{
	                	try
	                	{
		                	mySQLiteAdapter.openToWrite();
		             	    
		             	    
		             	    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		                    Date date = new Date();
		             	    String datumCas = dateFormat.format(date);
		             	    
		             	    mySQLiteAdapter.dodajNoviTermin(datumCas, vnosnopoljeNazivString, vnosnopoljeZacetekJemnjaString,
		             	    		vnosnopoljeKonecJemnjaString, vnosnopoljeKolicinaString, vnosnopoljePeriodaString,
		             	    		vnosnopoljeUraJemanjaString, vnosnopoljeUraOpombaString);
		             	    
		             	    mySQLiteAdapter.close();
		             	    
		             	    Context context = getApplicationContext();
		                	Toast.makeText(context, "Termin USPEŠNO vnešen", Toast.LENGTH_SHORT).show();
		                	
		                	Intent j = new Intent(DodajTermin.this, OpomnikJemanjaZdravil4Activity.class);
							startActivity(j);
	                	}
	                	catch(Exception e)
	                	{
	                		 Context context = getApplicationContext();
	 	                	Toast.makeText(context, "Prišlo je do napake pri vpisu podatkov v bazo!", Toast.LENGTH_SHORT).show();
	                	}
                	}
                	else
                	{
                		Context context = getApplicationContext();
 	                	Toast.makeText(context, "Datum zaèetka jemnaj zdravila je veèji od datuma konca jemanja zdravila!", Toast.LENGTH_SHORT).show();
                	}
                	
                }
                else
                {
                	Context context = getApplicationContext();
                	switch(aliVsePravilno)
                	{
                		case 1: Toast.makeText(context, "Napaka pri vnosnem polju NAZIV ZDRAVILA!", Toast.LENGTH_SHORT).show();
                				break;
                		case 2: Toast.makeText(context, "Napaka pri vnosnem polju DATUM PRIÈETKA JEMANJA ZDRAVILA!", Toast.LENGTH_SHORT).show();
                				break;
                		case 3: Toast.makeText(context, "Napaka pri vnosnem polju DATUM KONCA JEMANJA ZDRAVILA!", Toast.LENGTH_SHORT).show();
                				break;
                		case 4: Toast.makeText(context, "Napaka pri vnosnem polju URA JEMANJA ZDRAVILA!", Toast.LENGTH_SHORT).show();
        						break;
                		case 5: Toast.makeText(context, "Napaka pri vnosnem polju NA KOLIKO DNI ŽELITE JEMATI ZDRAVILO!", Toast.LENGTH_SHORT).show();
        					break;
                		case 6: Toast.makeText(context, "Napaka pri vnosnem polju KOLIÈINA ZDRAVILA!", Toast.LENGTH_SHORT).show();
    						break;
                	}
                }
                	
            }
        });
        
        // get the current date  
        final Calendar c = Calendar.getInstance();  
        mYear = c.get(Calendar.YEAR);  
        mMonth = c.get(Calendar.MONTH);  
        mDay = c.get(Calendar.DAY_OF_MONTH); 
        mUhr = c.get(Calendar.HOUR_OF_DAY);
        mMin = c.get(Calendar.MINUTE); 
  
        // display the current date (this method is below)  
        updateDisplay(kateriDatumVnasam);
        updateDisplayCas();
    }  
  
    // updates the date in the TextView
	//ce je status 1, potem morem datum izpisati v vnosno polje zaèetek jemnaj zdravila,
	//ce je 2 v konec jemnaja zdravila, ce pa 3 pa v obe polji
    private void updateDisplay(int status) {  
    	if(status==1)
    		vnosnopoljeZacetekJemnja.setText(Integer.toString(mDay)+"."+Integer.toString(mMonth + 1)+"."+Integer.toString(mYear));
    	else if(status ==2)
    		vnosnopoljeKonecJemnja.setText(Integer.toString(mDay)+"."+Integer.toString(mMonth + 1)+"."+Integer.toString(mYear));
    	else
    	{
    		vnosnopoljeZacetekJemnja.setText(Integer.toString(mDay)+"."+Integer.toString(mMonth + 1)+"."+Integer.toString(mYear));
    		vnosnopoljeKonecJemnja.setText(Integer.toString(mDay)+"."+Integer.toString(mMonth + 1)+"."+Integer.toString(mYear));
    	}
    }
    private void updateDisplayCas()
    {
    	if(mMin<10)
    	 vnosnopoljeUraJemanja.setText(Integer.toString(mUhr)+":0"+Integer.toString(mMin));
    	else
    		vnosnopoljeUraJemanja.setText(Integer.toString(mUhr)+":"+Integer.toString(mMin));
    }
    
    
  
    // the callback received when the user "sets" the date in the dialog  
    private DatePickerDialog.OnDateSetListener mDateSetListener =  new DatePickerDialog.OnDateSetListener() {  
  
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {  
                    mYear = year;  
                    mMonth = monthOfYear;  
                    mDay = dayOfMonth; 
                    
                    updateDisplay(kateriDatumVnasam);  
                }  
            }; 
            
    private TimePickerDialog.OnTimeSetListener mTimeSetListner = new TimePickerDialog.OnTimeSetListener(){
    	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {  
    				mUhr = hourOfDay;  
    				mMin = minute;   

    				updateDisplayCas();  
    	}  
    };
  
    @Override  
    protected Dialog onCreateDialog(int id) {  
        switch (id) {  
        case DATE_DIALOG_ID: 
        	Log.d("DATUM", "DATUM");
            return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay); 
        case TIME_DIALOG_ID:
        	Log.d("CAS", "CAS");
        	 return new TimePickerDialog(null, id, mTimeSetListner, mUhr, mMin, true);
        }  
        return null;  
    } 
    
    private int preveriPodatkeVnosnihPolj(int polje)
    {
    	//vrenem 0, èe vse pravilno, sicer stevilko polja,kjer je napaka
    	int aliVsePravilno = 0;
    	
    	switch(polje)
    	{
    	case 1:	vnosnopoljeNazivString = vnosnopoljeNaziv.getText().toString();
    			if(vnosnopoljeNazivString.length()==0)
    				aliVsePravilno=1;
    			break;
    		
    	case 2: vnosnopoljeZacetekJemnjaString =  vnosnopoljeZacetekJemnja.getText().toString();
    			zacetekJemanja = new Datum(vnosnopoljeZacetekJemnjaString);
    			boolean aliPravilno = zacetekJemanja.preveriZapisDatuma();
    			if(!aliPravilno)
    				aliVsePravilno=2;
    		break;
    			
    	case 3:  vnosnopoljeKonecJemnjaString =  vnosnopoljeKonecJemnja.getText().toString();
				konecJemanja = new Datum(vnosnopoljeKonecJemnjaString);
				boolean aliPravilno1 = konecJemanja.preveriZapisDatuma();
				if(!aliPravilno1)
					aliVsePravilno=3;
				break;
				
    	case 4: vnosnopoljeUraJemanjaString = vnosnopoljeUraJemanja.getText().toString();
    			try
    			{
    				String[] uraMinute = vnosnopoljeUraJemanjaString.split(":");
    				int ura = Integer.valueOf(uraMinute[0]);
    				int minute = Integer.valueOf(uraMinute[1]);
    				if((ura<0)&&(ura>23))
    					aliVsePravilno = 4;
    				else if((minute<0)&&(minute>59))
    					aliVsePravilno = 4;
    			}
    			catch(Exception e)
    			{
    				aliVsePravilno = 4;
    			}
    			break;
    	case 5: vnosnopoljePeriodaString = vnosnopoljePerioda.getText().toString();
    			if(vnosnopoljePeriodaString.length()==0)
    				aliVsePravilno=5;
    			break;
    			
    	case 6: vnosnopoljeKolicinaString = vnosnopoljeKolicina.getText().toString();
				if(vnosnopoljeKolicinaString.length()==0)
					aliVsePravilno=6;
				break;
				
    	case 7: break;
    	}
    	
    	
    	return aliVsePravilno ;
    }

}
