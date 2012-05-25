package com.matej.opomnik;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

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

}
