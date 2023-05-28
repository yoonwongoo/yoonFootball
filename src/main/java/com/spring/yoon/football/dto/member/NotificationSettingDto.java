package com.spring.yoon.football.dto.member;


import com.spring.yoon.football.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class NotificationSettingDto {
        private boolean footballMatchBoardReplyByWeb;
        private boolean footballMatchBoardUpdateByWeb;

        private NotificationSettingDto(Member member){

        this.footballMatchBoardUpdateByWeb = member.getNotificationSetting().isFootballMatchBoardUpdateByWeb();
        this.footballMatchBoardReplyByWeb = member.getNotificationSetting().isFootballMatchBoardReplyByWeb();

        }

        public static NotificationSettingDto form(Member member){

          return  new NotificationSettingDto(member);
        }


}
