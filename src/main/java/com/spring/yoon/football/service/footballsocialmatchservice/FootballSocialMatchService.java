package com.spring.yoon.football.service.footballsocialmatchservice;


import com.spring.yoon.football.domain.footballsocialmatch.FootballSocialMatchRepository;
import com.spring.yoon.football.dto.footballsocialmatch.FootballSocialMatchDto;
import com.spring.yoon.football.handler.exception.ErrorCode;
import com.spring.yoon.football.handler.exception.customserviceexception.entitynotfoundexception.FootballSocialMatchNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FootballSocialMatchService {


    private final FootballSocialMatchRepository footballSocialMatchRepository;

    /*매치 목록
    * 많아보이지 않아서 일단 굳이 페이징을 안하겠음....전체날짜도 아니고 날짜 하루만의 등록된 것을 볼 수 있으니까*/
    public List<FootballSocialMatchDto.Response> findFootballSocialMatchList(FootballSocialMatchDto.Search search){
        return  footballSocialMatchRepository.findByFootballSocialMatchList(search).stream().map(FootballSocialMatchDto.Response::new).collect(Collectors.toList());
    }
    
    /*소셜매치 자세히*/
    public FootballSocialMatchDto.Response findFootballSocialMatchDetails(long id){

        return footballSocialMatchRepository.findByFootballSocialMatchDetails(id).map(FootballSocialMatchDto.Response::new)
                .orElseThrow(()-> new FootballSocialMatchNotFoundException("존재하지않는 소셜매치입니다", ErrorCode.ENTITY_NOT_FOUND));

    }
}
