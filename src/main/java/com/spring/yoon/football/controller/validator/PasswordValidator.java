package com.spring.yoon.football.controller.validator;

import com.spring.yoon.football.dto.member.PasswordDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class PasswordValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(PasswordDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

        PasswordDto passwordDto = (PasswordDto) target;
        if(!passwordDto.getPassword().equals(passwordDto.getPasswordConfirm())){
            errors.rejectValue("passwordConfirm","유효성검사","새로운 비밀번호가 일치하지 않습니다");
        }
    }
}
