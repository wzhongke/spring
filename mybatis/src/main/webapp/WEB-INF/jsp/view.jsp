<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 王忠珂
  Date: 2016/11/5
  Time: 12:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>画廊</title>
    <style>
        *{padding:0; margin: 0;}
        body{
            background-color: #fff;
            color: #555;
            font-family:'Avenir Next', 'Lantinghei SC';
            font-size: 14px;
            -webkit-font-smoothing: antialiased;
            -moz-font-smoothing:antialiased;
            font-smoothing: antialiased;
        }
        .wrap{
            width:100%;
            height: 600px;
            position: absolute;
            top: 50%;
            margin-top: -300px;
            background-color: #333;
            overflow: hidden;
            -webkit-perspective: 800px;
            -moz-perspective: 800px;
        }

        /*海报样式*/
        .photo{
            width:260px;
            height:320px;
            position: absolute;
            z-index: 1;
            -webkit-box-shadow: 0 0 1px rgba(0,0,0,.01)  ;
            -moz-box-shadow: 0 0 1px rgba(0,0,0,.01)  ;
            box-shadow: 0 0 1px rgba(0,0,0,.01);
            -webkit-perspective:800px;
            -moz-perspective: 800px;
            -webkit-transition: all .5s;
            -moz-transition: all .5s;
            -ms-transition: all .5s;
            -o-transition: all .5s;
        }

        .photo .side{
            width: 100%;
            height: 100%;
            position: absolute;
            background-color: #eee;
            top: 0;
            right:0;
            padding: 20px;
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            box-sizing: border-box;
        }
        .photo .side-front{
        }
        .photo .side-front .image{
            width: 100%;
            height:250px;
            line-height: 250px;
            overflow: hidden;
        }
        .photo .side-front .caption{
            text-align: center;
            font-size: 16px;
            line-height: 50px;
        }
        .photo .side-front .image img{
            width: 100%;
        }

        .photo .side-back{
            -webkit-transform-style: preserve-3d;
        }
        .photo .side-back .desc{
            color: #666;
            font-size:14px;
            line-height: 1.5em;
        }

        /*当前选中的海报样式*/
        .photo-center{
            /* 设置水平垂直居中 */
            left: 50%;
            top: 50%;
            margin: -160px 0 0 -130px;
            z-index: 999;
        }

        /* 翻转翻转 */
        .photo-wrap{
            position: absolute;
            width:100%;
            height:100%;

            -webkit-transform-style: preserve-3d;
            -webkit-transition: all .6s;
        }

        .photo-wrap .side-front{
            -webkit-transform: rotateY(0deg);
        }
        .photo-wrap .side-back{
            -webkit-transform: rotateY(180deg);
        }
        .photo-wrap .side{
            /** 当元素不面向屏幕时隐藏 */
            -webkit-backface-visibility: hidden;
        }

        .photo-front .photo-wrap{
            -webkit-transform: rotateY(0deg);
        }
        .photo-back .photo-wrap{
            -webkit-transform: rotateY(180deg);
        }

        @font-face {
            font-family: 'icons';
            src:url('/static/fonts/icons.woff') format('woff');
            font-weight: normal;
            font-size: 14px;
        }
        .nav{
            width: 80%;
            height: 30px;
            line-height: 30px;
            position: absolute;
            left:10%;;
            bottom:20px;
            z-index: 999;
            text-align: center;
        }

        .nav .i{
            width: 30px;
            height:30px;
            display: inline-block;
            cursor: pointer;
            background-color: #aaa;
            text-align: center;
            -webkit-border-radius:50%;
            -moz-border-radius:50%;
            border-radius:50%;
            -webkit-transform: scale(.48);
            -moz-transform: scale(.48);
            -ms-transform: scale(.48);
            -o-transform: scale(.48);
            transform: scale(.48);
            -webkit-transition: all .5s;
            -moz-transition: all .5s;
            -ms-transition: all .5s;
            -o-transition: all .5s;
            transition: all .5s;
        }
        .nav .i:after{
            content: "\e600";
            font-family: 'icons';
            font-size: 80%;
            display: inline-block;
            line-height:30px;
            text-align: center;
            color: #fff;
            opacity: 0;
        }
        .nav .i-current{
            -webkit-transform: scale(1);
            -moz-transform: scale(1);
            -ms-transform: scale(1);
            -o-transform: scale(1);
            transform: scale(1);
        }
        .nav .i-current:after{
            opacity:1;
        }
        .nav .i-back{
            -webkit-transform: rotateY(-180deg);
            -moz-transform: rotateY(-180deg);
            -ms-transform: rotateY(-180deg);
            -o-transform: rotateY(-180deg);
            transform: rotateY(-180deg);
            background-color: #555;
        }

        .photo{
            left: 50%;
            top:50%;
            margin: -160px;
        }

        .photo-wrap{
            -webkit-transform-origin:0 50%;
            -moz-transform-origin:0 50%;
            -ms-transform-origin:0 50%;
            -o-transform-origin:0 50%;
            transform-origin:0 50%;
        }
        .photo-front .photo-wrap{
            -webkit-transform: translate(0px, 0px) rotateY(0deg);
            -moz-transform: translate(0px, 0px) rotateY(0deg);
            -ms-transform: translate(0px, 0px) rotateY(0deg);
            -o-transform: translate(0px, 0px) rotateY(0deg);
            transform: translate(0px, 0px) rotateY(0deg);
        }
        .photo-back .photo-wrap{
            -webkit-transform: translate(260px, 0px) rotateY(-180deg);
            -moz-transform: translate(260px, 0px) rotateY(-180deg);
            -ms-transform: translate(260px, 0px) rotateY(-180deg);
            -o-transform: translate(260px, 0px) rotateY(-180deg);
            transform: translate(260px, 0px) rotateY(-180deg);
        }
    </style>
