package com.example.nghiatrinh.tinhls;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
        ((TextView)findViewById(R.id.tv_resultFromDate)).setText(model.FormDate);
        ((TextView)findViewById(R.id.tv_resultToDate)).setText(model.ToDate);
        //so ngay gui
        String[] arrFromDate = model.FormDate.split("-");
        String[] arrToDate = model.ToDate.split("-");
        GregorianCalendar fromDate = new GregorianCalendar(Integer.parseInt(arrFromDate[2]),Integer.parseInt(arrFromDate[1])-1,Integer.parseInt(arrFromDate[0]));
        GregorianCalendar toDate = new GregorianCalendar(Integer.parseInt(arrToDate[2]),Integer.parseInt(arrToDate[1])-1,Integer.parseInt(arrToDate[0]));
        int days = (int)((toDate.getTimeInMillis()-fromDate.getTimeInMillis())/(1000*60*60*24));
        ((TextView)findViewById(R.id.tv_resultNumberOfDate)).setText(Integer.toString(days) + " ngày");

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
