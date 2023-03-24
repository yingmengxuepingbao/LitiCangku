package com.penghaisoft.pda.stereoscopic.controller;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.pda.basic.model.PalletInDto;
import com.penghaisoft.pda.basic.model.WmsAddressRealRela;
import com.penghaisoft.pda.basic.service.IWmsAddressRealRelaService;
import com.penghaisoft.pda.common.CommonUtil;
import com.penghaisoft.pda.common.Constant;
import com.penghaisoft.pda.common.IWmsCommonService;
import com.penghaisoft.pda.common.Resp;
import com.penghaisoft.pda.nuohua.service.SRService;
import com.penghaisoft.pda.outwarehouse.model.WmsTaskExecutionLog;
import com.penghaisoft.pda.outwarehouse.service.WmsOrderOutStereoscopicService;
import com.penghaisoft.pda.outwarehouse.service.WmsTaskExecutionLogService;
import com.penghaisoft.pda.storage.model.WmsGoods;
import com.penghaisoft.pda.storage.model.WmsPallet;
import com.penghaisoft.pda.storage.service.CommonStorageService;
import com.penghaisoft.pda.storage.service.StereoscopicHandInService;
import com.penghaisoft.pda.storage.service.TemporaryAreaStorageService;
import com.penghaisoft.pda.storage.service.WmsLocationStereoscopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * @Description PDA手动操作立库货物
 * @ClassName StereoscopicHandInController
 * @Author luot
 * @Date 2020/3/11 17:42
 **/
@Slf4j
@RestController
@RequestMapping("stereoscopic/hand")
public class StereoscopicHandInController {

    @Autowired
    private WmsOrderOutStereoscopicService wmsOrderOutStereoscopicService;

    @Autowired
    private WmsLocationStereoscopicService wmsLocationStereoscopicService;

    @Autowired
    private WmsTaskExecutionLogService wmsTaskExecutionLogService;

    @Autowired
    private CommonStorageService commonStorageService;

    @Autowired
    private IWmsCommonService wmsCommonService;

    @Autowired
    private IWmsAddressRealRelaService wmsAddressRealRelaService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StereoscopicHandInService handInService;

    @Value("${notice.other-sys-addr.pallet-in}")
    private String noticePalletInAddr;

    @Autowired
    private TemporaryAreaStorageService temporaryAreaStorageService;

    @Autowired
    private SRService service;

