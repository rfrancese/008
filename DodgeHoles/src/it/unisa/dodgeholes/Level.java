package it.unisa.dodgeholes;

import java.util.ArrayList;

public interface Level {

	public Ball getBall();
	
	public ArrayList<Hole> getHoles();

	public ArrayList<ObstacleH> getObstacles();

	public ArrayList<Life> getLifes();
	
	public EndHole getEndHole();

}