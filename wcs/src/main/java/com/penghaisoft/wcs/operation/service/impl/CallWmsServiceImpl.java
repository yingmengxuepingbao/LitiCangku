package com.penghaisoft.wcs.operation.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.basicmanagement.model.business.IWcsLocationRealService;
import com.penghaisoft.wcs.basicmanagement.model.business.IWcsPathService;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsLocationReal;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsPath;
import com.penghaisoft.wcs.common.service.IWcsCommonService;
import com.penghaisoft.wcs.expose.dto.PalletInDto;
import com.penghaisoft.wcs.monitormanagement.model.business.IWcsErrorLogService;
import com.penghaisoft.wcs.monitormanagement.model.dao.WcsErrorLogMapper;
import com.penghaisoft.wcs.monitormanagement.model.entity.WcsErrorLog;
import com.penghaisoft.wcs.operation.model.RecommendLocResp;
import com.penghaisoft.wcs.operation.model.dao.WcsBindingInfoMapper;
import com.penghaisoft.wcs.operation.model.WcsBindingInfo;
import com.penghaisoft.wcs.operation.service.CallWmsService;
import com.penghaisoft.wcs.taskmanagement.model.business.ITaskDispatchService;
import com.penghaisoft.wcs.taskmanagement.model.business.ITaskSplitService;
import com.penghaisoft.wcs.taskmanagement.model.dao.WcsTaskMapper;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsFourwaycarTask;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsTask;
import com.penghaisoft.wcs.util.JDBCUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description CallWmsServiceImpl
 * @Auther zhangxu
 * @Date 2020/3/27 17:11
 **/
@Slf4j
@Service
public class CallWmsServiceImpl implements CallWmsService {

    @Autowired
    private WcsTaskMapper wcsTaskMapper;

    @Autowired
    private WcsBindingInfoMapper bindingInfoMapper;

    @Autowired
    IWcsErrorLogService wcsErrorLogService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ITaskSplitService taskSplitService;

    @Autowired
    private IWcsLocationRealService wcsLocationRealService;

    @Autowired
    private IWcsCommonService wcsCommonService;

    @Autowired
    private IWcsPathService wcsPathService;

    @Autowired
    private ITaskDispatchService taskDispatchService;

    private static final String PRODUCT_IN_RECOMMEND = "WCS:PRODUCTIN:RECOMMEND:";

    private static final String VIRTUAL_PALLET_IN_RECOMMEND = "WCS:VIRTUALPALLETIN:RECOMMEND:";

    @Value("${notice.other-sys-addr.wms-base-url}")
    private String wmsBaseUrl;

    @Value("${wcs.config.pallet-count-1003}")
    private String palletCount;


