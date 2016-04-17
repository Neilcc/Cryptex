package com.zcc.cryptex.lib.net;


import com.zcc.cryptex.lib.callbackqueue.IRequestCallback;
import com.zcc.cryptex.lib.callbackqueue.IRequestLabel;

import java.util.HashMap;

/**
 * Created by Hengyun on 16/4/15.
 */
public interface IRequest<T> extends IRequestLabel {
    String getUrl();
    HashMap getParam();
    IRequestCallback getCallback();
}
