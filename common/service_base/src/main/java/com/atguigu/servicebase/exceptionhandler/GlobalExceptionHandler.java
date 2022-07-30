package com.atguigu.servicebase.exceptionhandler;

import com.atguigu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 张于宴
 * @version 2019.3
 * @description 统一异常处理
 * @date 2022/6/30 1:48
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    //指定出现什么方法执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e) {
        e.printStackTrace( );
        return R.error( ).message("执行了全局异常处理");
    }
    //特定异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException异常处理");
    }
   //自定义异常
   @ExceptionHandler(GuliExpception.class)
   @ResponseBody
   public R error(GuliExpception e){
        log.error(e.getMessage());
       e.printStackTrace();
       return R.error().code(e.getCode()).message(e.getMsg());
}
}
