package com.milk.redisBucket.enums;

/**
 * 获取令牌是否成功
 */
public enum Token {

    SUCCESS,
    FAILED;
    public boolean isSuccess(){
        return this.equals(SUCCESS);
    }
    public boolean isFailed(){
        return this.equals(FAILED);
    }
}
