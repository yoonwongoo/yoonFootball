package com.spring.yoon.football.enums.member;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Gender {

    MALE("남성"),
    FEMALE("여성");
    private String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    public String getName(){
        return name();
    }

    /*dto 받을때 enum type으로 받는데 json parsing error 경우 validator 유효성 보다 먼저 exception이 터져서 설정*/
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Gender existByGender(String gender){

        return Stream.of(values()).filter(g->g.name().equals(gender)).findFirst().orElse(null);
    }

    public static List<Gender> getEnumList(){
        return Stream.of(values()).collect(Collectors.toList());
    }
}
