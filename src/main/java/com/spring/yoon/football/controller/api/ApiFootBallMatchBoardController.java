package com.spring.yoon.football.controller.api;


import com.spring.yoon.football.config.security.AuthMember;
import com.spring.yoon.football.domain.member.Member;
import com.spring.yoon.football.dto.ResponseDto;
import com.spring.yoon.football.dto.footballmatchboard.FootballMatchBoardDto;
import com.spring.yoon.football.service.footballmatchboardservice.FootballMatchBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ApiFootBallMatchBoardController {

    private final FootballMatchBoardService footballMatchBoardService;

    /*팀 및 용병 구하는 글 등록*/
    @PostMapping("/football-match-board")
    public ResponseEntity<?> apiFootballMatchBoardAdd(@RequestBody @Valid FootballMatchBoardDto.AddRequest  request, @AuthMember Member member) {

        footballMatchBoardService.addFootballMatchBoard(request, member);
        return new ResponseEntity(new ResponseDto<>(request, "글 등록완료", HttpStatus.CREATED), HttpStatus.CREATED);
    }

    /*페이지 입장 시 글 리스트 더보기 및 조건*/
    @GetMapping("/football-match-board/list")
    public ResponseEntity<?> apiFootBallMatchBoardList(FootballMatchBoardDto.Search search,
                                                       Pageable pageable) {
        System.out.println(search.toString());
        Slice<FootballMatchBoardDto.SliceList> list = footballMatchBoardService.findFootballMatchBoardList(search, pageable);
        return new ResponseEntity(new ResponseDto<>(list, "리스트 검색완료", HttpStatus.OK), HttpStatus.OK);

    }


    /*내가 작성한 글 지우기*/
    @DeleteMapping("/football-match-board/{footballMatchBoardId}")
    public ResponseEntity<?> apiFootBallMatchBoardRemove(@PathVariable long footballMatchBoardId,@AuthMember Member member){

            footballMatchBoardService.removeFootBallMatchBoard(footballMatchBoardId,member);

        return new ResponseEntity(new ResponseDto<>(null, "글 삭제완료", HttpStatus.OK), HttpStatus.OK);
    }
    /*내가 작성한 글 수정하기*/
    @PutMapping("/football-match-board/edit")
    public ResponseEntity<?> apiFootBallMatchBoardModify(@RequestBody @Valid FootballMatchBoardDto.ModifyRequest request, @AuthMember Member member){

        System.out.println(request.toString());

        footballMatchBoardService.modifyFootballMatchBoard(request,member);
        return new ResponseEntity(new ResponseDto<>(null, "글 수정완료", HttpStatus.OK), HttpStatus.OK);
        }
}