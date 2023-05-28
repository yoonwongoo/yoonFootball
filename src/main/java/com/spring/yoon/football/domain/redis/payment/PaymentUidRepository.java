package com.spring.yoon.football.domain.redis.payment;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PaymentUidRepository extends CrudRepository<PaymentUid,String> {
    Optional<PaymentUid> findByIdAndMemberId(String payUid, long memberId);
    Optional<PaymentUid> findByMemberId(long memberId);

}
