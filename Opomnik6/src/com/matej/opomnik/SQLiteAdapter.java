package com.matej.opomnik;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class SQLiteAdapter 
{

	public static final String MYDATABASE_NAME = "BAZA_OPOMNIK_JEMANJA_ZDRAVIL1";
	public static final String MYDATABASE_TABLE = "OPOMNIKE";
	public static final int MYDATABASE_VERSION = 1;
	public static final String KEY_ID = "_id";
	
	public static final String TABELA_STOLPEC_NAZIV_SIFRA= "tabela_stolpec_sifra";
	public static final String TABELA_STOLPEC_KLASIFIKACIJA= "tabela_stolpec_klasifikacija";
	public static final String TABELA_STOLPEC_NAZIV_ZDRAVILA= "tabela_stolpec_naziv_zdravila";
	public static final String TABELA_STOLPEC_DATUM = "tabela_stolpec_datum";
	public static final String TABELA_STOLPEC_CAS_JEMANJA_ZDRAVILA = "tabela_stolpec_cas_jemanja_zdravila";
	public static final String TABELA_STOLPEC_KOLICINA_NA_DAN = "tabela_stolpec_kolicina_na_dan";
	public static final String TABELA_STOLPEC_OPOMIN = "tabela_stolpec_opomin";
	
	//public static final String KEY_CONTENT = "Content";

	//create table MY_DATABASE (ID integer primary key, Content text not null);
	private static final String SCRIPT_CREATE_DATABASE =
			"create table " + MYDATABASE_TABLE + " ("
					+ KEY_ID + " integer primary key autoincrement, "
					+ TABELA_STOLPEC_NAZIV_SIFRA + " text, "
					+ TABELA_STOLPEC_KLASIFIKACIJA + " text, "
					+ TABELA_STOLPEC_NAZIV_ZDRAVILA + " text, " 
					+ TABELA_STOLPEC_DATUM  + " text, "
					+ TABELA_STOLPEC_CAS_JEMANJA_ZDRAVILA    + " text, "
					+ TABELA_STOLPEC_KOLICINA_NA_DAN  + " text, "
					+ TABELA_STOLPEC_OPOMIN +" text);";

	private SQLiteHelper sqLiteHelper;
	private SQLiteDatabase sqLiteDatabase;
	
	private Context context;

	public SQLiteAdapter(Context c)
	{
		context = c;
	}

	public SQLiteAdapter openToRead() throws android.database.SQLException 
	{
		sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getReadableDatabase();
		return this;
	}

	public SQLiteAdapter openToWrite() throws android.database.SQLException 
	{
		sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getWritableDatabase();
		return this;
	}

	public void close()
	{
		sqLiteHelper.close();
	}

	public boolean dodajNoviTermin(String sifra, String klasifikacija, String naziv, String datum,
			 String ura, String kolicina, String opomba)
	{
		ContentValues contentValues = new ContentValues();
		//contentValues.put(KEY_CONTENT, content);
		contentValues.put(TABELA_STOLPEC_NAZIV_SIFRA, sifra);
		contentValues.put(TABELA_STOLPEC_KLASIFIKACIJA, klasifikacija);
		contentValues.put(TABELA_STOLPEC_NAZIV_ZDRAVILA, naziv);
		contentValues.put(TABELA_STOLPEC_DATUM, datum);
		contentValues.put(TABELA_STOLPEC_CAS_JEMANJA_ZDRAVILA, ura);
		contentValues.put(TABELA_STOLPEC_KOLICINA_NA_DAN, kolicina);
		contentValues.put(TABELA_STOLPEC_OPOMIN, opomba);
		
		return sqLiteDatabase.insert(MYDATABASE_TABLE, null, contentValues)>0;
	}

	public int deleteAll()
	{
		return sqLiteDatabase.delete(MYDATABASE_TABLE, null, null);
	}

	public Cursor queueAll()
	{
		String[] columns = new String[]{KEY_ID, TABELA_STOLPEC_NAZIV_SIFRA, TABELA_STOLPEC_KLASIFIKACIJA,
				TABELA_STOLPEC_NAZIV_ZDRAVILA, TABELA_STOLPEC_DATUM,TABELA_STOLPEC_CAS_JEMANJA_ZDRAVILA,
				TABELA_STOLPEC_KOLICINA_NA_DAN, TABELA_STOLPEC_OPOMIN};
		Cursor cursor = sqLiteDatabase.query(MYDATABASE_TABLE, columns,
				null, null, null, null, null);

		return cursor;
	}
	
public Cursor  getRow() throws SQLException{
		
		Cursor cursor=sqLiteDatabase.query(MYDATABASE_TABLE,
				new String[]{TABELA_STOLPEC_NAZIV_SIFRA, TABELA_STOLPEC_KLASIFIKACIJA,
				TABELA_STOLPEC_NAZIV_ZDRAVILA, TABELA_STOLPEC_DATUM,TABELA_STOLPEC_CAS_JEMANJA_ZDRAVILA,
				TABELA_STOLPEC_KOLICINA_NA_DAN, TABELA_STOLPEC_OPOMIN},
				KEY_ID,
				null,//selectionArgs,
				null,//groupBy,
				null,//having,
				null//orderBy
				);
		
		if (cursor != null) {
           cursor.moveToFirst();
        }
				
		return cursor;
		
	}
public Cursor dogodkiNaDatum(String dan, String mesec, String leto)
{
	Cursor c = sqLiteDatabase.query(MYDATABASE_TABLE, new String[]{TABELA_STOLPEC_NAZIV_ZDRAVILA, TABELA_STOLPEC_CAS_JEMANJA_ZDRAVILA,
			TABELA_STOLPEC_KOLICINA_NA_DAN, TABELA_STOLPEC_OPOMIN}, "tabela_stolpec_datum = ?", new String[] {dan+"."+mesec+"."+leto}, null, null, null);
	
	return c;
}
public void deleteRow(String datum, String naziv)
{
	try
	{
		sqLiteDatabase.delete(MYDATABASE_TABLE, "tabela_stolpec_datum=? AND tabela_stolpec_naziv_zdravila=?", new String[] {datum, naziv});
	}
	catch(Exception e){}
	
}
	
	public class SQLiteHelper extends SQLiteOpenHelper 
	{

		public SQLiteHelper(Context context, String name, CursorFactory factory, int version) 
		{
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) 
		{
			// TODO Auto-generated method stub
			db.execSQL(SCRIPT_CREATE_DATABASE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
		{
			// TODO Auto-generated method stub

		}

	}

}
