package com.spring.yoon.football.domain.notification;

import com.spring.yoon.football.domain.member.Member;

import java.util.List;

public interface NotificationRepositoryCustom {

    List<Notification> findByNotificationList(Member member, long lastId);
}
