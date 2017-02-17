package com.demo.mina_library;

import android.content.Context;

/**
 * Created by Administrator on 2017/2/17.
 * 构建者模式
 */

public class ConnectionConfig {

    private Context context;
    private String ip;
    private int port;
    private int readBufferSize;
    private long connectionTimeout;

    public static class Builder {

        private Context context;
        private String ip = "192.168.150.37";
        private int port = 9999;
        private int readBufferSize = 10240;
        private long connectionTimeout = 10000;

        public Builder(Context context) {
            this.context = context;
        }

        private void applyConfig(ConnectionConfig config) {
            config.context = context;
            config.ip = ip;
            config.port = port;
            config.readBufferSize = readBufferSize;
            config.connectionTimeout = connectionTimeout;
        }

        public ConnectionConfig build() {
            ConnectionConfig config = new ConnectionConfig();
            applyConfig(config);
            return config;
        }

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setIp(String ip) {
            this.ip = ip;
            return this;
        }

        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        public Builder setReadBufferSize(int readBufferSize) {
            this.readBufferSize = readBufferSize;
            return this;
        }

        public Builder setConnectionTimeout(long connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
            return this;
        }
    }

    public Context getContext() {
        return context;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public int getReadBufferSize() {
        return readBufferSize;
    }

    public long getConnectionTimeout() {
        return connectionTimeout;
    }
}
