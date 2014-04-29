package it.unisa.dodgeholes;

import it.unisa.dodgeholes.World.WorldListener;
import it.unisa.dodgeholes.framework.Game;
import it.unisa.dodgeholes.framework.Input.TouchEvent;
import it.unisa.dodgeholes.framework.gl.Camera2D;
import it.unisa.dodgeholes.framework.gl.FPSCounter;
import it.unisa.dodgeholes.framework.gl.SpriteBatcher;
import it.unisa.dodgeholes.framework.impl.GLScreen;
import it.unisa.dodgeholes.framework.math.OverlapTester;
import it.unisa.dodgeholes.framework.math.Rectangle;
import it.unisa.dodgeholes.framework.math.Vector2;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.KeyEvent;




public class GameScreen extends GLScreen {
    static final int GAME_READY = 0;    
    static final int GAME_RUNNING = 1;
    static final int GAME_PAUSED = 2;
    static final int GAME_LEVEL_END = 3;
    static final int GAME_OVER = 4;
    private DbLocale database = null;
  
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
    
    public GameScreen(Game game, int numLevel) {
        super(game);
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
	            
	        	this.counter=0;
		        world = new World(worldListener,world.getNumLevel()+1);
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
		/*Ho commentato per poter gestire i messaggi di fine livello
		SQLiteDatabase db = this.database.getReadableDatabase();
		String sql = "SELECT * FROM access where livello=\"Livello"+numLevel+"\"";
		Cursor c = db.rawQuery(sql, null);
		int numeroRighe = c.getCount();
		if(numeroRighe!=0)
		{
			//Aggiorno il punteggio migliore sul db locale
			if(c.moveToFirst())
			{
				int punteggio_salvato=c.getInt(1);
				int punteggio_attuale=(int)this.counter;
				if(punteggio_attuale<punteggio_salvato)
				{
					sql="UPDATE access set punteggio="+punteggio_attuale+" where livello=\"Livello"+numLevel+"\"";
				}
			}
			
		}
		db.close();*/
		
		
		//se il nickname e'z registrato sul db locale allora faccio questo altrimenti
		//non faccio nnt !
		//scrivi sul database Nickname recuperato dal db locale se registrato!
		//scrivi punteggio preso da counter con casting a int e numLevel 
		//Concatena Livello+numLevel
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
        if(Assets.musicActive)
        	Assets.music.stop();
    }

    @Override
    public void resume() {
    	if(Assets.musicActive)
    		Assets.music.play();
    }

    @Override
    public void dispose() {       
    }
    
}
