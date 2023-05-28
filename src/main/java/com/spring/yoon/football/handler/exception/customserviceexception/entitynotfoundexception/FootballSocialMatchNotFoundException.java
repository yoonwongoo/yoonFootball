package com.spring.yoon.football.handler.exception.customserviceexception.entitynotfoundexception;


import com.spring.yoon.football.handler.exception.ErrorCode;

public class FootballSocialMatchNotFoundException extends EntityNotFoundException{

    public FootballSocialMatchNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
