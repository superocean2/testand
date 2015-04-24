package com.android.nghiatrinh.thuchi.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.nghiatrinh.thuchi.R;
import com.android.nghiatrinh.thuchi.helpers.Helper;
import com.android.nghiatrinh.thuchi.model.Category;

import java.util.List;

public class EditCategoryActivity extends ActionBarActivity {
    Category incomeCategory =null;
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

        long id =Long.parseLong(intent.getStringExtra("categoryid"));
        incomeCategory = Category.findById(Category.class, id);
        if (incomeCategory!=null) {
            EditText name = (EditText) findViewById(R.id.edittext_income_category_name);
            name.setText(incomeCategory.getName());
            name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus)
                    {
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    }
                }
            });
        }

    }
    public void backButton(View view)
    {
        finish();
    }
    public void saveIncomeCategory(View view)
    {
        EditText editText = (EditText)findViewById(R.id.edittext_income_category_name);
        String name = editText.getText().toString();
        if (name.isEmpty())
        {
            Helper.validateRequired(this, "Category name");
            return;
        }
        List<Category> category = Category.find(Category.class,"name=?",name);
        if (category.size()>0)
        {
            Toast.makeText(this, R.string.exist_item_inform, Toast.LENGTH_SHORT).show();
        }
        else
        {
            if (incomeCategory!=null)
            {
                incomeCategory.setName(editText.getText().toString());
                incomeCategory.setVersion(incomeCategory.getVersion()+1);
                incomeCategory.save();
            }
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
