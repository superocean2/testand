package com.android.nghiatrinh.thuchi.model;

import java.util.Comparator;

/**
 * Created by NghiaTrinh on 4/1/2015.
 */
public class ExpenseComparatorByID implements Comparator<Expense> {
    @Override
    public int compare(Expense lhs, Expense rhs) {
        return  rhs.getId().compareTo(lhs.getId());
    }
}
