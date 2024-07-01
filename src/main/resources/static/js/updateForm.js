let currentUrl = window.location.href;
let postUrl =  currentUrl.substring(currentUrl.lastIndexOf('/') + 1);
let editor;
let contentHtml = '';
let homepage;
let hashTag=[];

function loadPost() {
  $.ajax({
    url: '/api/post/mypage/update',
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify({postUrl: postUrl}),
    success: function(data) {
      console.log("loadPost")
      console.log(data);

      const title = data.post.title;
      const content = data.post.pcontent;
      const file = data.post.file;
      const tags = data.tags
      homepage = data.homepage;

      let titleHtml = `${title}`;
      contentHtml = `${content}`;
      let fileHtml = `${file}`;
      let tagHtml = '';
      if(tags != null){
        tags.forEach(function(tag){
          hashTag.push(tag);
          tagHtml += `
              <div class="hashtag" id="${tag}">
                <span class="hashtag-value">#${tag}</span>
                <button type="button" onclick="removeHashtag('${tag}')">×</button>
              </div>`;
        })
      }


      //제목
      $("#title").val(titleHtml);

      //내용
      editor.setData(contentHtml);

      //태그
      $('#hashtagList').html(tagHtml)

    },
    error: function(error) {
      console.error('Error fetching posts', error);
    }
  });
}

$(document).ready(function () {
  loadPost()
  
});

$(function () {
  // file click event를 div에 넓게 줌
  $(".thumbnail_container").click(function (){
    $('#file').click(); // #file input 요소를 클릭
  });

  // 사진 미리보기
  $("#file").change(function () {
    let reg = /(.*?)\/(jpg|jpeg|png|gif)$/;
    let f = $(this)[0].files[0]
    if (!f.type.match(reg)) {
      return;
    }
    if (f) {
      let reader = new FileReader();
      reader.onload = function (e) {
        $("#showimg1").attr("src", e.target.result);
      }
      reader.readAsDataURL($(this)[0].files[0]);
    }
  })
  // category data 받아오기
  $.ajax({
    url: '/postingForm',
    method: 'GET',
    dataType: 'json',
    success: function (data) {
      let categorySelect = $('#category');
      let s = ""; // 변수 초기화

      if (data.code === 200) {
        $.each(data.categories, function (index, category) {
          s += `<option value="${category.categoryType}">${category.categoryType}</option>`;
        });
      } else if (data.code === 400) {
        s += "<option selected disabled>카테고리가 없습니다.</option>";
      }
      categorySelect.append(s);
    },
    error: function (error) {
      console.error('Error fetching categories:', error);
    }
  });

  // 폼 데이터 전송하기
  $(".form").submit(function (e) {
    e.preventDefault();
    let formData = new FormData();

    formData.append("updateData", new Blob([JSON.stringify({
      title: $("#title").val(),
      pContent: $("#editor").val(),
      openType: $("input[name='open_type']:checked").val(),
      updateTags: hashTag,
      category: $("#category").val(),
      postUrl: postUrl,
    })], {type: "application/json"}));

    // 파일 입력 요소가 있는지 확인
    if ($("#file")[0].files.length > 0) {
      formData.append("file", $("#file")[0].files[0]);
    }

    console.log("FormData being sent:", formData);

    $.ajax({
      type: "POST",
      url: "/api/post/mypage/update-post",
      data: formData,
      cache: false,
      processData: false, // 데이터 처리를 하지 않음
      contentType: false, // 기본 폼 데이터로 전송
      success: function (response) {
        location.href = "/"+homepage+"/"+postUrl+"/"+"detail";
      },
      error: function (xhr, status, error) {
        location.href = "/"+homepage+"/"+postUrl+"/"+"detail";
      }
    });
  });
});

// 해시 태그 추가, 삭제 로직
function addHashtag() {
  let hashtag = $("#hashtag").val();
  let item = getHashtagItem(hashtag);
  $("#hashtagList").append(item);
  $("#hashtag").val('');
  hashTag.push(hashtag);
}

function removeHashtag(hashtag) {
  document.getElementById(hashtag).remove();
  let temp = [];
  hashTag.forEach(function(tag){
    if(tag !== hashtag){
      temp.push(tag)
    }
  })
  hashTag = temp;
  console.log(hashTag);
}

function getHashtagItem(hashtag) {
  let item =
      `<div class="hashtag" id="${hashtag}">
			<span class="hashtag-value">#${hashtag}</span>
			<button type="button" onclick="removeHashtag('${hashtag}')">×</button>
		</div>`

  return item;
}
