package it.unisa.dodgeholes;

import java.util.ArrayList;

public class Level1 implements Level {
	
	private ArrayList<Hole> holes;
	private ArrayList<ObstacleH> obstaclesH;
	private ArrayList<ObstacleV> obstaclesV;
	private ArrayList<Life> lifes;
	private Ball ball;
	private EndHole endHole;
	
	
	public Level1()
	{
		ball=new Ball(0.95f, 10-1.5f);
		Ball.ballPosXInit=0.95f;
		Ball.ballPosYInit=10-1.5f;
		holes=generateHoles();
		obstaclesH=generateObstaclesH();
		obstaclesV=generateObstaclesV();
		lifes=generateLifes();
		endHole=new EndHole(13.9f,8f);
	}
	
	private ArrayList<Hole> generateHoles()
	{
		ArrayList<Hole> h=new ArrayList<Hole>();
		Hole h1=new Hole(2.2f,6f);
		Hole h2=new Hole(3f,1.9f);
		Hole h3=new Hole(7.5f,7.5f);
		Hole h4=new Hole(12f,1.9f);
		Hole h5=new Hole(3.8f,4.3f);
		Hole h6=new Hole(4.8f,4.3f);
		Hole h7=new Hole(6.8f,4.3f);
		Hole h8=new Hole(8.3f,4.3f);
		Hole h9=new Hole(9.3f,4.3f);
		Hole h10=new Hole(11.3f,4.3f);
		Hole h11=new Hole(10.3f,2.5f);
		Hole h12=new Hole(14.1f,0.95f);
		Hole h13=new Hole(14.1f,2.95f);
		Hole h14=new Hole(12.8f,8f);
		Hole h15=new Hole(14.1f,6.6f);
		
		
		h.add(h1);
		h.add(h2);
		h.add(h3);
		h.add(h4);
		h.add(h5);
		h.add(h6);
		h.add(h7);
		h.add(h8);
		h.add(h9);
		h.add(h10);
		h.add(h11);
		h.add(h12);
		h.add(h13);
		h.add(h14);
		h.add(h15);
		
		return h;
	}
	
	private ArrayList<ObstacleH> generateObstaclesH()
	{
		ArrayList<ObstacleH> o=new ArrayList<ObstacleH>();
		
		
		return o;
	}
	
	private ArrayList<ObstacleV> generateObstaclesV()
	{
		ArrayList<ObstacleV> o=new ArrayList<ObstacleV>();
		ObstacleV o1=new ObstacleV(3f,7.9f);
		ObstacleV o2=new ObstacleV(3f,5.8f);
		ObstacleV o3=new ObstacleV(3f,3.7f);
		ObstacleV o4=new ObstacleV(7.5f,1.5f);
		ObstacleV o5=new ObstacleV(7.5f,3.6f);
		ObstacleV o6=new ObstacleV(7.5f,5.7f);
		ObstacleV o7=new ObstacleV(12f,7.9f);
		ObstacleV o8=new ObstacleV(12f,5.8f);
		ObstacleV o9=new ObstacleV(12f,3.7f);
		
		o.add(o1);
		o.add(o2);
		o.add(o3);
		o.add(o4);
		o.add(o5);
		o.add(o6);
		o.add(o7);
		o.add(o8);
		o.add(o9);
		
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
