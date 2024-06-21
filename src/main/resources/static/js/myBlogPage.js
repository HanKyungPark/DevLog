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

    $.ajax({
        url: "api/post/list",
        type: "post",
        data: {"homepage": pathname},
        dataType: "json",
        success: function (data) {
            console.log(data);

            let postElement='';
            postElement += '<div class style="display: flex; justify-content: flex-end">';
            data.forEach(function (post,idx) {
                console.log(post)
                postElement += `
                <div class="post_container${idx+1}">
                <div class="blogpost_box" data-v0-t="card" id="blogpost_box${idx+1}">
                <a href="#">
                <img
                        src="https://minio.bmops.kro.kr/devlog/${post.file}"
                        alt="Featured Post"
                        width="300"
                        height="200"
                        class="blog_photo"
                        style="aspect-ratio: 300 / 200; object-fit: cover;"
                />
                </a>
                <div class="post_content">
                    <div class="post_header">
                        <div class="category_inpost">
                            Design
                        </div>
                        <div class="post_date">${post.pcreatedAt}</div>
                        <div class="icon-container">
                            &nbsp;&nbsp;<i class="bi bi-chat-dots">2</i>
                            &nbsp;<i class="bi bi-heart"></i>
                        </div>
                    </div>
                    <a class="post_title_container" href="#">
                        <h2 class="post_title">
                            ${post.title}
                        </h2>
                    </a>
                    <p class="text-gray-600 dark:text-gray-400 line-clamp-3">
                        ${post.pcontent}
                    </p>
                    </div>
                    </div>
                `;
            })
            postElement += `</div>`
            $(".post_container").append(postElement);

        }
    })
})