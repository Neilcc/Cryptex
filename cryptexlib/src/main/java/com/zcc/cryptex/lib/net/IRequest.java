package com.zcc.cryptex.lib.net;


import java.util.HashMap;

/**
 * Created by Hengyun on 16/4/15.
 */
public interface IRequest<T> extends IRequestLabel {
    String getUrl();
    HashMap getParam();
    IRequestCallback getCallback();
}
