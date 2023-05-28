package com.spring.yoon.football.controller;


import com.spring.yoon.football.config.security.AuthMember;
import com.spring.yoon.football.domain.member.Member;
import com.spring.yoon.football.dto.footballmatchboard.FootballMatchBoardDto;
import com.spring.yoon.football.enums.member.SkillLevel;
import com.spring.yoon.football.service.footballmatchboardservice.FootballMatchBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

@RequiredArgsConstructor
@Controller
public class FootballMatchBoardController {

    private final FootballMatchBoardService footballMatchBoardService;
    /*글 작성 폼*/
    @GetMapping("/football-match-board")
    public String  footBallMatchBoardForm(Model model){
        model.addAttribute("skillLevel", SkillLevel.getEnumList());
        return "footballmatchboard/write";
    }
    
    /*글 상세보기*/
    @GetMapping("/football-match-board/{id}")
    public String footBallMatchBoardDetail(@PathVariable long id, HttpServletRequest request, HttpServletResponse response,Model model){
        FootballMatchBoardDto.Detail detail = footballMatchBoardService.findFootballMatchBoard(id,request,response);
        model.addAttribute("footballMatchBoard",detail);
        return "footballmatchboard/detail";
    }
    
    /*페이지 입장 시 글 리스트 보기*/
    @GetMapping("/football-match-board/list")
    public String footBallMatchBoardList(FootballMatchBoardDto.Search search,
                                       Pageable pageable, Model model){
        Slice<FootballMatchBoardDto.SliceList> list =footballMatchBoardService.findFootballMatchBoardList(search,pageable);
        model.addAttribute("footballMatchBoardList",list);
        model.addAttribute("matchDay", LocalDate.now());
        return "footballmatchboard/list";
    }
    
    /*게시글 수정 폼*/
    @GetMapping("/football-match-board/edit/{id}")
    public String footBallMatchBoardModifyForm(@PathVariable long id, @AuthMember Member member, Model model){

       FootballMatchBoardDto.Detail detail= footballMatchBoardService.findFootballMatchBoardModifyForm(id,member);
        model.addAttribute("footballMatchBoard",detail);
        model.addAttribute("skillLevel", SkillLevel.getEnumList());
        return "footballmatchboard/modify";
    }
}
