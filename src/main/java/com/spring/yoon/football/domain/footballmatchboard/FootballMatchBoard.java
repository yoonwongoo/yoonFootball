package com.spring.yoon.football.domain.footballmatchboard;



import com.spring.yoon.football.converter.BooleanConverter;
import com.spring.yoon.football.domain.BaseTimeEntity;
import com.spring.yoon.football.domain.footballmatchboardreply.FootballMatchBoardReply;
import com.spring.yoon.football.domain.member.Member;
import com.spring.yoon.football.enums.member.SkillLevel;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/*사람들이 구하는 글 팀이나 용병 구해요*/
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class FootballMatchBoard extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JoinColumn(name = "memberId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "footballMatchBoard")
    private List<FootballMatchBoardReply> footballMatchBoardReplys = new ArrayList<>();
    /*실력*/
    @Enumerated(EnumType.STRING)
    private SkillLevel skillLevel;

    /*위치*/
    private String location;

    /*도로명 주소*/
    private String locationStreet;

    /*상세내용*/
    @Lob
    private String content;

    /*참가비*/
    private int participationFee;

    /*경기 날짜*/
    private LocalDate matchDay;

    /*경기 시작시간*/
    private LocalTime startTime;

    /*경기 끝시간*/
    private LocalTime endTime;

    /*모집 여부*/
    @Convert(converter = BooleanConverter.class)
    private boolean recruitment;

    private long countViews;


    public void updateFootballMatchBoard(SkillLevel skillLevel, String location, String locationStreet, String content,
                                         int participationFee, LocalDate matchDay, LocalTime startTime, LocalTime endTime){
        this.skillLevel=skillLevel;
        this.location =location;
        this.locationStreet =locationStreet;
        this.content = content;
        this.participationFee=participationFee;
        this.matchDay=matchDay;
        this.startTime=startTime;
        this.endTime =endTime;


    }

}


