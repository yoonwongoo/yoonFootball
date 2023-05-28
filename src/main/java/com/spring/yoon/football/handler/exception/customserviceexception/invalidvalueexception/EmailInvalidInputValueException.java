package com.spring.yoon.football.handler.exception.customserviceexception.invalidvalueexception;

import com.spring.yoon.football.handler.exception.ErrorCode;

/*잘못된 인증번호 입력*/
public class EmailInvalidInputValueException extends InvalidValueException{
    public EmailInvalidInputValueException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
