package com.android.nghiatrinh.thuchi.activitys;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.nghiatrinh.thuchi.R;
import com.android.nghiatrinh.thuchi.helpers.Helper;
import com.android.nghiatrinh.thuchi.model.Category;
import com.android.nghiatrinh.thuchi.model.Income;

import java.util.List;

public class AddNewCategoryActivity extends ActionBarActivity {
    String income = null;
    String type=null;
    String kind = null;
    String incomeId=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_category_layout);
        Intent intent = getIntent();
        income = intent.getStringExtra("income");
        type = intent.getStringExtra("type");
        kind = intent.getStringExtra("kind");
        incomeId = intent.getStringExtra("incomeid");
    }

    public void saveIncomeCategory(View view)
    {
        EditText editText = (EditText)findViewById(R.id.edittext_income_category_name);
        String name = editText.getText().toString();
        if (name.isEmpty())
        {
            Helper.validateRequired(this,"Category name");
            return;
        }
        List<Category> category = Category.find(Category.class,"name=?",name);
        if (category.size()>0)
        {
            Toast.makeText(this,R.string.exist_item_inform,Toast.LENGTH_SHORT).show();
        }
        else
        {
            boolean isIncome = kind.equals("income");
            Category incomeCategory =new Category(name,isIncome,-1);
            incomeCategory.save();
            Intent intent=new Intent(this,ListCategoryActivity.class);
            intent.putExtra("income",income);
            intent.putExtra("type",type);
            intent.putExtra("kind",kind);
            if(incomeId!=null)
            {
                intent.putExtra("incomeid",incomeId);
            }
            startActivity(intent);
            finish();
        }
    }
}
