package com.spring.yoon.football.enums.paytype;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PayType {
    
    KAKAO("카카오"), 
    NAVER("네이버"),
    TOSS("토스"),
    NULL("알 수없음");
    private String type;

    PayType(String type) {
        this.type = type;
    }


    @JsonCreator
    public static PayType existPayType(String payType){
        System.out.println(payType);
       return Arrays.stream(PayType.values()).filter(v->v.getType().equals(payType)).findFirst().orElse(null);
    }

    public static PayType convertPayType(String payType){
        return Arrays.stream(PayType.values()).filter(p->p.getType().equals(payType)).findFirst().orElse(null);
    }
}
