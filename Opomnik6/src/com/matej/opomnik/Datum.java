package com.matej.opomnik;

import android.util.Log;

public class Datum {
	
	private String datumVMojemZapisu;
	private int dan;
	private int mesec;
	private int leto;
	
	public Datum(String datum)
	{
		datumVMojemZapisu = datum;
		leto=mesec=dan=0;
	}
	public boolean preveriZapisDatuma()
	{
		try
		{
			String[] danMesecleto = datumVMojemZapisu.split("\\.");
			dan = Integer.valueOf(danMesecleto[0]);
			Log.d("DAN", danMesecleto[0]);
			mesec = Integer.valueOf(danMesecleto[1]);
			Log.d("MESEC", danMesecleto[1]);
			leto = Integer.valueOf(danMesecleto[2]);
			Log.d("LETO", danMesecleto[2]);
			boolean aliPrav = true;
			
			if((dan<=0)||(dan>31))
				aliPrav=false;
			if((mesec<=0)||(mesec>12))
				aliPrav=false;
			if((leto<=2011)||(leto>3000))
				aliPrav=false;
			
			return aliPrav;
		}
		catch(Exception e)
		{
			return false;
		}
		
	}
	public boolean AliDatumVecji(Datum vecjiDatum)
	{
		//prvo preverim, èe sem v istem letu
		if(this.leto==vecjiDatum.leto)
		{
			if(this.mesec==vecjiDatum.mesec)
			{
				if(this.dan<=vecjiDatum.dan)
					return true;
				else
					return false;
			}
			else if(this.mesec<vecjiDatum.mesec)
				return true;
			else
				return false;
		}
		else if(this.leto<=vecjiDatum.leto)
			return true;
		else
			return false;
	}

}
