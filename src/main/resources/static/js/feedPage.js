$(function () {
    $.ajax({
        url: '/api/post/list',
        method: 'GET',
        dataType: 'json',
        success: function(data) {
            let container = $('#dataContainer');

            // 데이터의 처음 20개 항목만 처리
            let limitedData = data.slice(0, 20);

            let row;
            $.each(limitedData, function(index, item) {
                // 6개마다 새로운 행을 생성
                if (index % 6 === 0) {
                    row = $('<div class="table-row"></div>');
                    container.append(row);
                }

                // pcreatedAt을 yyyy-mm-dd 형식으로 변환
                let date = new Date(item.pcreatedAt);
                let formattedDate = date.toISOString().split('T')[0];

                // openType이 1인 경우 좌물쇠 아이콘 추가
                let titleHtml = item.openType === 0 ? `<span class="lock-icon">[비공개]</span>${item.title}` : item.title;

                let cell = $(`
                            <div class="table-cell" onclick="location.href='/detail?postUrl=${item.postUrl}'">
                                <img src="${item.file}">
                                <h5>${titleHtml}</h5>
                                <h6>${formattedDate}</h6>
                            </div>
                        `);
                row.append(cell);
            });
        }
    });
});