/**
 * ajax的二次封装
 */

(function () {
    /**
     * @param opts
     */
    function doAjax(opts) {
        this.url = opts.url;
        this.data = opts.data || {};
        // this.isShowLoad = opts.isShowLoad == undefined ? false : opts.isShowLoad;
        this.type = opts.type || "post";
        this.dataType = opts.dataType || "json";
        this.callBack = opts.callBack;
        this.async = opts.async || true;
        this.contentType = opts.contentType == undefined ? "application/x-www-form-urlencoded" : opts.contentType;
        this.processData = opts.processData == undefined ? true : opts.processData;
        this.error = opts.error || requestError;
        this.init();
    }
    //初始化
    doAjax.prototype.init = function () {
        this.getRequire();
    };
    //发送请求
    doAjax.prototype.getRequire = function () {
        var self = this;
        // if (self.isShowLoad)
        //     showWait(0);
        $.ajax({
                url: self.url,
                data: self.data,
                type: self.type ,
                dataType: self.dataType,
                beforeSend: function(xhr){xhr.setRequestHeader('token', localStorage.getItem('token'));},//这里设置header
                async: self.async,
                contentType: self.contentType,
                processData: self.processData,
                success: function (result) {
                    beforeResponse(result);
                    // result=JSON.parse(decodeURIComponent(result));
                    if (self.callBack) {
                        if ($.isFunction(self.callBack)) {
                            self.callBack(result);
                        } else {
                            console.log("callBack is not a function");
                        }
                    }
                },
                error: self.error
            }
        );
    };
    window.doAjax = doAjax;
    return this;
})();

/**
 * 请求错误的统一处理
 * @param jqXHR
 * @param textStatus
 * @param errorThrown
 */
function requestError(jqXHR, textStatus, errorThrown) {
    /*弹出jqXHR对象的信息*/
    // console.log(jqXHR.responseText);
    console.log(jqXHR.status);
    // console.log(jqXHR.readyState);
    // console.log(jqXHR.statusText);
    // /*弹出其他两个参数的信息*/
    console.log(textStatus);
    console.log(errorThrown);
}
/**
 * 执行回调函数前的统一处理
 * @param result
 */
function beforeResponse(result) {
    // result=JSON.parse(decodeURIComponent(result));
    if(result.code==0118){
        console.log(result.message);
        location.href="login.html";
    }
}
// function doLogin() {
//     var user_name = $("#input_userName").val();
//     var pass_word = $("#input_pwd").val();
//     new doAjax({
//         url: '/skye/doLogin.php',
//         data: {
//             user_name: user_name,
//             pass_word: pass_word
//         },
//         callBack: function (json) {
//
//         }
//     });
// }
