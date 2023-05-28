package com.spring.yoon.football.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long>,MemberRepositoryCustom {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    boolean existsByProfileNickName(String nickName);
    Member findByUsername(String username);

}
