var _buz_service,_frontBasic;
console.log(window.parent.operationData);

$(function(){
    _buz_service={
        //获取列表信息
        getDataList : function(data, resolve, reject){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/taskLog/listCount",
                headers:{'token':localStorage.getItem('token')},
                data    : data || {},
                dataType: "json",
                success	: resolve,
                error	: reject
            });
        },
    };
    _frontBasic = {
        data: {
            currentNumber   : 0,  //记录当前页有多少条数据
            totalPageNum    : 0,  //记录一共多少页
            numberOnePage   : 10 //一页几条数据
        },
        init: function () {
            this.onLoad();
            this.bindEvent();
        },
        onLoad: function () {
            this.loadBasicList();
            this.initDatetime();
        },
        bindEvent: function () {
            var _this = this;
            //分页前一页
            $(document).on('click', '.previousPage', function () {
                if(parseInt($('.currentPage').text())==1){
                    return;
                }
                var currentPage=parseInt($('.currentPage').text())-(1);
                if(currentPage<1){
                    currentPage=1;
                }
                $('.currentPage').text(currentPage);
                _this.loadBasicList();
            });
            //分页下一页
            $(document).on('click', '.nextPage', function () {
                if(parseInt($('.currentPage').text())==_this.data.totalPageNum){
                    $('.currentPage').text(_this.data.totalPageNum);
                    return;
                }
                var currentPage= parseInt($('.currentPage').text())-(-1);

                if(currentPage>_this.data.totalPageNum){
                    currentPage=_this.data.totalPageNum;
                }
                $('.currentPage').text(currentPage);
                _this.loadBasicList();
            });
            //分页跳转
            $(document).on('click', '#pageJump', function () {
                var currentPage = $('.currentPageBtn').val();
                if(currentPage>_this.data.totalPageNum){
                    currentPage=_this.data.totalPageNum;
                }
                if(currentPage<1){
                    currentPage=1;
                }
                $('.currentPage').text(currentPage);
                _this.loadBasicList();
            });
            //点击搜索
            $(document).on('click', '#refrechTop', function () {
                $(".currentPage").text("1");
                _this.loadBasicList();
            });
        },
        //渲染库存列表
        loadBasicList : function () {
            var _this = this;

            var gmtCreateMin = $.trim($('#createDate_begin').val());
            if(gmtCreateMin!=""&&gmtCreateMin!=null){
                gmtCreateMin = $.trim($('#createDate_begin').val()) + " 00:00:00";
            }
            var gmtCreateMax = $.trim($('#createDate_end').val());
            if(gmtCreateMax!=""&&gmtCreateMax!=null){
                gmtCreateMax = $.trim($('#createDate_end').val()) + " 23:59:59";
            }
            var info={
                goodsCode    : $.trim($('#goodsCode').val()),
                batchNo    : $.trim($('#batchNo').val()),
                gmtCreateMin  : gmtCreateMin ,
                gmtCreateMax  : gmtCreateMax ,
                currentPage     : $.trim($(".currentPage").text()),
                orderBy         : _this.data.orderBy,
                numberOnePage   : _this.data.numberOnePage
            };
            _buz_service.getDataList(info,function (res) {
                var result=res;
                console.log(res);
                if(result.code==0) {
                    if(result.data.totalNumber!=0){
                        var data=result.data.infoList;
                        _this.basicListSource(data);
                        console.log("_this.data.numberOnePage="+_this.data.numberOnePage);
                        if(result.data.totalNumber>_this.data.numberOnePage){
                            $(".pagination-con").show();
                            _this.data.totalPageNum=result.data.totalNumber;
                            _this.loadPagingNumber(_this.data.totalPageNum);
                        }
                        else {
                            $(".pagination-con").hide();
                        }
                    }
                    else {
                        $("#formTable tbody").empty();
                        $.DialogByZ.Autofade({Content: "查无数据！"});
                        $(".pagination-con").hide();
                    }
                }
                else {
                    $.DialogByZ.Autofade({Content: result.message ||  "查无数据！"});
                }
            },function(err){
                _commonFun.errorTips('');
            })
        },
        basicListSource : function(list) {
            $("#formTable tbody").empty();
            var tbody='';
            var userId = localStorage.getItem("userId");
            for(var ele in list){
                tbody+='<tr data-id="'+list[ele].customerId+'">' +
                    '<td>'+list[ele].goodsCode+'</td>'+
                    '<td>'+list[ele].goodsName+'</td>'+
                    '<td>'+list[ele].batchNo+'</td>'+
                    '<td>'+list[ele].inBoundCount+'</td>'+
                    '<td>'+list[ele].inBoundBoxCount+'</td>'+
                    '<td>'+list[ele].outBoundCount+'</td>'+
                    '<td>'+list[ele].outBoundBoxCount+'</td>'+
                    '<td>'+list[ele].outInBoundCount+'</td>'+
                    '<td>'+list[ele].outInBoundBoxCount+'</td>';
                tbody += '</tr>'
            }
            $("#formTable tbody").html(tbody);
        },
        //加载分页
        loadPagingNumber: function(num) {
            parseInt(num%this.data.numberOnePage)==0 ?
                this.data.totalPageNum=parseInt(num/this.data.numberOnePage) :
                this.data.totalPageNum=parseInt(num/this.data.numberOnePage)+1;

            $(".dataNumber").html(num);//共多少条
            $(".pageNumber").html(this.data.totalPageNum);//共多少页
            $(".pageOneNumber").text(this.data.numberOnePage);//每页几条
        },
        initDatetime :function  () {
            $(".datainp").jeDate({
                festival: false,
                ishmsVal: false,
                initAddVal:[0],
                format: "YYYY-MM-DD",
                zIndex: 3000
            });
        }

    };
    _frontBasic.init();

    //表头排序
    $(document).on('click', '#formTable thead tr th', function (e) {
        e.stopPropagation();
        var _self=$(this);
        var orderBy=_self.data('sort');

        if(_self.find('i').hasClass('fa-sort-amount-asc')){
            _self.parents("thead").find('i').removeClass("th-sort-active");
            _self.find('i').removeClass('fa-sort-amount-asc').addClass('fa-sort-amount-desc th-sort-active');
            _frontBasic.data.orderBy=orderBy + " desc";
            _frontBasic.loadBasicList();

        }
        else if(_self.find('i').hasClass('fa fa-sort-amount-desc')){
            _self.parents("thead").find('i').removeClass("th-sort-active");
            _self.find('i')
                .removeClass('fa-sort-amount-desc')
                .addClass('fa-sort-amount-asc th-sort-active');
            _frontBasic.data.orderBy=orderBy + " asc";
            _frontBasic.loadBasicList();
        }

    });
    //重置
    $("#reset").on("click",function () {
        $("#goodsCodePop").val("");
        $("#batchNoPoP").val("");
        $("#toLocationCodePoP").val("");
        $("#createDate_begin").val("");
        $("#createDate_end").val("");
    });
    //导出excel
    $("#exportExcel").on("click",function () {
        let goodsCode = $("#goodsCodePop").val();
        let batchNo = $("#batchNoPoP").val();
        var gmtCreateMin = $.trim($('#createDate_begin').val());
        if(gmtCreateMin!=""&&gmtCreateMin!=null &&gmtCreateMin!=undefined){
            gmtCreateMin = $.trim($('#createDate_begin').val()) + " 00:00:00";
        }
        var gmtCreateMax = $.trim($('#createDate_end').val());
        if(gmtCreateMax!=""&&gmtCreateMax!=null &&gmtCreateMax!=undefined){
            gmtCreateMax = $.trim($('#createDate_end').val()) + " 23:59:59";
        }
        window.location.href=$.mpbGetHeaderPath()+"/excel/turnover?goodsCode="+goodsCode+"&batchNo="+batchNo+"&gmtCreateMax="+gmtCreateMax+"&gmtCreateMin="+gmtCreateMin;
    });
    //confirmPerson chosen change事件
    $(document).on('change', '#confirmPerson', function (e){
        $("#confirmPerson").val($("#confirmPerson option:selected").attr("value"));
    });



});
//安全库存 数据校验
function checkStockNumber(that) {
    var stockNumber = $(that).val();
    if(_commonFun.isNotBlank(stockNumber)){
        //必须 大于0 且只能是整数
        if(stockNumber <= 0){
            _commonFun.errorTips("必须大于0");
            $(that).val(1);
            return false;
        }
        if(!/^[1-9]\d*$/.test(stockNumber)){
            _commonFun.errorTips("只能是正整数");
            $(that).val(parseInt(stockNumber)+1);
            return false;
        }
    }else{
        _commonFun.errorTips("不能为空");
        $(that).val(1);
        return false;
    }
}





