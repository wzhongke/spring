/**
 * Created by admin on 2016/11/1.
 */
var zip_dragFile = {
    fileInput: null,    //html file控件
    dragDrop: null, //拖拽敏感区域
    upButton: null, //提交按钮
    url: "",    //ajax地址
    fileFilter: [], //过滤后的文件数组
    filter: function(files) {   //选择文件组的过滤方法
        return files;
    },
    // onSelect: function() {},    //文件选择后
    // onDelete: function() {},    //文件删除后
    // onDragOver: function() {},  //文件拖拽到敏感区域时
    // onDragLeave: function() {}, //文件离开到敏感区域时
    // onProgress: function() {},  //文件上传进度
    // onSuccess: function() {},   //文件上传成功时
    // onFailure: function() {},   //文件上传失败时,
    // onComplete: function() {},  //文件全部上传完毕时

    init: function() {
        if ( typeof FileReader === 'undefined' ) {
            alert( " 您的浏览器未实现 FileReader 接口 " );
        } else {
            var reader = new FileReader();
            // do sth.
        }

        var self = this;
        if (this.dragDrop) {
            this.dragDrop.addEventListener("dragover", function(e) { self.funDragHover(e); }, false);
            this.dragDrop.addEventListener("dragleave", function(e) { self.funDragHover(e); }, false);
            this.dragDrop.addEventListener("drop", function(e) { self.funGetFiles(e); }, false);
        }
        //文件选择控件选择
        if (this.fileInput) {
            this.fileInput.addEventListener("change", function(e) { self.funGetFiles(e); }, false);
        }
        //上传按钮提交
        if (this.upButton) {
            this.upButton.addEventListener("click", function(e) { self.funUploadFile(e); }, false);
        }
    },

//文件拖放
    funDragHover: function(e) {
        e.stopPropagation();
        e.preventDefault();
        this[e.type === "dragover"? "onDragOver": "onDragLeave"].call(e.target);
        return this;
    },
//获取选择文件，file控件或拖放
    funGetFiles: function(e) {
        // 取消鼠标经过样式
        this.funDragHover(e);
        // 获取文件列表对象
        var files = e.target.files || e.dataTransfer.files;
        //继续添加文件
        this.fileFilter = this.fileFilter.concat(this.filter(files));
        this.funDealFiles();
        return this;
    },

    filter: function(files) {
        var arrFiles = [];
        for (var i = 0, file; file = files[i]; i++) {
            if (file.type.indexOf("image") == 0 || (!file.type && /\.(?:jpg|png|gif)$/.test(file.name) /* for IE10 */)){
                if (file.size >= 512000) {
                    alert('您这张"'+ file.name +'"图片大小过大，应小于500k');
                } else {
                    arrFiles.push(file);
                }
            } else {
                alert('文件"' + file.name + '"不是图片。');
            }
        }
        return arrFiles;
    },
    //选中文件的处理与回调
    funDealFiles: function() {
        for (var i = 0, file; file = this.fileFilter[i]; i++) {
            //增加唯一索引值
            file.index = i;
        }
        //执行选择回调
        this.onSelect(this.fileFilter);
        return this;
    },
    onSelect: function(files) {
        var html = '', i = 0;
        $("#preview").html('<div class="upload_loading"></div>');
        var funAppendImage = function() {
            file = files[i];
            if (file) {
                var reader = new FileReader()
                reader.onload = function(e) {
                    html = html + '<div id="uploadList_'+ i +'" class="upload_append_list"><p><strong>' + file.name + '</strong>'+
                        '<a href="javascript:" class="upload_delete" title="删除" data-index="'+ i +'">删除</a><br />' + '<img id="uploadImage_' + i + '" src="' + e.target.result + '"class="upload_image" /></p>'+
                    '<span id="uploadProgress_' + i + '" class="upload_progress"></span>' +
                    '</div>';
                    i++;
                    funAppendImage();
                };
                reader.readAsDataURL(file);
            } else {
                $("#preview").html(html);
                if (html) {
                    //删除方法
                    $(".upload_delete").click(function() {
                        zip_dragFile.funDeleteFile(files[parseInt($(this).attr("data-index"))]);
                        return false;
                    });
                    //提交按钮显示
                    $("#fileSubmit").show();
                } else {
                    //提交按钮隐藏
                    $("#fileSubmit").hide();
                }
            }
        };
        funAppendImage();
    },

    //删除对应的文件
    funDeleteFile: function(fileDelete) {
        var arrFile = [];
        for (var i = 0, file; file = this.fileFilter[i]; i++) {
            if (file != fileDelete) {
                arrFile.push(file);
            } else {
                this.onDelete(fileDelete);
            }
        }
        this.fileFilter = arrFile;
        return this;
    },
    onDelete: function(file) {
        $("#uploadList_" + file.index).fadeOut();
    },
    onDragOver: function() {
        $(this).addClass("upload_drag_hover");
    },
    onDragLeave: function() {
        $(this).removeClass("upload_drag_hover");
    },
    //文件上传
    funUploadFile: function() {
        var self = this;
        for (var i = 0, file; file = this.fileFilter[i]; i++) {
            (function(file) {
                var xhr = new XMLHttpRequest();
                if (xhr.upload) {
                    // 上传中(h5的upload对象监听传输事件)
                    xhr.upload.addEventListener("progress", function(e) {
                        self.onProgress(file, e.loaded, e.total);
                    }, false);
                    // 文件上传成功或是失败
                    xhr.onreadystatechange = function(e) {
                        if (xhr.readyState == 4) {
                            if (xhr.status == 200) {
                                self.onSuccess(file, xhr.responseText);
                                self.funDeleteFile(file);
                                if (!self.fileFilter.length) {
                                    //全部完毕
                                    self.onComplete();
                                }
                            } else {
                                self.onFailure(file, xhr.responseText);
                            }
                        }
                    };

                    // 开始上传
                    xhr.open("POST", self.url, true);
                    xhr.setRequestHeader("X_FILENAME", file.name);
                    xhr.send(file);
                }
            })(file);
        }
    },
    onProgress: function(file, loaded, total) {
        var eleProgress = $("#uploadProgress_" + file.index),
            percent = (loaded / total * 100).toFixed(2) + '%';
        eleProgress.show().html(percent);
    },
    onSuccess: function(file, response) {
        $("#uploadInf").append("<p>上传成功，图片地址是：" + response + "</p>");
    },
    onFailure: function(file) {
        $("#uploadInf").append("<p>图片" + file.name + "上传失败！</p>");
        $("#uploadImage_" + file.index).css("opacity", 0.2);
    },
    onComplete: function() {
        //提交按钮隐藏
        $("#fileSubmit").hide();
        //file控件value置空
        $("#fileImage").val("");
        $("#uploadInf").append("<p>当前图片全部上传完毕，可继续添加上传。</p>");
    }
};

