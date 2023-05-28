package com.spring.yoon.football.domain.member;

import com.spring.yoon.football.converter.BooleanConverter;
import com.spring.yoon.football.domain.BaseTimeEntity;
import com.spring.yoon.football.dto.member.ProfileDto;
import com.spring.yoon.football.enums.member.Gender;
import com.spring.yoon.football.enums.member.PreferPosition;
import com.spring.yoon.football.enums.member.Role;
import com.spring.yoon.football.enums.member.SkillLevel;
import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Member extends BaseTimeEntity {
    /*회원가입 시 필수입력요소 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    private int point;

    @Embedded
    private Profile profile;

    @Embedded
    private NotificationSetting notificationSetting;

    //블랙리스트를 넣어야하나..?


    /*회원가입 후 선택입력요소*/
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @ToString
    @Builder
    public static class Profile{
        private String intro;
        private String image;
        private String nickName;
        @Enumerated(EnumType.STRING)
        private PreferPosition preferPosition;
        @Enumerated(EnumType.STRING)
        private SkillLevel skillLevel;
        @Enumerated(EnumType.STRING)
        private Gender gender ;

    }

    /*회원가입 시 기본 세팅 설정 false*/
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @ToString
    @Builder
    public static class NotificationSetting {
        @Convert(converter = BooleanConverter.class)
        private boolean footballMatchBoardUpdateByWeb;

        @Convert(converter = BooleanConverter.class)
        private boolean footballMatchBoardReplyByWeb;
    }


    /*embedded사용시 그 해당 value들이 다 null이면 빈 객체 반환 nullpoint*/
    @PostLoad
    private void profileInit(){
        if(this.profile==null){
            this.profile= new Profile();
        }
    }



    /*프로필 수정*/
    public void updateProfile(ProfileDto profileDto, String imageName){
        if(!imageName.equals("")){
            this.profile.image=imageName;
        }
        this.profile.nickName=profileDto.getNickName();
        this.profile.intro= profileDto.getIntro();
        this.profile.gender=profileDto.getGender();
        this.profile.skillLevel=profileDto.getSkillLevel();
        this.profile.preferPosition=profileDto.getPreferPosition();
    }

    /*알림 설정*/
    public void updateNotification(boolean footballMatchBoardReplyByWeb, boolean footballMatchBoardUpdateByWeb){
        this.notificationSetting.footballMatchBoardReplyByWeb=footballMatchBoardReplyByWeb;
        this.notificationSetting.footballMatchBoardUpdateByWeb=footballMatchBoardUpdateByWeb;

    }

    /*비밀번호 변경*/
    public void updatePassword(String encPassword){
        this.password=encPassword;
    }


    /*이메일 변경*/
    public void updateEmail(String email){
        this.email=email;
    }


    @Override
    public int hashCode() {
        return this.username.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Member){
            return this.username.equals(((Member) obj).getUsername());
        }
        return false;
    }
}
