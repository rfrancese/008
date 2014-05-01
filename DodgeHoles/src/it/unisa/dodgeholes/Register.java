package it.unisa.dodgeholes;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends Activity implements View.OnClickListener {
	
	private Button buttReg;
	private EditText nick;
	private String sv="";
	private DbLocale database = null;
	private TextView registerErrorMsg;
	
	


    private static String KEY_SUCCESS = "success";
    private static String KEY_UID = "uid";
    private static String KEY_FIRSTNAME = "fname";
    private static String KEY_LASTNAME = "lname";
    private static String KEY_USERNAME = "uname";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";
    private static String KEY_ERROR = "error";

	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.register);
		
		buttReg=(Button)findViewById(R.id.buttReg);
		buttReg.setOnClickListener(this);
		nick=(EditText)findViewById(R.id.nickname);
		registerErrorMsg=(TextView)findViewById(R.id.msgerror);
		
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
			NetAsync(v);
		}
	}
	
	 public void NetAsync(View view)
	 {
         new NetCheck().execute();
     }
	
	    /**
	     * Async Task to check whether internet connection is working
	     **/
	 private class NetCheck extends AsyncTask<String,String,Boolean>
	    {
	        private ProgressDialog nDialog;

	        @Override
	        protected void onPreExecute(){
	            super.onPreExecute();
	            nDialog = new ProgressDialog(Register.this);
	            nDialog.setMessage("Loading..");
	            nDialog.setTitle("Checking Network");
	            nDialog.setIndeterminate(false);
	            nDialog.setCancelable(true);
	            nDialog.show();
	        }

	        @Override
	        protected Boolean doInBackground(String... args)
	        {
	/**
	 * Gets current device state and checks for working internet connection by trying Google.
	 **/
	            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	            NetworkInfo netInfo = cm.getActiveNetworkInfo();
	            if (netInfo != null && netInfo.isConnected()) {
	                try {
	                    URL url = new URL("http://www.google.com");
	                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
	                    urlc.setConnectTimeout(3000);
	                    urlc.connect();
	                    if (urlc.getResponseCode() == 200) {
	                        return true;
	                    }
	                } catch (MalformedURLException e1) {
	                    // TODO Auto-generated catch block
	                    e1.printStackTrace();
	                } catch (IOException e) {
	                    // TODO Auto-generated catch block
	                    e.printStackTrace();
	                }
	            }
	            return false;

	        }
	        
	        protected void onPostExecute(Boolean th)
	        {
	            if(th == true)
	            {
	                nDialog.dismiss();
	                new ProcessRegister().execute();
	            }
	            else
	            {
	                nDialog.dismiss();
					registerErrorMsg.setText("Error in Network Connection");
	            }
	        }
	    }
	 
	 
	 
	 private class ProcessRegister extends AsyncTask<String, String, JSONObject> {

		 /**
		  * Defining Process dialog
		  **/
		         private ProgressDialog pDialog;

		         String nickname;
		         
		         protected void onPreExecute()
		         {
		             super.onPreExecute();
		             nick = (EditText) findViewById(R.id.nickname);
		             nickname=nick.getText().toString();
		             
		             pDialog = new ProgressDialog(Register.this);
		             pDialog.setTitle("Contacting Servers");
		             pDialog.setMessage("Registering ...");
		             pDialog.setIndeterminate(false);
		             pDialog.setCancelable(true);
		             pDialog.show();
		         }

		         @Override
		         protected JSONObject doInBackground(String... args)
		         {
			         UserFunctions userFunction = new UserFunctions();
			         JSONObject json = userFunction.registerUser(nickname);
			         return json;
		         }

		         protected void onPostExecute(JSONObject json)
		         {
		        /**
		         * Checks for success message.
		         **/
		                 try
		                 {
		                     if (json.getString(KEY_SUCCESS) != null)
		                     {
		                         registerErrorMsg.setText("");
		                         String res = json.getString(KEY_SUCCESS);

		                         String red = json.getString(KEY_ERROR);

		                         if(Integer.parseInt(res) == 1)
		                         {
		                            // pDialog.setTitle("Getting Data");
		                            // pDialog.setMessage("Loading Info");

		                             registerErrorMsg.setText("Successfully Registered");
		                             
		                             registraUtenteInLocale();
		                            
		                             //Intent registered = new Intent(getApplicationContext(), GameScreen.class);

		                             /**
		                              * Close all views before launching Registered screen
		                             **/
		                            /* registered.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		                             pDialog.dismiss();
		                             startActivity(registered);


		                               finish();*/
		                         }

		                         else if (Integer.parseInt(red) ==2)
		                         {
		                             pDialog.dismiss();
		                             registerErrorMsg.setText("User already exists");
		                         }
		                     }
		                     else
		                     {
		                         pDialog.dismiss();
		                         registerErrorMsg.setText("Error occured in registration");
		                     }

		                 } catch (JSONException e)
		                 {
		                     e.printStackTrace();
		                 }
		             }}
	 
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
