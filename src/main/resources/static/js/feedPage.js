$(function () {
    $.ajax({
        url: '/api/post/list',
        method: 'post',
        dataType: 'json',
        success: function (data) {
            let container = $('#container');

            // 데이터를 순회하며 처리
            $.each(data, function (index, item) {
                console.log(data[0]);
                // p_created_at을 yyyy-mm-dd 형식으로 변환
                let date = new Date(item.p_created_at);
                let formattedDate = date.toISOString().split('T')[0];

                // open_type이 false인 경우 좌물쇠 아이콘 추가
                let titleHtml = item.open_type === false ? `<span class="lock-icon">[비공개]</span>${item.title}` : item.title;
                let msg = `
<div class="detail gallery-item-container item-contain-animation-fade-in" id="pgi7c0cd68a821f43e18a4d0abc19887849_0" tabindex="0" aria-label="FUN WAYS TO DRESS UP A T-SHIRT" data-hash="7c0cd68a-821f-43e1-8a4d-0abc19887849" data-id="7c0cd68a-821f-43e1-8a4d-0abc19887849" data-idx="0" role="" data-hook="item-container"
style="width: 300px;height: 430px; overflow: hidden; border-color: rgb(0, 0, 0); border:2px solid black; transition: opacity 0.2s ease 0s; opacity: 1;border-radius: 20px;padding: 0;    box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;">
    <div>
    <div style="background-color: #fffeed;"><a style="color: black;font-size: 18px; font-family: Verdana" href="${item.homepage}">${item.homepage}</a></div>
        <div data-hook="item-wrapper" class="gallery-item-wrapper visible cube-type-fill [object Object]" id="item-wrapper-7c0cd68a-821f-43e1-8a4d-0abc19887849"
         style="height: 500px; width: 100%; margin: -1px;">
            <div class="gallery-item-content item-content-regular image-item gallery-item-visible gallery-item gallery-item-preloaded   " data-hook="image-item"
            style="width: 100%; height: 100%; margin-top: 0; margin-left: 0;">
                <img alt="FUN WAYS TO DRESS UP A T-SHIRT" id="7c0cd68a-821f-43e1-8a4d-0abc19887849" class="gallery-item-visible gallery-item gallery-item-preloaded" data-hook="gallery-item-image-img" data-idx="0" src="${item.file}" loading="eager" style="width: 300px; height: 280px;">
                <div class="gallery-item-common-info-outer [object Object]"
                style="box-sizing:content-box;background-color:white; padding: 0;margin: 0; height:150px;">
                <div style="background-color: #040940;color: white; font-size: 20px;font-family: 'Bodoni 72';overflow: hidden;
text-overflow: ellipsis;
white-space: nowrap;">${titleHtml}</div>
                    <div style="overflow:hidden;box-sizing:border-box;width:100%;" class="gallery-item-common-info gallery-item-bottom-info">
                        <article class="RiOfiW pu51Xe lyd6fK blog-post-description-font blog-text-color blog-card-background-color blog-card-border-color post-list-item blog-post-homepage-hover-container blog-post-homepage-border-color blog-post-homepage-post-container blog-post-homepage-background-color _UH27m" data-hook="item-info">
                          
                            
                            <div class="HpF4Kw" style="margin-top: 10px">
                                <div class="vDgiMx" style="padding: 0; margin: 0">
                                    <p class="xtYFC" style="overflow:hidden; text-align: left; padding: 0; margin: 0">
                                            
                                            <input type="hidden" value="${item.homepage}" id="homepage">
                                            <input type="hidden" value="${item.post_url}" id="postURL">
                                    </p>
                                    
                                    <h5 class="blog-post-homepage-excerpt-font blog-post-homepage-hover-visible" data-hook="blog-excerpt" style="text-align: right; padding: 0; margin: 0">
                                        <div><button id="likeButton" class="like-button" style="float: left;margin-top: 40px;margin-left: 20px;font-size: 30px"><i class="bi bi-heart"></i></button></div>
                                        
                                        
                                        <div  class="blog-post-homepage-meta-font" style="display: flex; justify-content: right; margin-right: 20px">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye" viewBox="0 0 16 16">
                                                <path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8M1.173 8a13 13 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5s3.879 1.168 5.168 2.457A13 13 0 0 1 14.828 8q-.086.13-.195.288c-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5s-3.879-1.168-5.168-2.457A13 13 0 0 1 1.172 8z"/>
                                                <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5M4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0"/>
                                                </svg><p> 조회수${item.hits}</p>
                                        </div>
                                       
                                       <div style="margin-right: 20px">${formattedDate}</div> 
                                       
                                        <a href="${item.homepage}" class="blog-post-homepage-meta-font" style="cursor: pointer;width: 50px"><p style="color: black; font-size: 30px;margin-right: 10px;margin-bottom: 20px"><i class="bi bi-house-check-fill"></i></p></a>
                                    </h5>
                                </div>
                                <div class="MyC1u blog-post-homepage-meta-font" style="display:flex;align-items:center;gap:12px">
                                    <div class="fQEAd blog-post-homepage-meta-font" style="display:flex; text-align: right">
                                        <i class="fa fa-heart" style="font-size:14px"></i>

                                    </div>
                                    <div class="flPaSE blog-post-homepage-meta-font" style="display:flex;align-items:center">
                                        <i class="fa fa-eye" style="font-size:14px"></i>
                                        <p style="font-size:14px;line-height:18px;margin:0px 3px"></p>
                                    </div>
                                </div>
                            </div>
                            
                        </article>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>`;
                container.append(msg);
            });

            // 이벤트 핸들러 추가
            // $(".detail").click(function () {
            //     let homepage=$(this).find("#homepage").val();
            //     let postURL=$(this).find("#postURL").val();
            //     location.href="/"+homepage+"/"+postURL+"/detail";
            // });
            // 모든 like-button 요소를 선택합니다.
            const likeButtons = document.querySelectorAll('.like-button');

            // 각 버튼에 대해 클릭 이벤트 리스너를 추가합니다.
            likeButtons.forEach(button => {
                button.addEventListener('click', function() {
                    const icon = this.querySelector('i');
                    if (icon.classList.contains('bi-heart')) {
                        icon.classList.remove('bi-heart');
                        icon.classList.add('bi-heart-fill');
                        this.classList.add('liked');
                    } else {
                        icon.classList.remove('bi-heart-fill');
                        icon.classList.add('bi-heart');
                        this.classList.remove('liked');
                    }
                });
            });

        }
    });
});
