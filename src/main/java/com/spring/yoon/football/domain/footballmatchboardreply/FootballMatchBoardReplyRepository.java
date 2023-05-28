package com.spring.yoon.football.domain.footballmatchboardreply;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FootballMatchBoardReplyRepository extends JpaRepository<FootballMatchBoardReply,Long> ,FootballMatchBoardReplyRepositoryCustom{
    /*글삭제 시 댓글 먼저 삭제*/
    @Modifying
    @Query("delete from FootballMatchBoardReply fr where fr.footballMatchBoard.id =:footballBoardId")
    void deleteByFootballMatchBoardReplyList(Long footballBoardId);

    /*내가 쓴 댓글*/
    @Query(value = "select fr from FootballMatchBoardReply fr join fetch fr.footballMatchBoard where fr.member.id=:memberId",
    countQuery =" select Count(fr) from FootballMatchBoardReply fr where fr.member.id =:memberId")
    Page<FootballMatchBoardReply> findByMyFootballMatchBoardReplyList(long memberId, Pageable pageable);
}
