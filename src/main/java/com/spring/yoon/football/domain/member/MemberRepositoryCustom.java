package com.spring.yoon.football.domain.member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryCustom{

    Page<Member> findByMemberList(Pageable pageable);
}
