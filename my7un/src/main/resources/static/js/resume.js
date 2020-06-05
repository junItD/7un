
//网站最后更新时间（版本更新需更改）
var siteLastUpdateTime = '2020年4月22日19点';

//网站开始时间
var siteBeginRunningTime = '2018-07-25 20:00:00';

// 广告上下滚动
function getStyle(obj,name){
    if(obj.currentStyle)
    {
        return obj.currentStyle[name];
    }
    else
    {
        return getComputedStyle(obj,false)[name];
    }
}
function startMove(obj,json,doEnd){
    clearInterval(obj.timer);
    obj.timer=setInterval(function(){
        var oStop=true;
        for(var attr in json)
        {
            var cur=0;
            if(attr=='opacity')
            {
                cur=Math.round(parseFloat(getStyle(obj,attr))*100);
            }
            else
            {
                cur=parseInt(parseInt(getStyle(obj,attr)));
            }
            var speed=(json[attr]-cur)/6;
            speed=speed>0?Math.ceil(speed):Math.floor(speed);
            if(cur!=json[attr])
            {
                oStop=false;
            }
            if(attr=='opacity')
            {
                obj.style.filter='alpha(opacity:'+(speed+cur)+')';
                obj.style.opacity=(speed+cur)/100;
            }
            else
            {
                obj.style[attr]=speed+cur+'px';
            }
        }
        if(oStop)
        {
            clearInterval(obj.timer);
            if(doEnd) doEnd();
        }
    },30);
}
window.onload=function(){
    var oDiv=document.getElementsByClassName('roll')[0];
    var oUl=oDiv.getElementsByTagName('ul')[0];
    var aLi=oUl.getElementsByTagName('li');

    var now=0;
    for(var i=0;i<aLi.length;i++)
    {
        aLi[i].index=i;
    }

    function next(){
        now++;
        if(now==aLi.length)
        {
            now=0;
        }
        startMove(oUl,{top:-26*now})
    }
    //设置广播滚动时间
    var timer=setInterval(next,3000);
    oDiv.onmouseover=function(){
        clearInterval(timer);
    };
    oDiv.onmouseout=function(){
        timer=setInterval(next,3000);
    }
};

//填充文章
function putInArticle(data) {
    $('.works').empty();
    var works = $('.works');
    $.each(data, function (index, obj) {
            var workCenter = $('<div class="workCenter">' +
                '<header class="article-header">' +
                '<h1 itemprop="name">' +
                '<a class="article-title" href="' + obj['id'] + '" target="_blank">' + obj['id'] + '</a>' +
                '</h1>' +
                '<div class="article-meta row">' +
                '<span class="articleType am-badge am-badge-success">' + obj['company'] + '</span>' +
                '<div class="articlePublishDate">' +
                '<i class="am-icon-calendar"><a class="linkColor" href="/archives?archive=' + obj['beginTime'] + '"> ' + obj['beginTime'] + '</a></i>' +
                '</div>' +
                '<div class="originalAuthor">' +
                '<i class="am-icon-user"> ' + obj['department'] + '</i>' +
                '</div>' +
                '<div class="categories">' +
                '<i class="am-icon-folder"><a class="linkColor" href="/categories?category=' + obj['whyLeave'] + '"> ' + obj['whyLeave'] + '</a></i>' +
                '</div>' +
                '</div>' +
                '</header>' +
                '<div class="article-entry">' +
                obj['postName'] +
                '</div>' +
                '<div class="read-all">' +
                '<a href="' + obj['id'] + '" target="_blank">阅读全文 <i class="am-icon-angle-double-right"></i></a>' +
                '</div>' +
                '<hr>' +
                '<div class="article-tags">' +

                '</div>' +
                '</div>');
            works.append(workCenter);
            var articleTags = $('.article-tags');
            for(var i=0;i<obj['articleTags'].length;i++){
                var articleTag = $('<i class="am-icon-tag"><a class="tag" href="/tags?tag=' + obj['articleTags'][i] + '"> ' + obj['articleTags'][i] + '</a></i>');
                articleTags.eq(index).append(articleTag);
            }
            // var likes = $('<span class="likes"><i class="am-icon-heart"> ' + obj['likes'] + '个喜欢</i></span>');
            // articleTags.eq(index).append(likes);
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