package com.android.nghiatrinh.thuchi.activitys;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.nghiatrinh.thuchi.R;
import com.android.nghiatrinh.thuchi.dialogs.MonthPickerDialog;
import com.android.nghiatrinh.thuchi.dialogs.YearPickerDialog;
import com.android.nghiatrinh.thuchi.fragments.DetailOverviewFragment;
import com.android.nghiatrinh.thuchi.fragments.IncomeFragment;
import com.android.nghiatrinh.thuchi.fragments.OverviewFragment;
import com.android.nghiatrinh.thuchi.fragments.ExpenseFragment;
import com.android.nghiatrinh.thuchi.helpers.Helper;
import com.android.nghiatrinh.thuchi.helpers.SwipeHelper;
import com.android.nghiatrinh.thuchi.model.Category;
import com.android.nghiatrinh.thuchi.model.Setting;
import com.android.nghiatrinh.thuchi.model.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class MainActivity extends ActionBarActivity{
    String bydate=null;
    String bymonth=null;
    String byyear=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Boolean isFirstTime = Category.listAll(Category.class).isEmpty();
        if (isFirstTime) {
            //initialize default categories
            initializeCategories();
            Intent intent = new Intent(getBaseContext(),WebSyncActivity.class);
            finish();
            startActivity(intent);
        }
        Resources res = getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale(Helper.getlanguageCode(this));
        res.updateConfiguration(conf, dm);

        setContentView(R.layout.main_layout);
        RelativeLayout main = (RelativeLayout)findViewById(R.id.main);

        OverviewFragment overviewFragment = new OverviewFragment();
        IncomeFragment incomeFragment = new IncomeFragment();
        ExpenseFragment expenseFragment = new ExpenseFragment();
        Intent intent = getIntent();
        String display = intent.getStringExtra("display");

        bydate = intent.getStringExtra("bydate");
        bymonth = intent.getStringExtra("bymonth");
        byyear = intent.getStringExtra("byyear");

        if (bydate!=null)
        {
            Bundle bundle = new Bundle();
            bundle.putString("bydate", bydate);
            overviewFragment.setArguments(bundle);
            incomeFragment.setArguments(bundle);
            expenseFragment.setArguments(bundle);
        }
        if (bymonth!=null)
        {
            Bundle bundle = new Bundle();
            bundle.putString("bymonth", bymonth);
            overviewFragment.setArguments(bundle);
            incomeFragment.setArguments(bundle);
            expenseFragment.setArguments(bundle);
        }
        if (byyear!=null)
        {
            Bundle bundle = new Bundle();
            bundle.putString("byyear", byyear);
            overviewFragment.setArguments(bundle);
            incomeFragment.setArguments(bundle);
            expenseFragment.setArguments(bundle);
        }
        if (display==null) {
            getFragmentManager().beginTransaction().replace(R.id.contents, overviewFragment).commit();
            return;
        }

        switch (display)
        {
            case "overview":
                getFragmentManager().beginTransaction().replace(R.id.contents, overviewFragment).commit();
                return;
            case "income":
                getFragmentManager().beginTransaction().replace(R.id.contents, incomeFragment).commit();
                return;
            case "expense":
                getFragmentManager().beginTransaction().replace(R.id.contents, expenseFragment).commit();
                return;
            default:
                getFragmentManager().beginTransaction().replace(R.id.contents, overviewFragment).commit();
        }
    }

    private void initializeCategories()
    {
        String[] incomeCategories = getListCategoriesIncome();
        String[] expenseCategories = getListCategoriesExpense();
        for (int i=0;i<incomeCategories.length;i++)
        {
            Category category=new Category(incomeCategories[i],true,Helper.getUsername(this), UUID.randomUUID().toString(),false,1);
            category.save();
        }
        for (int i=0;i<expenseCategories.length;i++)
        {
            Category category=new Category(expenseCategories[i],false,Helper.getUsername(this), UUID.randomUUID().toString(),false,1);
            category.save();
        }
    }
    private String[] getListCategoriesIncome()
    {
        String[] categoriesName = {"Luong","Thuong","Lam them"};

        return  categoriesName;
    }
    private String[] getListCategoriesExpense()
    {
        String[] categoriesName = {"Di cho","Sieu thi","An sang/trua/chieu","Uong nuoc/cafe","Dien","Nuoc","Internet","Nap DTDD","Gas","Xang xe","Sua xe","Gui xe","Rua xe","Taxi/Thue xe","Quan ao"
                                    ,"Giay dep","Phu kien thoi trang","Giai tri","Du lich","Lam dep","Xem phim","Sua cho con","Ta cho con","Hoc phi","Sach vo","Do choi","Tieu vat"
                                    ,"Dam cuoi","Bieu/Tang/cho","Kham benh","Thuoc men","The thao","Mua sam do dac","Sua nha","Thue nha"};

        return  categoriesName;
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
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (id==R.id.action_sync)
        {
            Intent intent = new Intent(this,WebSyncActivity.class);
            startActivity(intent);
            return true;
        }
        if (id==R.id.action_logout)
        {
            User.deleteAll(User.class);
            Intent intent = new Intent(this,Login.class);
            startActivity(intent);
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
        ExpenseFragment spendFragment = new ExpenseFragment();
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

    public void openOptionMenu(View view)
    {
        Helper.showMenu(this);
    }
    private class SetDate implements DatePickerDialog.OnDateSetListener
    {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String date = Helper.formatDate(year,monthOfYear,dayOfMonth,true,getBaseContext());
            Fragment fragment = getFragmentManager().findFragmentById(R.id.contents);
            Bundle bundle = new Bundle();
            bundle.putString("bydate",date);
            if (fragment instanceof IncomeFragment)
            {
                IncomeFragment incomeFragment = new IncomeFragment();
                incomeFragment.setArguments(bundle);
                //getFragmentManager().popBackStack();
                getFragmentManager().beginTransaction().replace(R.id.contents, incomeFragment).commit();
            }
            if (fragment instanceof  OverviewFragment|| fragment instanceof DetailOverviewFragment)
            {
                DetailOverviewFragment overviewFragment = new DetailOverviewFragment();
                overviewFragment.setArguments(bundle);
                //getFragmentManager().popBackStack();
                getFragmentManager().beginTransaction().replace(R.id.contents, overviewFragment).commit();
            }
            if (fragment instanceof ExpenseFragment)
            {
                ExpenseFragment expenseFragment = new ExpenseFragment();
                expenseFragment.setArguments(bundle);
                //getFragmentManager().popBackStack();
                getFragmentManager().beginTransaction().replace(R.id.contents, expenseFragment).commit();
            }
        }
    }

    
}

