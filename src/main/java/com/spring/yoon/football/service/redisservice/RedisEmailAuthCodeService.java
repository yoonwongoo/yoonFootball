package com.spring.yoon.football.service.redisservice;


import com.spring.yoon.football.domain.redis.emailauth.EmailAuthCode;
import com.spring.yoon.football.domain.redis.emailauth.EmailAuthCodeRepository;
import com.spring.yoon.football.dto.email.EmailMessageDto;
import com.spring.yoon.football.handler.exception.ErrorCode;
import com.spring.yoon.football.handler.exception.customserviceexception.entitynotfoundexception.EmailNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisEmailAuthCodeService {

    private final EmailAuthCodeRepository emailAuthNumberRepository;

    /*이메일 인증번호 생성*/
    public void createEmailAuthNumber(EmailMessageDto emailMessageDto){

        EmailAuthCode emailAuthCode = emailMessageDto.emailMessageDtoToEntity(emailMessageDto);
        emailAuthNumberRepository.save(emailAuthCode);
    }

    /*이메일 인증번호 발송 여부*/
    public boolean existsEmailAuthNumber(String email){
        return emailAuthNumberRepository.findById(email).isEmpty();
    }

    /*이메일 인증번호 삭제*/
    public void deleteEmailAuthNumber(String email){
        emailAuthNumberRepository.deleteById(email);
    }


    /*이메일 인증번호 일치하는지->일단 존재하는지 부터 확인*/
    public boolean isEmailAuthNumber(String email, String authCode){

        return emailAuthNumberRepository.findById(email).orElseThrow(()->
        {throw new EmailNotFoundException("알맞지 않는 인증번호입니다", ErrorCode.EMAIL_NOT_FOUND);}).getAuthCode().equals(authCode);
    }
}
