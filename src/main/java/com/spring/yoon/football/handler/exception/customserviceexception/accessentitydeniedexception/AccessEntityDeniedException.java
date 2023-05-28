package com.spring.yoon.football.handler.exception.customserviceexception.accessentitydeniedexception;

import com.spring.yoon.football.handler.exception.ErrorCode;
import com.spring.yoon.football.handler.exception.customserviceexception.CustomServiceException;

/*삭제나 수정시 자기 권한이 아닌 경우 Exception*/
public class AccessEntityDeniedException extends CustomServiceException {
    public AccessEntityDeniedException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }


}
