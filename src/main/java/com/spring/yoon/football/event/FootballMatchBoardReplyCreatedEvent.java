package com.spring.yoon.football.event;


import com.spring.yoon.football.domain.footballmatchboardreply.FootballMatchBoardReply;
import lombok.Getter;

/*내 게시글에 댓글을 작성할 경우 event*/
@Getter
public class FootballMatchBoardReplyCreatedEvent {

    private final FootballMatchBoardReply footballMatchBoardReply;

    public FootballMatchBoardReplyCreatedEvent(FootballMatchBoardReply footballMatchBoardReply) {
        this.footballMatchBoardReply = footballMatchBoardReply;
    }
}
