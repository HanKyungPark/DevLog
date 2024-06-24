

//유저정보
function loadUserInformation(){
    $.ajax({
        url: '/api/new-blog/userdata',
        method: 'GET',
        //댓글 릴스트 요청
        success: function(data) {
            console.log(data);

            data.forEach(function(newBlogData){
                let userHtml = '';
                //유저 계정
                userHtml += `
                <div>${newBlogData.account.acreatedAt}</div>
                <div>${newBlogData.account.biography}</div>
                <img src="${newBlogData.account.file}" >
                <div>${newBlogData.blogId}</div>
                `
                //유저 post들
                newBlogData.posts.forEach(function (post){
                      console.log(post);
                      userHtml += `
                        <div>${post.title}</div>
                        <img src="${post.file}" >
                    `
                })

                $('.just_test').html(userHtml);
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
    loadUserInformation();
});
console.log(1);