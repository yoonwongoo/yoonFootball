package com.spring.yoon.football.controller.api;


import com.spring.yoon.football.config.security.AuthMember;
import com.spring.yoon.football.controller.validator.EmailValidator;
import com.spring.yoon.football.controller.validator.PasswordValidator;
import com.spring.yoon.football.controller.validator.ProfileValidator;
import com.spring.yoon.football.domain.member.Member;
import com.spring.yoon.football.dto.ResponseDto;
import com.spring.yoon.football.dto.email.EmailAuthCodeDto;
import com.spring.yoon.football.dto.member.EmailDto;
import com.spring.yoon.football.dto.member.NotificationSettingDto;
import com.spring.yoon.football.dto.member.PasswordDto;
import com.spring.yoon.football.dto.member.ProfileDto;
import com.spring.yoon.football.service.mypageservice.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ApiMyPageController {

    private final MyPageService myPageService;

    private final PasswordValidator passwordValidator;

    private final ProfileValidator profileValidator;

    private final EmailValidator emailValidator;


    @InitBinder("passwordDto")
    public void initBinderPasswordDto(WebDataBinder webDataBinder){
        webDataBinder.addValidators(passwordValidator);
    }
    @InitBinder("profileDto")
    public void initBinderProfileDto(WebDataBinder webDataBinder) {
            webDataBinder.addValidators(profileValidator);
      }
    @InitBinder("emailAuthCodeDto")
    public void initBinderEmailDto(WebDataBinder webDataBinder) {
                webDataBinder.addValidators(emailValidator);
          }


    /*프로필 등록 및 수정*/
    @PostMapping("/profile")
    public ResponseEntity<ResponseDto> profileModify(@RequestPart @Valid ProfileDto profileDto,
                                                     @RequestPart(required = false) Optional<MultipartFile> image,
                                                     @AuthMember Member member){

        myPageService.modifyProfile(member,profileDto,image);
        return new ResponseEntity<>(new ResponseDto<>(null,"프로필 변경 성공", HttpStatus.OK),HttpStatus.OK);
    }

    /*계정 비밀번호 변경*/
    @PatchMapping("/password")
    public ResponseEntity<ResponseDto> passwordModify(@RequestBody @Valid PasswordDto passwordDto){

        return new ResponseEntity<>(new ResponseDto("null","비밀번호 변경완료",HttpStatus.OK),HttpStatus.OK);
    }

    /*계정 이메일 변경을 위한 이메일 인증번호 발송*/
    @PostMapping("/auth/email")
    public ResponseEntity<ResponseDto> sendAuthCodeEmail(@RequestBody @Valid EmailAuthCodeDto emailAuthCodeDto){
     /*이미 등록된 이메일이나 이메일 형식이 이상하거나 빈 값이면 안됨 */

        myPageService.sendToAutoCodeEmail(emailAuthCodeDto);

     return new ResponseEntity<>(new ResponseDto(null,"이메일 전송완료", HttpStatus.OK),HttpStatus.OK);
    }

    /*계정 이메일 변경*/
    @PatchMapping("/email")
    public ResponseEntity<ResponseDto> emailModify(@RequestBody @Valid EmailDto emailDto, @AuthMember Member member){
        /*인증번호 발급할때 이메일 중복에 대해 confirm을 하니까 굳이..? validate 할 필요 없을...듯..?*/

        myPageService.modifyEmail(member,emailDto);

        return new ResponseEntity<>(new ResponseDto("null","이메일 변경완료",HttpStatus.OK),HttpStatus.OK);
    }

    /*알림설정*/
    @PatchMapping("/notification")
    public ResponseEntity<?> notificationModify(@RequestBody NotificationSettingDto notificationSettingDto, @AuthMember Member member){

            myPageService.modifyNotification(notificationSettingDto,member);

        return new ResponseEntity<>(new ResponseDto("null","알림설정 변경완료",HttpStatus.OK),HttpStatus.OK);
    }
}
