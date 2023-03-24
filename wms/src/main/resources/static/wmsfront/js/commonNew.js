commonNew = {//分页控件拼接代码
        PaGe: function (id) {
            var html = "";
            html += "<div class='pagination-con'>";
            html += "<p class='listNumber rt'>";
            html += "<button class='btn btn-default btn-xs previousPage'><i class='fa fa-caret-left fa-lg'></i></button>";
            html += "<span class='currentPage'>1</span>/<span class='pageNumber'></span>";
            html += "<button class='btn btn-default btn-xs nextPage'><i class='fa fa-caret-right fa-lg'></i></button>";
            html += "<input class='currentPageBtn' value='1' type='text'  onkeyup='this.value=this.value.replace(/[^0-9]+/g, '');'/>";
            html += "<button type='button' id=''  data-loading-text='请求数据中...' class='btn btn-default pageJump' autocomplete='off'>跳转</button>";
            html += "每页<span class='pageOneNumber'>10</span>条，共<span class='dataNumber'></span>条记录";
            html += "</p>";
            html += "</div>";
            $("#" + id).append(html);
        },
        //分页控件方法
        bindPage: function (pageDOM, pageNumber, dataNumber, callback) {
            var that = this;
            var pages = $("#" + pageDOM + "");
            pages.find(".pageNumber").text(pageNumber);
            pages.find(".dataNumber").text(dataNumber);
            pages.find(".previousPage").unbind().on("click", function () {
                if(parseInt(pages.find(".currentPage").text())==1){
                    return;
                }
                var currentPage=parseInt(pages.find(".currentPage").text())-(1);
                if(currentPage<1){
                    currentPage=1;
                }
                pages.find(".currentPage").text(currentPage);
                callback();
            });
            pages.find(".nextPage").unbind().on("click", function () {
                if(parseInt(pages.find(".currentPage").text())==pageNumber){
                    pages.find('.currentPage').text(pageNumber);
                    return;
                }
                var currentPage= parseInt(pages.find(".currentPage").text())-(-1);
                if(currentPage>pageNumber){
                    currentPage=pageNumber;
                }
                pages.find(".currentPage").text(currentPage);
                callback();
            });
            pages.find(".pageJump").unbind().on("click", function () {
                var currentPage = pages.find(".currentPageBtn").val();
                if(currentPage>pageNumber){
                    currentPage=pageNumber;
                }
                if(currentPage<1){
                    currentPage=1;
                }
                pages.find(".currentPage").text(currentPage);
                callback();
            })
        },
        //获取指定名称的cookie的值
        getcookie: function (objname) {
            var arrstr = document.cookie.split("; ");
            for (var i = 0; i < arrstr.length; i++) {
                var temp = arrstr[i].split("=");
                if (temp[0] == objname)
                    return decodeURI(temp[1]);
            }
        },
        /*获取url参数*/
        getQueryString: function (name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null)
                return decodeURI(r[2]);
            return null;
        }
}