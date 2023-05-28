package com.spring.yoon.football.controller.api;


import com.spring.yoon.football.config.security.AuthMember;
import com.spring.yoon.football.domain.member.Member;
import com.spring.yoon.football.dto.ResponseDto;
import com.spring.yoon.football.service.notificationservice.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ApiNotificationController {


    private final NotificationService notificationService;

    /*알림조회*/
    @GetMapping({"/notification","/notification/{lastId}"})
    public ResponseEntity<?> notificationList(@PathVariable(required = false) Long lastId, @AuthMember Member member){
        if(lastId==null){
            lastId=0L;
        }


        return new ResponseEntity<>(new ResponseDto<>(notificationService.findNotificationList(member,lastId)
        ,"알림조회 완료", HttpStatus.OK),HttpStatus.OK);
    }
}
