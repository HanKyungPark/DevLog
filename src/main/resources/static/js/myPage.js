

$(document).ready(function() {
  //포스트가져와
  function loadPosts() {
    $.ajax({
      url: '/api/mypage/posts',
      method: 'GET',
      success: function(data) {
        let postsHtml = '';
        console.log(data.length);

        if(data.length === 0) {

          postsHtml = `<h2>게시글이 없습니다</h2>`

        } else {
          data.forEach(function(post) {

            // Date 객체 생성
            const date = new Date(post.pcreatedAt);

            // 날짜 관련 정보 추출
            const year = date.getFullYear(); // 연도
            const month = date.getMonth() + 1; // 월 (0부터 시작하므로 +1 필요)
            const day = date.getDate(); // 일

            // 원하는 형식으로 날짜 문자열 생성
            const formattedDate = `${year}년 ${month < 10 ? '0' + month : month}월 ${day < 10 ? '0' + day : day}일`;

            postsHtml += `
                    <div style="display:flex; align-items: center">
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
                    </a>
                    <div>
                      <div>
                        <button onclick=postUpdate() class="btn btn-primary">수정</button>
                      </div>
                      <div>
                        <button onclick=postDelete() class="btn btn-danger">삭제</button>
                      </div>
                    </div>    
                   </div>        
              `;
          });
        }

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
        // console.log(data)

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
          </div>
            `
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


//게시글 삭제 함수
function postDelete(button){
  let postUrl =  $(button).closest('a').attr('href');
  $.ajax({
        url: "/api/mypage/post/delete",
        type: 'GET',
        contentType: 'application/json', //json타입이라고 말함
        data: JSON.stringify({ url: postUrl }),
        success: function(response) {
          alert('Post updated successfully');
        },
        error: function(xhr, status, error) {
          alert('Failed to update post');
        }
  })
}

// 해당버튼을 누르면
// 해당 포스트의 수정페이지로 이동한다
function postUpdate(button) {
  let postUrl =  $(button).closest('a').attr('href');
  $.ajax({
    url: "/api/mypage/post/delete",  // 서버에서 수정 처리를 할 엔드포인트
    type: 'GET',
    contentType: 'application/json',
    data: JSON.stringify({ url: postUrl }),  // 서버에 전송할 데이터
    success: function(response) {
      alert('Post updated successfully');
    },
    error: function(xhr, status, error) {
      alert('Failed to update post');
    }
  })
}
function commentUpdate(button){
  $.ajax()
}

