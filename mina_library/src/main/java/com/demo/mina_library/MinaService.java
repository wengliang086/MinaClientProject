package com.demo.mina_library;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2017/2/17.
 */

public class MinaService extends Service {

    private ConnectionThread thread;

    @Override
    public void onCreate() {
        super.onCreate();
        thread = new ConnectionThread("mina", getApplicationContext());
        thread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        thread.disConnect();
        thread = null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 负责调用ConnectionManager类来完成与服务器的连接
     */
    class ConnectionThread extends HandlerThread {

        private Context mContext;
        private boolean isConnection;
        private ConnectionManager mManager;

        public ConnectionThread(String name, Context context) {
            super(name);
            this.mContext = context;
            ConnectionConfig config = new ConnectionConfig.Builder(context)
                    .setConnectionTimeout(10000)
                    .setIp("192.168.150.37")
                    .setReadBufferSize(10240)
                    .setPort(9999)
                    .build();
            mManager = new ConnectionManager(config);
        }

        @Override
        protected void onLooperPrepared() {
            super.onLooperPrepared();
            for (;;) {
                isConnection = mManager.connect();
                if (isConnection) {
                    break;
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void disConnect() {
            mManager.disConnect();
        }
    }
}
