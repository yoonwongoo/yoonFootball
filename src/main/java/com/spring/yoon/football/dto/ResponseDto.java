package com.spring.yoon.football.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class ResponseDto<T> {

    private T body;
    private String message;
    private HttpStatus status;


    public ResponseDto(T body, String message, HttpStatus status) {
        this.body = body;
        this.message = message;
        this.status = status;
    }
}
