package com.android.nghiatrinh.thuchi.model;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by NghiaTrinh on 3/11/2015.
 */
public class Expense extends SugarRecord<Expense> {
    ExpenseCategory Category;
    float Amount;
    Date ExpenseDate;
    public Expense(){
    }
    public Expense(ExpenseCategory category, float amount,Date expenseDate) {
        Category = category;
        Amount = amount;
        ExpenseDate=expenseDate;
    }

    public ExpenseCategory getCategory() {
        return Category;
    }

    public void setCategory(ExpenseCategory category) {
        Category = category;
    }

    public float getAmount() {
        return Amount;
    }

    public void setAmount(float amount) {
        Amount = amount;
    }

    public Date getExpenseDate() {
        return ExpenseDate;
    }

    public void setExpenseDate(Date expenseDate) {
        ExpenseDate = expenseDate;
    }
}
