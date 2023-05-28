package com.spring.yoon.football.dto.footballmatchboard;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.yoon.football.controller.validator.enumpattern.EnumPattern;
import com.spring.yoon.football.domain.footballmatchboard.FootballMatchBoard;
import com.spring.yoon.football.domain.member.Member;
import com.spring.yoon.football.enums.member.SkillLevel;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/*풋볼 매치 구하는글 DTO*/
public class FootballMatchBoardDto {


    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    /*글 등록 request*/
    public static class AddRequest {

        /*실력*/
        @EnumPattern(enumClass = SkillLevel.class, message = "실력을 입력해주세요")
        private SkillLevel skillLevel;

        /*위치*/
        @Size(max = 255, message = "255자 미만으로 작성해주세요")
        @NotBlank(message = "위치를 작성해주세요")
        private String location;

        /*위치*/
        @Size(max = 255, message = "255자 미만으로 작성해주세요")
        @NotBlank(message = "도로명주소를 작성해주세요")
        private String locationStreet;

        /*상세내용*/
        @NotBlank(message = "내용을 작성해주세요")
        private String content;

        /*참가비*/

        @Range(min = 0, message = "참가비를 입력해주세요")
        private int participationFee;

        /*경기 날짜*/
        @NotNull(message = "경기날짜를 입력해주세요")
        private LocalDate matchDay;

        /*경기 시작시간*/
        @NotNull(message = "경기시작시간을 입력해주세요")
        private LocalTime startTime;

        /*경기 마감시간*/
        @NotNull(message = "경기마감시간을 입력해주세요")
        private LocalTime endTime;


        public FootballMatchBoard toEntity(Member member) {
            return FootballMatchBoard.builder()
                    .skillLevel(this.skillLevel)
                    .location(this.location)
                    .locationStreet(this.locationStreet)
                    .content(this.content)
                    .participationFee(this.participationFee)
                    .matchDay(this.matchDay)
                    .startTime(this.startTime)
                    .endTime(this.endTime)
                    .recruitment(true)
                    .member(member)
                    .build();

        }
    }
    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    /*글 수정 request*/
    public static class ModifyRequest {

        @Min(value = 0,message = "게시글번호는 0이하 일 수 없습니다")
        @NotNull(message = "게시글 번호는 빈값일 수 없습니다")
        private long footballMatchBoardId;
        /*실력*/
        @EnumPattern(enumClass = SkillLevel.class, message = "실력을 입력해주세요")
        private SkillLevel skillLevel;

        /*위치*/
        @Size(max = 255, message = "255자 미만으로 작성해주세요")
        @NotBlank(message = "위치를 작성해주세요")
        private String location;

        /*위치*/
        @Size(max = 255, message = "255자 미만으로 작성해주세요")
        @NotBlank(message = "도로명주소를 작성해주세요")
        private String locationStreet;

        /*상세내용*/
        @NotBlank(message = "내용을 작성해주세요")
        private String content;

        /*참가비*/

        @Range(min = 0, message = "참가비를 입력해주세요")
        private int participationFee;

        /*경기 날짜*/
        @NotNull(message = "경기날짜를 입력해주세요")
        private LocalDate matchDay;

        /*경기 시작시간*/
        @NotNull(message = "경기시작시간을 입력해주세요")
        private LocalTime startTime;

        /*경기 마감시간*/
        @NotNull(message = "경기마감시간을 입력해주세요")
        private LocalTime endTime;

    }

    /*상세보기 dto*/
    @NoArgsConstructor
    @ToString
    @Getter
    public static class Detail {

        private long id;
        private long memberId;
        private String name;
        private SkillLevel skillLevel;

        /*위치*/
        private String location;

        /*위치*/
        private String locationStreet;

        /*상세내용*/
        private String content;

        /*참가비*/
        private int participationFee;

        /*경기 날짜*/
        private LocalDate matchDay;

        /*경기 시작시간*/
        private LocalTime startTime;

        /*경기 마감시간*/
        private LocalTime endTime;
        /*조회수*/            
        private  long countViews;
        
        /*모집 여부*/
        private boolean recruitment;

        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;
        public Detail(FootballMatchBoard footballMatchBoard) {
            this.id = footballMatchBoard.getId();
            this.memberId =footballMatchBoard.getMember().getId();
            this.name = footballMatchBoard.getMember().getName();
            this.skillLevel = footballMatchBoard.getSkillLevel();
            this.location = footballMatchBoard.getLocation();
            this.locationStreet = footballMatchBoard.getLocationStreet();
            this.content = footballMatchBoard.getContent();
            this.participationFee = footballMatchBoard.getParticipationFee();
            this.matchDay = footballMatchBoard.getMatchDay();
            this.startTime = footballMatchBoard.getStartTime();
            this.endTime = footballMatchBoard.getEndTime();
            this.countViews= footballMatchBoard.getCountViews();
            this.recruitment = footballMatchBoard.isRecruitment();
            this.createdDate =footballMatchBoard.getCreatedDate();
            this.modifiedDate = footballMatchBoard.getModifiedDate();
        }



    }

    /*목록 Slice dto*/
    @ToString
    @NoArgsConstructor
    @Getter
    public static class SliceList {

        public SliceList(FootballMatchBoard footballMatchBoard) {
            this.footballBoardId = footballMatchBoard.getId();
            this.name = footballMatchBoard.getMember().getName();
            this.skillLevel = footballMatchBoard.getSkillLevel().getValue();
            this.location = footballMatchBoard.getLocation();
            this.locationStreet = footballMatchBoard.getLocationStreet();
            this.participationFee = footballMatchBoard.getParticipationFee();
            this.matchDay = footballMatchBoard.getMatchDay();
            this.startTime = footballMatchBoard.getStartTime();
            this.endTime = footballMatchBoard.getEndTime();
            this.createdDate = footballMatchBoard.getCreatedDate();
            this.isRecruitment = footballMatchBoard.isRecruitment();
        }


        private long footballBoardId;

        private String name;

        private String skillLevel;
        /*위치*/
        private String location;

        /*위치*/
        private String locationStreet;

        /*참가비*/
        private int participationFee;

        /*경기 날짜*/
        private LocalDate matchDay;

        /*경기 시작시간*/
        @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "HH:ss")
        private LocalTime startTime;

        /*경기 마감시간*/
        @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "HH:ss")
        private LocalTime endTime;
        @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:ss")
        private LocalDateTime createdDate;

        /*모집여부*/
        private boolean isRecruitment;

    }


    /*검색조건 DTO*/
    @Getter
    @ToString
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Search{

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate matchDay;

        private SkillLevel skillLevel;

        private String hide;

        private long id;
    }


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    /*내가 쓴 글 dto*/
    public static class MyFootballMatchBoard{

        private long footballMatchBoardId;
        private String username;
        private String location;
        private LocalDate matchDay;
        private LocalTime startTime;
        private LocalTime endTime;

        private LocalDateTime createdDate;

        public MyFootballMatchBoard(FootballMatchBoard footballMatchBoard) {
            this.footballMatchBoardId = footballMatchBoard.getId();
            this.username = footballMatchBoard.getMember().getUsername();
            this.location = footballMatchBoard.getLocation();
            this.matchDay = footballMatchBoard.getMatchDay();
            this.startTime= footballMatchBoard.getStartTime();
            this.endTime = footballMatchBoard.getEndTime();
            this.createdDate = footballMatchBoard.getCreatedDate();
        }
    }

}
