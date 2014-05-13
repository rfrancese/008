package it.unisa.dodgeholes;

import it.unisa.dodgeholes.World.WorldListener;
import it.unisa.dodgeholes.framework.Game;
import it.unisa.dodgeholes.framework.Input.TouchEvent;
import it.unisa.dodgeholes.framework.gl.Camera2D;
import it.unisa.dodgeholes.framework.gl.FPSCounter;
import it.unisa.dodgeholes.framework.gl.SpriteBatcher;
import it.unisa.dodgeholes.framework.impl.GLGame;
import it.unisa.dodgeholes.framework.impl.GLScreen;
import it.unisa.dodgeholes.framework.math.OverlapTester;
import it.unisa.dodgeholes.framework.math.Rectangle;
import it.unisa.dodgeholes.framework.math.Vector2;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

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
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;




public class GameScreen extends GLScreen{
    static final int GAME_READY = 0;    
    static final int GAME_RUNNING = 1;
    static final int GAME_PAUSED = 2;
    static final int GAME_LEVEL_END = 3;
    static final int GAME_OVER = 4;
    static final int NUMBER_TOTAL_LEVEL=5;
    private DbLocale database = null;
    private boolean flag;
  
    private static String KEY_SUCCESS = "success";
    
    int state;
    Camera2D guiCam;
    Vector2 touchPoint;
    SpriteBatcher batcher;    
    World world;
    WorldListener worldListener;
    WorldRenderer renderer;    
    Rectangle pauseBounds;
    Rectangle resumeBounds;
    Rectangle quitBounds;
    Rectangle nextLevelBounds;
    Rectangle tryAgainBounds;
      
    FPSCounter fpsCounter;
    
    float counter, timeCounter;
    private int numLevel;
    private int appCounter;
    private int appLevel;
    
    
    public GameScreen(Game game, int numLevel) {
        super(game);
        this.flag=false;
        database=new DbLocale(glGame.getApplicationContext());
        
        state = GAME_READY;
        this.numLevel=numLevel;
        guiCam = new Camera2D(glGraphics, 480, 320);
        touchPoint = new Vector2();
        batcher = new SpriteBatcher(glGraphics, 1000);
        worldListener = new WorldListener() {
            
            public void hitHole() 
            {            
                Assets.playSound(Assets.hitHoleSound);
            }
            
            public void hitEndHole() 
            {            
                Assets.playSound(Assets.hitEndSound);
            }
              
        };
        world = new World(worldListener, this.numLevel);
        renderer = new WorldRenderer(glGraphics, batcher, world);
        pauseBounds = new Rectangle(480- 52, 320- 18-20, 40, 40);
        resumeBounds = new Rectangle(240-54, 160-19, 109, 38);
        quitBounds = new Rectangle(240-54, 160-45-19, 109, 38);
        nextLevelBounds = new Rectangle(240-54, 160-19, 109, 38);
        tryAgainBounds = new Rectangle(240-54, 160-19, 109, 38);
        
        
        fpsCounter = new FPSCounter();
        
        timeCounter=0;
        counter=0;
    }

	@Override
	public void update(float deltaTime) {
		
		timeCounter+=deltaTime;
		if(timeCounter >= 1.0f && this.state==GAME_RUNNING)
		{
			timeCounter =0;
			counter++;
			//Log.d("timer: ", ""+counter);
		}
		
		
	    if(deltaTime > 0.1f)
	        deltaTime = 0.1f;
	    
	    
	    
	    switch(state) {
	    case GAME_READY:
	        updateReady();
	        break;
	    case GAME_RUNNING:
	        updateRunning(deltaTime);
	        break;
	    case GAME_PAUSED:
	        updatePaused();
	        break;
	    case GAME_LEVEL_END:
	        updateLevelEnd();
	        break;
	    case GAME_OVER:
	        updateGameOver();
	        break;
	    }
	}
	
	private void updateReady() {
	    if(game.getInput().getTouchEvents().size() > 0) {
	        state = GAME_RUNNING;
	    }
	}
	
