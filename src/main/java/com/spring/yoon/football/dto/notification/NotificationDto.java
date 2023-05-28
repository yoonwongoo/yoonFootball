package com.spring.yoon.football.dto.notification;


import com.spring.yoon.football.domain.notification.Notification;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;



public class NotificationDto {



    public static class Request{


    }

    @Getter
    @NoArgsConstructor
    public static class Response{
            private long notificationId;
            private String title;
            private String message;
            private String createdDate;

    public Response(Notification notification){
            this.notificationId =notification.getId();
            this.title = notification.getTitle();
            this.message = notification.getMessage();
            this.createdDate =convertLocalDateTime(notification.getCreatedDate());

        }

    }

    /*시간변환*/
    private static String convertLocalDateTime(LocalDateTime createdDate){

         long betweenTime = ChronoUnit.MINUTES.between(createdDate, LocalDateTime.now());
         String StringCreatedDate;

         if (betweenTime == 0){
             StringCreatedDate = "방금 전";
         }else if (betweenTime < 60) {
             StringCreatedDate = betweenTime + "분 전";
         }else if (betweenTime < 60 * 24){
             StringCreatedDate = (betweenTime/60) + "시간 전";
         }else if (betweenTime < 60 * 24 * 28) {
             StringCreatedDate = (betweenTime/60/24) + "일 전";
         } else {
             StringCreatedDate = createdDate.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
         }
         return StringCreatedDate;
    }
}
