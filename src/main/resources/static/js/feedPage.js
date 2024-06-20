$(function () {
    $.ajax({
        url: '/api/post/list',
        method: 'GET',
        dataType: 'json',
        success: function(data) {
            let container = $('#dataContainer');

            // 데이터의 처음 20개 항목만 처리
            let limitedData = data.slice(0, 20);

            // 받은 데이터를 순회하면서 HTML 요소로 변환하여 컨테이너에 추가
            $.each(limitedData, function(index, item) {
                console.log(data);
                let html = `
                            <div class="item">
                            <img src="${item.file}"
                            <h3>${item.title}</h3><h5>${item.pcreatedAt}</h5>
                            </div>
                        `;
                container.append(html);
            });
        },
        error: function(error) {
            console.error('Error fetching data:', error);
        }
    });
})