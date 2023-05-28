package com.spring.yoon.football.util;

import java.util.concurrent.ThreadLocalRandom;

public class CreateAuthCode {


    public static String createAuthCode(){
       String authNumber = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
        return authNumber;
    }
}
