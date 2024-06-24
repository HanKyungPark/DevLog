$(function () {
    alert("시이이이이작")
    let info=window.location.pathname.split("/")[2];


    //페이지 정보들 불러오고 조회
    $.ajax({
        url: "/api/post/detail",
        type: "post",
        data:{"info":info},
        dataType: "json",
        success: function (data) {
            console.log(data)
            $("#title").text(data[0].title);
            $("#date").text(data[0].pcreatedAt);
            for (let i = 0; i < data[0].postTags.length; i++) {
                $("#tagName").text(data[0].postTags[i].tagName);
            }
            $("#date").text(data[0].pcreatedAt);
            $(".cont").html(data[0].pcontent);

        }
    })
})
//     function loadComments(postId) {
//         $.ajax({
//             url: "/api/comment/list",
//             type: "post",
//             data: { "postId": postId },
//             dataType: "json",
//             success: function (data) {
//                 console.log("Data received:", data); // 디버깅을 위해 추가
//                 let commentTableBody = $("#commentTable tbody");
//                 commentTableBody.empty(); // 기존 내용을 초기화
//                 data.forEach(function (comment) {
//                     let row = $("<tr></tr>");
//                     row.append("<td>" + comment.name + "</td>");
//                     row.append("<td>" + comment.ccontent + "</td>");
//                     row.append("<td>" + formatDate(comment.ccreatedAt) + "</td>");
//
//                     commentTableBody.append(row);
//                 });
//             },
//             error: function (xhr, status, error) {
//                 console.error("Error:", error);
//                 console.error("Status:", status);
//                 console.dir(xhr);
//             }
//         });
//     }
//
//     // 페이지 로드 시 댓글 목록 불러오기
//     loadComments(postId);
//
//     $(".btn").click(function (e) {
//         e.preventDefault();
//         console.log("Button clicked"); // 디버깅을 위해 추가
//
//         let commentData = {
//             accountId: $("#accountId").val(),
//             cContent: $("#cContent").val(),
//             postId: $("#postId").val(),
//             parentId: 1
//         };
//
//         console.log("Comment Data:", commentData); // 디버깅을 위해 추가
//
//         $.ajax({
//             url: "/api/comment/write",
//             type: "POST",
//             data: JSON.stringify(commentData),
//             contentType: "application/json",
//             success: function () {
//                 alert("댓글이 저장되었습니다.");
//                 // 댓글 목록 다시 로드
//                 loadComments(postId);
//             },
//             error: function (xhr, status, error) {
//                 console.error("Error:", error);
//                 console.error("Status:", status);
//                 console.dir(xhr);
//             }
//         });
//     });
// });
//
// //날짜 포맷 변환 함수
// function formatDate(dateString) {
//     const date = new Date(dateString);
//     const year = date.getFullYear();
//     let month = date.getMonth() + 1;
//     if (month < 10) {
//         month = '0' + month;
//     }
//     let day = date.getDate();
//     if (day < 10) {
//         day = '0' + day;
//     }
//     let hours = date.getHours();
//     if (hours < 10) {
//         hours = '0' + hours;
//     }
//     let minutes = date.getMinutes();
//     if (minutes < 10) {
//         minutes = '0' + minutes;
//     }
//     return `${year}-${month}-${day} ${hours}:${minutes}`;
// }