	private void updateRunning(float deltaTime) {
	    List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
	    int len = touchEvents.size();
	    for(int i = 0; i < len; i++) {
	        TouchEvent event = touchEvents.get(i);
	        if(event.type != TouchEvent.TOUCH_UP)
	            continue;
	        
	        touchPoint.set(event.x, event.y);
	        guiCam.touchToWorld(touchPoint);
	        
	        if(OverlapTester.pointInRectangle(pauseBounds, touchPoint)) {
	            
	            state = GAME_PAUSED;
	            return;
	        }            
	    }
	    
	    world.update(deltaTime, game.getInput().getAccelX(),game.getInput().getAccelY());
	    
	    if(world.state == World.WORLD_STATE_NEXT_LEVEL) {
	        state = GAME_LEVEL_END;        
	    }
	    if(world.state == World.WORLD_STATE_GAME_OVER) {
	        state = GAME_OVER;
	        
	    }
	}
	
	private void updatePaused() {
	    List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
	    int len = touchEvents.size();
	    for(int i = 0; i < len; i++) {
	        TouchEvent event = touchEvents.get(i);
	        if(event.type != TouchEvent.TOUCH_UP)
	            continue;
	        
	        touchPoint.set(event.x, event.y);
	        guiCam.touchToWorld(touchPoint);
	        
	        if(OverlapTester.pointInRectangle(resumeBounds, touchPoint)) {
	            
	            state = GAME_RUNNING;
	            return;
	        }
	        
	        if(OverlapTester.pointInRectangle(quitBounds, touchPoint)) {
	            
	            game.setScreen(new MainMenuScreen(game));
	            return;
	        
	        }
	    }
	}
	
	private void updateLevelEnd() {
	    List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
	    int len = touchEvents.size();
	    for(int i = 0; i < len; i++) {                   
	        TouchEvent event = touchEvents.get(i);
	        if(event.type != TouchEvent.TOUCH_UP)
	            continue;
	        
	        touchPoint.set(event.x, event.y);
	        guiCam.touchToWorld(touchPoint);
	        
	        if(OverlapTester.pointInRectangle(nextLevelBounds, touchPoint)) {
	            
	        	if(numLevel<=NUMBER_TOTAL_LEVEL)
	        	{
		        	this.counter=0;
			        world = new World(worldListener,world.getNumLevel()+1);
			        renderer = new WorldRenderer(glGraphics, batcher, world);
			        state = GAME_READY;
	        	}
	        	else
	        	{
	        		game.setScreen(new MainMenuScreen(game));
	        	}
	        	
	            return;
	        }
	        
	        if(OverlapTester.pointInRectangle(quitBounds, touchPoint)) {
	            
	            game.setScreen(new MainMenuScreen(game));
	            return;
	        }
	        
	    }
	}
	
	private void updateGameOver() {
	    List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
	    int len = touchEvents.size();
	    for(int i = 0; i < len; i++) {                   
	        TouchEvent event = touchEvents.get(i);
	        if(event.type != TouchEvent.TOUCH_UP)
	            continue;
	        
	        touchPoint.set(event.x, event.y);
	        guiCam.touchToWorld(touchPoint);
	        
	        if(OverlapTester.pointInRectangle(tryAgainBounds, touchPoint)) {
	            
	        	this.counter=0;
		        world = new World(worldListener,world.getNumLevel());
		        renderer = new WorldRenderer(glGraphics, batcher, world);
		        state = GAME_READY;
	            return;
	        }
	        
	        if(OverlapTester.pointInRectangle(quitBounds, touchPoint)) {
	            
	            game.setScreen(new MainMenuScreen(game));
	            return;
	        }
	    }
	}

	@Override
	public void present(float deltaTime) {
	    GL10 gl = glGraphics.getGL();
	    gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    gl.glEnable(GL10.GL_TEXTURE_2D);
	    
	    renderer.render();
	    
	    guiCam.setViewportAndMatrices();
	    gl.glEnable(GL10.GL_BLEND);
	    gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	    batcher.beginBatch(Assets.myitem);
	    switch(state) {
	    case GAME_READY:
	        presentReady();
	        this.flag=false;
	        break;
	    case GAME_RUNNING:
	        presentRunning();
	        break;
	    case GAME_PAUSED:
	        presentPaused();
	        break;
	    case GAME_LEVEL_END:
	    	presentLevelEnd();
	    	break;
	    case GAME_OVER:
	        presentGameOver();
	        break;
	    
	    }
	    batcher.endBatch();
	    gl.glDisable(GL10.GL_BLEND);
	    //fpsCounter.logFrame();
	    
	}
	
