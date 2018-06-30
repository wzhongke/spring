/**
 * Created by admin on 2016/11/3.
 */
define(['jquery'], function($){
    function Upload(opts){
        this.opts = $.extend({}, Upload.DEFAULTS, opts);
        var file = this.opts.file;
        if (file.type != "image/png" && file.type != "image/jpg" && file.type !="image/gif" && file.type != "image/jpeg") {
            alert("The file does not match png, jpg or gif");
        }
        this.upload();
    }
    Upload.DEFAULTS = {
        url : "",
        timeout: 5000,
        file:""
    };

    Upload.prototype.upload = function() {
        var formData = new FormData();
        $.ajax({
            type: "POST",
            url: this.opts.url,
            data: this.opts.file,
            timeout: this.opts.timeout,
            fromData: formData,
            success: function () {
                alert("success");
            },
            error: function () {
                alert("failed");
            }
        });
    };

    return {
        UploadFile: Upload
    };
});