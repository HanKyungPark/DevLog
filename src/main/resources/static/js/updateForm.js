let currentUrl = window.location.href;
let postUrl =  currentUrl.substring(currentUrl.lastIndexOf('/') + 1);
console.log(postUrl);

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


      let titleHtml = `${title}`;
      let contentHtml = `${content}`;
      let fileHtml = `<img src="${file}">`;
      let categoryHtml = '';
      let tagHtml = '';
      console.log(contentHtml);
      //태그
      data.tags.forEach(function(tag){
        tagHtml += `<div>${tag}</div>`;
      })

      $(".input_title_text_box").val(titleHtml);
      // $('.ck-restricted-editing_mode_standard').html(contentHtml);
      CKEDITOR.instances.editor.setData(contentHtml)

    },
    error: function(error) {
      console.error('Error fetching posts', error);
    }
  });
}
$(document).ready(function() {

    loadPost();

  })
