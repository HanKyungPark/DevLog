$(function (){
    let pathname = window.location.pathname.split("/")[1];
    $.ajax({
        url:"api/blog/check",
        type: "post",
        data:{"pathName":pathname},
        dataType:"json",
        success:function (data){
            if(!data){
                alert("존재하지 않는 블로그입니다.");
                location.href="/"
            }
        }
    })
})