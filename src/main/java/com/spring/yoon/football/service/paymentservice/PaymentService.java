package com.spring.yoon.football.service.paymentservice;

import com.spring.yoon.football.domain.member.Member;
import com.spring.yoon.football.dto.payment.PayDto;
import com.spring.yoon.football.dto.payment.PaymentDto;
import com.spring.yoon.football.enums.paytype.PayType;


public interface PaymentService<T extends PayDto> {

    PayType getPayType();

    T chargePointReady(PaymentDto.Request paymentDto, Member member);

    T chargePointApprove(String pg_token,Member member);



}
