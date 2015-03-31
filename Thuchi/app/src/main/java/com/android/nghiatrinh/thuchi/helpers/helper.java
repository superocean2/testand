package com.android.nghiatrinh.thuchi.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.nghiatrinh.thuchi.R;
import com.android.nghiatrinh.thuchi.activitys.MainActivity;
import com.android.nghiatrinh.thuchi.model.Income;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by NghiaTrinh on 3/16/2015.
 */
public final class Helper {
    private Helper()
    {

    }
    public static String formatMoney(double amount)
    {
        return new DecimalFormat("#,###").format(amount);
    }
    public  static String formatDate(Date date,boolean isyearmonthday)
    {
        if (isyearmonthday)
        {
            return new SimpleDateFormat("yyyy-MM-dd").format(date);
        }
        else {
            return new SimpleDateFormat("MM-dd-yyyy").format(date);
        }
    }
    public  static String formatDate(int year,int month, int day,boolean isyearmonthday)
    {
        if (isyearmonthday)
        {
            return String.format("%04d",year) + "-" + String.format("%02d", (month+1)) + "-" + String.format("%02d",day);
        }
        else {
            return String.format("%02d", (month+1)) + "-" + String.format("%02d",day) + "-" + String.format("%04d",year);
        }
    }
    public static String format2digits(Object o)
    {
        return String.format("%02d", o);
    }
    public static String format4digits(Object o)
    {
        return String.format("%04d", o);
    }
    public  static String formatDate(String date)
    {
        //date year/month/day
        String[] arr = date.split("-");
        //month/day/year
        return arr[1] +"-"+arr[2]+"-"+arr[0];
    }
    public  static String formatDate(Calendar date,boolean isyearmonthday)
    {
        if (isyearmonthday)
        {
            return String.format("%1$tY-%1$tm-%1$td", date);
        }
        else {
            //month/day/year
            return String.format("%1$tm-%1$td-%1$tY", date);
        }
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

}
