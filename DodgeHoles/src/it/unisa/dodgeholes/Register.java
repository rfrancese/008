package it.unisa.dodgeholes;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity implements View.OnClickListener {
	
	private Button buttReg;
	private EditText nick;
	private String sv="";
	private DbLocale database = null;
	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.register);
		
		buttReg=(Button)findViewById(R.id.buttReg);
		buttReg.setOnClickListener(this);
		nick=(EditText)findViewById(R.id.nickname);
		database = new DbLocale(getApplicationContext());

	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(nick.getText().toString().matches(""))
		{
			new AlertDialog.Builder(this)
			.setTitle("Error")
			.setMessage("Insert a nickname!")
			.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dlg, int sumthin) {
					
						
				}
			})
			.show();
		}
		else
		{
			//Controllo se l'utente si è registrato precedentemente
			if(leggiDati())
			{
				SQLiteDatabase db = this.database.getWritableDatabase();
				
				/*
				 * Utlizziamo l'oggetto ContentValues per creare una mappa dei nostri valori
				 */
				ContentValues valori = new ContentValues();
				 
				valori.put("nickname", nick.getText().toString()); //Inserisco il nome
				valori.put("punteggio", "");  //Inserisco il cognome
				valori.put("livello", "");     //Inserisco l'email
				 
				/*
				 * Il metodo insert restituisce l'ID della riga appena creata, in caso di successo,
				 * altrimenti restituisce -1
				 * primo parametro nome della tabella in cui fare l'inserimento
				 * secondo parametro (NULL) perchè utile quando si vuole inserire un record con 
				 * valori tutti null
				 * terzo parametro,la mappa dei valori da inserire
				 */
				long id = db.insert("access", null, valori);
				db.close();
			}
			else
			{
				//Messaggio d'errore
				new AlertDialog.Builder(this)
				.setTitle("Attenzione")
				.setMessage("Dal seguente dispositivo,ci risulta già una registrazione!")
				.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dlg, int sumthin) {
						
							
					}
				})
				.show();
			}
			
			
		}
		
	}
	
	public void onPause()
	{
		super.onPause();
		if(Assets.musicActive)
			Assets.music.stop();
	}
	
	public void onResume()
	{
		super.onResume();
		if(Assets.musicActive)
		{
			Assets.music.play();
		}
	}
	
	//Controllo se è presente un nickname nel database
	public boolean leggiDati()
	{
		SQLiteDatabase db = this.database.getReadableDatabase();
		final String sql = "SELECT * FROM access where nickname is not null";
		 
		 Cursor c = db.rawQuery(sql, null);
		 
		 int numeroRighe = c.getCount();
		 db.close();
		 if(numeroRighe==0)
			 return true;
		return false;
	}
	
	
	
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if ((keyCode == KeyEvent.KEYCODE_BACK))
		{
			//Log.d("back", "entro !");
		    this.finish(); 
		}
		
		return super.onKeyDown(keyCode, event);
	}

}
