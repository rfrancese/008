package it.unisa.dodgeholes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
		//audio è la musica del gioco
		//sound è il suono di arrivo nella buca di destinazione
		sql="CREATE TABLE setting ";
		sql+="(audio int default 1,";
		sql+="sound int default 1)";
		db.execSQL(sql);
		
		addSetting(db);
	}

	private void addSetting(SQLiteDatabase database)
	{
		
		//Carico il database 
		ContentValues valori = new ContentValues();
		 
		valori.put("audio",1); 
		valori.put("sound",1);
		
		/*
		 * Il metodo insert restituisce l'ID della riga appena creata, in caso di successo,
		 * altrimenti restituisce -1
		 * primo parametro nome della tabella in cui fare l'inserimento
		 * secondo parametro (NULL) perchè utile quando si vuole inserire un record con 
		 * valori tutti null
		 * terzo parametro,la mappa dei valori da inserire
		 */
		long id = database.insert("setting", null, valori);
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
	}
}