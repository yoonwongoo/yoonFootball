package com.spring.yoon.football.dto.footballsocialmatch;


import com.spring.yoon.football.controller.validator.enumpattern.EnumPattern;
import com.spring.yoon.football.domain.footballsocialmatch.FootballSocialMatch;
import com.spring.yoon.football.domain.footballstadium.FootballStadium;
import com.spring.yoon.football.domain.member.Member;
import com.spring.yoon.football.dto.footballstadium.FootBallStadiumDto;
import com.spring.yoon.football.enums.member.SkillLevel;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.ObjectUtils;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public class FootballSocialMatchDto {



    /*매치 등록*/
    @NoArgsConstructor
    @Getter
    @Builder
    @AllArgsConstructor
    public static class AddRequest{

        /*풋살 경기장 Id*/
        @NotNull(message = "빈값일 수 없습니다")
        @Min(value =1)
        private long footballStadiumId;
        /*매치의 인원수 */
        @Range(min=2,max=12,message = "2명 이상 12명 이하여야합니다")
        private int headCount;
        /*매치 상세정보*/
        @NotBlank(message = "매치등록사항을 입력해주세요")
        private String content;
        
        /*인당 참가비용*/
        @NotNull(message = "매치 참가비용은 빈 칸일 수 없습니다")
        @Range(min=0,max=10000,message = "참가비용을 0~10000원 사이로 작성해주세요")
        private int participationFee;
        /*경기 날짜*/
        @NotNull(message = "경기일자를 작성해주세요")
        private LocalDate matchDay;
        /*경기 시작시간*/
        @NotNull(message = "시작시간을 작성해주세요")
        private LocalTime startTime;
        /*경기 끝 시간*/
        @NotNull(message = "마감시간을 작성해주세요")
        private LocalTime endTime;

        @EnumPattern(enumClass = SkillLevel.class,message = "잘못된 실력입니다")
        private SkillLevel skillLevel;

        /*toEntity*/
        public FootballSocialMatch toEntity(Member member, FootballStadium footballStadium){

            return FootballSocialMatch.builder()
                    .member(member)
                    .participationFee(participationFee)
                    .content(content)
                    .matchDay(matchDay)
                    .startTime(startTime)
                    .endTime(endTime)
                    .accept(true)
                    .headCount(headCount)
                    .skillLevel(skillLevel)
                    .footballStadium(footballStadium)
                    .build();
        }

    }
    
    /*응답 구장정보까지 보여줄까..? 말까?*/
    @NoArgsConstructor
    @Getter
    @ToString
    public static class Response{
        private long id;
        /*담당자*/
        private Member member;
        /*매치의 인원수 */
        private int headCount;
        /*매치 실력*/
        private SkillLevel skillLevel;
        /*매치 가입가격*/
        private int participationFee;

        private String content;
        
        /*모집 상태*/
        private boolean accept;
        /*경기 날짜*/
        private LocalDate matchDay;
        /*경기 시작시간*/
        private LocalTime startTime;
        /*경기 끝 시간*/
        private LocalTime endTime;

        /*풋살 구장*/
        private FootBallStadiumDto.Response footballStadium;

        public Response(FootballSocialMatch footballSocialMatch){
            this.id=footballSocialMatch.getId();
            this.member=footballSocialMatch.getMember();
            this.headCount=footballSocialMatch.getHeadCount();
            this.skillLevel=footballSocialMatch.getSkillLevel();
            this.participationFee=footballSocialMatch.getParticipationFee();
            this.content=footballSocialMatch.getContent();
            this.accept=footballSocialMatch.isAccept();
            this.matchDay=footballSocialMatch.getMatchDay();
            this.startTime=footballSocialMatch.getStartTime();
            this.endTime=footballSocialMatch.getEndTime();
            this.footballStadium= new FootBallStadiumDto.Response(footballSocialMatch.getFootballStadium());

        }

    }

    /*매치 검색*/
    @Getter
    public static class Search{
        /*날짜 검색*/
        @DateTimeFormat(pattern="yyyy-MM-dd")
        private LocalDate matchDay;

        public Search(LocalDate matchDay) {
            this.matchDay = ObjectUtils.isEmpty(matchDay) ? LocalDate.now() : matchDay;
        }
    }
    
    
    /*매치등록 폼*/
    @Getter
    public static class ResponseForm{

        private long footballStadiumId;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate matchDay;

        @DateTimeFormat(pattern = "HH:mm")
        private LocalTime startTime;
        /*강제로 +1시간*/
        private LocalTime endTime;


        public ResponseForm(long footballStadiumId, LocalDate matchDay, LocalTime startTime, LocalTime endTime) {
            this.footballStadiumId = footballStadiumId;
            this.matchDay = matchDay;
            this.startTime = startTime;
            this.endTime = startTime.plusHours(1);
        }
    }


}
