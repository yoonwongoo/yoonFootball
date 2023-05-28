package com.spring.yoon.football.handler.exception.customserviceexception.entitynotfoundexception;


import com.spring.yoon.football.handler.exception.ErrorCode;

public class NoticeBoardNotFoundException extends EntityNotFoundException{

    public NoticeBoardNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
