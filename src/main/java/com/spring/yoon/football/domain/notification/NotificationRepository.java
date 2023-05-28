package com.spring.yoon.football.domain.notification;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Long>,NotificationRepositoryCustom {
}
