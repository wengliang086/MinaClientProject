package com.demo.minaclientproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.demo.mina_library.MinaService;
import com.demo.mina_library.SessionManager;

public class MinaTestActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mConnectView;
    private TextView mSendView;

    private MessageBroadcastReceiver receiver = new MessageBroadcastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mina_test);

        initView();
        registerBroadcast();
    }

    private void initView() {
        mConnectView = (TextView) findViewById(R.id.id_start_server);
        mSendView = (TextView) findViewById(R.id.id_send_message);
        mConnectView.setOnClickListener(this);
        mSendView.setOnClickListener(this);
    }

    private void registerBroadcast() {
        IntentFilter filter = new IntentFilter("com.commonlibrary.mina.broadcast");
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }

    private void unregisterBroadcast() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, MinaService.class));
        unregisterBroadcast();
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.id_send_message:
                    SessionManager.getInstance().writeToServer("123eee");
                    break;
                case R.id.id_start_server:
                    Intent intent = new Intent(this, MinaService.class);
                    startService(intent);
                    break;
            }
        } catch (Exception e) {
            Log.e("mina", e.getMessage(), e);
        }
    }

    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            setTitle(intent.getStringExtra("message"));
        }
    }
}
