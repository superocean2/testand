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

public class IncomeCategoryListAdapter extends ArrayAdapter<IncomeCategory>
{
    public IncomeCategoryListAdapter(Context context, ArrayList<IncomeCategory> incomes) {
        super(context,0, incomes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_category_layout,parent,false);
        }
        TextView name = (TextView)convertView.findViewById(R.id.textview_name);


        IncomeCategory income = getItem(position);
        name.setText(income.getName());
        return convertView;
    }
}
