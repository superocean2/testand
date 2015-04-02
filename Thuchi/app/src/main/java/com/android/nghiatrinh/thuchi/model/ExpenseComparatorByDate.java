package com.android.nghiatrinh.thuchi.model;

import com.android.nghiatrinh.thuchi.helpers.Helper;

import java.util.Calendar;
import java.util.Comparator;

/**
 * Created by NghiaTrinh on 4/1/2015.
 */
public class ExpenseComparatorByDate implements Comparator<Expense> {
    @Override
    public int compare(Expense lhs, Expense rhs) {
        Calendar leftDate = Helper.getNewCalendar(lhs.getDate());
        Calendar rightDate = Helper.getNewCalendar(rhs.getDate());
        return  rightDate.compareTo(leftDate);
    }
}
