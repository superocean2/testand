package com.android.nghiatrinh.thuchi.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.nghiatrinh.thuchi.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ExpenseListAdapter extends ArrayAdapter<Expense>
{
    public ExpenseListAdapter(Context context, ArrayList<Expense> incomes) {
        super(context,0, incomes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_income_expense_layout,parent,false);
        }
        TextView stt = (TextView)convertView.findViewById(R.id.textview_stt);
        TextView name = (TextView)convertView.findViewById(R.id.textview_name);
        TextView amount = (TextView)convertView.findViewById(R.id.textview_amount);
        TextView incomeId = (TextView)convertView.findViewById(R.id.itemId);

        Expense income = getItem(position);
        stt.setText(Integer.toString(position+1) +". ");
        Category category = Category.findById( Category.class,income.getCategoryid());
        if (category!=null)
        {
            name.setText(category.getName());
        }
        amount.setText(new DecimalFormat("#,###").format(income.getAmount()));
        incomeId.setText(income.getId().toString());
        return convertView;
    }
}
