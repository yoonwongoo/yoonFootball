package com.spring.yoon.football.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.yoon.football.config.security.AuthMember;
import com.spring.yoon.football.domain.member.Member;
import com.spring.yoon.football.dto.ResponseDto;
import com.spring.yoon.football.dto.payment.PayDto;
import com.spring.yoon.football.dto.payment.PaymentDto;
import com.spring.yoon.football.enums.paytype.PayType;
import com.spring.yoon.football.factory.paymentfactory.PaymentFactory;
import com.spring.yoon.football.service.paymentservice.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentFactory paymentFactory;

    /*결제 창*//*
    @GetMapping("/payment")
    public String paymentForm(@AuthMember Member member, Model model){
        return "payment/payment";
    }*/


    /*사용자가 결제 취소*/
    @GetMapping("/cancel")
    @ResponseBody
    public String cancelPayment(){

        return "사용자가 결제를 취소하였습니다";
    }
    /*결제 준비 응답 토큰받고 */
    /*결제 승인 요청 */
    @GetMapping("/success")
    @ResponseBody
    public ResponseEntity approvePayment(@RequestParam("pg_token") String pg_token, @AuthMember Member member) throws JsonProcessingException {
        PaymentService payApprove = paymentFactory.findPaymentService(PayType.KAKAO);
        PayDto response = payApprove.chargePointApprove(pg_token,member);

        return new ResponseEntity<>(new ResponseDto<>(response,"결제완료",HttpStatus.OK),HttpStatus.OK);
    }

    /*결제 준비 요청 매치신청과 동시에 결제*/
    @PostMapping("/api/payment")
    @ResponseBody
    public ResponseEntity chargePayment(@RequestBody @Valid PaymentDto.Request paymentDto, @AuthMember Member member){

        System.out.println(paymentDto.toString());
        PaymentService payReady = paymentFactory.findPaymentService(paymentDto.getPayType());
        PayDto response= payReady.chargePointReady(paymentDto,member);

        return new ResponseEntity(new ResponseDto(response,"결제요청준비",HttpStatus.OK),HttpStatus.OK);
    }
}
