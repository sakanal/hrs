package com.sakanal.base.exception;

public enum ErrorCodeEnum {
    REGISTER_FAIL_EXCEPTION(10000,"注册失败"),
    REPEAT_USERNAME_EXCEPTION(10001,"重复的用户名"),
    REPEAT_PHONE_EXCEPTION(10002,"重复的手机号"),
    SMS_FAIL_EXCEPTION(10003,"短信发送失败"),
    SMS_INVALID_EXCEPTION(10004,"无效的验证码"),
    LOGIN_FAIL_EXCEPTION(10010,"登录失败"),
    TOKEN_EXPIRE_EXCEPTION(10011,"登录失效"),
    TOKEN_INVALID_EXCEPTION(10012,"用户无效"),
    PHONE_INVALID_EXCEPTION(10013,"手机号不存在"),
    USER_INVALID_EXCEPTION(10014,"用户名无效"),
    PASSWORD_INVALID_EXCEPTION(10015,"密码错误"),
    PUBLISH_FAIL_EXCEPTION(20001,"发布失败"),
    UPDATE_FAIL_EXCEPTION(20001,"修改失败");

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
