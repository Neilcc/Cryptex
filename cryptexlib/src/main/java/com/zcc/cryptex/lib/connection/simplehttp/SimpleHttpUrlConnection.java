package com.zcc.cryptex.lib.connection.simplehttp;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.zcc.cryptex.lib.connection.BaseRespData;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public abstract class SimpleHttpUrlConnection<T extends BaseHttpRequestData, R extends BaseRespData> extends Thread {
    public static final String POST = "POST";
    public static final String GET = "GET";

    public static final int ERROR_CONNECTION = -444;
    private static final int TIMEOUT_SEC = 4 * 1000;
    protected IHeaderInfoBuilder IHeaderInfoBuilder;
    protected T idData;
    protected URL url;
    protected CallBack<R> callBack;

    public SimpleHttpUrlConnection(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("SimpleHttpUrlConnection", "illegal url : " + url);
        }
    }

    protected void setIHeaderInfoBuilder(IHeaderInfoBuilder IHeaderInfoBuilder) {
        this.IHeaderInfoBuilder = IHeaderInfoBuilder;
    }


    public abstract String getMethod();

    public abstract Class<R> getRepClass();

    public void send(T data, @NonNull CallBack<R> callBack) {
        this.idData = data;
        this.callBack = callBack;
        start();
    }

    @Override
    public void run() {
        request();
    }


    private void request() {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(getMethod());
            conn.setConnectTimeout(TIMEOUT_SEC);
            conn.setRequestProperty("connection", "keep-alive");
//            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/json");
            Map<String, String> headers = IHeaderInfoBuilder.createHeaderMap();
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
            if (getMethod().equals("POST")) {
                DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream());
                if (idData == null) {
                    dataOutputStream.write("".getBytes());
                } else {
                    dataOutputStream.write(idData.toString().getBytes());
                }
                dataOutputStream.flush();
            }
            int code = conn.getResponseCode();
            if (code == 200) {
                String content;
                InputStream inputStream = null;
                if (!TextUtils.isEmpty(conn.getContentEncoding())) {
                    String encode = conn.getContentEncoding().toLowerCase();
                    if (!TextUtils.isEmpty(encode) && encode.contains("gzip")) {
                        inputStream = new GZIPInputStream(conn.getInputStream());
                    }
                }
                if (null == inputStream) {
                    inputStream = conn.getInputStream();
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "gbk"));
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    builder.append(line).append("\n");
                }
                content = builder.toString();
                R dataRep = JSON.parseObject(content, getRepClass());
                callBack.onSuccess(dataRep);
            } else {
                callBack.onFailed(code, conn.getResponseMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            callBack.onFailed(ERROR_CONNECTION, e.getMessage());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public interface CallBack<R> {
        void onSuccess(R data);

        void onFailed(int code, String msg);
    }

    public interface IHeaderInfoBuilder {
        Map<String, String> createHeaderMap();
    }
}
