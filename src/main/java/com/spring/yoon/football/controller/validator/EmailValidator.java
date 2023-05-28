package com.spring.yoon.football.controller.validator;


import com.spring.yoon.football.domain.member.MemberRepository;
import com.spring.yoon.football.dto.email.EmailAuthCodeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
@Component
public class EmailValidator implements Validator {

    private final MemberRepository memberRepository;
    
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(EmailAuthCodeDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EmailAuthCodeDto emailAuthCodeDto = (EmailAuthCodeDto) target;
        if(memberRepository.existsByEmail(emailAuthCodeDto.getEmail())){
            errors.rejectValue("email","유효성 검사 에러","이미 등록된 이메일입니다");
        }
    }
}
