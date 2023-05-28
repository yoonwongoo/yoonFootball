package com.spring.yoon.football.handler.exception.customserviceexception.entitynotfoundexception;


import com.spring.yoon.football.handler.exception.ErrorCode;

public class EmailNotFoundException extends EntityNotFoundException{
    public EmailNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
