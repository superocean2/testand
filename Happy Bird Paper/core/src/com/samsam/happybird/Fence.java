package com.samsam.happybird;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by NghiaTrinh on 4/6/2016.
 */
public class Fence {
    Vector2 position = new Vector2();
    int height;
    boolean isCounted;

    public Fence(float x, float y,int height)
    {
        this.position.x = x;
        this.position.y = y;
        this.height = height;
        this.isCounted=false;
    }
}
