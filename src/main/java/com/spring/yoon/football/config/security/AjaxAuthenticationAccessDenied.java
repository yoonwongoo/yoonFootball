package com.spring.yoon.football.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.yoon.football.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Slf4j
public class AjaxAuthenticationAccessDenied implements AccessDeniedHandler {

    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("Responding with 403 status code");
        log.info(accessDeniedException.getMessage());
        log.info(accessDeniedException.getLocalizedMessage());
        String ajaxHeader = request.getHeader("X-Requested-With");
        final String ajaxHeaderValue ="XMLHttpRequest";
        boolean isAjax = ajaxHeaderValue.equals(ajaxHeader);
        if(!isAjax){
            response.sendError(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase());
        }else{
            response.setStatus(FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            ResponseEntity<ResponseDto> responseEntity = new ResponseEntity<>(new ResponseDto("null",accessDeniedException.getMessage(), FORBIDDEN),FORBIDDEN);
            response.getWriter().write(objectMapper.writeValueAsString(responseEntity));
        }

    }
}
