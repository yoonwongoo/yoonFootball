package com.spring.yoon.football.service.noticeboardservice;


import com.spring.yoon.football.domain.noticeboard.NoticeBoardRepository;
import com.spring.yoon.football.dto.noticeboard.NoticeBoardDto;
import com.spring.yoon.football.enums.boardtype.BoardType;
import com.spring.yoon.football.enums.noticecategory.NoticeCategory;
import com.spring.yoon.football.event.countviews.NoticeBoardCountViews;
import com.spring.yoon.football.handler.exception.ErrorCode;
import com.spring.yoon.football.handler.exception.customserviceexception.entitynotfoundexception.NoticeBoardNotFoundException;
import com.spring.yoon.football.service.countviewsservice.CountViewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class NoticeBoardService implements CountViewsService {

    private final NoticeBoardRepository noticeBoardRepository;
    
    private final ApplicationEventPublisher applicationEventPublisher;
    
    /*카테고리 별 공지사항 목록*/
    @Transactional(readOnly = true)

    public Page<NoticeBoardDto.ListResponse> findNoticeBoardList(NoticeCategory noticeCategory, Pageable pageable){
       Page<NoticeBoardDto.ListResponse> list = noticeBoardRepository.findByNoticeCategory(noticeCategory,pageable).map(NoticeBoardDto.ListResponse::new);
       return list;
    }

    /*공지사항 자세히*/
    @Transactional
    public NoticeBoardDto.DetailResponse findNoticeBoardDetails(long noticeBoardId, HttpServletRequest request, HttpServletResponse response){
        applicationEventPublisher.publishEvent(new NoticeBoardCountViews(BoardType.NOTICE,noticeBoardId,request,response));
        return noticeBoardRepository.findById(noticeBoardId).map(NoticeBoardDto.DetailResponse::new)
                .orElseThrow(()-> {throw new NoticeBoardNotFoundException("존재하지않는 공지사항입니다", ErrorCode.ENTITY_NOT_FOUND);});
    }


    /*공지사항 자주 찾는 공지 조회수 top5*/
    @Cacheable(value = "findTop5NoticeBoardList")
    @Transactional(readOnly = true)
    public List<NoticeBoardDto.ListResponse> findTop5NoticeBoardList(){

        return noticeBoardRepository.findTop5ByOrderByCountViewsDesc().stream().map(NoticeBoardDto.ListResponse::new).collect(Collectors.toList());
    }
    /*----------------------------------조회수를 위한-------------------------------------------------*/
    @Override
    public void countViews(long id) {
        noticeBoardRepository.updateNoticeBoardCountView(id);
    }

    @Override
    public BoardType getBoardType() {
        return BoardType.NOTICE;
    }
}
