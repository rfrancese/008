package it.unisa.dodgeholes;

import java.util.ArrayList;

public class Level6 implements Level {
	
	private ArrayList<Hole> holes;
	private ArrayList<ObstacleH> obstaclesH;
	private ArrayList<ObstacleV> obstaclesV;
	private ArrayList<Life> lifes;
	private Ball ball;
	private EndHole endHole;
	
	
	public Level6()
	{
		ball=new Ball(1.15f,7.7f);
		Ball.ballPosXInit=1.15f;
		Ball.ballPosYInit=7.7f;
		holes=generateHoles();
		obstaclesH=generateObstaclesH();
		obstaclesV=generateObstaclesV();
		lifes=generateLifes();
		endHole=new EndHole(6.8f,8.4f);
	}
	
	private ArrayList<Hole> generateHoles()
	{
		ArrayList<Hole> h=new ArrayList<Hole>();
		Hole h1=new Hole(3.20f,8.4f);
		Hole h2=new Hole(1.15f,0.95f);
		Hole h3=new Hole(3f,1.75f);
		Hole h4=new Hole(4f,1.75f);
		Hole h5=new Hole(5f,1.75f);
		Hole h6=new Hole(3.20f,6.6f);
		Hole h7=new Hole(4.5f,7.6f);
		Hole h8=new Hole(7.50f,3.35f);
		Hole h9=new Hole(9.30f,3.35f);
		Hole h10=new Hole(3.20f,4.8f);
		Hole h11=new Hole(2.30f,4.2f);
		Hole h12=new Hole(8.45f,4.70f);
		Hole h13=new Hole(4.8f,4.70f);
		Hole h14=new Hole(4.8f,6.6f);
		Hole h15=new Hole(5.8f,4.2f);
		Hole h16=new Hole(1.15f,4.2f);
		Hole h17=new Hole(10f,2.3f);
		Hole h18=new Hole(11f,0.95f);
		Hole h19=new Hole(10.3f,6.55f);
		Hole h20=new Hole(14.1f,2.3f);
		Hole h21=new Hole(11.8f,7.55f);
		Hole h22=new Hole(13.1f,2.3f);
		Hole h23=new Hole(12.1f,2.3f);
		
		Hole h24=new Hole(10.3f,5.35f);
		Hole h25=new Hole(11.3f,5.35f);
		Hole h26=new Hole(13.1f,5.35f);
		Hole h27=new Hole(14.1f,5.35f);
		Hole h28=new Hole(7.8f,7.4f);
		Hole h29=new Hole(9.2f,8.4f);
		Hole h30=new Hole(12.5f,4f);
		Hole h31=new Hole(11f,3.55f);
		
	
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
		h.add(h22);
		h.add(h23);
		h.add(h24);
		h.add(h25);
		h.add(h26);
		h.add(h27);
		h.add(h28);
		h.add(h29);
		h.add(h30);
		h.add(h31);
		
		return h;
	}
	
	private ArrayList<ObstacleH> generateObstaclesH()
	{
		ArrayList<ObstacleH> o=new ArrayList<ObstacleH>();
		ObstacleH o1=new ObstacleH(1.65f,3.35f);
		ObstacleH o2=new ObstacleH(3.75f,3.35f);
		ObstacleH o3=new ObstacleH(5.85f,3.35f);
		ObstacleH o4=new ObstacleH(6.6f,1.75f);
		ObstacleH o5=new ObstacleH(8.7f,1.75f);
		ObstacleH o6=new ObstacleH(1.65f,6.9f);
		ObstacleH o7=new ObstacleH(6.65f,6.55f);
		ObstacleH o8=new ObstacleH(8.75f,6.55f);
		ObstacleH o9=new ObstacleH(13.45f,6.55f);
		
		
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
		ObstacleV o1=new ObstacleV(5.9f,7.85f);
		ObstacleV o2=new ObstacleV(9.5f,5.25f);
		
		o.add(o1);
		o.add(o2);
		
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