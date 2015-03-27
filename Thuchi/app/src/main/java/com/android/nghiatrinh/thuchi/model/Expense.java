package com.android.nghiatrinh.thuchi.model;

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
    long userid;
    String description;

    public Expense(){
    }

    public Expense(long categoryid, double amount, String date, String hour, long userid, String description) {
        this.categoryid = categoryid;
        this.amount = amount;
        this.date = date;
        this.hour = hour;
        this.userid = userid;
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

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public static List<Expense> getByDate(String date)
    {
        return Expense.find(Expense.class,"date=?",date);
    }
    public static List<Expense> getByMonth(String month)
    {
        return Expense.find(Expense.class,"strftime('%m-%Y',date)=?",month);
    }
    public static List<Expense> getByYear(String year)
    {
        return Expense.find(Expense.class,"strftime('%Y',date)=?",year);
    }
}
