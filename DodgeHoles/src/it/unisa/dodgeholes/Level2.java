package it.unisa.dodgeholes;

import java.util.ArrayList;

public class Level2 implements Level {
	
	private ArrayList<Hole> holes;
	private ArrayList<ObstacleH> obstaclesH;
	private ArrayList<ObstacleV> obstaclesV;
	private ArrayList<Life> lifes;
	private Ball ball;
	private EndHole endHole;
	
	
	public Level2()
	{
		ball=new Ball(0.95f, 10-1.5f);
		holes=generateHoles();
		obstaclesH=generateObstaclesH();
		obstaclesV=generateObstaclesV();
		lifes=generateLifes();
		endHole=new EndHole(13.9f,2f);
	}
	
	private ArrayList<Hole> generateHoles()
	{
		ArrayList<Hole> h=new ArrayList<Hole>();
		/*Hole h1=new Hole(4.5f,3.6f);
		Hole h2=new Hole(8.2f,6f);
	
		h.add(h1);
		h.add(h2);*/
		
		return h;
	}
	
	private ArrayList<ObstacleH> generateObstaclesH()
	{
		ArrayList<ObstacleH> o=new ArrayList<ObstacleH>();
		ObstacleH o1=new ObstacleH(2.5f,7.7f);
		ObstacleH o2=new ObstacleH(4.6f,7.7f);
		ObstacleH o3=new ObstacleH(6.7f,7.7f);
		ObstacleH o4=new ObstacleH(8.8f,7.7f);
		ObstacleH o5=new ObstacleH(10.9f,7.7f);
		
		o.add(o1);
		o.add(o2);
		o.add(o3);
		o.add(o4);
		o.add(o5);
		
		return o;
	}
	
	private ArrayList<ObstacleV> generateObstaclesV()
	{
		ArrayList<ObstacleV> ov=new ArrayList<ObstacleV>();
		ObstacleV ov1=new ObstacleV(1.70f,6.3f);
		
		ov.add(ov1);
		
		return ov;
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
	
	public ArrayList<ObstacleH> getObstaclesH()
	{
		return this.obstaclesH;
	}
	
	public ArrayList<ObstacleV> getObstaclesV()
	{
		return this.obstaclesV;
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
