package com.android.nghiatrinh.thuchi.model;

import android.content.Context;

import com.android.nghiatrinh.thuchi.helpers.Helper;
import com.orm.SugarRecord;

import java.util.Date;
import java.util.List;

/**
 * Created by NghiaTrinh on 3/11/2015.
 */
public class Expense extends SugarRecord<Expense> {
    long categoryid;
    double amount;
    String date;
    String hour;
    String username;
    String description;

    public Expense(){
    }

    public Expense(long categoryid, double amount, String date, String hour, String username, String description) {
        this.categoryid = categoryid;
        this.amount = amount;
        this.date = date;
        this.hour = hour;
        this.username = username;
        this.description = description;
    }

    public long getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(long categoryid) {
        this.categoryid = categoryid;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static List<Expense> getByDate(String date,Context context)
    {
        return Expense.find(Expense.class,"date=? and username=?",date,String.valueOf(Helper.getUsername(context)));
    }
    public static List<Expense> getByMonth(String month,Context context)
    {
        return Expense.find(Expense.class,"strftime('%m-%Y',date)=? and username=?",month,String.valueOf(Helper.getUsername(context)));
    }
    public static List<Expense> getByYear(String year,Context context)
    {
        return Expense.find(Expense.class,"strftime('%Y',date)=? and username=?",year,String.valueOf(Helper.getUsername(context)));
    }
}
