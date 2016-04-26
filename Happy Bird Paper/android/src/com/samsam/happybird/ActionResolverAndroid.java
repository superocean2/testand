package com.samsam.happybird;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;


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
    public void showToast(final CharSequence text) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void showAds()
    {
        final Activity activity = (Activity)context;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View adsView = activity.findViewById(R.id.adjust_height);
                adsView.setVisibility(View.VISIBLE);
            }
        });
    }

    public void hideAds()
    {
        final Activity activity = (Activity)context;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View adsView = activity.findViewById(R.id.adjust_height);
                adsView.setVisibility(View.INVISIBLE);
            }
        });
    }
}
