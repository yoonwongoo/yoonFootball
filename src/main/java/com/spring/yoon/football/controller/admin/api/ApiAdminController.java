package com.spring.yoon.football.controller.admin.api;


import com.spring.yoon.football.config.security.AuthMember;
import com.spring.yoon.football.domain.member.Member;
import com.spring.yoon.football.dto.ResponseDto;
import com.spring.yoon.football.dto.footballsocialmatch.FootballSocialMatchDto;
import com.spring.yoon.football.dto.footballstadium.FootBallStadiumDto;
import com.spring.yoon.football.dto.noticeboard.NoticeBoardDto;
import com.spring.yoon.football.service.adminservice.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/api/admin")
@RestController
@Slf4j
public class ApiAdminController {


    private final AdminService adminService;

    /*풋살 경기장 등록*/
    @PostMapping("/football-stadium")
    public ResponseEntity<?> footballStadiumAdd(@RequestPart @Valid FootBallStadiumDto.AddRequest addRequest
            ,@RequestPart(required = false) Optional<MultipartFile> stadiumImageUrl ){
        adminService.addFootballStadium(addRequest,stadiumImageUrl);
        return new ResponseEntity<>(new ResponseDto<>(null,"경기장 등록완료", HttpStatus.CREATED),HttpStatus.CREATED);
    }
    /*풋살 경기장 수정*/
    @PutMapping("/football-stadium")
    public ResponseEntity<?> footballStadiumModify(
            @RequestPart @Valid FootBallStadiumDto.ModifyRequest modifyRequest,
            @RequestPart(required = false) Optional<MultipartFile> image,
            @AuthMember Member member){
        adminService.modifyFootballStadium(modifyRequest,image,member);
        return new ResponseEntity<>(new ResponseDto<>(null,"수정완료",HttpStatus.OK),HttpStatus.OK);
    }
    /*풋살 경기장 삭제*/
    @DeleteMapping("/football-stadium/{id}")
    public ResponseEntity<?> footballStadiumRemove(@PathVariable long id){
        adminService.removeFootballStadium(id);
        return new ResponseEntity<>(new ResponseDto<>(null,"삭제완료",HttpStatus.OK),HttpStatus.OK);
    }


    /*풋볼매치 등록*/
    @PostMapping("/football-social-match")
    public ResponseEntity<?> footballSocialMatchAdd(@RequestBody @Valid FootballSocialMatchDto.AddRequest addRequest , @AuthMember Member member){
            adminService.addFootballSocialMatch(addRequest,member);
           return new ResponseEntity<>(new ResponseDto<>(null,"등록완료",HttpStatus.OK),HttpStatus.OK);
       }


    /*공지사항 등록*/
    @PostMapping("/notice")
    public ResponseEntity<?> noticeBoardAdd(@RequestBody @Valid NoticeBoardDto.AddRequest addRequest, @AuthMember Member member){
        adminService.addNoticeBoard(addRequest,member);

        return new ResponseEntity<>(new ResponseDto<>(null,"공지사항 등록완료",HttpStatus.CREATED),HttpStatus.CREATED);
    }

}
