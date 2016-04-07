package com.samsam.happybird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Rectangle;

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
        Preferences prefs = Gdx.app.getPreferences( "newblock" );
        prefs.putInteger( "highscore", score );
        prefs.flush();
    }

    public static int getHighScore()
    {
        Preferences prefs = Gdx.app.getPreferences( "newblock" );
        return prefs.getInteger("highscore",0);
    }

    public static void saveStatus(int status)
    {
        Preferences prefs = Gdx.app.getPreferences( "newblock" );
        prefs.putInteger( "status", status );
        prefs.flush();
    }

    public static int getStatus()
    {
        Preferences prefs = Gdx.app.getPreferences( "newblock" );
        return prefs.getInteger("status",0);
    }
}
