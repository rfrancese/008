package it.unisa.dodgeholes;

import java.util.ArrayList;

import it.unisa.dodgeholes.framework.gl.Animation;
import it.unisa.dodgeholes.framework.gl.Camera2D;
import it.unisa.dodgeholes.framework.gl.SpriteBatcher;
import it.unisa.dodgeholes.framework.gl.TextureRegion;
import it.unisa.dodgeholes.framework.impl.GLGraphics;

import javax.microedition.khronos.opengles.GL10;


public class WorldRenderer {
    static final float FRUSTUM_WIDTH = 15;
    static final float FRUSTUM_HEIGHT = 10;    
    GLGraphics glGraphics;
    World world;
    Camera2D cam;
    SpriteBatcher batcher;    
    
    public WorldRenderer(GLGraphics glGraphics, SpriteBatcher batcher, World world) {
        this.glGraphics = glGraphics;
        this.world = world;
        this.cam = new Camera2D(glGraphics, FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        this.batcher = batcher;        
    }
    
    public void render() {
        
        cam.setViewportAndMatrices();
        renderBackground();
        renderObjects();        
    }
    
    public void renderBackground() {
        batcher.beginBatch(Assets.backgroundGame);
        batcher.drawSprite(cam.position.x, cam.position.y,
                           FRUSTUM_WIDTH, FRUSTUM_HEIGHT, 
                           Assets.backgroundGameRegion);
        batcher.endBatch();
    }
    
    public void renderObjects() {
        GL10 gl = glGraphics.getGL();
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        
        batcher.beginBatch(Assets.myitem);
        renderHole();
        renderEndHole();
        renderBall();
        renderObstacleH();
        renderObstacleV();
        renderLifes();
       
        batcher.endBatch();
        gl.glDisable(GL10.GL_BLEND);
    }
    
    private void renderBall() {
        
        
        float side = world.ball.velocity.x < 0? -1: 1;        
        
        batcher.drawSprite(world.ball.position.x, world.ball.position.y, 0.8f, 0.8f, Assets.ball);  
        
    }
    private void renderHole() {
        ArrayList<Hole> holes =world.holes;
        for (int i=0;i<holes.size();i++)
        {
        	batcher.drawSprite(holes.get(i).position.x, holes.get(i).position.y, 1f, 1f, Assets.hole);
        }
    }
    private void renderObstacleH() 
    {
        ArrayList<ObstacleH> obstacles =world.obstaclesH;
        for(int i=0;i<obstacles.size();i++)
        {
        	batcher.drawSprite(obstacles.get(i).position.x, obstacles.get(i).position.y, 2f, 0.5f, Assets.obstacleH);
        }
    }
    
    private void renderObstacleV() 
    {
        ArrayList<ObstacleV> obstaclesV =world.obstaclesV;
        for(int i=0;i<obstaclesV.size();i++)
        {
        	batcher.drawSprite(obstaclesV.get(i).position.x, obstaclesV.get(i).position.y, 0.5f, 2f, Assets.obstacleV);
        }
    }
    
    private void renderEndHole()
    {
    	EndHole endHole=world.endHole;
    	batcher.drawSprite(endHole.position.x, endHole.position.y, 1f, 1f, Assets.endHole);
    	
    }
    private void renderLifes()
    {
    	ArrayList<Life> lifes=world.lifes;
    	for(int i=0;i<lifes.size();i++)
    	{
    		batcher.drawSprite(lifes.get(i).position.x, lifes.get(i).position.y, 0.8f, 0.8f, Assets.life);
    	}
    }
    
   
}
