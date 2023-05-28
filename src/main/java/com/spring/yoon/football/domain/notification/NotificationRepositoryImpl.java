package com.spring.yoon.football.domain.notification;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.yoon.football.domain.member.Member;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.spring.yoon.football.domain.notification.QNotification.notification;


@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Notification> findByNotificationList(Member member, long lastId) {
          return jpaQueryFactory.select(notification)
                .from(notification)
                .where(notification.member.eq(member), eqLastId(lastId))
                .orderBy(notification.id.desc())
                .limit(40)
                .fetch();

    }

    /*lastId가 있나 없나?*/
    private BooleanExpression eqLastId(long lastId){
        if(lastId==0){
            return null;
        }
        return notification.id.lt(lastId);
    }
}
