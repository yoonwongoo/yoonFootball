package com.spring.yoon.football.enums.boardtype;

import lombok.Getter;


/*게시글의 타입을 분류하는 enum class
* 현재 클래스에는 게시글의 조회수를 증가시키는 */
@Getter
public enum BoardType {

    NOTICE("공지"),FOOTBALL_MATCH_BOARD("풋볼매치");

    private String value;

    BoardType(String value) {
        this.value = value;
    }

}
