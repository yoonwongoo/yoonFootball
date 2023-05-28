package com.spring.yoon.football.controller;


import com.spring.yoon.football.config.security.AuthMember;
import com.spring.yoon.football.domain.member.Member;
import com.spring.yoon.football.dto.footballmatchboard.FootballMatchBoardDto;
import com.spring.yoon.football.dto.footballmatchboardreply.FootballMatchBoardReplyDto;
import com.spring.yoon.football.dto.footballsocialmatchenrollment.FootballSocialMatchEnrollmentDto;
import com.spring.yoon.football.dto.member.EmailDto;
import com.spring.yoon.football.dto.member.NotificationSettingDto;
import com.spring.yoon.football.dto.member.ProfileDto;
import com.spring.yoon.football.enums.member.Gender;
import com.spring.yoon.football.enums.member.PreferPosition;
import com.spring.yoon.football.enums.member.SkillLevel;
import com.spring.yoon.football.service.mypageservice.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
/*유저에 관련된 컨트롤러*/
@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;



    /*마이 페이지*/
    @GetMapping("/my-page")
    public String myPageForm(@AuthMember Member member, Model model){

        model.addAttribute("member",member);
        return "mypage/my-page";
    }
    /*내 계정 정보 페이지*/
    @GetMapping("/my-page/account")
    public String accountForm(@AuthMember Member member, Model model){

        model.addAttribute("member",member);
        return "mypage/account";
    }
    /*내 프로필 페이지*/
    @GetMapping("/my-page/profile")
    public String profileForm(@AuthMember Member member, Model model){

        model.addAttribute("skillLevel", SkillLevel.getEnumList());
        model.addAttribute("preferPosition", PreferPosition.getEnumList());
        model.addAttribute("gender", Gender.getEnumList());
        model.addAttribute("profile", ProfileDto.form(member));
        return "mypage/profile";
    }
    /*패스워드 변경 페이지*/
    @GetMapping("/my-page/account/password")
    public String passwordModifyForm(){
        
        return "mypage/password";
    }
    /*이메일 변경페이지*/
    @GetMapping("/my-page/account/email")
    public String emailModifyForm(@AuthMember Member member,Model model){

        model.addAttribute("email", EmailDto.form(member));
        return "mypage/email";
    }
    /*알림설정 페이지*/
    @GetMapping("/my-page/account/notification")
    public String notificationModifyForm(@AuthMember Member member, Model model){
        model.addAttribute("notificationSetting", NotificationSettingDto.form(member));
        return "mypage/notification";
    }

    /*내가 쓴 글*/
    @GetMapping("/my-page/football-match-board")
    public String myFootballMatchBoardList(@AuthMember Member member, Pageable pageable, Model model){

      Page<FootballMatchBoardDto.MyFootballMatchBoard> myFootballMatchBoardPaging =
              myPageService.findMyFootballMatchBoardList(member, pageable);

        int currentPage = myFootballMatchBoardPaging.getPageable().getPageNumber()+1;
        int startPage= Math.max(1,currentPage-4);
        int lastPage= Math.min(myFootballMatchBoardPaging.getTotalPages(), currentPage+4);
        int totalLastPage = myFootballMatchBoardPaging.getTotalPages()-1;//제일 마지막 0부터 시작
        boolean isFirst  = myFootballMatchBoardPaging.isFirst();
        boolean isLast = myFootballMatchBoardPaging.isLast();

        model.addAttribute("myFootballList", myFootballMatchBoardPaging.getContent());
        model.addAttribute("currentPage",currentPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("lastPage",lastPage);
        model.addAttribute("totalLastPage",totalLastPage);
        model.addAttribute("isFirst",isFirst);
        model.addAttribute("isLast",isLast);



        return "mypage/football-match-board";
    }
    /*내가 단 댓글*/
    @GetMapping("/my-page/football-match-board/reply")
    public String myFootballMatchBoardReplyList(@AuthMember Member member,Pageable pageable, Model model){

        Page<FootballMatchBoardReplyDto.MyFootballMatchBoardReply> myFootballMatchBoardReplyPaging =
                myPageService.findMyFootballMatchBoardReplyList(member,pageable);

        int currentPage = myFootballMatchBoardReplyPaging.getPageable().getPageNumber()+1;
        int startPage = Math.max(1, currentPage-4);
        int lastPage = Math.min(myFootballMatchBoardReplyPaging.getTotalPages(),currentPage+4);
        int totalLastPage = myFootballMatchBoardReplyPaging.getTotalPages()-1;//제일 마지막
        boolean isFirst = myFootballMatchBoardReplyPaging.isFirst();
        boolean isLast = myFootballMatchBoardReplyPaging.isLast();

        model.addAttribute("myFootballReplyList",myFootballMatchBoardReplyPaging.getContent());
        model.addAttribute("currentPage",currentPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("lastPage",lastPage);
        model.addAttribute("totalLastPage",totalLastPage);
        model.addAttribute("isFirst",isFirst);
        model.addAttribute("isLast",isLast);
        return "mypage/football-match-board-reply";
    }

    /*나의 소셜풋볼매치*/
    @GetMapping("/my-page/football-social-match-enroll/list")
    public String footballSocialMatchEnrollList(@AuthMember Member member, Pageable pageable, Model model){
       Page<FootballSocialMatchEnrollmentDto.Response> list = myPageService.findMyFootballSocialMatchList(member,pageable);

       int currentPage = list.getPageable().getPageNumber()+1;
       int startPage = Math.max(1,currentPage-4);
       int lastPage =  Math.min(list.getTotalPages(),currentPage+4);
       int totalLastPage = list.getTotalPages();
       boolean isFirst = list.isFirst();
       boolean isLast = list.isLast();

       model.addAttribute("list",list.getContent());
       model.addAttribute("currentPage",currentPage);
       model.addAttribute("startPage",startPage);
       model.addAttribute("lastPage",lastPage);
       model.addAttribute("totalLastPage",totalLastPage);
       model.addAttribute("isFirst",isFirst);
       model.addAttribute("isLast",isLast);

       return "mypage/football-social-match-enroll";
    }
}
