package com.spring.yoon.football.enums.noticecategory;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum NoticeCategory {

    SOCIAL_MATCH("소셜매치")
    ,CASH("캐시")
    ,SYSTEM_ERROR("시스템오류")
    ,NOTICE("공지사항")
    ,TIP("팁")
    ,UPDATE("업데이트")

    ,EVENT("이벤트");

    private String value;

    NoticeCategory(String value) {
        this.value = value;

    }
    
    
    public static NoticeCategory convertNoticeCategory(String noticeCategory){

        return Arrays.stream(NoticeCategory.values()).filter(n->n.getValue().equals(noticeCategory))
                .findFirst().orElse(null);
    }

    @JsonCreator
    public static NoticeCategory existNoticeCategory(String noticeCategory){

        return Arrays.stream(NoticeCategory.values()).filter(n-> n.name().equals(noticeCategory))
                .findFirst().orElse(null);
    }


    public static List<NoticeCategory> getEnumList(){

        return Arrays.stream(NoticeCategory.values()).collect(Collectors.toList());

    }
}
