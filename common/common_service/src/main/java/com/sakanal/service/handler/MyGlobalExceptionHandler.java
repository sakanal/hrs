package com.sakanal.service.handler;

import com.sakanal.base.exception.MyException;
import com.sakanal.base.utils.R;
import lombok.extern.slf4j.Slf4j;
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

    @ExceptionHandler(MyException.class)
    @ResponseBody
    public R MyException(MyException e) {
        log.error("自定义异常捕获");
        return R.error(e.getCode(),e.getMsg());
    }
}

