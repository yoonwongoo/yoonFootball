package com.spring.yoon.football.domain.member;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.spring.yoon.football.domain.member.QMember.member;


@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    
    /*회원목록*/
    @Override
    public Page<Member> findByMemberList(Pageable pageable) {
        List<Member> list = jpaQueryFactory.select(member)
                .from(member)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = jpaQueryFactory.select(member.count())
                .from(member);

        return PageableExecutionUtils.getPage(list,pageable,count::fetchOne);
    }
}
