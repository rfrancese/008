package it.unisa.dodgeholes;

import it.unisa.dodgeholes.framework.GameObject;

public class Life extends GameObject {
    public static float LIFE_WIDTH = 0.8f;
    public static float LIFE_HEIGHT = 0.8f;

    public Life(float x, float y) {
        super(x, y, LIFE_WIDTH, LIFE_HEIGHT);
    }

}
