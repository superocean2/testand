package com.android.nghiatrinh.thuchi.model;

import com.orm.SugarRecord;

/**
 * Created by NghiaTrinh on 3/11/2015.
 */
public class IncomeCategory extends SugarRecord<IncomeCategory> {
    String Name;

    public IncomeCategory(){
    }
    public IncomeCategory(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
