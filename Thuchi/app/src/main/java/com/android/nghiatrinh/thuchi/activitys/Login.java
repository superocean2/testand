package com.android.nghiatrinh.thuchi.activitys;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.nghiatrinh.thuchi.R;
import com.android.nghiatrinh.thuchi.helpers.Helper;
import com.android.nghiatrinh.thuchi.model.User;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Login extends ActionBarActivity {

    String username;
    String password;
    final static String url="http://api.192.168.1.95.xip.io";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        List<User> users = User.listAll(User.class);
        if (!users.isEmpty())
        {
            Helper.setUsername(users.get(0).getUsername(), getBaseContext());
            Intent intent = new Intent(getBaseContext(),MainActivity.class);
            finish();
            startActivity(intent);
        }
    }
    public void doLogin(View view)
    {
        username=((EditText)findViewById(R.id.edittext_username)).getText().toString().trim();
        password =((EditText)findViewById(R.id.edittext_password)).getText().toString().trim();
        if(!username.isEmpty()&&!password.isEmpty()) {
            new AsyncLogin().execute();
        }
    }
    public void doRegister(View view)
    {
        username=((EditText)findViewById(R.id.edittext_username)).getText().toString().trim();
        password =((EditText)findViewById(R.id.edittext_password)).getText().toString().trim();
        if(!username.isEmpty()&&!password.isEmpty()) {
            new AsyncRegister().execute();
        }
    }
    private class  AsyncLogin extends AsyncTask<Void,Void,Void>
    {
        boolean success = false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //this method will be running on UI thread
            Toast.makeText(getBaseContext(),"waiting",Toast.LENGTH_SHORT).show();
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //this method will be running on UI thread
            if(success)
            {
                username=((EditText)findViewById(R.id.edittext_username)).getText().toString().trim();
                password =((EditText)findViewById(R.id.edittext_password)).getText().toString().trim();
                //check if user exist
                List<User> users = User.find(User.class,"username=?",username);
                if (users.isEmpty())
                {
                    User user = new User(username,password);
                    user.save();
                    List<User> usernews = User.find(User.class,"username=?",username);
                    if (!usernews.isEmpty())
                    {
                        Helper.setUsername(usernews.get(0).getUsername(), getBaseContext());
                    }
                }
                else
                {
                    Helper.setUsername(users.get(0).getUsername(), getBaseContext());
                }
                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                finish();
                startActivity(intent);
            }
            else
            {
                Toast.makeText(getBaseContext(),"Username or password is not correct",Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected Void doInBackground(Void... params) {
            String urlLogin=url+"/home/Login";
            List<NameValuePair> pairs = new ArrayList<>();
            username=((EditText)findViewById(R.id.edittext_username)).getText().toString().trim();
            password =((EditText)findViewById(R.id.edittext_password)).getText().toString().trim();
            pairs.add(new BasicNameValuePair("username",username));
            pairs.add(new BasicNameValuePair("password", password));
            HttpResponse response= Helper.getData(urlLogin, pairs);
            if (response.getStatusLine().getStatusCode()==200)
            {
                try {
                    HttpEntity resEntityGet = response.getEntity();
                    if (resEntityGet != null) {
                        String responseText = EntityUtils.toString(resEntityGet);
                        if (responseText.equals("ok"))
                        {
                            success=true;
                        }
                        else
                        {
                            success=false;
                        }
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
    private class  AsyncRegister extends AsyncTask<Void,Void,Void>
    {
        boolean success = false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //this method will be running on UI thread
            Toast.makeText(getBaseContext(),"waiting...",Toast.LENGTH_SHORT).show();
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //this method will be running on UI thread
            if(success)
            {
                User user = new User(username,password);
                user.save();
                List<User> usernews = User.find(User.class,"username=?",username);
                if (!usernews.isEmpty())
                {
                    Helper.setUsername(usernews.get(0).getUsername(), getBaseContext());
                }
                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                finish();
                startActivity(intent);
            }
            else
            {
                Toast.makeText(getBaseContext(),"Lost connection or username is exists",Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected Void doInBackground(Void... params) {
            String urlRegister= url+"/home/Register";
            List<NameValuePair> pairs = new ArrayList<>();
            username=((EditText)findViewById(R.id.edittext_username)).getText().toString().trim();
            password =((EditText)findViewById(R.id.edittext_password)).getText().toString().trim();
            pairs.add(new BasicNameValuePair("username",username));
            pairs.add(new BasicNameValuePair("password", password));
            HttpResponse response= Helper.postData(urlRegister, pairs);
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

