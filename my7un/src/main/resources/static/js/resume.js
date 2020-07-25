

//填充文章
function putInArticle(data) {
    $('.works').empty();
    var works = $('.works');
    $.each(data, function (index, obj) {
            var workCenter = $('<div class="workCenter">' +
                '<header class="article-header">' +
                '<h1 itemprop="name">' +
                '<a class="article-title" href="/getWorkRecordById/' + obj['id'] + '" target="_self">' + obj['company'] + '</a>' +
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
                '<a href="/getWorkRecordById/' + obj['id'] + '" target="_self">查看我在<span style="color: #a4241f;"> '+obj['companyAbb']+' </span> 的项目经历 <i class="am-icon-angle-double-right"></i></a>' +
                // '<a href="/getWorkRecordById/1591174028"  target="_self">查看我在<span style="color: #a4241f;"> '+obj['companyAbb']+' </span> 的项目经历 <i class="am-icon-angle-double-right"></i></a>' +
                '<br>'+
                '<br>'+
                '</div>' +
                '<hr>' +
                '<div class="article-tags">' +

                '</div>' +
                '</div>');
            works.append(workCenter);
    })

}

function openNewWin(url, title) {
    window.open(url, title);
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
$("#myResume").click(function(){
    // $.get("/upDown",function(){
    // });
    $.ajax({
        url:'/upDown?date='+new Date().getTime(),
        async:true,
        contentType:'application/x-www-form-urlencoded',
        data:{
            filePath : "resume.doc"
        },
        // xhr:function(){
        //     var xhr = new XMLHttpRequest();
        //     xhr.responseType = 'arraybuffer';
        //     return xhr;
        // },
        xhrFields: { responseType: "arraybuffer" },
        type:'POST',
        timeout:60000,
        success:function(result){
            var blob = new Blob([result], {type: "application/msword;charset=utf-8"}),
                Temp = document.createElement("a");
            Temp.href = window.URL.createObjectURL(blob);
            Temp.download ='赵峻.doc'
            $('body').append(Temp);
            Temp.click();
        }})
});
