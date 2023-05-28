package com.spring.yoon.football.dto.payment;

import com.spring.yoon.football.enums.paytype.PayType;
import com.spring.yoon.football.util.CreatePartnerOrderId;
import lombok.Getter;
import lombok.ToString;

public class PaymentDto {

    @ToString
    @Getter
    public static class Request{

        private int amount;//결제 금액
        private long footballSocialMatchId;//소셜매치 아이디;
        private PayType payType;
        private String partnerOrderId;

        public Request(int amount, long footballSocialMatchId, PayType payType) {
            this.amount = amount;
            this.footballSocialMatchId = footballSocialMatchId;
            this.payType = payType;
            this.partnerOrderId = CreatePartnerOrderId.createPartnerOrderId();
        }
    }
}
