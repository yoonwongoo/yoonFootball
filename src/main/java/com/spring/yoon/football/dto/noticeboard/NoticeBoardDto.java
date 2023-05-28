package com.spring.yoon.football.dto.noticeboard;


import com.spring.yoon.football.controller.validator.enumpattern.EnumPattern;
import com.spring.yoon.football.domain.member.Member;
import com.spring.yoon.football.domain.noticeboard.NoticeBoard;
import com.spring.yoon.football.enums.noticecategory.NoticeCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

public class NoticeBoardDto {



    @Getter
    @NoArgsConstructor
    @ToString
    public static class AddRequest{

        @NotBlank(message = "제목을 작성해주세요")
        private String title;
        
        @NotBlank(message = "내용을 작성해주세요")
        private String content;

        @EnumPattern(enumClass = NoticeCategory.class, message = "잘못된 카테고리입니다")
        private NoticeCategory noticeCategory;

        public NoticeBoard toEntity(String title, String content, NoticeCategory noticeCategory, Member member){
                return NoticeBoard.builder()
                        .title(title)
                        .content(content)
                        .noticeCategory(noticeCategory)
                        .build();

        }
    }
    
    /*응답 목록용*/
    @Getter
    @NoArgsConstructor
    public static class ListResponse{

        private long noticeBoardId;

        private String title;

        private NoticeCategory noticeCategory;

        private long countViews;

        public ListResponse(NoticeBoard noticeBoard) {
            this.noticeBoardId = noticeBoard.getId();
            this.title = noticeBoard.getTitle();
            this.noticeCategory = noticeBoard.getNoticeCategory();
            this.countViews =noticeBoard.getCountViews();
        }
    }


    /*응답 상세내용*/
    @Getter
    @NoArgsConstructor
    public static class DetailResponse{

        private long noticeBoardId;

        private String title;

        private String content;

        private NoticeCategory noticeCategory;

        private long countViews;

        public DetailResponse(NoticeBoard noticeBoard) {
            this.noticeBoardId = noticeBoard.getId();
            this.title = noticeBoard.getTitle();
            this.content = noticeBoard.getContent();
            this.noticeCategory = noticeBoard.getNoticeCategory();
            this.countViews =noticeBoard.getCountViews();
        }
    }
    
    
    
    
    
}
