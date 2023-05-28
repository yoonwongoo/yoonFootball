$(function(){

/*풋볼 글 작성하기에서 검색지도 열기 버튼*/
$("#kakaoMapSearch-openBtn").click(function(){
        $(".kakaoMap-modal").css("display","block");
        map.relayout();
})


/*풋볼 글 작성하기*/
$("#write_football_match_board").click(function(){

var form =JSON.stringify($(".football_match_board_form").serializeObject());
console.log(form);

    $.ajax({
        url:"/api/football-match-board",
        type:"post",
        contentType:"application/json; charset=utf-8",
        data:form,
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

/*풋볼 글 수정하기*/
$("#football_match_modify_board").click(function(){

var form =JSON.stringify($(".football_match_board_modify_form").serializeObject());
console.log(form);

    $.ajax({
        url:"/api/football-match-board/edit",
        type:"put",
        contentType:"application/json; charset=utf-8",
        data:form,
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



})