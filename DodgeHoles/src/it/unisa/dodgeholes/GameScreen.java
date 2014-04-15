package it.unisa.dodgeholes;

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



public class GameScreen extends GLScreen {
    static final int GAME_READY = 0;    
    static final int GAME_RUNNING = 1;
    static final int GAME_PAUSED = 2;
    static final int GAME_LEVEL_END = 3;
    static final int GAME_OVER = 4;
  
    int state;
    Camera2D guiCam;
    Vector2 touchPoint;
    SpriteBatcher batcher;    
    World world;
    
    WorldRenderer renderer;    
    Rectangle pauseBounds;
    Rectangle resumeBounds;
    Rectangle quitBounds;
      
    FPSCounter fpsCounter;
    
    public GameScreen(Game game) {
        super(game);
        state = GAME_READY;
        guiCam = new Camera2D(glGraphics, 480, 320);
        touchPoint = new Vector2();
        batcher = new SpriteBatcher(glGraphics, 1000);
        
        world = new World();
        renderer = new WorldRenderer(glGraphics, batcher, world);
        pauseBounds = new Rectangle(480- 64, 320- 64, 64, 64);
        resumeBounds = new Rectangle(240-55, 160-13, 110, 27);
        quitBounds = new Rectangle(240-30, 160-27-13, 61, 27);
        
        fpsCounter = new FPSCounter();
    }

	@Override
	public void update(float deltaTime) {
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
	        world = new World();
	        renderer = new WorldRenderer(glGraphics, batcher, world);
	        
	        state = GAME_READY;
	    }
	}
	
	private void updateGameOver() {
	    List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
	    int len = touchEvents.size();
	    for(int i = 0; i < len; i++) {                   
	        TouchEvent event = touchEvents.get(i);
	        if(event.type != TouchEvent.TOUCH_UP)
	            continue;
	        game.setScreen(new MainMenuScreen(game));
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
	    
	    }
	    batcher.endBatch();
	    gl.glDisable(GL10.GL_BLEND);
	    fpsCounter.logFrame();
	}
	
	private void presentReady() {
	    batcher.drawSprite(240, 160, 108, 32, Assets.ready);
	}
	
	private void presentRunning() {
	    batcher.drawSprite(480 - 32, 320 - 32, 64, 64, Assets.pause);
	    
	}
	
	private void presentPaused() {        
	    batcher.drawSprite(240, 160, 110, 27, Assets.resume);
	    batcher.drawSprite(240, 160-27, 61, 27, Assets.quit);
	    
	}
	
	

    @Override
    public void pause() {
        if(state == GAME_RUNNING)
            state = GAME_PAUSED;
    }

    @Override
    public void resume() {        
    }

    @Override
    public void dispose() {       
    }
}
