
var workId = "";

//填充文章
function putInArticle(data) {
    $('.workinfo').empty();
    var works = $('.workinfo');
    $.each(data, function (index, obj) {
            var workCenter = $('<section>' +
                '<span class="point-time point-purple"></span>' +
                '<time datetime="'+ obj['beginTime']+'">' +
                '<span> '+obj['beginTime']+' ~ '+obj['endTime']+'</span>' +
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

