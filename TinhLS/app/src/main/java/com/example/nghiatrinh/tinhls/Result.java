package com.example.nghiatrinh.tinhls;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.GregorianCalendar;


public class Result extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        ModelLS model = new Gson().fromJson(data,ModelLS.class);
        setContentView(R.layout.activity_result);
        DecimalFormat formatter = new DecimalFormat("#,###");
        ((TextView)findViewById(R.id.tv_resultAmount)).setText(formatter.format(model.Amount));
        ((TextView)findViewById(R.id.tv_interest)).setText(Double.toString(model.Interest)+ " %("+(model.InterestBy==1?"tháng":"năm")+")");
        TableLayout layout = (TableLayout)findViewById(R.id.tablelayoutResult);
        double interestRate = model.InterestBy==1?model.Interest:Double.valueOf(new DecimalFormat("#.###").format(model.Interest/12));
        if (model.IsCalByMonth)
        {
            layout.removeView(findViewById(R.id.tv_resultFromDate_row));
            layout.removeView(findViewById(R.id.tv_resultToDate_row));
            layout.removeView(findViewById(R.id.tv_resultNumberOfDate_row));
            //so thang gui
            ((TextView)findViewById(R.id.tv_result_number_of_month)).setText(model.NumberOfMonths + " tháng");
            long totalInterest = Math.round(model.Amount*interestRate*model.NumberOfMonths/100);
            long total =Math.round(totalInterest+model.Amount);
            ((TextView)findViewById(R.id.tv_resultInterestTotal)).setText(formatter.format(totalInterest));
            ((TextView)findViewById(R.id.tv_resultTotal)).setText(formatter.format(total));
        }
        else
        {
            layout.removeView(findViewById(R.id.tv_result_number_of_month_row));

            ((TextView)findViewById(R.id.tv_resultFromDate)).setText(model.FormDate);
            ((TextView)findViewById(R.id.tv_resultToDate)).setText(model.ToDate);
            //so ngay gui
            ((TextView)findViewById(R.id.tv_resultNumberOfDate)).setText(model.NumberOfDays + " ngày");
            long totalInterest = Math.round(model.Amount*interestRate*model.NumberOfDays/(30*100));
            long total =Math.round(totalInterest+model.Amount);
            ((TextView)findViewById(R.id.tv_resultInterestTotal)).setText(formatter.format(totalInterest));
            ((TextView)findViewById(R.id.tv_resultTotal)).setText(formatter.format(total));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
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
