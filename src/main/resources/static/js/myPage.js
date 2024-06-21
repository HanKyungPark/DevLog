

$(document).ready(function() {
  //포스트가져와
  function loadPosts() {
    $.ajax({
      url: '/api/mypage/post',
      method: 'GET',
      success: function(data) {
        let postsHtml = '';

        data.forEach(function(post) {

          const dateString = post.pcreatedAt;

          // Date 객체 생성
          const date = new Date(dateString);

          // 날짜 관련 정보 추출
          const year = date.getFullYear(); // 연도
          const month = date.getMonth() + 1; // 월 (0부터 시작하므로 +1 필요)
          const day = date.getDate(); // 일

          // 원하는 형식으로 날짜 문자열 생성
          const formattedDate = `${year}년 ${month < 10 ? '0' + month : month}월 ${day < 10 ? '0' + day : day}일`;


          postsHtml += `
                    <a class="blogpost_first" id="blogpost_third" href="${post.postUrl}">
                        <img
                            src="${post.file}"
                            width="80"
                            height="80"
                            alt="Blog post thumbnail"
                            class="post_img"
                            id="post_img_third"
                            style="aspect-ratio: 80 / 80; object-fit: cover;"
                        />
                        <div class="firstpost_container" id="third_container">
                            <h3 class="firstpost_title" id="thirdpost_title">
                            ${formattedDate}
                            </h3>
                            <p class="firstpost_content" id="third_content">
                                ${post.title}
                            </p>
                        </div>
                    </a>`;

        });


        $('#blogpost_inside').html(postsHtml);

      },

      error: function(error) {
        console.error('Error fetching posts', error);
      }
    });
  }

  //댓글
  function loadComments() {
    $.ajax({
      url: '/api/mypage/comment',
      method: 'GET',
      //그러고보니 상세에서 댓글 생성하는 걸 안했다 ^^;;
      success: function(data) {
        console.log(data)

        let commentsHtml = '';
        if(data.comments.length == 0){
          commentsHtml = `<h1>댓글이 없습니다.</h1>`
        } else {
          data.comments.forEach(function(comment) {
            commentsHtml +=
                `<div className="comments_frame" id="comments_frame3">

            <span className="user_img_frame" id="user_img_frame3">
              <img className="user_img" id="user_img3" alt="@shadcn" src="https://minio.bmops.kro.kr/devlog/"+${comment.file} />
            </span>
            <div className="comments_allcontents" id="comments_allcontents3">

              <div className="name_time" id="name_time3">
                <div className="comments_name" id="comments_name3"></div>
                <div className="comments_time" id="comments_time3">${comment.ccreatedAt}</div>
              </div>
              <p className="comments_content" id="comments_content3">
                ${comment.ccontent}
              </p>
            </div>
          </div>`
          })
        };

        $('#comments_box').html(commentsHtml);
      },
      error: function(error) {
        console.error('Error fetching comments', error);
      }
    });
  }

  function loadVisitCount(){
    $.ajax({
      url: '/api/mypage/visits',
      method: 'GET',
      //그러고보니 상세에서 댓글 생성하는 걸 안했다 ^^;;
      success: function(data) {
        console.log(data)
        let visitsHtml = '';

        if(data.visitCount == null){
          visitsHtml = `<h3>0</h3>`
        } else {
          visitsHtml += `
                    <div class="count_frame">
                        <div class="count">${data.visitCount}</div>
                    </div>`;
        }
        $('#visit_box').html(visitsHtml);
      },
      error: function(error) {
        console.error('Error fetching comments', error);
      }
    });
  };


  // 포스트 목록 로드
  loadPosts()

  // 댓글 목록 로드
  loadComments();

  //방문자수 로드
  loadVisitCount();

});