    @Autowired
    private WmsTaskExecutionLogService logService;
    /**
     * @return
     * @Description 立库手动入库【异常口入库】
     * @Author luot
     * @Date 2020/3/11 17:42
     * @Param
     **/
    @PostMapping("inwarehouse")
    public JSONObject handInSubmit(@RequestHeader("account") String account, @RequestHeader("areaCode") String areaCode, @RequestBody JSONObject param) {
        JSONObject result = null;
        log.info("接收成功============================");
//        托盘码
        String palletCode = param.getString("palletCode");

//        入库口地址【当前位置】
        String inAddress = param.getString("inAddress");
        Integer realAddress = 0;

        if (palletCode == null || "".equals(palletCode)) {
            result = CommonUtil.genErrorResult("请输入/扫描托盘码");
            return result;
        }

        if (inAddress == null || "".equals(inAddress)) {
            result = CommonUtil.genErrorResult("请选择当前位置");
            return result;
        }else{
//            将入库口地址转换成物理地址
            WmsAddressRealRela searchOb = new WmsAddressRealRela();
            searchOb.setAddressCode(inAddress);
            List<WmsAddressRealRela> addressList = wmsAddressRealRelaService.queryByAddressCode(searchOb);
            if(addressList != null && !addressList.isEmpty()){
                realAddress = addressList.get(0).getRealAddress();
            }
        }

        List<WmsPallet> palletList = wmsLocationStereoscopicService.queryWmsPallet(palletCode);
        if(palletList != null && !palletList.isEmpty()){
            WmsPallet wmsPallet = palletList.get(0);
            if(wmsPallet.getGoodsCode() == null || "".equals(wmsPallet.getGoodsCode())){
                result = CommonUtil.genErrorResult("当前托盘未绑定");
                return result;
            }
        }

        String key = Constant.ConfigInfo.INTERFACE_FOR_WCS_REDIS + palletCode;
        if (stringRedisTemplate.hasKey(key)) {
            result = CommonUtil.genErrorResult("当前托盘码正在处理中");
            return result;
        } else {
            stringRedisTemplate.opsForValue().set(key, "1", Constant.ConfigInfo.INTERFACE_FOR_WCS_REDIS_LOST_MINUTES, TimeUnit.MINUTES);//写入对象，并设置失效时间
        }

        String targetLocation = "";
//        增加异常处理，如果托盘被锁定的库位上的托盘就是自己可以直接入到该库位【待确认】
        targetLocation = wmsLocationStereoscopicService.queryRecommendLocationCodeCheck(palletCode);
        if("".equals(targetLocation)){
            Resp resp = wmsLocationStereoscopicService.queryRecommendLocationCode(palletCode);
            if ("0".equals(resp.getCode())) {
                stringRedisTemplate.delete(key);//删除对象
                result = CommonUtil.genErrorResult("获取推荐库位失败：" + resp.getMessage());
                return result;
            } else {
                targetLocation = (String) resp.getData();
            }
        }

        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        long taskId = wmsCommonService.getTaskIds(Constant.TaskType.HAND_IN, 1)[0];
        WmsTaskExecutionLog wmsTaskExecutionLog = new WmsTaskExecutionLog();
        wmsTaskExecutionLog.setAreaCode(areaCode);
//            任务类型 1 生产入库 2 分拣入库 3 移库 4 出库 5越库 6托盘入库
        wmsTaskExecutionLog.setTaskType(String.valueOf(Constant.TaskType.HAND_IN.getTaskType()));
        wmsTaskExecutionLog.setPalletCode(palletCode);
//            入库口地址
        wmsTaskExecutionLog.setInAddress(String.valueOf(realAddress));
//            任务状态1创建2执行3完成4异常5取消6创建失败
        wmsTaskExecutionLog.setTaskStatus("1");
//        List<WmsPallet> palletList = wmsLocationStereoscopicService.queryWmsPallet(palletCode);
        if(palletList != null && !palletList.isEmpty()){
            wmsTaskExecutionLog.setGoodsCode(palletList.get(0).getGoodsCode());
            wmsTaskExecutionLog.setBatchNo(palletList.get(0).getBatchNo());
        }
        wmsTaskExecutionLog.setCreateBy(account);
        wmsTaskExecutionLog.setGmtCreate(now);
        wmsTaskExecutionLog.setActiveFlag("1");
        wmsTaskExecutionLog.setTaskId(taskId);
//        wmsTaskExecutionLog.setOrderNo(orderNo);
        wmsTaskExecutionLog.setLocationCode(targetLocation);

        try {
            PalletInDto palletInDto = new PalletInDto();
            palletInDto.setTaskId(taskId);
            palletInDto.setTaskType(String.valueOf(Constant.TaskType.HAND_IN.getTaskType()));
//            目标库位
            palletInDto.setTargetLocation(Integer.parseInt(targetLocation));
//            入口path
            palletInDto.setFromAddress(realAddress);
            palletInDto.setPalletCode(palletCode);
            palletInDto.setOperator(account);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<PalletInDto> request = new HttpEntity<PalletInDto>(palletInDto, headers);

//                          调用wcs接收出库指令接口
            ResponseEntity<JSONObject> wcsResp = restTemplate.postForEntity(noticePalletInAddr, request, JSONObject.class);
            if (wcsResp.getStatusCodeValue()!=200){
                log.error("调wcs接口失败！");
//                库位状态回滚成初始状态0可用
                wmsLocationStereoscopicService.revertLocationStatus0(targetLocation);
                result = CommonUtil.genErrorResult("启动失败，获取推荐库位成功，调用WCS入库接口失败;");
                stringRedisTemplate.delete(key);//删除对象
                return result;
            }else {
                JSONObject noticeResult = wcsResp.getBody();
//                              状态码：1成功 0 本次下达失败
                if (noticeResult.getString("code").equals("1")){
                    log.info("调wcs接口成功！");
                }else {
                    log.error("调wcs接口失败：" + noticeResult.getString("message"));
//                    库位状态回滚成初始状态0可用
                    wmsLocationStereoscopicService.revertLocationStatus0(targetLocation);
                    result = CommonUtil.genErrorResult("启动失败，获取推荐库位成功，调用WCS入库接口失败;" + noticeResult.getString("message"));
                    stringRedisTemplate.delete(key);//删除对象
                    return result;
                }
            }

//            创建入立体库的指令任务、更新托盘状态
            wmsTaskExecutionLogService.inStereoscopicTaskCreate(wmsTaskExecutionLog);
        } catch (Exception e) {
//            库位状态回滚成初始状态0可用
            wmsLocationStereoscopicService.revertLocationStatus0(targetLocation);
            result = CommonUtil.genErrorResult("启动失败，获取推荐库位成功，调用WCS入库接口失败;");
            stringRedisTemplate.delete(key);//删除对象
            return result;
        }

        stringRedisTemplate.delete(key);//删除对象
        result = CommonUtil.genSucessResultWithData(null);
        return result;
    }

