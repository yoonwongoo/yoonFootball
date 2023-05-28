package com.spring.yoon.football.controller.api;


import com.spring.yoon.football.config.security.AuthMember;
import com.spring.yoon.football.domain.member.Member;
import com.spring.yoon.football.dto.ResponseDto;
import com.spring.yoon.football.dto.footballmatchboardreply.FootballMatchBoardReplyDto;
import com.spring.yoon.football.service.footballmatchboardreplyservice.FootballMatchBoardReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ApiFootballMatchBoardReplyController {


    private final FootballMatchBoardReplyService footballMatchBoardReplyService;

    /*댓글 작성하기*/
    @PostMapping("/football-match-board/reply")
    public ResponseEntity<?> footballMatchBoardReplyAdd(@RequestBody @Valid FootballMatchBoardReplyDto.AddRequest addRequest,
                                                        @AuthMember Member member){
        System.out.println(addRequest.toString());
        footballMatchBoardReplyService.addFootballMatchBoardReply(addRequest,member);
        return new ResponseEntity<>(new ResponseDto("null","댓글 작성완료", HttpStatus.CREATED),HttpStatus.CREATED);
    }
    
    /*댓글보기*/
    @GetMapping("/football-match-board/reply/{boardId}")
    public ResponseEntity<?> footballMatchBoardReplyList(@PathVariable long boardId, Pageable pageable){
        Page<FootballMatchBoardReplyDto.ListResponse> pageList =
                footballMatchBoardReplyService.findFootballMatchBoardReplyList(boardId,pageable);

        List<FootballMatchBoardReplyDto.ListResponse> list =pageList.getContent();

        int currentPage = pageList.getPageable().getPageNumber()+1;
        int startPage= Math.max(1,currentPage-4);
        int lastPage= Math.min(pageList.getTotalPages(), currentPage+4);
        int totalLastPage = pageList.getTotalPages();//제일 마지막
        boolean isFirst  = pageList.isFirst();
        boolean isLast = pageList.isLast();
        Map<String,Object> map = new HashMap<>();
        map.put("totalLastPage",totalLastPage);
        map.put("isFirst",isFirst);
        map.put("isLast",isLast);
        map.put("currentPage",currentPage);
        map.put("startPage",startPage);
        map.put("lastPage",lastPage);
        map.put("list",list);
        return new ResponseEntity<>(new ResponseDto<>(map,"댓글 가져오기 완료",HttpStatus.OK),HttpStatus.OK);
    }
}
