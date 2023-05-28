package com.spring.yoon.football.enums.member;


import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum PreferPosition {

    DEFENDER("수비"),
    MIDFIELDER("미드필더"),
    STRIKER("공격");

    
    private String value;

    PreferPosition(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getName(){
        return name();
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static PreferPosition existPreferPosition(String position){

        return Stream.of(values()).filter(p->p.name().equals(position)).findFirst().orElse(null);

    }

    public static List<PreferPosition> getEnumList(){
        return Stream.of(values()).collect(Collectors.toList());
    }
}
