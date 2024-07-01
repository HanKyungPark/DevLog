$(function (){
    // 프로필 이미지 미리보기 기능
    document.getElementById('file').addEventListener('change', function(event) {
        var reader = new FileReader();
        reader.onload = function() {
            var output = document.getElementById('profileImagePreview');
            output.src = reader.result;
        };
        reader.readAsDataURL(event.target.files[0]);
    });

    $("#btn-submit").click(function (e){
        e.preventDefault();
        post();
    });
});

function post() {
    let form = $("#profileForm")[0];
    let formData = new FormData(form);

    // FormData 디버깅
    for (let [key, value] of formData.entries()) {
        console.log(key, value);
    }

    $.ajax({
        url: "/api/account/updateinfo",
        type: "post",
        data: formData,
        contentType: false,
        processData: false,
        cache: false,
        success: function () {
            alert("블로그 수정 완료!");
            location.href = "/home";
        },
        error: function (xhr, status, error) {
            console.error("Error occurred: ", status, error);
            alert("블로그 저장 중 오류가 발생했습니다.");
        }
    });
}
