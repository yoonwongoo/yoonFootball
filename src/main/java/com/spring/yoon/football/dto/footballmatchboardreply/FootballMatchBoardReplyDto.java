package com.spring.yoon.football.dto.footballmatchboardreply;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.yoon.football.domain.footballmatchboardreply.FootballMatchBoardReply;
import com.spring.yoon.football.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NoArgsConstructor
@Getter
public class FootballMatchBoardReplyDto {


    /*댓글 작성에 대한 request*/
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class AddRequest {
        @NotNull(message = "게시글 번호는 필수 입니다")
        /*게시글 번호*/
        private long footballMatchBoardId;
        @NotBlank(message = "댓글을 작성해주세요")
        /*게시글 댓글 내용*/
        private String comment;

        public FootballMatchBoardReply toEntity(Member member){
            return FootballMatchBoardReply.builder()
                    .comment(comment)
                    .member(member)
                    .build();

        }
    }

    /*n개의 댓글 response*/
    @NoArgsConstructor
    @ToString
    @Getter
    public static class ListResponse{

        public ListResponse(FootballMatchBoardReply footballMatchBoardReply) {
            this.replyId = footballMatchBoardReply.getId();
            this.username = footballMatchBoardReply.getMember().getUsername();
            this.comment = footballMatchBoardReply.getComment();
            this.createdDate = footballMatchBoardReply.getCreatedDate();
        }

        private long replyId;
        private String username;
        private String comment;
        @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdDate;
    }
    /*내가 쓴 댓글 DTO*/
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MyFootballMatchBoardReply{
        private long footballMatchBoardId;
        private String comment;
        private String location;
        private LocalDateTime createdDate;
        private LocalDate matchDay;
        private LocalTime startTime;
        private LocalTime endTime;

        public MyFootballMatchBoardReply(FootballMatchBoardReply footballMatchBoardReply) {
            this.footballMatchBoardId = footballMatchBoardReply.getFootballMatchBoard().getId();
            this.comment = footballMatchBoardReply.getComment();
            this.location = footballMatchBoardReply.getFootballMatchBoard().getLocation();
            this.createdDate = footballMatchBoardReply.getCreatedDate();
            this.matchDay = footballMatchBoardReply.getFootballMatchBoard().getMatchDay();
            this.startTime = footballMatchBoardReply.getFootballMatchBoard().getStartTime();
            this.endTime = footballMatchBoardReply.getFootballMatchBoard().getEndTime();
        }
    }
}
