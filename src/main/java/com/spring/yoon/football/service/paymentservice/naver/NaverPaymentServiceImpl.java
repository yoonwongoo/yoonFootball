package com.spring.yoon.football.service.paymentservice.naver;


import com.spring.yoon.football.domain.member.Member;
import com.spring.yoon.football.dto.payment.PayDto;
import com.spring.yoon.football.dto.payment.PaymentDto;
import com.spring.yoon.football.enums.paytype.PayType;
import com.spring.yoon.football.service.paymentservice.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class NaverPaymentServiceImpl implements PaymentService {

    @Override
    public PayType getPayType() {
        return PayType.NAVER;
    }

    @Override
    public PayDto chargePointReady(PaymentDto.Request paymentDto, Member member) {
        return null;
    }

    @Override
    public PayDto chargePointApprove(String pg_token,Member member) {
        return null;
    }



}
