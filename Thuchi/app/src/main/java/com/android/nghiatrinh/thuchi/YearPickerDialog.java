package com.android.nghiatrinh.thuchi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;


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
        yearPicker.setMaxValue(2035);
        yearPicker.setMinValue(2014);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.select_year).setView(view);
        final Dialog dialog =builder.create();
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(getActivity(),String.valueOf(yearPicker.getValue()),Toast.LENGTH_SHORT).show();
            }
        });
        return  dialog;
    }
}
