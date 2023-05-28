package com.spring.yoon.football.eventlistener;

import com.spring.yoon.football.event.countviews.CountViewEvent;
import com.spring.yoon.football.factory.countviewsfactory.CountViewsFactory;
import com.spring.yoon.football.service.countviewsservice.CountViewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@Component
@RequiredArgsConstructor
public class CountViewEventListener {

    /*notice, footballMatchBoard*/
    private final CountViewsFactory countViewsFactory;

    @Async
    @EventListener
    @Transactional
    public void noticeCountView(CountViewEvent countViewEvent) {
        log.info("카운트뷰 실행");
        if(checkCookie(countViewEvent, countViewEvent.getRequest(), countViewEvent.getResponse())){
            CountViewsService countViewsService =countViewsFactory.findCountViewsService(countViewEvent.getBoardType());
            countViewsService.countViews(countViewEvent.getId());
        }

    }
    /*쿠키 체크*/
    private boolean checkCookie(CountViewEvent countViewEvent,HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();

        for(Cookie cookie : cookies){
            if(cookie.getName().equals(String.valueOf(countViewEvent.getBoardType())+countViewEvent.getId()))
                return false;
        }

        addCookie(countViewEvent,response);
        return true;
    }
    /*쿠키생성*/
    private void addCookie(CountViewEvent countViewEvent,HttpServletResponse response){
        Cookie cookie = new Cookie(String.valueOf(countViewEvent.getBoardType())+countViewEvent.getId(),String.valueOf(countViewEvent.getId()));
        cookie.setComment("게시글 조회수 중복방지");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60*60*24);
        response.addCookie(cookie);

    }
}
