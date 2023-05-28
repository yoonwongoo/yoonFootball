/*풋볼경기장 삭제 및 수정*/

/*이미지클릭*/
$("#img-box").click(function(){
    $("#image").click();
});



/*이미지변경*/
$("#image").change((e)=>{
    const fileReader= new FileReader();
    fileReader.readAsDataURL(e.target.files[0]);
    fileReader.onload = ()=>{
        $("#img-box img").attr("src",fileReader.result);
    }

})

/*수정하기 버튼*/
$("#football_stadium_modify").click(function(){
    let form = $(".football_stadium_modify_form").serializeObject();
    let imageFile=$("#image")[0].files[0];
    console.log(imageFile);
    console.log(form);

    const formData = new FormData();
    formData.append("modifyRequest",new Blob([JSON.stringify(form)], {type:"application/json; charset=utf-8"} ));

    formData.append("image",imageFile);

    console.log(formData);
    $.ajax({
        url:"/api/admin/football-stadium",
        type:"put",
        data:formData,
        processData: false,
        contentType: false,
        beforeSend:(xhr)=>{
              xhr.setRequestHeader(header,token);
              xhr.setRequestHeader("Accept","application/json; charset=utf-8");
        }

    }).done(res=>{
        console.log(res);
    }).fail(err=>{
        console.log(err);
    })
})


/*삭제하기 버튼*/
$("#football_stadium_remove").click(function(){
    let id =$("#footballStadiumId").val();
      $.ajax({
            url:`/api/admin/football-stadium/${id}`,
            type:"delete",
            beforeSend:(xhr)=>{
                  xhr.setRequestHeader(header,token);
                  xhr.setRequestHeader("Accept","application/json; charset=utf-8");
            }

        }).done(res=>{
            console.log(res);
        }).fail(err=>{
            console.log(err);
        })


})
