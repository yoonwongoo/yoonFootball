package com.spring.yoon.football.controller.validator;

import com.spring.yoon.football.domain.member.MemberRepository;
import com.spring.yoon.football.dto.auth.SignUpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SignUpValidator implements Validator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(SignUpDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

        SignUpDto signUpDto = (SignUpDto) target;

        if(memberRepository.existsByUsername(signUpDto.getUsername())){
            errors.rejectValue("username","중복검사","이미 가입된 아이디입니다");
        } 
        if(memberRepository.existsByEmail(signUpDto.getEmail())){
            errors.rejectValue("email","중복검사","이미 등록된 이메일입니다");
        }
    }
}
