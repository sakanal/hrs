package com.sakanal.base.exception;

public enum ErrorCodeEnum {
    REGISTER_FAIL_EXCEPTION(10000,"注册失败"),
    REPEAT_USERNAME_EXCEPTION(10001,"重复的用户名"),
    LOGIN_FAIL_EXCEPTION(10010,"登录失败"),
    TOKEN_EXPIRE_EXCEPTION(10011,"登录失效");

    private final int code;
    private final String msg;
    ErrorCodeEnum(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}