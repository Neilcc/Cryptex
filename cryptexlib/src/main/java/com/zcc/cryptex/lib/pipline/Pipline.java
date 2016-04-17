package com.zcc.cryptex.lib.pipline;


import java.util.Map;

/**
 * Created by Hengyun on 16/4/15.
 */
public interface Pipline {

    void setBasic(Valve valve);

    void addValve(Valve valve);

    void removeValve(Valve valve);

//    void disableValve(Valve valve);

    void invoke(Map<String, Object> params);
}
