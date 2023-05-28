package com.spring.yoon.football.controller.api;

import com.spring.yoon.football.service.footballmatchboardservice.FootballMatchBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ApiFootballSocialController {

    private final FootballMatchBoardService footballMatchBoardService;
    
    /*날짜별 구장목록*/
    @GetMapping("/football-social-match")
    public ResponseEntity<?> apiFootballSocialMatchList(){

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
