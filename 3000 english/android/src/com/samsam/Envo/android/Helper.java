package com.samsam.Envo.android;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by NghiaTrinh on 5/12/2015.
 */
public final class Helper {

    public static boolean isExistRegister(Context context)
    {
        String id = getRegiterId(context);
        if (id.isEmpty())
        {
            return  false;
        }
        return true;
    }
    public static String getRegiterId(Context context)
    {
        return context.getSharedPreferences("register",0).getString("registerId","");
    }
    public static void saveRegisterId(Context context,String id)
    {
        context.getSharedPreferences("register",0).edit().putString("registerId",id).commit();
    }
    public static String getBeacons(Context context)
    {
        return context.getSharedPreferences("listbeacons",0).getString("beacons","");
    }
    public static void saveBeacons(Context context,String id)
    {
        context.getSharedPreferences("listbeacons",0).edit().putString("beacons",id).commit();
    }
    public static int getDelayTime(Context context)
    {
        return context.getSharedPreferences("delay",0).getInt("delaytime",10);
    }
    public static void saveDelayTime(Context context,int delay)
    {
        context.getSharedPreferences("delay",0).edit().putInt("delaytime",delay).commit();
    }

    public static String getServerUrl(Context context)
    {
        return context.getSharedPreferences("server",0).getString("serverUrl","");
    }
    public static void saveServerUrl(Context context,String url)
    {
        context.getSharedPreferences("server",0).edit().putString("serverUrl",url).commit();
    }

    public static boolean isConnectingToInternet(Context context){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }

    public static HttpResponse getData(String url,List<NameValuePair> data)
    {
        HttpResponse responseGet=null;
        try {
            HttpClient client = new DefaultHttpClient();
            String paramString = URLEncodedUtils.format(data, "utf-8");
            url+="?";
            url+=paramString;
            HttpGet get = new HttpGet(url);
            responseGet = client.execute(get);
        } catch (Exception e) {
        }
        return  responseGet;
    }

    public static HttpResponse postData(String url,List<NameValuePair> data)
    {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-type", "application/x-www-form-urlencoded");
        try {
            post.setEntity(new UrlEncodedFormEntity(data));
        }
        catch (UnsupportedEncodingException e)
        {

        }
        HttpResponse response=null;
        try
        {
            response=client.execute(post);
        }
        catch (IOException e)
        {

        }
        return  response;
    }
}
