package com.spring.yoon.football.eventlistener;


import com.spring.yoon.football.domain.member.Member;
import com.spring.yoon.football.domain.notification.Notification;
import com.spring.yoon.football.domain.notification.NotificationRepository;
import com.spring.yoon.football.enums.notification.NotificationType;
import com.spring.yoon.football.event.FootballMatchBoardReplyCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

import static com.spring.yoon.football.controller.SseController.sseEmitters;

@Slf4j
@Component
@RequiredArgsConstructor
public class FootballMatchBoardEventListener {

    private final NotificationRepository notificationRepository;
    /*게시글 업데이트 및 댓글 작성에 대한 이벤트*/
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void handleFootballMatchBoardReplyCreatedEvent(FootballMatchBoardReplyCreatedEvent footballMatchBoardReplyCreatedEvent) throws IOException {
        Member footballBoardWriter = footballMatchBoardReplyCreatedEvent.getFootballMatchBoardReply().getFootballMatchBoard().getMember();
        Member footballBoardReplyWriter =footballMatchBoardReplyCreatedEvent.getFootballMatchBoardReply().getMember();
        log.info("실행실행");

        /*작성자와 댓글 단 사람이 달라야 알림 전송*/
        if(!footballBoardWriter.equals(footballBoardReplyWriter)){
            if(footballBoardWriter.getNotificationSetting().isFootballMatchBoardReplyByWeb()){
                notificationRepository.save(Notification.builder()
                                .title("게시글 댓글 알림")
                                .message(footballBoardReplyWriter.getUsername()+"님이"+"댓글을 작성하였습니다")
                                .notificationType(NotificationType.FOOTBALL_MATCH_BOARD_REPLY)
                                .member(footballBoardWriter)
                                .build());
                /*실시간 알림 보내기*/
                if(sseEmitters.containsKey(footballBoardWriter.getId())){
                   SseEmitter sseEmitter = sseEmitters.get(footballBoardWriter.getId());
                   sseEmitter.send(SseEmitter.event().name("addComment").data("댓글이 달렸습니다!!!!!"));
                }
            }



        }

    }
}
