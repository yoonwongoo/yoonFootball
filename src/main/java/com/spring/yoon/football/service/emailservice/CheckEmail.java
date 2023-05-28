package com.spring.yoon.football.service.emailservice;

public interface CheckEmail {
    
    /*인증번호 이메일 전송 여부 파악 이미 전송이 되었으면 false 아니면 true
    * 일반적인 인증기반아닌 알림 기반 이메일을 보내야할때는 이메일을 보냈는지? 알필요가 없다고 생각하기에
    * 따로 분리*/
    void checkEmailStatus(String email);

}
