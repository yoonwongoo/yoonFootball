package com.spring.yoon.football.event;

import com.spring.yoon.football.domain.footballmatchboard.FootballMatchBoard;
import lombok.Getter;

/*내가 댓글을 단 게시글이 수정*/
@Getter
public class FootballMatchBoardUpdateEvent {

    private FootballMatchBoard footballMatchBoard;

    public FootballMatchBoardUpdateEvent(FootballMatchBoard footballMatchBoard) {
        this.footballMatchBoard = footballMatchBoard;
    }
}
