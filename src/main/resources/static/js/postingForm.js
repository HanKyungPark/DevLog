$(function () {
    // category data 받아오기
    $.ajax({
        url: '/postingForm',
        method: 'GET',
        dataType: 'json',
        success: function(data) {
            let categorySelect = $('#category');
            let s = ""; // 변수 초기화

            if (data.code === 200) {
                $.each(data.categories, function(index, category) {
                    s += `<option value="${category.categoryType}">${category.categoryType}</option>`;
                });
            } else if (data.code === 400) {
                s += "<option selected disabled>카테고리가 없습니다.</option>";
            }
            categorySelect.append(s);
        },
        error: function(error) {
            console.error('Error fetching categories:', error);
        }
    });

    // 폼 데이터 전송하기
    $(".form").submit(function (e) {
        e.preventDefault();

        if ($("#title").val() === "") {
            alert("제목은 필수 입력사항입니다.");
            return;
        }

        let tags = [];
        let tagArray = $("#tag").val().split("#");

        // 빈 문자열 제거
        tagArray = tagArray.filter(tag => tag.trim() !== "");
        tags.push(...tagArray);

        let formData = new FormData();
        formData.append("postData", new Blob([JSON.stringify({
            title: $("#title").val(),
            pContent: $("#editor").val(),
            openType: $("input[name='open_type']:checked").val(),
            postTags: tags,
            category: $("#category").val()
        })], { type: "application/json" }));

        // 파일 입력 요소가 있는지 확인

            formData.append("file", $("#file")[0].files[0]);


        console.log("FormData being sent:", formData);

        $.ajax({
            type: "POST",
            url: "/api/post/posting",
            data: formData,
            cache: false,
            processData: false, // 데이터 처리를 하지 않음
            contentType: false, // 기본 폼 데이터로 전송
            success: function (response) {
                alert(response.message || "포스트가 성공적으로 등록되었습니다.");
                location.href = "/home";
            },
            error: function(xhr, status, error) {
                console.error('Error submitting form:', error);
                console.error('Status:', status);
            }
        });
    });
});
