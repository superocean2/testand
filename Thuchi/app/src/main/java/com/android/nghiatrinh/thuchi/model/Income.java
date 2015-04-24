package com.android.nghiatrinh.thuchi.model;

import android.content.Context;

import com.android.nghiatrinh.thuchi.helpers.Helper;
import com.orm.SugarRecord;

import java.util.Date;
import java.util.List;

/**
 * Created by NghiaTrinh on 3/11/2015.
 */
public class Income extends SugarRecord<Income> {
    String categoryid;
    double amount;
    String date;
    String hour;
    String username;
    String description;
    String incomeid;
    boolean isdelete;
    Integer version;

    public Income(){
    }

    public Income(String categoryid, double amount, String date, String hour, String username, String description,String incomeid,boolean isdelete,Integer version) {
        this.categoryid = categoryid;
        this.amount = amount;
        this.date = date;
        this.hour = hour;
        this.username = username;
        this.description = description;
        this.incomeid=incomeid;
        this.isdelete=isdelete;
        this.version=version;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
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

    public String getIncomeid() {
        return incomeid;
    }

    public void setIncomeid(String incomeid) {
        this.incomeid = incomeid;
    }

    public boolean isdelete() {
        return isdelete;
    }

    public void setIsdelete(boolean isdelete) {
        this.isdelete = isdelete;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public static List<Income> getByDate(String date,Context context)
    {
        return Income.find(Income.class,"date=? and username=? and isdelete=?",date,Helper.getUsername(context),"0");
    }
    public static List<Income> getByMonth(String month,Context context)
    {
        return Income.find(Income.class,"strftime('%m-%Y',date)=? and username=? and isdelete=?",month,Helper.getUsername(context),"0");
    }
    public static List<Income> getByYear(String year,Context context)
    {
        return Income.find(Income.class,"strftime('%Y',date)=? and username=? and isdelete=?",year,Helper.getUsername(context),"0");
    }
}
