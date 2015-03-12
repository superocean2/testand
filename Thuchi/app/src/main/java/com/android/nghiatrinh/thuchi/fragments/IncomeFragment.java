package com.android.nghiatrinh.thuchi.fragments;

import android.app.ActivityOptions;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.nghiatrinh.thuchi.R;
import com.android.nghiatrinh.thuchi.activitys.AddNewIncomeActivity;


/**
 * Created by NghiaTrinh on 3/2/2015.
 */
public class IncomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.income_fragment_layout,container,false);
        Button button_new = (Button)view.findViewById(R.id.add_new_income);
        TextView title_list = (TextView)view.findViewById(R.id.textview_title_list);
        button_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddNewIncomeActivity.class);
                startActivity(intent);
            }
        });
        title_list.setText(R.string.today);
        ListView listView = (ListView)view.findViewById(R.id.listview_list_income);

        return view;
    }
}
