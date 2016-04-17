package com.zcc.cryptex.lib.pipline;

import java.util.Map;

/**
 * Created by Hengyun on 16/4/15.
 */
public interface Valve {

    void invoke(Map<String, Object> param, CryptexContext context);

}
