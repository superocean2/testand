package com.android.nghiatrinh.thuchi.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.android.nghiatrinh.thuchi.R;
import com.android.nghiatrinh.thuchi.fragments.DetailOverviewFragment;
import com.android.nghiatrinh.thuchi.fragments.ExpenseFragment;
import com.android.nghiatrinh.thuchi.fragments.IncomeFragment;
import com.android.nghiatrinh.thuchi.fragments.OverviewFragment;
import com.android.nghiatrinh.thuchi.helpers.Helper;

import java.util.Calendar;


/**
 * Created by NghiaTrinh on 3/10/2015.
 */
public class YearPickerDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.year_picker_dialog,null);
        final NumberPicker yearPicker = (NumberPicker) view.findViewById(R.id.year_selected_year);
        Button okButton = (Button)view.findViewById(R.id.ok_btn_year);
        Calendar calendar = Calendar.getInstance();
        yearPicker.setMaxValue(2035);
        yearPicker.setMinValue(2014);
        yearPicker.setValue(calendar.get(calendar.YEAR));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.select_year).setView(view);
        final Dialog dialog =builder.create();
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Fragment fragment =getActivity().getFragmentManager().findFragmentById(R.id.contents);
                String year = Helper.format4digits(yearPicker.getValue());
                Bundle bundle = new Bundle();
                bundle.putString("byyear",year);
                if (fragment instanceof IncomeFragment)
                {
                    IncomeFragment incomeFragment = new IncomeFragment();
                    incomeFragment.setArguments(bundle);
                    //getFragmentManager().popBackStack();
                    getActivity().getFragmentManager().beginTransaction().replace(R.id.contents, incomeFragment).commit();
                }
                if (fragment instanceof  OverviewFragment || fragment instanceof DetailOverviewFragment)
                {
                    DetailOverviewFragment overviewFragment = new DetailOverviewFragment();
                    overviewFragment.setArguments(bundle);
                    //getFragmentManager().popBackStack();
                    getActivity().getFragmentManager().beginTransaction().replace(R.id.contents, overviewFragment).commit();
                }
                if (fragment instanceof ExpenseFragment)
                {
                    ExpenseFragment expenseFragment = new ExpenseFragment();
                    expenseFragment.setArguments(bundle);
                    //getFragmentManager().popBackStack();
                    getActivity().getFragmentManager().beginTransaction().replace(R.id.contents, expenseFragment).commit();
                }
            }
        });
        return  dialog;
    }
}
