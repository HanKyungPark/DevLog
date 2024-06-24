$(function () {
    let pathname = window.location.pathname.split("/")[1];
    $.ajax({
        url: "api/blog/check", type: "post", data: {"pathName": pathname}, dataType: "json", success: function (data) {
            if (!data) {
                alert("존재하지 않는 블로그입니다.");
                location.href = "/"
            }
        }
    })

    $(document).ready(function() {
        let pathname = window.location.pathname.split("/")[1];

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
                      <div class="post_date">${post.pcreatedAt}</div> <!-- pcreatedAt 요소를 하단으로 이동 -->
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
    });

})