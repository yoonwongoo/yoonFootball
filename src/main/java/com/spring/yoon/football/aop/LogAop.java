package com.spring.yoon.football.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class LogAop {


    @Pointcut("within(com.spring.yoon.football.controller.*)")
    private void logPointCut(){}

    @Pointcut("!within(com.spring.yoon.football.controller.SseController)")
    private void excludeLogPointCut(){}

    @Before("logPointCut() && excludeLogPointCut()")
    public void logAop(JoinPoint joinpoint){
        log.info("로그인 아이디:{} --- 실행 메서드:{}",getPrincipal(),joinpoint.getSignature().getName());
    }


    private String getPrincipal() {
        AbstractAuthenticationToken authentication = (AbstractAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        return authentication.getName();
    }
}