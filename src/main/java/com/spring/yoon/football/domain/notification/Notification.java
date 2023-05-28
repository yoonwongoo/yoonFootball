package com.spring.yoon.football.domain.notification;


import com.spring.yoon.football.domain.BaseTimeEntity;
import com.spring.yoon.football.domain.member.Member;
import com.spring.yoon.football.enums.notification.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Notification extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String message;

    @JoinColumn(name="memberId")
    @ManyToOne(fetch=FetchType.LAZY)
    private Member member;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;
}
