package com.spring.yoon.football.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class SseController {

    public static Map<Long, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

    @GetMapping("/sub")
    public SseEmitter connectSse(@RequestParam long id){
        System.out.println(id);

        // 현재 클라이언트를 위한 SseEmitter 생성
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        try {
            // 연결!!
            sseEmitter.send(SseEmitter.event().name("connect"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // user의 pk값을 key값으로 해서 SseEmitter를 저장s
        sseEmitters.put(id, sseEmitter);
        for(long asd :sseEmitters.keySet()){
            System.out.println(asd);
        }
        sseEmitter.onCompletion(() -> sseEmitters.remove(id));
        sseEmitter.onTimeout(() -> sseEmitters.remove(id));
        sseEmitter.onError((e) -> sseEmitters.remove(id));

        return sseEmitter;

    }
}
