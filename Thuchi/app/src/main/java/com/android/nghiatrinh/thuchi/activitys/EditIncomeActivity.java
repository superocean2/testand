package com.android.nghiatrinh.thuchi.activitys;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.android.nghiatrinh.thuchi.R;
import com.android.nghiatrinh.thuchi.helpers.Helper;
import com.android.nghiatrinh.thuchi.model.Category;
import com.android.nghiatrinh.thuchi.model.Income;
import com.android.nghiatrinh.thuchi.model.IncomeCategoryAutocompleteListAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class EditIncomeActivity extends ActionBarActivity {
    Income editIncome=null;
    String incomeId=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_income_expense_layout);
        Intent intent = getIntent();
        String o = intent.getStringExtra("income");
        incomeId = intent.getStringExtra("incomeid");

        if (incomeId!=null)
        {
            editIncome = Income.findById(Income.class,Long.parseLong(incomeId));
        }
        if(editIncome==null){
            Intent editintent = new Intent(this,MainActivity.class);
            editintent.putExtra("display","income");
            finish();
            startActivity(editintent);
        }
        Calendar now = Calendar.getInstance();
        String dateNow = Helper.formatDate(now,false,getBaseContext());
        ((EditText) findViewById(R.id.edittext_income_date)).setText(dateNow);

        //autocomplete
        List<Category> list = Category.find(Category.class,"isincome=? and username=?","1",String.valueOf(Helper.getUsername(getBaseContext())));
        ArrayList<Category> listCategory = new ArrayList<>();
        for(Category item:list)
        {
            listCategory.add(item);
        }
        IncomeCategoryAutocompleteListAdapter adapter = new IncomeCategoryAutocompleteListAdapter(this,listCategory);
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.autocomplete_income_category);
        //how many character will trigger auto complete
        autoCompleteTextView.setThreshold(1);

        autoCompleteTextView.setAdapter(adapter);


        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView categoryId = (TextView)view.findViewById(R.id.hiddenCategoryID);
                ((TextView)findViewById(R.id.hiddenCategoryID)).setText(categoryId.getText().toString());

            }
        });
        if (editIncome!=null) {
            Category category = Category.find(Category.class,"categoryid=?", editIncome.getCategoryid()).get(0);
            if (category!=null) {
                ((AutoCompleteTextView) findViewById(R.id.autocomplete_income_category)).setText(category.getName());
                ((TextView)findViewById(R.id.hiddenCategoryID)).setText(editIncome.getCategoryid());
            }
            ((EditText)findViewById(R.id.edittext_income_amount)).setText(Helper.formatMoney(editIncome.getAmount()));
            if (editIncome.getDate()!=null) {
                ((EditText) findViewById(R.id.edittext_income_date)).setText(Helper.formatDate(editIncome.getDate(),getBaseContext()));
            }
            if (editIncome.getDescription()!=null) {
                ((EditText) findViewById(R.id.edittext_income_desc)).setText(editIncome.getDescription());
            }
        }
        if (o!=null) {
            Income income = new Gson().fromJson(o,Income.class);
            Category category = Category.find(Category.class,"categoryid=?", income.getCategoryid()).get(0);
            if (category!=null) {
                ((AutoCompleteTextView) findViewById(R.id.autocomplete_income_category)).setText(category.getName());
                ((TextView)findViewById(R.id.hiddenCategoryID)).setText(income.getCategoryid());
            }
            ((EditText)findViewById(R.id.edittext_income_amount)).setText(Helper.formatMoney(income.getAmount()));
            if (income.getDate()!=null) {
                ((EditText) findViewById(R.id.edittext_income_date)).setText(Helper.formatDate(income.getDate(),getBaseContext()));
            }
            if (income.getDescription()!=null) {
                ((EditText) findViewById(R.id.edittext_income_desc)).setText(income.getDescription());
            }
        }
        final EditText amountText = (EditText)findViewById(R.id.edittext_income_amount);

        final TextWatcher textWatcher = new TextWatcher() {
            boolean isTextChanged = false;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isTextChanged&&!s.toString().isEmpty())
                {
                    isTextChanged=true;
                    amountText.setTextKeepState(Helper.formatMoney(Double.parseDouble(s.toString().replace(",",""))));
                    //Selection.setSelection(amountText.getText(), amountText.getText().length());
                }
                else
                {
                    isTextChanged=false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        amountText.addTextChangedListener(textWatcher);
    }
    public void backButton(View view)
    {
        finish();
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
            ((EditText)findViewById(R.id.edittext_income_date)).setText(Helper.formatDate(year,monthOfYear,dayOfMonth,false,getBaseContext()));
        }
    }
    public void openChooseCategory(View view)
    {
        Intent  intent = new Intent(this,ListCategoryActivity.class);
        Income income = new Income();
        String amount=  ((EditText)findViewById(R.id.edittext_income_amount)).getText().toString().replace(",","");
        String date=  ((EditText)findViewById(R.id.edittext_income_date)).getText().toString();
        String desc=  ((EditText)findViewById(R.id.edittext_income_desc)).getText().toString();

        if (!amount.isEmpty()) {
            income.setAmount(Double.parseDouble(amount));
        }
        if (!date.isEmpty())
        {
            String[] arrDate = date.split("-");
            String ydate;
            if (Helper.getlanguageCode(this).equals("vi"))
            {
                ydate=arrDate[2]+"-"+arrDate[1]+"-"+arrDate[0];
            }
            else
            {
                ydate=arrDate[2]+"-"+arrDate[0]+"-"+arrDate[1];
            }
            income.setDate(ydate);
        }
        if (!desc.isEmpty())
        {
            income.setDescription(desc);
        }
        String json = new Gson().toJson(income);
        intent.putExtra("income",json);
        intent.putExtra("type","edit");
        intent.putExtra("kind","income");
        intent.putExtra("incomeid",incomeId);
        startActivity(intent);
        overridePendingTransition(R.transition.pull_in_right, R.transition.fade_out);
    }
    public void saveIncome(View view)
    {
        String amount=  ((EditText)findViewById(R.id.edittext_income_amount)).getText().toString().replace(",","");
        String date=  ((EditText)findViewById(R.id.edittext_income_date)).getText().toString();
        String[] arrDate = date.split("-");
        String ydate = arrDate[2]+"-"+arrDate[0]+"-"+arrDate[1];
        if (Helper.getlanguageCode(getBaseContext()).equals("vi"))
        {
            ydate=arrDate[2]+"-"+arrDate[1]+"-"+arrDate[0];
        }
        String desc=  ((EditText)findViewById(R.id.edittext_income_desc)).getText().toString();
        String name = ((EditText)findViewById(R.id.autocomplete_income_category)).getText().toString();
        String categoryId = ((TextView)findViewById(R.id.hiddenCategoryID)).getText().toString();
        Category c = Category.find(Category.class,"categoryid=?",categoryId).get(0);
        if (!validateInput(amount,name))return;

        if (!name.trim().isEmpty()&&!c.getName().equals(name.trim()))
        {
            Category category = new Category(name,true,Helper.getUsername(getBaseContext()), UUID.randomUUID().toString(),false,1);
            category.save();
            categoryId = Category.findWithQuery(Category.class,"select * from CATEGORY order by ID desc limit 1").get(0).getCategoryid();
        }

        editIncome.setAmount(Double.parseDouble(amount));
        editIncome.setDate(ydate);
        editIncome.setDescription(desc);
        editIncome.setCategoryid(categoryId);
        editIncome.setVersion(editIncome.getVersion()+1);
        editIncome.save();
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("display","income");
        intent.putExtra("bydate",ydate);

        startActivity(intent);
    }

    private boolean validateInput(String amount,String name)
    {
        if (amount.isEmpty())
        {
            Helper.validateRequired(this,R.string.amount);
            return false;
        }
        if (name.isEmpty())
        {
            Helper.validateRequired(this,R.string.category_name);
            return false;
        }
        return true;
    }
}
