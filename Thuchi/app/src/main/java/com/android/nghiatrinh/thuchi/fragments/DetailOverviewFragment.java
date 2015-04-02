package com.android.nghiatrinh.thuchi.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.nghiatrinh.thuchi.R;
import com.android.nghiatrinh.thuchi.activitys.DetailIncomeExpenseActivity;
import com.android.nghiatrinh.thuchi.helpers.Helper;
import com.android.nghiatrinh.thuchi.model.Expense;
import com.android.nghiatrinh.thuchi.model.Income;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by NghiaTrinh on 3/2/2015.
 */
public class DetailOverviewFragment extends Fragment {
    String bydate=null;
    String bymonth=null;
    String byyear=null;
    ArrayList<Income> incomes=new ArrayList<>();
    ArrayList<Expense> expenses=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.detail_overview_fragment_layout,container,false);
        Helper.setActiveFragment(getActivity(), true, false, false);
        TextView textviewIncome = (TextView)view.findViewById(R.id.today_income);
        TextView textviewExpense = (TextView)view.findViewById(R.id.today_expense);
        TextView textviewRemaining = (TextView)view.findViewById(R.id.today_remaining);
        TextView title = (TextView)view.findViewById(R.id.today_title);
        Button buttonViewDetail=(Button)view.findViewById(R.id.btn_view_detail);
        Double totalIncomes = 0.0;
        Double totalExpense = 0.0;

        if (getArguments()!=null) {
            bydate = getArguments().getString("bydate");
            bymonth = getArguments().getString("bymonth");
            byyear = getArguments().getString("byyear");
        }
        if (bydate!=null)
        {
           List<Income> listincomes = Income.getByDate(bydate);
           for(Income income:listincomes)
           {
               incomes.add(income);
               totalIncomes+=income.getAmount();
           }
            List<Expense> listexpense= Expense.getByDate(Helper.formatDate(bydate));
            for(Expense expense:listexpense)
            {
                expenses.add(expense);
                totalExpense+=expense.getAmount();
            }
           textviewIncome.setText(Helper.formatMoney(totalIncomes));
           textviewExpense.setText(Helper.formatMoney(totalExpense));
           textviewRemaining.setText(Helper.formatMoney(totalIncomes-totalExpense));
           title.setText(Helper.formatDate(bydate));
        }

        //month
        if (bymonth!=null)
        {
            List<Income> listincomes = Income.getByMonth(bymonth);
            for(Income income:listincomes)
            {
                incomes.add(income);
                totalIncomes+=income.getAmount();
            }
            List<Expense> listexpense= Expense.getByMonth(bymonth);
            for(Expense expense:listexpense)
            {
                expenses.add(expense);
                totalExpense+=expense.getAmount();
            }
            textviewIncome.setText(Helper.formatMoney(totalIncomes));
            textviewExpense.setText(Helper.formatMoney(totalExpense));
            textviewRemaining.setText(Helper.formatMoney(totalIncomes-totalExpense));
            title.setText(bymonth);
        }
        //year
        if (byyear!=null)
        {
            List<Income> listincomes = Income.getByYear(byyear);
            for(Income income:listincomes)
            {
                incomes.add(income);
                totalIncomes+=income.getAmount();
            }
            List<Expense> listexpense= Expense.getByYear(byyear);
            for(Expense expense:listexpense)
            {
                expenses.add(expense);
                totalExpense+=expense.getAmount();
            }
            textviewIncome.setText(Helper.formatMoney(totalIncomes));
            textviewExpense.setText(Helper.formatMoney(totalExpense));
            textviewRemaining.setText(Helper.formatMoney(totalIncomes-totalExpense));
            title.setText(byyear);
        }
        buttonViewDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DetailIncomeExpenseActivity.class);
                if (bydate!=null)
                {
                    intent.putExtra("bydate", bydate);
                }
                if (bymonth!=null)
                {
                    intent.putExtra("bymonth", bymonth);
                }
                if (byyear!=null)
                {
                    intent.putExtra("byyear", byyear);
                }
                startActivity(intent);
            }
        });

        return view;
    }
}
