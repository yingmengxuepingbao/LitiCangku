

/**
 * javawebframework system fix by zhangx
 * rights reserved.
 * @created 2018/09/19
 * 用于解析界面table
 * options{url:查询的url地址,
 * 		   queryParams:传输参数,
 * 		   columns:[]展示字段，json字符串
 * 		   callback：回调函数 数据加载完后展示的数据
 *        }
 *	更改为兼容两种模式
 */

(function($){

    var optionsParams;
    /*firefox*/
    function __firefox(){
        HTMLElement.prototype.__defineGetter__("runtimeStyle", __element_style);
        window.constructor.prototype.__defineGetter__("event", __window_event);
        Event.prototype.__defineGetter__("srcElement", __event_srcElement);
    }
    function __element_style(){
        return this.style;
    }
    function __window_event(){
        return __window_event_constructor();
    }
    function __event_srcElement(){
        return this.target;
    }
    function __window_event_constructor(){
        if(document.all){
            return window.event;
        }
        var _caller = __window_event_constructor.caller;
        while(_caller!=null){
            var _argument = _caller.arguments[0];
            if(_argument){
                var _temp = _argument.constructor;
                if(_temp.toString().indexOf("Event")!=-1){
                    return _argument;
                }
            }
            _caller = _caller.caller;
        }
        return null;
    }
    if(window.addEventListener){
        __firefox();
    }
    /*end firefox*/

    document.oncontextmenu = function (ev) {
        ev.preventDefault();
    }


    /* *
     * 表格头部右击事件
     * */
    $.fn.thclick = function(tableId){
        var op = optionsParams[tableId];
        var $columns = op.columns;
        if(event.stopPropagation) {
            event.stopPropagation()
        } else {
            event.cancelBubble = true;
        }

        var x = event.pageX;
        var y = event.pageY;
        if($(window).width()-x <100)x=x-100;
        var $meng = $('<div></div>');
        $("body").append($meng).css({"position":"relative"});
        $meng.css({"width":"100%","height":"100%","position":"absolute","left":"0","top":"0","z-index":"9999"});

        //生成列表盒子
        var $listbox = $("<ul></ul>");
        if($columns)
        {
            $columns.forEach(function(d){
                if(d.title){
                    if(d.hidden){
                        $listbox.append($("<li><input name='tableinput' value='"+ d.field +"' type='checkbox'><span>"+ d.title +"</span></li>"));
                    }else{
                        $listbox.append($("<li><input name='tableinput' value='"+ d.field +"' type='checkbox' checked><span>"+ d.title +"</span></li>"));
                    }
                }

            });
        }
        $meng.append($listbox);
        $listbox.css({"position":"absolute","left":x+"px","top":y+"px","background":"#fff","padding":"10px","border":"1px solid #ccc","list-style":"none"});

        $listbox.on("click",function(e){
            e.stopPropagation();
        });

        $meng.one("click",function(){
            var $this = this;
            var chk_value =[];
            $('input[name="tableinput"]:checked').each(function(){
                chk_value.push($(this).val());
            });
            if(chk_value.length==0){
                alert("你还没有选择任何内容！");
            }
            if($columns)
            {
                var col_new = [];
                $columns.forEach(function(d){
                    if(!d.title){
                        col_new.push(d);
                    }else{
                        if($.inArray(d.field, chk_value)!=-1){
                            d["hidden"]=false;
                            col_new.push(d);
                        }else{
                            d["hidden"]=true;
                            col_new.push(d);
                        }
                    }
                });
                op.columns =col_new;
                $('#'+tableId).tableDatagrid(op);
            }
            $this.remove();
        });

    }

    $.fn.columnclick = function(tableId,field){
        var op = optionsParams[tableId];
        var $this = this;
        var sort = "desc";
        if($($this).hasClass('fa-caret-up')){
            sort = "asc";
        }
        //如果已经存在了就按存在的值
        var orderField = field;
        if(orderField=="createDateStr"){
            orderField = "createDate";
        }
        if(orderField =="updateDateStr"){
            orderField = "updateDate";
        }
        if(op.queryParams.orderField){
            op.queryParams.orderField = orderField;
            op.queryParams.sort = sort;

        }else{

            var _queryParams={queryParams:{orderField:orderField,sort:sort}};
            if(op.queryParams){
                $.extend(true,op,_queryParams);
            }
        }
        console.log('当发生排序按钮点击，pageNo置0');
        op.queryParams.pageNo = 0;
        $('#'+tableId).tableDatagrid(op);
    }

    $.fn.tableDatagrid = function(options) {
        // console.log('grid入参',options);
        var tableId = this.get(0).id;
        if(optionsParams){
            optionsParams[tableId]=options;
        }else{
            var map_ = new Object();
            map_[tableId]= options;
            optionsParams = map_;
        }
        //总条数
        var totalCount = 0;
        //是否多选
        var multiselect = true;

        //每页条数
        var everyPageSize = $.calculatedHeight();
        //当前页码
        var thePageNum = "";

        var params = options || {};
        //判断是否是点的查询按钮
        if(!params.type){
            thePageNum = "";
            everyPageSize = $.calculatedHeight();
        }
        //判断是否可以多选
        if(!params.mutiselect){
            multiselect = false;
        }
        if(params.queryParams.pageNo){
            if(""!=params.queryParams.pageNo&&params.queryParams.pageNo>0){
                thePageNum = (parseInt(params.queryParams.pageNo)-1)+"";
            }
        }
        //如果已经存在了就按存在的值
        if(params.queryParams.pageSize){
            everyPageSize = params.queryParams.pageSize;
        }
        //end
        if(params.pager){
            var _queryParams={queryParams:{pageNo:thePageNum,
                    pageSize:everyPageSize}
            };
            if(params.queryParams){
                $.extend(true,params,_queryParams);
            }
        }
        var requestType='GET';
        if (params.requestType) {
            requestType = params.requestType;
        }
        // console.log('requestType',requestType);
        var dataType='text';
        if (params.dataType) {
            dataType = params.dataType;
        }
        // console.log('dataType',dataType);
        if(!params.queryParams.pageNo){
            params.queryParams.pageNo = 0;
            // console.log('params.queryParams',params.queryParams);
        }
        $.ajax({
            type:requestType,
            dataType:dataType,
            data : params.queryParams,
            url:params.url,
            success:function(data){
                var decodeData = decodeURIComponent(data);
                // console.log('decodeData',decodeData);
                //移除起始的"
                decodeData = decodeData.substr(1,decodeData.length-2);
                var fromData=JSON.parse(decodeData);
                // console.log('fromData',fromData);
                data = new Object();
                data.success = fromData.code=='0'?true:false;
                // console.log('对象转换之后',data);
                data.message = fromData.message;
                if(data.success){
                    data.pageNo = fromData.data.pageNo;
                    data.pageSize = fromData.data.pageSize;
                    data.totalCount = fromData.data.totalCount;
                    data.dataList = fromData.data.dataList;
                    totalCount = data.totalCount;
                    var table = "";
                    var select="";
                    var tableList = data.dataList;
                    //flagMaterialType = data.materialType;
                    /*if($("#"+tableId).find("thead").length>0){
                        table = table + "<thead>" + $("#"+tableId+" thead").html() + "</thead>";
                    }else{*/
                    var $columns = params.columns;

                    table = table + "<thead ><tr class='theadThTop'>"
                    var $orderField = params.queryParams.orderField;
                    var $sort = params.queryParams.sort;
                    if(params.rownumbers){
                        table = table +"<th scope='col'>&nbsp;</th>";
                    }
                    $.each($columns, function(idx, obj) {
                        if(obj.checkbox){
                            //table = table +"<th scope='col'><input type='hidden' id='th_"+obj.field+"' ><input type='checkbox'/></th>";
                            table = table +"<th scope='col'><span onclick='$(this).thclick("+JSON.stringify(tableId)+")' style='background:url(../../images/icon/filter3.png);background-size:100%;' class='table_filter3'></span></th>";
                        }else{
                            if(!obj.hidden){
                                if(!obj.noOrder){
                                    if($orderField){
                                        if($orderField==obj.field||($orderField=="createDate"&&obj.field=="createDateStr")||($orderField=="updateDate"&&obj.field=="updateDateStr")){
                                            if($sort=="asc"){
                                                table = table +"<th scope='col' style='padding-right:20px;position:relative;' ><span class='fa fa-caret-up table_theader_paixu' style='color:#000;' onclick='$(this).columnclick("+JSON.stringify(tableId)+","+JSON.stringify(obj.field)+")'></span><span class='fa fa-caret-down table_theader_paixu_down' onclick='$(this).columnclick("+JSON.stringify(tableId)+","+JSON.stringify(obj.field)+")'></span>"+obj.title+"</th>";
                                            }else{
                                                table = table +"<th scope='col' style='padding-right:20px;position:relative;' ><span class='fa fa-caret-up table_theader_paixu' onclick='$(this).columnclick("+JSON.stringify(tableId)+","+JSON.stringify(obj.field)+")'></span><span class='fa fa-caret-down table_theader_paixu_down' style='color:#000;' onclick='$(this).columnclick("+JSON.stringify(tableId)+","+JSON.stringify(obj.field)+")'></span>"+obj.title+"</th>";
                                            }
                                        }else{
                                            table = table +"<th scope='col' style='padding-right:20px;position:relative;' ><span class='fa fa-caret-up table_theader_paixu' onclick='$(this).columnclick("+JSON.stringify(tableId)+","+JSON.stringify(obj.field)+")'></span><span class='fa fa-caret-down table_theader_paixu_down' onclick='$(this).columnclick("+JSON.stringify(tableId)+","+JSON.stringify(obj.field)+")'></span>"+obj.title+"</th>";
                                        }
                                    }else{
                                        table = table +"<th scope='col' style='padding-right:20px;position:relative;' ><span class='fa fa-caret-up table_theader_paixu' onclick='$(this).columnclick("+JSON.stringify(tableId)+","+JSON.stringify(obj.field)+")'></span><span class='fa fa-caret-down table_theader_paixu_down' onclick='$(this).columnclick("+JSON.stringify(tableId)+","+JSON.stringify(obj.field)+")'></span>"+obj.title+"</th>";
                                    }
                                }else{
                                    table = table +"<th scope='col' style='padding-right:20px;position:relative;' >"+obj.title+"</th>";
                                }


                            }
                        }
                    });
                    table = table + "</tr>";
                    table = table + "</thead>";


                    //}

                    table = table + "<tbody>";
                    if(tableList.length>0){
                        for(var x=0;x<tableList.length;x++){
                            if(tableList[x].id!=null){
                                table = table +"<tr id="+tableList[x].id+" onclick='$.colChange(id,0,"+multiselect+",\""+tableId+"\")'>";
                            }else{
                                table = table +"<tr id="+tableList[x].ID+" onclick='$.colChange(id,0,"+multiselect+",\""+tableId+"\")'>";
                            }

                            if(params.rownumbers){
                                table = table +"<td align='left' class='tableIndex'></td>";
                            }
                            $.each($columns, function(idx, obj) {

                                if(obj.checkbox){
                                    table = table +"<td style='width:22px;'><input id="+"checkboxId"+tableList[x][obj.field]+" type='checkbox' onclick='$.colChange(id,1,"+multiselect+",\""+tableId+"\")'><input type='hidden' class='id' value='"+tableList[x][obj.field]+"'></td>";
                                }else{
                                    table = table +"<td align='left' id='td_"+obj.field+"'"
                                    //如果有className设置className属性
                                    if(obj.className){
                                        table = table + " class='"+obj.className+"' ";
                                    }
                                    //如果是hidden隐藏掉
                                    if(obj.hidden){
                                        table = table + " hidden ";
                                    }
                                    table = table +">";
                                    //如果有格式化函数 处理格式化函数返回值
                                    if(obj.formatter){
                                        if($.isFunction(obj.formatter)){
                                            table = table + obj.formatter(tableList[x][obj.field],tableList[x]);
                                        }
                                    }else{
                                        if(typeof(tableList[x][obj.field]) == "undefined"){
                                            table = table +"";
                                        }else{
                                            table = table +tableList[x][obj.field];
                                        }

                                    }
                                    table = table +"</td>"
                                }
                            });


                            table = table +"</tr>";
                        }
                        //------------------------------------------------------------
                        table = table + "</tbody>";

                        var e=new RegExp("undefined","g");
                        table = table.replace(e, "");
                    }else{
                        table = table + "<tr><td colspan='22' style='vertical-align:middle; text-align:center;'>查询无结果！</td></tr></tbody>";
                    }

                    $("#"+tableId).html(table)

                    if(options && options.fixedTitle==true){
                        //头部固定 盒子和表头
                        if($(".fixed_to_top").length){
                            $(".fixed_to_top").remove();
                        }
                        var bablebox_thead = $("<div class='fixed_to_top'></div>").append($("<div class='fixed_talbe_box'><table class='table'>"+table+"</table></div>"));
                        $("body").append(bablebox_thead);
                        /* *
                         * 页面滚动表格头部显示
                         * */
                        $(document).on("scroll",function(){
                            var bodyTop = $(document).scrollTop();
                            var tableTop = $("#"+tableId).offset().top;
                            if(bodyTop >= tableTop)
                            {
                                $(".fixed_to_top").show();
                            }
                            else
                            {
                                $(".fixed_to_top").hide();
                            }
                        });
                        $("#"+tableId).parent().on("scroll",function(){
                            //console.log($(this).scrollLeft())
                            //console.log($(".fixed_to_top"))
                            $(".fixed_to_top div.fixed_talbe_box").scrollLeft($(this).scrollLeft());
                        })
                    }


                }else{
                    // alert(data);
                    alert(data.message);
                    return false;
                }
                if(""==thePageNum){
                    if(params.pager){
                        $.getPage(thePageNum,params,totalCount,tableId);
                    }
                }
                if(params.rownumbers){
                    $.showTableIndex(tableId,thePageNum,params.queryParams.pageSize);
                }

                //回调函数
                if(params.callback){
                    if($.isFunction(params.callback)){
                        params.callback();
                    }
                }
            }
        });
    }

    //页面表格点击行变色，点击checkbox变色，再次点击取消
    $.colChange = function(id,flag,multiselect,tableId) {
        if(id.indexOf("checkboxId")!= -1){
            id=id.replace("checkboxId","");
        }
        var checkobj=$("#"+tableId).find("#checkboxId"+id);
        var obj = $("#"+tableId).find("#"+id);

        if(flag==0){
            if(checkobj.is(":checked")){
                checkobj.prop("checked",false);
                obj.removeClass("table-tr-active");
            }else{
                if(!multiselect){
                    obj.siblings().removeClass("table-tr-active");
                    obj.parent().find("input[type=checkbox]").prop("checked",false);
                }

                checkobj.prop("checked",true);
                obj.addClass("table-tr-active");
            }
        }else{
            if(checkobj.is(":checked")){
                checkobj.prop("checked",false);
                obj.removeClass("table-tr-active");
            }else{
                checkobj.prop("checked",true);
                obj.addClass("table-tr-active");
            }
        }
    }

    //分页获取记录
    $.getPage = function(pageNum,options,totalCount,tableId){
        var pager = options.pager;
        var everyPageSize = options.queryParams.pageSize;
        var thePageNum = options.queryParams.pageNo;

        var _options = {type:"pagination"};
        var options = $.extend(true,options,_options);

        var pageHtml = "<select id='everyPageSize' style='height:25px;width:50px;text-align: center;border-radius: 5px;' value='"+everyPageSize+"'>"+
            "<option id='pageSize_5'>5</option><option id='pageSize_10'>10</option><option id='pageSize_20'>20</option><option id='pageSize_40'>40</option><option id='pageSize_60'>60</option><option id='pageSize_80'>80</option></select>条&nbsp;&nbsp;";


        var totalPage = 1;
        if(""!=pageNum && undefined!=pageNum){
            thePageNum = pageNum;
            //每页显示条数
            everyPageSize =  $("#"+pager+" #everyPageSize option:selected").text();
            var pageSizeCheckedId = $("#"+pager+" #everyPageSize option:selected").attr("id");
            //总页数
            if(totalCount == 0){
                totalPage = 1;
            }else{
                if(totalCount%everyPageSize==0){
                    totalPage = totalCount/everyPageSize
                }else{
                    totalPage = (totalCount-(totalCount%everyPageSize))/everyPageSize + 1
                }
            }
            var thisPageNum = parseInt(pageNum);
            if(thisPageNum!=1){
                pageHtml = pageHtml + "&nbsp;<a id='First'>[首页]</a><a id='Prev' >&lt; 前一页</a>"
            }
            if(totalPage<=5){
                for(var i=1;i<=totalPage;i++){
                    pageHtml = pageHtml + "<a class='pageNum' id='pageNum_"+i+"'>"+i+"</a>"
                }
            }else{
                if(pageNum<=3){
                    for(var i=1;i<=5;i++){
                        pageHtml = pageHtml + "<a class='pageNum' id='pageNum_"+i+"'>"+i+"</a>"
                    }
                    pageHtml = pageHtml + "<b>...</b>"
                }else{
                    if(totalPage<=thisPageNum+2){
                        pageHtml = pageHtml + "<b>...</b>"
                        for(var i=totalPage-4;i<=totalPage;i++){
                            pageHtml = pageHtml + "<a class='pageNum' id='pageNum_"+i+"'>"+i+"</a>"
                        }
                    }else{
                        pageHtml = pageHtml + "<b>...</b>";
                        for(var i=thisPageNum-2;i<=thisPageNum+2;i++){
                            pageHtml = pageHtml + "<a class='pageNum' id='pageNum_"+i+"'>"+i+"</a>"
                        }
                        pageHtml = pageHtml + "<b>...</b>";
                    }
                }
            }
            if(thisPageNum!=totalPage){
                pageHtml = pageHtml + "<a id='Next' >后一页 &gt;</a><a id='Last' >[尾页]</a>"
            }
            pageHtml = pageHtml + "&nbsp; 共<span id=totalPage>"+totalPage+"</span>页&nbsp; 共<span id=totalCount>"+totalCount+"</span>条&nbsp; 跳到<input name='currPage' type='text' value='"+pageNum+"' maxlength='10'  id='currPage' >页&nbsp;&nbsp;<a id='goToPage'>跳转</a> "
            $("#"+pager).html(pageHtml);
            $("#"+pager+" #pageNum_"+thisPageNum).addClass(" chosed");
            $("#"+pager+" #everyPageSize #"+pageSizeCheckedId).attr("selected","selected");
            //首页，前一页。后一页，尾页
            $("#"+pager+" #First").on("click",function(){
                $.getPage("1",options,totalCount,tableId);
            });
            $("#"+pager+" #Prev").on("click",function(){
                $.getPage((thisPageNum-1)+"",options,totalCount,tableId);
            });
            $("#"+pager+" #Next").on("click",function(){
                $.getPage((thisPageNum+1)+"",options,totalCount,tableId);
            });
            $("#"+pager+" #Last").on("click",function(){
                $.getPage(totalPage+"",options,totalCount,tableId);
            });
            try {

                options.queryParams.pageNo = thePageNum;
                options.queryParams.pageSize = everyPageSize;
                $('#'+tableId).tableDatagrid(options);
            } catch (e){
                console.log(e);
            }

        }else{
            //总页数
            totalPage = 1;
            if(totalCount == 0){
                totalPage = 1;
            }else{
                if(totalCount%everyPageSize==0){
                    totalPage = totalCount/everyPageSize
                }else{
                    totalPage = (totalCount-(totalCount%everyPageSize))/everyPageSize + 1
                }
            }
            if(totalPage<=5){
                for(var i=1;i<=totalPage;i++){
                    pageHtml = pageHtml + "<a class='pageNum' id='pageNum_"+i+"'>"+i+"</a>"
                }
            }else{
                for(var i=1;i<=5;i++){
                    pageHtml = pageHtml + "<a class='pageNum' id='pageNum_"+i+"'>"+i+"</a>"
                }
                pageHtml = pageHtml + "..."
            }
            if("1"!=totalPage){
                pageHtml = pageHtml + "<a id='Next' >后一页 &gt;</a><a id='Last' >[尾页]</a>"
            }
            pageHtml = pageHtml + "&nbsp; 共<span id=totalPage>"+totalPage+"</span>页&nbsp; 共<span id=totalCount>"+totalCount+"</span>条&nbsp; 跳到<input name='currPage' type='text' value='1' maxlength='10'  id='currPage' >页&nbsp;&nbsp;<a id='goToPage'>跳转</a> "
            $("#"+pager).html(pageHtml);
            $("#"+pager+" #pageNum_1").addClass(" chosed");
//	  		$("#"+pager+" #everyPageSize #pageSize_20").attr("selected","selected");
            $("#"+pager+" #everyPageSize #pageSize_"+everyPageSize).attr("selected","selected");
            //后一页，尾页
            $("#"+pager+" #Next").on("click",function(){
                $.getPage("2",options,totalCount,tableId);
            });
            $("#"+pager+" #Last").on("click",function(){
                $.getPage(totalPage+"",options,totalCount,tableId);
            });
        }
        $("#"+pager+" .pageNum").on("click",function(){
            $("#"+pager+" .pageNum").removeClass(" chosed");
            $(this).addClass(" chosed");
            $.getPage($(this).text(),options,totalCount,tableId);
        });
        //跳转第几页
        $("#"+pager+" #goToPage").on("click",function(){
            var goToPage = $("#"+pager+" #currPage").val();
            var e=goToPage.replace(/[^0-9]/ig,"");
            var goToPageNum = parseInt(e);
            if(goToPageNum == 0){
                goToPageNum = 1;
            }
            if(""!=goToPage && undefined!=goToPage && goToPageNum<=totalPage){
                $.getPage(goToPageNum+"",options,totalCount,tableId);
            }else{
                $("#"+pager+" #currPage").val("");
                alert("您输入的页码有误，请重新输入！");
            }
        });
        $("#"+pager+" #everyPageSize").on("change",function(){
            $.getPage("1",options,totalCount,tableId);
        });
    }

    $.showTableIndex = function(_tableId,thePageNum,everyPageSize){
        var index = 0;
        var thePageNumInt = 0;
        if(""!=thePageNum){
            thePageNumInt = parseInt(thePageNum+"");
        }
        for(var i=(thePageNumInt*everyPageSize+1);i<=((thePageNumInt+1)*everyPageSize);i++){
            $("#"+_tableId+" .tableIndex:eq("+index+")").text(i);
            index++;
        }
    }

    /*
     自动计算屏幕高度
     * */
    $.calculatedHeight = function(){
        var winHeight = $(window).height();
        if(winHeight>800)
        {
            return 20;
        }
        else
        {
            return 10;
        }
    }
})(jQuery)
