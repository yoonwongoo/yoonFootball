package com.spring.yoon.football.handler.exception.customserviceexception.entitynotfoundexception;


import com.spring.yoon.football.handler.exception.ErrorCode;

public class FootballMatchBoardNotFoundException extends EntityNotFoundException {
    public FootballMatchBoardNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
