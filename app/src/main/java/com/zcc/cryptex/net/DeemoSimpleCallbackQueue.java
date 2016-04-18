package com.zcc.cryptex.net;


import com.zcc.cryptex.lib.callbackqueue.impl.SimpleCallbackQueueBase;
import com.zcc.cryptex.lib.net.IRequest;

/**
 * Created by Hengyun on 16/4/15.
 */
public class DeemoSimpleCallbackQueue extends SimpleCallbackQueueBase {


    @Override
    protected <T> void processRequest(final IRequest<T> request) {
        final DeemoRequest request1 = (DeemoRequest) request;
//        BaseApi.getInstance().get(request.getUrl(), request1.getParam(), request1.getResultClass(), true, new IRequestCallback<T>() {
//            @Override
//            public void onSuccess(T response) {
//                onLabelCallbackFinishedSuccess(request1.getLabel(), response);
//                request1.getCallback().onSuccess(response);
//            }
//
//            @Override
//            public void onFailure(int code, String msg) {
//                onLabelCallbackFinishedFailed(request1.getLabel(), code, msg);
//                request1.getCallback().onFailed(code, msg);
//            }
//        });
    }
}
