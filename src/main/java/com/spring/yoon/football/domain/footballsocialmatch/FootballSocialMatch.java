package com.spring.yoon.football.domain.footballsocialmatch;

import com.spring.yoon.football.converter.BooleanConverter;
import com.spring.yoon.football.domain.BaseTimeEntity;
import com.spring.yoon.football.domain.footballsocialmatchenrollment.FootballSocialMatchEnrollment;
import com.spring.yoon.football.domain.footballstadium.FootballStadium;
import com.spring.yoon.football.domain.member.Member;
import com.spring.yoon.football.enums.member.SkillLevel;
import com.spring.yoon.football.handler.exception.ErrorCode;
import com.spring.yoon.football.handler.exception.customserviceexception.invalidvalueexception.InvalidValueException;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@ToString
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FootballSocialMatch extends BaseTimeEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /*담당자*/
    @JoinColumn(name = "memberId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    /*매치등록에 필요한 상세내용*/
    @Lob
    private String content;

    @Enumerated(EnumType.STRING)
    private SkillLevel skillLevel;
    /*풋살 구장*/
    @JoinColumn(name="footballStadiumId")
    @ManyToOne(fetch = FetchType.LAZY)
    private FootballStadium footballStadium;

    /*매치의 인원수 */
    private int headCount;
    /*매치 비용*/
    private  int participationFee;

    /*모집 상태*/
    @Convert(converter = BooleanConverter.class)
    private boolean accept;

    /*매치신청*/
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "footballSocialMatch")
    private List<FootballSocialMatchEnrollment> footballSocialMatchEnrollmentList;

    /*경기 날짜*/
    private LocalDate matchDay;
    /*경기 시작시간*/
    private LocalTime startTime;
    /*경기 끝 시간*/
    private LocalTime endTime;

    

    /*매치에 신청 등록*/
    public void addEnrollment(FootballSocialMatchEnrollment footballSocialMatchEnrollment){
        this.footballSocialMatchEnrollmentList.add(footballSocialMatchEnrollment);
        footballSocialMatchEnrollment.attach(this);
    }

    
    
    /*매치 신청이 가능한가*/
    public void isAcceptEnroll(Member member){
        isNotClosed();
        isNotHeadCountExceed();
        isDuplication(member);
    }


    /* 기간이 지났나?*/
    private void isNotClosed(){

        if(!LocalDateTime.of(matchDay,startTime).isAfter(LocalDateTime.now())) throw new InvalidValueException("마감된 매치입니다", ErrorCode.INVALID_INPUT_VALUE);
    }

    /*인원이 넘었나?*/
    private void isNotHeadCountExceed(){
         if(footballSocialMatchEnrollmentList.stream().count()>=headCount)  throw new InvalidValueException("매치 인원초과입니다", ErrorCode.INVALID_INPUT_VALUE);
    }

    /*중복신청인가?*/
    private void isDuplication(Member member){

        for(FootballSocialMatchEnrollment enrollment : this.footballSocialMatchEnrollmentList){
           if (enrollment.getMember().equals(member)) throw new InvalidValueException("이미 신청한 매치입니다",ErrorCode.INVALID_INPUT_VALUE);
        }

    }
}
