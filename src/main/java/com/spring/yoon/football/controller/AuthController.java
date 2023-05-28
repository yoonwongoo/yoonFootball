package com.spring.yoon.football.controller;


import com.spring.yoon.football.config.security.AuthMember;
import com.spring.yoon.football.domain.member.Member;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*회원가입과 로그인*/
@Controller
public class AuthController {
    /*로그인 폼*/
    @GetMapping("/sign-in")
    public String signInForm(@AuthMember Member member){
        if(member!=null){
            return "redirect:/";
        }
        return "auth/sign-in";
    }

    /*회원가입 폼*/
    @GetMapping("/sign-up")
    public String signUpForm(@AuthMember Member member){
        if(member!=null){
            return "redirect:/";
        }
        return "auth/sign-up";
    }



}
