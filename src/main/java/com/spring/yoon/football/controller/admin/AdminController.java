package com.spring.yoon.football.controller.admin;


import com.spring.yoon.football.config.security.AuthMember;
import com.spring.yoon.football.domain.member.Member;
import com.spring.yoon.football.dto.footballsocialmatch.FootballSocialMatchDto;
import com.spring.yoon.football.dto.footballstadium.FootBallStadiumDto;
import com.spring.yoon.football.dto.member.MemberDto;
import com.spring.yoon.football.enums.member.SkillLevel;
import com.spring.yoon.football.enums.noticecategory.NoticeCategory;
import com.spring.yoon.football.service.adminservice.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


/*관리자 페이지*/
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;


    /*admin main페이지*/
    @GetMapping("/main")
    public String adminMainPage(@AuthMember Member member, Model model){
        /*대충 등록된 매치? 이런 것들 하면될 듯..?*/
        model.addAttribute("member",member);
        model.addAttribute("mySocialMatchScheduleList",adminService.findMySocialMatchScheduleList(member));
        return "admin/main";
    }


    /*회원관리*/
    @GetMapping("/member")
    public String memberList(Pageable pageable, Model model){
       Page<MemberDto.Response> memberListPaging = adminService.findMemberList(pageable);

        int currentPage = memberListPaging.getPageable().getPageNumber()+1;
        int startPage= Math.max(1,currentPage-4);
        int lastPage= Math.min(memberListPaging.getTotalPages(), currentPage+4);
        int totalLastPage = memberListPaging.getTotalPages()-1;//제일 마지막 0부터 시작
        boolean isFirst  = memberListPaging.isFirst();
        boolean isLast = memberListPaging.isLast();
        model.addAttribute("memberList", memberListPaging.getContent());
        model.addAttribute("currentPage",currentPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("lastPage",lastPage);
        model.addAttribute("totalLastPage",totalLastPage);
        model.addAttribute("isFirst",isFirst);
        model.addAttribute("isLast",isLast);



        return "admin/member-list";
    }
    
    /*구장 등록하기 폼*/
    @GetMapping("/football-stadium")
    public String footballStadiumForm(){
        return "admin/football-stadium";
    }
    


    /*공지사항 및 이벤트 등록 폼*/
    @GetMapping("/notice")
    public String noticeForm(Model model){
        model.addAttribute("noticeCategory", NoticeCategory.getEnumList());

        return "admin/notice-board-write";
    }

    /*카테고리 등록 폼*/
    @GetMapping("/category")
    public String categoryForm(){

        return null;
    }


    /*등록된 구장 리스트 */
    @GetMapping("/football-stadium/list")
    public String footballStadiumList(Model model){

        model.addAttribute("footballStadiumList",adminService.findFootballStadiumList());
        return "admin/football-stadium-list";
    }
    
    /*등록된 구장 상세보기 및 수정페이지 제공*/
    @GetMapping("/football-stadium/{id}")
    public String footballStadiumDetails(@PathVariable long id, Model model){
        model.addAttribute("footballStadium",adminService.findFootballStadiumDetails(id));
        return  "admin/football-stadium-details";
    }

    /*매치를 등록할 수 있는 구장목록
    * 매치등록하기 할 때 보여지는 구장들이다.
    **/
    @GetMapping("/football-match/list")
    public String footballStadiumMatchList(FootBallStadiumDto.Search search , Model model){
        model.addAttribute("footballStadiumMatchList",adminService.findFootballStadiumMatchList(search));
        model.addAttribute("matchDay",search.getMatchDay());
        return "admin/football-stadium-match-list";
    }

    /*매치 등록폼*/
    /*구장번호, 날짜, 시작시간, 마감시간*/
    @GetMapping("/football-social-match")
    public String footballSocialMatchForm(@ModelAttribute FootballSocialMatchDto.ResponseForm responseForm, Model model){
        model.addAttribute("responseForm",responseForm);
        model.addAttribute("skillLevel", SkillLevel.getEnumList());
        return "admin/football-social-match-write";
    }







}
