package com.matej.opomnik;



import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class SeznamVsehVnosovTerminov extends ListActivity{
private SQLiteAdapter mySQLiteAdapter;
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
	      
	      mySQLiteAdapter = new SQLiteAdapter(this);
	      mySQLiteAdapter.openToRead();

	      Cursor cursor = mySQLiteAdapter.queueAll();
	      startManagingCursor(cursor);

	      String[] from = new String[]{SQLiteAdapter.TABELA_STOLPEC_NAZIV_ZDRAVILA, SQLiteAdapter.TABELA_STOLPEC_ZACETEK_JEMANJA,
	    		  SQLiteAdapter.TABELA_STOLPEC_KONEC_JEMANJA, SQLiteAdapter.TABELA_STOLPEC_PERDIODA,
	    		  SQLiteAdapter.TABELA_STOLPEC_KOLICINA_NA_DAN, SQLiteAdapter.TABELA_STOLPEC_CAS_JEMANJA_ZDRAVILA};
	      
	      int[] to = new int[]{R.id.widget58, R.id.widget59, R.id.widget62, R.id.widget60, R.id.widget63, R.id.textView1};

	      SimpleCursorAdapter cursorAdapter =new SimpleCursorAdapter(this, R.layout.row, cursor, from, to);

	      setListAdapter(cursorAdapter);
	    
	      mySQLiteAdapter.close();

	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) 
	{
		try
		{
			mySQLiteAdapter = new SQLiteAdapter(this);
		    mySQLiteAdapter.openToRead();
		    
			Cursor c = mySQLiteAdapter.getRow();
			c.moveToPosition(position);
			
			String naziv = c.getString(c.getColumnIndex("tabela_stolpec_naziv_zdravila"));
			String zacetekJemanja = c.getString(c.getColumnIndex("tabela_stolpec_zacetek_jemanja"));
			String konecJemanja = c.getString(c.getColumnIndex("tabela_stolpec_konec_jemanja"));
			String uraJemanja = c.getString(c.getColumnIndex("tabela_stolpec_cas_jemanja_zdravila"));
			String perioda = c.getString(c.getColumnIndex("tabela_stolpec_perioda"));
			String kolicina = c.getString(c.getColumnIndex("tabela_stolpec_kolicina_na_dan"));
			String opomba = c.getString(c.getColumnIndex("tabela_stolpec_opomin"));
			
			c.close();
			mySQLiteAdapter.close();
			
			String podatki = naziv+","+zacetekJemanja+","+konecJemanja+","+uraJemanja+","+perioda+","+kolicina+","+opomba;
			
			Intent i = new Intent(v.getContext(), UrejanjeTerminov.class);
        	i.putExtra("podatki", podatki );
        	startActivity(i);
		}
		catch(Exception e)
		{
			Toast.makeText(this, "PRIŠLO JE DO NAPAKE!", Toast.LENGTH_SHORT).show();
		}
	}
}
