package com.spring.yoon.football.domain.footballstadium;


import com.spring.yoon.football.dto.footballstadium.FootBallStadiumDto;

import java.util.List;

public interface FootballStadiumRepositoryCustom {


    List<FootBallStadiumDto.FootballStadiumMatchResponse> findByFootballStadiumMatchList(FootBallStadiumDto.Search search);
}
