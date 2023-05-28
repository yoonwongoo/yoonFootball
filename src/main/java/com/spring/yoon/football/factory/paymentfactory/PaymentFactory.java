package com.spring.yoon.football.factory.paymentfactory;

import com.spring.yoon.football.enums.paytype.PayType;
import com.spring.yoon.football.service.paymentservice.PaymentService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class PaymentFactory {

   private Map<PayType, PaymentService> strategies;

   public PaymentFactory(Set<PaymentService> serviceSet) {
      createStrategies(serviceSet);

   }

   public PaymentService findPaymentService(PayType payType){
      return strategies.get(payType);
   }

   private void createStrategies(Set<PaymentService> serviceSet){
      strategies = new HashMap<>();
      serviceSet.forEach(s-> strategies.put(s.getPayType(),s));
   }
}
