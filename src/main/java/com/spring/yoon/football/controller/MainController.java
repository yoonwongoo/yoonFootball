package com.spring.yoon.football.controller;


import com.spring.yoon.football.config.security.AuthMember;
import com.spring.yoon.football.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@RequiredArgsConstructor
@Controller
@Slf4j
public class MainController {

    private final ApplicationContext ac;

    @GetMapping("/")
    public String mainPage(@AuthMember Member member, Model model){

        return "main";
    }


}
