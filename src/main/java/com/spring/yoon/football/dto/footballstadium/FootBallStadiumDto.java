package com.spring.yoon.football.dto.footballstadium;

import com.spring.yoon.football.domain.footballstadium.FootballStadium;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.ObjectUtils;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FootBallStadiumDto {
    /*풋살구장등록*/
    @NoArgsConstructor
    @Getter
    public static class AddRequest{
        @NotBlank(message = "경기장이름을 입력해주세요")
        private String stadiumName;
        @NotBlank(message = "경기장위치를 입력해주세요")
        private String location;
        @NotBlank(message = "경기장도로명주소를 입력해주세요")
        private String locationStreet;
        @NotBlank(message = "경기장 상세정보를 입력해주세요")
        private String stadiumDetails;

        private String stadiumImageUrl;

        private LocalTime availableStartTime;

        private LocalTime availableEndTime;

        /*샤워가능*/
        private boolean ableToShower;

        /*주차가능*/
        private boolean ableToParking;

        /*운동화 풋살화 대여가능*/
        private boolean ableToRentalShoes;


    public FootballStadium toEntity(String stadiumImageUrl){
       return FootballStadium.builder()
               .stadiumName(this.stadiumName)
               .location(this.location)
               .locationStreet(this.locationStreet)
               .stadiumDetails(this.stadiumDetails)
               .stadiumImageUrl(stadiumImageUrl)
               .availableStartTime(this.availableStartTime)
               .availableEndTime(this.availableEndTime)
               .ableToShower(ableToShower)
               .ableToParking(ableToParking)
               .ableToRentalShoes(ableToRentalShoes)
               .build();
    }
    }

    /*풋볼구장 수정 DTO*/
    @NoArgsConstructor
    @Getter
    @ToString
    @AllArgsConstructor
    public static class ModifyRequest{
        private long footballStadiumId;
        private String location;
        private String locationStreet;
        private String stadiumName;
        private String stadiumDetails;
        private String stadiumImageUrl;
        private boolean ableToShower;
        private boolean ableToParking;
        private boolean ableToRentalShoes;
        private LocalTime availableStartTime;
        private LocalTime availableEndTime;
    }


    @NoArgsConstructor
    @Getter
    @ToString
    public static class Response{

        private long id;

        private String stadiumName;

        private String location;

        private String locationStreet;

        /*샤워가능*/
        private boolean ableToShower;

        /*주차가능*/
        private boolean ableToParking;

        /*운동화 풋살화 대여가능*/
        private boolean ableToRentalShoes;

        private String stadiumDetails;

        private String stadiumImageUrl;

        private LocalTime availableStartTime;

        private LocalTime availableEndTime;

        private LocalDateTime createdDate;

        public Response(FootballStadium footballStadium){
            this.id=footballStadium.getId();
            this.stadiumName= footballStadium.getStadiumName();
            this.location= footballStadium.getLocation();
            this.locationStreet= footballStadium.getLocationStreet();
            this.ableToShower=footballStadium.isAbleToShower();
            this.ableToParking=footballStadium.isAbleToParking();
            this.ableToRentalShoes=footballStadium.isAbleToRentalShoes();
            this.stadiumDetails = footballStadium.getStadiumDetails();
            this.stadiumImageUrl = footballStadium.getStadiumImageUrl();
            this.availableStartTime = footballStadium.getAvailableStartTime();
            this.availableEndTime = footballStadium.getAvailableEndTime();
            this.createdDate = footballStadium.getCreatedDate();

        }
    }
    @NoArgsConstructor
    @Getter
    @ToString
    /*매치등록이 가능한 구장목록*/
    public static class FootballStadiumMatchResponse{
        /*구장 아이디*/
        private long footballStadiumId;
        /*구장 이름*/
        private String stadiumName;
        /*구장 위치*/
        private String location;
        /*구장 이미지*/
        private String stadiumImageUrl;
        /*이용시간*/
        private LocalTime availableStartTime;
        /*이용시간*/
        private LocalTime availableEndTime;
        /*구장 매치 등록 시간 모음*/
        private List<LocalTime> footballSocialMatchList;



        public FootballStadiumMatchResponse(long footballStadiumId, String stadiumName, String location, String stadiumImageUrl, LocalTime availableStartTime, LocalTime availableEndTime, List<LocalTime> footballSocialMatchList) {
            this.footballStadiumId = footballStadiumId;
            this.stadiumName = stadiumName;
            this.location = location;
            this.stadiumImageUrl = stadiumImageUrl;
            this.availableStartTime = availableStartTime;
            this.availableEndTime = availableEndTime;

            List<LocalTime> localTimes = new ArrayList<>();
            for(int i=availableStartTime.getHour(); i<=availableEndTime.getHour();i++){
                localTimes.add(LocalTime.of(i,00));
            }

            this.footballSocialMatchList = localTimes.stream().filter(l->footballSocialMatchList.stream().noneMatch(n->{
                return l.equals(n);
            })).collect(Collectors.toList());

        }
    }


    /*검색조건*/
    @Getter
    public static class Search{

        /*날짜*/
        @DateTimeFormat(pattern="yyyy-MM-dd")
        public LocalDate matchDay;

        public Search(LocalDate matchDay) {
            this.matchDay = ObjectUtils.isEmpty(matchDay) ? LocalDate.now() : matchDay;
        }
    }
}
