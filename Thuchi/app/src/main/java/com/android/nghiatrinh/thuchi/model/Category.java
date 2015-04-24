package com.android.nghiatrinh.thuchi.model;

import com.orm.SugarRecord;


/**
 * Created by NghiaTrinh on 3/11/2015.
 */
public class Category extends SugarRecord<Category> implements Comparable {
    String name;
    boolean isincome;
    String username;
    String categoryid;
    boolean isdelete;
    Integer version;

    public Category(){
    }

    public Category(String name, boolean isincome, String username,String categoryid,boolean isdelete,Integer version) {
        this.name = name;
        this.isincome = isincome;
        this.username = username;
        this.categoryid=categoryid;
        this.isdelete=isdelete;
        this.version=version;
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

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
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
}
