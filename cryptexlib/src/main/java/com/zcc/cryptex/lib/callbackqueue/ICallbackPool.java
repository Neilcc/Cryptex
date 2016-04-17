package com.zcc.cryptex.lib.callbackqueue;

import java.util.Set;

/**
 * Created by Hengyun on 16/4/15.
 */
public interface ICallbackPool {

    void pushCallback(String label, IRequestCallback callback);

    int excCallback(String label);

    void excCallback(String label, boolean isSuccess, Object response, int code);

    Set<String> getAllLabels();
}
