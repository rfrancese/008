package it.unisa.dodgeholes;

import it.unisa.dodgeholes.framework.Screen;
import it.unisa.dodgeholes.framework.impl.GLGame;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.view.KeyEvent;


public class StartGame extends GLGame {
    boolean firstTimeCreate = true;
    
    @Override
    public Screen getStartScreen() {
        return new MainMenuScreen(this);
    }
    
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {         
        super.onSurfaceCreated(gl, config);
        if(firstTimeCreate) {
        	//Invece di salvare sul file,salva sul database
            //Save.load(getFileIO());
            Assets.load(this);
            firstTimeCreate = false;            
        } else {
            Assets.reload();
        }
    }     
    
    @Override
    public void onPause() {
        super.onPause();
        /*if(Save.soundEnabled)
            Assets.music.pause();*/
        if(Assets.musicActive)
        	Assets.music.pause();
        	
    }
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		return false;
	}
}