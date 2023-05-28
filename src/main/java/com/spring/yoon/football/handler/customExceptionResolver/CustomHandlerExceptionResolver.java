package com.spring.yoon.football.handler.customExceptionResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.yoon.football.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.*;

@Slf4j
public class CustomHandlerExceptionResolver implements HandlerExceptionResolver {

    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.info("커스텀 에러");
        try{
            if(ex instanceof HttpRequestMethodNotSupportedException){
                String accept = request.getHeader("accept");
                if(MediaType.APPLICATION_JSON_VALUE.equals(accept)){
                    response.setStatus(METHOD_NOT_ALLOWED.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setCharacterEncoding("UTF-8");
                    ResponseEntity<ResponseDto> responseEntity = new ResponseEntity<>(new ResponseDto("null",ex.getMessage(), METHOD_NOT_ALLOWED),METHOD_NOT_ALLOWED);
                    response.getWriter().write(objectMapper.writeValueAsString(responseEntity));
                    return new ModelAndView();
                }else {
                 return new ModelAndView("/error/405");
                }
            }
            if(ex instanceof TypeMismatchException){
                String accept = request.getHeader("accept");
                if(MediaType.APPLICATION_JSON_VALUE.equals(accept)){
                    response.setStatus(BAD_REQUEST.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setCharacterEncoding("UTF-8");
                    ResponseEntity<ResponseDto> responseEntity = new ResponseEntity<>(new ResponseDto("null",ex.getMessage(), BAD_REQUEST),BAD_REQUEST);
                    response.getWriter().write(objectMapper.writeValueAsString(responseEntity));
                    return new ModelAndView();
                }else {
                 return new ModelAndView("/error/400");
                }
            }

            if(ex instanceof NoSuchElementException){
            String accept = request.getHeader("accept");
            if(MediaType.APPLICATION_JSON_VALUE.equals(accept)){
                response.setStatus(NOT_FOUND.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("UTF-8");
                ResponseEntity<ResponseDto> responseEntity = new ResponseEntity<>(new ResponseDto("null",ex.getMessage(), NOT_FOUND),NOT_FOUND);
                response.getWriter().write(objectMapper.writeValueAsString(responseEntity));
                return new ModelAndView();
            }else {
             return new ModelAndView("/error/404");
            }
        }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return null;
    }
}
