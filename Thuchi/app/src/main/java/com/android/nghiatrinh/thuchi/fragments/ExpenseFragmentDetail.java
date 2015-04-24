package com.android.nghiatrinh.thuchi.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.nghiatrinh.thuchi.R;
import com.android.nghiatrinh.thuchi.helpers.Helper;
import com.android.nghiatrinh.thuchi.model.Expense;
import com.android.nghiatrinh.thuchi.model.ExpenseComparatorByDate;
import com.android.nghiatrinh.thuchi.model.ExpenseComparatorByID;
import com.android.nghiatrinh.thuchi.model.ExpenseListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by NghiaTrinh on 4/1/2015.
 */
public class ExpenseFragmentDetail extends Fragment{
    String bydate=null;
    String bymonth=null;
    String byyear=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = getActivity().getLayoutInflater().inflate(R.layout.income_expense_detail_fragment,container,false);
        TextView totalTextview = (TextView)view.findViewById(R.id.textview_total);
        View line_break = view.findViewById(R.id.line_space_break);
        ArrayList<Expense> incomes = new ArrayList<>();
        Helper.setActiveFragment(getActivity(),false,false,true);

        Double total =0.0;
        List<Expense> list=new ArrayList<>();
        List<Expense> listTemp = new ArrayList<>();
        if (getArguments()!=null) {
            bydate = getArguments().getString("bydate");
            bymonth = getArguments().getString("bymonth");
            byyear = getArguments().getString("byyear");
        }
        if (bydate!=null)
        {
            list = Expense.getByDate(bydate,getActivity());
        }
        if (bymonth!=null)
        {
            list = Expense.getByMonth(bymonth,getActivity());
        }
        if (byyear!=null)
        {
            list = Expense.getByYear(byyear,getActivity());
        }

        for(Expense income:list)
        {
            boolean checkExist=false;
            long id = 0;
            for (Expense temp:listTemp)
            {
                if(temp.getCategoryid().equals(income.getCategoryid()))
                {
                    checkExist=true;
                    id=temp.getId();
                    break;
                }
            }
            if (checkExist)
            {
                for (int i=0;i<listTemp.size();i++)
                {
                    if (listTemp.get(i).getId()==id)
                    {
                        double amount = listTemp.get(i).getAmount() + income.getAmount();
                        listTemp.get(i).setAmount(amount);
                    }
                }
            }
            else
            {
                listTemp.add(income);
            }
        }

        if (bydate!=null)
        {
            Collections.sort(listTemp, new ExpenseComparatorByID());
        }
        if (bymonth!=null)
        {
            Collections.sort(listTemp,new ExpenseComparatorByDate());
        }
        if (byyear!=null)
        {
            Collections.sort(listTemp,new ExpenseComparatorByDate());
        }

        for (Expense income:listTemp)
        {
            total+=income.getAmount();
            incomes.add(income);
        }
        if (incomes.size()==0)
        {
            //hide
            line_break.setVisibility(View.INVISIBLE);
            totalTextview.setVisibility(View.INVISIBLE);
        }
        totalTextview.setText(Helper.formatMoney(total));
        ListView listView =(ListView)view.findViewById(R.id.listview_detail);
        ExpenseListAdapter adapter = new ExpenseListAdapter(getActivity(),incomes);
        listView.setAdapter(adapter);
        return view;
    }
}
