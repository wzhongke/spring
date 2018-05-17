/**
 * Created by admin on 2016/11/3.
 */
define(['jquery'], function ($) {
    function DragFile (opts) {
        //阻止浏览器默认行。
        $(document).on({
            dragleave:function(e){    //拖离
                e.preventDefault();
            },
            drop:function(e){  //拖后放
                e.preventDefault();
            },
            dragenter:function(e){    //拖进
                e.preventDefault();
            },
            dragover:function(e){    //拖来拖去
                e.preventDefault();
            }
        });

        document.getElementById("drop").addEventListener("drop", function(e){
            e.preventDefault(); //取消默认浏览器拖拽效果
            var fileList = e.dataTransfer.files,
                file = fileList[0],
                fileNumber = fileList.length; //获取文件对象

            for (var i= 0; i<fileNumber; file = fileList[++i]) {
                var img = window.URL.createObjectURL(file),
                    fileName = file.name,
                    filesize = Math.floor((fileList[0].size)/1024);
                var str = "<img src='"+img+"'><p>图片名称："+fileName+"</p><p>大小："+filesize+"KB</p>";
                $("#preview").append(str);
            }
        });

    }

    return {
        DragFile: DragFile
    };
});