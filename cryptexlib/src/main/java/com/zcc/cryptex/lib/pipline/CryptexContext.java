package com.zcc.cryptex.lib.pipline;

import java.util.Map;

/**
 * Created by Hengyun on 16/4/15.
 */
public interface CryptexContext {

    void invokeNext(Map<String, Object> params);

}
