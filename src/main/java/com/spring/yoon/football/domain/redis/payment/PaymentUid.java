package com.spring.yoon.football.domain.redis.payment;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
@RedisHash(value="PaymentUid")
public class PaymentUid {


    /*결제 유효시간은 15분*/
    @Id
    @Indexed
    private String id;//결제 고유아이디

    @Indexed
    private long memberId;//회원 id
    
    @Indexed
    private long footballSocialMatchId; //아아디
    
    @Indexed
    private String partnerOrderId;//랜덤 주문번호

    @Builder.Default
    @TimeToLive
    private long expiration =300L;



}
