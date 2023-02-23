package com.sakanal.base.exception;

import cn.hutool.core.exceptions.ValidateException;
import com.sakanal.base.exception.MyException;
import com.sakanal.base.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class MyGlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R Exception(Exception e) {
        log.error("Exception异常捕获");
        e.printStackTrace();
        return R.error(e.getMessage());
    }

    @ExceptionHandler(ValidateException.class)
    @ResponseBody
    public R ValidateException(ValidateException e) {
        log.error("ValidateException异常捕获==>token过期");
        return R.error(ErrorCodeEnum.TOKEN_EXPIRE_EXCEPTION.getCode(), ErrorCodeEnum.TOKEN_EXPIRE_EXCEPTION.getMsg());
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseBody
    public R MissingRequestHeaderException(MissingRequestHeaderException e) {
        // TODO 通过token获取数据的时候，可能因为跨域发送OPTIONS请求导致header中没有token
        String headerName = e.getHeaderName();
        if (!"token".equals(headerName)){
            log.error("MissingRequestHeaderException异常捕获");
        }
        return R.error(e.getMessage());
    }
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public R MyException(MyException e) {
        log.error("自定义异常捕获");
        return R.error(e.getCode(),e.getMsg());
    }
}

