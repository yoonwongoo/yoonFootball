package com.spring.yoon.football.domain.redis.emailauth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@RedisHash(value = "email")
public class EmailAuthCode {


    @Id
    private String email;

    private String authCode;
    @Builder.Default
    @TimeToLive
    private long expiration =300L;

}
