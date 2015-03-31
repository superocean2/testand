package com.android.nghiatrinh.thuchi.activitys;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.android.nghiatrinh.thuchi.R;
import com.android.nghiatrinh.thuchi.helpers.Helper;
import com.android.nghiatrinh.thuchi.model.Category;
import com.android.nghiatrinh.thuchi.model.Expense;
import com.android.nghiatrinh.thuchi.model.IncomeCategoryAutocompleteListAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditExpenseActivity extends ActionBarActivity {
    Expense editExpense=null;
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
            editExpense = Expense.findById(Expense.class,Long.parseLong(incomeId));
        }
        if(editExpense==null){
            Intent editintent = new Intent(this,MainActivity.class);
            editintent.putExtra("display","expense");
            finish();
            startActivity(editintent);
        }
        Calendar now = Calendar.getInstance();
        String dateNow = Helper.formatDate(now,false);
        ((EditText) findViewById(R.id.edittext_income_date)).setText(dateNow);

        //autocomplete
        List<Category> list = Category.find(Category.class,"isincome=?","0");
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
        if (editExpense!=null) {
            Category category = Category.findById(Category.class, editExpense.getCategoryid());
            if (category!=null) {
                ((AutoCompleteTextView) findViewById(R.id.autocomplete_income_category)).setText(category.getName());
                ((TextView)findViewById(R.id.hiddenCategoryID)).setText(String.valueOf(editExpense.getCategoryid()));
            }
            ((EditText)findViewById(R.id.edittext_income_amount)).setText(Helper.formatMoney(editExpense.getAmount()));
            if (editExpense.getDate()!=null) {
                ((EditText) findViewById(R.id.edittext_income_date)).setText(Helper.formatDate(editExpense.getDate()));
            }
            if (editExpense.getDescription()!=null) {
                ((EditText) findViewById(R.id.edittext_income_desc)).setText(editExpense.getDescription());
            }
        }
        if (o!=null) {
            Expense income = new Gson().fromJson(o,Expense.class);
            Category category = Category.findById(Category.class, income.getCategoryid());
            if (category!=null) {
                ((AutoCompleteTextView) findViewById(R.id.autocomplete_income_category)).setText(category.getName());
                ((TextView)findViewById(R.id.hiddenCategoryID)).setText(String.valueOf(income.getCategoryid()));
            }
            ((EditText)findViewById(R.id.edittext_income_amount)).setText(Helper.formatMoney(income.getAmount()));
            if (income.getDate()!=null) {
                ((EditText) findViewById(R.id.edittext_income_date)).setText(Helper.formatDate(income.getDate()));
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
            ((EditText)findViewById(R.id.edittext_income_date)).setText(Helper.formatDate(year,monthOfYear,dayOfMonth,false));
        }
    }
    public void openChooseCategory(View view)
    {
        Intent  intent = new Intent(this,ListCategoryActivity.class);
        Expense income = new Expense();
        String amount=  ((EditText)findViewById(R.id.edittext_income_amount)).getText().toString().replace(",","");
        String date=  ((EditText)findViewById(R.id.edittext_income_date)).getText().toString();
        String desc=  ((EditText)findViewById(R.id.edittext_income_desc)).getText().toString();

        if (!amount.isEmpty()) {
            income.setAmount(Double.parseDouble(amount));
        }
        if (!date.isEmpty())
        {
            String[] arrDate = date.split("-");
            String ydate = arrDate[2]+"-"+arrDate[0]+"-"+arrDate[1];
            income.setDate(ydate);
        }
        if (!desc.isEmpty())
        {
            income.setDescription(desc);
        }
        String json = new Gson().toJson(income);
        intent.putExtra("income",json);
        intent.putExtra("type","edit");
        intent.putExtra("kind","expense");
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
        String desc=  ((EditText)findViewById(R.id.edittext_income_desc)).getText().toString();
        String name = ((EditText)findViewById(R.id.autocomplete_income_category)).getText().toString();
        String categoryId = ((TextView)findViewById(R.id.hiddenCategoryID)).getText().toString();
        Category c = Category.findById(Category.class,Long.parseLong(categoryId));
        if (!validateInput(amount,name))return;

        if (!name.trim().isEmpty()&&!c.getName().equals(name.trim()))
        {
            Category category = new Category(name,false,-1);
            category.save();
            categoryId = Category.findWithQuery(Category.class,"select ID from CATEGORY order by ID desc limit 1").get(0).getId().toString();
        }

        editExpense.setAmount(Double.parseDouble(amount));
        editExpense.setDate(ydate);
        editExpense.setDescription(desc);
        editExpense.setCategoryid(Long.parseLong(categoryId));
        editExpense.setHour("00:00:00");
        editExpense.setUserid(-1);
        editExpense.save();
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("display","expense");
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