    /**
     * 诺华手动入立库功能[产成品]
     * @param param
     * @return
     */
    @PostMapping("inwarehouse/product")
    public JSONObject handIn(@RequestBody JSONObject param){
        JSONObject result = null;
        if(param==null || param.getString("palletCode")==null || "".equals(param.getString("palletCode"))){
            result = CommonUtil.genErrorResult("托盘码为空！");
            return result;
        }
        if(param==null || param.getString("batchCode")==null || "".equals(param.getString("batchCode"))){
            result = CommonUtil.genErrorResult("物料码批次号为空！");
            return result;
        }

        //获取托盘码
        String palletCode = param.getString("palletCode");
        //获取批次号等
        String batch= param.getString("batchCode");
        //获取物料编码
        String materialsCode ="";
        String batchNo="";


        //原材料  899030 In-PP004 000000000001
        if("50".equals( param.getString("inboundType") )) {
            batch = batch.replaceAll(" + ", ";");
            if (ciShu(batch, ";") != 2) {
                result = CommonUtil.genErrorResult("编码格式错误！");
                return result;
            }

            Map<String,String> map=GoodsFiled(batch,";");
            materialsCode =batch.substring(0,Integer.valueOf(map.get("1"))-1);
            batchNo=batch.substring(Integer.valueOf(map.get("1")),Integer.valueOf(map.get("2"))-1);
        }
        //成品  016761279768158110SFUD3172405003050
        if("10".equals( param.getString("inboundType") )) {
            batch = batch.replaceAll(" + ", ";");
            if (ciShu(batch, ";") > 0) {
                result = CommonUtil.genErrorResult("编码格式错误！");
                return result;
            }
            //768158
            materialsCode=batch.substring(9,15);
            //SFUD3
            batchNo=batch.substring(18,23);
        }


        List<WmsTaskExecutionLog> wmsTaskExecutionLogs=logService.selectByStatus("2","20");
        WmsTaskExecutionLog wmsTask =new WmsTaskExecutionLog();
        wmsTask.setTaskStatus("1");
        wmsTask.setPalletCode(palletCode);
        //如果存在正在出库的任务，校验入库状态1的托盘是否存在。
        List<WmsTaskExecutionLog> wmsTaskExecutionLoglist= wmsTaskExecutionLogService.selectTaskByPallet(wmsTask);

        if(wmsTaskExecutionLogs.size()>0){
            if(wmsTaskExecutionLoglist!=null && !wmsTaskExecutionLoglist.isEmpty()){
                result = CommonUtil.genErrorResult("有出库任务:"+wmsTaskExecutionLogs.size()+"个，正在执行！请先将托盘移走，等待出库任务完成");
                return result;
            }else{
                log.info("入库状态1的托盘不存在，创建！");
                String sId = UUID_SR();
                WmsTaskExecutionLog wmsTaskExecutionLog= new WmsTaskExecutionLog();
                wmsTaskExecutionLog.setTaskId(Long.valueOf(sId));
                wmsTaskExecutionLog.setUserDefined4(sId);
                wmsTaskExecutionLog.setTaskType(param.getString("inboundType"));
                wmsTaskExecutionLog.setTaskStatus("1");
                wmsTaskExecutionLog.setInAddress("2001");
                wmsTaskExecutionLog.setGoodsCode(materialsCode);
                wmsTaskExecutionLog.setBatchNo(batchNo);
                wmsTaskExecutionLog.setUserDefined3("1");
                wmsTaskExecutionLog.setUserDefined1(param.getString("Weight"));
                wmsTaskExecutionLog.setPalletCode(palletCode);
                logService.inStereoscopicTaskCreate(wmsTaskExecutionLog);
                result = CommonUtil.genErrorResult("有出库任务:"+wmsTaskExecutionLogs.size()+"个，正在执行！请先将托盘移走，等待出库任务完成");
                return result;
            }

        }

        WmsGoods goodInfo = commonStorageService.queryGoodInfoByCode(materialsCode);
        if (null==goodInfo){
            result = CommonUtil.genErrorResult("商品不存在！");
            return result;
        }
        WmsPallet pallet = commonStorageService.queryPalletInfoByCode(palletCode);
        if (null==pallet){
            result = CommonUtil.genErrorResult("托盘未维护！");
            return result;
        }
        if(pallet.getLocationCode()!=null){
            result=CommonUtil.genErrorResult("货位信息已绑定，请更换托盘");
            return result;
        }
        int amount=Integer.parseInt(param.getString("Weight"));

        //uer3未审核
        temporaryAreaStorageService.bindPalletGoods(palletCode,materialsCode,amount,batchNo,"PDA",null,"1");

        String taskId=param.getString("materialsCode");
        log.info("任务号是否有传入："+taskId);
        if(param.getString("materialsCode")==null||"".equals(param.getString("materialsCode"))){
            if(wmsTaskExecutionLoglist!=null && wmsTaskExecutionLoglist.size()>0) {
                log.info("未传入且存在入库状态1的托盘存在！");
                taskId = String.valueOf(wmsTaskExecutionLoglist.get(0).getTaskId()) ;
                WmsTaskExecutionLog wmsTaskExecutionLog = new WmsTaskExecutionLog();
                wmsTaskExecutionLog.setTaskId((wmsTaskExecutionLoglist.get(0)).getTaskId());
                wmsTaskExecutionLog.setTaskType(param.getString("inboundType"));
                wmsTaskExecutionLog.setTaskStatus("2");
                wmsTaskExecutionLog.setInAddress("2001");
                wmsTaskExecutionLog.setGoodsCode(materialsCode);
                wmsTaskExecutionLog.setBatchNo(batchNo);
                wmsTaskExecutionLog.setUserDefined3("1");
                wmsTaskExecutionLog.setUserDefined1(param.getString("Weight"));
                wmsTaskExecutionLog.setPalletCode(palletCode);
                logService.updateByTaskId(wmsTaskExecutionLog);
            }else{
                log.info("未传入且入库状态1的托盘不存在！");
                wmsTask =new WmsTaskExecutionLog();
                wmsTask.setTaskStatus("2");
                wmsTask.setPalletCode(palletCode);
                //如果存在正在出库的任务，校验入库状态1的托盘是否存在。
                List<WmsTaskExecutionLog> wmsTaskList= wmsTaskExecutionLogService.selectTaskByPallet(wmsTask);
                if(wmsTaskList!=null && wmsTaskList.size()>0) {
                    log.info("此托盘存在正在入库的任务数据！");
                    taskId = String.valueOf(wmsTaskList.get(0).getTaskId());
                }else{
                    taskId = UUID_SR();
                    WmsTaskExecutionLog wmsTaskExecutionLog = new WmsTaskExecutionLog();
                    wmsTaskExecutionLog.setTaskId(Long.valueOf(taskId));
                    wmsTaskExecutionLog.setTaskType(param.getString("inboundType"));
                    wmsTaskExecutionLog.setTaskStatus("2");
                    wmsTaskExecutionLog.setInAddress("2001");
                    wmsTaskExecutionLog.setGoodsCode(materialsCode);
                    wmsTaskExecutionLog.setBatchNo(batchNo);
                    wmsTaskExecutionLog.setUserDefined3("1");
                    wmsTaskExecutionLog.setUserDefined1(param.getString("Weight"));
                    wmsTaskExecutionLog.setPalletCode(palletCode);
                    logService.inStereoscopicTaskCreate(wmsTaskExecutionLog);
                }
            }
        }
        else{
            log.info("param.getString(\"materialsCode\") =" +param.getString("materialsCode") );
            //查询任务号是否绑定
            WmsTaskExecutionLog wmsTaskExecutionLog=logService.selectByTaskId(taskId);
            if(wmsTaskExecutionLog!=null){
                wmsTaskExecutionLog.setTaskType(param.getString("inboundType"));
                wmsTaskExecutionLog.setTaskStatus("2");
                wmsTaskExecutionLog.setInAddress("2001");
                wmsTaskExecutionLog.setGoodsCode(materialsCode);
                wmsTaskExecutionLog.setBatchNo(batchNo);
                wmsTaskExecutionLog.setUserDefined3("1");
                wmsTaskExecutionLog.setUserDefined1(param.getString("Weight"));
                wmsTaskExecutionLog.setPalletCode(palletCode);
                logService.updateByTaskId(wmsTaskExecutionLog);
            }
            else{
                wmsTaskExecutionLog.setTaskId(Long.valueOf(taskId));
                wmsTaskExecutionLog.setTaskType(param.getString("inboundType"));
                wmsTaskExecutionLog.setTaskStatus("2");
                wmsTaskExecutionLog.setInAddress("2001");
                wmsTaskExecutionLog.setGoodsCode(materialsCode);
                wmsTaskExecutionLog.setBatchNo(batchNo);
                wmsTaskExecutionLog.setUserDefined3("1");
                wmsTaskExecutionLog.setUserDefined1(param.getString("Weight"));
                wmsTaskExecutionLog.setPalletCode(palletCode);
                logService.inStereoscopicTaskCreate(wmsTaskExecutionLog);
            }
        }
        log.info("下发的任务号为："+taskId);
        //发送速锐启动
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("taskId",Long.valueOf(taskId));
        jsonObject.put("action","2");
        jsonObject.put("agvCode","");
        jsonObject.put("taskType","1");
        jsonObject=service.sendTask(jsonObject);
        if(jsonObject==null){
            result=CommonUtil.genErrorResult("速锐任务下发失败！");
        }
        else{
            if("0".equals(jsonObject.getString("resStat"))){
                result=CommonUtil.genErrorResult("任务下发成功");
            }else{
                result=CommonUtil.genErrorResult("速锐任务下发失败！");
            }
        }


        return result;
    }


