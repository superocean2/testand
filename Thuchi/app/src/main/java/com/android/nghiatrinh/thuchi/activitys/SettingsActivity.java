package com.android.nghiatrinh.thuchi.activitys;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.nghiatrinh.thuchi.R;
import com.android.nghiatrinh.thuchi.helpers.Helper;
import com.android.nghiatrinh.thuchi.model.Setting;

import java.util.List;

public class SettingsActivity extends ActionBarActivity {
    private  static String languageCode = "en";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        RadioButton english = (RadioButton)findViewById(R.id.englishCheckbox);
        RadioButton vietnamese = (RadioButton)findViewById(R.id.vietnameseCheckbox);
        String languageCode = Helper.getlanguageCode(this);
        if (languageCode.equals("vi"))
        {
            vietnamese.setChecked(true);
        }
        else
        {
            english.setChecked(true);
        }
    }

    public void onSettingsLanguageChecked(View view)
    {
        boolean checked = ((RadioButton)view).isChecked();
        switch (view.getId())
        {
            case R.id.englishCheckbox:
                if(checked)
                {
                    languageCode="en";
                }
                break;
            case R.id.vietnameseCheckbox:
                if(checked)
                {
                    languageCode="vi";
                }
                break;
        }
    }
    public void saveSettings(View view)
    {
        Helper.setlanguageCode(languageCode,this);
        Intent intent = new Intent(this,MainActivity.class);
        finish();
        startActivity(intent);
    }
    public  void cancelSettings(View view)
    {
        finish();
    }
}
