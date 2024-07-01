$(document).ready(function () {
    // file click event를 div에 넓게 줌
    $(".thumbnail_container").off('click').on('click', function () {
        $('#file').click(); // #file input 요소를 클릭
    });

    // 사진 미리보기
    $("#file").off('change').on('change', function () {
        let reg = /(.*?)\/(jpg|jpeg|png|gif)$/;
        let f = this.files[0];
        if (!f.type.match(reg)) {
            alert("이미지 파일만 가능합니다");
            return;
        }
        if (f) {
            let reader = new FileReader();
            reader.onload = function (e) {
                $("#showimg1").attr("src", e.target.result);
            }
            reader.readAsDataURL(f);
        }
    });

    // category data 받아오기
    function fetchCategories() {
        $.ajax({
            url: '/postingForm',
            method: 'GET',
            dataType: 'json',
            success: function (data) {
                let categorySelect = $('#category');
                let s = ""; // 변수 초기화
                console.log(data.length);
                if (data.code === 200) {
                    $.each(data.categories, function (index, category) {
                        s += `<option value="${category.categoryType}">${category.categoryType}</option>`;
                    });
                } else if (data.code === 400) {
                    s += "<option selected disabled>카테고리가 없습니다.</option>";
                }
                categorySelect.html(s);
            },
            error: function (error) {
                console.error('Error fetching categories:', error);
                alert('카테고리 데이터를 불러오는 중 오류가 발생했습니다.');
            }
        });
    }

    fetchCategories(); // 페이지 로드 시 카테고리 데이터 가져오기

    // 폼 데이터 전송하기
    $(".form").off('submit').on('submit', function (e) {
        e.preventDefault();

        // 파일 입력 요소가 선택되지 않은 경우 경고 메시지 표시
        if ($("#file")[0].files.length === 0) {
            alert("파일을 등록하세요");
            return;
        }

        let formData = new FormData();
        formData.append("postData", new Blob([JSON.stringify({
            title: $("#title").val(),
            pContent: $("#editor").val(),
            openType: $("input[name='open_type']:checked").val(),
            postTags: tag,
            category: $("#category").val()
        })], { type: "application/json" }));

        // 파일 입력 요소가 있는지 확인 후 추가
        if ($("#file")[0].files.length > 0) {
            formData.append("file", $("#file")[0].files[0]);
        }

        console.log("FormData being sent:", formData);

        $.ajax({
            type: "POST",
            url: "/api/post/posting",
            data: formData,
            cache: false,
            processData: false, // 데이터 처리를 하지 않음
            contentType: false, // 기본 폼 데이터로 전송
            success: function (response) {
                location.href = "/home";
            },
            error: function (xhr, status, error) {
                console.error('Error during form submission:', error);
                alert('폼 전송 중 오류가 발생했습니다.');
            }
        });
    });
});

let tag = [];

// 해시 태그 추가, 삭제 로직
function addHashtag() {
    let hashtag = $("#hashtag").val();
    if (hashtag.trim() === "") {
        alert("해시태그를 입력하세요.");
        return;
    }
    let item = getHashtagItem(hashtag);
    $("#hashtagList").append(item);
    $("#hashtag").val('');
    tag.push(hashtag);
}

function removeHashtag(hashtag) {
    $("#" + hashtag).remove();
    let index = tag.indexOf(hashtag);
    if (index > -1) {
        tag.splice(index, 1);
    }
}

function getHashtagItem(hashtag) {
    return `
        <div class="hashtag" id="${hashtag}">
            <span class="hashtag-value">#${hashtag}</span>
            <button type="button" onclick="removeHashtag('${hashtag}')">×</button>
        </div>&nbsp;
    `;
}
