package com.spring.yoon.football.domain.footballmatchboardreply;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FootballMatchBoardReplyRepositoryCustom {

    Page<FootballMatchBoardReply> findByFootballMatchBoardReplyList(long boardId, Pageable pageable);
}
