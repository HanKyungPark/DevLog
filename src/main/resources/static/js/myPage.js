let postCurrentPage = 0;
const postPageSize = 5;
let posts = [];
let commentCurrentPage = 0;
const commentPageSize = 8;
let comments = [];
let file;

//게시글 저장하기
function loadPosts() {
  $.ajax({
    url: '/api/mypage/posts',
    method: 'GET',
    success: function(data) {
      posts = data;
      displayPosts()
      postToggleButtons()
    }, error: function(error) {
      console.error('Error fetching posts', error);
    }
  });
}

//게시글 보여주기
function displayPosts() {
  const start = postCurrentPage * postPageSize;
  const end = start + postPageSize;
  const pagePosts = posts.slice(start, end);

  let postsHtml = '';

  if(posts == null){

    postsHtml = `<h2>게시글이 없습니다.</h2>`

  } else {

    $('#blogpost_inside').empty();

    pagePosts.forEach(function(post) {
      // Date 객체 생성
      const date = new Date(post.pcreatedAt);

      // 날짜 관련 정보 추출
      const year = date.getFullYear(); // 연도
      const month = date.getMonth() + 1; // 월 (0부터 시작하므로 +1 필요)
      const day = date.getDate(); // 일

      // 원하는 형식으로 날짜 문자열 생성
      const formattedDate = `${year}년 ${month < 10 ? '0' + month : month}월 ${day < 10 ? '0' + day : day}일`;

      postsHtml += `
                    <div class="blog_post_box" style="display:flex; align-items: center; justify-content: space-between">
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
                    <div >
                      <div>
                        <a  href="/mypage/post/update/${post.postUrl}" class="btn btn-primary">수정</a>
                      </div>
                      <div>
                        <button onclick="postDelete(this)" class="btn btn-danger">삭제</button>
                      </div>
                    </div>    
                   </div>        
              `;
    });

    $('#blogpost_inside').html(postsHtml);

  }

}

//게시글 삭제
function postDelete(button){
  let postUrl = $(button).closest('.blog_post_box').find('a.blogpost_first').attr('href');

  $.ajax({
    url: "/api/mypage/post/delete",
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify({ postUrl: postUrl }),
    success: function(response) {
      displayPosts()
    },
    error: function(xhr, status, error) {
      alert('Failed to delete post');
    }
  })
}

//게시글 이전페이지 이동
function postPrevPage() {
  if (postCurrentPage > 0) {
    postCurrentPage--;
    displayPosts();
    postToggleButtons();
  }
}

//게시글 다음 페이지 이동
function postNextPage() {
  if ((postCurrentPage + 1) * postPageSize < posts.length) {
    postCurrentPage++;
    displayPosts();
    postToggleButtons();
  }
}

//게시글 버튼 유무
function postToggleButtons() {
  $('#prev-btn').css('display', postCurrentPage <= 0 ? 'none' : 'block');
  $('#next-btn').css('display', (postCurrentPage + 1) * postPageSize >= posts.length ? 'none' : 'block');
  // $('#prev-btn').prop('disabled', postCurrentPage <= 0);
  // $('#next-btn').prop('disabled', (postCurrentPage + 1) * postPageSize >= posts.length);
}


//댓글 저장하기
function loadComments() {
  $.ajax({
    url: '/api/mypage/comment',
    method: 'GET',
    //댓글 리스트 요청
    success: function(data) {
      let commentsHtml = '';
      file = data.file;
      comments = data.comments;

      console.log(comments);
      console.log(file);

      displayComments()
      commentToggleButtons()

    },
    error: function(error) {
      console.error('Error fetching comments', error);
    }
  });
}

//댓글 보여주기
function displayComments() {

  const start = commentCurrentPage * commentPageSize;
  const end = start + commentPageSize;
  const pageComments = comments.slice(start, end);


  let commentsHtml = '';

  $('#comments_box').empty();

  if(comments == 0){

    commentsHtml = `<h1>댓글이 없습니다.</h1>`

  } else {

    pageComments.forEach(function(comment) {

      const date = new Date(comment.ccreatedAt);

      // 날짜 관련 정보 추출
      const year = date.getFullYear(); // 연도
      const month = date.getMonth() + 1; // 월 (0부터 시작하므로 +1 필요)
      const day = date.getDate(); // 일

      // 원하는 형식으로 날짜 문자열 생성
      const formattedDate = `${year}년 ${month < 10 ? '0' + month : month}월 ${day < 10 ? '0' + day : day}일`;

      commentsHtml +=
          `<div className="comments_frame" id="comments_frame3" style="display: flex; align-items: center; justify-content: space-between">
                    
                  <span className="user_img_frame" id="user_img_frame3">
                    <img className="user_img" 
                          id="user_img3"
                          src="${file}"
                          alt="user_image"
                          width="50" height="50"
                          style="border-radius: 50%;"
                          />
                  </span>
            <div className="comments_allcontents" id="comments_allcontents3">

              <div className="name_time" id="name_time3">
                <div className="comments_name" id="comments_name3"></div>
                <div className="comments_time" id="comments_time3">${formattedDate}</div>
              </div>
              <p className="comments_content" id="comments_content3">
                ${comment.ccontent}
              </p>
            </div>
            <div>
                <div>
                  <a  href="해당글의 상세페이지로 이동" class="btn btn-primary">수정</a>
                </div>
                <div>
                  <button onclick="commentDelete(this)" class="btn btn-danger">
                  삭제
                  <p id="comment_id" style="display: none">${comment.commentId}</p>
                  </button>
                </div>
              </div>
          </div>
            `;

      $("#comments_box").html(commentsHtml);

    })
  };
  $('#comments_box').html(commentsHtml);

}

//댓글삭제
function commentDelete(button){
  let commentId = $(button).find("#comment_id").text();

  $.ajax({
    url: "/api/mypage/comment/delete",
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify({ commentId: commentId }),
    success: function(response) {
      displayComments();
    },
    error: function(xhr, status, error) {
      alert('Failed to delete comment');
    }
  })
}

//댓글 이전페이지 이동
function commentNextPage() {
  if ((commentCurrentPage + 1) * commentPageSize < comments.length) {
    commentCurrentPage++;
    displayComments();
    commentToggleButtons();
  }
}

//댓글 다음 페이지 이동
function commentNextPage() {
  if ((commentCurrentPage + 1) * commentPageSize < comments.length) {
    commentCurrentPage++;
    displayComments();
    commentToggleButtons();
  }
}

//댓글 버튼 유무
function commentToggleButtons() {
  $('#comment-prev-btn').css('display', commentCurrentPage <= 0 ? 'none' : 'block');
  $('#comment-next-btn').css('display', (commentCurrentPage + 1) * commentPageSize >= comments.length ? 'none' : 'block');
}

//조회수 가져오기
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

//페이지 로드 되면
$(document).ready(function() {

  // 포스트 목록 로드
  loadPosts()
  console.log("포스트는 끝")

  // 댓글 목록 로드
  loadComments();
  console.log("댓글 끝")

  //방문자수 로드
  loadVisitCount();
  console.log("방문자수 끝")
});

