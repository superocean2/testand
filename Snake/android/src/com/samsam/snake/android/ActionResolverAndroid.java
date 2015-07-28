package com.samsam.snake.android;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.samsam.snake.ActionResolver;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.internal.zzhl.runOnUiThread;

/**
 * Created by NghiaTrinh on 7/20/2015.
 */
public class ActionResolverAndroid implements ActionResolver {
    Handler handler;
    Context context;
    private ShareDialog shareDialog;
    private boolean canPresentShareDialog;
    private InterstitialAd interstitialAd;

    public ActionResolverAndroid(Context context) {
        handler = new Handler();
        this.context = context;
        FacebookSdk.sdkInitialize(context);
        canPresentShareDialog = ShareDialog.canShow(
                ShareLinkContent.class);
        shareDialog = new ShareDialog((Activity)context);


        //google ads
        interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(context.getString(R.string.banner_ad_unit_id));
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

    @Override
    public void showFbShare(final String title, final String desc, final String url, final String imageUrl) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentTitle(title)
                        .setContentDescription(desc)
                        .setContentUrl(Uri.parse(url))
                        .setImageUrl(Uri.parse(imageUrl))
                        .build();
                if (canPresentShareDialog) {
                    shareDialog.show(linkContent);
                }
            }
        });
    }

    @Override
    public void showOrLoadInterstital() {
        try {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                    }
                    else {
                        AdRequest interstitialRequest = new AdRequest.Builder().build();
                        interstitialAd.loadAd(interstitialRequest);
                    }
                }
            });
        } catch (Exception e) {
        }
    }

    @Override
    public String getWorldScore(String url) {
        String urlGet=url+"/home/GetData";
        String key = "dfamd175azxhm5900075ips1a82";
        List<NameValuePair> pairs = new ArrayList<>();

        pairs.add(new BasicNameValuePair("key",key));
        HttpResponse response= Helpers.getData(urlGet,pairs);
        if (response.getStatusLine().getStatusCode()==200) {
            try {
                HttpEntity resEntityGet = response.getEntity();
                if (resEntityGet != null) {
                    return EntityUtils.toString(resEntityGet);
                }
            }
            catch (Exception e)
            {

            }
        }

        return "0";
    }

    @Override
    public boolean postWorldScore(String url,int score,String key) {
        String urlPost=url+"/home/PostData";
        List<NameValuePair> pairs = new ArrayList<>();

        pairs.add(new BasicNameValuePair("score",String.valueOf(score)));
        pairs.add(new BasicNameValuePair("key",key));

        HttpResponse response= Helpers.postData(urlPost, pairs);
        if (response.getStatusLine().getStatusCode()==200)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
