package com.spring.yoon.football.service.emailservice;


import com.spring.yoon.football.dto.email.EmailMessageDto;
import com.spring.yoon.football.handler.exception.ErrorCode;
import com.spring.yoon.football.handler.exception.customserviceexception.invalidvalueexception.EmailAlreadySendException;
import com.spring.yoon.football.service.redisservice.RedisEmailAuthCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

//회원가입과 이메일 변경의 이메일 인증 서비스
@RequiredArgsConstructor
@Service
public class AuthEmailService implements EmailService,CheckEmail {

    private final JavaMailSender javaMailSender;
    private final RedisEmailAuthCodeService redisEmailAuthCodeService;

    /*인증번호 이메일 전송*/
    @Override
    public void sendEmail(EmailMessageDto emailMessageDto) {

        checkEmailStatus(emailMessageDto.getTo());

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(emailMessageDto.getTo());
            mimeMessageHelper.setSubject(emailMessageDto.getTitle());
            mimeMessageHelper.setText(emailMessageDto.getMessage(), true);
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        redisEmailAuthCodeService.createEmailAuthNumber(emailMessageDto);
    }
    
    /*인증번호 이메일 전송 여부 파악 이미 전송이 되었으면 false 아니면 true*/
    @Override
    public void checkEmailStatus(String email){
        if(!redisEmailAuthCodeService.existsEmailAuthNumber(email)){

            throw new EmailAlreadySendException("이미 발송한 이메일입니다", ErrorCode.EMAIL_AlREADY_SEND);
        }
    }


}
