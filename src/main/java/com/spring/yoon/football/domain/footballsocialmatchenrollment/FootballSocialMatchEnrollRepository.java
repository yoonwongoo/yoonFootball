package com.spring.yoon.football.domain.footballsocialmatchenrollment;

import com.spring.yoon.football.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FootballSocialMatchEnrollRepository extends JpaRepository<FootballSocialMatchEnrollment,Long> {

    @Query(value = "SELECT fse FROM FootballSocialMatchEnrollment fse join fetch fse.footballSocialMatch fs where fse.member = :member",
            countQuery="SELECT COUNT(fse) FROM FootballSocialMatchEnrollment fse where fse.member=:member")
    Page<FootballSocialMatchEnrollment> findByMyFootballSocialMatchEnrollmentList(Member member, Pageable pageable);
}
