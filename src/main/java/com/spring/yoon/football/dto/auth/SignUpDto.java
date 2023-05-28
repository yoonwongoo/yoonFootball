package com.spring.yoon.football.dto.auth;


import com.spring.yoon.football.domain.member.Member;
import com.spring.yoon.football.enums.member.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class SignUpDto {

    @NotBlank (message="아이디를 입력해주세요")
    @Size(min = 3, max = 10, message = "아이디는 3 ~ 10자 이여야 합니다.")
    private String username;
    
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;
    
    @Size(min = 2, max = 10, message = "이름은 2 ~ 10자 이여야 합니다.")
    @NotBlank(message = "이름을 입력해주세요")
    private String name;
    
    @Email(message = "이메일 형식이 올바르지 않습니다")
    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    @NotBlank(message = "인증번호를 입력해주세요")
    private String authCode;


    public Member signUpDtoToMember(SignUpDto signUpDto){
        return  Member.builder()
                .username(signUpDto.getUsername())
                .email(signUpDto.getEmail())
                .name(signUpDto.getName())
                .role(Role.ROLE_USER)
                .password(signUpDto.password)
                .point(0)
                .profile(Member.Profile.builder().build())
                .notificationSetting(Member.NotificationSetting.builder().build())
                .build();
    }

    public void pwToEncPw(String password){
        this.password=password;
    }


}
