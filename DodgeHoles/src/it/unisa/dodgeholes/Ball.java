package it.unisa.dodgeholes;

import it.unisa.dodgeholes.framework.DynamicGameObject;

public class Ball extends DynamicGameObject{
    public static final int BALL_STATE_MOVING=0;
    public static final int BALL_STATE_HIT_OBST_H =1;
    public static final int BALL_STATE_HIT_OBST_V =2;
    
    public static final float BALL_MOVE_VELOCITY = 20;
    public static final float BALL_WIDTH = 0.8f;
    public static final float BALL_HEIGHT = 0.8f;
    
    public static float ballPosXInit;
    public static float ballPosYInit;
    
    int state;
    float stateTime;    

    public Ball(float x, float y) {
        super(x, y, BALL_WIDTH, BALL_HEIGHT);
        state = BALL_STATE_MOVING;
        ballPosXInit=0f;
        ballPosYInit=0f;
        stateTime = 0;        
    }

    public void update(float deltaTime) {     
        velocity.add(World.gravity.x * deltaTime, World.gravity.y * deltaTime);
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);
       
        
       
        
        if(position.x < 0.97f)
        {
            position.x =0.97f;
        }
        if(position.y < 0.87f)
            position.y =0.87f;
        if(position.x > World.WORLD_WIDTH-0.87f)
            position.x = World.WORLD_WIDTH-0.87f;
        if(position.y > World.WORLD_HEIGHT-1.5f)
            position.y = World.WORLD_HEIGHT-1.5f;
        
        
        stateTime += deltaTime;
    }
    
   public void hitObstacleH()
   {
	  
	   state=BALL_STATE_HIT_OBST_H;
	   stateTime=0;
   }
   
   public void hitObstacleV()
   {
	  
	   state=BALL_STATE_HIT_OBST_V;
	   stateTime=0;
   }
    
   
}
