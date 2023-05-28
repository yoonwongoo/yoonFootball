package com.spring.yoon.football.domain.noticeboard;

import com.spring.yoon.football.enums.noticecategory.NoticeCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeBoardRepository extends JpaRepository<NoticeBoard,Long>{

    /*자주 찾는 공지*/
    List<NoticeBoard> findTop5ByOrderByCountViewsDesc();

    /*카테고리별 공지사항 목록 페이징*/
    Page<NoticeBoard> findByNoticeCategory(NoticeCategory noticeCategory, Pageable pageable);
    /*조회수 늘리기*/
    @Modifying
    @Query("UPDATE NoticeBoard nb SET nb.countViews =nb.countViews+1 where id =:boardId")
    void updateNoticeBoardCountView(long boardId);
}
