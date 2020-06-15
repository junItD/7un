//右键菜单
jQuery(document).ready(function ($) {
    $(document.body).append(
        `<div id="spig" class="spig"><div id="message">加载中……</div><div id="mumu" class="mumu"></div></div>`
    );
    $("#spig").mousedown(function (e) {
        if (e.which == 3) {
            showMessage(
                "秘密通道:<br /> <a style='color: #5bf71d' href=\"https://github.com/ght5935\" title=\"gitHub\">gitHub</a> ",
                10000);
        }
    });
    $("#spig").bind("contextmenu", function (e) {
        return false;
    });
});

//鼠标在消息上时
jQuery(document).ready(function ($) {
    $("#message").hover(function () {
        $("#message").fadeTo("100", 1);
    });
});
// 文本复制提示
try {
    document.body.oncopy = function () {
        alert("复制成功！若要转载请务必保留原文链接，申明来源，谢谢合作！")
    }
} catch (e) {}

//鼠标在上方时
jQuery(document).ready(function ($) {
    $(".mumu").mouseover(function () {
        $(".mumu").fadeTo("300", 0.3);
        msgs = ["我隐身了，你看不到我", "我会隐身哦！嘿嘿！", "别动手动脚的，把手拿开！", "把手拿开我才出来！"];
        var i = Math.floor(Math.random() * msgs.length);
        showMessage(msgs[i]);
    });
    $(".mumu").mouseout(function () {
        $(".mumu").fadeTo("300", 1)
    });
});

//开始
jQuery(document).ready(function ($) {
    var now = (new Date()).getHours();
    if (now > 0 && now <= 6) {
        showMessage(' 你是夜猫子呀？还不睡觉，明天不用上班的么？', 6000);
    } else if (now > 6 && now <= 11) {
        showMessage(' 早上好，早起的鸟儿有虫吃噢！早起的虫儿被鸟吃，你是鸟儿还是虫儿？', 6000);
    } else if (now > 11 && now <= 14) {
        showMessage(' 中午了，吃饭了么？还是在纠结吃什么呢！', 6000);
    } else if (now > 14 && now <= 18) {
        showMessage(' 真是忙碌的一个下午！还好有你在！', 6000);
    } else if (now > 18 && now <= 22) {
        showMessage(' 晚上了啊,是不是该睡觉啦！', 6000);
    } else {
        showMessage(' 快来逗我玩吧！', 6000);
    }


    $(".spig").animate({
        top: $(".spig").offset().top + 300,
        left: document.body.offsetWidth - 160
    }, {
        queue: false,
        duration: 1000
    });
});

//鼠标在某些元素上方时
jQuery(document).ready(function ($) {

    $('#q').focus(function () {
        $(".spig").animate({
            top: $("#q").offset().top - 70,
            left: $("#q").offset().left - 170
        }, {
            queue: false,
            duration: 1000
        });
    });

});


//无聊讲点什么
jQuery(document).ready(function ($) {

    window.setInterval(function () {
        msgs = ["我得意地飘！~飘！~", "看我筋斗云...哎呀,翻过了~~", "偷偷告诉你 峻仔很帅哦!~"];
        var i = Math.floor(Math.random() * msgs.length);
        showMessage(msgs[i], 10000);
    }, 35000);
});

//无聊动动
jQuery(document).ready(function ($) {
    window.setInterval(function () {
        msgs = ["你是不是无聊呀！", "你觉得峻仔帅么~", "是不是不喜欢我呀...", "天行健君子...", "摸摸头,摸摸头...", ];
        var i = Math.floor(Math.random() * msgs.length);
        s = [0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.75, -0.1, -0.2, -0.3, -0.4, -0.5, -0.6, -0.7, -0.75];
        var i1 = Math.floor(Math.random() * s.length);
        var i2 = Math.floor(Math.random() * s.length);
        $(".spig").animate({
            left: document.body.offsetWidth / 2 * (1 + s[i1]),
            top: document.body.offsetheight / 2 * (1 + s[i1])
        }, {
            duration: 2000,
            complete: showMessage(msgs[i])
        });
    }, 45000);
});

var spig_top = 50;
//滚动条移动
jQuery(document).ready(function ($) {
    var f = $(".spig").offset().top;
    $(window).scroll(function () {
        $(".spig").animate({
            top: $(window).scrollTop() + f + 300
        }, {
            queue: false,
            duration: 1000
        });
    });
});

