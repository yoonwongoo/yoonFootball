package com.spring.yoon.football.service.countviewsservice;


import com.spring.yoon.football.enums.boardtype.BoardType;

/*게시글*/
public interface CountViewsService {
    /*게시글의 유형 ex) 공지사항 게시글 or 풋살모집게시글에 대한 타입반환 */
    BoardType getBoardType();

    void countViews(long id);
}
