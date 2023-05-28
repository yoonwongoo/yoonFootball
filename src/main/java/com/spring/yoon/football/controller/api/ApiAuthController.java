package com.spring.yoon.football.controller.api;


import com.spring.yoon.football.controller.validator.EmailValidator;
import com.spring.yoon.football.controller.validator.SignUpValidator;
import com.spring.yoon.football.dto.ResponseDto;
import com.spring.yoon.football.dto.auth.SignUpDto;
import com.spring.yoon.football.dto.email.EmailAuthCodeDto;
import com.spring.yoon.football.service.authservice.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class ApiAuthController {
    private final AuthService authService;
    private final SignUpValidator signUpValidator;
    private final EmailValidator emailValidator;


    @InitBinder("signUpDto")
    public void initBinderSignUpDto(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpValidator);
    }
    @InitBinder("emailAuthCodeDto")
    public void initBinderEmailDto(WebDataBinder webDataBinder) {
                   webDataBinder.addValidators(emailValidator);
    }

    /*회원가입*/
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpDto signUpDto ){

        authService.addSignUpDto(signUpDto);

        System.out.println(signUpDto.toString());
        return new ResponseEntity<>(new ResponseDto<>(null,"회원가입 성공",HttpStatus.CREATED), HttpStatus.CREATED);
    }

    /*회원가입 이메일 인증번호 */
    @PostMapping("/auth/sign-up")
    public ResponseEntity<?> sendAuthCodeEmail(@RequestBody @Valid EmailAuthCodeDto emailAuthCodeDto  ){

        authService.sendToAutoCodeEmail(emailAuthCodeDto);
        return new ResponseEntity<>(new ResponseDto<>(null,"인증번호 발송완료",HttpStatus.OK), HttpStatus.OK);
    }






}
