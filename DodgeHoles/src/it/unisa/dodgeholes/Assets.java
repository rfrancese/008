package it.unisa.dodgeholes;

import android.database.sqlite.SQLiteDatabase;
import it.unisa.dodgeholes.framework.Music;
import it.unisa.dodgeholes.framework.Sound;
import it.unisa.dodgeholes.framework.gl.Animation;
import it.unisa.dodgeholes.framework.gl.Font;
import it.unisa.dodgeholes.framework.gl.Texture;
import it.unisa.dodgeholes.framework.gl.TextureRegion;
import it.unisa.dodgeholes.framework.impl.GLGame;


public class Assets {
	
	public static Texture backgroundMenu;
    public static TextureRegion backgroundMenuRegion;
    public static Texture backgroundGame;
    public static TextureRegion backgroundGameRegion;
    
    
    public static Texture myitem;
    
    public static TextureRegion life;
    public static TextureRegion lifeWrite;
    public static TextureRegion timeWrite;
    
    public static TextureRegion play;
    public static TextureRegion level;
    public static TextureRegion scores;
    public static TextureRegion setting;
    public static TextureRegion register;
   
    public static TextureRegion resume;
    public static TextureRegion quit;
    public static TextureRegion ready;
    public static TextureRegion nextLevel;
    public static TextureRegion tryAgain;
    
    public static TextureRegion logo;
    
    public static TextureRegion pause;   
   
    public static TextureRegion ball;
    public static TextureRegion hole;
    public static TextureRegion endHole;
    public static TextureRegion obstacleH;
    public static TextureRegion obstacleV;
    
    public static TextureRegion gameOver;
    public static TextureRegion win;
    
    
    public static Music music;
    public static Sound hitHoleSound;
    public static Sound hitEndSound;
    
    public static Font font;
    
    public static boolean musicActive;
    public static boolean soundActive;
    
    private static ComodoSettings settaggi;
    
    public static void load(GLGame game)
    {
    	//Leggi dal database e imposta il valore a musicActive e soundActive
    	settaggi=new ComodoSettings();
    	
    	musicActive=ComodoSettings.getAudio(new DbLocale(settaggi));;
    	soundActive=ComodoSettings.getSound(new DbLocale(settaggi));;
    	
        backgroundMenu = new Texture(game, "backgroundMenu.png");
        backgroundMenuRegion = new TextureRegion(backgroundMenu, 0, 0, 480, 320);
        backgroundGame = new Texture(game, "backgroundGame.png");
        backgroundGameRegion = new TextureRegion(backgroundGame, 0, 0, 480, 320);
        myitem=new Texture(game, "myitem.png");
        
        
        
        life=new TextureRegion(myitem,0,0,32,32);
        lifeWrite=new TextureRegion(myitem,160,0,53,25);
        timeWrite=new TextureRegion(myitem,213,0,71,25);
        
        play=new TextureRegion(myitem,125,32,109,38);
        scores=new TextureRegion(myitem,343,32,109,38);
        level=new TextureRegion(myitem,17,32,109,38);
        setting=new TextureRegion(myitem,17,70,109,38);
        register=new TextureRegion(myitem,234,32,109,38);
        
        resume=new TextureRegion(myitem, 312, 146, 109, 38);
        quit=new TextureRegion(myitem, 312, 108, 109, 38);
        nextLevel=new TextureRegion(myitem, 381, 70, 109, 38);
        tryAgain=new TextureRegion(myitem, 312, 184, 109, 38);
        
        ready = new TextureRegion(myitem, 16, 371, 208, 38);//
        
        logo = new TextureRegion(myitem, 16, 236, 300, 135);//
        
        pause = new TextureRegion(myitem, 272, 108, 40, 40);//
        
       
        ball = new TextureRegion(myitem, 0, 0, 32, 32);
        hole = new TextureRegion(myitem, 128, 0, 32, 32);
        endHole = new TextureRegion(myitem, 96, 0, 32, 32);
        obstacleH=new TextureRegion(myitem, 32, 0, 64, 16);
        obstacleV=new TextureRegion(myitem, 0, 32, 16, 64);
        
        gameOver=new TextureRegion(myitem, 125, 70, 256, 38);
        win=new TextureRegion(myitem, 224, 371, 92, 38);
        
        
        font = new Font(myitem, 16, 108, 16, 16, 20);
        
        music = game.getAudio().newMusic("music.mp3");
        music.setLooping(true);
        music.setVolume(0.5f);
        //if(Save.soundEnabled)
        if(musicActive)
            music.play();
        
        hitHoleSound = game.getAudio().newSound("ballHoles.mp3");
        hitEndSound = game.getAudio().newSound("ballEndHole.mp3");
        
             
    }       
    
    public static void reload()
    {
        backgroundMenu.reload();
        backgroundGame.reload();
        
        myitem.reload();
        //if(Save.soundEnabled)
        if(musicActive)
            music.play();
    }
    
    public static void playSound(Sound sound)
    {
        //if(Save.soundEnabled)
    	if(soundActive)
            sound.play(1);
    	
    }
}
