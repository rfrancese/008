package it.unisa.dodgeholes;

import it.unisa.dodgeholes.framework.GameObject;

public class Obstacle extends GameObject {
    public static float OBSTACLE_WIDTH = 2f;
    public static float OBSTACLE_HEIGHT = 0.5f;
    

    public Obstacle(float x, float y) {
        super(x, y, OBSTACLE_WIDTH, OBSTACLE_HEIGHT);
        
    }
    

}

