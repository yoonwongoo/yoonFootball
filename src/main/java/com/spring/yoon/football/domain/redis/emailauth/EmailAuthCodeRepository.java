package com.spring.yoon.football.domain.redis.emailauth;

import org.springframework.data.repository.CrudRepository;

public interface EmailAuthCodeRepository extends CrudRepository<EmailAuthCode,String> {


}
