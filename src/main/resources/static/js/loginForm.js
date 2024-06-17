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
})