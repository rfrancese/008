package it.unisa.dodgeholes;

import java.util.ArrayList;

public class Level1 implements Level {
	
	private ArrayList<Hole> holes;
	private ArrayList<ObstacleH> obstacles;
	private ArrayList<Life> lifes;
	private Ball ball;
	private EndHole endHole;
	
	
	public Level1()
	{
		ball=new Ball(0.95f, 10-1.5f);
		holes=generateHoles();
		obstacles=generateObstacles();
		lifes=generateLifes();
		endHole=new EndHole(13.9f,2f);
	}
	
	private ArrayList<Hole> generateHoles()
	{
		ArrayList<Hole> h=new ArrayList<Hole>();
		Hole h1=new Hole(3.6f,7.4f);
		Hole h2=new Hole(5.1f,6.5f);
		Hole h3=new Hole(8f,5.1f);
		Hole h4=new Hole(13.5f,3.2f);
		
		h.add(h1);
		h.add(h2);
		h.add(h3);
		h.add(h4);
		return h;
	}
	
	private ArrayList<ObstacleH> generateObstacles()
	{
		ArrayList<ObstacleH> o=new ArrayList<ObstacleH>();
		ObstacleH o1=new ObstacleH(1.4f,7.5f);
		ObstacleH o2=new ObstacleH(2.4f,5f);
		ObstacleH o3=new ObstacleH(5.2f,5.3f);
		ObstacleH o4=new ObstacleH(10.1f,4.1f);
		
		o.add(o1);
		o.add(o2);
		o.add(o3);
		o.add(o4);
		
		return o;
	}
	
	private ArrayList<Life> generateLifes()
	{
		ArrayList<Life> l=new ArrayList<Life>();
    	Life l1=new Life(3f,10-0.50f);
    	Life l2=new Life(4.1f,10-0.50f);
    	Life l3=new Life(5.2f,10-0.50f);
    	l.add(l1);
    	l.add(l2);
    	l.add(l3);
    	return l;
	}
	
	public Ball getBall()
	{
		return this.ball;
	}
	
	public ArrayList<Hole> getHoles()
	{
		return this.holes;
	}
	
	public ArrayList<ObstacleH> getObstacles()
	{
		return this.obstacles;
	}
	
	public ArrayList<Life> getLifes()
	{
		return this.lifes;
	}

	public EndHole getEndHole() 
	{
		return this.endHole;
	}
	

}
