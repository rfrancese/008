package it.unisa.dodgeholes;

import it.unisa.dodgeholes.framework.math.Circle;
import it.unisa.dodgeholes.framework.math.OverlapTester;
import it.unisa.dodgeholes.framework.math.Rectangle;
import it.unisa.dodgeholes.framework.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.util.Log;


public class World {
	public interface WorldListener {
        public void hitHole();
        public void hitEndHole();
    }
    

    public static final float WORLD_WIDTH = 15;
    public static final float WORLD_HEIGHT = 10;
    public static final int WORLD_STATE_RUNNING = 0;
    public static final int WORLD_STATE_NEXT_LEVEL = 1;
    public static final int WORLD_STATE_GAME_OVER = 2;
    public static final Vector2 gravity = new Vector2(0, -12);

    public final Ball ball;
    public final ArrayList<Hole> holes;
    public final ArrayList<ObstacleH> obstaclesH;
    public final ArrayList<ObstacleV> obstaclesV;
    public final ArrayList<Life> lifes;
    public final EndHole endHole;
    public final WorldListener listener;
    
    private Level level;
    
    public int state;
    
    private int numLevel;
    
   

    public World(WorldListener listener, int numLevel) {
    	
        this.numLevel=numLevel;
        level=getCurrentLevel();
        this.ball=level.getBall();
        this.holes=level.getHoles();
        this.obstaclesH=level.getObstaclesH();
        this.obstaclesV=level.getObstaclesV();
        this.endHole=level.getEndHole();
        this.lifes=level.getLifes();
        
      
        this.listener = listener;
        
        this.state = WORLD_STATE_RUNNING;
    }
    
    private Level getCurrentLevel()
    {
    	Level l=null;
    	switch(numLevel)
    	{
    		case 1:
    			l=new Level1();
    			break;
    		case 2:
    			l=new Level2();
    			break;
    		case 3:
    			l=new Level3();
    			break;
    		case 4:
    			l=new Level4();
    			break;
    		case 5:
    			l=new Level5();
    			break;
    		/*default:
    			l=new Level1();*/
    	}
    	
    	return l;
    }
    
    public int getNumLevel()
    {
    	return this.numLevel;
    }

	

public void update(float deltaTime, float accelX, float accelY) {
    updateBall(deltaTime, -accelY,accelX);
    
    checkObstacleHCollisions();
    checkObstacleVCollisions();
    checkHolesCollisions();
    checkEndHoleCollisions();
    
}

private void updateBall(float deltaTime, float accelX, float accelY) {
  
	
    if (ball.state == Ball.BALL_STATE_MOVING)
    {
		ball.velocity.x = -accelX /7*Ball.BALL_MOVE_VELOCITY;
        ball.velocity.y=-accelY/5*Ball.BALL_MOVE_VELOCITY;
    }
    else
    {
    	//Controllo velocita' in modo che si mantenga sotto una certa soglia 
    	
    	if((-accelX /7*Ball.BALL_MOVE_VELOCITY)<-7)
    		ball.velocity.x=-7;
    	else
    	{
    		if((-accelX /7*Ball.BALL_MOVE_VELOCITY)>7)
    			ball.velocity.x=7;
    		else
    			ball.velocity.x=-accelX /7*Ball.BALL_MOVE_VELOCITY;
    	}
    	
    	if((-accelY/5*Ball.BALL_MOVE_VELOCITY)<-7)
    		ball.velocity.y=-7;
    	else
    	{
    		if((-accelY/5*Ball.BALL_MOVE_VELOCITY)>7)
    			ball.velocity.y=7;
    		else
    			ball.velocity.y=(-accelY/5*Ball.BALL_MOVE_VELOCITY);
    	}
    }
    
    ball.update(deltaTime);
 
}


private void checkObstacleHCollisions() 
{
	
	for (int i=0;i<obstaclesH.size();i++)
	{
		if (OverlapTester.overlapRectangles(obstaclesH.get(i).bounds,ball.bounds)) 
		{
			//Log.d("coll", "collisione");
			
			if(ball.position.y>obstaclesH.get(i).position.y)
			{
				ball.hitObstacleH();
				ball.position.y=obstaclesH.get(i).position.y+0.64f;
				break;
			}
			
			if(ball.position.y<obstaclesH.get(i).position.y)
			{
				ball.hitObstacleH();
				ball.position.y=obstaclesH.get(i).position.y-0.64f;
				break;
			}
		}
		else
		{
			if(ball.state==ball.BALL_STATE_HIT_OBST_H)
				ball.state=ball.BALL_STATE_MOVING;
		}
	}
}

private void checkObstacleVCollisions() 
{
	//Circle cb=new Circle(ball.position.x,ball.position.y,0.4f);
	for (int i=0;i<obstaclesV.size();i++)
	{
		if (OverlapTester.overlapRectangles(obstaclesV.get(i).bounds,ball.bounds))
		//if(OverlapTester.overlapCircleRectangle(cb, obstaclesV.get(i).bounds))
		{
			//Log.d("coll", "collisioneV");
			
			if(ball.position.x>obstaclesV.get(i).position.x)
			{
				ball.hitObstacleV();
				ball.position.x=obstaclesV.get(i).position.x+0.64f;
				break;
				
			}
				
			if(ball.position.x<obstaclesV.get(i).position.x)
			{
				ball.hitObstacleV();
				ball.position.x=obstaclesV.get(i).position.x-0.64f;
				break;
			}
		}
		else
		{
			if(ball.state==ball.BALL_STATE_HIT_OBST_V)
				ball.state=ball.BALL_STATE_MOVING;
		}
	}
}

private void checkHolesCollisions()
{
	for(int i=0;i<holes.size();i++)
	{
		if (OverlapTester.overlapRectangles(holes.get(i).bounds,ball.bounds)) 
		{
			Circle c=new Circle(holes.get(i).position.x,holes.get(i).position.y,0.05f);
			if(OverlapTester.overlapCircleRectangle(c, ball.bounds))
			{
				listener.hitHole();
				ball.position.x=Ball.ballPosXInit;
				ball.position.y=Ball.ballPosYInit;
				if (this.lifes.size()>1)
					this.lifes.remove(lifes.size()-1);
				else
				{
					this.lifes.remove(lifes.size()-1);
					this.ball.position.x=-1f;
					this.ball.position.y=-1f;
					this.state=WORLD_STATE_GAME_OVER;
				}
				break;
			}
		}
	}
}

private void checkEndHoleCollisions()
{
	if (OverlapTester.overlapRectangles(endHole.bounds,ball.bounds)) 
	{
		Circle c=new Circle(endHole.position.x,endHole.position.y,0.05f);
		if(OverlapTester.overlapCircleRectangle(c, ball.bounds))
		{
			listener.hitEndHole();
			this.ball.position.x=-1f;
			this.ball.position.y=-1f;
			this.state=WORLD_STATE_NEXT_LEVEL;
		}
	}
}


}