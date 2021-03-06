package com.samsam.xmasblock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Json;

import java.util.Objects;
import java.util.Random;

/**
 * Created by NghiaTrinh on 7/6/2015.
 */
public class Helpers {
    public  static boolean isTouchedInRect(Rectangle r,float xInput,float yInput)
    {
        if (r.x<=xInput&&r.x+r.width>=xInput&&r.y<=yInput&&r.y+r.height>=yInput)
        {
            return true;
        }
        return  false;
    }

    public static int randomInt(int min, int max)
    {
        Random r = new Random();
        return r.nextInt(max-min)+min;
    }

    public static void saveHighScore(int score)
    {
        Preferences prefs = Gdx.app.getPreferences( "profiles" );
        prefs.putInteger( "highscore", score );
        prefs.flush();
    }

    public static int getHighScore()
    {
        Preferences prefs = Gdx.app.getPreferences( "profiles" );
        return prefs.getInteger("highscore",0);
    }
}
