package com.zcc.cryptex.lib.pipline.exception;

/**
 * Created by Hengyun on 16/4/15.
 */
public class OutOfLimtException extends RuntimeException{

    public OutOfLimtException(){
        super("out of limit on cryptex pipe");
    }
}
