package com.matej.opomnik;


import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class SeznamVsehVnosovTerminov extends ListActivity{
private SQLiteAdapter mySQLiteAdapter;
private int pozicija;
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
	      
	      mySQLiteAdapter = new SQLiteAdapter(this);
	      mySQLiteAdapter.openToRead();

	      Cursor cursor = mySQLiteAdapter.queueAll();
	      startManagingCursor(cursor);

	      String[] from = new String[]{SQLiteAdapter.TABELA_STOLPEC_NAZIV_ZDRAVILA, SQLiteAdapter.TABELA_STOLPEC_DATUM,
	    		  SQLiteAdapter.TABELA_STOLPEC_CAS_JEMANJA_ZDRAVILA,
	    		  SQLiteAdapter.TABELA_STOLPEC_KOLICINA_NA_DAN, SQLiteAdapter.TABELA_STOLPEC_OPOMIN};
	      
	      int[] to = new int[]{R.id.widget58, R.id.widget59, R.id.widget62, R.id.textView1, R.id.widget60};

	      SimpleCursorAdapter cursorAdapter =new SimpleCursorAdapter(this, R.layout.row, cursor, from, to);

	      setListAdapter(cursorAdapter);
	    
	      mySQLiteAdapter.close();
	      
	     

	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) 
	{
		pozicija = position;
		 AlertDialog.Builder builder = new AlertDialog.Builder(this);
		  	builder.setMessage("Izberite akcijo!")
		  	   .setCancelable(false)
		  	   .setPositiveButton("Uredi", new DialogInterface.OnClickListener() {
		  	       public void onClick(DialogInterface dialog, int id) 
		  	       {
		  	    	  urejanje();
		  	       }
		  	   })
		  	   .setNegativeButton("Briši", new DialogInterface.OnClickListener() 
		  	   {
		  	       public void onClick(DialogInterface dialog, int id) { 
		  	    	 brisi();
		  	      }
		  	   });
		  	AlertDialog alert = builder.create();
		  	
		  	alert.show();
		  	
	}
	
	public void brisi()
	{
		try
    	{
  	    	mySQLiteAdapter = new SQLiteAdapter(this);
 		    mySQLiteAdapter.openToRead();
 		    
 			Cursor c = mySQLiteAdapter.getRow();
 			c.moveToPosition(pozicija);
 			
 			String naziv = c.getString(c.getColumnIndex("tabela_stolpec_naziv_zdravila"));
 			String datum = c.getString(c.getColumnIndex("tabela_stolpec_datum"));
 			
 			 mySQLiteAdapter.deleteRow(datum, naziv);
 			 
 			mySQLiteAdapter.close();
 			
 			startActivity(getIntent()); finish();

    	 }
    	catch(Exception e)
		{
    		Context context = getApplicationContext();
			Toast.makeText(context, "PRIŠLO JE DO NAPAKE!", Toast.LENGTH_SHORT).show();
		}
	}
	public void urejanje()
	{
			mySQLiteAdapter = new SQLiteAdapter(this);
		    mySQLiteAdapter.openToRead();
		    
			Cursor c = mySQLiteAdapter.getRow();
			c.moveToPosition(pozicija);
			
			String naziv = c.getString(c.getColumnIndex("tabela_stolpec_naziv_zdravila"));
			String datum = c.getString(c.getColumnIndex("tabela_stolpec_datum"));
			String ura = c.getString(c.getColumnIndex("tabela_stolpec_cas_jemanja_zdravila"));
			String kolicina = c.getString(c.getColumnIndex("tabela_stolpec_kolicina_na_dan"));
			String opomba = c.getString(c.getColumnIndex("tabela_stolpec_opomin"));
			
			 mySQLiteAdapter.deleteRow(datum, naziv);
			 
			mySQLiteAdapter.close();
			Intent j = new Intent(SeznamVsehVnosovTerminov.this, UrejanjeTerminov.class);
			j.putExtra("naziv", naziv);
			j.putExtra("datum", datum);
			j.putExtra("ura", ura);
			j.putExtra("kolicina", kolicina);
			j.putExtra("opomba", opomba);
			startActivity(j);
	}
	
	

}
