package com.samsam.snake;

/**
 * Created by NghiaTrinh on 7/20/2015.
 */
public interface ActionResolver {
     void showToast(CharSequence text);
     void showFbShare(String title, String desc, String url, String imageUrl);
     void showOrLoadInterstital();
     boolean postWorldScore(String url,int score,String key);
     void showWaitingDialog();
}
