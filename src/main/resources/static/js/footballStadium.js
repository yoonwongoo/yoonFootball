
/*이미지 수정*/
$(".stadium-image").click(function(){
    alert("안눌리냐?");
    $("#stadiumImageUrl").click();
});


/*이미지 등록*/
$("#stadiumImageUrl").change(function(e){

const fileReader = new FileReader();
fileReader.readAsDataURL(e.target.files[0]);
fileReader.onload = function(){
    $("#football-stadium-img-box").css("display","block");
    $("#football-stadium-img").attr("src", fileReader.result);
}


});

/*풋살 구장 등록*/
$("#write_football_stadium").click(function(){
    let form = $(".football_stadium_form").serializeObject();
    let image =$("#stadiumImageUrl")[0].files[0];

    const formData = new FormData();
        formData.append("stadiumImageUrl",image);
        formData.append("addRequest",new Blob([JSON.stringify(form)], {type:"application/json; charset=utf-8"}));
    console.log(formData);
    console.log(form);

   $.ajax({
        url:"/api/admin/football-stadium",
        type:"POST",
        processData: false,
        contentType: false,
        data:formData,
        beforeSend:function(xhr){
        xhr.setRequestHeader(header,token);
        }
    }).done(res=>{
        console.log(res);
    }).fail(err=>{
        console.log(err);
    })

});