	private void presentReady() 
	{
		batcher.drawSprite(35, 320 - 18, 53, 25, Assets.lifeWrite);
	    batcher.drawSprite(260, 320 - 18, 71, 25, Assets.timeWrite);
	    Assets.font.drawText(batcher,(int)counter+"s", 311, 320 - 18);
		
	    batcher.drawSprite(240, 160, 208, 38, Assets.ready);
	}
	
	private void presentRunning() 
	{
	    batcher.drawSprite(480 - 32, 320 - 18, 40, 40, Assets.pause);
	    
	    batcher.drawSprite(35, 320 - 18, 53, 25, Assets.lifeWrite);
	    batcher.drawSprite(260, 320 - 18, 71, 25, Assets.timeWrite);
	    Assets.font.drawText(batcher,(int)counter+"s", 311, 320 - 18);
	}
	
	private void presentPaused() 
	{ 
		
		batcher.drawSprite(35, 320 - 18, 53, 25, Assets.lifeWrite);
	    batcher.drawSprite(260, 320 - 18, 71, 25, Assets.timeWrite);
	    Assets.font.drawText(batcher,(int)counter+"s", 311, 320 - 18);
	    
	    batcher.drawSprite(240, 160, 109, 38, Assets.resume);
	    batcher.drawSprite(240, 160-45, 109, 38, Assets.quit);
	    
	}
	
	private void presentLevelEnd()
	{
		batcher.drawSprite(35, 320 - 18, 53, 25, Assets.lifeWrite);
	    batcher.drawSprite(260, 320 - 18, 71, 25, Assets.timeWrite);
	    Assets.font.drawText(batcher,(int)counter+"s", 311, 320 - 18);
		
		batcher.drawSprite(240, 200, 92, 38, Assets.win);
		batcher.drawSprite(240, 160, 109, 38, Assets.nextLevel);
		batcher.drawSprite(240, 160-45, 109, 38, Assets.quit);
		
		if(!flag)
		{
			appCounter=(int)counter;
			this.appLevel=this.numLevel;
			this.numLevel++;
			NetAsync(glGame.getCurrentFocus());
			this.flag=true;
		}
	}

	/*Devi controllare questo codice
	 * 
	 */
	 public void NetAsync(View v)
	 {
		 //Se precedentemente mi sono registrato
		 //Se c'e' un nickname registrato in locale allora posso trasmettere i dati
		 //sul server altrimenti memorizzo in locale
		 scriviPunteggioInLocale();
		 if(leggiDati())
		 { 
			 Log.d("Messaggio","L'utente non e' registrato e quindi non posso trasmettere il punteggio in rete");
		 }
		 else
		 {
			 new NetCheck().execute();
		 }
     }
	
