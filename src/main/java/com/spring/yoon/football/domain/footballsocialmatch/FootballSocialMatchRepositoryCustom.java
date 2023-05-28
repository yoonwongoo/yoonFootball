package com.spring.yoon.football.domain.footballsocialmatch;


import com.spring.yoon.football.dto.footballsocialmatch.FootballSocialMatchDto;

import java.util.List;

public interface FootballSocialMatchRepositoryCustom {
    /*매치 목록*/
    List<FootballSocialMatch> findByFootballSocialMatchList(FootballSocialMatchDto.Search search);
}
