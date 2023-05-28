package com.spring.yoon.football.domain.footballstadium;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.yoon.football.dto.footballstadium.FootBallStadiumDto;

import java.util.List;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.spring.yoon.football.domain.footballsocialmatch.QFootballSocialMatch.footballSocialMatch;
import static com.spring.yoon.football.domain.footballstadium.QFootballStadium.footballStadium;

public class FootballStadiumRepositoryImpl implements FootballStadiumRepositoryCustom{


    private JPAQueryFactory jpaQueryFactory;

    public FootballStadiumRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }


    /*매치등록이 가능한 구장목록*/
    @Override
    public List<FootBallStadiumDto.FootballStadiumMatchResponse> findByFootballStadiumMatchList(FootBallStadiumDto.Search search) {
        return  jpaQueryFactory
                        .selectFrom(footballStadium)
                        .distinct()
                        .leftJoin(footballStadium.footballSocialMatches, footballSocialMatch)
                        .on(footballSocialMatch.matchDay.eq(search.getMatchDay()))
                        .orderBy(footballStadium.id.asc())
                        .transform(groupBy(footballStadium.id).list(
                                Projections.constructor(FootBallStadiumDto.FootballStadiumMatchResponse.class,
                                        footballStadium.id.as("footballStadiumId"),
                                        footballStadium.stadiumName,
                                        footballStadium.location,
                                        footballStadium.stadiumImageUrl,
                                        footballStadium.availableStartTime,
                                        footballStadium.availableEndTime,
                                        GroupBy.list(footballSocialMatch.startTime)
                                ))
                        );
    }



}
