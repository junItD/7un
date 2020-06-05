
//网站开始时间
var siteBeginRunningTime = '2018-07-25 20:00:00';


//填充文章
function putInArticle(data) {
    $('.works').empty();
    var works = $('.works');
    $.each(data, function (index, obj) {
            var workCenter = $('<div class="workCenter">' +
                '<header class="article-header">' +
                '<h1 itemprop="name">' +
                '<a class="article-title" href="' + obj['id'] + '" target="_blank">' + obj['company'] + '</a>' +
                '</h1>' +
                '<div class="article-meta row">' +
                // '<span class="articleType am-badge am-badge-success">' + obj['postName'] + '</span>' +
                '<div class="articlePublishDate">' +
                '<i class="am-icon-calendar"> &nbsp;' + obj['beginTime'] + '</i>' +
                '<i >~ </i>' +
                '<i>' + obj['endTime'] + '</i>' +
                '<i">   &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</i>' +
                '</div>' +
                // '<br>'+
                '<div class="originalAuthor">' +
                '<i class="am-icon-th"> 所属部门 :</i>' +
                '<i"> ' + obj['department'] + '</i>' +
                '<i">   &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</i>' +
                '</div>' +
                // '<br>'+
                '<div class="categories">' +
                '<i class="am-icon-arrow-circle-o-up"> 离职原因 :</i>' +
                '<i > ' + obj['whyLeave'] + '</a></i>' +
                '</div>' +
                '</div>' +
                '</header>' +
                // '<div class="article-entry">' +
                // obj['postName'] +
                // '</div>' +
                '<br>'+
                '<div class="read-all">' +
                '<a href="' + obj['id'] + '" target="_blank">查看我在<span style="color: #a4241f;"> '+obj['companyAbb']+' </span> 的项目经历 <i class="am-icon-angle-double-right"></i></a>' +
                '<br>'+
                '</div>' +
                '<hr>' +
                '<div class="article-tags">' +

                '</div>' +
                '</div>');
            works.append(workCenter);
    })

}

//首页文章分页请求
function ajaxFirst(currentPage) {
    //加载时请求
    $.ajax({
        type: 'POST',
        url: '/getWork',
        dataType: 'json',
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
// getTimeDiff();
var timeDiff;
timeDiff = getTimeDiff();
//获取工作时长
function getTimeDiff() {
    //加载时请求
    $.ajax({
        type: 'POST',
        url: '/getTimeDiff',
        dataType: 'json',
        success: function (data) {
            if(data['status'] == 103){
                dangerNotice(data['message'] + " 获得文章信息失败");
            } else {
                //放入数据
                timeDiff = data['data'];
                $('.gzjy').empty();
                var gzjy = $('.gzjy');
                gzjy.append('搬砖时长：').append(timeDiff);
                scrollTo(0,0);//回到顶部
            }
        }
    });
    return timeDiff;
}



//网站运行时间
//beginTime为建站时间的时间戳
function siteRunningTime(time) {
    var theTime;
    var strTime = "";
    if (time >= 86400){
        theTime = parseInt(time/86400);
        strTime += theTime + "天";
        time -= theTime*86400;
    }
    if (time >= 3600){
        theTime = parseInt(time/3600);
        strTime += theTime + "时";
        time -= theTime*3600;
    }
    if (time >= 60){
        theTime = parseInt(time/60);
        strTime += theTime + "分";
        time -= theTime*60;
    }
    strTime += time + "秒";

    $('.siteRunningTime').html(strTime);
}

var nowDate = new Date().getTime();
//网站开始运行日期
var oldDate = new Date(siteBeginRunningTime.replace(/-/g,'/'));
var time = oldDate.getTime();
var theTime = parseInt((nowDate-time)/1000);
setInterval(function () {
    siteRunningTime(theTime);
    theTime++;
},1000);