package com.example.nghiatrinh.tinhls;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Parcel;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText txtAmount = (EditText)findViewById(R.id.txtInputAmount);
        txtAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    try {
                        String stAmount = txtAmount.getText().toString().replace(",","");
                        Double amount = Double.parseDouble(stAmount);
                        DecimalFormat formatter = new DecimalFormat("#,###");
                        txtAmount.setText(formatter.format(amount));
                    }
                    catch(Exception name)
                    {
                        Toast.makeText(getApplicationContext(),"Số tiền nhập vào không đúng",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void Calculate(View view)
    {
        if(!CheckDataInput()) return;
        Intent intent = new Intent(this,Result.class);
        ModelLS model = new ModelLS();
        model.Amount = Double.parseDouble(((EditText) findViewById(R.id.txtInputAmount)).getText().toString().replace(",", ""));
        model.Interest = Double.parseDouble(((EditText) findViewById(R.id.txtInterestRate)).getText().toString());
        RadioGroup groupInterestBy = (RadioGroup)findViewById(R.id.rdgInterestBy);
        switch (groupInterestBy.getCheckedRadioButtonId())
        {
            case R.id.rdoByMonth:
                model.InterestBy=1;
                break;
            case R.id.rdoByYear:
                model.InterestBy=2;
                break;
        }
        RadioGroup groupCalBy = (RadioGroup)findViewById(R.id.rdgCalBy);
        switch (groupCalBy.getCheckedRadioButtonId())
        {
            case R.id.rdoCalculateByMonth:
                model.IsCalByMonth=true;
                break;
            case R.id.rdoCalculateByDays:
                model.IsCalByMonth=false;
                break;
        }

        if (model.IsCalByMonth)
        {
            model.NumberOfMonths = Integer.parseInt(((TextView) findViewById(R.id.txt_numberOfMonth)).getText().toString());
        }
        else {
            model.FormDate = ((TextView) findViewById(R.id.tv_Fromdate)).getText().toString();
            model.ToDate = ((TextView) findViewById(R.id.tv_Todate)).getText().toString();
            model.NumberOfDays = Integer.parseInt(((TextView) findViewById(R.id.txt_numberOfDays)).getText().toString());
        }

        intent.putExtra("data", new Gson().toJson(model));
        startActivity(intent);
    }
    public void showCalculateByMonthDialog(View view)
    {
        InputMonthDialog dialog = new InputMonthDialog();
        dialog.show(getSupportFragmentManager(),"monthdialog");
    }
    public void setNumberOfMonths(String months)
    {
        ((TextView)findViewById(R.id.txt_numberOfMonth)).setText(months);
        (findViewById(R.id.txt_numberOfMonth_left)).setVisibility(View.VISIBLE);
        (findViewById(R.id.txt_numberOfMonth_right)).setVisibility(View.VISIBLE);
    }
    public void setNumberOfDays(String days,String fromDate,String toDate)
    {
        //Toast.makeText(this,days,Toast.LENGTH_SHORT).show();
        ((TextView)findViewById(R.id.txt_numberOfDays)).setText(days);
        ((TextView)findViewById(R.id.tv_Fromdate)).setText(fromDate);
        ((TextView)findViewById(R.id.tv_Todate)).setText(toDate);
        (findViewById(R.id.txt_numberOfDays_left)).setVisibility(View.VISIBLE);
        (findViewById(R.id.txt_numberOfDays_right)).setVisibility(View.VISIBLE);
    }
    public void showCalculateByDaysDialog(View view)
    {
        InputDaysDialog dialog = new InputDaysDialog();
        dialog.show(getSupportFragmentManager(),"daysdialog");
    }
    private boolean CheckDataInput()
    {
        String amount = ((EditText)findViewById(R.id.txtInputAmount)).getText().toString();
        String interest = ((EditText)findViewById(R.id.txtInterestRate)).getText().toString();
        if (amount.isEmpty()||!amount.replace(",","").matches("[0-9]+[\\.[0-9]]*")) {
            Toast.makeText(this,"Chưa nhập số tiền muốn tính",Toast.LENGTH_SHORT ).show();
            return false;
        }
        if (interest.isEmpty()||!interest.matches("[0-9]+[\\.[0-9]]*")) {
            Toast.makeText(this,"Chưa nhập lãi suất",Toast.LENGTH_SHORT ).show();
            return false;
        }

        RadioButton rdoByMonth = (RadioButton)findViewById(R.id.rdoCalculateByMonth);
        RadioButton rdoByDay = (RadioButton)findViewById(R.id.rdoCalculateByDays);

        if (!rdoByDay.isChecked()&&!rdoByMonth.isChecked()) return  false;

        if(rdoByMonth.isChecked())
        {
            String nbrOfMonth = ((TextView)findViewById(R.id.txt_numberOfMonth)).getText().toString();
            if (nbrOfMonth.isEmpty()) {
                Toast.makeText(this,"Số tháng gửi không đúng",Toast.LENGTH_SHORT ).show();
                return false;
            }
        }
        else
        {
            String fromDate = ((TextView)findViewById(R.id.tv_Fromdate)).getText().toString();
            String toDate = ((TextView)findViewById(R.id.tv_Todate)).getText().toString();
            String nbrOfDays = ((TextView)findViewById(R.id.txt_numberOfDays)).getText().toString();
            if (fromDate.isEmpty()) {
                Toast.makeText(this,"Chưa nhập ngày gửi",Toast.LENGTH_SHORT ).show();
                return false;
            }

            if (toDate.isEmpty()){
                Toast.makeText(this,"Chưa nhập ngày rút",Toast.LENGTH_SHORT ).show();
                return false;
            }

            if (nbrOfDays.isEmpty()) {
                Toast.makeText(this,"Số ngày gửi không đúng",Toast.LENGTH_SHORT ).show();
                return false;
            }
        }






        return  true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
