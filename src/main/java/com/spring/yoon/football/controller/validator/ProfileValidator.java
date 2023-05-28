package com.spring.yoon.football.controller.validator;

import com.spring.yoon.football.domain.member.MemberRepository;
import com.spring.yoon.football.dto.member.ProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
@Component
public class ProfileValidator implements Validator {

    private final MemberRepository memberRepository;
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(ProfileDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        /*ProfileDto profileDto = (ProfileDto) target;
        if(memberRepository.existsByProfileNickName(profileDto.getNickName())){

            errors.rejectValue("nickName","유효성 검사 에러","이미 등록된 닉네임입니다");
        }*///form 때메 보낼까말가 고민중...
    }
}
