package com.example.nghiatrinh.tinhls;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.GregorianCalendar;

/**
 * Created by NghiaTrinh on 12/25/2014.
 */
public class InputDaysDialog extends android.support.v4.app.DialogFragment {

    private View thisView=null;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_calculate_by_days, null);
        thisView=view;
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.input_number_of_days)
                .setView(view);

        Button okButton = (Button)view.findViewById(R.id.btnOKInCalculateByDays);
        Button putDateButton = (Button)view.findViewById(R.id.btnPutDate);
        Button withDrawButton = (Button)view.findViewById(R.id.btnWithDrawDate);
        final Dialog dialog = builder.create();
        final DatePickerFragment date = new DatePickerFragment();
        putDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = (getActivity()).getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putInt("inputTo", 1);
                date.setArguments(bundle);
                date.show(ft,"datePicker1");
            }
        });
        withDrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = (getActivity()).getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putInt("inputTo", 2);
                date.setArguments(bundle);
                date.show(ft,"datePicker1");
            }
        });
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ValidateInput(view)){
                    Toast.makeText(getActivity(),R.string.input_number_of_days,Toast.LENGTH_SHORT).show();
                }
                else {
                    String fromDay=((EditText)view.findViewById(R.id.txtPutDate)).getText().toString();
                    String toDay=((EditText)view.findViewById(R.id.txtWithDrawDate)).getText().toString();
                    String[] arrFromDate = fromDay.split("-");
                    String[] arrToDate = toDay.split("-");
                    GregorianCalendar fromDate = new GregorianCalendar(Integer.parseInt(arrFromDate[2]),Integer.parseInt(arrFromDate[1])-1,Integer.parseInt(arrFromDate[0]));
                    GregorianCalendar toDate = new GregorianCalendar(Integer.parseInt(arrToDate[2]),Integer.parseInt(arrToDate[1])-1,Integer.parseInt(arrToDate[0]));
                    int days = (int)((toDate.getTimeInMillis()-fromDate.getTimeInMillis())/(1000*60*60*24));
                    MainActivity activity = (MainActivity) getActivity();
                    activity.setNumberOfDays(Integer.toString(days), fromDay, toDay);
                    dialog.dismiss();
                }
            }
        });
        return dialog;
    }
    private boolean ValidateInput(View view)
    {
        String txtPutDate = ((EditText)view.findViewById(R.id.txtPutDate)).getText().toString();
        String txtWithdrawDate = ((EditText)view.findViewById(R.id.txtWithDrawDate)).getText().toString();
        if (txtPutDate.isEmpty()||txtWithdrawDate.isEmpty()) return false;
        String[] arrFromDate = txtPutDate.split("-");
        String[] arrToDate = txtWithdrawDate.split("-");
        GregorianCalendar fromDate = new GregorianCalendar(Integer.parseInt(arrFromDate[2]),Integer.parseInt(arrFromDate[1])-1,Integer.parseInt(arrFromDate[0]));
        GregorianCalendar toDate = new GregorianCalendar(Integer.parseInt(arrToDate[2]),Integer.parseInt(arrToDate[1])-1,Integer.parseInt(arrToDate[0]));
        int days = (int)((toDate.getTimeInMillis()-fromDate.getTimeInMillis())/(1000*60*60*24));
        if (days<1) return false;
        return true;
    }
    public void setDateForInputDate(String date,int inputTo)
    {

        //Toast.makeText(getActivity(),date,Toast.LENGTH_SHORT).show();
        if (inputTo==1)
        {
            EditText inputText = (EditText)thisView.findViewById(R.id.txtPutDate);
            EditText withdrawtxt = (EditText)thisView.findViewById(R.id.txtWithDrawDate);
            if (!withdrawtxt.getText().toString().isEmpty())
            {
                String dateWithDraw = withdrawtxt.getText().toString();
                String[] arr = dateWithDraw.split("-");
                GregorianCalendar dWithDraw = new GregorianCalendar(Integer.parseInt(arr[2]),Integer.parseInt(arr[1])-1,Integer.parseInt(arr[0]));
                String[] arrPut = date.split("-");
                GregorianCalendar dPutDate = new GregorianCalendar(Integer.parseInt(arrPut[2]),Integer.parseInt(arrPut[1])-1,Integer.parseInt(arrPut[0]));
                if (dWithDraw.before(dPutDate)||dWithDraw.equals(dPutDate))
                {

                    Toast.makeText(getActivity(),"Ngày rút hoặc ngày gửi không đúng",Toast.LENGTH_SHORT).show();
                }
                else {
                    inputText.setText(date);
                }
            }
            else {
                inputText.setText(date);
            }
        }
        if (inputTo==2)
        {
            EditText inputText = (EditText)thisView.findViewById(R.id.txtPutDate);
            EditText withdrawtxt = (EditText)thisView.findViewById(R.id.txtWithDrawDate);
            if (!inputText.getText().toString().isEmpty())
            {
                String[] arr = date.split("-");
                GregorianCalendar dWithDraw = new GregorianCalendar(Integer.parseInt(arr[2]),Integer.parseInt(arr[1])-1,Integer.parseInt(arr[0]));
                String[] arrPut = inputText.getText().toString().split("-");
                GregorianCalendar dPutDate = new GregorianCalendar(Integer.parseInt(arrPut[2]),Integer.parseInt(arrPut[1])-1,Integer.parseInt(arrPut[0]));
                if (dWithDraw.before(dPutDate)||dWithDraw.equals(dPutDate))
                {
                    Toast.makeText(getActivity(),"Ngày rút hoặc ngày gửi không đúng",Toast.LENGTH_SHORT).show();
                }
                else {
                    withdrawtxt.setText(date);
                }
            }
            else {
                withdrawtxt.setText(date);
            }
        }
    }
}
