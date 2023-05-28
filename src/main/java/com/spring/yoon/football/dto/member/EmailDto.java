package com.spring.yoon.football.dto.member;

import com.spring.yoon.football.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
/*이메일 변경 DTO*/
@NoArgsConstructor
@Getter
public class EmailDto {
    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "이메일 형식이 올바르지 않습니다")
    private String email;

    @NotBlank(message = "인증번호를 입력해주세요")
    private String authCode;

    private EmailDto(Member member) {
        this.email = member.getEmail();
    }

    public static EmailDto form(Member member){
        return new EmailDto(member);

    }
}
