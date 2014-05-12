package it.unisa.dodgeholes;

import it.unisa.dodgeholes.framework.Game;
import it.unisa.dodgeholes.framework.Input.TouchEvent;
import it.unisa.dodgeholes.framework.gl.Camera2D;
import it.unisa.dodgeholes.framework.gl.SpriteBatcher;
import it.unisa.dodgeholes.framework.impl.GLScreen;
import it.unisa.dodgeholes.framework.math.OverlapTester;
import it.unisa.dodgeholes.framework.math.Rectangle;
import it.unisa.dodgeholes.framework.math.Vector2;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import org.json.JSONException;
import org.json.JSONObject;

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
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


public class MainMenuScreen extends GLScreen {
    Camera2D guiCam;
    SpriteBatcher batcher;
    //Rectangle soundBounds;
    Rectangle playBounds;
    Rectangle levelBounds;
    Rectangle settingBounds;
    Rectangle registerBounds;
    Rectangle scoresBounds;
    Vector2 touchPoint;
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";

	private DbLocale database = null;

    public MainMenuScreen(Game game) {
        super(game);
        guiCam = new Camera2D(glGraphics, 480, 320);
        batcher = new SpriteBatcher(glGraphics, 100);
        //soundBounds = new Rectangle(0, 0, 64, 64);
        playBounds = new Rectangle(240-54, 150-38-19, 109, 38);
        levelBounds = new Rectangle(240-109-54, 150-19, 109, 38);
        settingBounds = new Rectangle(240-109-54, 150-76-19, 109, 38);
        registerBounds = new Rectangle(240+109-54, 150-76-19, 109, 38);
        scoresBounds = new Rectangle(240+109-54, 150-19, 109, 38);
        database = new DbLocale(glGame.getApplicationContext());
        
        touchPoint = new Vector2();
       
        new NetCheck().execute();
    }
   
	    /**
	     * Async Task to check whether internet connection is working
	     **/
	 private class NetCheck extends AsyncTask<String,String,Boolean>
	    {
	      
	        protected void onPreExecute()
	        {
	            super.onPreExecute();
	        }

	        @Override
	        protected Boolean doInBackground(String... args)
	        {
	/**
	 * Gets current device state and checks for working internet connection by trying Google.
	 **/
	            ConnectivityManager cm = (ConnectivityManager) glGame.getSystemService(Context.CONNECTIVITY_SERVICE);
	            NetworkInfo netInfo = cm.getActiveNetworkInfo();
	            if (netInfo != null && netInfo.isConnected())
	            {
	                try
	                {
	                    URL url = new URL("http://www.google.com");
	                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
	                    urlc.setConnectTimeout(3000);
	                    urlc.connect();
	                    if (urlc.getResponseCode() == 200)
	                    {
	                        return true;
	                    }
	                }
	                catch (MalformedURLException e1)
	                {
	                    e1.printStackTrace();
	                }
	                catch (IOException e)
	                {
	                    e.printStackTrace();
	                }
	            }
	            return false;

	        }
	        
	        protected void onPostExecute(Boolean th)
	        {
	            if(th == true)
	            {
	                new ProcessRegister().execute();
	            }
	            //se non c'e connessione non faccio nulla,perche' non posso recuperare i dati dal server
	        }
	    }
	 
	 
	 private class ProcessRegister extends AsyncTask<String, String, JSONObject>
	 {

		         private String deviceIMEI;
		        
		         private ArrayList<String> livelli;
		         
		         private ArrayList<Integer> livello_punteggio;
		         
		         protected void onPreExecute()
		         {
		             super.onPreExecute();
		             //Ricavo l'IMEI
		             TelephonyManager tManager = (TelephonyManager)glGame.getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
		            this.deviceIMEI = tManager.getDeviceId();
		            livelli=new ArrayList<String>();
		            
		            livello_punteggio=new ArrayList<Integer>();
		            
		            caricaLivelliUtenteEpunteggi();
		         }

