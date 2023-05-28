package com.spring.yoon.football.service.footballsocialmatchenrollservice;


import com.spring.yoon.football.domain.footballsocialmatch.FootballSocialMatch;
import com.spring.yoon.football.domain.footballsocialmatch.FootballSocialMatchRepository;
import com.spring.yoon.football.domain.footballsocialmatchenrollment.FootballSocialMatchEnrollRepository;
import com.spring.yoon.football.domain.footballsocialmatchenrollment.FootballSocialMatchEnrollment;
import com.spring.yoon.football.domain.member.Member;
import com.spring.yoon.football.domain.payment.Payment;
import com.spring.yoon.football.dto.footballsocialmatchenrollment.FootballSocialMatchEnrollmentDto;
import com.spring.yoon.football.handler.exception.ErrorCode;
import com.spring.yoon.football.handler.exception.customserviceexception.entitynotfoundexception.FootballSocialMatchNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FootballSocialMatchEnrollService {

    private final FootballSocialMatchRepository footballSocialMatchRepository;

    private final FootballSocialMatchEnrollRepository footballSocialMatchEnrollRepository;

    /*나의 소셜매치 보기리스트 */
    @Transactional(readOnly = true)
    public Page<FootballSocialMatchEnrollmentDto.Response> findByMyFootballSocialMatchList(Member member, Pageable pageable){
        return footballSocialMatchEnrollRepository.findByMyFootballSocialMatchEnrollmentList(member, pageable)
                .map(FootballSocialMatchEnrollmentDto.Response::new);
    }

    /*등록*/
    @Transactional
    public void addFootballSocialMatchEnroll(long id , Member member, Payment payment){
        FootballSocialMatch footballSocialMatch = footballSocialMatchRepository.findByFootballSocialMatch(id).orElseThrow(()-> new FootballSocialMatchNotFoundException("존재하지않는매치입니다", ErrorCode.ENTITY_NOT_FOUND));

        /*매치 신청이 가능한지 유효성 체크*/
/*
        validateEnrollment(footballSocialMatch,member);
*/


        /*등록*/
        FootballSocialMatchEnrollment footballSocialMatchEnrollment = FootballSocialMatchEnrollment.of(member,footballSocialMatch,payment);
        footballSocialMatch.addEnrollment(footballSocialMatchEnrollment);
        footballSocialMatchEnrollRepository.save(footballSocialMatchEnrollment);

    }



    /*모임참가 가능 유효성체크*//*
    private void validateEnrollment(FootballSocialMatch footballSocialMatch, Member member){
        footballSocialMatch.isAcceptEnroll(member);

    }*/
}
