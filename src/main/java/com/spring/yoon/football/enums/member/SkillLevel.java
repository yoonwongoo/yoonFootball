package com.spring.yoon.football.enums.member;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum SkillLevel {

    HIGH("상"),MIDDLE("중"),LOW("하");

    private String value;

    SkillLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getName(){
        return name();
    }

    @JsonCreator
    public static SkillLevel existSkillLevel(String skill){
        return Stream.of(SkillLevel.values()).filter(s->s.name().equals(skill.toUpperCase())).findFirst().orElse(null);

    }

    public static List<SkillLevel> getEnumList(){
        return Stream.of(SkillLevel.values()).collect(Collectors.toList());

    }


}
