$(function () {

    $(".b").click(function () {
        location.href=window.location.pathname;
    })
    let accountId = 0;
    let pathname = window.location.pathname.split("/")[1];
    let postElement = '<div class="post" style="margin-top: -10px;min-height: 350px; background-size: cover;border-radius: 20px">'; // 포스트 컨테이너 시작

    //url 따와서 인스타처럼 윗부분 만들기
    $.ajax({
        url: "/api/user/info",
        type: "post",
        data: {"homepage": pathname},
        success: function (list) {
            $("#profile").attr("src", list.file);
            $("#uname").text(list.blogId);
            $("#uhomepage").text(list.homepage);
            $("#ubiography").text(list.biography);

            accountId = list.accountId;
        }
    });
    console.log(accountId);
    //해당 카테고리에 해당하는 카테고리들 불러오고 갯수 구하기
    $(document).ready(function() {
        $("#all").css("background-color", "#b4d4d6");
    }); //페이지 로드시 all카테고리 색상변경
    $.ajax({
        url: "/api/mypage/categorie",
        data:{"homepage":pathname},
        type: "post",
        success: function (data) {
            console.log(data);
            data.forEach(function (category, idx) {
                let name = "category" + (idx + 1);
                //갯수 구한만큼 div 보이게 하고 해당 카테고리 넣고 누르면 value 구하게 하기
                $("#" + name).css("display", "");
                $("#" + name).text(category.categoryType);
                $("#" + name).attr("value", category.categoryId);
                // 클릭 이벤트 추가 및 배경색 변경
                $("#" + name).click(function() {
                    // 클릭된 버튼
                    const clickedButton = $(this);

                    // 다른 버튼들의 배경색 원래대로 돌리기
                    $(this).siblings().css("background-color", "");

                    // 클릭된 버튼 배경색 파란색으로 변경 및 selected 클래스 추가
                    clickedButton.css("background-color", "#b4d4d6").addClass("selected");
                    if(clickedButton.attr("id") !== "all") {
                        $("#all").css("background-color", "");
                    }
                });
            })
        }
    })
    let posttotal = 0; // ��� 포스트 ��수
    // AJAX 요청
    //존재하는 블로그인지 확인하기
    $.ajax({
        url: "api/blog/check",
        type: "post",
        data: {"pathName": pathname},
        dataType: "json",
        success: function (data) {
            if (!data) {
                alert("존재하지 않는 블로그입니다.");
                location.href = "/";
            }
        }
    });
// 해당 블로그의 모든 목록 가져오기
    // AJAX 요청
    $.ajax({
        url: "/api/post/myblog/list",
        type: "POST",
        data: {"homepage": pathname},
        dataType: "json",
        success: function (data) {
            console.log(data);
            //if (data.length == 0) {
            // 각 포스트 데이터를 HTML로 변환하여 추가
            data.forEach(function (post, idx) {
                posttotal = data.length; // 전체 포스트 ��수
                accountId = post.accountId

                $("#utotalpost").text(posttotal);
                postElement += `

                <div class="blogpost_box" data-post-url="${post.post_url}" data-v0-t="card" id="blogpost_box${idx + 1}">
                    <a href="#">
                        <img
                            src="${post.file}"
                            alt="Featured Post"
                            class="blog_photo"
                        />
                    </a>
                    <div class="post_content" id="updatecontainer">
                        <div class="post_header">
                            <div class="category_inpost">${post.category_type}</div>
                            <div class="icon-container">
                                &nbsp;&nbsp;<i class="bi bi-chat-dots">${post.comment_count}</i>
                                &nbsp;<i class="bi bi-heart"></i>
                            </div>
                        </div>
                        <a class="post_title_container" href="#">
                            <h2 class="post_title">${post.title}</h2>
                        </a>
                        <!--<p class="text-gray-600">${post.p_content}</p> -->
                        <div class="post_date">${formatDate(post.p_created_at)}</div> <!-- formatDate 함수를 적용 -->
                    </div>
                </div>`;
            });
            postElement += '</div>'; // 포스트 컨테이너 닫기
            // $(".post").append("</div>"); // 컨��이�� 보이게 하
            $(".post").append(postElement); // HTML 추가

            // 추가한 포스트에 CSS 적용
            $(".blogpost_box").each(function () {
                $(this).addClass("blogpost_box"); // 클래스 추가
            })

        }
        //}
    });

    $.ajax({
        url:"/api/post/view",
        type:"post",
        data:{"homepage":pathname},
        success:function (data) {
            $("#uviews").text(data);
        }
    })

    $(".a").click(function () {
        let categoryId = $(this).attr("value");
        accountId = pathByAccountId(pathname);
        $.ajax({
            url: "/api/search/category",
            data: {"categoryId": categoryId,"accountId":accountId},
            type: "post",
            success: function (posts) {
                $(".post").find(".post").empty(); // 컨테이너 초기화
                console.log(posts);
                posts.forEach(function (post, idx) {
                    let postElement = `
                        <div class="blogpost_box" data-post-url="${post.post_url}" data-v0-t="card" id="blogpost_box${idx + 1}">
                            <a href="#">
                                <img
                                    src="${post.file}"
                                    alt="Featured Post"
                                    class="blog_photo"
                                />
                            </a>
                            <div class="post_content">
                                <div class="post_header">
                                    <div class="category_inpost">${post.category_type}</div>
                             
                                    <div class="icon-container">
                                        &nbsp;&nbsp;<i class="bi bi-chat-dots">${post.comment_count}</i>
                                        &nbsp;<i class="bi bi-heart"></i>
                                    </div>
                                </div>
                                <a class="post_title_container" href="#">
                                    <h2 class="post_title">${post.title}</h2>
                                </a>
                                <!--<p class="text-gray-600">${post.p_content}</p> -->
                                <div class="post_date">${formatDate(post.p_created_at)}</div> <!-- formatDate 함수를 적용 -->
                            </div>
                        </div>`;

                    $(".post").find(".post").append(postElement); // HTML 추가
                });
            }
        });
    });

});
$(document).on("click", ".blogpost_box", function () {
    let homepage = window.location.pathname.split("/")[1];
    let postUrl = $(this).data("post-url"); // 클릭한 요소의 data-post-url 속성 값 가져오기

    location.href = '/' + homepage + "/" + postUrl + "/detail";});



function pathByAccountId(pathname){
    console.log(pathname)
    $.ajax({
        url: "/api/user/info",
        type: "post",
        data: {"homepage": pathname},
        success: function (list) {
            accountId = list.accountId;
            return accountId
        }
    });
    return accountId;
}


// 날짜 포맷 변환 함수
function formatDate(dateString) {
    const date = new Date(dateString);
    const year = date.getFullYear();
    let month = date.getMonth() + 1;
    if (month < 10) {
        month = '0' + month;
    }
    let day = date.getDate();
    if (day < 10) {
        day = '0' + day;
    }
    let hours = date.getHours();
    if (hours < 10) {
        hours = '0' + hours;
    }
    let minutes = date.getMinutes();
    if (minutes < 10) {
        minutes = '0' + minutes;
    }
    return `${year}-${month}-${day} ${hours}:${minutes}`;
}


