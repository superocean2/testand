package com.android.nghiatrinh.thuchi.activitys;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.nghiatrinh.thuchi.R;
import com.android.nghiatrinh.thuchi.helpers.Helper;
import com.android.nghiatrinh.thuchi.model.Category;
import com.android.nghiatrinh.thuchi.model.Expense;
import com.android.nghiatrinh.thuchi.model.Income;
import com.android.nghiatrinh.thuchi.model.IncomeExpenseJson;
import com.android.nghiatrinh.thuchi.model.User;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebSyncActivity extends ActionBarActivity {

    TextView waiting;
    boolean isPostingDone=false;
    //final static String url="http://api.192.168.1.95.xip.io";
    final static String url="http://www.nghia.somee.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sync_layout);
        waiting=(TextView)findViewById(R.id.textView1);
        new AsyncPOST().execute();
        waiting.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isPostingDone)
                {
                    isPostingDone=false;
                    new AsyncGET().execute();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private class  AsyncGET extends AsyncTask<Void,Void,Void>
    {
        boolean success = false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //this method will be running on UI thread
            waiting.setText(R.string.getting);
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //this method will be running on UI thread
            if(success)
            {
                waiting.setText(R.string.done);
            }
            else
            {
                Toast.makeText(getBaseContext(),R.string.error_update,Toast.LENGTH_LONG).show();
            }

            Intent intent = new Intent(getBaseContext(),MainActivity.class);
            finish();
            startActivity(intent);
        }
        @Override
        protected Void doInBackground(Void... params) {
            String urlGet=url+"/home/GetData";
            String username = Helper.getUsername(getBaseContext());
            List<NameValuePair> pairs = new ArrayList<>();

            pairs.add(new BasicNameValuePair("username",username));
            HttpResponse response= Helper.getData(urlGet,pairs);
            if (response.getStatusLine().getStatusCode()==200)
            {
                try {
                    HttpEntity resEntityGet = response.getEntity();
                    if (resEntityGet != null) {
                        String responseText = EntityUtils.toString(resEntityGet);

//                        IncomeExpenseJson incomeExpenseJson = new Gson().fromJson(responseText,IncomeExpenseJson.class);
//                        if (incomeExpenseJson.Response.equals("ok"))
//                        {
//                            //update database;
//                            Income.deleteAll(Income.class);
//                            Expense.deleteAll(Expense.class);
//                            Category.deleteAll(Category.class);
//
//                            for (Category category:incomeExpenseJson.categories)
//                            {
//                                Category category1 = new Category(category.getName(),category.isincome(),category.getUsername(),category.getCategoryid(),category.isdelete(),category.getVersion());
//                                category1.save();
//                            }
//                            for(Income income:incomeExpenseJson.incomes)
//                            {
//                                Income income1 = new Income(income.getCategoryid(),income.getAmount(),income.getDate(),income.getHour(),income.getUsername(),income.getDescription(),income.getIncomeid(),income.isdelete(),income.getVersion());
//                                income1.save();
//                            }
//                            for(Expense expense:incomeExpenseJson.expenses)
//                            {
//                                Expense expense1 = new Expense(expense.getCategoryid(),expense.getAmount(),expense.getDate(),expense.getHour(),expense.getUsername(),expense.getDescription(),expense.getExpenseid(),expense.isdelete(),expense.getVersion());
//                                expense1.save();
//                            }
//
//                            success=true;
//                        }
//                        else
//                        {
//                            success=false;
//                        }
                    }
                }
                catch (IOException e){}
            }
            else
            {
                //register error
                success=false;
            }
            return  null;
        }
    }
    private class  AsyncPOST extends AsyncTask<Void,Void,Void>
    {
        boolean success = false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //this method will be running on UI thread
            waiting.setText(R.string.posting);
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //this method will be running on UI thread
            if(success)
            {
                isPostingDone=true;
                waiting.setText(R.string.getting);
            }
            else
            {
                Toast.makeText(getBaseContext(),R.string.error_update,Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                finish();
                startActivity(intent);
            }

        }
        @Override
        protected Void doInBackground(Void... params) {
            String urlPost=url+"/home/PostData";
            List<NameValuePair> pairs = new ArrayList<>();
            String incomeJson=new Gson().toJson(Income.listAll(Income.class));
            String expenseJson=new Gson().toJson(Expense.listAll(Expense.class));
            String categoryJson=new Gson().toJson(Category.listAll(Category.class));
            String username = Helper.getUsername(getBaseContext());

            pairs.add(new BasicNameValuePair("income",incomeJson));
            pairs.add(new BasicNameValuePair("expense",expenseJson));
            pairs.add(new BasicNameValuePair("category",categoryJson));
            pairs.add(new BasicNameValuePair("username",username));

            HttpResponse response= Helper.postData(urlPost, pairs);
            if (response.getStatusLine().getStatusCode()==200)
            {
                //register success
                success=true;
            }
            else
            {
                //register error
                success=false;
            }
            return  null;
        }
    }
}
