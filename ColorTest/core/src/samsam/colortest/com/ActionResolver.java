package samsam.colortest.com;

import java.awt.Dialog;

/**
 * Created by NghiaTrinh on 7/20/2015.
 */
public interface ActionResolver {
     void showToast(CharSequence text);
     void showFbShare(String title,String desc,String url,String imageUrl);
     void showOrLoadInterstital();
     void showSmallGoogleAds();
}
