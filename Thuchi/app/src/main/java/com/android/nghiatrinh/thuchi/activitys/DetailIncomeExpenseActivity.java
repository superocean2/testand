package com.android.nghiatrinh.thuchi.activitys;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.nghiatrinh.thuchi.R;
import com.android.nghiatrinh.thuchi.fragments.ExpenseFragmentDetail;
import com.android.nghiatrinh.thuchi.fragments.IncomeFragmentDetail;
import com.android.nghiatrinh.thuchi.helpers.Helper;
import com.android.nghiatrinh.thuchi.model.Expense;
import com.android.nghiatrinh.thuchi.model.Income;
import com.android.nghiatrinh.thuchi.model.IncomeComparatorByID;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DetailIncomeExpenseActivity extends ActionBarActivity {
    String bydate=null;
    String bymonth=null;
    String byyear=null;

    IncomeFragmentDetail incomeFragmentDetail = new IncomeFragmentDetail();
    ExpenseFragmentDetail expenseFragmentDetail = new ExpenseFragmentDetail();
    Bundle bundle = new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_income_expense_layout);
        bydate = getIntent().getStringExtra("bydate");
        bymonth = getIntent().getStringExtra("bymonth");
        byyear = getIntent().getStringExtra("byyear");
        TextView title = (TextView)findViewById(R.id.title_detail);

        if (bydate!=null)
        {
            title.setText(Helper.formatDate(bydate,getBaseContext()));
            bundle.putString("bydate",bydate);
            incomeFragmentDetail.setArguments(bundle);
            expenseFragmentDetail.setArguments(bundle);
        }
        if(bymonth!=null)
        {
            title.setText(bymonth);
            bundle.putString("bymonth",bymonth);
            incomeFragmentDetail.setArguments(bundle);
            expenseFragmentDetail.setArguments(bundle);
        }
        if(byyear!=null)
        {
            title.setText(byyear);
            bundle.putString("byyear",byyear);
            incomeFragmentDetail.setArguments(bundle);
            expenseFragmentDetail.setArguments(bundle);
        }

        getFragmentManager().beginTransaction().replace(R.id.contents_detail, incomeFragmentDetail).commit();
    }
    public void goToDetailIncome(View view)
    {
        if (bydate!=null)
        {
            bundle.putString("bydate",bydate);
            incomeFragmentDetail.setArguments(bundle);
        }
        if(bymonth!=null)
        {
            bundle.putString("bymonth",bymonth);
            incomeFragmentDetail.setArguments(bundle);
        }
        if(byyear!=null)
        {
            bundle.putString("byyear",byyear);
            incomeFragmentDetail.setArguments(bundle);
        }
        getFragmentManager().beginTransaction().replace(R.id.contents_detail, incomeFragmentDetail).commit();
    }
    public void goToBack(View view)
    {
        finish();
    }
    public void goToDetailExpense(View view)
    {
        if (bydate!=null)
        {
            bundle.putString("bydate",bydate);
            expenseFragmentDetail.setArguments(bundle);
        }
        if(bymonth!=null)
        {
            bundle.putString("bymonth",bymonth);
            expenseFragmentDetail.setArguments(bundle);
        }
        if(byyear!=null)
        {
            bundle.putString("byyear",byyear);
            expenseFragmentDetail.setArguments(bundle);
        }
        getFragmentManager().beginTransaction().replace(R.id.contents_detail, expenseFragmentDetail).commit();
    }
}
