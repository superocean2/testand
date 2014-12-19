package com.example.nghiatrinh.tinhls;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by NghiaTrinh on 12/19/2014.
 */
public class DatePickerFragment extends DialogFragment implements OnDateSetListener{
    private int inputTo;
    @Override
    public void setArguments(Bundle bundle)
    {
        inputTo = bundle.getInt("inputTo");
    }
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceBundle)
    {
        Calendar calendar = Calendar.getInstance();
        return new DatePickerDialog(getActivity(),this,calendar.get(calendar.YEAR),calendar.get(calendar.MONTH),calendar.get((calendar.DAY_OF_MONTH)));
    }

    //for implement Listener
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
    {
        MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.setDateForInputDate(dayOfMonth+"-"+monthOfYear+"-"+year,inputTo);
    }
}
