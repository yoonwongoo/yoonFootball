package com.spring.yoon.football.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.yoon.football.dto.ResponseDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler{

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errMsg ="";
           response.setStatus(UNAUTHORIZED.value());
           response.setContentType(MediaType.APPLICATION_JSON_VALUE);
           response.setCharacterEncoding("UTF-8");
           if(exception instanceof BadCredentialsException){
               errMsg = "비밀번호가 일치하지 않습니다";
           } else if(exception instanceof UsernameNotFoundException){
               errMsg = "존재하지 않는 아이디입니다";
           } else if(exception instanceof AuthenticationServiceException){
               errMsg = "ajax 헤더 토큰이 필요합니다";
           }
        ResponseEntity entity= new ResponseEntity( new ResponseDto<>(null, errMsg, UNAUTHORIZED), UNAUTHORIZED);
           response.getWriter().write(objectMapper.writeValueAsString(entity));
       }
    }

