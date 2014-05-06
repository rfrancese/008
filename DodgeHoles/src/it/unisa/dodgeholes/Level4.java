package it.unisa.dodgeholes;

import java.util.ArrayList;

public class Level4 implements Level {
	
	private ArrayList<Hole> holes;
	private ArrayList<ObstacleH> obstaclesH;
	private ArrayList<ObstacleV> obstaclesV;
	private ArrayList<Life> lifes;
	private Ball ball;
	private EndHole endHole;
	
	
	public Level4()
	{
		ball=new Ball(8.5f,4.1f);
		Ball.ballPosXInit=8.5f;
		Ball.ballPosYInit=4.1f;
		holes=generateHoles();
		obstaclesH=generateObstaclesH();
		obstaclesV=generateObstaclesV();
		lifes=generateLifes();
		endHole=new EndHole(13f,5.6f);
	}
	
	private ArrayList<Hole> generateHoles()
	{
		ArrayList<Hole> h=new ArrayList<Hole>();
		Hole h1=new Hole(14.1f,5.6f);
		Hole h2=new Hole(14.1f,4f);
		Hole h3=new Hole(10.4f,0.95f);
		Hole h4=new Hole(9f,2.3f);
		Hole h5=new Hole(10.65f,3.3f);
		Hole h6=new Hole(6f,0.95f);
		Hole h7=new Hole(1.15f,0.95f);
		Hole h8=new Hole(3.45f,0.95f);
		Hole h9=new Hole(3.45f,2.95f);
		Hole h10=new Hole(2.45f,3.95f);
		Hole h11=new Hole(2f,1.95f);
		Hole h12=new Hole(3.6f,5.6f);
		Hole h13=new Hole(3.6f,6.6f);
		Hole h14=new Hole(3.6f,8.4f);
		Hole h15=new Hole(1.15f,7.5f);
		Hole h16=new Hole(5.6f,6.6f);
		Hole h17=new Hole(6.2f,8.4f);
		Hole h18=new Hole(11.9f,5.6f);
		Hole h19=new Hole(14.1f,6.6f);
		Hole h20=new Hole(14.1f,8.4f);
		Hole h21=new Hole(11.9f,7.6f);
		
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
		h.add(h16);
		h.add(h17);
		h.add(h18);
		h.add(h19);
		h.add(h20);
		h.add(h21);
		
		return h;
	}
	
	private ArrayList<ObstacleH> generateObstaclesH()
	{
		ArrayList<ObstacleH> o=new ArrayList<ObstacleH>();
		ObstacleH o1=new ObstacleH(8.85f,4.8f);
		ObstacleH o2=new ObstacleH(10.95f,4.8f);
		ObstacleH o3=new ObstacleH(13.05f,4.8f);
		ObstacleH o4=new ObstacleH(8.85f,3.3f);
		ObstacleH o5=new ObstacleH(6.15f,4.8f);
		ObstacleH o6=new ObstacleH(4.05f,4.8f);
		
		o.add(o1);
		o.add(o2);
		o.add(o3);
		o.add(o4);
		o.add(o5);
		o.add(o6);
		
		return o;
	}
	
	private ArrayList<ObstacleV> generateObstaclesV()
	{
		ArrayList<ObstacleV> o=new ArrayList<ObstacleV>();
		ObstacleV o1=new ObstacleV(7.5f,6.8f);
		ObstacleV o2=new ObstacleV(7.5f,4.7f);
		ObstacleV o3=new ObstacleV(7.5f,2.6f);
		
		o.add(o1);
		o.add(o2);
		o.add(o3);
		
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
