package com.spring.yoon.football.controller.api;


import com.spring.yoon.football.service.footballsocialmatchenrollservice.FootballSocialMatchEnrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ApiFootballSocialMatchEnrollController {

    private final FootballSocialMatchEnrollService footballSocialMatchEnrollService;

    
/*    매치 신청하기 일단 이거는 보류를 하자.......*//*
    @PostMapping("/football-social-match/{id}/enroll")
    public ResponseEntity<?> FootballSocialMatchEnrollAdd(@PathVariable long id, @AuthMember Member member){
        footballSocialMatchEnrollService.addFootballSocialMatchEnroll(id,member);
        return new ResponseEntity(id, HttpStatus.CREATED);
    }*/
}
