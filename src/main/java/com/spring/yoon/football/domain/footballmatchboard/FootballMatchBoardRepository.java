package com.spring.yoon.football.domain.footballmatchboard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FootballMatchBoardRepository extends JpaRepository<FootballMatchBoard,Long>,FootballMatchBoardRepositoryCustom {

    /*글 경기시간 마감처리 update*/
    @Modifying
    @Query("update FootballMatchBoard f set f.recruitment=false where f.id in :ids")
    void updateRecruitment(List<Long> ids);

    /*글 상세보기*/
    @Query("select f from FootballMatchBoard f join fetch f.member where f.id =:footballBoardId ")
    Optional<FootballMatchBoard> findByFootballBoardDetail(long footballBoardId);

    /*내가 쓴 글*/
    @Query(value = "select f from FootballMatchBoard f join fetch f.member m where m.id =:memberId",
    countQuery = "select Count(f) from FootballMatchBoard f join  f.member m where m.id =:memberId")
    Page<FootballMatchBoard> findByMyFootballMatchBoardList(long memberId, Pageable pageable);
    
    /*조회수 증가*/
    @Modifying
    @Query("UPDATE FootballMatchBoard fb set fb.countViews =fb.countViews+1 where id=:id ")
    void updateFootballMatchBoardCountView(long id);
}
