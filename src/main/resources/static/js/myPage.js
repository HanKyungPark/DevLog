let postCurrentPage = 0;
const postPageSize = 5;
let posts = [];
let commentCurrentPage = 0;
const commentPageSize = 6;
let comments = [];
let file;
let homepage;
let categories = [];

//게시글 저장하기
function loadPosts() {
  $.ajax({
    url: '/api/mypage/posts',
    method: 'GET',
    success: function (data) {
      posts = data.posts;
      homepage = data.homepage;
      displayPosts()
      postToggleButtons()
    }, error: function (error) {
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

  if (posts == null) {

    postsHtml = `<h2>게시글이 없습니다.</h2>`

  } else {

    $('#blogpost_inside').empty();

    pagePosts.forEach(function (post) {
      // Date 객체 생성
      const date = new Date(post.pcreatedAt);

      // 날짜 관련 정보 추출
      const year = date.getFullYear(); // 연도
      const month = date.getMonth() + 1; // 월 (0부터 시작하므로 +1 필요)
      const day = date.getDate(); // 일

      // 원하는 형식으로 날짜 문자열 생성
      const formattedDate = `${year}년 ${month < 10 ? '0' + month : month}월 ${day
      < 10 ? '0' + day : day}일`;

      postsHtml += `
                    <div class="blog_post_box" style="display:flex; align-items: center; justify-content: space-between">
                    <a class="blogpost_first" id="blogpost_third" href="/hompage/${post.postUrl}/detail">
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
                            <p class="firstpost_content" id="third_content" style="width: 120px; overflow: hidden;
text-overflow: ellipsis;
white-space: nowrap;">
                                ${post.title}
                            </p>
                        </div>
                    </a>
                    <div >
                      <div>
                        <a  href="/mypage/post/update/${post.postUrl}" class="btn" style="background-color: #bfd6d2;color: white">수정</a>
                      </div>
                      <div>
                        <button onclick="confirmPostDelete(this)" class="btn btn" style="margin-top: 5px;background-color: #6f7574;color: white">
                        삭제
                        <p id="post_id" style="display: none">${post.postId}</p>
                        </button>
                      </div>
                    </div>    
                   </div>        
              `;
    });

    $('#blogpost_inside').html(postsHtml);
  }

}

//게시글 삭제
function postDelete(button) {

  let postId = $(button).find("#post_id").text();

  $.ajax({
    url: "/api/mypage/post/delete",
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify({postId: postId}),
    success: function (response) {
      loadPosts()
    },
    error: function (xhr, status, error) {
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


//댓글 저장하기
function loadComments() {
  $.ajax({
    url: '/api/mypage/comment',
    method: 'GET',
    //댓글 리스트 요청
    success: function (data) {

      comments = data;

      displayComments()
      commentToggleButtons()
    },
    error: function (error) {
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

  if (comments == 0) {

    commentsHtml = `<h1>댓글이 없습니다.</h1>`

  } else {

    pageComments.forEach(function (data) {

      const date = new Date(data.comment.ccreatedAt);

      // 날짜 관련 정보 추출
      const year = date.getFullYear(); // 연도
      const month = date.getMonth() + 1; // 월 (0부터 시작하므로 +1 필요)
      const day = date.getDate(); // 일

      // 원하는 형식으로 날짜 문자열 생성
      const formattedDate = `${year}년 ${month < 10 ? '0' + month : month}월 ${day
      < 10 ? '0' + day : day}일`;

      commentsHtml +=
          `<div className="comments_frame" id="comments_frame3" style="display: flex; align-items: center; justify-content: space-between;margin-bottom: 5px">
                    
                  <span className="user_img_frame" id="user_img_frame3">
                    <img className="user_img" 
                          id="user_img3"
                          src="${data.file}"
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
              <div style="width: 150px; overflow: hidden;
text-overflow: ellipsis;
white-space: nowrap;">
              <span style="width: 100px !important;height: auto!important;" className="comments_content" id="comments_content3">
                ${data.comment.ccontent}
              </span>
              </div>
            </div>
            <div>
                <div>
                  <a  href="/${data.homepage}/${data.postUrl}/detail" class="btn" style="background-color: #bfd6d2;color: white">수정</a>
                </div>
                <div>
                  <button onclick="confirmCommentDelete(this)" class="btn" style="margin-top: 5px;background-color: #6f7574;color: white"">
                  삭제
                  <p id="comment_id" style="display: none">${data.comment.commentId}</p>
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
function commentDelete(button) {
  let commentId = $(button).find("#comment_id").text();

  $.ajax({
    url: "/api/mypage/comment/delete",
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify({commentId: commentId}),
    success: function (response) {
      loadComments();
    },
    error: function (xhr, status, error) {
      alert('Failed to delete comment');
    }
  })
}

//댓글 이전페이지 이동
function commentPrevPage() {
  if (commentCurrentPage > 0) {
    commentCurrentPage--;
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
//


//조회수 가져오기
function loadVisitCount() {
  $.ajax({
    url: '/api/mypage/visits',
    method: 'GET',
    success: function (data) {
      let visitsHtml = '';

      if (data == null) {
        visitsHtml = `<h4>0</h4>`
      } else {
        visitsHtml += `
                    <div class="count_frame">
                        <div class="count">${data}</div>
                    </div>`;
      }
      $('#visit_box').html(visitsHtml);
    },
    error: function (error) {
      console.error('Error fetching comments', error);
    }
  });
};


//카테고리 추가
function addCategory(button){
  let categoryName = $(button).prev('.category-input').val();
  console.log(categoryName)
  $.ajax({
    url:'/api/mypage/category-post',
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify({categoryName: categoryName}),
    success: function(response) {

      if(response.categoryType == null){
        alert("동일한 카테고리 이름이 존재합니다.")
      } else {
        alert("카테고리 이름이 추가되었습니다.")

      }
      $(button).prev('.category-input').val("");
      loadCategories();
      validationCategories();
    },
    error: function(error) {
      console.error('Error adding category:', error);
    }
  })

}

//카테고리 불러오기
function loadCategories(){

  $.ajax({
    url:'/api/mypage/categories',
    method: 'GET',
    success: function(data){
      categories = data;
      validationCategories();
      displayCategories();
    },
    error: function(){
      console.log("카테고리를 불러오지 못했습니다.")
    }
  })
}

//카테고리 보여주기
function displayCategories(){
  const pageCategories = categories;

  $('#category-container').empty();

  categoryHtml = '';

  pageCategories.forEach(function(category){
    categoryHtml +=`
          <div class="category-name" style="display: flex; justify-content: space-between; ">
                            <div clas="category-type">${category.categoryType}</div>
                            <div>
                                <button class="category-delete" onclick="categoryDelete(this)">
                                    삭제
                                    <div class="category-id" style="display: none;">${category.categoryId}</div>
                                </button>
                                <button class="category-update" onclick="openCategoryUpdate(this)">
                                    수정
                                </button>
                            </div>
                        </div>
                        <form class="category-form" style="display: none; ">
                        <div class="form-group"  style = "display: flex; justify-content: center">
                            <input type="text" class="form-control input-title-text-box category-input" placeholder="Enter a category">
                            <button type="button" onclick="categoryUpdate(this)" class="btn add-category-btn">
                              변경
                            <div class="category-id" style="display: none;">${category.categoryId}</div>
                            </button>
                            <button type="button" onclick="closeCategoryUpdate(this)" class="btn add-category-btn">
                              닫기
                            </button>
                            
                        </div>
                    </form>`
  })

  $('#category-container').html(categoryHtml);
}

//카테고리 크기마다 검증
function validationCategories(){
  if(categories.length >= 5){
    $('#category-add-button').css('display', 'none');
    $('#category-bound-notification').css('display', 'block')
  } else {
    $('#category-add-button').css('display', 'flex');
    $('#category-bound-notification').css('display', 'none')
  }
}

//카테고리삭제
function categoryDelete(button){
  let categoryId = $(button).find(".category-id").text();

  $.ajax({
    url: "/api/mypage/category/delete",
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify({categoryId: categoryId}),
    success: function () {
      loadCategories();
      validationCategories();
      alert('카테고리가 삭제되었습니다');
    }, error: function (xhr, status, error) {
      alert('Failed to delete post');
    }
  })
}


//카테고리 수정
function categoryUpdate(button){
  let categoryId = $(button).find(".category-id").text();
  let categoryForm = $(button).closest('.category-form')
  let categoryType = categoryForm.find(".category-input").val();

  $.ajax({
    url: "/api/mypage/category/update",
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify({
      categoryId: categoryId,
      categoryType : categoryType
    }),
    success: function (response) {

      if(response == '600'){
        alert('동일한 이름이 존재합니다.');
      } else {
        loadCategories()
        categoryForm.css('display', 'none');
        alert('카테고리 이름이 변경되었습니다.');
      }

    }, error: function (xhr, status, error) {
      alert('Failed to delete post');
    }
  })

}

//카테고리 수정폼 보여주기
function openCategoryUpdate(button){
  let categoryForm = $(button).closest('.category-name').next('.category-form');
  categoryForm.css('display', 'flex');
}

//카테고리 수정폼 닫기
function closeCategoryUpdate(button){
  let categoryForm = $(button).closest('.category-form');
  categoryForm.css('display', 'none');
}

// 게시글 삭제 확인 및 삭제
function confirmPostDelete(button) {
  if (confirm("해당 게시글을 삭제 하시겠습니까?")) {
    postDelete(button);
  }
}

// 댓글 삭제 확인 및 삭제
function confirmCommentDelete(button) {
  if (confirm("해당 댓글을 삭제 하시겠습니까?")) {
    commentDelete(button);
  }
}


//페이지 로드
$(document).ready(function () {

  // 포스트 목록 로드
  loadPosts()

  // 댓글 목록 로드
  loadComments();

  //방문자수 로드
  loadVisitCount();

  //카테고리 불러오기
  loadCategories();

});