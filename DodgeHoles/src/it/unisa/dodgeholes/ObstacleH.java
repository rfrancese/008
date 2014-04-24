package it.unisa.dodgeholes;

import it.unisa.dodgeholes.framework.GameObject;

public class ObstacleH extends GameObject {
    public static float OBSTACLE_H_WIDTH = 2f;
    public static float OBSTACLE_H_HEIGHT = 0.5f;
    

    public ObstacleH(float x, float y) {
        super(x, y, OBSTACLE_H_WIDTH, OBSTACLE_H_HEIGHT);
        
    }
    

}

