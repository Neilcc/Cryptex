package com.zcc.cryptex.net;


import com.zcc.cryptex.lib.callbackqueue.impl.CryptexRequestBase;
import com.zcc.cryptex.lib.net.IRequestCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Hengyun on 16/4/15.
 */
public class DeemoRequest<T> extends CryptexRequestBase {
    private String url;
    private HashMap<String, String> params;
    private String label;
    private IRequestCallback callback;
    private Class paramClass;

    public DeemoRequest(String url, HashMap<String, String> params, Class<T> clazz, final IRequestCallback<T> callback) {
        if (url == null) {
            throw new NullPointerException("destination url cannot be null");
        }
        this.url = url;
        this.params = params;
        this.callback = callback;
        this.paramClass = clazz;
        generateLabel();
    }

    @Override
    public String getLabel() {
        return label;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public HashMap getParam() {
        return params;
    }

    public Class getResultClass() {
        return paramClass;
    }

    public IRequestCallback getCallback() {

        return callback;
    }


    private void generateLabel() {
        label = url;
        if (params != null && params.values().size() > 0) {
            ArrayList<String> keys = new ArrayList<String>((params.keySet()));
            Collections.sort(keys);
            for (String s : keys) {
                label += params.get(s);
            }
        }
    }
}
