package com.spring.yoon.football.dto.member;


import com.spring.yoon.football.controller.validator.enumpattern.EnumPattern;
import com.spring.yoon.football.domain.member.Member;
import com.spring.yoon.football.enums.member.Gender;
import com.spring.yoon.football.enums.member.PreferPosition;
import com.spring.yoon.football.enums.member.SkillLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Optional;

@NoArgsConstructor
@Getter
@ToString
public class ProfileDto {

        @Size(min=2,max =255, message = "자기소개를 2-255자 이내로 작성해주세요")
        private String intro;

        private String image;

        @Size(min =2,max = 10, message = "닉네임은 2-10자 이내로 작성해주세요")
        @NotBlank(message = "닉네임을 띄어쓰기 없이 작성해주세요")
        private String nickName;

        @EnumPattern(enumClass = PreferPosition.class,message = "선호하는 포지션을 선택해주세요")
        private PreferPosition preferPosition;

        @EnumPattern(enumClass = SkillLevel.class, message = "나의 실력을 선택해주세요")
        private SkillLevel skillLevel;

        @EnumPattern(enumClass = Gender.class, message ="성별을 선택해주세요")
        private Gender gender;

        private ProfileDto(Member member){
             this.intro= Optional.ofNullable(member.getProfile().getIntro()).orElse(null);
             this.image= Optional.ofNullable(member.getProfile().getImage()).orElse(null);
             this.nickName=Optional.ofNullable(member.getProfile().getNickName()).orElse(null);
             this.preferPosition=Optional.ofNullable(member.getProfile().getPreferPosition()).orElse(null);
             this.skillLevel=Optional.ofNullable(member.getProfile().getSkillLevel()).orElse(null);
             this.gender=Optional.ofNullable(member.getProfile().getGender()).orElse(null);

         }

         public static ProfileDto form(Member member){
           return  new ProfileDto(member);
         }


}
