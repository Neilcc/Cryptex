package com.zcc.cryptex.lib.net;

/**
 * Created by Hengyun on 16/4/15.
 */
public interface IRequestCallback<T> {

    void onSuccess(T response);

    void onFailed(int i, String msg);
}
