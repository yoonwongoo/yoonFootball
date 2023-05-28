package com.spring.yoon.football.service.footballmatchboardreplyservice;


import com.spring.yoon.football.domain.footballmatchboard.FootballMatchBoard;
import com.spring.yoon.football.domain.footballmatchboard.FootballMatchBoardRepository;
import com.spring.yoon.football.domain.footballmatchboardreply.FootballMatchBoardReply;
import com.spring.yoon.football.domain.footballmatchboardreply.FootballMatchBoardReplyRepository;
import com.spring.yoon.football.domain.member.Member;
import com.spring.yoon.football.dto.footballmatchboardreply.FootballMatchBoardReplyDto;
import com.spring.yoon.football.event.FootballMatchBoardReplyCreatedEvent;
import com.spring.yoon.football.handler.exception.ErrorCode;
import com.spring.yoon.football.handler.exception.customserviceexception.entitynotfoundexception.FootballMatchBoardNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

;

@RequiredArgsConstructor
@Service
public class FootballMatchBoardReplyService {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final FootballMatchBoardReplyRepository footballMatchBoardReplyRepository;
    private final FootballMatchBoardRepository footballMatchBoardRepository;

    /*댓글 작성*/
    @Transactional
    public void addFootballMatchBoardReply(FootballMatchBoardReplyDto.AddRequest addRequest, Member member){

        FootballMatchBoard footballMatchBoard =
                footballMatchBoardRepository.findByFootballBoardDetail(addRequest.getFootballMatchBoardId())
                        .orElseThrow((()->{throw new FootballMatchBoardNotFoundException("존재하지 않는 게시글입니다", ErrorCode.ENTITY_NOT_FOUND);
                        }));

        FootballMatchBoardReply reply = addRequest.toEntity(member);
        /*양방향 이므로 둘 다 넣어줌*/
        reply.addFootballMatchBoard(footballMatchBoard);
        /*event 발생 게시글 작성자에게 댓글 알림 발송*/
        applicationEventPublisher.publishEvent(new FootballMatchBoardReplyCreatedEvent(reply));

        footballMatchBoardReplyRepository.save(reply);

    }

    /*댓글 목록*/
    @Transactional(readOnly = true)
    public Page<FootballMatchBoardReplyDto.ListResponse> findFootballMatchBoardReplyList(long boardId, Pageable pageable){


     return footballMatchBoardReplyRepository.findByFootballMatchBoardReplyList(boardId, pageable)
             .map(FootballMatchBoardReplyDto.ListResponse::new);

    }

}
