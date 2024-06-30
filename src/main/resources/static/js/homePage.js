// import Swiper from 'swiper';
// import $ from 'jquery';
//
// const heroSlider = new Swiper('.hero-slider', {
//     slidesPerView: 1,
//     spaceBetween: 0,
//     navigation: {
//         nextEl: '.swiper-button-next',
//         prevEl: '.swiper-button-prev',
//     },
//     loop: true, // 무한 스크롤 기능을 위한 loop 옵션 추가
// });
//
// // 다음/이전 버튼 클릭 시 페이지 이동
// const prevBtn = document.querySelector('.swiper-button-prev');
// const nextBtn = document.querySelector('.swiper-button-next');
//
// if (prevBtn && nextBtn) {
//     prevBtn.addEventListener('click', () => {
//         heroSlider.slidePrev();
//     });
//
//     nextBtn.addEventListener('click', () => {
//         heroSlider.slideNext();
//     });
// }
//
// // 무한 스크롤 기능 구현 (기존 코드 유지)
// heroSlider.on('slideChange', (swiper) => {
//     const currentSlideIndex = swiper.activeIndex;
//     const totalSlides = swiper.slides.length;
//
//     if (currentSlideIndex === 0) {
//         swiper.slideToLoop(totalSlides - 2); // 마지막 페이지에서 첫 페이지로 이동
//     }
//
//     if (currentSlideIndex === totalSlides - 1) {
//         swiper.slideToLoop(1); // 첫 페이지에서 두 번째 페이지로 이동
//     }
// });

$(function (){
    // 조회수 가장 많은 블로그 + 글 가장 많은 블로그
    $.ajax({
        url: "api/post/rankbyhits",
        type: "post",
        dataType: "json",
        success: function (data) {

                $("#linkbyhits").attr("href", "http://dev.devlog.r-e.kr/" + data[0].homepage);
                $("#filebyhits").attr("src", data[0].file);
                $("#homepagebyhits").text(data[0].homepage);
                $("#biographybyhits").text(data[0].biography);
            console.log("포스트 가장많은 id"+data[0].accountId)
        }
    });

    $.ajax({
        url: "api/post/rankbyposts",
        type: "post",
        dataType: "json",
        success: function (data) {
            if (data && data.length > 0) {
                $("#linkbyposts").attr("href", "http://dev.devlog.r-e.kr/" + data[0].homepage);
                $("#filebyposts").attr("src", data[0].file);
                $("#homepagebyposts").text(data[0].homepage);
                $("#biographybyposts").text(data[0].biography);
                console.log("포스트 가장많은 id"+data[0].accountId)
            }
        }
    });
});
