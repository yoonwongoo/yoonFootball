package com.spring.yoon.football.service.adminservice;


import com.spring.yoon.football.config.security.AuthMember;
import com.spring.yoon.football.domain.footballsocialmatch.FootballSocialMatch;
import com.spring.yoon.football.domain.footballsocialmatch.FootballSocialMatchRepository;
import com.spring.yoon.football.domain.footballstadium.FootballStadium;
import com.spring.yoon.football.domain.footballstadium.FootballStadiumRepository;
import com.spring.yoon.football.domain.member.Member;
import com.spring.yoon.football.domain.member.MemberRepository;
import com.spring.yoon.football.domain.noticeboard.NoticeBoard;
import com.spring.yoon.football.domain.noticeboard.NoticeBoardRepository;
import com.spring.yoon.football.dto.footballsocialmatch.FootballSocialMatchDto;
import com.spring.yoon.football.dto.footballstadium.FootBallStadiumDto;
import com.spring.yoon.football.dto.member.MemberDto;
import com.spring.yoon.football.dto.noticeboard.NoticeBoardDto;
import com.spring.yoon.football.handler.exception.ErrorCode;
import com.spring.yoon.football.handler.exception.customserviceexception.entitynotfoundexception.FootballStadiumNotFoundException;
import com.spring.yoon.football.util.ImageUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminService {
    @Value("${file.path}")
    private  String path;
    private final MemberRepository memberRepository;
    
    private final ImageUploader imageUploader;

    private final FootballStadiumRepository footBallStadiumRepository;

    private final NoticeBoardRepository noticeBoardRepository;
    private final FootballSocialMatchRepository footballSocialMatchRepository;

    /*회원관리 & 회원목록*/
    public Page<MemberDto.Response> findMemberList(Pageable pageable) {

        Page<MemberDto.Response> memberListPaging = memberRepository.findByMemberList(pageable).map(MemberDto.Response::new);

        return memberListPaging;

    }

    /*풋살 구장등록*/
    @Transactional
    public void addFootballStadium(FootBallStadiumDto.AddRequest addRequest,
                                   Optional<MultipartFile> stadiumImageUrl) {
        String imageName = imageUploader.imageUpload(stadiumImageUrl);

        FootballStadium footBallStadium = addRequest.toEntity(imageName);
        footBallStadiumRepository.save(footBallStadium);
    }

    /*풋살 구장 목록*/
    /*사실 상 풋살장이 메모리랑 응답시간에 무리가 갈 정도로 개수가 많지 않다.*/
    /*많아지는 경우에는 다시 리팩토링 들어가야한다.*/
    @Transactional(readOnly = true)
    public List<FootBallStadiumDto.Response> findFootballStadiumList() {
        return footBallStadiumRepository.findAllByOrderByIdDesc().stream().map(FootBallStadiumDto.Response::new).collect(Collectors.toList());
    }


    /*구장 상세보기*/
    @Transactional(readOnly = true)
    public FootBallStadiumDto.Response findFootballStadiumDetails(long id) {

        /*구장유무 유효성 체크 및 return*/
        return  new FootBallStadiumDto.Response(existsFootballStadium(id));
    }

    /*구장 수정하기*/
    @Transactional
    public void modifyFootballStadium(FootBallStadiumDto.ModifyRequest request,
                                      Optional<MultipartFile> multipartFile,
                                      @AuthMember Member member) {
        /*구장유무 유효성 체크*/
        FootballStadium footballStadium = existsFootballStadium(request.getFootballStadiumId());

        /*이미지 업로드*/
        String imageName = imageUploader.imageUpload(multipartFile);

        footballStadium.updateFootballStadium(request.getLocation(),request.getLocationStreet(),
                request.getStadiumName(),request.getStadiumDetails(),imageName,
                request.isAbleToShower(), request.isAbleToParking(), request.isAbleToRentalShoes(),
                request.getAvailableStartTime(), request.getAvailableEndTime());

        footBallStadiumRepository.save(footballStadium);

    }



    /*구장 삭제하기*/
    @Transactional
    public void removeFootballStadium(long footballStadiumId){
        /*구장유무 유효성 체크*/
        FootballStadium footballStadium = existsFootballStadium(footballStadiumId);

        footBallStadiumRepository.delete(footballStadium);
    }

    /*매치를 등록할 수 있는 구장목록
    * 매치등록하기 할 때 보여지는 구장들이다.
    **/
    @Transactional(readOnly = true)
    public List<FootBallStadiumDto.FootballStadiumMatchResponse> findFootballStadiumMatchList(FootBallStadiumDto.Search search){
        return footBallStadiumRepository.findByFootballStadiumMatchList(search);

    }

    /*매치 등록하기*/
    @Transactional
    public void addFootballSocialMatch(FootballSocialMatchDto.AddRequest addRequest, Member member){
        /*구장존재 유효성 체크*/
        FootballStadium footballStadium = existsFootballStadium(addRequest.getFootballStadiumId());


        FootballSocialMatch footballSocialMatch = addRequest.toEntity(member,footballStadium);
        footballSocialMatchRepository.save(footballSocialMatch);

    }

    /*구장유무 유효성 체크*/
    @Transactional(readOnly = true)
    private FootballStadium existsFootballStadium(long footballStadiumId){
       return footBallStadiumRepository.findById(footballStadiumId).orElseThrow(()->{
            throw new FootballStadiumNotFoundException("존재하지않는 구장입니다,", ErrorCode.ENTITY_NOT_FOUND);
        });
    }
    
    /*공지사항 등록*/
    @Transactional
    public void addNoticeBoard(NoticeBoardDto.AddRequest addRequest, Member member){

        NoticeBoard noticeBoardEntity = addRequest.toEntity(addRequest.getTitle(),addRequest.getContent(),addRequest.getNoticeCategory(),member);

        noticeBoardRepository.save(noticeBoardEntity);

    }


    /*관리자 페이지 입장 시 매니저 소셜매치 스케줄 */
    @Transactional(readOnly = true)
    public List<FootballSocialMatchDto.Response> findMySocialMatchScheduleList(Member member){
        return footballSocialMatchRepository.findByMyScheduleSocialMatchList(member)
               .stream().map(FootballSocialMatchDto.Response::new).collect(Collectors.toList());

    }
}
