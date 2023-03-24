//总库位数
// var countLocs = 226;
//是否在库位上展示库位号
var showLocationId = true;
$(function () {
    initColDesc();
    initLocDiv(1);
    init();
});
//两秒钟刷新一次
window.setInterval(init,5000);
function init() {
    initLocStatus();
}

function floorChange() {
    let floorNum = $('#floor').val();
    initColDesc();
    initLocDiv(floorNum);
    init();
}

//初始化列的描述
function initColDesc() {
    let colDescHtml = "";
    colDescHtml = colDescHtml + "<th></th>";
    for (let col = 1; col <= 26; col++) {
        let desc = col<10 ? "0"+col : col.toString();
        colDescHtml = colDescHtml + "<th>"+desc+"</th>";
        if(col == 2 || col == 12 || col == 21){
            colDescHtml = colDescHtml + "<th></th>";
        }
    }
    $('#head-desc').html(colDescHtml);
}

//画货位图，默认全是空货位
function initLocDiv(floorNum) {
    let locHtml = "";
    let rowDescClass = "row-desc-td";
    //一共30层高
    for (let row = 1; row <=32 ; row++) {
        locHtml = locHtml +"<tr>";
        let rowNum = row<10 ? '0'+row : row.toString();
        locHtml = locHtml + "<td class='"+rowDescClass+"'>"+rowNum+"</td>";
        //23+1列，第一列是标号
        for (let col = 1; col <= 26; col++) {
            let colStr = col<10 ? '0'+col : col.toString();
            let rowStr = row<10 ? '0'+row : row.toString();

            let locId = floorNum + rowStr + colStr;
            // alert(locId);
            let locDesc = floorNum + "楼，第"+row+"行，第"+col+"列";
            let tdClass = 'empty-loc';
            // if (1 <= row && row <= 2 && 4 <= col && col <= 10){
            //     tdClass = 'non-existent-loc';
            // }
            // if (row == 3 && 4 <= col && col <= 7){
            //     tdClass = 'non-existent-loc';
            // }
            // if (1 <= row && row <= 7 && 24 <= col && col <= 28){
            //     tdClass = 'non-existent-loc';
            // }
            // if ((row == 6 || row == 15 || row == 23)&& 3 <= col && col <= 28){
            //     tdClass = 'non-existent-loc';
            // }
            // if (row == 7 && 24 <= col && col <= 28){
            //     tdClass = 'non-existent-loc';
            // }
            // if ((row == 14 || row == 16 || row == 30)&& 25 <= col && col <= 28){
            //     tdClass = 'non-existent-loc';
            // }
            // if ((row == 22 || row == 24)&& 26 <= col && col <= 28){
            //     tdClass = 'non-existent-loc';
            // }
            if (1 <= row && row <= 7 && 22 <= col && col <= 26){
                tdClass = 'non-existent-loc';
            }

            if ((row == 30 || row == 31 || row == 32)&& 1 <= col && col <= 20){
                tdClass = 'non-existent-loc';
            }
            if (showLocationId){
                if (tdClass=='non-existent-loc'){
                    locHtml = locHtml + "<td id='"+locId+"' class='"+tdClass+"' onclick='showLocInfo("+locId+")' title='"+ locDesc+"'>"+" "+"</td>";
                }else{
                    locHtml = locHtml + "<td id='"+locId+"' class='"+tdClass+"' onclick='showLocInfo("+locId+")' title='"+ locDesc+"'>"+locId+"</td>";
                }
            }else{
                locHtml = locHtml + "<td id='"+locId+"' class='"+tdClass+"' onclick='showLocInfo("+locId+")' title='"+ locDesc+"'>&nbsp;</td>";
            }

            if(col == 2 || col == 12 || col == 21){
                locHtml = locHtml + "<td class='non-existent-loc'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>";
            }
        }
        locHtml = locHtml +"</tr>";
    }
    $('#floor-container').html(locHtml);
}

//展示详情模态框
function showLocInfo(locId,locDesc) {
    $("#loc-modal-pallet-no").text("");
    $("#loc-modal-goods-name").text("");
    $("#loc-modal-goods-code").text("");
    $("#loc-modal-batch-no").text("");
    $("#loc-modal-amount").text("");
    // 点击后弹出模态框
    let title = $("#"+locId).attr("title");
    $("#loc-modal-desc").text(locId+"("+title+")");
    //调用服务端接口获取货位详情
    $.ajax({
        type	:"get",
        url		: $.mpbGetHeaderPath()+"/location/detail/"+locId,
        headers:{'token':localStorage.getItem('token')},
        data    : {},
        dataType: "json",
        success	: function (data) {
            let info = data.data;
            //    给数据赋值
            $("#loc-modal-pallet-no").text(info.palletCode);
            $("#loc-modal-goods-name").text(info.goodsName);
            $("#loc-modal-goods-code").text(info.goodsCode);
            $("#loc-modal-batch-no").text(info.batchNo);
            $("#loc-modal-amount").text(info.amount);
            if(info.useStatus == '0'){
                $("#loc-modal-useStatus").text('可用');
            }else if(info.useStatus == '1'){
                $("#loc-modal-useStatus").text('入库中');
            }else if(info.useStatus == '2'){
                $("#loc-modal-useStatus").text('出库中');
            }else if(info.useStatus == '3'){
                $("#loc-modal-useStatus").text('占用');
            }else if(info.useStatus == '4'){
                $("#loc-modal-useStatus").text('异常');
            }
        },
        error	: function (e) {
            console.log(e);
        }
    });
    $("#myModal").modal('show');
}

//初始化货位状态
function initLocStatus() {
    // alert("init")
    $.ajax({
        type	:"post",
        url		: $.mpbGetHeaderPath()+"/location/allLocationList",
        headers:{'token':localStorage.getItem('token')},
        data    : {},
        dataType: "json",
        success	: function (data) {
            let dataList = data.data;
            let countEmpty = 0;
            let countUsed = 0;
            let countInLocked = 0;
            let countOutLocked = 0;
            let countDisabled = 0;
            let countError = 0;
            // alert("dataList.length: " + dataList.length);
            for (let i = 0; i < dataList.length; i++) {
                let locationObj = dataList[i];
                let locationCode = locationObj.locationCode;
                //使用状态 0可用1入库中2出库中3占用4异常
                let useStatus = locationObj.useStatus;
                let activeFlag = locationObj.activeFlag;

                $("#"+locationCode).removeClass();
                if ("0"==activeFlag){
                    $("#"+locationCode).addClass("disabled-div");
                    countDisabled++;
                }else if ("0"==useStatus){
                    $("#"+locationCode).addClass("empty-div");
                    countEmpty++;
                }else if ("1"==useStatus){
                    $("#"+locationCode).addClass("locked-in-div");
                    countInLocked++;
                }else if ("2"==useStatus){
                    $("#"+locationCode).addClass("locked-out-div");
                    countOutLocked++;
                }else if("3"==useStatus){
                    $("#"+locationCode).addClass("used-div");
                    countUsed++;
                }else if("4"==useStatus){
                    $("#"+locationCode).addClass("error-div");
                    countError++;
                }else{
                    console.log(locationObj);
                }
            }
        //    给数据赋值
            $("#empty-count").text(countEmpty);
            $("#used-count").text(countUsed);
            $("#locked-in-count").text(countInLocked);
            $("#locked-out-count").text(countOutLocked);
            $("#disabled-count").text(countDisabled);
            $("#error-count").text(countError);
        },
        error	: function (e) {
            console.log(e);
        }
    });

}