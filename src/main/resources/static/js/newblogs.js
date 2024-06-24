


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
           
                <img src="${newBlogData.account.file}" style="width: 150px;height: 150px; border-radius: 50px; margin-right: 30px;border: 1px solid black">
                <div class="user-total" style="width: 200px">
                <div><p class="blink" style="color: red;font-size: 15px">New!</p></div>
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
                        <div><p style="height: 10px">${post.title}</p></div>
                        <div class="user-posts"><img src="${post.file}" style="width: 130px;height: 130px; border: 1px solid #b8d6d2"></div>
                        </div>
                    `
                });
                userHtml +=`</div></div></div></div><hr>`;


                $('.just_test').append(userHtml);
            })
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
    $('.follow-btn').click(function() {
        $(this).toggleClass('active');
    });
});