    /**
     * 调用wms接口的统一方法,WMS接口0成功1异常
     * 0 服务端异常
     * 1 请求成功（包括业务异常）
     * 2 客户端异常
     * @param url
     * @param param
     * @return
     */
    private Resp callWmsInterface(String url, Map<String, String> param){
        Resp resp = new Resp();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

//        todo 统一放入库区
            param.put("areaCode", "L-BJ01");
            HttpEntity<Map> request = new HttpEntity<Map>(param, headers);
//              上传wms
            ResponseEntity<JSONObject> wcsResp = restTemplate.postForEntity(wmsBaseUrl + url, request, JSONObject.class);
            if (wcsResp.getStatusCodeValue() != 200) {
                log.error("调WMS接口{}服务端返回异常！",url);
                resp.setCode("0");
                resp.setMsg("调WMS接口服务端返回异,http请求返回码="+wcsResp.getStatusCodeValue());
            } else {
                JSONObject noticeResult = wcsResp.getBody();
                resp.setCode("1");
                resp.setData(noticeResult);
            }
        }catch (Exception e) {
            e.printStackTrace();
            log.error("调WMS接口{}客户端异常！",url);
            String errorMsg = e.getMessage();
            if (errorMsg.length()>200){
                errorMsg = errorMsg.substring(0,200);
            }
            resp.setCode("2");
            resp.setMsg(errorMsg);
        }finally {

            return resp;
        }
    }

    /**
     * 需要读取托盘绑定信息，
     * 调用wms接口进行记录，
     * 写入wcs绑定信息表
     * 进行绑定记录+上传wms(1 查本地表 2调用wms 3改状态)
     * @param bindingInfo  包括 String palletCode, String goodsCode,
     *                             Integer amount, String batchNo
     * @param operator
     * @return
     */
    @Override
    public RecommendLocResp uploadBindingInfo(WcsBindingInfo bindingInfo, String operator){
        RecommendLocResp recommendLocResp = null;
        String bindUrl = "expose/goodsbind";
        Integer id = bindingInfo.getId();
//            查看有没有上传wms ，没有则进行上传
        Map<String, String> param = new HashMap<String, String>();
        param.put("palletCode", bindingInfo.getPalletCode());
        param.put("batchNo", bindingInfo.getBatchNo());
        param.put("goodsCode", bindingInfo.getGoodsCode());
        param.put("amount", bindingInfo.getAmount().toString());

        Resp callWmsResp = callWmsInterface(bindUrl,param);
//        ------------------------------------------
//        Resp callWmsResp = new Resp();
//        callWmsResp.setCode("1");
//        JSONObject fakeData = new JSONObject();
//        fakeData.put("code",Constant.RESULT.SUCCESS.code);
//        JSONObject dd = new JSONObject();
//        dd.put("taskId",200702100001L);
//        fakeData.put("data",dd);
//        callWmsResp.setData(fakeData);
//        ---------------------------------------
//            * 0 服务端异常
//            * 1 请求成功（包括业务异常）
//            * 2 客户端异常
        String respCode = callWmsResp.getCode();
//            发生客户端或者服务端异常
        if ("0".equals(respCode) || "2".equals(respCode)){
            updateBindInfo(id,"2",callWmsResp.getMsg(),operator);
            return null;
        }
//                实际上wms 只会判断有没有重复绑定
        //            请求成功
        JSONObject wmsRespData = (JSONObject) callWmsResp.getData();
/*        {
            "code": "0",
            "data": {
            "taskId": "2007271000004"
            },
            "message": ""
        }*/
        if (wmsRespData.getString("code").equals(Constant.RESULT.SUCCESS.code)) {
            recommendLocResp = new RecommendLocResp();
            recommendLocResp.setTaskId(wmsRespData.getJSONObject("data").getLong("taskId"));
            updateBindInfo(id,"1",null,operator);
            return recommendLocResp;
        } else {
            updateBindInfo(id,"2",wmsRespData.getString("message"),operator);
            return null;
//            if (wmsRespData.getString("code").equals(Constant.RESULT.FAILED.code)){
//                recommendLocResp = new RecommendLocResp();
//                recommendLocResp.setTaskId(wmsRespData.getJSONObject("data").getLong("taskId"));
//                updateBindInfo(id,"1",null,operator);
//                return recommendLocResp;
//            }else {
//                updateBindInfo(id,"2",callWmsResp.getMsg(),operator);
//                return null;
//            }
        }

    }

    /**
     * 更新绑定结果
     * @param id
     * @param reportWmsFlag
     * @param msg
     */
    private void updateBindInfo(Integer id,String reportWmsFlag,String msg,String operator){
        WcsBindingInfo update = new WcsBindingInfo();
        update.setId(id);
        update.setGmtModified(new Date());
        update.setLastModifiedBy(operator);
        update.setReportWms(reportWmsFlag);
        if (null!=msg && msg.length()>256){
            msg = msg.substring(0,255);
        }
        update.setErrorMsg(msg);
        bindingInfoMapper.updateByPrimaryKeySelective(update);
    }


    /**
     * 码垛机绑定后发生动作
     * 调用wms接口计算推荐库位，拿到推荐库位后进行入库任务拆解
     *
     * @param bindingInfo  包括 String palletCode, String goodsCode,
     *                     Integer amount, String batchNo
     * @param palletizerId
     * @param addressId
     * @param operator
     * @return
     */
    @Override
    public boolean recommendAndSplitProductInTask(WcsBindingInfo bindingInfo, Integer palletizerId, Integer addressId, String operator) {
//        判断有没有计算完（有可能2秒上一次处理没结束，不要重复处理）
        String palletCode = bindingInfo.getPalletCode();
        String redisK = PRODUCT_IN_RECOMMEND + palletCode;
        boolean isHandling = redisTemplate.hasKey(redisK);
        if (isHandling){
            log.info("码垛机{}的托盘{}正在处理中，本次放弃处理",palletizerId,palletCode);
            return false;
        }
        redisTemplate.opsForValue().set(redisK,addressId.toString(),10, TimeUnit.SECONDS);

        String url = "expose/productin/location/automatic";
//        1 调用wms 计算推荐库位
        Map<String, String> param = new HashMap<String, String>();
        param.put("palletCode", bindingInfo.getPalletCode());
//        生产入库
        param.put("taskType", String.valueOf(Constant.TaskType.PRODUCT_IN.getTaskType()));
        param.put("inAddress", addressId.toString());

        Resp callWmsResp = callWmsInterface(url,param);

        String respCode = callWmsResp.getCode();
//            发生客户端或者服务端异常
        if ("0".equals(respCode) || "2".equals(respCode)){
            log.error(callWmsResp.getMsg());
            return false;
        }

//      请求成功,wms 返回业务错误
        JSONObject wmsRespData = (JSONObject) callWmsResp.getData();
        if (wmsRespData.getString("code").equals(Constant.RESULT.FAILED.code)) {
            log.error(callWmsResp.getMsg());
            return false;
        }
//      请求成功，wms返回的推荐数据
        JSONObject recommendData = wmsRespData.getJSONObject("data");
//        1 越库0 非越库 默认0
        String isCross = recommendData.getString("isCross");

//        任务号重复不管越不越库都需要校验
        Long taskId = recommendData.getLong("taskId");

//        校验任务有没有重复下发
        boolean isDuplicate = taskSplitService.isDuplicateTask(taskId);
        if (isDuplicate){
            log.info("任务{}重复！",taskId);
            return false;
        }


        if ("1".equals(isCross)){
//            todo 玉皇粮油版本的越库 堆垛机也要参与
//            这是一个固定的出库口，可以写死，进行越库任务拆分

//        根据库位转获取入库线--固定就从1号堆垛机
            int[] crossAddress = wcsCommonService.getCrossAddress();
            Integer inBufferAddress = crossAddress[0];
            //      判断地址是否合法,从码垛线到堆垛机入库线
            Resp pathResult = wcsPathService.isPathLegal(addressId,inBufferAddress);
            if ("0".equals(pathResult.getCode())){
//            地址不合法
                log.info(pathResult.getMsg());
                return false;
            }
            WcsPath wcsPath = (WcsPath) pathResult.getData();
//          越库时候的入库路径id-从码垛线到入库线
            Integer pathId = wcsPath.getPathId();
//            taskSplitService.splitCrossTask(addressId,pathId,taskId,palletCode,operator);
            redisTemplate.delete(redisK);
        }

//          普通入库任务拆分
        if("0".equals(isCross)){

//        根据入库口和目的地货架到wcs_path找到path_id,找不到提示不行
            String locationCode = recommendData.getString("locationCode");
//        目的地库位
            int targetLocation = Integer.valueOf(locationCode).intValue();
            WcsLocationReal locationReal = wcsLocationRealService.findById(targetLocation);
            if (locationReal==null){
                log.info("库位{}不存在！",targetLocation);
                return false;
            }
//        根据库位转获取入库线
            Integer inBufferAddress = wcsCommonService.getInBufferAddressByLocation(targetLocation);
            if (inBufferAddress == null){
                log.info("入库库位与入库线转换错误！");
                return false;
            }
//      判断地址是否合法,从码垛线到堆垛机入库线
            Resp pathResult = wcsPathService.isPathLegal(addressId,inBufferAddress);
            if ("0".equals(pathResult.getCode())){
//            地址不合法
                log.info(pathResult.getMsg());
                return false;
            }
            WcsPath wcsPath = (WcsPath) pathResult.getData();
//        路径id
            Integer pathId = wcsPath.getPathId();
//        拆分
            PalletInDto palletInDto = new PalletInDto();
            palletInDto.setTaskId(taskId);
            palletInDto.setPalletCode(palletCode);
            palletInDto.setFromAddress(addressId);
            palletInDto.setTargetLocation(targetLocation);
            palletInDto.setOperator(operator);
            taskSplitService.splitInTask(inBufferAddress,pathId,palletInDto,locationReal);
            redisTemplate.delete(redisK);
        }
        return true;
    }

    /**
     * todo 这里待定
     * 查询report_wms=1
     *
     * @return
     */
    @Override
    public List<WcsBindingInfo> queryMoveBindingInfo() {
        WcsBindingInfo bindingInfo = new WcsBindingInfo();
        bindingInfo.setReportWms("1");
        return bindingInfoMapper.queryMoveBindingInfo(bindingInfo);
    }

    /**
     * 转移绑定信息
     * @param list
     * @param idList
     * @return
     */
    @Override
    public Resp dealBindingInfo(List<WcsBindingInfo> list, List<Integer> idList) {
        Resp resp = new Resp();
        //开始批量插入his数据库
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Statement statement = null;
        long dealNum = 0;
        try {
            conn = JDBCUtil.getConn();
            conn.setAutoCommit(false);////关闭自动提交
            String sql = "insert into wcs_binding_info (palletizer_id,address_id,pallet_code,goods_code," +
                    "amount,batch_no,report_wms,recommended_location,is_cross,error_msg,create_by,gmt_create) values(?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);

            for(int i = 0; i < list.size(); i++){
                //palletizer_id
                if (list.get(i).getPalletizerId() == null){
                    ps.setNull(1, Types.INTEGER);
                }else {
                    ps.setInt(1,list.get(i).getPalletizerId());
                }
                //address_id
                if (list.get(i).getAddressId() == null){
                    ps.setNull(2, Types.INTEGER);
                }else {
                    ps.setInt(2,list.get(i).getAddressId());
                }
                //pallet_code
                ps.setString(3,list.get(i).getPalletCode());
                //goods_code
                ps.setString(4,list.get(i).getGoodsCode());
                //amount
                if (list.get(i).getAmount() == null){
                    ps.setNull(5, Types.INTEGER);
                }else {
                    ps.setInt(5,list.get(i).getAmount());
                }
                //batch_no
                ps.setString(6,list.get(i).getBatchNo());
                //report_wms
                ps.setString(7,list.get(i).getReportWms());
                //recommended_location
                if (list.get(i).getRecommendedLocation() == null){
                    ps.setNull(8, Types.INTEGER);
                }else {
                    ps.setInt(8,list.get(i).getRecommendedLocation());
                }

                //is_cross
                ps.setString(9,list.get(i).getIsCross());
                //error_msg
                ps.setString(10,list.get(i).getErrorMsg());
                //create_by
                ps.setString(11,list.get(i).getCreateBy());

                //gmt_create
                if (list.get(i).getGmtCreate() == null){
                    ps.setNull(12, java.sql.Types.DATE);
                }else {
                    ps.setTimestamp(12,new Timestamp(list.get(i).getGmtCreate().getTime()));
                }

                ps.addBatch();
                if (i % 10000 == 0 && i != 0){
                    ps.executeBatch();
                    conn.commit();
                    dealNum=dealNum+10000;
                    ps.clearBatch();
                }
            }
            ps.executeBatch();
            conn.commit();
            if (dealNum == 0){
                dealNum = list.size();
            }else {
                dealNum = dealNum+(list.size()-dealNum);
            }
            conn.close();
            System.out.println("wcs_binding_info插入his条数"+dealNum);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setCode("0");
            resp.setMsg("wcs_binding_info批量插入异常");
            return resp;
        } finally {
            JDBCUtil.close(conn,ps,rs);
        }
        //开始批量删除原数据库
        //根据taskIdList
        int delectNum = bindingInfoMapper.deleteBindInfoByIdList(idList);
        System.out.println("wcs_binding_info删除原库数量"+delectNum);
        if (delectNum != dealNum){
            resp.setCode("0");
            resp.setMsg("wcs_binding_info批量插入"+dealNum+"批量删除"+delectNum);
            return resp;
        }

        resp.setCode("1");
        resp.setMsg("wcs_binding_info转移成功");
        return resp;
    }

    /**
     * @param palletCode
     * @return java.lang.Integer
     * @Description 请求wms 获取推荐库位,当托盘到达立库入库口时发生
     * @Date 2020/7/9 14:38
     **/
    @Override
    public WcsTask recommendProductInLocation(String palletCode) {
        Integer locationCode = null;
        //        判断有没有计算完（有可能2秒上一次处理没结束，不要重复处理）
        String redisK = PRODUCT_IN_RECOMMEND + palletCode;
        boolean isHandling = redisTemplate.hasKey(redisK);
        if (isHandling){
            log.info("托盘{}正在处理中，本次放弃处理",palletCode);
            return null;
        }
//        查找这个托盘之前生成的任务号
        WcsTask inWcsTask = taskDispatchService.findFourWayCarWaitingInTask(palletCode);
        if (null==inWcsTask){
            return null;
        }
        Long taskId = inWcsTask.getTaskId();
        redisTemplate.opsForValue().set(redisK,"fourwaycar",10, TimeUnit.SECONDS);

        String url = "expose/productin/location/automatic";
//        1 调用wms 计算推荐库位
        Map<String, String> param = new HashMap<String, String>();
        param.put("palletCode", palletCode);
        param.put("taskId", taskId.toString());
//        生产入库
        param.put("taskType", String.valueOf(Constant.TaskType.PRODUCT_IN.getTaskType()));
        param.put("inAddress", wcsCommonService.getInBufferAddressByLocation(0).toString());

//        todo 上线前修改
        Resp callWmsResp = callWmsInterface(url,param);
//------------------------
//        Resp callWmsResp = new Resp();
//        callWmsResp.setCode("1");
//        JSONObject fakeData = new JSONObject();
//        fakeData.put("code","0");
//        JSONObject data = new JSONObject();
//        data.put("locationCode",10131);
//        data.put("taskId",taskId);
//        fakeData.put("data",data);
//        callWmsResp.setData(fakeData);
//-------------------------
        String respCode = callWmsResp.getCode();
//            发生客户端或者服务端异常
        if ("0".equals(respCode) || "2".equals(respCode)){
            log.error(callWmsResp.getMsg());
            return null;
        }

//      请求成功,wms 返回业务错误
        JSONObject wmsRespData = (JSONObject) callWmsResp.getData();
        if (wmsRespData.getString("code").equals(Constant.RESULT.FAILED.code)) {
            log.error(wmsRespData.getString("message"));
            return null;
        }
//      请求成功，wms返回的推荐数据
        JSONObject recommendData = wmsRespData.getJSONObject("data");
//          普通入库任务拆分
//        根据入库口和目的地货架到wcs_path找到path_id,找不到提示不行
        locationCode = recommendData.getInteger("locationCode");
        WcsLocationReal locationReal = wcsLocationRealService.findById(locationCode);
        if (locationReal==null){
            log.info("库位{}不存在！",locationCode);
            return null;
        }
//        根据库位转获取入库线
        Integer inBufferAddress = wcsCommonService.getInBufferAddressByLocation(locationCode);
        if (inBufferAddress == null){
            log.info("入库库位与入库线转换错误！");
            return null;
        }
//      判断地址是否合法,从码垛线到堆垛机入库线
        Resp pathResult = wcsPathService.isPathLegal(inWcsTask.getFromAddress(),inBufferAddress);
        if ("0".equals(pathResult.getCode())){
//            地址不合法
            log.info(pathResult.getMsg());
            return null;
        }
        redisTemplate.delete(redisK);
        inWcsTask.setToAddress(locationCode);
        WcsFourwaycarTask fourwaycarTask = taskDispatchService.findFourWayCarTaskByTaskId(taskId);
        if (null!=fourwaycarTask){
            inWcsTask.setUserDefined1(fourwaycarTask.getTaskNo().toString());
            inWcsTask.setUserDefined2(fourwaycarTask.getFourwaycarTaskId().toString());
        }
        WcsTask updWcsTask = new WcsTask();
        updWcsTask.setTaskId(taskId);
        updWcsTask.setToAddress(locationCode);
        updWcsTask.setLastModifiedBy("推荐库位");
        updWcsTask.setGmtModified(new Date());
        wcsTaskMapper.updateByTaskIdSelective(updWcsTask);
        return inWcsTask;
    }

    /**
     * @return java.lang.Integer
     * @Description 请求wms获取出库的虚拟托盘库位
     * @Date 2020/7/17 10:01
     **/
    @Override
    public Resp recommendVirtualPalletOutLocation() {
/*        {
            "code": "0",
            "data": [
                {
                    "amount": 0,
                        "batchNo": "",
                        "channelLocation": "",
                        "fromLocation": "10419",
                        "goodsCode": "pallet",
                        "goodsName": "",
                        "operator": "wcs",
                        "palletCode": "V00004",
                        "relyMoveTask": null,
                        "targetAddress": "3001",
                        "taskId": 2007272500001,
                        "taskType": "25"
                }
            ],
            "message": "成功"
        }*/
        String url = "expose/start/outwarehouse/virtualpallet";
//        InterfaceForQXFWcsController
        Map<String, String> param = new HashMap<>();
        Resp resp = callWmsInterface(url,param);
        if(resp.getCode().equals("1")){
            JSONObject wmsResult = (JSONObject)resp.getData();
            if ("0".equals(wmsResult.getString("code"))){
                JSONArray dataArr = wmsResult.getJSONArray("data");
                Resp result = new Resp();
                JSONObject data = new JSONObject();
                data.put("locationCode",dataArr.getJSONObject(0).getString("fromLocation"));
                data.put("palletCode",dataArr.getJSONObject(0).getString("palletCode"));
                data.put("taskId",dataArr.getJSONObject(0).getString("taskId"));
                result.setData(data);
                return result;
            }else {
                log.error("请求托盘出库库位，wms返回业务异常={}",resp.getMsg());
                return null;
            }
        }else {
            log.error("请求托盘出库库位，系统异常={}",resp.getMsg());
            return null;
        }


    }

    /**
     * @param palletCode
     * @return com.penghaisoft.framework.util.Resp
     * @Description LED显示
     * @Date 2020/7/29 14:54
     **/
    @Override
    public Resp showOutPalletInfo(Integer taskNo,String palletCode,String status) {
//        1 根据taskNo查询taskId
        WcsFourwaycarTask fourwaycarTask = taskDispatchService.findFourWayCarTaskByTaskNo(taskNo);
        if (null != fourwaycarTask){
            Long taskId = fourwaycarTask.getTaskId();
            Map<String,String> param = new HashMap<>();
            param.put("taskId",taskId.toString());
            param.put("palletCode",palletCode);
            param.put("status",status);
            String url = "expose/show/led";
//            调用WMS进行LED显示
            Resp resp = callWmsInterface(url,param);
            if (!"1".equals(resp.getCode())){
                log.error("调用WMS接口{}异常",url);
            }
            return resp;
        }
        return null;
    }

    /**
     * @param virtualPalletCode
     * @return com.penghaisoft.wcs.taskmanagement.model.entity.WcsTask
     * @Description 获取WMS虚拟托盘入库推荐库位
     * @Date 2020/8/13 16:50
     **/
    @Override
    public WcsTask recommendVirtualPalletInLocation(String virtualPalletCode) {
//        判断有没有计算完（有可能2秒上一次处理没结束，不要重复处理）
        String redisK = VIRTUAL_PALLET_IN_RECOMMEND + virtualPalletCode;
        boolean isHandling = redisTemplate.hasKey(redisK);
        if (isHandling){
            log.info("虚拟托盘{}正在处理中，本次放弃处理",virtualPalletCode);
            return null;
        }
        redisTemplate.opsForValue().set(redisK,virtualPalletCode,10, TimeUnit.SECONDS);

        String url = "expose/virtualpalletin/location/automatic";
        //        1 调用wms 计算推荐库位
        Map<String, String> param = new HashMap<String, String>();
        param.put("palletCode", virtualPalletCode);
        param.put("amount", palletCount);
        param.put("inAddress","1003");
//        虚拟托盘入库
        param.put("taskType", String.valueOf(Constant.TaskType.VIRTUAL_PALLET_IN.getTaskType()));
//        todo 上线前修改
        Resp callWmsResp = callWmsInterface(url,param);
//        ------------------------------------------
//        Resp callWmsResp = new Resp();
//        callWmsResp.setCode("1");
//        JSONObject fakeData = new JSONObject();
//        fakeData.put("code",Constant.RESULT.SUCCESS.code);
//        JSONObject dd = new JSONObject();
//        dd.put("taskId",2008141500002L);
//        dd.put("locationCode",10301);
//        fakeData.put("data",dd);
//        callWmsResp.setData(fakeData);
//        ---------------------------------------
        if ("1".equals(callWmsResp.getCode())){

            JSONObject wmsRespData = (JSONObject)callWmsResp.getData();
        //      请求成功,wms 返回业务错误
            if (wmsRespData.getString("code").equals(Constant.RESULT.FAILED.code)) {
                log.error("请求WMS虚拟托盘入库返回业务异常={}",callWmsResp.getMsg());
                redisTemplate.delete(redisK);
                return null;
            }
//      请求成功，wms返回的推荐数据
            JSONObject recommendData = wmsRespData.getJSONObject("data");

            Long taskId = recommendData.getLong("taskId");
            //        校验任务有没有重复下发
            boolean isDuplicate = taskSplitService.isDuplicateTask(taskId);
            if (isDuplicate){
                log.info("任务{}重复！",taskId);
                redisTemplate.delete(redisK);
                return null;
            }
//            校验库位存在
            Integer locationCode = recommendData.getInteger("locationCode");
            WcsLocationReal locationReal = wcsLocationRealService.findById(locationCode);
            if (locationReal==null){
                log.info("库位{}不存在！",locationCode);
                redisTemplate.delete(redisK);
                return null;
            }
            PalletInDto palletInDto = new PalletInDto();
            palletInDto.setTaskId(taskId);
            palletInDto.setPalletCode(virtualPalletCode);
            palletInDto.setOperator("ExposeForFourWayCarController");
//            叠盘机入口
            palletInDto.setFromAddress(1003);
            palletInDto.setTargetLocation(locationCode);

            ResponseResult responseResult = taskSplitService.splitHandInTask(palletInDto);

            JSONObject resultData = (JSONObject) responseResult.getData();

            WcsTask result = new WcsTask();
            result.setId(resultData.getInteger("wcsTaskId"));
            result.setUserDefined1(resultData.getString("fourwaycarTaskId"));
            result.setUserDefined2(resultData.getString("fourwaycarTaskNo"));
            result.setUserDefined3(locationCode.toString());
            result.setUserDefined4(taskId.toString());
            redisTemplate.delete(redisK);
            return result;
        }else {
            redisTemplate.delete(redisK);
            return null;
        }
    }

    /**
     * @param taskId
     * @param palletCode
     * @return void
     * @Description 告知WMS托盘即将出库，增加出库托盘数
     * @Date 2020/9/25 14:53
     **/
    @Override
    public void palletReadyOut(Long taskId, String palletCode) {
        Map<String,String> param = new HashMap<>();
        param.put("taskId",taskId.toString());
        param.put("palletCode",palletCode);
        String url = "expose/pallet/out/ready";
//            调用WMS进行LED显示
        Resp resp = callWmsInterface(url,param);
        if (!"1".equals(resp.getCode())){
            log.error("调用WMS接口{}异常",url);
        }
    }

}
