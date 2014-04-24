package it.unisa.dodgeholes;

import it.unisa.dodgeholes.framework.GameObject;

public class ObstacleV extends GameObject {
    public static float OBSTACLE_V_WIDTH = 0.5f;
    public static float OBSTACLE_V_HEIGHT = 2f;
    

    public ObstacleV(float x, float y) {
        super(x, y, OBSTACLE_V_WIDTH, OBSTACLE_V_HEIGHT);
        
    }
    

}
