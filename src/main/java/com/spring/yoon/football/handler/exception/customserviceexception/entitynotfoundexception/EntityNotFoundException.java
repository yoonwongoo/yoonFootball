package com.spring.yoon.football.handler.exception.customserviceexception.entitynotfoundexception;


import com.spring.yoon.football.handler.exception.ErrorCode;
import com.spring.yoon.football.handler.exception.customserviceexception.CustomServiceException;

/*존재하지않는 entity Exception*/
public class EntityNotFoundException extends CustomServiceException {

    public EntityNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

}
