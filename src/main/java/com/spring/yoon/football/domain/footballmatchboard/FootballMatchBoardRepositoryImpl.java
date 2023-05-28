package com.spring.yoon.football.domain.footballmatchboard;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.yoon.football.dto.footballmatchboard.FootballMatchBoardDto;
import com.spring.yoon.football.enums.member.SkillLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.spring.yoon.football.domain.footballmatchboard.QFootballMatchBoard.footballMatchBoard;
import static com.spring.yoon.football.domain.member.QMember.member;

@RequiredArgsConstructor
public class FootballMatchBoardRepositoryImpl implements FootballMatchBoardRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    private final EntityManager entityManager;
    /*팀 개인 글 목록 페이징 적용*/
    @Transactional
    @Override
    public Slice<FootballMatchBoard> findByFootballMatchBoardList(FootballMatchBoardDto.Search search, Pageable pageable) {
        List<FootballMatchBoard> list = jpaQueryFactory.select(footballMatchBoard)
            .from(footballMatchBoard)
            .innerJoin(footballMatchBoard.member, member).fetchJoin()
            .where(eqMatchDay(search.getMatchDay()),eqSkillLevel(search.getSkillLevel()),matchHide(search.getHide()),gtId(search.getId()))
            .limit(pageable.getPageSize() + 1)
            .fetch();
        boolean hasNext = false;

        if (list.size() > pageable.getPageSize()) {
            hasNext = true;
        }
        /*마감 있나 거른다*/
        List<Long> ids = list.stream().filter(l -> LocalDateTime.of(l.getMatchDay(), l.getStartTime()).isBefore(LocalDateTime.now()) && l.isRecruitment() != false)
            .map(FootballMatchBoard::getId)
            .collect(Collectors.toList());

        if (!ids.isEmpty()) {
        /*마감 값 확인 후 bulk update*/
            updateRecruitment(ids);
        /*다시 조회 */
            list = jpaQueryFactory.select(footballMatchBoard)
                .from(footballMatchBoard)
                .innerJoin(footballMatchBoard.member, member).fetchJoin()
                .where(eqMatchDay(search.getMatchDay()),eqSkillLevel(search.getSkillLevel()),matchHide(search.getHide()),gtId(search.getId()))
                .limit(pageable.getPageSize() + 1)
                .fetch();
        }
        Slice<FootballMatchBoard> lists = new SliceImpl<>(list, pageable, hasNext);
            return lists;



    }

    private BooleanExpression eqSkillLevel(SkillLevel skillLevel){
        if(ObjectUtils.isEmpty(skillLevel)){
            return null;
        }else{
            return footballMatchBoard.skillLevel.eq(skillLevel);
        }


    }
    private BooleanExpression eqMatchDay(LocalDate localDate) {

        if (ObjectUtils.isEmpty(localDate) || localDate.isBefore(LocalDate.now()))
            return footballMatchBoard.matchDay.eq(LocalDate.now());
        else
            return footballMatchBoard.matchDay.eq(localDate);
    }
    /*지난 경기 가리기 현재 시각으로 보여준다*/
    private BooleanExpression matchHide(String hide){

        if(ObjectUtils.isEmpty(hide)) {
            return null;
        }else{

          return footballMatchBoard.startTime.goe(LocalTime.now());
        }


    }


    private BooleanExpression gtId(long id){
        if(id == 0){
            return null;
        }else{
            return  footballMatchBoard.id.gt(id);
        }

    }

    /*마감된 경기 변경*/
    private void updateRecruitment(List<Long> ids){
        jpaQueryFactory.update(footballMatchBoard)
                .set(footballMatchBoard.recruitment,false)
                .where(footballMatchBoard.id.in(ids))
                .execute();
        entityManager.clear();
    }


}
