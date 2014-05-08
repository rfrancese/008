package it.unisa.dodgeholes;

import java.util.ArrayList;

public class Level5 implements Level {
	
	private ArrayList<Hole> holes;
	private ArrayList<ObstacleH> obstaclesH;
	private ArrayList<ObstacleV> obstaclesV;
	private ArrayList<Life> lifes;
	private Ball ball;
	private EndHole endHole;
	
	
	public Level5()
	{
		ball=new Ball(13.9f,1.45f);
		Ball.ballPosXInit=13.9f;
		Ball.ballPosYInit=1.45f;
		holes=generateHoles();
		obstaclesH=generateObstaclesH();
		obstaclesV=generateObstaclesV();
		lifes=generateLifes();
		endHole=new EndHole(1.10f,8.4f);
	}
	
	private ArrayList<Hole> generateHoles()
	{
		ArrayList<Hole> h=new ArrayList<Hole>();
		Hole h1=new Hole(3.20f,8.4f);
		Hole h2=new Hole(3.20f,6.6f);
		Hole h3=new Hole(4.3f,5f);
		Hole h4=new Hole(4.3f,4f);
		Hole h5=new Hole(1.89f,3f);
		Hole h6=new Hole(1.15f,0.95f);
		Hole h7=new Hole(6.75f,2.5f);
		Hole h8=new Hole(8.45f,0.95f);
		Hole h9=new Hole(6.75f,8.4f);
		Hole h10=new Hole(6.75f,5f);
		Hole h11=new Hole(8.55f,5f);
		Hole h12=new Hole(8.45f,6.8f);
		Hole h13=new Hole(10.75f,3f);
		Hole h14=new Hole(10.75f,4f);
		Hole h15=new Hole(10.75f,5f);
		Hole h16=new Hole(11.85f,4.5f);
		Hole h17=new Hole(11.95f,6.3f);
		Hole h18=new Hole(14.05f,5f);
		Hole h19=new Hole(14.05f,8.4f);
		Hole h20=new Hole(4.2f,7.1f);
		Hole h21=new Hole(1.15f,7.35f);
		
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
		ObstacleH o1=new ObstacleH(1.65f,5.75f);
		ObstacleH o2=new ObstacleH(3.75f,5.75f);
		ObstacleH o3=new ObstacleH(3.05f,2.25f);
		ObstacleH o4=new ObstacleH(5.15f,2.25f);
		ObstacleH o5=new ObstacleH(13.5f,2.25f);
		ObstacleH o6=new ObstacleH(11.4f,2.25f);
		ObstacleH o7=new ObstacleH(10f,7.1f);
		ObstacleH o8=new ObstacleH(12.1f,7.1f);

		o.add(o1);
		o.add(o2);
		o.add(o3);
		o.add(o4);
		o.add(o5);
		o.add(o6);
		o.add(o7);
		o.add(o8);

		return o;
	}
	
	private ArrayList<ObstacleV> generateObstaclesV()
	{
		ArrayList<ObstacleV> o=new ArrayList<ObstacleV>();
		ObstacleV o1=new ObstacleV(5.9f,3.65f);
		ObstacleV o2=new ObstacleV(5.9f,5.75f);
		ObstacleV o3=new ObstacleV(5.9f,7.85f);
		ObstacleV o4=new ObstacleV(9.25f,1.55f);
		ObstacleV o5=new ObstacleV(9.25f,3.65f);
		ObstacleV o6=new ObstacleV(9.25f,5.75f);
		
		o.add(o1);
		o.add(o2);
		o.add(o3);
		o.add(o3);
		o.add(o4);
		o.add(o5);
		o.add(o6);
		
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
