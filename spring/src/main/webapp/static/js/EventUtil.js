/**
 * Created by 王忠珂 on 2016/8/31.
 */

define([], function(){
    var EventUtil = {
        addHandler: function (element, type, handler) {
            if(element.addEventListener){
                element.addEventListener(type, handler, false);
            }else if(element.attachEvent){
                element.attachEvent("on" + type, handler);
            }else{
                element["on" + type] = handler;
            }
        },

        removeHandler: function (element, type, handler) {
            if(element.removeEventListener){
                element.removeEventListener(type, handler, false);
            }else if(element.detachEvent){
                element.detachEvent("on" + type, handler);
            }else{
                element["on" + type] = null;
            }
        },

        getEvent: function (event) {
            return event ? event : window.event;
        },

        getTarget: function (event) {
            return event.target || event.srcElement;
        },

        preventDefault: function (event) {
            if(event.preventDefault){
                event.preventDefault();
            }else{
                event.returnValue = false;
            }
        },

        stopPropagation: function (event) {
            if(event.stopPropagation){
                event.stopPropagation();
            }else{
                event.cancelBubble = true;
            }
        },

        getButton: function (event) {
            if(document.implementation.hasFeature("MouseEvents", "2.0")){
                return event.button;
            } else {
                switch (event.button){
                    case 0:
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                        return 0;
                    case 2 :
                    case 6:
                        return 2;
                    case 4:
                        return 1;
                }
            }

        },

        getCharCode: function (event) {
            if(typeof event.charCode == "number")
                return event.charCode;
            else{
                return event.keyCode;
            }
        },

        // 剪贴板
        getClipboardText: function (event) {
            var clipboardData = (event.clipboardData || window.clipboardData);
            return clipboardData.getData("text");
        },
        setClipboardText: function (event, value) {
            if(event.clipboardData){
                return event.clipboardData.setData("text/plain", value);
            }else if(window.clipboardData){
                return window.clipboardData("text", value);
            }
        },
        bind: function (fn, context) {
            return function () {
                return fn.apply(context, arguments);
            }
        },
        curryBind: function (fn, context) {
            var args = Array.prototype.slice.call(arguments, 2);
            return function () {
                var innerArgs = Array.prototype.slice.call(arguments);
                var finalArgs = args.concat(innerArgs);
                return fn.apply(context, finalArgs);
            }
        },
        /*setTimeoutTest(callback, 1000, {name:"wang"});*/
        setTimeout: function (fn, time) {
            var args = Array.prototype.slice.call(arguments, 2);
            setTimeout(function () {
                fn(args[0]);
                setTimeout(arguments.callee, time);
            }, time);
        },
        // 分时处理函数，防止执行时间过长，导致明显阻塞--Yielding Processes
        chunk: function (array, process, context) {
            setTimeout(function () {
                var item = array.shift();
                process.call(context, item);

                if(array.length > 0) {
                    setTimeout(arguments.callee, 100);
                }
            }, 100);
        }
    };

    return {
        EventUtil: EventUtil
    };
});
