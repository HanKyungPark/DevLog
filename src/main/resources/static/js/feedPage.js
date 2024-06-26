$(function () {
    $.ajax({
        url: '/api/post/list',
        method: 'post',
        dataType: 'json',
        success: function (data) {
            let container = $('#container');

            // 데이터를 순회하며 처리
            $.each(data, function (index, item) {
                // p_created_at을 yyyy-mm-dd 형식으로 변환
                let date = new Date(item.p_created_at);
                let formattedDate = date.toISOString().split('T')[0];

                // open_type이 false인 경우 좌물쇠 아이콘 추가
                let titleHtml = item.open_type === false ? `<span class="lock-icon">[비공개]</span>${item.title}` : item.title;
                let msg = `<div class="gallery-item-container item-container-regular has-custom-focus visible hover-animation-fade-in"
                                         id="pgi7c0cd68a821f43e18a4d0abc19887849_0" tabindex="0"
                                         aria-label="FUN WAYS TO DRESS UP A T-SHIRT"
                                         data-hash="7c0cd68a-821f-43e1-8a4d-0abc19887849"
                                         data-id="7c0cd68a-821f-43e1-8a4d-0abc19887849" data-idx="0" role=""
                                         data-hook="item-container"
                                         style="width: 300px;height: 400px; overflow: hidden; border-width: 1px; border-color: rgb(0, 0, 0); border-style: dotted; transition: opacity 0.2s ease 0s; opacity: 1;">
                                        <div>
                                            <div data-hook="item-wrapper"
                                                 class="gallery-item-wrapper visible cube-type-fill [object Object]"
                                                 id="item-wrapper-7c0cd68a-821f-43e1-8a4d-0abc19887849"
                                                 style="height: 500px; width: 100%; margin: -1px;">
                                                <div class="gallery-item-content item-content-regular image-item gallery-item-visible gallery-item gallery-item-preloaded   "
                                                     data-hook="image-item"
                                                     style="width: 100%; height: 100%; margin-top: 0px; margin-left: 0px;">
                                                    <img alt="FUN WAYS TO DRESS UP A T-SHIRT"
                                                         id="7c0cd68a-821f-43e1-8a4d-0abc19887849"
                                                         class="gallery-item-visible gallery-item gallery-item-preloaded"
                                                         data-hook="gallery-item-image-img" data-idx="0"
                                                         src="https://minio.bmops.kro.kr/devlog/${item.file}"
                                                         loading="eager" style="width: 300px; height: 300px;">
                                       
                                        <div class="gallery-item-common-info-outer [object Object]"
                                             style="height:310px;box-sizing:content-box">
                                            <div style="overflow:hidden;box-sizing:border-box;width:100%;"
                                                 class="gallery-item-common-info gallery-item-bottom-info">
                                                <article
                                                        class="RiOfiW pu51Xe lyd6fK blog-post-description-font blog-text-color blog-card-background-color blog-card-border-color post-list-item blog-post-homepage-hover-container blog-post-homepage-border-color blog-post-homepage-post-container blog-post-homepage-background-color _UH27m"
                                                        data-hook="item-info">
                                                    <h2 class="blog-post-homepage-title-font"
                                                        style="text-align:left;display:block;white-space:nowrap;overflow:hidden;text-overflow:ellipsis"
                                                        id="long-title-7c0cd68a-821f-43e1-8a4d-0abc19887849" title="${item.title}">
                                                        <a >${titleHtml}</a>
                                                    </h2>
                                                    <h3 class="blog-post-homepage-excerpt-font blog-post-homepage-hover-visible"
                                                        data-hook="blog-excerpt">${item.explain}</h3>
                                                    <div class="HpF4Kw"
                                                         style="display:flex;flex-direction:row;justify-content:space-between">
                                                        <div class="vDgiMx" style="overflow:hidden">
                                                            <p class="xtYFC" style="overflow:hidden;white-space:nowrap;text-overflow:ellipsis">
                                                                <a href="/profile/${item.user_id}"
                                                                   class="blog-post-homepage-meta-font">${item.nick}</a>
                                                            </p>
                                                            <p class="qHGTYa blog-post-homepage-meta-font">${formattedDate}</p>
                                                        </div>
                                                        <div class="MyC1u blog-post-homepage-meta-font"
                                                             style="display:flex;align-items:center;gap:12px">
                                                            <div class="fQEAd blog-post-homepage-meta-font"
                                                                 style="display:flex;align-items:center">
                                                                <i class="fa fa-heart" style="font-size:14px"></i>
                                                                <p style="font-size:14px;line-height:18px;margin:0px 3px">${item.like}</p>
                                                            </div>
                                                            <div class="flPaSE blog-post-homepage-meta-font"
                                                                 style="display:flex;align-items:center">
                                                                <i class="fa fa-eye" style="font-size:14px"></i>
                                                                <p style="font-size:14px;line-height:18px;margin:0px 3px">${item.view}</p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                             </div>
                                            </div>
                                        </div>
                                                </article>
                                            </div>
                                        </div>
                                    </div>`;
                container.append(msg);
            });
        }
    });
});
