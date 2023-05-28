package com.spring.yoon.football.service.verifyservice;

public interface VerifyAuthCodeService {
    /*이메일에 대한 인증번호 및 휴대전화에 대한 인증번호 유효성 검사*/
    void verifyAuthCode(String identifier, String authCode);
}
