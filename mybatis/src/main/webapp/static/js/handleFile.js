
define (["jquery", "EventUtil"], function($, util){
    var droptarget = document.getElementById("files_list");
    var FileAPI = function() {
           // 不必把文件读取到JavaScript中
        function createObjectURL (blob) {
            if (window.URL) {
                return window.URL.createObjectURL(blob);
            } else if (window.webkitURL) {
                return window.webkitURL.createObjectURL(blob);
            } else {
                return null;
            }
        }

        function handleEvent() {
            var info = '',
                output = $("#output"),
                files, i, len;
            util.EventUtil.preventDefault(event);
            if (event.type == "drop") {
                files = event.dataTransfer.files;
                i = 0;
                len = files.length;
                while (i<len) {
                    var url = createObjectURL(files[i]);
                    console.log(url);
                    var image = new Image();
                    image.src = url;
                    image.name = files[i].name;

                    image.onload = function() {
                        var canvas = document.createElement("canvas");
                        ctx = canvas.getContext("2d");
                        var hight = this.height / 4,
                            width = this.width / 4;
                        canvas.height = hight;
                        canvas.width = width;
                        ctx.drawImage(this, 0, 0, width, hight);
                        var imgurl = canvas.toDataURL("image/png");
                        output.append('<img src="' + imgurl + '" name="'+ this.name +'"/>');
                    };
                    i++;
                }
            }
        }

        util.EventUtil.addHandler(droptarget, "dragenter", handleEvent);
        util.EventUtil.addHandler(droptarget, "dragover", handleEvent);
        util.EventUtil.addHandler(droptarget, "drop", handleEvent);

        function upload() {
            var $images = $("#output").find("img");
            for (var i=0; i<$images.length; i++) {
                var data = $($images[i]).attr("src");
                var imageData = encodeURIComponent(data);
                var formData = {
                    imageName: $($images[i]).attr("name"),
                    imageData: imageData
                };

                $.post("/files/uploadImage", formData, function (data, textStatus) {
                    console.log(textStatus);
                });
            }
            // var data=$("img").attr("src");
            // var imageData = encodeURIComponent(data);
            // var formData = {
            //     imageName:"name.jpg",
            //     imageData: imageData
            // };

            // dataURL 的格式为 “data:image/png;base64,****”,逗号之前都是一些说明性的文字，我们只需要逗号之后的就行了
            // data=data.split(',')[1];
            // data=window.atob(data);
            // var ia = new Uint8Array(data.length);
            // for (var i = 0; i < data.length; i++) {
            //     ia[i] = data.charCodeAt(i);
            // }

            // canvas.toDataURL 返回的默认格式就是 image/png
            // var blob=new Blob([ia], {type:"image/png"});
            // var fd=new FormData();
            //
            // fd.append('file',blob);

        }

        $("#uploadFile").click(function () {
            upload();
        })
    };

    return {
        FileAPI: FileAPI
    };
});
