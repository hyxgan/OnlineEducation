package com.atguigu.exception;



import com.atguigu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.Annotation;

/**
 * @auther hyx
 */
@Slf4j
@ControllerAdvice
public class myExceptionHandler{

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
         e.printStackTrace();
      return R.error().message("全局异常处理");
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R practicalError(ArithmeticException arithmeticException){
         arithmeticException.printStackTrace();
         return R.error().message("数学异常");
    }

    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R GuliException(GuliException guliException)
    {
         log.error(guliException.getMessage());
        guliException.printStackTrace();
       return R.error().code(guliException.getCode()).message(guliException.getMsg());
    }
}
