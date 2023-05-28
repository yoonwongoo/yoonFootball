package com.spring.yoon.football.domain.footballmatchboardreply;


import com.spring.yoon.football.domain.BaseTimeEntity;
import com.spring.yoon.football.domain.footballmatchboard.FootballMatchBoard;
import com.spring.yoon.football.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FootballMatchBoardReply extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "footballMatchBoardId")
    private FootballMatchBoard footballMatchBoard;


    public void addFootballMatchBoard(FootballMatchBoard footballMatchBoard) {
        this.footballMatchBoard = footballMatchBoard;
        footballMatchBoard.getFootballMatchBoardReplys().add(this);
    }
}
