package com.example.nghiatrinh.tinhls;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by NghiaTrinh on 12/25/2014.
 */
public class InputMonthDialog extends android.support.v4.app.DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_calculate_by_month, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.input_number_of_months)
                .setView(view);
        Button okButton = (Button)view.findViewById(R.id.btnOKMonthsDialog);
        final Dialog dialog = builder.create();
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ValidateInput(view)){
                    Toast.makeText(getActivity(),R.string.input_number_of_months,Toast.LENGTH_SHORT).show();
                }
                else {
                    MainActivity activity = (MainActivity) getActivity();
                    activity.setNumberOfMonths(((EditText) view.findViewById(R.id.numberOfMonths)).getText().toString());
                    dialog.dismiss();
                }
            }
        });
        return dialog;
    }
    private boolean ValidateInput(View view)
    {
        String txt = ((EditText)view.findViewById(R.id.numberOfMonths)).getText().toString();
        if (txt.isEmpty()||!txt.matches("[1-9]?[1-9]")) return false;
        else
        return true;
    }

}
