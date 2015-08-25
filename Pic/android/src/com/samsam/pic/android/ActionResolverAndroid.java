package com.samsam.pic.android;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;


import com.samsam.pic.ActionResolver;


/**
 * Created by NghiaTrinh on 7/20/2015.
 */
public class ActionResolverAndroid implements ActionResolver {
    Handler handler;
    Context context;


    public ActionResolverAndroid(Context context) {
        handler = new Handler();
        this.context = context;
    }

    @Override
    public void showEndActivity() {
        context.startActivity(new Intent(context,EndActivity.class));
    }
}
