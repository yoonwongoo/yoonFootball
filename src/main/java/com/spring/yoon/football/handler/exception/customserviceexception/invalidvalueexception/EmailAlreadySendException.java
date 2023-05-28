package com.spring.yoon.football.handler.exception.customserviceexception.invalidvalueexception;

import com.spring.yoon.football.handler.exception.ErrorCode;

public class EmailAlreadySendException extends InvalidValueException{
    public EmailAlreadySendException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
