package com.spring.yoon.football.handler.exception.customserviceexception.invalidvalueexception;


import com.spring.yoon.football.handler.exception.ErrorCode;
import com.spring.yoon.football.handler.exception.customserviceexception.CustomServiceException;

/*유효성이 Exception*/
public class InvalidValueException extends CustomServiceException {
    public InvalidValueException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }



}
