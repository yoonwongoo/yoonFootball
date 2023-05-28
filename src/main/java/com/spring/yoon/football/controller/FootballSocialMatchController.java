package com.spring.yoon.football.controller;


import com.spring.yoon.football.dto.footballsocialmatch.FootballSocialMatchDto;
import com.spring.yoon.football.service.footballsocialmatchservice.FootballSocialMatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class FootballSocialMatchController {


    private final FootballSocialMatchService footballSocialMatchService;

    /*등록된 소셜매치 목록*/
    @GetMapping("/football-social-match/list")
    public String footballSocialList(FootballSocialMatchDto.Search search , Model model){

        model.addAttribute("footballSocialMatchList",
                footballSocialMatchService.findFootballSocialMatchList(search));
        model.addAttribute("matchDay", search.getMatchDay());
        return "footballsocialmatch/list";
    }

    /*등록된 소셜매치 자세히*/
    @GetMapping("/football-social-match/{id}")
    public String footballSocialDetails(@PathVariable long id, Model model){
        model.addAttribute("footballSocialMatch",footballSocialMatchService.findFootballSocialMatchDetails(id));
        return "footballsocialmatch/detail";
    }

}
