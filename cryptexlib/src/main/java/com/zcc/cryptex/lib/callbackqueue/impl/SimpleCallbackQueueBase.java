package com.zcc.cryptex.lib.callbackqueue.impl;


import com.zcc.cryptex.lib.callbackqueue.ICallBackQueue;
import com.zcc.cryptex.lib.callbackqueue.manager.CallbackPoolImpl;
import com.zcc.cryptex.lib.net.IRequest;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Hengyun on 16/4/15.
 */
public abstract class SimpleCallbackQueueBase implements ICallBackQueue {

    protected Set<String> requestLabels = Collections.synchronizedSet(new HashSet<String>());
    protected CallbackPoolImpl callbackPool = new CallbackPoolImpl();

    public SimpleCallbackQueueBase() {
    }

    @Override
    public <T   > void pushCreptexCallbackRequest(IRequest<T> request) {
        if (request == null) {
            return;
        }
        if (requestLabels.contains(request.getLabel())) {
            callbackPool.pushCallback(request.getLabel(), request.getCallback());
        } else {
            processRequest(request);
        }
    }

    protected abstract <T  > void processRequest(IRequest<T> request);


}
