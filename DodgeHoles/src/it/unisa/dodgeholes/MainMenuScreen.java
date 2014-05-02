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

import android.content.Intent;
import android.os.Bundle;


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
