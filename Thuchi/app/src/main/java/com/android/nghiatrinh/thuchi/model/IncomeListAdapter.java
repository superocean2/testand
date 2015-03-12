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

public class IncomeListAdapter extends ArrayAdapter<Income>
{
    public IncomeListAdapter(Context context, ArrayList<Income> incomes) {
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

        Income income = getItem(position);
        stt.setText(Integer.toString(position+1) +". ");
        name.setText(income.getCategory().getName());
        amount.setText(new DecimalFormat("#,###").format(income.getAmount()));
        return convertView;
    }
}