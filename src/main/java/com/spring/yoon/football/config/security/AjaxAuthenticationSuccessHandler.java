package com.spring.yoon.football.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.yoon.football.dto.ResponseDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.OK;

public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    private final ObjectMapper objectMapper = new ObjectMapper();
    private ResponseEntity entity;

     @Override
     public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
          RequestCache cache = new HttpSessionRequestCache();
          SavedRequest savedRequest = cache.getRequest(request,response);


         String msg="로그인 성공";
         response.setStatus(OK.value());
         response.setContentType(MediaType.APPLICATION_JSON_VALUE);
         response.setCharacterEncoding("UTF-8");

         if(savedRequest!=null){
              entity= new ResponseEntity( new ResponseDto<>(savedRequest.getRedirectUrl() ,msg, OK), OK);

         }else{
              entity= new ResponseEntity( new ResponseDto<>("/" ,msg, OK), OK);

         }

        response.getWriter().write(objectMapper.writeValueAsString(entity));
     }
 }
