package com.zcc.cryptex.lib.callbackqueue;


import com.zcc.cryptex.lib.net.IRequest;

/**
 * Created by Hengyun on 16/4/15.
 */
public interface ICallBackQueue {

    <T  >void pushCreptexCallbackRequest(IRequest<T> request);
}
