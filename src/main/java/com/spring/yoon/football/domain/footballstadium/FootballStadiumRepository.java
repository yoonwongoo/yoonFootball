package com.spring.yoon.football.domain.footballstadium;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FootballStadiumRepository extends JpaRepository<FootballStadium,Long>,FootballStadiumRepositoryCustom {
    
    /*구장목록 가져오기*/
    List<FootballStadium> findAllByOrderByIdDesc();
}
