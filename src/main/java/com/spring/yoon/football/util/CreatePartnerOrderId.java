package com.spring.yoon.football.util;

import java.util.concurrent.ThreadLocalRandom;

public class CreatePartnerOrderId {

    public static String createPartnerOrderId(){
       String authNumber = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
        return authNumber;
    }
}
