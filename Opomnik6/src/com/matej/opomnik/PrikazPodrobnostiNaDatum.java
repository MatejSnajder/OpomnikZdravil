package com.matej.opomnik;

import java.util.ArrayList;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class PrikazPodrobnostiNaDatum extends OpomnikJemanjaZdravil4Activity{
	
	private SQLiteAdapter mySQLiteAdapter;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prikazpodrobnostinadatum);
        
        String leto = getIntent().getStringExtra("leto");
        String mesec = getIntent().getStringExtra("mesec");
        String dan = getIntent().getStringExtra("dan");
        
        TextView naslov = (TextView) findViewById(R.id.textView1);
        naslov.setText("Zdravila, ki je potrebno vzeti na dan: "+dan+"."+mesec+"."+leto);
        
        mySQLiteAdapter = new SQLiteAdapter(this);
	    mySQLiteAdapter.openToRead();
	    Log.d("mes", "open");
	    Cursor cursor = mySQLiteAdapter.dogodkiNaDatum(dan, mesec, leto);
	    startManagingCursor(cursor);
	    
	    
	    ListView sezznamZdravilZaDanes = (ListView) findViewById(R.id.listView1);
	    
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
	    
	    /*sezznamZdravilZaDanes.setOnItemClickListener(
	            new OnItemClickListener()
	            {
	                @Override
	                public void onItemClick(AdapterView<?> arg0, View view,
	                        int position, long id) {

	                             //Take action here.
	                     }
	                }
	         );*/
        
    }

}
