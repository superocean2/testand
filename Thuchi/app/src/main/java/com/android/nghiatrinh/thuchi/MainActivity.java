package com.android.nghiatrinh.thuchi;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends ActionBarActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        OverviewFragment overviewFragment = new OverviewFragment();
        getFragmentManager().beginTransaction().replace(R.id.contents, overviewFragment).commit();
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
    public void goToOverview(View view)
    {
        OverviewFragment overviewFragment = new OverviewFragment();
        getFragmentManager().beginTransaction().replace(R.id.contents, overviewFragment).commit();
    }
    public void goToIncome(View view)
    {
        IncomeFragment incomeFragment = new IncomeFragment();
        getFragmentManager().beginTransaction().replace(R.id.contents, incomeFragment).commit();
    }
    public void goToSpend(View view)
    {
        SpendFragment spendFragment = new SpendFragment();
        getFragmentManager().beginTransaction().replace(R.id.contents, spendFragment).commit();
    }

    public void openDateDialog(View view)
    {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this,new SetDate(),calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        dialog.setTitle(R.string.select_date);
        dialog.show();
    }
    public void openMonthDialog(View view)
    {
        MonthPickerDialog dialog = new MonthPickerDialog();
        dialog.show(getSupportFragmentManager(),"MonthPicker");
    }

    public void openYearDialog(View view)
    {
        YearPickerDialog dialog = new YearPickerDialog();
        dialog.show(getSupportFragmentManager(),"YearPicker");
    }
    public class SetDate implements DatePickerDialog.OnDateSetListener
    {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Toast.makeText(view.getContext(),year+"-"+monthOfYear+"-"+dayOfMonth,Toast.LENGTH_SHORT).show();
        }
    }
}

