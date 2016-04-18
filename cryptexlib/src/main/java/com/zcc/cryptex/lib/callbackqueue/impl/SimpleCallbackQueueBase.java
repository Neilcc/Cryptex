package com.zcc.cryptex.lib.callbackqueue.impl;

import com.zcc.cryptex.lib.callbackqueue.ICallBackQueue;
import com.zcc.cryptex.lib.net.IRequest;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Hengyun on 16/4/15.
 */
public abstract class SimpleCallbackQueueBase implements ICallBackQueue {

    protected static final Object MUTEX = new Object();
    protected Set<String> requestLabels = Collections.synchronizedSet(new HashSet<String>());
    protected CallbackPoolImpl callbackPool = new CallbackPoolImpl();

    public SimpleCallbackQueueBase() {
    }

    @Override
    public <T> void pushCreptexCallbackRequest(IRequest<T> request) {
        if (request == null) {
            return;
        }
//        Linear and synchronized inputs
        synchronized (MUTEX) {
            if (requestLabels.contains(request.getLabel())) {
                callbackPool.pushCallback(request.getLabel(), request.getCallback());
            } else {
                requestLabels.add(request.getLabel());
                processRequest(request);
            }
        }
    }

    protected abstract <T> void processRequest(IRequest<T> request);

    protected <T> void onLabelCallbackFinishedSuccess(String label, T respose) {
//            sync task
        synchronized (MUTEX) {
            callbackPool.excCallback(label, true, respose, -1);
            requestLabels.remove(label);
        }
    }

    protected void onLabelCallbackFinishedFailed(String label, int code, String msg) {
//            sync task
        synchronized (MUTEX) {
            callbackPool.excCallback(label, false, msg, code);
            requestLabels.remove(label);
        }
    }


}
