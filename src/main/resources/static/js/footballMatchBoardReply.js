
/*최초로 댓글 로딩해줌*/
footballMatchBoardReplyList(0);
let footballMatchBoardId =$("#footballMatchBoardId").val();

/*글 지우기*/
$("#board-remove").click(function(){
    /*이건 ajax*/
    $.ajax({
            url:`/api/football-match-board/${footballMatchBoardId}`,
            type:"delete",
            beforeSend:function(xhr){
                xhr.setRequestHeader(header,token);
                xhr.setRequestHeader("Accept","application/json");
            }

    }).done(res=>{
        console.log(res);
    }).fail(err=>{
        console.log(err);
    })


})
$("#board-modify").click(function(){
    location.href=`/football-match-board/edit/${footballMatchBoardId}`
})
/*글 수정하기*/


/*댓글 작성하기*/
$("#write_football_match_board_reply").click(function(){
    let comment =$("#comment").val();
    let footballMatchBoardId = $("#footballMatchBoardId").val();
    let data =JSON.stringify({
        comment:comment,
        footballMatchBoardId:footballMatchBoardId

    })
    $.ajax({
        url:"/api/football-match-board/reply",
        type:"post",
        contentType:"application/json; charset=utf-8",
        data:data,
        beforeSend:function(xhr){
            xhr.setRequestHeader(header,token);
            xhr.setRequestHeader("Accept","application/json");
        }

    })
    .done(res=>{


    })
    .fail(err=>{

    });
})


/*댓글 목록보기 페이징*/
function footballMatchBoardReplyList(page){
    let footballMatchBoardId = $("#footballMatchBoardId").val();

    $.ajax({
         url:`/api/football-match-board/reply/${footballMatchBoardId}?page=${page}`,
         type:"GET"

    }).done(res=>{
        console.log(res.body);
       $(".comment-area-list").html(footballMatchBoardListHtml(res.body,footballMatchBoardId));

    }).fail(err=>{


    })



};


/*댓글 목록 Html*/
function footballMatchBoardListHtml(body,footballMatchBoardId){
    let replyHtml ="";

    let boardId=footballMatchBoardId;
    let startPage =body.startPage;
    let totalLastPage=body.totalLastPage;
    let lastPage =body.lastPage;
    let currentPage = body.currentPage;

    let isFirst= body.isFirst;
    let isLast = body.isLast;

    for(var i =0; i<body.list.length; i++){
        let replyList =body.list[i];
        let username = replyList.username;
        let comment = replyList.comment;
        let createdDate = replyList.createdDate;

    replyHtml+=`
    <div class="card mt-1">
       <div class="card-header d-flex justify-content-between">
        <span>${username}</span>
        <span>${createdDate}</span>
       </div>
       <div class="card-body">
         <span class="card-text">${comment}</span>
       </div>
    </div>
    `
    }

     replyHtml+=`
         <div class="row mt-2">
            <div class="col-12">
                <ul class="pagination pagination justify-content-center">`
                    if(!isFirst){
                     replyHtml+=`
                        <li class="page-item">
                            <a class="page-link" href="javascript:footballMatchBoardReplyList(0);"> << </a>
                        </li>`
                    }

                    for(var i =startPage; i<=lastPage; i++){
                    if(i==currentPage){
                     replyHtml+=`
                             <li class="page-item active">
                                <a class="page-link" href="javascript:footballMatchBoardReplyList(${i-1});">${i}</a>
                              </li>
                              `
                    }else{
                     replyHtml+=`
                             <li class="page-item ">
                                <a class="page-link" href="javascript:footballMatchBoardReplyList(${i-1});">${i}</a>
                              </li>
                              `
                    }

                    }
                    if(!isLast){
                     replyHtml+=`
                            <li class="page-item" >
                                <a class="page-link" href="javascript:footballMatchBoardReplyList(${totalLastPage-1});"> >> </a>
                            </li>`
                    }
                 replyHtml+=`
                </ul>
            </div>
         </div>
        `

return replyHtml;

}




