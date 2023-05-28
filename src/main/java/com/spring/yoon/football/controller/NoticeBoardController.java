package com.spring.yoon.football.controller;


import com.spring.yoon.football.dto.noticeboard.NoticeBoardDto;
import com.spring.yoon.football.enums.noticecategory.NoticeCategory;
import com.spring.yoon.football.service.noticeboardservice.NoticeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Controller
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;


    /*공지사항 메인 및 자주 질문하는 공지*/
    @GetMapping("/notices")
    public String noticeBoardList(Model model){
        model.addAttribute("noticeTop5BoardList",noticeBoardService.findTop5NoticeBoardList());
        model.addAttribute("noticeCategory", NoticeCategory.getEnumList());
        return "notice/notice-main";
    }
    /*카테고리별 공지사항 목록*/
    @GetMapping("/notices/{noticeCategory}")
    public String noticeCategoryList(@PathVariable NoticeCategory noticeCategory, Pageable pageable, Model model){

        Page<NoticeBoardDto.ListResponse> list = noticeBoardService.findNoticeBoardList(noticeCategory,pageable);


        int currentPage=list.getPageable().getPageNumber()+1;
        int startPage = Math.max(1,currentPage-4);
        int lastPage = Math.min(list.getTotalPages(),currentPage+4);
        System.out.println(lastPage+"---------------------------------------------------------");
        int totalLastPage = list.getTotalPages()-1;
        boolean first = list.isFirst();
        boolean last = list.isLast();

        model.addAttribute("noticeBoardList", list.getContent());
        model.addAttribute("noticeCategory",noticeCategory);
        model.addAttribute("currentPage",currentPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("lastPage",lastPage);
        model.addAttribute("first",first);
        model.addAttribute("last",last);

        return "notice/notice-list";
    }

    /*공지사항 상세내용*/
    @GetMapping("/notice/{id}")
    public String noticeCategoryList(@PathVariable long id, HttpServletRequest request, HttpServletResponse response, Model model){
       NoticeBoardDto.DetailResponse noticeBoard = noticeBoardService.findNoticeBoardDetails(id,request,response);
        model.addAttribute("noticeBoard",noticeBoard);
       return "notice/notice-details";
    }


}
