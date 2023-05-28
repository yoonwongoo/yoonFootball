package com.spring.yoon.football.properties;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "kakao")
@Getter
@ConstructorBinding
@RequiredArgsConstructor
public class KakaoPayProperties {

    private final String readyUrl;
    private final String approveUrl;
    private final String cid;
    private final String partnerOrderId;
    private final String partnerUserId;
    private final String itemName;
    private final int quantity;
    private final int taxFreeAmount;
    private final String kakaoAk;
    private final String redirectApprovalUrl;
    private final String redirectFailUrl;
    private final String redirectCancelUrl;
}
