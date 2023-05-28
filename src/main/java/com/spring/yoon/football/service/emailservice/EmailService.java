package com.spring.yoon.football.service.emailservice;

import com.spring.yoon.football.dto.email.EmailMessageDto;

/*이메일 전송하기 현재 서비스에는 알림 목적의 이메일과 인증코드에 대한 이메일 전송이 있다.*/
public interface EmailService{

    void sendEmail(EmailMessageDto emailMessageDto);


}
