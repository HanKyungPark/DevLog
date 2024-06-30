$(function () {
    $.ajax({
        url: '/api/account/check',
        type: 'GET',
        success: function (response) {
            if(response == null){
                alert("로그인 해 주세요");
                location.href="/";
            }
        },
        error: function (error) {
            alert("로그인 해 주세요");
            location.href="/";
            console.log(error);
        }
    })

    $("#myBlog").click(function (e) {
        e.preventDefault();
        $.ajax({
            url: '/api/account/check',
            type: 'GET',
            success: function (response) {
                if(response == null){
                    alert("계정이 없습니다.\n 로그인 해주세요");
                    location.href = "/";
                }
                location.href = "/" + response;
            },
            error: function (error) {
                console.log(error);
            }
        });
    })
});