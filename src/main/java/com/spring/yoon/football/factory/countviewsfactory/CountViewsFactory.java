package com.spring.yoon.football.factory.countviewsfactory;

import com.spring.yoon.football.enums.boardtype.BoardType;
import com.spring.yoon.football.service.countviewsservice.CountViewsService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class CountViewsFactory {

    private Map<BoardType, CountViewsService> strategies;

    public CountViewsFactory(Set<CountViewsService> serviceSet){
        createStrategies(serviceSet);
    }

    public CountViewsService findCountViewsService(BoardType boardType){
        return strategies.get(boardType);
    }

    private void createStrategies(Set<CountViewsService> serviceSet){
        this.strategies = new HashMap<>();
        serviceSet.forEach(s-> strategies.put(s.getBoardType(),s));
    }
}
