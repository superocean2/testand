package com.android.nghiatrinh.thuchi.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.nghiatrinh.thuchi.R;
import com.android.nghiatrinh.thuchi.activitys.MainActivity;
import com.android.nghiatrinh.thuchi.model.Income;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by NghiaTrinh on 3/16/2015.
 */
public final class Helper {
    private Helper()
    {

    }

    public static String getlanguageCode(Context context)
    {
       return context.getSharedPreferences("languageCode",0).getString("language","vi");
    }
    public static void setlanguageCode(String languageCode,Context context)
    {
        context.getSharedPreferences("languageCode",0).edit().putString("language",languageCode).commit();
    }

    public static String getUsername(Context context)
    {
        return context.getSharedPreferences("user",0).getString("username","0");
    }
    public static void setUsername(String username,Context context) {
        context.getSharedPreferences("user",0).edit().putString("username",username).commit();
    }
    public static byte[] readBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            throw new IOException("Could not completely read file " + file.getName() + " as it is too long (" + length + " bytes, max supported " + Integer.MAX_VALUE + ")");
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        // Close the input stream and return bytes
        is.close();
        return bytes;
    }

    public static void writeBytesToFile(File theFile, byte[] bytes) throws IOException {
        BufferedOutputStream bos = null;

        try {
            FileOutputStream fos = new FileOutputStream(theFile);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        }finally {
            if(bos != null) {
                try  {
                    //flush and close the BufferedOutputStream
                    bos.flush();
                    bos.close();
                } catch(Exception e){}
            }
        }
    }

    public static String formatMoney(double amount)
    {
        return new DecimalFormat("#,###").format(amount);
    }
    public  static String formatDate(Date date,boolean isyearmonthday,Context context)
    {
        if (isyearmonthday)
        {
            return new SimpleDateFormat("yyyy-MM-dd").format(date);
        }
        else {
            if (getlanguageCode(context).equals("vi"))
            {
                return new SimpleDateFormat("dd-MM-yyyy").format(date);
            }
        }
        return new SimpleDateFormat("MM-dd-yyyy").format(date);
    }
    public  static String formatDate(int year,int month, int day,boolean isyearmonthday,Context context)
    {
        if (isyearmonthday)
        {
            return String.format("%04d",year) + "-" + String.format("%02d", (month+1)) + "-" + String.format("%02d",day);
        }
        else {
            if (getlanguageCode(context).equals("vi"))
            {
              return   String.format("%02d",day) + "-" +String.format("%02d", (month+1))  + "-" + String.format("%04d",year);
            }
        }
        return String.format("%02d", (month+1)) + "-" + String.format("%02d",day) + "-" + String.format("%04d",year);
    }
    public static String format2digits(Object o)
    {
        return String.format("%02d", o);
    }
    public static String format4digits(Object o)
    {
        return String.format("%04d", o);
    }
    public  static String formatDate(String date,Context context)
    {
        //date year/month/day
        String[] arr = date.split("-");
        if (getlanguageCode(context).equals("vi"))
        {
            return arr[2] +"-"+arr[1]+"-"+arr[0];
        }
        //month/day/year
        return arr[1] +"-"+arr[2]+"-"+arr[0];
    }
    public  static String formatDate(Calendar date,boolean isyearmonthday,Context context)
    {
        if (isyearmonthday)
        {
            return String.format("%1$tY-%1$tm-%1$td", date);
        }
        else {
            if (getlanguageCode(context).equals("vi")) {
                return String.format("%1$td-%1$tm-%1$tY", date);
            }
        }
        //month/day/year
        return String.format("%1$tm-%1$td-%1$tY", date);
    }

    public static void validateRequired(Context context,String fieldname)
    {
        Toast.makeText(context,fieldname+" "+ context.getString(R.string.required),Toast.LENGTH_SHORT).show();
    }
    public static void validateRequired(Context context,int stringResourceId)
    {
        Toast.makeText(context, context.getString(stringResourceId)+" "+context.getString(R.string.required),Toast.LENGTH_SHORT).show();
    }

    public static void setActiveFragment(Activity activity, boolean overview,boolean income,boolean expense)
    {
        Button overviewButton = (Button)activity.findViewById(R.id.btn_overview);
        Button incomeButton = (Button)activity.findViewById(R.id.btn_income);
        Button expenseButton = (Button)activity.findViewById(R.id.btn_expense);
        if (overview)
        {
            overviewButton.setBackgroundResource(R.drawable.active_tab);
            incomeButton.setBackgroundResource(R.drawable.tab_btn_blue);
            expenseButton.setBackgroundResource(R.drawable.tab_btn_blue);
        }
        if (income)
        {
            overviewButton.setBackgroundResource(R.drawable.tab_btn_blue);
            incomeButton.setBackgroundResource(R.drawable.active_tab);
            expenseButton.setBackgroundResource(R.drawable.tab_btn_blue);
        }
        if (expense)
        {
            overviewButton.setBackgroundResource(R.drawable.tab_btn_blue);
            incomeButton.setBackgroundResource(R.drawable.tab_btn_blue);
            expenseButton.setBackgroundResource(R.drawable.active_tab);
        }
    }

    public  static Calendar getNewCalendar(String date)
    {
        Calendar calendar = Calendar.getInstance();
        String[] arr = date.split("-");
        String year=arr[0];
        String month=arr[1];
        String day = arr[2];
        calendar.set(Calendar.YEAR,Integer.parseInt(year));
        calendar.set(Calendar.MONTH,Integer.parseInt(month)-1);
        calendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(day));
        return  calendar;
    }
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
    public static boolean hasActiveInternetConnection(Context context) {
        if (isNetworkAvailable(context)) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
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
