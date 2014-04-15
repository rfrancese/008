package it.unisa.dodgeholes;

import it.unisa.dodgeholes.framework.math.OverlapTester;
import it.unisa.dodgeholes.framework.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.util.Log;


public class World {
    

    public static final float WORLD_WIDTH = 15;
    public static final float WORLD_HEIGHT = 10;
    public static final int WORLD_STATE_RUNNING = 0;
    public static final int WORLD_STATE_NEXT_LEVEL = 1;
    public static final int WORLD_STATE_GAME_OVER = 2;
    public static final Vector2 gravity = new Vector2(0, -12);

    public final Ball ball;
    public final Hole hole;
    public final Obstacle obstacle;
    
   
    

    
    public int state;
    public boolean flag;
   

    public World() {
        this.ball = new Ball(0.4f, 10-0.4f);
        this.hole=new Hole(5f,5f);
        this.obstacle=new Obstacle(2f,3f);
        
        
        
        

        
        this.state = WORLD_STATE_RUNNING;
        flag=true;
        
    }

	

public void update(float deltaTime, float accelX, float accelY) {
    updateBob(deltaTime, -accelY,accelX);
    
    checkObstacleCollisions();
    checkHolesCollisions();
    
}

private void updateBob(float deltaTime, float accelX, float accelY) {
  
	
    if (ball.state == Ball.BALL_STATE_MOVING)
    {
		ball.velocity.x = -accelX /7*Ball.BALL_MOVE_VELOCITY;
        ball.velocity.y=-accelY/5*Ball.BALL_MOVE_VELOCITY;
        Log.d("vely", ""+ball.velocity.y);
    }
    else
    {
    	ball.velocity.x = -accelX /7*Ball.BALL_MOVE_VELOCITY;
        ball.velocity.y=(-accelY/5*Ball.BALL_MOVE_VELOCITY)%5;//Mantengo la velocita' sotto una certa soglia per evitare che superi l'ostacolo
    }
    
    ball.update(deltaTime);
    
    
}


private void checkObstacleCollisions() 
{

	if (OverlapTester.overlapRectangles(obstacle.bounds,ball.bounds)) 
	{
		Log.d("coll", "collisione");
		
		if(ball.position.y>obstacle.position.y)
		{
			ball.hitObstacle();
			ball.position.y=obstacle.position.y+0.6f;
			
		}
			
		if(ball.position.y<obstacle.position.y)
		{
			ball.hitObstacle();
			ball.position.y=obstacle.position.y-0.6f;
		}
	}
	else
		ball.state=ball.BALL_STATE_MOVING;
}
private void checkHolesCollisions()
{
	
	if (OverlapTester.overlapRectangles(hole.bounds,ball.bounds)) 
	{
		
		if(ball.position.y<hole.position.y)
			flag=false;
		else
			flag=true;
		if(ball.position.y<hole.position.y+0.5f && flag)
		{
			ball.position.x=0.4f;
			ball.position.y=10-0.4f;
		}
		if(ball.position.y>hole.position.y-0.5f && !flag)
		{
			ball.position.x=0.4f;
			ball.position.y=10-0.4f;
		}
	}
}


}