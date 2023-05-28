package com.spring.yoon.football.service.redisservice;


import com.spring.yoon.football.domain.redis.payment.PaymentUid;
import com.spring.yoon.football.domain.redis.payment.PaymentUidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisPaymentService {
    
    
    private final PaymentUidRepository paymentUidRepository;
    
    
    /*pid저장*/
    public void createPaymentUid(String pid, long memberId,long footballSocialMatchId, String partnerOrderId){
        PaymentUid paymentUid = PaymentUid.builder()
                .id(pid)
                .footballSocialMatchId(footballSocialMatchId)
                .partnerOrderId(partnerOrderId)
                .memberId(memberId)
                .build();
        paymentUidRepository.save(paymentUid);
    }
    
    /*pid찾기*/
    public PaymentUid findByPaymentUid(long memberId){
       return paymentUidRepository.findByMemberId(memberId).orElseThrow(()-> new RuntimeException("해당 결제가 없습니다"));
    }
}
