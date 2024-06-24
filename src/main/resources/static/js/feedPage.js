$(function () {
    $.ajax({
        url: '/api/post/list',
        method: 'post',
        dataType: 'json',
        success: function (data) {
            let container = $('#dataContainer');

            // 데이터를 순회하며 처리
            let row;
            $.each(data, function (index, item) {
                // 6개마다 새로운 행을 생성
                if (index % 6 === 0) {
                    row = $('<div class="table-row"></div>');
                    container.append(row);
                }

                // p_created_at을 yyyy-mm-dd 형식으로 변환
                let date = new Date(item.p_created_at);
                let formattedDate = date.toISOString().split('T')[0];

                // open_type이 false인 경우 좌물쇠 아이콘 추가
                let titleHtml = item.open_type === false ? `<span class="lock-icon">[비공개]</span>${item.title}` : item.title;

                let cell = $(`
                    <div class="table-cell">
                        <input type="hidden" value="${item.post_url}" class="postURL">
                        <input type="hidden" value="${item.account_id}" class="accountId">
                        <input type="hidden" value="${item.homepage}" class="homepage">
                        <img src="https://minio.bmops.kro.kr/devlog/${item.file}">
                        <h5>${titleHtml}</h5>
                        <h6>${formattedDate}</h6>
                    </div>
                `);
                row.append(cell);
            });

            $(".table-cell").click(function () {
                let postUrl = $(this).find(".postURL").val();
                let homepage = $(this).find(".homepage").val();
                let accountId = $(this).find(".accountId").val();

                location.href = '/' + homepage + "/" + postUrl + "/detail";
            });
        }
    });
});