package com.github.qingying0.im.dto;

import com.github.qingying0.im.exception.CustomCode;
import com.github.qingying0.im.exception.CustomException;
import lombok.Data;

@Data
public class ResultDTO<T>{

    private Integer code;
    private String message;
    private T data;

    public static <T> ResultDTO<T> errorOf(String message) {
        ResultDTO<T> resultDTO = new ResultDTO<>();
        resultDTO.setCode(500);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static <T> ResultDTO<T> errorOf(int code, String message) {
        ResultDTO<T> resultDTO = new ResultDTO<>();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static <T> ResultDTO<T> errorOf(int code, String message, T data) {
        ResultDTO<T> resultDTO = new ResultDTO<>();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        resultDTO.setData(data);
        return resultDTO;
    }

    public static <T> ResultDTO<T> errorOf(CustomCode c) {
        ResultDTO<T> resultDTO = new ResultDTO<>();
        resultDTO.setCode(c.getCode());
        resultDTO.setMessage(c.getMessage());
        return resultDTO;
    }

    public static <T> ResultDTO<T> errorOf(CustomException e) {
        ResultDTO<T> resultDTO = new ResultDTO<>();
        resultDTO.setCode(e.getCode());
        resultDTO.setMessage(e.getMessage());
        return resultDTO;
    }

    public static <T> ResultDTO<T> okOf() {
        return ResultDTO.customCode(CustomCode.SUCCESS);
    }

    public static <T> ResultDTO<T> okOf(T data) {
        return ResultDTO.customCode(CustomCode.SUCCESS, data);
    }

    public static <T> ResultDTO<T> customCode(CustomCode customCode) {
        ResultDTO<T> resultDTO = new ResultDTO<>();
        resultDTO.setCode(customCode.getCode());
        resultDTO.setMessage(customCode.getMessage());
        return resultDTO;
    }

    public static <T> ResultDTO<T> customCode(CustomCode customCode, T data) {
        ResultDTO<T> resultDTO = new ResultDTO<>();
        resultDTO.setCode(customCode.getCode());
        resultDTO.setMessage(customCode.getMessage());
        resultDTO.setData(data);
        return resultDTO;
    }

    public static <T> ResultDTO customException(CustomException customException) {
        ResultDTO<T> resultDTO = new ResultDTO<>();
        resultDTO.setCode(customException.getCode());
        resultDTO.setMessage(customException.getMessage());
        return resultDTO;
    }
}
