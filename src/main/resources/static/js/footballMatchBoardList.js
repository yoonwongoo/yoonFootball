


const footballMatchBoardListSearch =(function(){

    let matchDay ="";
    let order =0; //등록순
    let skillLevel=""; //실력

    const getMatchDay =function(){
        return matchDay;
    }
    const setMatchDay = function(set){
        matchDay=set;
    }

    const getOrder =function(){
       return getOrder;
    }
    const setOrder = function(set){
        order=set;
    }

    const getSkillLevel =function(){
        return skillLevel;
    }
    const setSkillLevel = function(set){
         skillLevel=set;
    }

        return {
                getMatchDay:getMatchDay,
                setMatchDay:setMatchDay,
                getOrder:getOrder,
                setOrder:setOrder,
                getSkillLevel:getSkillLevel,
                setSkillLevel:setSkillLevel
        }




})();
/*게시글 목록 ajax*/
function footballMatchBoardList(data,type){

    $.ajax({
        url:"/api/football-match-board/list",
        type:"get",
        data:data,
        beforeSend:function(xhr){
        xhr.setRequestHeader("Accept","application/json");
        }

    }).done(res=>{
        console.log(res);
        if(res.body.content.length==0){
            $("#more_list").remove();
        }
        else if(type=="html"){
            alert("html");
            $(".football-board-list").html(footballMatchBoardListSearchHtml(res.body));

        }else{
            alert("append");
            $(".football-board-list").append(footballMatchBoardListSearchHtml(res.body));
        }

    }).fail(err=>{
        console.log(err);
    })

}
/*더보기 클릭 시*/
$("#more_list").click(function(){
    let lastId = $(".football-board-list a").last().data("board_id");
    let data= $("#search_form").serialize();
    data+=`&id=${lastId}`
    footballMatchBoardList(data,"append");


})

/*날짜 변경*/
$("#matchDay").change(function(){
    let data= $("#search_form").serialize();
    footballMatchBoardList(data,"html");
});


/*실력 변경*/
$("#skillLevel").change(function(){
    let data= $("#search_form").serialize();
    footballMatchBoardList(data,"html");
})


/*마감 제외*/
$("#hide").change(function(e){
    let data= $("#search_form").serialize();
    footballMatchBoardList(data,"html");
})


/*글 목록*/
function footballMatchBoardListSearchHtml(list){
    let boardHtml="";
    for(var i=0; i<list.content.length; i++){
       let boardList = list.content[i];
       let footballBoardId =boardList.footballBoardId;
       let name = boardList.name;;
       let skillLevel = boardList.skillLevel;
       let location = boardList.location;
       let locationStreet = boardList.locationStreet;
       let participationFee = boardList.participationFee;
       let matchDay =boardList.matchDay;
       let startTime =boardList.startTime;
       let endTime = boardList.endTime;
       let createdDate =boardList.createdDate;
       let isRecruitment=boardList.recruitment;



boardHtml+=
` <a href="/football-match-board/${footballBoardId}" data-board_id=${footballBoardId} >
     <div class="card">
       <div class="card-body">
         <h5 class="card-title">${location}</h5>
         <div class="card-text">${locationStreet}</div>

         <span class="fw-bold">${matchDay}/</span>
         <span>${startTime}~</span>
         <span>${endTime}</span>
         <div class="d-flex justify-content-between">
             <div class="d-flex">
             <div>
                 <span class="text-primary  fw-bold fs-6">실력-</span>
                 <span class="badge bg-secondary">${skillLevel}</span>
             </div>
             <div class="ms-1">
                 <span class="text-primary  fw-bold fs-6">참가비-</span>`
                 if(participationFee == 0){
                      boardHtml+=`<span class="badge bg-secondary">무료</span>`
                 }else{
                      boardHtml+=`<span class="badge bg-secondary">${participationFee}원</span>`
                 }
                boardHtml+=`
             </div>
             </div>

             <div class="me-1">`
                if(isRecruitment){
                    boardHtml+=`<div class="badge bg-primary">모집중</div>`
                }else{
                    boardHtml+=`<div class="badge bg-secondary">마감</div>`
                }
                boardHtml+=`
              </div>
         </div>
       </div>
     </div>
 </a>`
}
return boardHtml;

}
