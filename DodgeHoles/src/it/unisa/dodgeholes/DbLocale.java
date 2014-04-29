package it.unisa.dodgeholes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Uso questa classe per creare un database locale sul telefonino del
 * cliente. Quando l'utente si registra,il suo nickname viene scritto
 * nel database access,in modo da non permettere di effettuare 
 * più registrazioni da un singolo telefonino
 * @author Antonio De Piano & Giuseppe Sabato
 *
 */
public class DbLocale extends SQLiteOpenHelper {
	
	//Nome del database che vogliamo creare
	private static final String DB_NOME = "DODGEHOLES";

	/**
	 * Numero della versione del database.
	 * 
	 * La numerazione della vesione del database deve iniziare dal numero 1.
	 * Quando viene specificata una nuova versione android useguirà la funzione onUpgrade.
	 */
	private static final int DB_VERSIONE = 1;

	public DbLocale(Context context)
	{
		super(context, DB_NOME, null, DB_VERSIONE);
	}

	public void onCreate(SQLiteDatabase db)
	{
		/*
		 * Stringa contenente la sintassi SQL per la
		 * creazione della tabella ACCESS
		 */
		String sql = "CREATE TABLE access "; 
		sql += "(nickname VARCHAR(20) primary key)";
		db.execSQL(sql);
		
		sql=null;
		sql="CREATE TABLE punteggi ";
		sql+="(nickname VARCHAR(20),";
		sql+="punteggio int,";
		sql+="livello varchar(20),";
		sql+="foreign key(nickname) references access(nickname),";
		sql+="primary key(nickname,livello))";
		//Eseguiamo la query
		db.execSQL(sql);
		
		sql=null;
		sql="CREATE TABLE setting ";
		sql+="(audio boolean default true,";
		sql+="sound boolean default true)";
		db.execSQL(sql);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
	}

}