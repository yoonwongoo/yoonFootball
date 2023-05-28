$(function(){



/*수정완료 메세지*/
function alertMessage(alertMessage){

$(".alert").text(`${alertMessage}`).css("display","block").fadeOut(5000);

}


/*프로필 이미지 클릭 시*/
$(".profile_img_box").click(function(e){

    $("#image").click();
})




/*프로필 이미지 변경 시 감지*/
$("#image").change(function(e){

const fileReader = new FileReader();
fileReader.readAsDataURL(e.target.files[0]);
fileReader.onload = function() {
    $("#profile_img").attr("src", fileReader.result);
}
})




/*프로필 수정*/
$("#modify_profile").click(function(){

    let form =$(".profile_form").serializeObject();
    let image =$("#image")[0].files[0];

    const formData = new FormData();
            formData.append("image", image);
            formData.append("profileDto", new Blob([JSON.stringify(form)] , {type:"application/json; charset=utf-8"}));
    console.log(formData);
    $.ajax({
        url:"/api/profile",
        type:"post",
        processData: false,
        contentType: false,
        data:formData,
        beforeSend:function(xhr){
            xhr.setRequestHeader(header,token);
        }


    }).done(res=>{
        console.log(res);
        alertMessage("프로필 변경완료");
        $("#nickName-err").text("");
        $("#intro-err").text("");
        $("#gender-err").text("");
        $("#skillLevel-err").text("");
        $("#preferPosition-err").text("");

    }).fail(err=>{
        console.log(err);
        $("#nickName-err").text("");
        $("#intro-err").text("");
        $("#gender-err").text("");
        $("#skillLevel-err").text("");
        $("#preferPosition-err").text("");

        $("#nickName-err").text(err.responseJSON.body.nickName);
        $("#intro-err").text(err.responseJSON.body.intro);
        $("#gender-err").text(err.responseJSON.body.gender);
        $("#skillLevel-err").text(err.responseJSON.body.skillLevel);
        $("#preferPosition-err").text(err.responseJSON.body.preferPosition);
    });


})

/*비밀번호 수정*/
$("#modify_password").click(function(){


    let form =JSON.stringify($(".password_form").serializeObject());

    $.ajax({
        url:"/api/password",
        type:"patch",
        contentType:"application/json-patch+json; charset=utf-8",
        data:form,
        beforeSend:function(xhr){
            xhr.setRequestHeader(header,token);
        }
     }).done(res=>{

        console.log(res);
        if(res.status=='OK'){
            alert("변경되었습니다");
            location.href="/my-page/account";
        }
     }).fail(err=>{
        $("#password_err").text("");
        $("#passwordConfirm_err").text("");

        $("#password_err").text(err.responseJSON.body.password);
        $("#passwordConfirm_err").text(err.responseJSON.body.passwordConfirm);
     })



})


/*이메일 수정*/
$("#modify_email").click(function(){


    let form =JSON.stringify($(".email_form").serializeObject());

    $.ajax({
        url:"/api/email",
        type:"patch",
        contentType:"application/json-patch+json; charset=utf-8",
        data:form,
        beforeSend:function(xhr){
            xhr.setRequestHeader(header,token);
        }
     }).done(res=>{

        console.log(res);
        if(res.status=='OK'){
            alert("변경되었습니다");
            location.href="/my-page/account";
        }
     }).fail(err=>{
        $("#email_err").text("");

        $("#email_err").text(err.responseJSON.body.email);

     })



})

/*이메일 변경 인증번호 이메일 전송*/
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


/*알림 설정*/
$(".notification_form").change(function(){

    let form =JSON.stringify($(".notification_form").serializeObject());
    console.log(form);


    $.ajax({
            url:"/api/notification",
            contentType:"application/json; charset=UTF-8",
            type:"patch",
            data:form,
            beforeSend:function(xhr){
                xhr.setRequestHeader(header,token);
                xhr.setRequestHeader("Accept","application/json");
            }

    }).done(res=>{

    }).fail(err=>{


    })
})




})