//鼠标点击时
jQuery(document).ready(function ($) {
    var stat_click = 0;
    $(".mumu").click(function () {
        if (!ismove) {
            stat_click++;
            if (stat_click > 4) {
                msgs = ["你有完没完呀？", "你已经摸我" + stat_click + "次了", "你是不是喜欢我呀 !"];
                var i = Math.floor(Math.random() * msgs.length);
            } else {
                msgs = ["筋斗云！~我飞！", "别摸我，有什么好摸的！", "惹不起你，我还躲不起你么？", "不要摸我了，我会咬你你的！ 哼哼",
                    "喜欢我的话,就告诉我呀 ~~"];
                var i = Math.floor(Math.random() * msgs.length);
            }
            s = [0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.75, -0.1, -0.2, -0.3, -0.4, -0.5, -0.6, -0.7, -
                0.75];
            var i1 = Math.floor(Math.random() * s.length);
            var i2 = Math.floor(Math.random() * s.length);
            $(".spig").animate({
                left: document.body.offsetWidth / 2 * (1 + s[i1]),
                top: document.body.offsetheight / 2 * (1 + s[i1])
            }, {
                duration: 500,
                complete: showMessage(msgs[i])
            });
        } else {
            ismove = false;
        }
    });
});
//显示消息函数
function showMessage(a, b) {
    if (b == null) b = 10000;
    jQuery("#message").hide().stop();
    jQuery("#message").html(a);
    jQuery("#message").fadeIn();
    jQuery("#message").fadeTo("1", 1);
    jQuery("#message").fadeOut(b);
    jQuery("#message").css({
        "opacity": "0.5",
        "color": '#1fec0c',
        "background": '#333',
        "padding": '10px',
        "box-sizing": "border-box",
        "border-radius": '10px',
        "text-shadow": '1px 1px #3305f8'
    });
};

//拖动
var _move = false;
var ismove = false; //移动标记
var _x, _y; //鼠标离控件左上角的相对位置
jQuery(document).ready(function ($) {
    $("#spig").mousedown(function (e) {
        _move = true;
        _x = e.pageX - parseInt($("#spig").css("left"));
        _y = e.pageY - parseInt($("#spig").css("top"));
    });
    $(document).mousemove(function (e) {
        if (_move) {
            var x = e.pageX - _x;
            var y = e.pageY - _y;
            var wx = $(window).width() - $('#spig').width();
            var dy = $(document).height() - $('#spig').height();
            if (x >= 0 && x <= wx && y > 0 && y <= dy) {
                $("#spig").css({
                    top: y,
                    left: x,
                }); //控件新位置
                ismove = true;
            }
        }
    }).mouseup(function () {
        _move = false;
    });
});


(function ($) {
    $.fn.extend({
        Scroll: function (opt, callback) {
            if (!opt) {
                var opt = {}
            }
            var _this = this.eq(0).find("ul:first");
            var lineH = _this.find("li:first").height(),
                line = opt.line ? parseInt(opt.line, 10) : parseInt(this.height() / lineH, 10),
                speed = opt.speed ? parseInt(opt.speed, 10) : 7000,
                timer = opt.timer ? parseInt(opt.timer, 10) : 7000;
            if (line == 0) {
                line = 1
            }
            var upHeight = 0 - line * lineH;
            scrollUp = function () {
                _this.animate({
                    marginTop: upHeight
                }, speed, function () {
                    for (i = 1; i <= line; i++) {
                        _this.find("li:first").appendTo(_this)
                    }
                    _this.css({
                        marginTop: 0
                    })
                })
            };
            _this.hover(function () {
                clearInterval(timerID)
            }, function () {
                timerID = setInterval("scrollUp()", timer)
            }).mouseout()
        }
    })
})(jQuery);
$(document).ready(function () {
    $(".bulletin").Scroll({
        line: 1,
        speed: 1000,
        timer: 5000
    })
});
try {
    if (window.console && window.console.log) {
        console.log("%c欢迎访问峻仔的博客！", "color:green");
    }
} catch (e) {}
var sweetTitles = {
    x: 10,
    y: 20,
    tipElements: "a",
    noTitle: true,
    init: function () {
        var noTitle = this.noTitle;
        $(this.tipElements).each(function () { // 获取所有的a标签console.log(this)

            $(this).mouseover(function (e) {
                if (noTitle) {
                    isTitle = true
                } else {
                    isTitle = $.trim(this.title) != ""
                }
                if (isTitle) {
                    this.title = this.innerText;
                    this.myTitle = this.title;
                    this.myHref = this.href;
                    this.myHref = (this.myHref.length > 30 ? this.myHref.toString().substring(0,
                        30) + "..." : this.myHref);
                    this.title = "";

                    showMessage(this.myTitle)
                }
            }).mouseout(function () {
                if (this.myTitle != null) {
                    this.title = this.myTitle;

                }
            }).mousemove(function (e) {

            })
        })
    }
};
$(function () {
    sweetTitles.init()
});