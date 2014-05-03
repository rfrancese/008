package it.unisa.dodgeholes;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

public class ComodoSettings extends Activity
{
	
	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	
	public void onResume()
	{
		super.onResume();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if ((keyCode == KeyEvent.KEYCODE_BACK))
		{
			//Log.d("back", "entro !");
		    this.finish(); 
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
	 public static boolean getAudio(DbLocale database)
	 {
	        SQLiteDatabase db = database.getReadableDatabase();
	        Cursor c = db.rawQuery("SELECT * FROM setting",null);
	        c.moveToFirst();
	        db.close();
	        if(c.getInt(0)==1)
	        	return true;
	        else
	        	return false;
	 }
	 
	 public static boolean getSound(DbLocale database)
	 {
		 SQLiteDatabase db = database.getReadableDatabase();
	        Cursor c = db.rawQuery("SELECT * FROM setting",null);
	        c.moveToFirst();
	        db.close();
	        if(c.getInt(1)==1)
	        	return true;
	        else
	        	return false;
	 }
	 
	 public static void setSound(int valore,DbLocale database)
	 {
		//Chiedo l'accesso al database in scrittura
		 SQLiteDatabase db = database.getWritableDatabase();
		 
		 /*
		  * Utlizziamo l'oggetto ContentValues per creare una mappa dei nostri valori
		  */
		 ContentValues valori = new ContentValues();

		 valori.put("sound",valore);  

		 /*
		  * Il metodo update restituisce il numero di righe aggiornate
		  */
		 int id = db.update("setting", valori, null,null);
	 }
	 
	 public static void setAudio(int valore,DbLocale database)
	 {
		//Chiedo l'accesso al database in scrittura
		 SQLiteDatabase db = database.getWritableDatabase();
		 
		 /*
		  * Utlizziamo l'oggetto ContentValues per creare una mappa dei nostri valori
		  */
		 ContentValues valori = new ContentValues();

		 valori.put("audio",valore);  

		 /*
		  * Il metodo update restituisce il numero di righe aggiornate
		  */
		 int id = db.update("setting", valori, null,null);
	 }
}
