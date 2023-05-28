package com.spring.yoon.football.domain.footballsocialmatch;

import com.spring.yoon.football.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FootballSocialMatchRepository extends JpaRepository<FootballSocialMatch,Long>,FootballSocialMatchRepositoryCustom {

   @Query("SELECT f FROM FootballSocialMatch f join fetch f.footballStadium fs join fetch f.member m where f.id =:id")
   Optional<FootballSocialMatch> findByFootballSocialMatchDetails(long id);

   @Query("SELECT f FROM FootballSocialMatch f join fetch f.footballStadium fs where f.member =:member and f.matchDay = CURRENT_DATE")
   List<FootballSocialMatch> findByMyScheduleSocialMatchList(Member member);

   /*매치에 대한 신청에 대한 select 이것을 Details랑 합칠지 고민...*/
   @Query("SELECT f FROM FootballSocialMatch f LEFT JOIN FETCH f.footballSocialMatchEnrollmentList fs LEFT JOIN FETCH fs.member m WHERE f.id=:id")
   Optional<FootballSocialMatch> findByFootballSocialMatch(long id);

}
