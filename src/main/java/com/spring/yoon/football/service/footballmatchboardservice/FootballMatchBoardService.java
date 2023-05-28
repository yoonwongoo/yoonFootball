package com.spring.yoon.football.service.footballmatchboardservice;


import com.spring.yoon.football.config.security.AuthMember;
import com.spring.yoon.football.domain.footballmatchboard.FootballMatchBoard;
import com.spring.yoon.football.domain.footballmatchboard.FootballMatchBoardRepository;
import com.spring.yoon.football.domain.footballmatchboardreply.FootballMatchBoardReplyRepository;
import com.spring.yoon.football.domain.member.Member;
import com.spring.yoon.football.dto.footballmatchboard.FootballMatchBoardDto;
import com.spring.yoon.football.enums.boardtype.BoardType;
import com.spring.yoon.football.event.countviews.FootballMatchBoardCountViews;
import com.spring.yoon.football.handler.exception.ErrorCode;
import com.spring.yoon.football.handler.exception.customserviceexception.accessentitydeniedexception.AccessEntityDeniedException;
import com.spring.yoon.football.handler.exception.customserviceexception.entitynotfoundexception.FootballMatchBoardNotFoundException;
import com.spring.yoon.football.service.countviewsservice.CountViewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*권한 검사 중복코드 발생생생*/
@RequiredArgsConstructor
@Service
public class FootballMatchBoardService implements CountViewsService {

    private final FootballMatchBoardRepository footBallMatchBoardRepository;

    private final FootballMatchBoardReplyRepository footballMatchBoardReplyRepository;

    private final ApplicationEventPublisher applicationEventPublisher;
    
    /*글 등록*/
    @Transactional
    public void addFootballMatchBoard(FootballMatchBoardDto.AddRequest request, Member member){

        FootballMatchBoard footballMatchBoardEntity =request.toEntity(member);
        footBallMatchBoardRepository.save(footballMatchBoardEntity);
    }



    /*글 수정하기 폼 */
    @Transactional(readOnly = true)
    public FootballMatchBoardDto.Detail  findFootballMatchBoardModifyForm(long footballMatchBoardId, Member member){
        /*풋볼게시글 유무 유효성 체크*/
        FootballMatchBoard footballMatchBoard = existsFootballMatchBoard(footballMatchBoardId);
        /*나의 게시글이 맞는지 권한*/
        checkMyFootballMatchBoard(member.getId(), footballMatchBoard.getMember().getId());

        return new FootballMatchBoardDto.Detail(footballMatchBoard);

    }

    /*글 보기*/
    @Transactional(readOnly = true)
    public FootballMatchBoardDto.Detail findFootballMatchBoard(long id, HttpServletRequest request, HttpServletResponse response){
        applicationEventPublisher.publishEvent(new FootballMatchBoardCountViews(BoardType.FOOTBALL_MATCH_BOARD,id,request,response));
      return footBallMatchBoardRepository.findByFootballBoardDetail(id).map(FootballMatchBoardDto.Detail::new)
              .orElseThrow(()->{throw new FootballMatchBoardNotFoundException("해당게시글이 없습니다", ErrorCode.ENTITY_NOT_FOUND);});
    }


    /*글 목록보기*/
    @Transactional
   public Slice<FootballMatchBoardDto.SliceList> findFootballMatchBoardList(FootballMatchBoardDto.Search search, Pageable pageable){
       Slice<FootballMatchBoard> list = footBallMatchBoardRepository.findByFootballMatchBoardList(search,pageable);
       Slice<FootballMatchBoardDto.SliceList> sliceLists = list.map(FootballMatchBoardDto.SliceList::new);

       return sliceLists;
    }

    /*글 삭제하기 */
    @Transactional
    public void removeFootBallMatchBoard(@PathVariable Long footballMatchBoardId, @AuthMember Member member){
        /*풋볼게시글 유무 유효성 체크*/
        FootballMatchBoard footballMatchBoard = existsFootballMatchBoard(footballMatchBoardId);

        /*나의 게시글이 맞는지 권한*/
        checkMyFootballMatchBoard(member.getId(), footballMatchBoard.getMember().getId());


        footballMatchBoardReplyRepository.deleteByFootballMatchBoardReplyList(footballMatchBoardId);
        footBallMatchBoardRepository.delete(footballMatchBoard);
    }
    /*글 수정*/
    @Transactional
    public void modifyFootballMatchBoard(FootballMatchBoardDto.ModifyRequest request, @AuthMember Member member){
        /*풋볼게시글 유무 유효성 체크*/
        FootballMatchBoard footballMatchBoard = existsFootballMatchBoard(request.getFootballMatchBoardId());


        /*나의 게시글이 맞는지 권한*/
        checkMyFootballMatchBoard(member.getId(), footballMatchBoard.getMember().getId());


        /*변경*/
        footballMatchBoard.updateFootballMatchBoard(request.getSkillLevel(), request.getLocation(), request.getLocationStreet(), request.getContent()
                ,request.getParticipationFee(),request.getMatchDay(),request.getStartTime(),request.getEndTime());
        footBallMatchBoardRepository.save(footballMatchBoard);
    }
    
    
    
    
    /*풋볼게시글 유무 유효성 체크*/

    private FootballMatchBoard existsFootballMatchBoard(long footballStadiumId){
         return footBallMatchBoardRepository.findById(footballStadiumId).orElseThrow(()->{
            throw new FootballMatchBoardNotFoundException("존재하지않는 풋볼게시글입니다,", ErrorCode.ENTITY_NOT_FOUND);
    });
    }
    
    /*나의 게시글이 맞는지 권한*/

    private void checkMyFootballMatchBoard(Long memberId, long footballBoardMemberId){
        if(!(memberId == footballBoardMemberId))
            throw new AccessEntityDeniedException("권한이 없습니다",ErrorCode.ACCESS_DENIED);

    }


    /*----------------------------------조회수를 위한-------------------------------------------------*/
    /*게시글 유형 타입반환*/
    @Override
    public BoardType getBoardType() {
        return BoardType.FOOTBALL_MATCH_BOARD;
    }
    /*조회수 증가*/
    @Override
    public void countViews(long id) {
        footBallMatchBoardRepository.updateFootballMatchBoardCountView(id);
    }
}
