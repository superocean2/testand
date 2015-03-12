package com.android.nghiatrinh.thuchi.activitys;

import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.nghiatrinh.thuchi.R;

import java.util.Calendar;

public class AddNewIncomeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_income_expense_layout);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new_income, menu);
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
    public void openDateDialog(View view)
    {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this,new SetDate(),calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        dialog.setTitle(R.string.select_date);
        dialog.show();
    }
    private class SetDate implements DatePickerDialog.OnDateSetListener
    {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Toast.makeText(view.getContext(), year + "-" + monthOfYear + "-" + dayOfMonth, Toast.LENGTH_SHORT).show();
        }
    }
    public void openChooseCategory(View view)
    {
        Intent  intent = new Intent(this,ListCategoryActivity.class);
        Bundle bundle= ActivityOptions.makeCustomAnimation(this, R.transition.pull_in_right, R.transition.fade_out).toBundle();
        startActivity(intent,bundle);
    }
}
