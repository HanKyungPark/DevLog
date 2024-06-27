

//유저정보
function loadUserInformation(){
    $.ajax({
        url: '/api/new-blog/userdata',
        method: 'GET',
        //댓글 릴스트 요청
        success: function(data) {
            console.log(data);

            data.forEach(function(newBlogData) {
                let userHtml = '';
                //유저 계정
                userHtml += `
                <div class="user-block">
           
                <img src="${newBlogData.account.file}" style="width: 180px;min-width: 180px;height: 180px; border-radius: 100px; margin-right: 30px;border:2px solid black">
                <div class="user-total" style="width: 200px;margin-right: 80px">
                <div> 
                <a style="color: black" href="${newBlogData.account.homepage}"><div class="blog_home" style="width: 200px"><button style="  font-size: 15px;
    border-radius: 30px ;
    width: 60px;
    height: 30px;
    background-color: #5dd2fc;
    float: right;
    margin-right: 30px;
    cursor: pointer;"><b>블로그</b><input type="hidden" value="${newBlogData.account.homepage}" class="homepage"></button></div></a></div>
               <div> <button class="follow-button" style="    font-size: 15px;
    border-radius: 30px ;
    width: 60px;
    height: 30px;
    background-color: #90f5dd;
    margin-right: 10px;
    margin-left: 40px;
    cursor: pointer;"><b class="follow-p">팔로우</b></button></div>
                <div><p class="blink" style="color: red;font-size: 15px">New!&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p></div>
                <div>${newBlogData.account.blogId}</div>
                <div>${newBlogData.account.biography}</div>
                <div>${newBlogData.account.acreatedAt.substring(0,10)}</div>
                </div>
               
                <div class="postbox">
                <div class="user-posts">
                `
                //유저 post들
                newBlogData.posts.forEach(function (post) {
                    userHtml += `
                        <div class="post-container">
                             <input type="hidden" value="${post.postUrl}" class="postUrl">
                        <input type="hidden" value="${newBlogData.account.accountId}" class="accountId">
                        <input type="hidden" value="${newBlogData.account.homepage}" class="homepage">
                        <div class="user-posts"><img class="posted_img" src="${post.file}" style="width: 130px;height: 130px; border: 1px solid #b8d6d2;border-radius: 20px"></div>
                        <div style="height: 20px; font-size: 15px;width: 100px; overflow: hidden;
text-overflow: ellipsis;
white-space: nowrap;"><b>${post.title}</b></div>
                        </div>
                    `
                });
                userHtml +=`</div></div></div></div><hr>`;


                $('.just_test').append(userHtml);
            });


            //클릭시 해당 게시물로 이동
            $(".post-container").click(function(){

                let postUrl = $(this).find(".postUrl").val();
                let homepage = $(this).find(".homepage").val();
                let accountId = $(this).find(".accountId").val();

                location.href = '/' + homepage + "/" + postUrl + "/detail";
            });

            // 버튼 클릭 이벤트 핸들러 설정
            $('.just_test').on('click', '.follow-button', function() {
                const button = $(this);
                if (button.text().trim() === '팔로우') {
                    button.css('background-color', '#adb8b6'); // 색상 변경
                    button.html('<b>팔로잉</b>'); // 텍스트 변경
                } else {
                    button.css('background-color', '#90f5dd'); // 색상 원래대로
                    button.html('<b>팔로우</b>'); // 텍스트 원래대로
                }
            });
        },

        error: function(error) {
            console.error('Error fetching comments', error);
        }
    });
}
//게시글 목록
function loadPosts(){

}

$(document).ready(function() {
    loadUserInformation();
});
console.log(1);