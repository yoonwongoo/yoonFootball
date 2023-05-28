package com.spring.yoon.football.event.countviews;

import com.spring.yoon.football.enums.boardtype.BoardType;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Getter
public class FootballMatchBoardCountViews extends CountViewEvent{
    public FootballMatchBoardCountViews(BoardType boardType, long id, HttpServletRequest request, HttpServletResponse response) {

        super(boardType, id,request,response);
    }
}
