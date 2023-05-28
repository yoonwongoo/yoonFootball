package com.spring.yoon.football.handler.exception.customserviceexception.entitynotfoundexception;


import com.spring.yoon.football.handler.exception.ErrorCode;

public class FootballStadiumNotFoundException extends EntityNotFoundException{

    public FootballStadiumNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
