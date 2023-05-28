package com.spring.yoon.football.domain.footballsocialmatchenrollment;


import com.spring.yoon.football.domain.BaseTimeEntity;
import com.spring.yoon.football.domain.footballsocialmatch.FootballSocialMatch;
import com.spring.yoon.football.domain.member.Member;
import com.spring.yoon.football.domain.payment.Payment;
import lombok.*;

import javax.persistence.*;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class FootballSocialMatchEnrollment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JoinColumn(name = "memberId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name="footballSocialMatchId")
    @ManyToOne
    private FootballSocialMatch footballSocialMatch;

    @OneToOne(fetch =FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name="paymentId")
    private Payment payment;


    public static FootballSocialMatchEnrollment of(Member member,
                                                   FootballSocialMatch footballSocialMatch,
                                                   Payment payment){

        return  FootballSocialMatchEnrollment.builder()
                .footballSocialMatch(footballSocialMatch)
                .payment(payment)
                .member(member)
                .build();
    }


    public void attach(FootballSocialMatch footballSocialMatch){
        this.footballSocialMatch =footballSocialMatch;
    }

}
