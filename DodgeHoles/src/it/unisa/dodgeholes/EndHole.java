package it.unisa.dodgeholes;

import it.unisa.dodgeholes.framework.GameObject;

public class EndHole extends GameObject {
    public static float ENDHOLE_WIDTH = 1f;
    public static float ENDHOLE_HEIGHT = 1f;

    public EndHole(float x, float y) {
        super(x, y, ENDHOLE_WIDTH, ENDHOLE_HEIGHT);
    }

}
