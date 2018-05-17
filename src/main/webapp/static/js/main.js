/**
 * Created by 王忠珂 on 2016/9/2.
 */
requirejs.config({
    paths:{
        jquery: 'jquery-1.11.1.min'
    }
});

requirejs(['jquery', 'handleFile'], function ($, handleFile) {

   handleFile.FileAPI();

});