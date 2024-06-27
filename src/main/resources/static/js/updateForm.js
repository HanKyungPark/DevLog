let currentUrl = window.location.href;
let postUrl =  currentUrl.substring(currentUrl.lastIndexOf('/') + 1);
console.log(postUrl);

let contentHtml = '';


// "ck-restricted-editing_mode_standard ck ck-content ck-editor__editable ck-rounded-corners ck-editor__editable_inline ck-blurred"
function loadPost() {
  $.ajax({
    url: 'http://localhost:8080/api/post/mypage/update',
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify({postUrl: postUrl}),
    success: function(data) {
      console.log(data);

      const title = data.post.title;
      const content = data.post.pcontent;
      const categoryType = data.categoryType;
      const openTyp = data.post.openType;
      const file = data.post.file;
      const tags = data.tags

      let titleHtml = `${title}`;
      contentHtml = `${content}`;
      let fileHtml = `${file}`;
      let categoryHtml = '';
      let tagHtml = '';
      tags.forEach(function(tag){
        tagHtml += `<div class="hashtag" id="${tag}">
			<span class="hashtag-value">${tag}</span>
			<button type="button" onclick="removeHashtag('${tag}')">×</button>
		      </div>`;
      })

      //제목
      $("#title").val(titleHtml);

      //카테고리
      $("#category").html(categoryHtml)

      //태그
      $('#hashtagList').html(tagHtml)

      //사진
      $('#showimg1').attr('src', fileHtml)


    },
    error: function(error) {
      console.error('Error fetching posts', error);
    }
  });
}
$(document).ready(function() {

    loadPost();

  })
