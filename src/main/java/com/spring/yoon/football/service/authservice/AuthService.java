package com.spring.yoon.football.service.authservice;

import com.spring.yoon.football.config.security.PrincipalMember;
import com.spring.yoon.football.domain.member.Member;
import com.spring.yoon.football.domain.member.MemberRepository;
import com.spring.yoon.football.dto.auth.SignUpDto;
import com.spring.yoon.football.dto.email.EmailAuthCodeDto;
import com.spring.yoon.football.dto.email.EmailMessageDto;
import com.spring.yoon.football.service.emailservice.AuthEmailService;
import com.spring.yoon.football.service.verifyservice.VerifyEmailAuthCodeService;
import com.spring.yoon.football.util.CreateAuthCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RequiredArgsConstructor
@Service
public class AuthService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final TemplateEngine templateEngine;
    private final AuthEmailService emailService;

    private final VerifyEmailAuthCodeService verifyEmailAuthCode;
    private final BCryptPasswordEncoder bCryptEncoder;


    /*회원가입*/
    @Transactional
    public Member addSignUpDto(SignUpDto signUpDto){
        verifyEmailAuthCode.verifyAuthCode(signUpDto.getEmail(),signUpDto.getAuthCode());

        String encodePw = bCryptEncoder.encode(signUpDto.getPassword());
        signUpDto.pwToEncPw(encodePw);//패스워드 encode처리
        Member memberEntity =signUpDto.signUpDtoToMember(signUpDto);
        return memberRepository.save(memberEntity);

    }
    /*회원가입을 위한 인증 이메일 전송*/
    @Transactional
    public void sendToAutoCodeEmail(EmailAuthCodeDto emailAuthCodeDto){
       String authCode= CreateAuthCode.createAuthCode();
       Context context = new Context();
       context.setVariable("title","회원가입 인증 이메일입니다");
       context.setVariable("message","인증시간은 5분입니다");
       context.setVariable("authCode",authCode);
       String message = templateEngine.process("email/auth-code", context);
        emailService.sendEmail(EmailMessageDto.builder()
                 .message(message)
                 .title("YoonFootBallEmail 인증")
                 .to(emailAuthCodeDto.getEmail())
                 .authCode(authCode)
                 .build());
       }

    /*로그인*/
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByUsername(username);

        if(member==null){
            throw new UsernameNotFoundException("존재하지않는 유저입니다");
        }

        return new PrincipalMember(member);
    }
}
