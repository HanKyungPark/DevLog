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
    $("#btn-summit").click(function (e){
        e.preventDefault();

        let form = $("#profileForm")[0];
        let formData = new FormData(form);



        $.ajax({
            url:"/api/blog/create",
            type:"post",
            data:formData,
            contentType : false,
            processData: false,
            cache:false,
            success: function (data){
                console.log(data);
                if(data){
                    alert("중복된 URL 입니다.");
                } else{
                    alert("블로그 등록 성공!");
                    location.href = "/home";
                }
            }
        })
    })
})