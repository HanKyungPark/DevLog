import Swiper from 'swiper';

const heroSlider = new Swiper('.hero-slider', {
    slidesPerView: 1,
    spaceBetween: 0,
    navigation: {
        nextEl: '.swiper-button-next',
        prevEl: '.swiper-button-prev',
    },
    loop: true, // 무한 스크롤 기능을 위한 loop 옵션 추가
});

// 다음/이전 버튼 클릭 시 페이지 이동
const prevBtn = document.querySelector('.swiper-button-prev');
const nextBtn = document.querySelector('.swiper-button-next');

prevBtn.addEventListener('click', () => {
    heroSlider.slidePrev();
});

nextBtn.addEventListener('click', () => {
    heroSlider.slideNext();
});

// 무한 스크롤 기능 구현 (기존 코드 유지)
heroSlider.on('slideChange', (swiper) => {
    const currentSlideIndex = swiper.activeIndex;
    const totalSlides = swiper.slides.length;

    if (currentSlideIndex === 0) {
        swiper.goToSlide(totalSlides - 2); // 마지막 페이지에서 첫 페이지로 이동
    }

    if (currentSlideIndex === totalSlides - 1) {
        swiper.goToSlide(1); // 첫 페이지에서 두 번째 페이지로 이동
    }
});
