package com.spring.yoon.football.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.yoon.football.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
@Slf4j
public class AjaxAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.info("401에러");
        String ajaxHeader = request.getHeader("X-Requested-With");
        final String ajaxHeaderValue ="XMLHttpRequest";
        boolean isAjax = ajaxHeaderValue.equals(ajaxHeader);
        if(!isAjax){
            response.sendRedirect("/sign-in");
        }else{
            response.setStatus(UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");

            ResponseEntity entity= new ResponseEntity(new ResponseDto<>(null, "세션이 만료되었습니다", UNAUTHORIZED), UNAUTHORIZED);
            response.getWriter().write(objectMapper.writeValueAsString(entity));
        }

    }
}
