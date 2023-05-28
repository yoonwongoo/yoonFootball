package com.spring.yoon.football.service.mypageservice;


import com.spring.yoon.football.domain.footballmatchboard.FootballMatchBoardRepository;
import com.spring.yoon.football.domain.footballmatchboardreply.FootballMatchBoardReplyRepository;
import com.spring.yoon.football.domain.member.Member;
import com.spring.yoon.football.domain.member.MemberRepository;
import com.spring.yoon.football.dto.email.EmailAuthCodeDto;
import com.spring.yoon.football.dto.email.EmailMessageDto;
import com.spring.yoon.football.dto.footballmatchboard.FootballMatchBoardDto;
import com.spring.yoon.football.dto.footballmatchboardreply.FootballMatchBoardReplyDto;
import com.spring.yoon.football.dto.footballsocialmatchenrollment.FootballSocialMatchEnrollmentDto;
import com.spring.yoon.football.dto.member.EmailDto;
import com.spring.yoon.football.dto.member.NotificationSettingDto;
import com.spring.yoon.football.dto.member.PasswordDto;
import com.spring.yoon.football.dto.member.ProfileDto;
import com.spring.yoon.football.service.emailservice.AuthEmailService;
import com.spring.yoon.football.service.footballsocialmatchenrollservice.FootballSocialMatchEnrollService;
import com.spring.yoon.football.service.verifyservice.VerifyAuthCodeService;
import com.spring.yoon.football.util.CreateAuthCode;
import com.spring.yoon.football.util.ImageUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MyPageService {

    private final FootballMatchBoardReplyRepository footballMatchBoardReplyRepository;
    private final FootballMatchBoardRepository footballMatchBoardRepository;

    private final FootballSocialMatchEnrollService footballSocialMatchEnrollService;
    private final MemberRepository memberRepository;
    private final TemplateEngine templateEngine;
    private final AuthEmailService authEmailService;
    private final ImageUploader imageUploader;
    private final VerifyAuthCodeService verifyAuthCode;
    private final BCryptPasswordEncoder bCryptEncoder;


    /*프로필 수정*/
    @Transactional
    public void modifyProfile(Member member, ProfileDto profileDto, Optional<MultipartFile>  image){

        String imageName = imageUploader.imageUpload(image);

        member.updateProfile(profileDto,imageName);

        memberRepository.save(member);
    }
    
    /*비밀번호 수정*/
    @Transactional
    public void modifyPassword(Member member, PasswordDto passwordDto){
        String encPassword = bCryptEncoder.encode(passwordDto.getPasswordConfirm());

        member.updatePassword(encPassword);
        memberRepository.save(member);
    }

    /*이메일 인증 필요*/
    /*이메일 변경*/
    @Transactional
    public void modifyEmail(Member member, EmailDto emailDto){
        verifyAuthCode.verifyAuthCode(emailDto.getEmail(), emailDto.getAuthCode());

        member.updateEmail(emailDto.getEmail());
        memberRepository.save(member);
        
    }

    /* 이메일변경  이메일 인증번호 발송*/
    @Transactional
    public void sendToAutoCodeEmail(EmailAuthCodeDto emailAuthCodeDto){
        String authCode= CreateAuthCode.createAuthCode();
        Context context = new Context();
        context.setVariable("title","이메일 변경 인증 이메일입니다");
        context.setVariable("content","인증시간은 5분입니다");
        context.setVariable("authCode",authCode);
        String message = templateEngine.process("email/auth-code", context);

        authEmailService.sendEmail(EmailMessageDto.builder()
                  .message(message)
                  .title("YoonFootBallEmail 인증")
                  .to(emailAuthCodeDto.getEmail())
                  .authCode(authCode)
                  .build());
    }


    /*내가 쓴 글*/
    @Transactional(readOnly = true)
    public Page<FootballMatchBoardDto.MyFootballMatchBoard> findMyFootballMatchBoardList(Member member, Pageable pageable){
       return footballMatchBoardRepository.findByMyFootballMatchBoardList(member.getId(), pageable)
                .map(FootballMatchBoardDto.MyFootballMatchBoard::new);
    }


    /*내가 쓴 댓글*/
    @Transactional(readOnly = true)
    public Page<FootballMatchBoardReplyDto.MyFootballMatchBoardReply> findMyFootballMatchBoardReplyList(Member member
    , Pageable pageable){

        return footballMatchBoardReplyRepository.findByFootballMatchBoardReplyList(member.getId(),pageable)
                .map(FootballMatchBoardReplyDto.MyFootballMatchBoardReply::new);
    }


    /*알림 설정*/
    @Transactional
    public void modifyNotification(NotificationSettingDto notificationSettingDto, Member member){

        member.updateNotification(notificationSettingDto.isFootballMatchBoardReplyByWeb(), notificationSettingDto.isFootballMatchBoardReplyByWeb());
        memberRepository.save(member);
    }
    
    
    /*나의 소셜풋볼매치*/
    @Transactional(readOnly = true)
    public Page<FootballSocialMatchEnrollmentDto.Response> findMyFootballSocialMatchList(Member member, Pageable pageable){

        return footballSocialMatchEnrollService.findByMyFootballSocialMatchList(member,pageable);

    }
}
