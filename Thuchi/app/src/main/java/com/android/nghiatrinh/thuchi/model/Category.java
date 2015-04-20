package com.android.nghiatrinh.thuchi.model;

import com.orm.SugarRecord;


/**
 * Created by NghiaTrinh on 3/11/2015.
 */
public class Category extends SugarRecord<Category> implements Comparable {
    String name;
    boolean isincome;
    String username;

    public Category(){
    }

    public Category(String name, boolean isincome, String username) {
        this.name = name;
        this.isincome = isincome;
        this.username = username;
    }

    @Override
    public int compareTo(Object category) {

        String[] arrThisName = this.name.trim().split(" ");
        String[] arrOtherName = ((Category)category).getName().trim().split(" ");

        try{
            return arrThisName[0].compareToIgnoreCase(arrOtherName[0]);
        }
        catch (Exception ex)
        {
            return 0;
        }

    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isincome() {
        return isincome;
    }

    public void setIsincome(boolean isincome) {
        this.isincome = isincome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
