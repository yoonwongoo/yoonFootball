package com.spring.yoon.football.service.paymentservice.kakao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.yoon.football.domain.footballsocialmatch.FootballSocialMatch;
import com.spring.yoon.football.domain.footballsocialmatch.FootballSocialMatchRepository;
import com.spring.yoon.football.domain.member.Member;
import com.spring.yoon.football.domain.payment.Payment;
import com.spring.yoon.football.domain.payment.PaymentRepository;
import com.spring.yoon.football.domain.redis.payment.PaymentUid;
import com.spring.yoon.football.dto.payment.PayDto;
import com.spring.yoon.football.dto.payment.PaymentDto;
import com.spring.yoon.football.dto.payment.kakaopay.KakaoPayDto;
import com.spring.yoon.football.enums.paytype.PayType;
import com.spring.yoon.football.handler.exception.ErrorCode;
import com.spring.yoon.football.handler.exception.customserviceexception.entitynotfoundexception.FootballSocialMatchNotFoundException;
import com.spring.yoon.football.properties.KakaoPayProperties;
import com.spring.yoon.football.service.footballsocialmatchenrollservice.FootballSocialMatchEnrollService;
import com.spring.yoon.football.service.paymentservice.PaymentService;
import com.spring.yoon.football.service.redisservice.RedisPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KakaoPaymentServiceImpl implements PaymentService<PayDto> {

    private final ObjectMapper objectMapper;
    private final KakaoPayProperties kakaoPayProperties;
    private final WebClient webClient;
    private final FootballSocialMatchEnrollService footballSocialMatchEnrollService;
    private final RedisPaymentService redisPaymentService;
    private final PaymentRepository paymentRepository;
    private final FootballSocialMatchRepository footballSocialMatchRepository;

    /*카카오페이로 충전준비*/

    @Transactional(readOnly = true)
    @Override
    public KakaoPayDto.KakaoPayReadyResponse chargePointReady(PaymentDto.Request paymentDto, Member member){
       FootballSocialMatch footballSocialMatch = footballSocialMatchRepository.findByFootballSocialMatch(paymentDto.getFootballSocialMatchId()).orElseThrow(()-> new FootballSocialMatchNotFoundException("존재하지않는매치입니다", ErrorCode.ENTITY_NOT_FOUND));
       /*일단 먼저 가능한지...중복신청/인원초과/기간체크*/
       footballSocialMatch.isAcceptEnroll(member);

       URI uri = URI.create(kakaoPayProperties.getReadyUrl());

       KakaoPayDto.KakaoPayReadyRequest kakaoPayRequest = KakaoPayDto.KakaoPayReadyRequest.builder()
                   .cid(kakaoPayProperties.getCid())
                   .partner_order_id(paymentDto.getPartnerOrderId())
                   .partner_user_id(String.valueOf(member.getId()))
                   .item_name(kakaoPayProperties.getItemName())
                   .quantity(kakaoPayProperties.getQuantity())
                   .total_amount(footballSocialMatch.getParticipationFee())
                   .tax_free_amount(kakaoPayProperties.getTaxFreeAmount())
                   .approval_url(kakaoPayProperties.getRedirectApprovalUrl())
                   .fail_url(kakaoPayProperties.getRedirectFailUrl())
                   .cancel_url(kakaoPayProperties.getRedirectCancelUrl())
                   .build();


      MultiValueMap<String,String> multiValueMap = new LinkedMultiValueMap<String,String>();

      Map<String, String> map = objectMapper.convertValue(kakaoPayRequest, new TypeReference<Map<String, String>>() {});
      multiValueMap.setAll(map);

      KakaoPayDto.KakaoPayReadyResponse response = webClient.post()
                  .uri(uri)
                  .bodyValue(multiValueMap)
                  .headers(headers->{
                      headers.add("Authorization",kakaoPayProperties.getKakaoAk());
                      headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
                  })
                  .retrieve()
                  .onStatus(HttpStatus::is4xxClientError,exResponse-> Mono.error(new RuntimeException(exResponse.bodyToMono(String.class).block())))
                  .bodyToMono(KakaoPayDto.KakaoPayReadyResponse.class)
                  .block();
      
        /*redis에 저장*/
        redisPaymentService.createPaymentUid(response.getTid(),member.getId(),paymentDto.getFootballSocialMatchId(),paymentDto.getPartnerOrderId());


       return response;

   }

       /*카카오페이로 충전승인*/
       @Transactional
       @Override
       public KakaoPayDto.KakaoPayApproveResponse chargePointApprove(String pg_token, Member member){
           URI uri = URI.create(kakaoPayProperties.getApproveUrl());
            /*tid를 가져오자*/
           PaymentUid paymentUid = redisPaymentService.findByPaymentUid(member.getId());

           KakaoPayDto.KakaoPayApproveRequest kakaoPayApproveRequest = KakaoPayDto.KakaoPayApproveRequest.builder()
                   .cid(kakaoPayProperties.getCid())
                   .tid(paymentUid.getId())
                   .partner_order_id(paymentUid.getPartnerOrderId())
                   .partner_user_id(String.valueOf(paymentUid.getMemberId()))
                   .pg_token(pg_token)
                   .build();

           MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<String, String>();

           Map<String, String> map = objectMapper.convertValue(kakaoPayApproveRequest, new TypeReference<Map<String, String>>() {
           });
           multiValueMap.setAll(map);


           KakaoPayDto.KakaoPayApproveResponse response = webClient.post()
                   .uri(uri)
                   .bodyValue(multiValueMap)
                   .headers(headers -> {
                       headers.add("Authorization", kakaoPayProperties.getKakaoAk());
                       headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
                   })
                   .retrieve()
                   /*여기 고쳐햐맘*/
                   .onStatus(HttpStatus::is4xxClientError,exResponse-> Mono.error(new RuntimeException(exResponse.bodyToMono(String.class).block())))
                   .bodyToMono(KakaoPayDto.KakaoPayApproveResponse.class)
                   .block();

           /*매치에 신청등록*/
           Payment paymentEntity= Payment.builder()
                   .payment(true)
                   .payMethod(response.getPayment_method_type())
                   .payType(PayType.KAKAO)
                   .amount(response.getAmount().getTotal())
                   .member(member)
                   .payUid(response.getTid())
                   .build();

           Payment payment = paymentRepository.save(paymentEntity);

           footballSocialMatchEnrollService.addFootballSocialMatchEnroll(paymentUid.getFootballSocialMatchId(),member,payment);
           return response;

       }
    @Override
    public PayType getPayType() {
        return PayType.KAKAO;
    }
}
