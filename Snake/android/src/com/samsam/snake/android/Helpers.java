package com.samsam.snake.android;

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
import java.util.Random;

/**
 * Created by NghiaTrinh on 7/6/2015.
 */
public class Helpers {

    public static int randomInt(int min, int max)
    {
        Random r = new Random();
        return r.nextInt(max-min)+min;
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
