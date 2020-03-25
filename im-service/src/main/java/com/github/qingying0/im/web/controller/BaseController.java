package com.github.qingying0.im.web.controller;

import com.github.qingying0.im.dto.ResultDTO;
import com.github.qingying0.im.exception.CustomCode;
import com.github.qingying0.im.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class BaseController {

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResultDTO<Object> handlercustomException(CustomException e) {
        System.out.println("发生错误");
        e.printStackTrace();
        return ResultDTO.errorOf(e);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultDTO<Object> handlerException(Exception e) {
        e.printStackTrace();
        return ResultDTO.errorOf(CustomCode.FAIL);
    }
}
