package com.samsam.testlearning;

import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ConnectionRequestListener;

/**
 * Created by NghiaTrinh on 6/24/2016.
 */
public class ConnectionGame implements ConnectionRequestListener {
    @Override
    public void onConnectDone(ConnectEvent event) {
        if (event.getResult() == WarpResponseResultCode.SUCCESS) {
            System.out.println("yipee I have connected");
        }
    }

    @Override
    public void onDisconnectDone(ConnectEvent event) {
        System.out.println("On Disconnected invoked");
    }

    @Override
    public void onInitUDPDone(byte b) {

    }
}