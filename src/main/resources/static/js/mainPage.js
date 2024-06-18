$(function (){
    if(sessionStorage.getItem("accountId")==null){
        $(".header").css("display","none");
    }
});