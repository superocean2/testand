package com.example.nghiatrinh.tinhls;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void Calculate(View view)
    {
        if(!CheckDataInput()) return;
        Intent intent = new Intent(this,Result.class);
        startActivity(intent);
    }
    private boolean CheckDataInput()
    {
        String amount = ((EditText)findViewById(R.id.txtInputAmount)).getText().toString();
        String fromDate = ((EditText)findViewById(R.id.txtPutDate)).getText().toString();
        String toDate = ((EditText)findViewById(R.id.txtWithDrawDate)).getText().toString();
        String interest = ((EditText)findViewById(R.id.txtInterestRate)).getText().toString();
        if (amount.isEmpty()) {
            Toast.makeText(this,"Chưa nhập số tiền muốn tính",Toast.LENGTH_SHORT ).show();
            return false;
        }
        if (fromDate.isEmpty()) {
            Toast.makeText(this,"Chưa nhập ngày gửi",Toast.LENGTH_SHORT ).show();
            return false;
        }

        if (toDate.isEmpty()){
            Toast.makeText(this,"Chưa nhập ngày rút",Toast.LENGTH_SHORT ).show();
            return false;
        }

        if (interest.isEmpty()) {
            Toast.makeText(this,"Chưa nhập lãi suất",Toast.LENGTH_SHORT ).show();
            return false;
        }
        return  true;
    }

    public void showPutDatePickerDialog(View view)
    {
        DatePickerFragment date = new DatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("inputTo",1);
        date.setArguments(bundle);
        date.show(getSupportFragmentManager(), "datePicker1");
    }
    public void showWithDrawDatePickerDialog(View view)
    {
        DatePickerFragment date = new DatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("inputTo",2);
        date.setArguments(bundle);
        date.show(getSupportFragmentManager(), "datePicker1");
    }
    public void setDateForInputDate(String date,int inputTo)
    {
        if (inputTo==1)
        {
            EditText inputText = (EditText)findViewById(R.id.txtPutDate);
            inputText.setText(date);
        }
        if (inputTo==2)
        {
            EditText inputText = (EditText)findViewById(R.id.txtWithDrawDate);
            inputText.setText(date);
        }
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
