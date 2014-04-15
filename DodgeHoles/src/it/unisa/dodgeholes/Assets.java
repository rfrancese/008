package it.unisa.dodgeholes;

import it.unisa.dodgeholes.framework.Music;
import it.unisa.dodgeholes.framework.Sound;
import it.unisa.dodgeholes.framework.gl.Animation;
import it.unisa.dodgeholes.framework.gl.Font;
import it.unisa.dodgeholes.framework.gl.Texture;
import it.unisa.dodgeholes.framework.gl.TextureRegion;
import it.unisa.dodgeholes.framework.impl.GLGame;


public class Assets {
    public static Texture background;
    public static TextureRegion backgroundRegion;
    
    
    public static Texture myitem; 
    
    public static TextureRegion play;
   
    public static TextureRegion resume;
    public static TextureRegion quit;
    public static TextureRegion ready;
    
    public static TextureRegion logo;
    public static TextureRegion soundOn;
    public static TextureRegion soundOff;
    
    public static TextureRegion pause;   
   
    public static TextureRegion ball;
    public static TextureRegion hole;
    public static TextureRegion endHole;
    public static TextureRegion obstacle;
    
    
    public static Music music;
    
    
    public static void load(GLGame game) {
        background = new Texture(game, "background.png");
        backgroundRegion = new TextureRegion(background, 0, 0, 480, 320);
        myitem=new Texture(game, "myitem.png");
        
        
        
       
        play=new TextureRegion(myitem,109,32,109,38);
        
        resume=new TextureRegion(myitem, 329, 0, 110, 27);
        quit=new TextureRegion(myitem, 160, 0, 61, 27);
        
        ready = new TextureRegion(myitem, 221, 0, 108, 32);//
        
        logo = new TextureRegion(myitem, 0, 108, 300, 135);//
        soundOff = new TextureRegion(myitem, 300, 172, 64, 64);//
        soundOn = new TextureRegion(myitem, 364, 108, 64, 64);//
       
        pause = new TextureRegion(myitem, 300, 108, 64, 64);//
        
       
        ball = new TextureRegion(myitem, 0, 0, 32, 32);
        hole = new TextureRegion(myitem, 64, 0, 32, 32);
        endHole = new TextureRegion(myitem, 32, 0, 32, 32);
        obstacle=new TextureRegion(myitem, 96, 0, 64, 16);
        
        
        
        music = game.getAudio().newMusic("music.mp3");
        music.setLooping(true);
        music.setVolume(0.5f);
        if(Settings.soundEnabled)
            music.play();
             
    }       
    
    public static void reload() {
        background.reload();
        
        myitem.reload();
        if(Settings.soundEnabled)
            music.play();
    }
    
    public static void playSound(Sound sound) {
        if(Settings.soundEnabled)
            sound.play(1);
    }
}
