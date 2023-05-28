package com.spring.yoon.football.event.countviews;

import com.spring.yoon.football.enums.boardtype.BoardType;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*게시글 조회 수*/
@Getter
public class NoticeBoardCountViews extends CountViewEvent{
    public NoticeBoardCountViews(BoardType boardType, long id, HttpServletRequest request, HttpServletResponse response) {
        super(boardType, id, request, response);
    }


}
