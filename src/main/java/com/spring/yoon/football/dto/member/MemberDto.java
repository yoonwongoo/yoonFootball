package com.spring.yoon.football.dto.member;

import com.spring.yoon.football.domain.member.Member;
import lombok.Getter;


public class MemberDto {

    @Getter
    public static class Response{

        private String username;
        private String name;
        private String email;
        public Response(Member member) {
            this.username = member.getUsername();
            this.name = member.getName();
            this.email = member.getEmail();
        }


    }
}
