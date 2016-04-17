package com.zcc.cryptex.lib.callbackqueue.manager;


import com.zcc.cryptex.lib.callbackqueue.ICallbackPool;
import com.zcc.cryptex.lib.callbackqueue.ILabelCallbackFinishListener;
import com.zcc.cryptex.lib.callbackqueue.IRequestCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Hengyun on 16/4/15.
 */
public class CallbackPoolImpl implements ICallbackPool {

    private ILabelCallbackFinishListener labelCallbackFinishListener;
    private HashMap<String, ArrayList<IRequestCallback>> callbackArrays = new HashMap<>();


    @Override
    public void pushCallback(String label, IRequestCallback callback) {
        if(label == null || callback == null){
            return;
        }
        ArrayList<IRequestCallback> arrayList  = callbackArrays.get(label);
        if(arrayList!=null){
            arrayList.add(callback);
        }else {
            arrayList = new ArrayList<>();
            arrayList.add(callback);
            callbackArrays.put(label, arrayList);
        }
    }

    @Override
    public int excCallback(String label) {
        return -1;
    }

    @Override
    public void excCallback(String label, boolean isSuccess, Object response, int code) {
        ArrayList<IRequestCallback> arrayList  = callbackArrays.get(label);
        if(arrayList!=null){
            for(IRequestCallback callback: arrayList){
                if(isSuccess){
                    callback.onSuccess(response);
                }else {
                    callback.onFailed(code, String.valueOf(response));
                }
            }
        }
    }

    @Override
    public Set<String> getAllLabels() {
        return callbackArrays.keySet();
    }

}
