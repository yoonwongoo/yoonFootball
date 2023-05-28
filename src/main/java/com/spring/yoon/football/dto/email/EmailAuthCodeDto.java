package com.spring.yoon.football.dto.email;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/*인증번호 이메일전송 Dto*/
@NoArgsConstructor
@Getter
public class EmailAuthCodeDto {

    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "이메일 형식이 올바르지 않습니다")
    private String email;
}
