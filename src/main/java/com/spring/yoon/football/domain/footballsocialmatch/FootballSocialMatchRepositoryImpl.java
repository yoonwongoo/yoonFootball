package com.spring.yoon.football.domain.footballsocialmatch;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.yoon.football.dto.footballsocialmatch.FootballSocialMatchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.spring.yoon.football.domain.footballsocialmatch.QFootballSocialMatch.footballSocialMatch;
import static com.spring.yoon.football.domain.footballstadium.QFootballStadium.footballStadium;


@RequiredArgsConstructor
public class FootballSocialMatchRepositoryImpl implements FootballSocialMatchRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    private final EntityManager entityManager;
    /*등록된 매치 목록*/

    @Transactional
    @Override
    public List<FootballSocialMatch> findByFootballSocialMatchList(FootballSocialMatchDto.Search search) {

        List<FootballSocialMatch> list =jpaQueryFactory.select(footballSocialMatch)
                        .from(footballSocialMatch)
                        .join(footballSocialMatch.footballStadium, footballStadium).fetchJoin()
                        .where(eqMatchDay(search.getMatchDay()))
                        .fetch();
        /*이미 지난 매치*/
       List<Long> listId = list.stream().filter(l-> LocalDateTime.of(l.getMatchDay(),l.getStartTime()).isBefore(LocalDateTime.now()))
                .map(FootballSocialMatch::getId)
                .collect(Collectors.toList());

       /*이미 지난 매치가 있으면 업데이트 후 다시 조회*/
       if(!listId.isEmpty()){
           jpaQueryFactory.update(footballSocialMatch)
                   .set(footballSocialMatch.accept,false)
                   .where(footballSocialMatch.id.in(listId))
                   .execute();
           entityManager.clear();
         list =jpaQueryFactory.select(footballSocialMatch)
                .from(footballSocialMatch)
                .join(footballSocialMatch.footballStadium, footballStadium).fetchJoin()
                .where(eqMatchDay(search.getMatchDay()))
                .fetch();
       }

        return list;
    }
    
    /*날짜 별 매치조건*/
    private BooleanExpression eqMatchDay(LocalDate matchDay){
        if(ObjectUtils.isEmpty(matchDay)){
            System.out.println("날짜 없음");
            return footballSocialMatch.matchDay.eq(LocalDate.now());
        }
        else{
            System.out.println("날짜 있음");
            return footballSocialMatch.matchDay.eq(matchDay);
        }
        
    }
}
