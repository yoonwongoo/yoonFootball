package com.spring.yoon.football.domain.payment;


import com.spring.yoon.football.converter.BooleanConverter;
import com.spring.yoon.football.converter.PayTypeConverter;
import com.spring.yoon.football.domain.BaseTimeEntity;
import com.spring.yoon.football.domain.member.Member;
import com.spring.yoon.football.enums.paytype.PayType;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class Payment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /*결제 고유코드*/
    private String payUid;

    /*결제회사*/
    @Convert(converter = PayTypeConverter.class)
    private PayType payType;
    
    /*결제방법*/
    private String payMethod;

    /*결제금액*/
    private int amount;

    /*결제한 사람*/
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    /*결제상태*/
    @Convert(converter = BooleanConverter.class)
    private boolean payment;
}
