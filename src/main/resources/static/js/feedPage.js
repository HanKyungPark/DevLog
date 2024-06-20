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

                let cell = $(`
                            <div class="table-cell" onclick="location.href='${item.postUrl}'">
                                <img src="https://minio.bmops.kro.kr/devlog/${item.file}">
                                <h3>${item.title}</h3>
                                <h5>${formattedDate}</h5>
                            </div>
                        `);
                row.append(cell);
            });
        },
        error: function(error) {
            console.error('Error fetching data:', error);
        }
    });
});