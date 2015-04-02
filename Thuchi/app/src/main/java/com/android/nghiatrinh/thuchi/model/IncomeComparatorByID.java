package com.android.nghiatrinh.thuchi.model;

import com.android.nghiatrinh.thuchi.helpers.Helper;

import java.util.Calendar;
import java.util.Comparator;

/**
 * Created by NghiaTrinh on 4/1/2015.
 */
public class IncomeComparatorByID implements Comparator<Income> {
    @Override
    public int compare(Income lhs, Income rhs) {
        return  rhs.getId().compareTo(lhs.getId());
    }
}
