package com.android.nghiatrinh.thuchi.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.nghiatrinh.thuchi.R;
import com.android.nghiatrinh.thuchi.helpers.Helper;
import com.android.nghiatrinh.thuchi.model.Expense;
import com.android.nghiatrinh.thuchi.model.Income;

import java.util.Calendar;
import java.util.List;

/**
 * Created by NghiaTrinh on 3/2/2015.
 */
public class OverviewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.overview_fragment_layout,container,false);
        Helper.setActiveFragment(getActivity(), true, false, false);
        Calendar today = Calendar.getInstance();
        String thisMonth =Helper.format2digits(today.get(Calendar.MONTH) +1) +"-"+Helper.format4digits(today.get(Calendar.YEAR));
        String thisYear = Helper.format4digits(today.get(Calendar.YEAR));
        List<Income> incomesBydate = Income.getByDate(Helper.formatDate(today,true,getActivity()),getActivity());
        List<Income> incomesBymonth = Income.getByMonth(thisMonth,getActivity());
        List<Income> incomesByyear = Income.getByYear(thisYear,getActivity());

        List<Expense> expensesBydate = Expense.getByDate(Helper.formatDate(today,true,getActivity()),getActivity());
        List<Expense> expensesBymonth = Expense.getByMonth(thisMonth,getActivity());
        List<Expense> expensesByyear = Expense.getByYear(thisYear,getActivity());

        Double totalIncomeBydate=0.0;
        Double totalIncomeBymonth=0.0;
        Double totalIncomeByyear=0.0;

        Double totalExpenseBydate=0.0;
        Double totalExpenseBymonth=0.0;
        Double totalExpenseByyear=0.0;

        TextView todayIncome = (TextView)view.findViewById(R.id.today_income);
        TextView todayExpense = (TextView)view.findViewById(R.id.today_expense);
        TextView todayRemaining = (TextView)view.findViewById(R.id.today_remaining);

        TextView monthIncome = (TextView)view.findViewById(R.id.month_income);
        TextView monthExpense = (TextView)view.findViewById(R.id.month_expense);
        TextView monthRemaining = (TextView)view.findViewById(R.id.month_remaining);

        TextView yearIncome = (TextView)view.findViewById(R.id.year_income);
        TextView yearExpense = (TextView)view.findViewById(R.id.year_expense);
        TextView yearRemaining = (TextView)view.findViewById(R.id.year_remaining);

        TextView monthTitle = (TextView)view.findViewById(R.id.month_title);
        TextView yearTitle = (TextView)view.findViewById(R.id.year_title);

        for(Income income:incomesBydate)
        {
            totalIncomeBydate+=income.getAmount();
        }
        for(Income income:incomesBymonth)
        {
            totalIncomeBymonth+=income.getAmount();
        }
        for(Income income:incomesByyear)
        {
            totalIncomeByyear+=income.getAmount();
        }

        for(Expense expense:expensesBydate)
        {
            totalExpenseBydate+=expense.getAmount();
        }
        for(Expense expense:expensesBymonth)
        {
            totalExpenseBymonth+=expense.getAmount();
        }
        for(Expense expense:expensesByyear)
        {
            totalExpenseByyear+=expense.getAmount();
        }

        todayIncome.setText(Helper.formatMoney(totalIncomeBydate));
        todayExpense.setText(Helper.formatMoney(totalExpenseBydate));
        todayRemaining.setText(Helper.formatMoney(totalIncomeBydate-totalExpenseBydate));

        monthIncome.setText(Helper.formatMoney(totalIncomeBymonth));
        monthExpense.setText(Helper.formatMoney(totalExpenseBymonth));
        monthRemaining.setText(Helper.formatMoney(totalIncomeBymonth-totalExpenseBymonth));

        yearIncome.setText(Helper.formatMoney(totalIncomeByyear));
        yearExpense.setText(Helper.formatMoney(totalExpenseByyear));
        yearRemaining.setText(Helper.formatMoney(totalIncomeByyear-totalExpenseByyear));

        monthTitle.setText(thisMonth);
        yearTitle.setText(thisYear);

        return view;
    }
}
