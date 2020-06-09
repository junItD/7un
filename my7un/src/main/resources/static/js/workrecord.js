
var workId = "";

//填充文章
function putInArticle(data) {
    $('.workinfo').empty();
    var works = $('.workinfo');
    $.each(data, function (index, obj) {
        /*<section data-am-scrollspy="{animation: 'slide-left'}">
                                    <span class="point-time point-purple"></span>
                                    <time datetime="2019-04">
                                        <span>2019/04/07</span>
                                    </time>
                                    <aside>
                                        <p class="things">不知道该说什么</p>
                                        <p class="brief"><span class="text-yello">
                                                斯人如彩虹，遇见方知有。很多事情，真的是身不由己。
                                            </span></p>
                                    </aside>
                                </section>*/
            var workCenter = $('<section data-am-scrollspy="{animation: \'slide-left\'}">' +
                '<span class="point-time point-purple"></span>' +
                '<time datetime="'+ obj['beginTime']+'">' +
                '<span> '+obj['beginTime']+' </span>' +
                '</time>' +
                ' <aside>' +
                '<p class="things">'+obj['project']+'</p>' +
                '<p class="brief">' +
                '<span class="text-yello">'+obj['projetDetail']+'</span>'+
                '</aside>' +
                '</section>'+
                ' <br><hr data-am-widget="divider" style="" class="am-divider am-divider-dashed" />'
            );
            works.append(workCenter);
    })

}

$.ajax({
    type: 'HEAD', // 获取头信息，type=HEAD即可
    url : window.location.href,
    async:false,
    success:function (data, status, xhr) {
        workId = xhr.getResponseHeader("workId");
    }
});

//首页文章分页请求
function ajaxFirst(currentPage) {
    //加载时请求
    $.ajax({
        type: 'POST',
        url: '/getWorkRecord',
        dataType: 'json',
            data:{
                workId:workId,
            },
        success: function (data) {
            if(data['status'] == 103){
                dangerNotice(data['message'] + " 获得文章信息失败");
            } else {
                //放入数据
                putInArticle(data['data']);
                scrollTo(0,0);//回到顶部

                var length = data['data'].length;
            }
        },
        error: function () {
            alert("获得信息失败！");
        }
    });
}
ajaxFirst(1);

