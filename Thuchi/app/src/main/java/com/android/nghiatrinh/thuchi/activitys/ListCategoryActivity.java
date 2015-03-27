package com.android.nghiatrinh.thuchi.activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.nghiatrinh.thuchi.R;
import com.android.nghiatrinh.thuchi.model.Expense;
import com.android.nghiatrinh.thuchi.model.Income;
import com.android.nghiatrinh.thuchi.model.Category;
import com.android.nghiatrinh.thuchi.model.IncomeCategoryListAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ListCategoryActivity extends ActionBarActivity {

    ArrayList<Category> arrayList = new ArrayList<>();
    Income income = null;
    String type=null;
    String incomeId=null;
    String kind = null;
    String categoryId=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_layout);
        Intent intent = getIntent();
        String o = intent.getStringExtra("income");
        incomeId = intent.getStringExtra("incomeid");

        type = intent.getStringExtra("type");
        kind = intent.getStringExtra("kind");

        if (o!=null) {
            income = new Gson().fromJson(o,Income.class);
        }
        else
        {
            income = new Income();
        }
        ListView listView = (ListView)findViewById(R.id.listview_list_income_category);
        List<Category> list;
        if (kind.equals("income")) {
            list = Category.find(Category.class,"isincome=?","1");
        }
        else
        {
            //expense
            list = Category.find(Category.class,"isincome=?","0");
        }
        for (Category incomeCategory:list)
        {
            arrayList.add(incomeCategory);
        }
        IncomeCategoryListAdapter adapter = new IncomeCategoryListAdapter(this,arrayList);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView categoryId = (TextView)view.findViewById(R.id.hiddenCategoryID);
                income.setCategoryid(Long.parseLong(categoryId.getText().toString()));
                String json = new Gson().toJson(income);
                Intent intent = null;
                if (type.equals("add"))
                {
                    if (kind.equals("income")) {
                        intent = new Intent(getBaseContext(), AddNewIncomeActivity.class);
                    }
                    else
                    {
                        intent = new Intent(getBaseContext(), AddNewExpenseActivity.class);
                    }
                }
                else
                {
                    if (kind.equals("income")) {
                        intent = new Intent(getBaseContext(), EditIncomeActivity.class);
                    }
                    else
                    {
                        intent = new Intent(getBaseContext(), EditExpenseActivity.class);
                    }

                    if (incomeId!=null)
                    {
                        intent.putExtra("incomeid",incomeId);
                    }
                }
                intent.putExtra("income",json);

                finish();
                startActivity(intent);
                overridePendingTransition(R.transition.pull_in_left, R.transition.push_out_right);
            }
        });
    }
    public void openAddNewCategory(View view)
    {
        Intent intent;
        if (kind.equals("income"))
        {
            intent=new Intent(this,AddNewCategoryActivity.class);
            String json = new Gson().toJson(income);
            intent.putExtra("income",json);
            intent.putExtra("type",type);
            intent.putExtra("kind",kind);
            if(incomeId!=null)
            {
                intent.putExtra("incomeid",incomeId);
            }
        }
        else
        {
            intent=new Intent(this,AddNewCategoryActivity.class);
            String json = new Gson().toJson(income);
            intent.putExtra("income",json);
            intent.putExtra("type",type);
            intent.putExtra("kind",kind);
            if(incomeId!=null)
            {
                intent.putExtra("incomeid",incomeId);
            }
        }
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.transition.pull_in_left, R.transition.push_out_right);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.clearHeader();
        menu.add(0, 0, 0, R.string.edit);
        menu.add(0, 1, 1, R.string.delete);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        final Category category = arrayList.get(info.position);
        switch (item.getOrder())
        {
            case 0:
                Intent intent;
                intent=new Intent(this,EditCategoryActivity.class);
                String json = new Gson().toJson(income);
                intent.putExtra("income", json);
                intent.putExtra("type",type);
                intent.putExtra("kind",kind);
                intent.putExtra("categoryid",category.getId());
                if(incomeId!=null)
                {
                    intent.putExtra("incomeid",incomeId);
                }
                finish();
                startActivity(intent);
                return true;
            case 1:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.confirm_delete)
                        .setIcon(R.drawable.ic_action_warning)
                        .setTitle(R.string.delete)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //check before delete
                                List<Income> incomes = Income.find(Income.class, "categoryid=?", category.getId().toString());
                                List<Expense> expenses = Expense.find(Expense.class, "categoryid=?", category.getId().toString());
                                if (incomes.isEmpty() && expenses.isEmpty()) {
                                    category.delete();
                                } else {
                                    Toast.makeText(getBaseContext(),R.string.cannot_delete_category,Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismiss();
                                finish();
                                startActivity(getIntent());
                                overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
                            }
                        })
                        .setNegativeButton(R.string.cancel, null).show();
                return true;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_list_category_menu,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.create:
                Intent intent = new Intent(this,AddNewCategoryActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
