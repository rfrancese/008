package it.unisa.dodgeholes;

import it.unisa.dodgeholes.framework.GameObject;

public class Hole extends GameObject {
    public static float HOLE_WIDTH = 1f;
    public static float HOLE_HEIGHT = 1f;

    public Hole(float x, float y) {
        super(x, y, HOLE_WIDTH, HOLE_HEIGHT);
    }

}