    /**
     * 获取log所在字符串的位置
     * @param url
     * @param log
     * @return
     */
    public static Map GoodsFiled(String url, String log){
        Map<String,String> map=new HashMap<>();
        int h=0;
        for(int i=1;i<3;i++){
            h=url.indexOf(log,h);
            h++;
            if(h>0){
                map.put(String.valueOf(i),String.valueOf(h));
            }

        }
        return map;
    }
    //创建方法ciShu，返回int计算s2在字符串s1中出现的次数；
    public static int ciShu(String s1,String s2) {
        int he = 0;
        for (int i = 0; i < s1.length(); i++) {
            int t = s1.indexOf(s2, i);
            if (i == t) {
                he++;
            }
        }
        return he;
    }


    /**
     *诺华手动出立库功能
     */
    @PostMapping("out")
    public JSONObject handOut(@RequestBody JSONObject param){

        log.info("接收到手动出立库任务："+param);

        JSONObject result=null;

        try{
            JSONObject jsonObject=handInService.outTask(param);
            if(jsonObject==null){
                result = CommonUtil.genErrorResult("请求失败");
            }
            else{
                result = CommonUtil.genErrorResult(jsonObject.getString("resOutbound"));
            }
        }catch (Exception e){
            result = CommonUtil.genErrorResult(e.toString());
        }


        return result;
    }
    /**
     * 诺华切换库口模式
     * param:
     * "inAddress":"0"入库
     * "inAddress":"1"出库
     */
    @PostMapping("change")
    public JSONObject handChange(@RequestBody JSONObject param){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("portModel",Integer.valueOf(param.getString("inAddress")));
        jsonObject= handInService.changeWarehouse(jsonObject);
        JSONObject result=null;
        if(jsonObject==null){
            result = CommonUtil.genErrorResult("更改失败");
        }else{
            result = CommonUtil.genSucessResultWithData(jsonObject.get("returnInfo"));
        }
        return result;
    }
    //随机生成11位的AGV任务
    public static String UUID_SR() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 10]);
        }
        return shortBuffer.toString();
    }
    public static String[] chars = new String[] { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9"};
}