	 public void scriviPunteggioInLocale()
	 {
		 SQLiteDatabase db = this.database.getReadableDatabase();
			String sql = "SELECT * FROM punteggi where livello=\"Livello"+appLevel+"\"";
			Cursor c = db.rawQuery(sql, null);
			int numeroRighe = c.getCount();
			
			db=this.database.getWritableDatabase();
			
			if(numeroRighe>0)
			{
				//Aggiorno il punteggio migliore sul db locale
				if(c.moveToFirst())
				{
					
					int punteggio_salvato=c.getInt(0);
					int punteggio_attuale=this.appCounter;
					
					if(punteggio_attuale<punteggio_salvato)
					{
						String whereClause = "livello = ?";
						 
						/*
						 * Per ogni campo che ho scritto nella varabile whereClause devo specificare, in whereArgs
						 * il valori a cui sono condizionati.
						 */
						String li="Livello"+appLevel;
						
						String[] whereArgs = {li};
						
						 /*
						  * Utlizziamo l'oggetto ContentValues per creare una mappa dei nostri valori
						  */
						
						 ContentValues valori = new ContentValues();

						 valori.put("punteggio",punteggio_attuale);  

						 /*
						  * Il metodo update restituisce il numero di righe aggiornate
						  */
		
						 int id = db.update("punteggi", valori, whereClause,whereArgs);
					}
				}
			}
			else
			{
					
					/*
					 * Utlizziamo l'oggetto ContentValues per creare una mappa dei nostri valori
					 */
					
					//Carico il database 
		
					ContentValues valori = new ContentValues();
					 
					int punteggio_attuale=this.appCounter;
					 
					valori.put("punteggio", punteggio_attuale);
					valori.put("livello","Livello"+appLevel);
					
					/*
					 * Il metodo insert restituisce l'ID della riga appena creata, in caso di successo,
					 * altrimenti restituisce -1
					 * primo parametro nome della tabella in cui fare l'inserimento
					 * secondo parametro (NULL) perche' utile quando si vuole inserire un record con 
					 * valori tutti null
					 * terzo parametro,la mappa dei valori da inserire
					 */
		 
					long id = db.insert("punteggi", null, valori);
			}
			db.close();
		 Log.d("Messaggio","Entro in scriviPunteggioInLocale");
		}
		
	 
	 
	 
	//Controllo se e' presente un record nella tabella access
		public boolean leggiDati()
		{
			SQLiteDatabase db = this.database.getReadableDatabase();
			final String sql = "SELECT * FROM access";
			 
			 Cursor c = db.rawQuery(sql, null);
			 
			 int numeroRighe = c.getCount();
			 db.close();
			 Log.d("DB LOCALE",""+numeroRighe);
			 if(numeroRighe==0)
				 return true;
			return false;
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
	        	ConnectivityManager cm = (ConnectivityManager)glGame.getSystemService(Context.CONNECTIVITY_SERVICE);
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
	            	Log.d("Messaggio","Richiamo la classe per salvare i punteggi");
	                new ProcessRegister().execute();
	            }
	            else
	            {
	                Log.d("Messaggio","Network error. The score will be saved on the local");
	            }
	        }
	    }
	 
	 
	 
	 private class ProcessRegister extends AsyncTask<String, String, JSONObject>
	 {

		 /**
		  * Defining Process dialog
		  **/
		         private String nickname;
		         private String livello;
		         private int punteggio;
		         
		         protected void onPreExecute()
		         {
		             super.onPreExecute();
		             this.livello="Livello"+appLevel;
			         this.nickname=recuperaNickname();
			         this.punteggio=appCounter;
			         
			         Log.d("Dati da inviare al parser:",""+this.nickname+" "+this.livello+" "+this.punteggio);
		         }

		         protected JSONObject doInBackground(String... args)
		         {
		        	 UserFunctions userFunction = new UserFunctions();
			         JSONObject json = userFunction.salvoPunteggio(nickname,livello,punteggio);
			         if(json==null)
			        	 Log.d("doInBackground","Json null");
			         return json;
		         }

		         protected void onPostExecute(JSONObject json)
		         {
		        	 	try
		                 {
		                     if (json.getString(KEY_SUCCESS) != null)
		                     {
		                         String res = json.getString(KEY_SUCCESS);
		                         
		                         Log.d("Successo :",""+Integer.parseInt(res));
		                         
		                         if(Integer.parseInt(res) == 1)
		                         {
		                        	 //Miglior punteggio registrato
		                        	 
		                             Log.d("Messaggi","You have improved his personal record");
		                         }
		                         if(Integer.parseInt(res) == 2)
		                         {
		                            //Punteggio inferiore al tuo record personale
		                            Log.d("Messaggio","Punteggio inferiore al tuo record personale");
		                         
		                         }
		                     }
		                     else
		                     {
		                         Log.d("Messaggio","Errore nel trattamento dei dati");
		                         //Errore nel trasmettere i dati
		                     }

		                 } 
		                 catch (JSONException e)
		                 {
		                     e.printStackTrace();
		                 }
		         }
	 }
	
	public String recuperaNickname()
	 {
		 SQLiteDatabase db = this.database.getReadableDatabase();
			final String sql = "SELECT * FROM access";
			 
			 Cursor c = db.rawQuery(sql, null);
			 String nick=null;
			 
			 if(c.moveToFirst())
				 nick=c.getString(0);
			 Log.d("Messaggio",nick);
			 db.close();
			 return nick;
	 }
	
	private void presentGameOver() 
	{
		batcher.drawSprite(35, 320 - 18, 53, 25, Assets.lifeWrite);
	    batcher.drawSprite(260, 320 - 18, 71, 25, Assets.timeWrite);
	    Assets.font.drawText(batcher,(int)counter+"s", 311, 320 - 18);
		
		 batcher.drawSprite(240, 200, 256, 38, Assets.gameOver);
		 batcher.drawSprite(240, 160, 109, 38, Assets.tryAgain);
		 batcher.drawSprite(240, 160-45, 109, 38, Assets.quit);
	}
	
	
    @Override
    public void pause() {
        if(state == GAME_RUNNING)
            state = GAME_PAUSED;
        /*if(Assets.musicActive)
        	Assets.music.pause();*/
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
