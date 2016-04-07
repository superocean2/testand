package com.samsam.happybird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by NghiaTrinh on 4/6/2016.
 */
public class Bird {
    public Vector2 position;
    public Vector2 velocity;
    Texture image;

    public Bird()
    {
        position = new Vector2();
        velocity = new Vector2();
    }
}
