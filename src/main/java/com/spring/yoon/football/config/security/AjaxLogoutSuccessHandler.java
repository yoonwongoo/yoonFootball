package com.spring.yoon.football.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.yoon.football.dto.ResponseDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.OK;

public class AjaxLogoutSuccessHandler implements LogoutSuccessHandler {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        
          String msg="로그아웃 성공";
          response.setStatus(OK.value());
          response.setContentType(MediaType.APPLICATION_JSON_VALUE);
          response.setCharacterEncoding("UTF-8");

         ResponseEntity entity= new ResponseEntity( new ResponseDto<>(null,"로그아웃 성공", OK), OK);
         response.getWriter().write(objectMapper.writeValueAsString(entity));
    }
}
