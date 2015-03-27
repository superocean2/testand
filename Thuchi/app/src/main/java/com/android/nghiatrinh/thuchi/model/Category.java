package com.android.nghiatrinh.thuchi.model;

import com.orm.SugarRecord;

/**
 * Created by NghiaTrinh on 3/11/2015.
 */
public class Category extends SugarRecord<Category> {
    String name;
    boolean isincome;
    long userid;

    public Category(){
    }

    public Category(String name, boolean isincome, long userid) {
        this.name = name;
        this.isincome = isincome;
        this.userid = userid;
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

    public boolean getIsIncome() {
        return isincome;
    }

    public void setIsIncome(boolean isincome) {
        this.isincome = isincome;
    }

    public long getUserId() {
        return userid;
    }

    public void setUserId(long userid) {
        this.userid = userid;
    }
}
