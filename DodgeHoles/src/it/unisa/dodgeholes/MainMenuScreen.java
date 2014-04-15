package it.unisa.dodgeholes;

import it.unisa.dodgeholes.framework.Game;
import it.unisa.dodgeholes.framework.Input.TouchEvent;
import it.unisa.dodgeholes.framework.gl.Camera2D;
import it.unisa.dodgeholes.framework.gl.SpriteBatcher;
import it.unisa.dodgeholes.framework.impl.GLScreen;
import it.unisa.dodgeholes.framework.math.OverlapTester;
import it.unisa.dodgeholes.framework.math.Rectangle;
import it.unisa.dodgeholes.framework.math.Vector2;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;


public class MainMenuScreen extends GLScreen {
    Camera2D guiCam;
    SpriteBatcher batcher;
    Rectangle soundBounds;
    Rectangle playBounds;
    Vector2 touchPoint;

    public MainMenuScreen(Game game) {
        super(game);
        guiCam = new Camera2D(glGraphics, 480, 320);
        batcher = new SpriteBatcher(glGraphics, 100);
        soundBounds = new Rectangle(0, 0, 64, 64);
        playBounds = new Rectangle(240-54, 150-19, 109, 38);
        
        
        touchPoint = new Vector2();               
    }       

    @Override
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
                    
                    game.setScreen(new GameScreen(game));
                    return;
                }
                
                if(OverlapTester.pointInRectangle(soundBounds, touchPoint)) {
                   
                    Settings.soundEnabled = !Settings.soundEnabled;
                    if(Settings.soundEnabled) 
                        Assets.music.play();
                    else
                        Assets.music.pause();
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        GL10 gl = glGraphics.getGL();        
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        guiCam.setViewportAndMatrices();
        
        gl.glEnable(GL10.GL_TEXTURE_2D);
        
        batcher.beginBatch(Assets.background);
        batcher.drawSprite(240, 160, 480, 320, Assets.backgroundRegion);
        batcher.endBatch();
        
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);               
        
        batcher.beginBatch(Assets.myitem);                 
        
        batcher.drawSprite(240, 320 - 10 - 71, 300, 135, Assets.logo);
       
        batcher.drawSprite(240, 150, 109, 38, Assets.play);
        batcher.drawSprite(32, 32, 64, 64, Settings.soundEnabled?Assets.soundOn:Assets.soundOff);
                
        batcher.endBatch();
        
        gl.glDisable(GL10.GL_BLEND);
    }
    
    @Override
    public void pause() {        
        Settings.save(game.getFileIO());
    }

    @Override
    public void resume() {        
    }       

    @Override
    public void dispose() {        
    }
}
