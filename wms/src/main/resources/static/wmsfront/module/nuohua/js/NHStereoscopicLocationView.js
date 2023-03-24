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

function layersChange() {
    let layersNum = $('#layers').val();
    initColDesc();
    initLocDiv(layersNum);
    init();
}

//初始化列的描述
function initColDesc() {
    let colDescHtml = "";
    colDescHtml = colDescHtml + "<th>X&#47;Y</th>";
    //Y:横着 （1-6）
    for (let row = 1; row <=6 ; row++){
        let desc = row<10 ? "0"+row : row.toString();
        colDescHtml = colDescHtml + "<th>"+desc+"</th>";
    }
    $('#head-desc').html(colDescHtml);
}

//画货位图，默认全是空货位 （）
function initLocDiv(layersNum) {
    let locHtml = "";
    let rowDescClass = "row-desc-td";
    //横处理 （1-27）
    for (let col = 1; col <= 27; col++){
        locHtml = locHtml +"<tr>";
        let rowNum = col<10 ? '0'+col : col.toString();
        locHtml = locHtml + "<td class='"+rowDescClass+"'>"+rowNum+"</td>";
        //纵处理
        for (let row = 1; row <=6 ; row++){
            let colStr = col<10 ? '0'+ col : col.toString();
            let rowStr = row<10 ? '0'+ row : row.toString();
            //层处理
            let layersNumN=layersNum<10 ? '0'+layersNum : layersNum.toString();
            let locId =  colStr + rowStr + layersNumN;
            let locDesc = "第"+col+"行，第"+row+"列，第" +layersNum + "层";
            let tdClass = 'empty-loc';
            if(layersNum ==1){
                if(col == 1 && (row==4 ||row==5 ||row==6)){
                    tdClass = 'non-existent-loc';
                }
            }
            if((1<=col &&col<=6)&&(1<=row && row<=3)){
                tdClass = 'non-existent-loc';
            }
            if(( 7<=col && col <= 14)&&(row == 2)){
                tdClass = 'non-existent-loc';
            }
            if(col == 10 &&(1<=row &&row<=3)){
                tdClass = 'non-existent-loc';
            }
            //横着15 置空
            if(col == 15){
                tdClass = 'non-existent-loc';
            }

            if (showLocationId){
                if (tdClass=='non-existent-loc'){
                    locHtml = locHtml + "<td id='"+locId+"' class='"+tdClass+"'  title='"+ locDesc+"'>"+" "+"</td>";
                }else{
                    locHtml = locHtml + "<td id='"+locId+"' class='"+tdClass+"' onclick='showLocInfo("+locId+","+rowStr+","+colStr+","+layersNumN+")' title='"+ locDesc+"'>"+locId+"</td>";
                }
            }else{
                locHtml = locHtml + "<td id='"+locId+"' class='"+tdClass+"' onclick='showLocInfo("+locId+","+rowStr+","+colStr+","+layersNumN+")' title='"+ locDesc+"'>&nbsp;</td>";
            }

        }
        locHtml = locHtml +"</tr>";
    }
    $('#layers-container').html(locHtml);
}

//展示详情模态框 货位编码、x行、y列、z层
function showLocInfo(locId ,rowStr,colStr,layersNumN) {
    console.log("locId:"+locId);
    $("#loc-modal-pallet-no").text("");
    $("#loc-modal-goods-name").text("");
    $("#loc-modal-goods-code").text("");
    $("#loc-modal-batch-no").text("");
    $("#loc-modal-amount").text("");
    // 点击后弹出模态框
    let title = "第"+colStr+"行，第"+rowStr+"列，第" +layersNumN + "层";

    //因为页面展示，与项目实际编码不一致，id需要处理 因为编码从10开始，处理小于100即可。
    let col = colStr<10 ? '0'+colStr : colStr.toString();
    let row = rowStr<10 ? '0'+rowStr : rowStr.toString();
    //层处理
    let layersNum=layersNumN<10 ? '0'+layersNumN : layersNumN.toString();
    var locationCode = col + row + layersNum;
    $("#loc-modal-desc").text(locationCode+"("+title+")");
    console.log(locationCode);
    //调用服务端接口获取货位详情
    $.ajax({
        type	:"get",
        url		: $.mpbGetHeaderPath()+"/location/detail/"+locationCode,
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
            let inKu = 0;
            let outKu = 0;
            // alert("dataList.length: " + dataList.length);
            for (let i = 0; i < dataList.length; i++) {
                let locationObj = dataList[i];
                let locationCode = locationObj.locationCode;
                //使用状态 0可用1入库中2出库中3占用4异常  5入库未过账，6 出库未过账
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
                }else if("5"==useStatus){
                    $("#"+locationCode).addClass("inKu-div");
                    inKu++;
                }else if("6"==useStatus){
                    $("#"+locationCode).addClass("outKu-div");
                    outKu++;
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
            $("#inKu-count").text(inKu);
            $("#outKu-count").text(outKu);
        },
        error	: function (e) {
            console.log(e);
        }
    });

}