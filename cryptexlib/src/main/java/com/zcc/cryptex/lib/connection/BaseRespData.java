package com.zcc.cryptex.lib.connection;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

public class BaseRespData implements IProguardKeep {
    private String message;
    private int code;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String getMessage() {
        return TextUtils.isEmpty(message) ? "" : message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
