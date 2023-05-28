package com.spring.yoon.football.service.emailservice;

import com.spring.yoon.football.dto.email.EmailMessageDto;
import org.springframework.stereotype.Service;

@Service
public class NotificationEmailService implements EmailService{
    @Override
    public void sendEmail(EmailMessageDto emailMessageDto) {

        System.out.println("그냥 이메일 보내기");

    }
}
