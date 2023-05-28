package com.spring.yoon.football.service.verifyservice;

import com.spring.yoon.football.handler.exception.ErrorCode;
import com.spring.yoon.football.handler.exception.customserviceexception.invalidvalueexception.EmailInvalidInputValueException;
import com.spring.yoon.football.service.redisservice.RedisEmailAuthCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/*이메일 인증번호 유효성 검사 레디스에 의존*/
@RequiredArgsConstructor
@Service
public class VerifyEmailAuthCodeService implements VerifyAuthCodeService {

    private final RedisEmailAuthCodeService redisEmailAuthCodeService;
    /*이메일 인증번호 검증*/
    public void verifyAuthCode(String email,String authCode){
         if(!redisEmailAuthCodeService.isEmailAuthNumber(email,authCode)){

            throw new EmailInvalidInputValueException("인증번호가 일치하지않습니다", ErrorCode.EMAIL_INVAlID_INPUT);
         }
         redisEmailAuthCodeService.deleteEmailAuthNumber(email);
        }
}
