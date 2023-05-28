package com.spring.yoon.football.dto.payment.kakaopay;

import com.spring.yoon.football.dto.payment.PayDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class KakaoPayDto{
    
    /*결제준비요청 카카오페이*/
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class KakaoPayReadyRequest {
        private String cid;
        private String partner_order_id;
        private String partner_user_id;
        private String item_name;
        private int quantity;
        private int total_amount;
        private int tax_free_amount;
        private String approval_url;
        private String cancel_url;
        private String fail_url;
    }

    /*결제준비응답 포인트 카카오페이*/
    @NoArgsConstructor
    @Getter
    public static class KakaoPayReadyResponse  extends PayDto {
        private String tid;
        private String next_redirect_app_url;
        private String next_redirect_mobile_url;
        private String next_redirect_pc_url;
        private String android_app_scheme;
        private String ios_app_scheme;
        private LocalDateTime created_at;

    }

    /*결제승인 요청 카카오페이*/
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class KakaoPayApproveRequest{
        private String cid;
        private String tid;
        private String partner_order_id;
        private String partner_user_id;
        private String pg_token;
    }

    /*결제승인 응답 카카오페이*/
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class KakaoPayApproveResponse extends PayDto {
       private String aid;
       private String tid;
       private String cid;
       private String sid;
       private String partner_order_id;
       private String partner_user_id;
       private String payment_method_type;
       private Amount amount;
       private CardInfo card_info;
       private String item_name;
       private String item_code;
       private int quantity;
       private LocalDateTime created_at;
       private LocalDateTime approved_at;
       private String payload;


    }


}