		         private void caricaLivelliUtenteEpunteggi()
		         {
		        	 //Dovrei usare una mappa per essere piu' efficiente !
		        	SQLiteDatabase db = database.getReadableDatabase();
		     		final String sql = "SELECT * FROM punteggi";
		     		 
		     		 Cursor c = db.rawQuery(sql, null);
		     		 //Scorri la query e riempi gli array
		     		 String liv[]=new String[5];
		     		 for(int i=0;i<liv.length;i++)
		     		 {
		     			 liv[i]="Livello"+(i+1);
		     		 }
		     		 
		     		 
		     		 
		     		while(c.moveToNext())
		     		{
		     			livelli.add(c.getString(1));
		     			livello_punteggio.add(c.getInt(0));
		     		}
		     		
		     		int f=0;
		     		
		     		for(int j=0;j<liv.length;j++)
	     			{
		     			f=0;
			     		for(int i=0;i<livelli.size();i++)
			     		{
			     			if(liv[j].equals(livelli.get(i)))
			     			{
			     				f=1;
			     			}
			     		}
			     		if(f==0)
			     		{
			     			livelli.add("Livello"+(j+1));
			     			livello_punteggio.add(0);
			     		}
	     			}
		     		
		     		 db.close();
		         }
		        
		         
		         @Override
		         protected JSONObject doInBackground(String... args)
		         {
		        	 //Qui controlla prima se l'utente non ha effettuato una registrazione in locale
			         UserFunctions userFunction = new UserFunctions();
			         JSONObject json1 = userFunction.caricaPunteggi(livelli,livello_punteggio,deviceIMEI);
			         JSONObject json = userFunction.controllaIMEI(deviceIMEI);
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
		                         String res = json.getString(KEY_SUCCESS);

		                         String red = json.getString(KEY_ERROR);
		                         
		                         Log.d("Successo :",""+Integer.parseInt(res));
		                         if(Integer.parseInt(res) == 1)
		                         {
		                        	 //Non faccio nulla! Imei non ancora registrato
		                         }

		                         else if (Integer.parseInt(red) ==2)
		                         {
		                            //Inserisci nickname in locale con quello scaricare dal server tramite JSON
		                        	 registraUtenteInLocale(json.getString("dati"));
		                         }
		                     }

		                 } catch (JSONException e)
		                 {
		                     e.printStackTrace();
		                 }
		             }}
	 
	private void registraUtenteInLocale(String nickname)
	{
		SQLiteDatabase db = this.database.getWritableDatabase();
		
		/*
		 * Utlizziamo l'oggetto ContentValues per creare una mappa dei nostri valori
		 */
		
		//Carico il database 
		ContentValues valori = new ContentValues();
		 
		valori.put("nickname", nickname); 
		
		/*
		 * Il metodo insert restituisce l'ID della riga appena creata, in caso di successo,
		 * altrimenti restituisce -1
		 * primo parametro nome della tabella in cui fare l'inserimento
		 * secondo parametro (NULL) perche' utile quando si vuole inserire un record con 
		 * valori tutti null
		 * terzo parametro,la mappa dei valori da inserire
		 */
		long id = db.insert("access", null, valori);
		
		db.close();
	}

    
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);                        
            if(event.type == TouchEvent.TOUCH_UP) {
                touchPoint.set(event.x, event.y);
                guiCam.touchToWorld(touchPoint);
                
                if(OverlapTester.pointInRectangle(playBounds, touchPoint)) {
                    
                    game.setScreen(new GameScreen(game,1));
                    
                    return;
                }
                
                if(OverlapTester.pointInRectangle(levelBounds, touchPoint)) {
                    
                	Intent myIntent = new Intent(this.glGame, ChooseLevel.class);
                	SingletonParametersBridge.getInstance().addParameter("game",game);
                	this.glGame.startActivity(myIntent);
                	
                    return;
                }
                
                if(OverlapTester.pointInRectangle(settingBounds, touchPoint)) {
                	Intent myIntent = new Intent(this.glGame, Setting.class);
                	this.glGame.startActivity(myIntent);
                	
                    return;
                }
                
                if(OverlapTester.pointInRectangle(registerBounds, touchPoint)) {
                	Intent myIntent = new Intent(this.glGame, Register.class);
                	this.glGame.startActivity(myIntent);
                	
                    return;
                }
                
                if(OverlapTester.pointInRectangle(scoresBounds, touchPoint)) {
                	Intent myIntent = new Intent(this.glGame, Scores.class);
                	this.glGame.startActivity(myIntent);
                	
                    return;
                }
                
                /*if(OverlapTester.pointInRectangle(soundBounds, touchPoint)) {
                   
                    Save.soundEnabled = !Save.soundEnabled;
                    if(Save.soundEnabled) 
                        Assets.music.play();
                    else
                        Assets.music.pause();
                }*/
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        GL10 gl = glGraphics.getGL();        
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        guiCam.setViewportAndMatrices();
        
        gl.glEnable(GL10.GL_TEXTURE_2D);
        
        batcher.beginBatch(Assets.backgroundMenu);
        batcher.drawSprite(240, 160, 480, 320, Assets.backgroundMenuRegion);
        batcher.endBatch();
        
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);               
        
        batcher.beginBatch(Assets.myitem);                 
        
        batcher.drawSprite(240, 320 - 10 - 71, 300, 135, Assets.logo);
       
        batcher.drawSprite(240, 150-38, 109, 38, Assets.play);
        batcher.drawSprite(240-109, 150, 109, 38, Assets.level);
        batcher.drawSprite(240+109, 150, 109, 38, Assets.scores);
        batcher.drawSprite(240-109, 150-76, 109, 38, Assets.setting);
        batcher.drawSprite(240+109, 150-76, 109, 38, Assets.register);
        
        //batcher.drawSprite(32, 32, 64, 64, Save.soundEnabled?Assets.soundOn:Assets.soundOff);
                
        batcher.endBatch();
        
        gl.glDisable(GL10.GL_BLEND);
    }
    
    @Override
    public void pause() {        
        //Save.save(game.getFileIO());
    	/*if(Assets.musicActive)
    		Assets.music.stop();*/
    }

    @Override
    public void resume() {
    	/*if(Assets.musicActive)
    		Assets.music.play();*/
    }       

    @Override
    public void dispose() {        
    }
}
