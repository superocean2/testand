package com.android.nghiatrinh.thuchi.activitys;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.nghiatrinh.thuchi.R;
import com.android.nghiatrinh.thuchi.helpers.Constants;
import com.android.nghiatrinh.thuchi.helpers.Helper;
import com.android.nghiatrinh.thuchi.helpers.SyncService;

import java.io.File;
import java.io.IOException;


public class Sync extends ActionBarActivity {
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private TextView textView;
    private Handler handler = new Handler();
    private static int REQUEST_ENABLE_BT=3;
    BluetoothAdapter mBluetoothAdapter =null;
    private String mConnectedDeviceName = null;
    private SyncService syncService=null;
    private static final int REQUEST_CONNECT_DEVICE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sync_layout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        textView = (TextView) findViewById(R.id.textView1);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this,"Bluetooth not support in this device",Toast.LENGTH_SHORT).show();
            finish();
        }
        else
        {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
            else
            {
                //start service
                setupSync();
            }
        }

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (syncService != null) {
            syncService.stop();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (syncService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (syncService.getState() == SyncService.STATE_NONE) {
                // Start the Bluetooth chat services
                syncService.start();
            }
        }
    }
    private void setupSync()
    {
        syncService=new SyncService(this,mHandler);
        //ensureDiscoverable();
        Intent serverIntent = new Intent(this, DeviceListActivity.class);
        Toast.makeText(this, "go to intent", Toast.LENGTH_SHORT).show();
        startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
//        File file = getDatabasePath("income_expense.db");
//        sendData(file);
    }
    /**
     * Makes this device discoverable.
     */
    private void ensureDiscoverable() {
        if (mBluetoothAdapter.getScanMode() !=
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 600);
           startActivity(discoverableIntent);
        }
    }
    private void sendData(File file)
    {
        if (syncService.getState() != SyncService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            syncService.write(Helper.readBytesFromFile(file));
        }
        catch (IOException e)
        {

        }
    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case SyncService.STATE_CONNECTED:
                            String s = getString(R.string.connected);
                            s+="( "+mConnectedDeviceName+" )";
                            textView.setText(s);
                            break;
                        case SyncService.STATE_CONNECTING:
                            String s1 = getString(R.string.connecting);
                            s1+="( "+mConnectedDeviceName+" )";
                            textView.setText(s1);
                            break;
                        case SyncService.STATE_LISTEN:
                        case SyncService.STATE_NONE:
                            textView.setText(R.string.waiting);
                            break;
                    }
                    break;
                case Constants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;

                    break;
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    File readFile = getFilesDir();
                    try {
                        Helper.writeBytesToFile(readFile, readBuf);
                        Toast.makeText(getBaseContext(),"database file size:"+String.valueOf(readFile.length()),Toast.LENGTH_LONG);
                    }
                    catch (IOException e)
                    {

                    }

                    break;
                case Constants.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);

                    break;
                case Constants.MESSAGE_TOAST:
                    Toast.makeText(getBaseContext(), msg.getData().getString(Constants.TOAST),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private void connectDevice(Intent data) {
        // Get the device MAC address
        String address = data.getExtras()
                .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        syncService.connect(device);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==REQUEST_ENABLE_BT)
        {
            if (resultCode==RESULT_OK)
            {
                setupSync();
                // Start long running operation in a background thread
//                new Thread(new Runnable() {
//                    public void run() {
//                        while (progressStatus < 100) {
//                            progressStatus += 1;
//                            // Update the progress bar and display the
//                            //current value in the text view
//                            handler.post(new Runnable() {
//                                public void run() {
//                                    progressBar.setProgress(progressStatus);
//                                    textView.setText(progressStatus+"/"+progressBar.getMax());
//                                }
//                            });
//                            try {
//                                // Sleep for 200 milliseconds.
//                                //Just to display the progress slowly
//                                Thread.sleep(200);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }).start();
            }
            else
            {
                Toast.makeText(this,R.string.dont_have_permission,Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        if (requestCode==REQUEST_CONNECT_DEVICE)
        {
            if (resultCode==RESULT_CANCELED)
            {
                Toast.makeText(this,R.string.dont_have_permission,Toast.LENGTH_SHORT).show();
            }
            else
            {
                connectDevice(data);
            }
        }
    }
}

