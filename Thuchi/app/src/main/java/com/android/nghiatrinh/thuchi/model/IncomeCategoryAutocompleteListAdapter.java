package com.android.nghiatrinh.thuchi.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.nghiatrinh.thuchi.R;

import java.util.ArrayList;

public class IncomeCategoryAutocompleteListAdapter extends ArrayAdapter<Category>
{
    public IncomeCategoryAutocompleteListAdapter(Context context, ArrayList<Category> incomes) {
        super(context,0, incomes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_category_autocomplete_layout,parent,false);
        }
        TextView name = (TextView)convertView.findViewById(R.id.textview_name);
        TextView categoryId = (TextView)convertView.findViewById(R.id.hiddenCategoryID);
        Category category = getItem(position);
        name.setText(category.getName());
        categoryId.setText(category.getCategoryid());
        return convertView;
    }
}
