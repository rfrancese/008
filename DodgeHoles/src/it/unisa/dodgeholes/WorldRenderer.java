package it.unisa.dodgeholes;

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
        batcher.beginBatch(Assets.background);
        batcher.drawSprite(cam.position.x, cam.position.y,
                           FRUSTUM_WIDTH, FRUSTUM_HEIGHT, 
                           Assets.backgroundRegion);
        batcher.endBatch();
    }
    
    public void renderObjects() {
        GL10 gl = glGraphics.getGL();
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        
        batcher.beginBatch(Assets.myitem);
        renderHole();
        renderBob();
        renderObstacle();
        
       
        batcher.endBatch();
        gl.glDisable(GL10.GL_BLEND);
    }
    
    private void renderBob() {
        
        
        float side = world.ball.velocity.x < 0? -1: 1;        
        
        batcher.drawSprite(world.ball.position.x, world.ball.position.y, 0.8f, 0.8f, Assets.ball);  
        
    }
    private void renderHole() {
        Hole hole =world.hole;
        batcher.drawSprite(hole.position.x, hole.position.y, 1f, 1f, Assets.hole);
    }
    private void renderObstacle() 
    {
        Obstacle obstacle =world.obstacle;
        batcher.drawSprite(obstacle.position.x, obstacle.position.y, 2f, 0.5f, Assets.obstacle);
    }
    
   
}
