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
		ball=new Ball(4.4f,3.7f);
		Ball.ballPosXInit=4.4f;
		Ball.ballPosYInit=3.7f;
		holes=generateHoles();
		obstaclesH=generateObstaclesH();
		obstaclesV=generateObstaclesV();
		lifes=generateLifes();
		endHole=new EndHole(9.55f,3.7f);
	}
	
	private ArrayList<Hole> generateHoles()
	{
		ArrayList<Hole> h=new ArrayList<Hole>();
		Hole h1=new Hole(9.55f,5f);
		Hole h2=new Hole(5.355f,5f);
		Hole h3=new Hole(1.8f,2.95f);
		Hole h4=new Hole(1.8f,7.7f);
		Hole h5=new Hole(13.15f,2.95f);
		Hole h6=new Hole(13.15f,7.7f);
		Hole h7=new Hole(6.85f,0.95f);
		Hole h8=new Hole(8.15f,0.95f);
		Hole h9=new Hole(3.3f,7f);
		Hole h10=new Hole(11.65f,7f);
		Hole h11=new Hole(11.65f,3.8f);
		Hole h12=new Hole(8.25f,7f);
		Hole h13=new Hole(14.1f,5f);
		Hole h14=new Hole(14.1f,0.95f);
		Hole h15=new Hole(10.7f,1.5f);
		Hole h16=new Hole(5.2f,2.2f);
		Hole h17=new Hole(5.9f,3.9f);
		
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
		h.add(h12);
		h.add(h13);
		h.add(h14);
		h.add(h15);
		h.add(h16);
		h.add(h17);
		
		return h;
	}
	
	private ArrayList<ObstacleH> generateObstaclesH()
	{
		ArrayList<ObstacleH> o=new ArrayList<ObstacleH>();
		ObstacleH o1=new ObstacleH(3.25f,7.7f);
		ObstacleH o2=new ObstacleH(5.35f,7.7f);
		ObstacleH o3=new ObstacleH(7.45f,7.7f);
		ObstacleH o4=new ObstacleH(9.55f,7.7f);
		ObstacleH o5=new ObstacleH(11.65f,7.7f);//4
		
		ObstacleH o6=new ObstacleH(11.65f,2.95f);
		ObstacleH o7=new ObstacleH(9.55f,2.95f);
		ObstacleH o8=new ObstacleH(3.25f,2.95f);
		ObstacleH o9=new ObstacleH(5.35f,2.95f);
		
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
	
	private ArrayList<ObstacleV> generateObstaclesV()
	{
		ArrayList<ObstacleV> o=new ArrayList<ObstacleV>();
		ObstacleV o1=new ObstacleV(2.5f,6.4f);
		
		ObstacleV o2=new ObstacleV(12.35f,6.4f);
		ObstacleV o3=new ObstacleV(12.35f,4.3f);
		ObstacleV o4=new ObstacleV(8.8f,4.3f);
		ObstacleV o5=new ObstacleV(2.5f,4.3f);
		//ObstacleV o6=new ObstacleV(6.1f,4.3f);
		ObstacleV o7=new ObstacleV(7.45f,6.4f);
		ObstacleV o8=new ObstacleV(7.45f,4.3f);
		ObstacleV o9=new ObstacleV(7.45f,2.2f);
		
		o.add(o1);
		o.add(o2);
		o.add(o3);
		o.add(o4);
		o.add(o5);
		//o.add(o6);
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
