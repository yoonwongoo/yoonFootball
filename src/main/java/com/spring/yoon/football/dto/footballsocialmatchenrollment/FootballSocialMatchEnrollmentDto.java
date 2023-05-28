package com.spring.yoon.football.dto.footballsocialmatchenrollment;


import com.spring.yoon.football.domain.footballsocialmatchenrollment.FootballSocialMatchEnrollment;
import com.spring.yoon.football.domain.member.Member;
import com.spring.yoon.football.dto.footballsocialmatch.FootballSocialMatchDto;
import lombok.Getter;

public class FootballSocialMatchEnrollmentDto {


    @Getter
    public static class Response{

        private long id;
        private Member member;
        private FootballSocialMatchDto.Response footballSocialMatch;


        public Response(FootballSocialMatchEnrollment footballSocialMatchEnrollment) {
            this.id = footballSocialMatchEnrollment.getId();
            this.member = footballSocialMatchEnrollment.getMember();
            this.footballSocialMatch = new FootballSocialMatchDto.Response(footballSocialMatchEnrollment.getFootballSocialMatch());
        }
    }
}
