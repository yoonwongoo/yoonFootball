$(function(){

/*회원가입*/
$("#sign_up").click(function(e){
    e.preventDefault();
    var form = JSON.stringify($("#sign_up_form").serializeObject());
    $.ajax({
        url:"/api/sign-up",
        type:"post",
        data:form,
        contentType:"application/json; charset=utf-8",
        beforeSend:function(xhr){
            xhr.setRequestHeader("Accept","application/json");
            xhr.setRequestHeader(header,token);
        }

    }).done(res=>{
          console.log(res);
        if(res.status=="CREATED"){
            alert("회원가입성공!");
            location.href="/sign-in";

        }else{
             alert("일시적인 오류 발생 다시 시도해주세요");
         }

    }).fail(err=>{
        console.log(err);
        $("#username-err").text("");
        $("#password-err").text("");
        $("#name-err").text("");
        $("#email-err").text("");

        $("#username-err").text(err.responseJSON.body.username);
        $("#password-err").text(err.responseJSON.body.password);
        $("#name-err").text(err.responseJSON.body.name);
        $("#email-err").text(err.responseJSON.body.email);


    })

})

/*회원가입 인증번호 이메일 전송*/
$("#email_auth_number").click(function(){

    let data=JSON.stringify({
        email:$("#email").val()
    });


    $.ajax({
            url:"/api/auth/sign-up",
            type:"post",
            contentType:"application/json; charset=utf-8",
            data:data,
            beforeSend:function(xhr){
                xhr.setRequestHeader("Accept","application/json");
                xhr.setRequestHeader(header,token);
            }

    }).done(res=>{
        console.log(res);
        if(res.status=='200'){
            $("#email_err").text("");
            $("#email_err").text("전송되었습니다");
        }


    }).fail(err=>{
        console.log(err);
        $("#email_err").text("");
        $("#email_err").text(err.responseJSON.body.email);
    })


})

/*로그인하기*/
$("#sign_in").click(function(e){
    e.preventDefault();

    let form = JSON.stringify($("#sign_in_form").serializeObject());

    $.ajax({
        url:"/api/login",
        type:"POST",
        contentType:"application/json; charset=utf-8",
        data:form,
        beforeSend:function(xhr){
            xhr.setRequestHeader("Accept","application/json");
            xhr.setRequestHeader(header,token);
        }


    }).done(res=>{
        console.log(res);
        if(res.body.status=="OK"){
            location.href=res.body.body;
        }else{
             alert("일시적인 오류 발생 다시 시도해주세요");
         }

    }).fail(err=>{
    console.log(err);
        if(err.responseJSON.body.status =="UNAUTHORIZED"){
            alert("아이디 또는 비밀번호가 일치하지 않습니다");
        }else{
            alert("일시적인 오류 발생 다시 시도해주세요");
        }

    })

});





})