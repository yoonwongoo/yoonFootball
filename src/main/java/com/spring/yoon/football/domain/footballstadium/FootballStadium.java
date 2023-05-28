package com.spring.yoon.football.domain.footballstadium;



import com.spring.yoon.football.converter.BooleanConverter;
import com.spring.yoon.football.domain.BaseTimeEntity;
import com.spring.yoon.football.domain.footballsocialmatch.FootballSocialMatch;
import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class FootballStadium extends BaseTimeEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String stadiumName;

    private String location;

    private String locationStreet;
    @Lob
    private String stadiumDetails;

    private String stadiumImageUrl;

    /*샤워가능*/
    @Convert(converter = BooleanConverter.class)
    private boolean ableToShower;

    /*주차가능*/
    @Convert(converter = BooleanConverter.class)
    private boolean ableToParking;

    /*운동화 풋살화 대여가능*/
    @Convert(converter = BooleanConverter.class)
    private boolean ableToRentalShoes;

    /*이용가능시간*/
    private LocalTime availableStartTime;

    /*이용가능시간*/
    private LocalTime availableEndTime;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy ="footballStadium")
    private List<FootballSocialMatch> footballSocialMatches = new ArrayList<>();

    /*풋볼 경기장 수정*/
    public void updateFootballStadium(String location, String locationStreet, String stadiumName, String stadiumDetails,
    String stadiumImageUrl,boolean ableToShower, boolean ableToParking, boolean ableToRentalShoes,
    LocalTime availableStartTime, LocalTime availableEndTime){
          if(!stadiumImageUrl.equals("")){
              this.stadiumImageUrl=stadiumImageUrl;

          }
           this.location=location;
           this.locationStreet=locationStreet;
           this.stadiumName=stadiumName;
           this.stadiumDetails=stadiumDetails;
           this.ableToShower = ableToShower;
           this.ableToParking =ableToParking;
           this.ableToRentalShoes = ableToRentalShoes;
           this.availableStartTime = availableStartTime;
           this.availableEndTime =availableEndTime;

    }

}
