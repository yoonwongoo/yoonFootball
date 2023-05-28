package com.spring.yoon.football.event.countviews;


import com.spring.yoon.football.enums.boardtype.BoardType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@AllArgsConstructor
@Getter
public abstract class CountViewEvent {
    /*notice, footballMatchBoard*/
    private BoardType boardType;
    /*게시글의 아이디*/
    private long id;

    private HttpServletRequest request;

    private HttpServletResponse response;

}
