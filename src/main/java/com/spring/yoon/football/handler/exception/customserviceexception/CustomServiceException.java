package com.spring.yoon.football.handler.exception.customserviceexception;


import com.spring.yoon.football.handler.exception.ErrorCode;
import lombok.Getter;

@Getter
public class CustomServiceException extends RuntimeException {
    private ErrorCode errorCode;


    public CustomServiceException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode=errorCode;
    }


}
