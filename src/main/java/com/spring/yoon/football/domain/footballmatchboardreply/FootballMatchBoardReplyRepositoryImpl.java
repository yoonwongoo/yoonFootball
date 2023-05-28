package com.spring.yoon.football.domain.footballmatchboardreply;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.spring.yoon.football.domain.footballmatchboardreply.QFootballMatchBoardReply.footballMatchBoardReply;
import static com.spring.yoon.football.domain.member.QMember.member;


@RequiredArgsConstructor
public class FootballMatchBoardReplyRepositoryImpl implements FootballMatchBoardReplyRepositoryCustom{

   private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<FootballMatchBoardReply> findByFootballMatchBoardReplyList(long boardId, Pageable pageable) {


    List<FootballMatchBoardReply> list = jpaQueryFactory.select(footballMatchBoardReply)
        .from(footballMatchBoardReply)
        .join(footballMatchBoardReply.member,member).fetchJoin()
        .where(footballMatchBoardReply.footballMatchBoard.id.eq(boardId))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long count = jpaQueryFactory.select(footballMatchBoardReply.count())
        .from(footballMatchBoardReply)
        .where(footballMatchBoardReply.footballMatchBoard.id.eq(boardId))
        .fetchOne();

    Page<FootballMatchBoardReply> pageList = new PageImpl(list, pageable,count);
        return pageList;
    }
}
