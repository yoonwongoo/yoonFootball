package com.spring.yoon.football.domain.footballmatchboard;

import com.spring.yoon.football.dto.footballmatchboard.FootballMatchBoardDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface FootballMatchBoardRepositoryCustom {
    /*api*/
    /*팀/개인 글 목록*/
    Slice<FootballMatchBoard> findByFootballMatchBoardList(FootballMatchBoardDto.Search search, Pageable pageable);
    


}
