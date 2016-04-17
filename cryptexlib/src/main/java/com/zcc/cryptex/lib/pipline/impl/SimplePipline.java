package com.zcc.cryptex.lib.pipline.impl;


import com.zcc.cryptex.lib.pipline.CryptexContext;
import com.zcc.cryptex.lib.pipline.Pipline;
import com.zcc.cryptex.lib.pipline.Valve;
import com.zcc.cryptex.lib.pipline.exception.OutOfLimtException;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Hengyun on 16/4/15.
 */
public class SimplePipline implements Pipline {

    private static final int DEFAULT_VALUE_LIMIT = 100;
    private ArrayList<Valve> valves = new ArrayList<>();
    private Set<Integer> disabled = new TreeSet<>();
    private Valve basicValve;

    @Override
    public void setBasic(Valve valve) {
        if (valve == null) {
            throw new NullPointerException("basic valve can not be null");
        }
        basicValve = valve;
    }

    @Override
    public void addValve(Valve valve) {
        if (valve == null) {
            return;
        }
        if(valves.size() == DEFAULT_VALUE_LIMIT){
            throw new OutOfLimtException();
        }
        valves.add(valve);
    }

    @Override
    public void removeValve(Valve valve) {
        if (valve == null) {
            return;
        }
        valves.remove(valve);
    }

    public void disableValve(Valve valve) {
        if (valve == null) {
            return;
        }
        int index = valves.indexOf(valve);
        if (index > -1) {
            disabled.add(index);
        }
    }

    public void resetDisable(){
        disabled.clear();
    }

    public void removeDisable(Valve valve){
        if(valve == null){
            return;
        }
        int index = valves.indexOf(valve);
        if (index > -1) {
            disabled.remove(index);
        }
    }

    @Override
    public void invoke(Map<String, Object> params) {
        new SimpleCryptexPipHandler().invokeNext(params);
    }

    private class SimpleCryptexPipHandler implements CryptexContext {

        private Map<String, Object> params;

        public SimpleCryptexPipHandler(){

        }

        public SimpleCryptexPipHandler(Map<String, Object> params) {
            this.params = params;
        }

        @Override
        public void invokeNext(Map<String, Object> params) {
            for(int i = 0 ; i <valves.size(); i ++){
                if(disabled.contains(i)){
                    continue;
                }
                valves.get(i).invoke(params,this);
            }
            SimplePipline.this.basicValve.invoke(params, this);
        }
    }
}
