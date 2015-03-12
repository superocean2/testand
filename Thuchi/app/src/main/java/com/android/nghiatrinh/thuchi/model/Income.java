package com.android.nghiatrinh.thuchi.model;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by NghiaTrinh on 3/11/2015.
 */
public class Income extends SugarRecord<Income> {
    IncomeCategory Category;
    float Amount;
    Date InComeDate;

    public Income(){
    }
    public Income(IncomeCategory category, float amount,Date inComeDate) {
        Category = category;
        Amount = amount;
        InComeDate = inComeDate;
    }

    public IncomeCategory getCategory() {
        return Category;
    }

    public void setCategory(IncomeCategory category) {
        Category = category;
    }

    public float getAmount() {
        return Amount;
    }

    public void setAmount(float amount) {
        Amount = amount;
    }

    public Date getInComeDate() {
        return InComeDate;
    }

    public void setInComeDate(Date inComeDate) {
        InComeDate = inComeDate;
    }
}
