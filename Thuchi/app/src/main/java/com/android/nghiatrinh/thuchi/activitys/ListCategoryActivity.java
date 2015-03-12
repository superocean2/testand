package com.android.nghiatrinh.thuchi.activitys;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.android.nghiatrinh.thuchi.R;
import com.android.nghiatrinh.thuchi.model.IncomeCategory;
import com.android.nghiatrinh.thuchi.model.IncomeCategoryListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListCategoryActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_layout);
        ListView listView = (ListView)findViewById(R.id.listview_list_income_category);
        List<IncomeCategory> list = IncomeCategory.listAll(IncomeCategory.class);
        ArrayList<IncomeCategory> arrayList = new ArrayList<>();
        for (IncomeCategory incomeCategory:list)
        {
            arrayList.add(incomeCategory);
        }
        IncomeCategoryListAdapter adapter = new IncomeCategoryListAdapter(this,arrayList);
        listView.setAdapter(adapter);
    }
    public void openAddNewCategory(View view)
    {
        Intent intent = new Intent(this,AddNewIncomeCategoryActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.transition.pull_in_left, R.transition.push_out_right);
    }
}
