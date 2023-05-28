package com.spring.yoon.football.service.notificationservice;


import com.spring.yoon.football.domain.member.Member;
import com.spring.yoon.football.domain.notification.NotificationRepository;
import com.spring.yoon.football.dto.notification.NotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    
    /*알림조회하기*/
    public List<NotificationDto.Response> findNotificationList(Member member, long lastId){
        return notificationRepository.findByNotificationList(member,lastId).stream().map(NotificationDto.Response::new)
                .collect(Collectors.toList());

    }
    
}