</head>
<body onselectstart="return false;">
<div class="wrap" id="wrap">

    <c:forEach var="image" items="${imageList.elements}" varStatus="status">
        <div class="photo photo-front" onclick="turn(this);" id="photo_${status.index}">
            <div class="photo-wrap">
                <div class="side side-front">
                    <p class="image"><img src="${image.path}" alt=""></p>
                    <p class="caption">${image.caption}</p>
                </div>
                <div class="side side-back">
                    <p class="desc">${image.description}</p>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
<script>

    var photoCenter = null,
            navCurrent = null;
    // 3. 通用函数
    function g(selector) {
        var method = selector.substr(0,1) == "."? 'getElementsByClassName' : 'getElementById';
        return document[method](selector.substr(1));
    }

    function random(range){
        var max = Math.max(range[0], range[1]);
        var min = Math.min(range[0], range[1]);

        var diff = max - min;
        return Math.ceil( Math.random() * diff + min);
    }
    // 4. 输出所有海报
    var data = data;
    function addPhotos(){
        var length = $(".photo").length;
        rsort( random([0, length-1]));
    }
    addPhotos();

    // 6. 计算左右分区的范围 {left:{x:[min, max], y:[]}, right:{}}
    function range(){
        var ranges = {left:{x:[], y:[]}, right:{x:[], y:[]}};
        var wrap = {
            w:g('#wrap').clientWidth,
            h:g('#wrap').clientHeight
        };
        var photo = {
            w:g('.photo')[0].clientWidth,
            h:g('.photo')[0].clientHeight
        };
        ranges.wrap = wrap;
        ranges.photo = photo;

        ranges.left.x = [ 0-photo.w, wrap.w/2 - photo.w/2];
        ranges.left.y = [0-photo.h, wrap.h];

        ranges.right.x = [ wrap.w/2 + photo.w/2, wrap.w + photo.w];
        ranges.right.y = [0-photo.h, wrap.h];

        return ranges;
    }



    // 5. 排序海报
    function rsort(n){
        var _photos = g('.photo');
        if (photoCenter != null){
            photoCenter.className = photoCenter.className.replace(/\s*photo-center\s*/, '')
                    .replace(/\s*photo-front\s*/, '')
                    .replace(/\s*photo-back\s*/, '');
            photoCenter.style['-webkit-transform'] = 'rotate('+ random([-150, 150]) +'deg) scale(1)';
        }
        photoCenter = g("#photo_" + n);
        photoCenter.className += ' photo-center';
        photoCenter.style.left = "";
        photoCenter.style.top = "";
        photoCenter.style['-webkit-transform']='rotate(360deg) scale(1.3)';

        // 把剩下的海报分为左右区域两个部分
        var ranges = range();
        var i, len;
        for(i=0, len=_photos.length/2; i<len; i++){
            var photo = _photos[i];
            if(/photo-center/.test(photo.className))
                continue;
            photo.style.left = random(ranges.left.x) + 'px';
            photo.style.top = random(ranges.left.y) + 'px';
            photo.style['-webkit-transform'] = 'rotate('+ random([-150, 150]) +'deg) scale(1)';
        }

        for(i=len, len=_photos.length; i<len; i++){
            var photo = _photos[i];
            if(/photo-center/.test(photo.className))
                continue;
            photo.style.left = random(ranges.right.x) + 'px';
            photo.style.top = random(ranges.right.y) + 'px';
            photo.style['-webkit-transform'] = 'rotate('+ random([-150, 150]) +'deg) scale(1)';
        }

        if (navCurrent != null) {
            navCurrent.className = navCurrent.className.replace(/\s*i-current\s*/, '').replace(/\s*i-back\s*/, '');
        }
        navCurrent = g('#nav_' + n);
        console.log("n = " + n);
        navCurrent.className += ' i-current ';
    }

    // 1. 翻面控制
    function turn(el) {
        var cls = el.className;
        var n = el.id.split('_')[1];
        if(!/photo-center/.test(cls)){
            return rsort(n);
        }

        if(/photo-front/.test(cls)){
            cls = cls.replace(/photo-front/, 'photo-back');
            g('#nav_' + n).className += " i-back";
        }else{
            cls = cls.replace(/photo-back/, 'photo-front');
            g('#nav_' + n).className = g('#nav_' + n).className.replace(/\s*i-back\s*/, '');
        }
        el.className = cls;
    }


</script>
</body>
</html>
