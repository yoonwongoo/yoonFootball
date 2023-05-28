package com.spring.yoon.football.dto.email;


import com.spring.yoon.football.domain.redis.emailauth.EmailAuthCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*인증번호 이메일 전송 DTO*/
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmailMessageDto {

    private String to;
    private String title;
    private String authCode;
    private String message;

    public EmailAuthCode emailMessageDtoToEntity(EmailMessageDto emailMessageDto) {
       return EmailAuthCode.builder()
               .email(emailMessageDto.to)
               .authCode(emailMessageDto.getAuthCode())
               .build();
    }
}
