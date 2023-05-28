package com.spring.yoon.football.domain.noticeboard;


import com.spring.yoon.football.converter.NoticeCategoryConverter;
import com.spring.yoon.football.domain.BaseTimeEntity;
import com.spring.yoon.football.domain.member.Member;
import com.spring.yoon.football.enums.noticecategory.NoticeCategory;
import lombok.*;

import javax.persistence.*;



@Builder
@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoticeBoard extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @Lob
    private String content;
    
    /*게시글 카테고리*/
    @Convert(converter = NoticeCategoryConverter.class)
    private NoticeCategory noticeCategory;

    @JoinColumn(name = "memberId")
    @ManyToOne(fetch=FetchType.LAZY)
    private Member member;

    /*조회수*/
    private long countViews;
}
