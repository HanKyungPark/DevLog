$(function () {

    let postUrl = "";
    let pathname = window.location.pathname.split("/")[1];
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



    // AJAX 요청
    $.ajax({
        url: "/api/post/myblog/list",
        type: "POST",
        data: {"homepage": pathname},
        dataType: "json",
        success: function(data) {

            let postElement = '<div class="post_container">'; // 포스트 컨테이너 시작

            // 각 포스트 데이터를 HTML로 변환하여 추가
            data.forEach(function(post, idx) {
                postUrl = post.postUrl;
                postElement += `
                <div class="blogpost_box" data-v0-t="card" id="blogpost_box${idx+1}">
                    <a href="#">
                        <img
                            src="https://minio.bmops.kro.kr/devlog/${post.file}"
                            alt="Featured Post"
                            class="blog_photo"
                        />
                    </a>
                    <div class="post_content">
                        <div class="post_header">
                            <div class="category_inpost">${post.category}</div>
                            <div class="icon-container">
                                &nbsp;&nbsp;<i class="bi bi-chat-dots">2</i>
                                &nbsp;<i class="bi bi-heart"></i>
                            </div>
                        </div>
                        <a class="post_title_container" href="#">
                            <h2 class="post_title">${post.title}</h2>
                        </a>
                        <!--<p class="text-gray-600">${post.pcontent}</p> -->
                        <div class="post_date">${formatDate(post.pcreatedAt)}</div> <!-- formatDate 함수를 적용 -->
                    </div>
                </div>`;
            });

            postElement += '</div>'; // 포스트 컨테이너 닫기
            $(".post_container").append(postElement); // HTML 추가

            // 추가한 포스트에 CSS 적용
            $(".blogpost_box").each(function() {
                $(this).addClass("blogpost_box"); // 클래스 추가
            });
        }
    });
    $(document).on("click", ".blogpost_box", function() {

        let homepage = pathname;

        location.href = '/' + homepage + "/" + postUrl + "/detail";
    });
});

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
