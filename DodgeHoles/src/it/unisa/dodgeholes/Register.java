package it.unisa.dodgeholes;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
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
			//Determino se e' attiva la connessione a internet
			ConnectivityManager cm =(ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
			boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
			//se e' attiva la connessione
			if(isConnected)
			{
				//Se nickname gia' presente
				if(nicknameRegistrato())
				{
						new AlertDialog.Builder(this)
						.setTitle("Errore")
						.setMessage("Nickname non disponibile!")
						.setNeutralButton("Ok", new DialogInterface.OnClickListener(){
							public void onClick(DialogInterface dlg, int sumthin) {
							}
						})
						.show();
				}
				else
				{
					this.registraUtenteInLocale();
					this.registraUtenteSulServer();
				}
			}
			else
			{
				new AlertDialog.Builder(this)
				.setTitle("Attenzione")
				.setMessage("Rete non disponibile !")
				.setNeutralButton("Ok", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dlg, int sumthin) {
					}
				})
				.show();
			}
		}
	}
		 
	private void registraUtenteSulServer()
	{
		 String result = "";
	        String stringaFinale = "";
	        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	        nameValuePairs.add(new BasicNameValuePair("nickname",nick.getText().toString()));
	        InputStream is = null;
	 
	        //http post
	        try
	        {
	                HttpClient httpclient = new DefaultHttpClient();
	                HttpPost httppost = new HttpPost("http://www.depiano.it/registraUtente.php");
	                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	                HttpResponse response = httpclient.execute(httppost);
	                HttpEntity entity = response.getEntity();
	                is = entity.getContent();
	        }
	        catch(Exception e)
	        {
	                Log.e("TEST", "Errore nella connessione http "+e.toString());
	        }		
	}

	private void registraUtenteInLocale()
	{
		SQLiteDatabase db = this.database.getWritableDatabase();
		
		/*
		 * Utlizziamo l'oggetto ContentValues per creare una mappa dei nostri valori
		 */
		
		//Carico il database 
		ContentValues valori = new ContentValues();
		 
		valori.put("nickname", nick.getText().toString()); 
		
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

	//Controlla che il nickname scelto dall'utente sia disponibile
	public boolean nicknameRegistrato()
	{
		String result = "";
        String stringaFinale = "";
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("nickname",nick.getText().toString()));
        InputStream is = null;
 
        //http post
        try
        {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://www.depiano.it/controllaUtente.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
        }
        catch(Exception e)
        {
                Log.e("TEST", "Errore nella connessione http "+e.toString());
        }
        if(is != null)
        {
            //converto la risposta in stringa
            try
            {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                    }
                    is.close();
 
                    result=sb.toString();
            }catch(Exception e)
            {
                    Log.e("TEST", "Errore nel convertire il risultato "+e.toString());
            }
 
            //parsing dei dati arrivati in formato json
            try
            {
            		
                    JSONArray jArray = new JSONArray(result);
                    JSONObject json_data = jArray.getJSONObject(0);
                    stringaFinale=json_data.getString("messaggio");
                    if(stringaFinale.equals("nickname in uso"))
                    	return true;
            }
            catch(JSONException e)
            {
                    Log.e("log_tag", "Error parsing data "+e.toString());
            }
        }
        return false;
       
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
	
	//Controllo se e' presente un record nella tabella access
	/*
	public boolean leggiDati()
	{
		SQLiteDatabase db = this.database.getReadableDatabase();
		final String sql = "SELECT * FROM access";
		 
		 Cursor c = db.rawQuery(sql, null);
		 
		 int numeroRighe = c.getCount();
		 db.close();
		 if(numeroRighe==0)
			 return true;
		return false;
	}
	*/
	
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
