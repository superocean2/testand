package com.samsam.colors;

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

    public static String toCapitalize(String s)
    {
        String first = s.substring(0,1);
        String remain = s.substring(1,s.length()-1);
        return first.toUpperCase()+remain.toLowerCase();
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

    public static String post(String url,String json)
    {
        Net.HttpRequest httpPost = new Net.HttpRequest(Net.HttpMethods.POST);
        httpPost.setUrl(url);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setContent(json);

        GameHttpListener listener = new GameHttpListener();
        Gdx.net.sendHttpRequest (httpPost,listener);
        return listener.result;
    }

    public static String get(String url)
    {
        Net.HttpRequest httpGet = new Net.HttpRequest(Net.HttpMethods.GET);
        httpGet.setUrl(url);
        GameHttpListener listener = new GameHttpListener();
        Gdx.net.sendHttpRequest(httpGet, listener);
        return listener.result;
    }

    public static String jsonSerialize(Objects o)
    {
        Json json = new Json();
        return json.toJson(o);
    }

    public static Objects jsonDeserialize(String js)
    {
        Json json = new Json();
        return json.fromJson(Objects.class,js);
    }

    private static class GameHttpListener implements Net.HttpResponseListener
    {
        public String result;
        @Override
        public void handleHttpResponse(Net.HttpResponse httpResponse) {
            result= httpResponse.getResultAsString();
        }

        @Override
        public void failed(Throwable t) {
            result = "failed";
        }

        @Override
        public void cancelled() {

        }
    }
}
