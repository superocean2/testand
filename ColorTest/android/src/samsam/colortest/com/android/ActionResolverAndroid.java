package samsam.colortest.com.android;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import samsam.colortest.com.ActionResolver;
import samsam.colortest.com.ColorTest;

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
    public void showSmallGoogleAds() {
        AdView adView = (AdView)((Activity)context).findViewById(R.id.bottom);
        adView.setVisibility(View.VISIBLE);
    }
}