//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.nuohua.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.constant.Constant.TaskType;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsAddressRealRelaMapper;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsGoodsMapper;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsHBLocationStereoscopicMapper;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsLocationStereoscopicMapper;
import com.penghaisoft.wms.basicmanagement.model.entity.*;
import com.penghaisoft.wms.common.service.IWmsCommonService;
import com.penghaisoft.wms.expose.FloorNumGenerate;
import com.penghaisoft.wms.nuohua.service.*;
import com.penghaisoft.wms.outboundmanagement.model.business.IWmsOrderOutStereoscopicService;
import com.penghaisoft.wms.outboundmanagement.model.dao.WmsOrderOutStereoscopicDeailMapper;
import com.penghaisoft.wms.outboundmanagement.model.dao.WmsOrderOutStereoscopicMapper;
import com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutStereoscopic;
import com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutStereoscopicDeail;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsMoveStereoscopicService;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsTaskExecutionLogService;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsMoveStereoscopicMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsOrderCheckPalletMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsPalletMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsTaskExecutionLogMapper;
import com.penghaisoft.wms.storagemanagement.model.entity.*;
import com.penghaisoft.wms.util.ConstantUtil;
import com.penghaisoft.wms.util.PostResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service("differentBusinessNHService")
public class DifferentBusinessNHServiceImpl extends BaseService implements DifferentBusinessNHService {
    private static final Logger log = LoggerFactory.getLogger(DifferentBusinessNHServiceImpl.class);
    @Autowired
    private IWmsOrderOutStereoscopicService wmsOrderOutStereoscopicService;
    @Resource
    private WmsGoodsMapper wmsGoodsMapper;
    @Resource
    private WmsLocationStereoscopicMapper wmsLocationStereoscopicMapper;
    @Resource
    private WmsHBLocationStereoscopicMapper wmsHBLocationStereoscopicMapper;
    @Resource
    private WmsAddressRealRelaMapper wmsAddressRealRelaMapper;
    @Autowired
    private IWmsCommonService wmsCommonService;
    @Autowired
    private SLWCSService sLWCSService;
    @Autowired
    private IWmsTaskExecutionLogService wmsTaskExecutionLogService;
    @Resource
    private WmsOrderOutStereoscopicMapper wmsOrderOutStereoscopicMapper;
    @Resource
    private WmsMoveStereoscopicMapper wmsMoveStereoscopicMapper;
    @Resource
    private WmsPalletMapper wmsPalletMapper;
    @Resource
    private WmsOrderCheckPalletMapper wmsOrderCheckPalletMapper;
    @Resource
    private WmsOrderOutStereoscopicDeailMapper wmsOrderOutStereoscopicDeailMapper;
    @Resource
    private WmsTaskExecutionLogMapper wmsTaskExecutionLogMapper;
    @Autowired
    private InterfaceForNHService interfaceForNHService;

    @Autowired
    private RestTemplate restTemplate;

    //post请求工具类
    @Autowired
    public PostResponseUtil postResponseUtil;
    //调WCS接口
    @Autowired
    private WcsForNHService wcsForNHService;
    @Autowired
    private WmsNHAgvService wmsNHAgvService;
    @Autowired
    private IWmsMoveStereoscopicService wmsMoveStereoscopicService;

    public DifferentBusinessNHServiceImpl() {
    }

    /**
     * 功能描述: 入库推荐-生成推荐库位（一个）
     * @return com.penghaisoft.framework.util.Resp
     * @params WmsTaskExecutionLog
     * @params areaCode 所属库区
     */
    public Resp queryRecommendLocationCodeHB(WmsTaskExecutionLog wmsTaskExecutionLog, String areaCode) {
        Resp resp = new Resp();
        //库位编码 6位（例：010203（x 01;y:02;z:03））
        String locationCode = "";
        //立库库位信息表-实体类
        WmsLocationStereoscopic searchOb = new WmsLocationStereoscopic();
        if(wmsTaskExecutionLog!=null) {
            searchOb.setGoodsCode(wmsTaskExecutionLog.getGoodsCode());
            searchOb.setBatchNo(wmsTaskExecutionLog.getBatchNo());
        }else{
            resp.setCode(RESULT.FAILED.code);
            resp.setMsg("wmsTaskExecutionLog 数据异常！");
            return resp;
        }
        String goodsCode = wmsTaskExecutionLog.getGoodsCode();
        String batchNo = wmsTaskExecutionLog.getBatchNo();
        //判断是原材料，还是成品：
        Boolean bvFlag = false;
        WmsGoods wmsGoods = this.wmsGoodsMapper.queryByCode(wmsTaskExecutionLog.getGoodsCode());
        if (wmsGoods != null&& !"".equals(wmsGoods) && wmsGoods.getGoodsType() != null  && ("0".equals(wmsGoods.getGoodsType()) || "3".equals(wmsGoods.getGoodsType()))) {
            bvFlag = true;
        }else{
           log.info("数据库未查到数据，或GoodsType-商品类型为空，或是成品！");
        }
        log.info("是否包材或虚拟托盘={}", bvFlag);

        long t1 = System.currentTimeMillis();
        //-查询所有可用货位
        List<WmsLocationStereoscopic> listRight = this.wmsHBLocationStereoscopicMapper.getLocationInfoBylocationCodeList(searchOb);
        long t2 = System.currentTimeMillis();
        log.info("##################查询所有可用货位，SQL查询耗时：" + (t2 - t1) + "ms！#########################");
        //判断是否有空库位推荐
        if (listRight != null && listRight.size() > 0) {
            List list =new ArrayList();
            list.add("1");
            list.add("2");
            list.add("3");
            list.add("4");
            log.info("入库类型: "+wmsTaskExecutionLog.getTaskType());
            WmsLocationStereoscopic wmsLocationStereoscopic =new WmsLocationStereoscopic();
            List<Integer> shelvesNumberList = Arrays.asList(1,3,13,15,25,27,37,39);
            Integer floorNumber=0;
            wmsLocationStereoscopic.setShelvesNumberList(new ArrayList(shelvesNumberList));
            List<Integer> flooNumberOne =Arrays.asList(1,2,3,4);
             //查询各层，总使用巷道数,并从小到大排序，分配权重
            List<Integer> xiangdaoList =  wmsHBLocationStereoscopicMapper.selTunnelCount(wmsLocationStereoscopic);
            if(xiangdaoList!=null && !xiangdaoList.isEmpty()){
                List<Integer> result1 = new ArrayList<>();//取出差的数据
                result1 =  flooNumberOne.stream().filter(item->xiangdaoList.stream().allMatch(each->!item.equals(each))).collect(Collectors.toList());
                if(result1!=null && !result1.isEmpty()){
                    log.info("根据权重分配，此"+result1.get(0)+"层，未分配货物");
                    floorNumber = result1.get(0);
                }else{
                    log.info("此"+xiangdaoList.get(0)+"层，分配的巷道少");
                    floorNumber = xiangdaoList.get(0);
                }
            }else {
                log.info("巷道没有被占用，从1层开始入库");
                floorNumber=1;
            }

            wmsLocationStereoscopic.setGoodsCode(goodsCode);
            wmsLocationStereoscopic.setBatchNo(batchNo);
            wmsLocationStereoscopic.setFloorNumberList(list);
            // 添加巷道是否有出库任务，有出库任何，换巷道推荐货位  ：任务状态 1：创建未执行，7 待执行，8 入库任务结束后执行
            log.info("查询 同批次的，可用货位  -开始");
            List<WmsLocationStereoscopic> tongpi_Location= wmsHBLocationStereoscopicMapper.getLocationCodeByUseStatus(wmsLocationStereoscopic);
            log.info("查询 同批次的，可用货位  -结束");
            if(tongpi_Location!=null && tongpi_Location.size()>0){
                locationCode = selectlocationCodeByfloor1(goodsCode, batchNo, locationCode, tongpi_Location);
                log.info("判断是否存在同批次，巷道有空位置的："+locationCode.toString());
            }
            if(locationCode==null || "".equals(locationCode)){

                List<Integer> tongpi_Ceng= wmsHBLocationStereoscopicMapper.getZhanyongCeng(wmsLocationStereoscopic);
                if(tongpi_Ceng!=null &&!tongpi_Ceng.isEmpty()){
                    //清空层数
                    list = new ArrayList();
                    log.info("查询同批次，所在层数:"+tongpi_Ceng.get(0));
                    list.add(tongpi_Ceng.get(0));
                }else{
                    //清空层数
                    list = new ArrayList();
                    log.info("未查询到同批次，推荐层数:"+floorNumber);
                    list.add(floorNumber);
                }
                log.info("查询空巷道信息");
                wmsLocationStereoscopic.setFloorNumberList(list);
                List<WmsLocationStereoscopic> kongxiangdaoList =  wmsHBLocationStereoscopicMapper.getKongXiangDao(wmsLocationStereoscopic);
                if (kongxiangdaoList != null && kongxiangdaoList.size() > 0) {
                    locationCode = selectlocationCodeByfloor1(goodsCode, batchNo, locationCode, kongxiangdaoList);
                    //空巷道，找库位
                    log.info("空巷道，找库位:"+locationCode);
                }
            }
            log.info("推荐货位："+locationCode);
            if ("".equals(locationCode)) {
                resp.setCode(RESULT.FAILED.code);
                resp.setMsg("没有找到合适的库位！");
                return resp;
            } else {
                //自查 货位信息查询
                JSONObject jsonObject =new JSONObject();
                jsonObject.put("warehouse","L-NH01");
                jsonObject.put("layer",Integer.parseInt(locationCode.substring(locationCode.length()-1)));
                jsonObject.put("cargoLocationId",locationCode);
                // TODO 自查 货位信息查询
                JSONObject returnJsonObject = sLWCSService.cargoLocationStatus(jsonObject);
                //模拟返回
//                String str="{ \"returnStatus\": 0, \"msg\": \"操作成功\",\"data\": null}";
//                JSONObject returnJsonObject = JSONObject.parseObject(str);
                log.info("货位信息查询"+returnJsonObject);
                if(returnJsonObject!=null && returnJsonObject.getInteger("returnStatus").equals(1) ){
                    resp.setCode(RESULT.FAILED.code);
                    resp.setMsg("货位信息查询: 请求速锐WCS请求结果失败:"+returnJsonObject.getInteger("returnStatus"));
                    return resp;
                }else if(returnJsonObject!=null && returnJsonObject.getInteger("returnStatus").equals(2)){
                    resp.setCode(RESULT.FAILED.code);
                    resp.setMsg("货位信息查询: 请求速锐WCS -超时！");
                    return resp;
                }else{
                    // TODO 货位信息查询，入库任务下发
                    JSONArray jArray3 = returnJsonObject.getJSONArray("data");
                    if(jArray3!=null &&!"".equals(jArray3)) {
                        for (int i = 0; i < jArray3.size(); i++) {
                            JSONObject object3 = jArray3.getJSONObject(i);
                            if ("Y".equals(object3.getString("cargoLocationStatus"))) {
                                resp.setCode(RESULT.FAILED.code);
                                resp.setMsg("货位信息查询: 请求速锐WCS 货位状态有货:" + object3.getString("cargoLocationStatus"));
                                return resp;
                            }
                        }
                    }
                    //判断是否有出库任务，有出库任务，暂停入库
                    WmsTaskExecutionLog chukuLog=new WmsTaskExecutionLog();
                    chukuLog.setTaskStatus(String.valueOf(Constant.TaskType.STRAIGHT_OUT));

                    //成功 且无货，下发入库任务
                    JSONObject ruku_jsonObject =new JSONObject();
                    Map<String,Object> map =new HashMap<>();
                    //组号
                    ruku_jsonObject.put("groupId", ConstantUtil.UUID_GroupId());
                    //下发时间
                    ruku_jsonObject.put("msgTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    //优先级
                    ruku_jsonObject.put("priorityCode", "");
                    //仓库编码
                    ruku_jsonObject.put("warehouse", "L-NH01");
                    //任务单号
                    map.put("taskId", wmsTaskExecutionLog.getTaskId());
                    //任务类型
                    map.put("taskType", "0");
                    //任务起点
                    map.put("startNode", "2001");
                    //任务终点
                    map.put("endNode", locationCode);
                    //托盘编号
                    map.put("barCode", wmsTaskExecutionLog.getPalletCode());
                    map.put("order", 1);
                    //tasks
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.add( new JSONObject(map));
                    ruku_jsonObject.put("tasks", jsonArray);
                    //调速锐的 入库：任务下发接口
                    log.info("调速锐的 入库：任务下发接口 开始!");
                    JSONObject rukuJsonObject = sLWCSService.taskReceive(ruku_jsonObject);
                    //模拟返回
//                    String str1="{ \"returnStatus\": 0, \"msg\": \"操作成功\",\"data\": null}";
//                    JSONObject rukuJsonObject = JSONObject.parseObject(str1);
                    log.info("调速锐的 入库：任务下发接口 结束!");
                    if(rukuJsonObject.getInteger("returnStatus")!=0){
                        resp.setCode(RESULT.FAILED.code);
                        resp.setMsg(rukuJsonObject.getString("returnInfo"));
                        return resp;
                    }
                }

                WmsLocationStereoscopic updateOb = new WmsLocationStereoscopic();
                updateOb.setLocationCode(locationCode);
                updateOb.setUseStatus("1");
                updateOb.setLastModifiedBy("wcs_query_in_location");
                updateOb.setGmtModified(new Date());

                this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb);
                resp.setCode(RESULT.SUCCESS.code);
                resp.setData(locationCode);
                return resp;
            }
        } else {
            resp.setCode(RESULT.FAILED.code);
            resp.setMsg("楼层巷道全被占用，没有找到合适的库位！");
            return resp;
        }
    }
    /**
     *功能描述:
     * 查询所有的库位，根据指定层，查找推荐库位。
     * @params goodsCode 商品编号
     * @params batchNo 批次号
     * @params locationCode 库位
     * @params listRight 所有库位
     * @params list  层数
     * @return java.lang.String
     */
    public String selectlocationCodeByfloor(String goodsCode, String batchNo, String locationCode, List<WmsLocationStereoscopic> listRight, List list) {
       //控制巷道大小问题。  将查到信息 进行排序，
        List mixList = new ArrayList();
        List maxList = new ArrayList();
        for (WmsLocationStereoscopic w : listRight) {
            int number = Integer.parseInt(w.getLocationCode().substring(0, 2));
            if (number < 15) {
                mixList.add(w);
            } else {
                maxList.add(w);
            }
        }

        ListIterator<Integer> li = mixList.listIterator();
        // 将游标定位到集合的结尾
        while (li.hasNext()) {
            li.next();
        }
        // 迭代器遍历hasPrevious()方法用于反向遍历的时候判断是否还有下一个元素
        while (li.hasPrevious()) {
            maxList.add(li.previous());
        }
        listRight = new ArrayList<>();
        listRight.addAll(maxList);

        m: for(int j = 0; j< list.size(); j++ ) {
           //查找合适的库位
          g: for(int i = 0; i< listRight.size(); i++ ){
                   //判断同层的，可用的推荐货位
               if (listRight.get(i).getFloorNumber().equals(list.get(j))) {
                   //查询此列是否被锁，此列如果被锁, (再以15为界限，判断)  结束本次循环。
                   List<WmsLocationStereoscopic> logkList = this.wmsHBLocationStereoscopicMapper.getLocationorlockBy(listRight.get(i));
                    if(logkList!=null && logkList.size()>0){
                        for(int n=0 ;n<logkList.size();n++){
                            if(logkList.get(n).getLayerNumber()<15 && listRight.get(i).getLayerNumber()<15){
                                continue g;
                            }
                            if(logkList.get(n).getLayerNumber()>15 && listRight.get(i).getLayerNumber()>15){
                                continue g;
                            }
                        }
                    }
                   //判断此巷道是否在短巷道内，如果在短巷道
                   int  shelvesNumber = listRight.get(i).getShelvesNumber();
                   List<Integer> shelvesNumberList = Arrays.asList(1,3,13,15,25,27,37,39);
                   //如果是短巷道，查询短巷道阻隔sql语句
                   List<WmsLocationStereoscopic> LocationStereoscopicList=null;
                   if(shelvesNumberList.contains(shelvesNumber)){
                       log.info("短巷道：");
                        LocationStereoscopicList = this.wmsHBLocationStereoscopicMapper.getLocationPassDuanDao(listRight.get(i));
                   }else {
                       log.info("非短巷道：");
                       //判断向道是否被阻隔,有数据即被阻隔
                       LocationStereoscopicList = this.wmsHBLocationStereoscopicMapper.getLocationPass(listRight.get(i));
                   }
                   if (LocationStereoscopicList==null ||LocationStereoscopicList.size() == 0) {
                       log.info("向道未被阻隔：层" + listRight.get(i).getFloorNumber() + "行:" + listRight.get(i).getLocationCode());
                       WmsLocationStereoscopic searchObNew = new WmsLocationStereoscopic();
                       searchObNew.setGoodsCode(goodsCode);
                       searchObNew.setBatchNo(batchNo);
                       List list1 = new ArrayList();
                       list1.add(listRight.get(i).getFloorNumber());
                       searchObNew.setFloorNumberList(list1);
                       searchObNew.setColumnNumber(listRight.get(i).getColumnNumber());
                       // y列，15是个走道
                       if (listRight.get(i).getLayerNumber() > 15) {
                           log.info("推荐的库位，列大于15，查找此行，最里面的库位！");
                           //当y大于15的时候，从数字大的排序
                           List lisnew = this.wmsHBLocationStereoscopicMapper.getLocationInfoByFloorMax(searchObNew);
                           if (lisnew != null && lisnew.size() > 0) {
                               locationCode = ((WmsLocationStereoscopic) lisnew.get(0)).getLocationCode();
                               log.info("货位：" + locationCode);
                               break m;
                           }
                       } else {
                           //推荐的库位小于15 查询此行，最小的库位
                           log.info("推荐的库位，列小于15，查找此行，最里面的库位！");
                           locationCode = ((WmsLocationStereoscopic) listRight.get(i)).getLocationCode();
                           break m;
                       }
                   }

               }
           }
       }
       log.info("推荐的最终货位："+locationCode);
        return locationCode;
    }
    //=================================现场调试================================
    /**
     *功能描述: 推荐货位
     * @params
     * @return java.lang.String
     */
    public String selectlocationCodeByfloor1(String goodsCode, String batchNo, String locationCode, List<WmsLocationStereoscopic> listRight) {
       //控制巷道大小问题。  将查到信息 进行排序，
        List mixList = new ArrayList();
        List maxList = new ArrayList();
        for (WmsLocationStereoscopic w : listRight) {
            int number = Integer.parseInt(w.getLocationCode().substring(0, 2));
            if (number < 15) {
                mixList.add(w);
            } else {
                maxList.add(w);
            }
        }

        ListIterator<Integer> li = mixList.listIterator();
        // 将游标定位到集合的结尾
        while (li.hasNext()) {
            li.next();
        }
        // 迭代器遍历hasPrevious()方法用于反向遍历的时候判断是否还有下一个元素
        while (li.hasPrevious()) {
            maxList.add(li.previous());
        }
        listRight = new ArrayList<>();
        listRight.addAll(maxList);
           //查找合适的库位
          g: for(int i = 0; i< listRight.size(); i++ ){
                   //查询此列是否被锁，此列如果被锁, (再以15为界限，判断)  结束本次循环。
                   List<WmsLocationStereoscopic> logkList = this.wmsHBLocationStereoscopicMapper.getLocationorlockBy(listRight.get(i));
                    if(logkList!=null && logkList.size()>0){
                        for(int n=0 ;n<logkList.size();n++){
                            if(logkList.get(n).getLayerNumber()<15 && listRight.get(i).getLayerNumber()<15){
                                continue g;
                            }
                            if(logkList.get(n).getLayerNumber()>15 && listRight.get(i).getLayerNumber()>15){
                                continue g;
                            }
                        }
                    }
                  //判断此巷道是否在短巷道内，如果在短巷道
                  int  shelvesNumber = listRight.get(i).getShelvesNumber();
                  List<Integer> shelvesNumberList = Arrays.asList(1,3,13,15,25,27,37,39);
                  //如果是短巷道，查询短巷道阻隔sql语句
                  List<WmsLocationStereoscopic> LocationStereoscopicList=null;
                  if(shelvesNumberList.contains(shelvesNumber)){
                      log.info("短巷道：");
                      LocationStereoscopicList = this.wmsHBLocationStereoscopicMapper.getLocationPassDuanDao(listRight.get(i));
                  }else {
                      log.info("非短巷道：");
                      //判断向道是否被阻隔,有数据即被阻隔
                      LocationStereoscopicList = this.wmsHBLocationStereoscopicMapper.getLocationPass(listRight.get(i));
                  }
                  if (LocationStereoscopicList.size() == 0) {
                       log.info("向道未被阻隔 且巷道没有出库任务：层" + listRight.get(i).getFloorNumber() + "行:" + listRight.get(i).getLocationCode());
                      /* WmsLocationStereoscopic searchObNew = new WmsLocationStereoscopic();
                       searchObNew.setGoodsCode(goodsCode);
                       searchObNew.setBatchNo(batchNo);
                       List list1 =new ArrayList();
                       list1.add(listRight.get(i).getFloorNumber());
                       searchObNew.setFloorNumberList(list1);
                       searchObNew.setShelvesNumber(listRight.get(i).getShelvesNumber());
                       // y列，15是个走道
                       if (listRight.get(i).getLayerNumber() > 15) {
                           log.info("推荐的库位，列大于15，查找此行，最里面的库位！");
                           //当y大于15的时候，从数字大的排序
                           List lisnew = this.wmsHBLocationStereoscopicMapper.getLocationInfoByFloorMax(searchObNew);
                           if (lisnew != null && lisnew.size() > 0) {
                               locationCode = ((WmsLocationStereoscopic) lisnew.get(0)).getLocationCode();
                               log.info("货位："+locationCode);
                               break g;
                           }
                       } else {
                           //推荐的库位小于15 查询此行，最小的库位
                           log.info("推荐的库位，列小于15，查找此行，最里面的库位！");
                           locationCode =((WmsLocationStereoscopic) listRight.get(i)).getLocationCode();
                           break g;
                       }*/
                      locationCode =((WmsLocationStereoscopic) listRight.get(i)).getLocationCode();
                      log.info("货位："+locationCode);
                      break g;
                   }
               }
           log.info("推荐的最终货位："+locationCode);
            return locationCode;
    }


    /**
     * @return
     * @Description 立体库入库时，推荐库位状态回滚成初始状态0可用
     * @Author luot
     * @Date 2020/2/20 19:36
     * @Param
     **/
    public Resp revertLocationStatus0(String locationCode) {
        WmsLocationStereoscopic updateOb = new WmsLocationStereoscopic();
        updateOb.setLocationCode(locationCode);
        updateOb.setUseStatus("0");
        updateOb.setLastModifiedBy("wsc_query_in_location_revert");
        updateOb.setGmtModified(new Date());
        this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb);
        return this.success();
    }

    public Resp queryRecommendLocationCode(String goodsCode, String batchNo, String areaCode) {
        Resp resp = new Resp();
        String locationCode = "";
        Boolean bvFlag = false;
        WmsGoods wmsGoods = this.wmsGoodsMapper.queryByCode(goodsCode);
        if (wmsGoods != null && wmsGoods.getGoodsType() != null && ("0".equals(wmsGoods.getGoodsType()) || "3".equals(wmsGoods.getGoodsType()))) {
            bvFlag = true;
        }

        log.info("是否包材或虚拟托盘={}", bvFlag);
        long t1 = System.currentTimeMillis();
        List<WmsLocationStereoscopic> allLocationList = this.wmsHBLocationStereoscopicMapper.getAllLocationInfo();
        long t2 = System.currentTimeMillis();
        log.info("##################推荐库位算法SQL查询耗时：" + (t2 - t1) + "ms！#########################");
        long t3 = System.currentTimeMillis();
        List<WmsLocationStereoscopic> virtualPalletRangeList = new ArrayList();
        List<WmsLocationStereoscopic> normalGoodsRangeList = new ArrayList();
        List<WmsLocationStereoscopic> normalGoodsBeiHuoRangeList = new ArrayList();
        Iterator var18 = allLocationList.iterator();

        while(true) {
            while(var18.hasNext()) {
                WmsLocationStereoscopic tmp = (WmsLocationStereoscopic)var18.next();
                if (1 == tmp.getFloorNumber() && 3 == tmp.getShelvesNumber() && 5 >= tmp.getLayerNumber()) {
                    virtualPalletRangeList.add(tmp);
                } else if (tmp.getShelvesNumber() == 4) {
                    normalGoodsBeiHuoRangeList.add(tmp);
                } else {
                    normalGoodsRangeList.add(tmp);
                }
            }

            log.info("######静态变量楼层：5#######");
            if (bvFlag) {
                locationCode = this.getReVirtualPalletLocation(virtualPalletRangeList);
            }

            if ("".equals(locationCode)) {
                List<Integer> list = FloorNumGenerate.getRandomFloorList();
                List<WmsTaskExecutionLog> runTaskList = this.wmsTaskExecutionLogMapper.queryRunInWhTask();
                if (runTaskList != null && !runTaskList.isEmpty()) {
                    List<Integer> listBig2 = new LinkedList();
                    List<Integer> listLess2 = new LinkedList();
                    Iterator var22 = ((List)list).iterator();

                    while(true) {
                        if (!var22.hasNext()) {
                            list = new LinkedList();
                            ((List)list).addAll(listLess2);
                            ((List)list).addAll(listBig2);
                            break;
                        }

                        Integer tmp = (Integer)var22.next();
                        int num = 0;
                        Iterator var25 = runTaskList.iterator();

                        while(var25.hasNext()) {
                            WmsTaskExecutionLog taskTmp = (WmsTaskExecutionLog)var25.next();
                            if (taskTmp.getLocationCode() != null && taskTmp.getLocationCode().startsWith(Integer.toString(tmp))) {
                                ++num;
                                if (num >= 2) {
                                    break;
                                }
                            }
                        }

                        if (num >= 2) {
                            listBig2.add(tmp);
                        } else {
                            listLess2.add(tmp);
                        }
                    }
                }

                Iterator var30 = ((List)list).iterator();

                Integer tmp;
                while(var30.hasNext()) {
                    tmp = (Integer)var30.next();
                    log.info("#################遍历" + tmp + "楼！#########################");
                    locationCode = this.getRecommendLocation(normalGoodsRangeList, goodsCode, batchNo, tmp);
                    if (!"".equals(locationCode)) {
                        log.info("#################推荐库位" + locationCode + "#########################");
                        break;
                    }

                    log.info("#################没有可推荐库位#########################");
                }

                if ("".equals(locationCode)) {
                    var30 = ((List)list).iterator();

                    while(var30.hasNext()) {
                        tmp = (Integer)var30.next();
                        locationCode = this.getRecommendLocation(normalGoodsBeiHuoRangeList, goodsCode, batchNo, tmp);
                        if (!"".equals(locationCode)) {
                            break;
                        }
                    }
                }
            }

            long t4 = System.currentTimeMillis();
            log.info("##################内存库位遍历查找耗时：" + (t4 - t3) + "ms！#########################");
            if ("".equals(locationCode)) {
                resp.setCode(RESULT.FAILED.code);
                resp.setMsg("没有找到合适的库位！");
                return resp;
            }

            long t5 = System.currentTimeMillis();
            WmsLocationStereoscopic updateOb = new WmsLocationStereoscopic();
            updateOb.setLocationCode(locationCode);
            updateOb.setUseStatus("1");
            updateOb.setLastModifiedBy("wcs_query_in_location");
            updateOb.setGmtModified(new Date());
            this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb);
            long t6 = System.currentTimeMillis();
            log.info("##################更新库位状态耗时：" + (t6 - t5) + "ms！#########################");
            resp.setCode(RESULT.SUCCESS.code);
            resp.setData(locationCode);
            return resp;
        }
    }

    public Resp querySortingRecommendLocationCode(String goodsCode, String batchNo, String areaCode) {
        Resp resp = new Resp();
        String locationCode = "";
        long t1 = System.currentTimeMillis();
        List<WmsLocationStereoscopic> allLocationList = this.wmsHBLocationStereoscopicMapper.getAllLocationInfo();
        long t2 = System.currentTimeMillis();
        log.info("##################推荐库位算法SQL查询耗时：" + (t2 - t1) + "ms！#########################");
        long t3 = System.currentTimeMillis();
        List<WmsLocationStereoscopic> virtualPalletRangeList = new ArrayList();
        List<WmsLocationStereoscopic> normalGoodsRangeList = new ArrayList();
        List<WmsLocationStereoscopic> normalGoodsBeiHuoRangeList = new ArrayList();
        Iterator var16 = allLocationList.iterator();

        while(true) {
            while(var16.hasNext()) {
                WmsLocationStereoscopic tmp = (WmsLocationStereoscopic)var16.next();
                if (1 == tmp.getFloorNumber() && 3 == tmp.getShelvesNumber() && 5 >= tmp.getLayerNumber()) {
                    virtualPalletRangeList.add(tmp);
                } else if (tmp.getShelvesNumber() == 4) {
                    normalGoodsBeiHuoRangeList.add(tmp);
                } else {
                    normalGoodsRangeList.add(tmp);
                }
            }

            Integer floorNumber = 1;
            locationCode = this.getRecommendLocation(normalGoodsRangeList, goodsCode, batchNo, floorNumber);
            if ("".equals(locationCode)) {
                locationCode = this.getRecommendLocation(normalGoodsBeiHuoRangeList, goodsCode, batchNo, floorNumber);
            }

            long t4 = System.currentTimeMillis();
            log.info("##################内存库位遍历查找耗时：" + (t4 - t3) + "ms！#########################");
            if ("".equals(locationCode)) {
                resp.setCode(RESULT.FAILED.code);
                resp.setMsg("没有找到合适的库位！");
                return resp;
            }

            long t5 = System.currentTimeMillis();
            WmsLocationStereoscopic updateOb = new WmsLocationStereoscopic();
            updateOb.setLocationCode(locationCode);
            updateOb.setUseStatus("1");
            updateOb.setLastModifiedBy("wcs_query_in_location");
            updateOb.setGmtModified(new Date());
            this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb);
            long t6 = System.currentTimeMillis();
            log.info("##################更新库位状态耗时：" + (t6 - t5) + "ms！#########################");
            resp.setCode(RESULT.SUCCESS.code);
            resp.setData(locationCode);
            return resp;
        }
    }

    public String getReVirtualPalletLocation(List<WmsLocationStereoscopic> locationList) {
        String recommendLocation = "";
        List<List<WmsLocationStereoscopic>> allList = new ArrayList();
        List<List<WmsLocationStereoscopic>> notContainLockList = new ArrayList();
        List<List<WmsLocationStereoscopic>> emptyList = new ArrayList();
        List<List<WmsLocationStereoscopic>> containLockList = new ArrayList();
        Integer tmpFloorNum = 0;
        Integer tmpShelvelsNum = 0;
        Integer tmpLayerNum = 0;
        List<WmsLocationStereoscopic> tranList = new ArrayList();
        Iterator var11 = locationList.iterator();

        while(var11.hasNext()) {
            WmsLocationStereoscopic tmp = (WmsLocationStereoscopic)var11.next();
            if (tmpFloorNum != tmp.getFloorNumber() || tmpShelvelsNum != tmp.getShelvesNumber() || tmpLayerNum != tmp.getLayerNumber()) {
                tranList = new ArrayList();
                tmpFloorNum = tmp.getFloorNumber();
                tmpShelvelsNum = tmp.getShelvesNumber();
                tmpLayerNum = tmp.getLayerNumber();
            }

            tranList.add(tmp);
            if (!allList.contains(tranList)) {
                allList.add(tranList);
            }
        }

        var11 = allList.iterator();

        List tmpList;
        while(var11.hasNext()) {
            tmpList = (List)var11.next();
            Boolean containLockFlag = false;
            Boolean flag_3 = false;
            Iterator var15 = tmpList.iterator();

            while(true) {
                while(var15.hasNext()) {
                    WmsLocationStereoscopic tmp = (WmsLocationStereoscopic)var15.next();
                    if (tmp.getUseStatus() != null && ("1".equals(tmp.getUseStatus()) || "2".equals(tmp.getUseStatus()) || "4".equals(tmp.getUseStatus()))) {
                        containLockFlag = true;
                    } else if (tmp.getUseStatus() != null && "3".equals(tmp.getUseStatus())) {
                        flag_3 = true;
                    }
                }

                if (containLockFlag) {
                    containLockList.add(tmpList);
                } else if (flag_3) {
                    notContainLockList.add(tmpList);
                } else {
                    emptyList.add(tmpList);
                }
                break;
            }
        }

        var11 = notContainLockList.iterator();

        int i;
        WmsLocationStereoscopic tmp;
        WmsLocationStereoscopic after;
        while (var11.hasNext()) {
            tmpList = (List) var11.next();

            for (i = 0; i < tmpList.size() - 1; ++i) {
                tmp = (WmsLocationStereoscopic) tmpList.get(i);
                if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                    break;
                }

                after = (WmsLocationStereoscopic) tmpList.get(i + 1);
                if (after.getUseStatus() != null && "3".equals(after.getUseStatus())) {
                    recommendLocation = tmp.getLocationCode();
                    break;
                }
            }

            if (!"".equals(recommendLocation)) {
                break;
            }

            Collections.reverse(tmpList);

            for (i = 0; i < tmpList.size() - 1; ++i) {
                tmp = (WmsLocationStereoscopic) tmpList.get(i);
                if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                    break;
                }

                after = (WmsLocationStereoscopic) tmpList.get(i + 1);
                if (after.getUseStatus() != null && "3".equals(after.getUseStatus())) {
                    recommendLocation = tmp.getLocationCode();
                    break;
                }
            }

            if (!"".equals(recommendLocation)) {
                break;
            }
        }

        if ("".equals(recommendLocation) && !emptyList.isEmpty()) {
            recommendLocation = ((WmsLocationStereoscopic) ((List) emptyList.get(0)).get(((List) emptyList.get(0)).size() / 2)).getLocationCode();
        }

        if ("".equals(recommendLocation)) {
            var11 = containLockList.iterator();

            while (var11.hasNext()) {
                tmpList = (List) var11.next();

                for (i = 0; i < tmpList.size() - 1; ++i) {
                    tmp = (WmsLocationStereoscopic) tmpList.get(i);
                    if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                        break;
                    }

                    after = (WmsLocationStereoscopic) tmpList.get(i + 1);
                    if (after.getUseStatus() != null && "3".equals(after.getUseStatus())) {
                        recommendLocation = tmp.getLocationCode();
                        break;
                    }
                }

                if (!"".equals(recommendLocation)) {
                    break;
                }

                Collections.reverse(tmpList);

                for (i = 0; i < tmpList.size() - 1; ++i) {
                    tmp = (WmsLocationStereoscopic) tmpList.get(i);
                    if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                        break;
                    }

                    after = (WmsLocationStereoscopic) tmpList.get(i + 1);
                    if (after.getUseStatus() != null && "3".equals(after.getUseStatus())) {
                        recommendLocation = tmp.getLocationCode();
                        break;
                    }
                }

                if (!"".equals(recommendLocation)) {
                    break;
                }
            }
        }

        return recommendLocation;
    }

    /**
     * @return
     * @Description 托盘入库 从某一楼层 推荐库位
     * @Author luot
     * @Date 2020/7/24 16:45
     * @Param
     **/
    public String getRecommendLocation(List<WmsLocationStereoscopic> locationList, String goodsCode, String batchNo, Integer floorNum) {
        String recommendLocation = "";
        List<List<WmsLocationStereoscopic>> allList = new ArrayList();
        List<List<WmsLocationStereoscopic>> notContainLockList = new ArrayList();
        List<List<WmsLocationStereoscopic>> emptyList = new ArrayList();
        List<List<WmsLocationStereoscopic>> containLockList = new ArrayList();
        List<WmsLocationStereoscopic> floorLocationList = new ArrayList();
        Iterator var11 = locationList.iterator();

        while (var11.hasNext()) {
            WmsLocationStereoscopic tmp = (WmsLocationStereoscopic) var11.next();
            if (floorNum == tmp.getFloorNumber()) {
                floorLocationList.add(tmp);
            }
        }

        Integer tmpShelvelsNum = 0;
        Integer tmpLayerNum = 0;
        List<WmsLocationStereoscopic> tranList = new ArrayList();
        Iterator var14 = floorLocationList.iterator();

        while (var14.hasNext()) {
            WmsLocationStereoscopic tmp = (WmsLocationStereoscopic) var14.next();
            if (tmpShelvelsNum != tmp.getShelvesNumber() || tmpLayerNum != tmp.getLayerNumber()) {
                tranList = new ArrayList();
                tmpShelvelsNum = tmp.getShelvesNumber();
                tmpLayerNum = tmp.getLayerNumber();
            }

            tranList.add(tmp);
            if (!allList.contains(tranList)) {
                allList.add(tranList);
            }
        }

        var14 = allList.iterator();

        while (var14.hasNext()) {
            List<WmsLocationStereoscopic> tmpList = (List) var14.next();
            Boolean containLockFlag = false;
            Boolean flag_3 = false;
            HashSet<String> goodsCodeSet = new HashSet();
            Iterator var19 = tmpList.iterator();

            while (var19.hasNext()) {
                WmsLocationStereoscopic tmp = (WmsLocationStereoscopic) var19.next();
                if (tmp.getUseStatus() != null && ("1".equals(tmp.getUseStatus()) || "2".equals(tmp.getUseStatus()) || "4".equals(tmp.getUseStatus()))) {
                    containLockFlag = true;
                } else if (tmp.getUseStatus() != null && "3".equals(tmp.getUseStatus())) {
                    flag_3 = true;
                }

                if (tmp.getGoodsCode() != null && !"".equals(tmp.getGoodsCode())) {
                    goodsCodeSet.add(tmp.getGoodsCode());
                }
            }

            if (goodsCodeSet.size() <= 2) {
                if (containLockFlag) {
                    containLockList.add(tmpList);
                } else if (flag_3) {
                    notContainLockList.add(tmpList);
                } else {
                    emptyList.add(tmpList);
                }
            }
        }

        recommendLocation = this.getLocation(notContainLockList, goodsCode, batchNo);
        if ("".equals(recommendLocation) && !emptyList.isEmpty()) {
            Integer shelvelsNum = ((WmsLocationStereoscopic) ((List) emptyList.get(0)).get(0)).getShelvesNumber();
            if (shelvelsNum == 4) {
                recommendLocation = ((WmsLocationStereoscopic) ((List) emptyList.get(0)).get(((List) emptyList.get(0)).size() - 1)).getLocationCode();
            } else if (shelvelsNum == 20) {
                recommendLocation = ((WmsLocationStereoscopic) ((List) emptyList.get(0)).get(((List) emptyList.get(0)).size() - 1)).getLocationCode();
            } else if (shelvelsNum == 21) {
                recommendLocation = ((WmsLocationStereoscopic) ((List) emptyList.get(0)).get(0)).getLocationCode();
            } else if (shelvelsNum == 1) {
                recommendLocation = ((WmsLocationStereoscopic) ((List) emptyList.get(0)).get(0)).getLocationCode();
            } else {
                recommendLocation = ((WmsLocationStereoscopic) ((List) emptyList.get(0)).get(((List) emptyList.get(0)).size() / 2)).getLocationCode();
            }
        }

        if ("".equals(recommendLocation)) {
            recommendLocation = this.getLocation(containLockList, goodsCode, batchNo);
        }

        return recommendLocation;
    }

    /**
     * @return
     * @Description 从指定范围的库位找推荐库位
     * @Author luot
     * @Date 2020/7/24 17:21
     * @Param
     **/
    public String getLocation(List<List<WmsLocationStereoscopic>> list, String goodsCode, String batchNo) {
        String recommendLocation = "";
        Iterator var5 = list.iterator();

        List tmpList;
        Integer shelvelsNum;
        WmsLocationStereoscopic tmp;
        while (var5.hasNext()) {
            tmpList = (List) var5.next();
            shelvelsNum = ((WmsLocationStereoscopic) tmpList.get(0)).getShelvesNumber();
            int i;
//            WmsLocationStereoscopic tmp;
            if (shelvelsNum != 1 && shelvelsNum != 21) {
                if (shelvelsNum != 4 && shelvelsNum != 20) {
                    for (i = 0; i < tmpList.size() - 1; ++i) {
                        tmp = (WmsLocationStereoscopic) tmpList.get(i);
                        if (tmp.getLayerNumber() <= 3 && shelvelsNum == 2 || tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                            break;
                        }

                        tmp = (WmsLocationStereoscopic) tmpList.get(i + 1);
                        if (tmp.getUseStatus() != null && "3".equals(tmp.getUseStatus())) {
                            if ("pallet".equals(goodsCode)) {
                                if (tmp.getGoodsCode() != null && goodsCode.equals(tmp.getGoodsCode())) {
                                    recommendLocation = tmp.getLocationCode();
                                    break;
                                }
                            } else if (tmp.getGoodsCode() != null && goodsCode.equals(tmp.getGoodsCode()) && tmp.getBatchNo() != null && batchNo.equals(tmp.getBatchNo())) {
                                recommendLocation = tmp.getLocationCode();
                                break;
                            }
                        }
                    }

                    if (!"".equals(recommendLocation)) {
                        break;
                    }

                    Collections.reverse(tmpList);

                    for (i = 0; i < tmpList.size() - 1; ++i) {
                        tmp = (WmsLocationStereoscopic) tmpList.get(i);
                        if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                            break;
                        }

                        tmp = (WmsLocationStereoscopic) tmpList.get(i + 1);
                        if (tmp.getUseStatus() != null && "3".equals(tmp.getUseStatus())) {
                            if ("pallet".equals(goodsCode)) {
                                if (tmp.getGoodsCode() != null && goodsCode.equals(tmp.getGoodsCode())) {
                                    recommendLocation = tmp.getLocationCode();
                                    break;
                                }
                            } else if (tmp.getGoodsCode() != null && goodsCode.equals(tmp.getGoodsCode()) && tmp.getBatchNo() != null && batchNo.equals(tmp.getBatchNo())) {
                                recommendLocation = tmp.getLocationCode();
                                break;
                            }
                        }
                    }

                    if (!"".equals(recommendLocation)) {
                        break;
                    }
                } else {
                    for (i = 0; i < tmpList.size() - 1; ++i) {
                        tmp = (WmsLocationStereoscopic) tmpList.get(i);
                        if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                            break;
                        }

                        tmp = (WmsLocationStereoscopic) tmpList.get(i + 1);
                        if (tmp.getUseStatus() != null && "3".equals(tmp.getUseStatus())) {
                            if ("pallet".equals(goodsCode)) {
                                if (tmp.getGoodsCode() != null && goodsCode.equals(tmp.getGoodsCode())) {
                                    recommendLocation = tmp.getLocationCode();
                                    break;
                                }
                            } else if (tmp.getGoodsCode() != null && goodsCode.equals(tmp.getGoodsCode()) && tmp.getBatchNo() != null && batchNo.equals(tmp.getBatchNo())) {
                                recommendLocation = tmp.getLocationCode();
                                break;
                            }
                        }
                    }

                    if (!"".equals(recommendLocation)) {
                        break;
                    }
                }
            } else {
                Collections.reverse(tmpList);

                for (i = 0; i < tmpList.size() - 1; ++i) {
                    tmp = (WmsLocationStereoscopic) tmpList.get(i);
                    if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                        break;
                    }

                    tmp = (WmsLocationStereoscopic) tmpList.get(i + 1);
                    if (tmp.getUseStatus() != null && "3".equals(tmp.getUseStatus())) {
                        if ("pallet".equals(goodsCode)) {
                            if (tmp.getGoodsCode() != null && goodsCode.equals(tmp.getGoodsCode())) {
                                recommendLocation = tmp.getLocationCode();
                                break;
                            }
                        } else if (tmp.getGoodsCode() != null && goodsCode.equals(tmp.getGoodsCode()) && tmp.getBatchNo() != null && batchNo.equals(tmp.getBatchNo())) {
                            recommendLocation = tmp.getLocationCode();
                            break;
                        }
                    }
                }

                if (!"".equals(recommendLocation)) {
                    break;
                }
            }
        }

        if ("".equals(recommendLocation)) {
            var5 = list.iterator();

            do {
                HashSet goodsCodeSet;
                do {
                    do {
                        if (!var5.hasNext()) {
                            return recommendLocation;
                        }

                        tmpList = (List) var5.next();
                        shelvelsNum = ((WmsLocationStereoscopic) tmpList.get(0)).getShelvesNumber();
                    } while (shelvelsNum != 3);

                    goodsCodeSet = new HashSet();
                    Iterator var12 = tmpList.iterator();

                    while (var12.hasNext()) {
                        tmp = (WmsLocationStereoscopic) var12.next();
                        if (tmp.getLayerNumber() <= 3 && shelvelsNum == 2) {
                            break;
                        }

                        if (tmp.getGoodsCode() != null && !"".equals(tmp.getGoodsCode())) {
                            goodsCodeSet.add(tmp.getGoodsCode());
                        }
                    }
                } while (goodsCodeSet.size() != 1);

                WmsLocationStereoscopic after;
                int i;
                for (i = 0; i < tmpList.size() - 1; ++i) {
                    tmp = (WmsLocationStereoscopic) tmpList.get(i);
                    if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                        break;
                    }

                    after = (WmsLocationStereoscopic) tmpList.get(i + 1);
                    if (after.getUseStatus() != null && "3".equals(after.getUseStatus())) {
                        recommendLocation = tmp.getLocationCode();
                        break;
                    }
                }

                if (!"".equals(recommendLocation)) {
                    break;
                }

                Collections.reverse(tmpList);

                for (i = 0; i < tmpList.size() - 1; ++i) {
                    tmp = (WmsLocationStereoscopic) tmpList.get(i);
                    if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                        break;
                    }

                    after = (WmsLocationStereoscopic) tmpList.get(i + 1);
                    if (after.getUseStatus() != null && "3".equals(after.getUseStatus())) {
                        recommendLocation = tmp.getLocationCode();
                        break;
                    }
                }
            } while ("".equals(recommendLocation));
        }

        return recommendLocation;
    }

    /**
     * 功能描述: 批量出库 -出库单，批量启动
     * 不支持批量出库
     *
     * @return com.penghaisoft.framework.util.Resp
     * @params
     */
    public Resp queryOutRecommendLocationCode(WmsOrderOutStereoscopicDeail wmsOrderOutStereoscopicDeail, String targetAddress, String loginName) {
        Resp resp = new Resp();
        resp.setCode(RESULT.SUCCESS.code);
        //现场添加-盘库状态 22 出库状态20  TaskType.STRAIGHT_OUT  TaskType.CHECK_OUT
        String  taskType="" ;
        //根据 WmsOrderOutStereoscopicDeail
        WmsOrderOutStereoscopic wmsOrderOutStereoscopic =new WmsOrderOutStereoscopic();
        wmsOrderOutStereoscopic.setOrderNo(wmsOrderOutStereoscopicDeail.getOrderNo());
        WmsOrderOutStereoscopic wmsOrderOutStereoscopicNew = wmsOrderOutStereoscopicService.queryByOrderNo(wmsOrderOutStereoscopic);
        if(wmsOrderOutStereoscopicNew!=null && !"".equals(wmsOrderOutStereoscopicNew)){
            if(wmsOrderOutStereoscopicNew.getOrderType()!=null &&!"".equals(wmsOrderOutStereoscopicNew.getOrderType()) ){
                log.info("出库类型："+wmsOrderOutStereoscopicNew.getOrderType());
               taskType=wmsOrderOutStereoscopicNew.getOrderType();
            }
        }
        Integer findAmount = 0;
        List<WmsOutTask> wmsOutTaskList = new LinkedList();

        String goodsCode ="";
        String batchNo="";
        Integer planAmount=0;
        String userdefined2 ="";
        if(wmsOrderOutStereoscopicDeail!=null){
            goodsCode = wmsOrderOutStereoscopicDeail.getGoodsCode();
            batchNo=wmsOrderOutStereoscopicDeail.getBatchNo();
            planAmount = wmsOrderOutStereoscopicDeail.getPlanAmount();
            userdefined2= wmsOrderOutStereoscopicDeail.getUserDefined3();
        }
        WmsLocationStereoscopic seachOb = new WmsLocationStereoscopic();
        seachOb.setGoodsCode(goodsCode);
        seachOb.setBatchNo(batchNo);
        seachOb.setUserDefined2(userdefined2);
        Long useableNum;
        //查询可用商品数量
        useableNum = this.wmsHBLocationStereoscopicMapper.queryUseableGoodsAmount(seachOb);
        //可用商品数量比计划商品数量少，直接返回！
        if (useableNum < (long) planAmount) {
            resp.setCode(RESULT.FAILED.code);
            batchNo = batchNo == null || "".equals(batchNo) ? "无" : batchNo;
            resp.setMsg("(商品编码：" + goodsCode + "-批次号：" + batchNo + ")库存数量不足！");
            return resp;
        }
        //项目只有4层，找出相关的库位信息，修改库位状态。
        List<Integer> floorNumList = new ArrayList();
        floorNumList.add(1);
        floorNumList.add(2);
        floorNumList.add(3);
        floorNumList.add(4);
        long t1 = System.currentTimeMillis();

        WmsLocationStereoscopic seachObAll = new WmsLocationStereoscopic();
        seachObAll.setGoodsCode(goodsCode);
        seachObAll.setBatchNo(batchNo);
        seachObAll.setFloorNumberList(floorNumList);
        //查询所有符合条件的库位
        List<WmsLocationStereoscopic> listAll = this.wmsHBLocationStereoscopicMapper.getOutRihtSimpleLocation(seachObAll);
        //按时间顺序，查询批次号
        WmsGoods wmsGoods = new WmsGoods();
        wmsGoods.setGoodsCode(goodsCode);
        List<WmsGoods> listWmsGoods = this.wmsGoodsMapper.getHBBatchNo(wmsGoods);
        //将所有符合条件的库位，按照批次先后排序
        List<WmsLocationStereoscopic> paixu_list=new ArrayList<>();
        if(listWmsGoods!=null &&listWmsGoods.size()>0){
            for(int i=0; i<listWmsGoods.size();i++){
                for(int j =0;j<listAll.size();j++){
                    if(listWmsGoods.get(i).getBatchNo().equals(listAll.get(j).getBatchNo()) ){
                        paixu_list.add(listAll.get(j));
                    }
                }
            }
        }
        //待出货的货位
        List huoweiList = new ArrayList();
        if(paixu_list!=null && paixu_list.size()>0){

        //将查到信息 进行排序，
        List mixList = new ArrayList();
        List maxList = new ArrayList();
        for (WmsLocationStereoscopic w : paixu_list) {
            int number = Integer.parseInt(w.getLocationCode().substring(0, 2));
            if (number < 15) {
                mixList.add(w);
            } else {
                maxList.add(w);
            }
        }
        ListIterator<Integer> li = maxList.listIterator();
        // 将游标定位到集合的结尾
        while (li.hasNext()) {
            li.next();
        }
        // 迭代器遍历hasPrevious()方法用于反向遍历的时候判断是否还有下一个元素
        while (li.hasPrevious()) {
            mixList.add(li.previous());
        }
            paixu_list = new ArrayList<>();
            paixu_list.addAll(mixList);

            for (int i =0 ;i< paixu_list.size();i++) {
                findAmount = findAmount + paixu_list.get(i).getAmount();
                huoweiList.add(paixu_list.get(i).getLocationCode());
                WmsOutTask wmsOutTask = new WmsOutTask();
                wmsOutTask.setTaskId(wmsCommonService.getTaskIds(TaskType.HAND_IN, 1)[0]);
                wmsOutTask.setTargetAddress("2001");
                wmsOutTask.setOperator(loginName);
                wmsOutTask.setTaskType(taskType);
                wmsOutTask.setFromLocation(paixu_list.get(i).getLocationCode());
                wmsOutTask.setPalletCode(paixu_list.get(i).getPalletCode());
                wmsOutTask.setGoodsCode(goodsCode);
                wmsOutTask.setBatchNo(batchNo);
                wmsOutTask.setAmount(paixu_list.get(i).getAmount());
                wmsOutTask.setUserDefined2(userdefined2);
                wmsOutTaskList.add(wmsOutTask);
                if (findAmount >= planAmount) {
                    break;
                }
            }
        }else {
            resp.setCode(RESULT.FAILED.code);
            resp.setMsg("当前立库中商品(" + goodsCode + "-批次号:" + batchNo + ")没有可出的数据！");
            return resp;
        }

        long t2 = System.currentTimeMillis();
        log.info("----------------查询所有符合出库的库位，并计算出出货的库位" + (t2 - t1) + "ms！--------------------");

        if (findAmount < planAmount) {
            resp.setCode(RESULT.FAILED.code);
            resp.setMsg("当前时间点，立库中商品(" + goodsCode + "-批次号:" + batchNo + ")可出库的数量不足！请稍后再试！");
            return resp;
        } else {
            WmsLocationStereoscopic updateLocation = new WmsLocationStereoscopic();
            updateLocation.setLastModifiedBy(loginName);
            updateLocation.setGmtModified(new Date());
            if (huoweiList != null && !huoweiList.isEmpty()) {
                updateLocation.setLocationCodeList(huoweiList);
                //TODO 预审 不锁巷道
                updateLocation.setUseStatus("6");
                this.wmsLocationStereoscopicMapper.updateByLocationCodeBatch(updateLocation);
            }
            WmsLocationStereoscopic returnData = new WmsLocationStereoscopic();
            returnData.setLocationCodeList(huoweiList);
            returnData.setWmsOutTaskList(wmsOutTaskList);
            resp.setData(returnData);
            return resp;
        }
    }


    public Resp revertOut(List<WmsMoveTask> wmsMoveTaskList, List<WmsOutTask> wmsOutTaskList, String orderNo) {
        Resp resp = new Resp();
        resp.setCode(RESULT.SUCCESS.code);
        List<String> outlockList = new ArrayList();
        if (wmsOutTaskList != null && !wmsOutTaskList.isEmpty()) {
            Iterator iter = wmsOutTaskList.iterator();

            while (iter.hasNext()) {
                WmsOutTask tmp = (WmsOutTask) iter.next();
                if (tmp.getFromLocation() != null) {
                    outlockList.add(tmp.getFromLocation());
                }
            }
        }

        WmsLocationStereoscopic updateLocation = new WmsLocationStereoscopic();
        updateLocation.setLastModifiedBy("query_out_location_revert");
        updateLocation.setGmtModified(new Date());
        if (outlockList != null && !outlockList.isEmpty()) {
            updateLocation.setLocationCodeList(outlockList);
            updateLocation.setUseStatus("3");
            this.wmsLocationStereoscopicMapper.updateByLocationCodeBatch(updateLocation);
        }

        if (orderNo != null && !"".equals(orderNo)) {
            WmsOrderOutStereoscopic wmsOrderOutStereoscopic = new WmsOrderOutStereoscopic();
            wmsOrderOutStereoscopic.setOrderStatus("5");
            wmsOrderOutStereoscopic.setOrderNo(orderNo);
            this.wmsOrderOutStereoscopicMapper.updateByOrderNo(wmsOrderOutStereoscopic);
        }

        return this.success();
    }

    @Transactional
    public Resp palletOutLocationCheck(String locationCode, String loginName, String targetAddress) {
        Date now = new Date();
        Resp resp = new Resp();
        List<WmsOutTask> wmsOutTaskList = new LinkedList();
        WmsPallet moveWmsPallet = new WmsPallet();
        WmsLocationStereoscopic searchOb = new WmsLocationStereoscopic();
        searchOb.setLocationCode(locationCode);
        List<WmsLocationStereoscopic> list = this.wmsHBLocationStereoscopicMapper.getTotalRowLocation(searchOb);
        if (list != null && !list.isEmpty()) {
            Boolean leftUnLocked = true;
            Boolean rightUnLocked = true;
            Integer shelvesNumber = ((WmsLocationStereoscopic) list.get(0)).getShelvesNumber();
            Integer layerNumber = ((WmsLocationStereoscopic) list.get(0)).getLayerNumber();
            Iterator var14 = list.iterator();

            WmsLocationStereoscopic tmp;
            while (var14.hasNext()) {
                tmp = (WmsLocationStereoscopic) var14.next();
                if (locationCode.equals(tmp.getLocationCode())) {
                    if (tmp.getUseStatus() != null && !"3".equals(tmp.getUseStatus())) {
                        return this.fail("当前托盘库位非占用状态，不能启动托盘出库！");
                    }

                    WmsPallet wmsPallet = new WmsPallet();
                    wmsPallet.setPalletCode(tmp.getPalletCode());
                    List<WmsPallet> palletList = this.wmsPalletMapper.queryByAny(wmsPallet);
                    if (palletList != null && !palletList.isEmpty()) {
                        WmsPallet ptmp = (WmsPallet) palletList.get(0);
                        if (ptmp.getLockBy() != null && !"".equals(ptmp.getLockBy())) {
                            return this.fail("当前托盘被锁定，不能启动托盘出库！");
                        }

                        moveWmsPallet = ptmp;
                        break;
                    }

                    return this.fail("当前托盘信息有误，不能启动托盘出库！");
                }
            }

            var14 = list.iterator();

            while (var14.hasNext()) {
                tmp = (WmsLocationStereoscopic) var14.next();
                if (locationCode.equals(tmp.getLocationCode())) {
                    break;
                }

                if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                    leftUnLocked = false;
                    break;
                }
            }

            Collections.reverse(list);
            var14 = list.iterator();

            while (var14.hasNext()) {
                tmp = (WmsLocationStereoscopic) var14.next();
                if (locationCode.equals(tmp.getLocationCode())) {
                    break;
                }

                if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                    rightUnLocked = false;
                    break;
                }
            }

            if (shelvesNumber != 1 && shelvesNumber != 21) {
                if (shelvesNumber != 4 && shelvesNumber != 20) {
                    if (!rightUnLocked && !leftUnLocked) {
                        return this.fail("当前托盘库位的出库通道不通畅，不能启动托盘出库！");
                    }
                } else if (!leftUnLocked) {
                    return this.fail("当前托盘库位的出库通道不通畅，不能启动托盘出库！");
                }
            } else if (!rightUnLocked) {
                return this.fail("当前托盘库位的出库通道不通畅，不能启动托盘出库！");
            }

            WmsLocationStereoscopic updateOb1 = new WmsLocationStereoscopic();
            updateOb1.setLocationCode(locationCode);
            updateOb1.setUseStatus("2");
            updateOb1.setLastModifiedBy(loginName);
            updateOb1.setGmtModified(now);
            this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb1);
            WmsOutTask wmsOutTask = new WmsOutTask();
            wmsOutTask.setTaskId(this.wmsCommonService.getTaskIds(TaskType.HAND_OUT, 1)[0]);
            wmsOutTask.setTargetAddress(targetAddress);
            wmsOutTask.setOperator(loginName);
            wmsOutTask.setTaskType(String.valueOf(TaskType.HAND_OUT.getTaskType()));
            wmsOutTask.setFromLocation(locationCode);
            wmsOutTask.setPalletCode(moveWmsPallet.getPalletCode());
            wmsOutTask.setGoodsCode(moveWmsPallet.getGoodsCode());
            wmsOutTask.setGoodsName(moveWmsPallet.getGoodsName());
            wmsOutTask.setBatchNo(moveWmsPallet.getBatchNo());
            wmsOutTask.setAmount(moveWmsPallet.getAmount());
            wmsOutTaskList.add(wmsOutTask);
            resp.setData(wmsOutTaskList);
            return resp;
        } else {
            return this.fail("当前托盘库位信息有误，不能启动托盘出库！");
        }
    }

    @Transactional
    public Resp startPalletOut(List<WmsOutTask> wmsOutTaskList, String loginName) {
        Date now = new Date();
        String orderNo = "";
        if (wmsOutTaskList != null && !wmsOutTaskList.isEmpty()) {
            WmsOutTask tmp = (WmsOutTask) wmsOutTaskList.get(0);
            if (!String.valueOf(TaskType.VIRTUAL_PALLET_OUT).equals(tmp.getTaskType())) {
                orderNo = this.wmsCommonService.getOrderNoByType("PSO");
                WmsOrderOutStereoscopic wmsOrderOutStereoscopic = new WmsOrderOutStereoscopic();
                wmsOrderOutStereoscopic.setOrderNo(orderNo);
                wmsOrderOutStereoscopic.setOutAddress(tmp.getTargetAddress());
                wmsOrderOutStereoscopic.setOrderType(String.valueOf(TaskType.HAND_OUT.getTaskType()));
                wmsOrderOutStereoscopic.setOrderOutId(CommonUtils.getUUID());
                wmsOrderOutStereoscopic.setCreateBy(loginName);
                wmsOrderOutStereoscopic.setGmtCreate(now);
                wmsOrderOutStereoscopic.setActiveFlag("1");
                wmsOrderOutStereoscopic.setOrderStatus("2");
                this.wmsOrderOutStereoscopicMapper.create(wmsOrderOutStereoscopic);
                WmsOrderOutStereoscopicDeail wmsOrderOutStereoscopicDeail = new WmsOrderOutStereoscopicDeail();
                wmsOrderOutStereoscopicDeail.setOrderOutDetailId(CommonUtils.getUUID());
                wmsOrderOutStereoscopicDeail.setOrderNo(orderNo);
                wmsOrderOutStereoscopicDeail.setGoodsCode(tmp.getGoodsCode());
                wmsOrderOutStereoscopicDeail.setGoodsName(tmp.getGoodsName());
                wmsOrderOutStereoscopicDeail.setBatchNo(tmp.getBatchNo());
                wmsOrderOutStereoscopicDeail.setPlanAmount(tmp.getAmount());
                wmsOrderOutStereoscopicDeail.setCreateBy(loginName);
                wmsOrderOutStereoscopicDeail.setGmtCreate(now);
                wmsOrderOutStereoscopicDeail.setActiveFlag("1");
                this.wmsOrderOutStereoscopicDeailMapper.create(wmsOrderOutStereoscopicDeail);
            }

            WmsPallet t = new WmsPallet();
            t.setPalletCode(tmp.getPalletCode());
            t.setLockBy(String.valueOf(tmp.getTaskId()));
            t.setLastModifiedBy(loginName);
            t.setGmtModified(now);
            t.setUserDefined1(orderNo);
            this.wmsPalletMapper.updateByPalletCode(t);
            WmsTaskExecutionLog wmsTaskExecutionLog = new WmsTaskExecutionLog();
            wmsTaskExecutionLog.setTaskId(tmp.getTaskId());
            wmsTaskExecutionLog.setOrderNo(orderNo);
            wmsTaskExecutionLog.setTaskType(tmp.getTaskType());
            wmsTaskExecutionLog.setPalletCode(tmp.getPalletCode());
            wmsTaskExecutionLog.setTaskStatus("1");
            wmsTaskExecutionLog.setOutAddress(tmp.getTargetAddress());
            wmsTaskExecutionLog.setLocationCode(tmp.getFromLocation());
            wmsTaskExecutionLog.setGoodsCode(tmp.getGoodsCode());
            wmsTaskExecutionLog.setBatchNo(tmp.getBatchNo());
            wmsTaskExecutionLog.setCreateBy(loginName);
            wmsTaskExecutionLog.setGmtCreate(now);
            wmsTaskExecutionLog.setActiveFlag("1");
            wmsTaskExecutionLog.setUserDefined1(Integer.toString(tmp.getAmount()));
            this.wmsTaskExecutionLogMapper.create(wmsTaskExecutionLog);
        }

        return this.success();
    }
    /**
     *功能描述: 盘点 - 启动
     * @params
     * @return com.penghaisoft.framework.util.Resp
     */
    @Transactional
    public Resp lockCheckLocationCode(WmsOrderCheck wmsOrderCheck) {
        Resp resp = new Resp();
        resp.setCode(RESULT.SUCCESS.code);
        List<WmsOutTask> wmsOutTaskList = new LinkedList();
        StringBuffer sb = new StringBuffer("");
        WmsOrderCheckPallet checkPallet = new WmsOrderCheckPallet();
        checkPallet.setOrderNo(wmsOrderCheck.getOrderNo());
        List<WmsOrderCheckPallet> locationStatusList = this.wmsOrderCheckPalletMapper.queryLocationStatus(checkPallet);
        if (locationStatusList != null && !locationStatusList.isEmpty()) {
            Iterator var7 = locationStatusList.iterator();

            while (var7.hasNext()) {
                WmsOrderCheckPallet tmp = (WmsOrderCheckPallet) var7.next();
                if (tmp.getUseStatus() != null && ("1".equals(tmp.getUseStatus()) || "2".equals(tmp.getUseStatus()))) {
                    return this.fail("当前盘点托盘库位及途径库位中包含锁定状态的库位");
                }

                if (tmp.getUseStatus() != null && "4".equals(tmp.getUseStatus())) {
                    return this.fail("当前盘点托盘库位及途径库位中包含异常状态的库位");
                }
            }
        }

        String targetAddress = "1";
        WmsAddressRealRela address = new WmsAddressRealRela();
        address.setAddressCode(wmsOrderCheck.getOutAddress());
        List<WmsAddressRealRela> addressList = this.wmsAddressRealRelaMapper.queryByAddressCode(address);
        if (addressList != null && !addressList.isEmpty()) {
            WmsAddressRealRela tmp = (WmsAddressRealRela) addressList.get(0);
            targetAddress = String.valueOf(tmp.getRealAddress());
        }

        List<WmsOrderCheckPallet> list = this.wmsOrderCheckPalletMapper.queryOutList(checkPallet);
        WmsOutTask tmp;
        if (list != null && !list.isEmpty()) {
            Iterator var11 = list.iterator();

            while (var11.hasNext()) {
                WmsOrderCheckPallet tmp1 = (WmsOrderCheckPallet) var11.next();
                //WmsOrderCheckPallet tmp = (WmsOrderCheckPallet)var11.next();
                tmp = new WmsOutTask();
                tmp.setTaskId(this.wmsCommonService.getTaskIds(TaskType.CHECK_OUT, 1)[0]);
                tmp.setTargetAddress(targetAddress);
                tmp.setOperator(wmsOrderCheck.getCreateBy());
                tmp.setTaskType(String.valueOf(TaskType.CHECK_OUT.getTaskType()));
                tmp.setPalletCode(tmp1.getPalletCode());
                tmp.setFromLocation(tmp1.getLocationCode());
                tmp.setGoodsCode(tmp1.getGoodsCode());
                tmp.setGoodsName(tmp1.getGoodsName());
                tmp.setBatchNo(tmp1.getBatchNo());
                wmsOutTaskList.add(tmp);
            }
        }

        List<String> outlockList = new ArrayList();
        Iterator iter = wmsOutTaskList.iterator();

        while (iter.hasNext()) {
            tmp = (WmsOutTask) iter.next();
            if (tmp.getFromLocation() != null) {
                outlockList.add(tmp.getFromLocation());
            }
        }

        WmsLocationStereoscopic updateLocation = new WmsLocationStereoscopic();
        updateLocation.setLastModifiedBy(wmsOrderCheck.getCreateBy());
        updateLocation.setGmtModified(new Date());
        if (outlockList != null && !outlockList.isEmpty()) {
            updateLocation.setLocationCodeList(outlockList);
            updateLocation.setUseStatus("2");
            this.wmsLocationStereoscopicMapper.updateByLocationCodeBatch(updateLocation);
        }

        resp.setData(wmsOutTaskList);
        resp.setMsg(sb.toString());
        return resp;
    }

    /**
     * 功能描述: 移库-创建信息
     *
     * @return com.penghaisoft.framework.util.Resp
     * @params
     */
    @Transactional
    public Resp createOrEditYk(WmsMoveStereoscopic wmsMoveStereoscopic) {
        Date now = new Date();
        String moveNo = wmsMoveStereoscopic.getMoveNo();
        WmsPallet moveWmsPallet = new WmsPallet();
        String moveGoodsCode = "";
        String fromFloor = "";
        String toFloor = "";
        if (wmsMoveStereoscopic.getFromLocationCode() != null) {
            fromFloor = wmsMoveStereoscopic.getFromLocationCode().substring(1, 2);
        }
        if (wmsMoveStereoscopic.getToLocationCode() != null) {
            toFloor = wmsMoveStereoscopic.getToLocationCode().substring(1, 2);
        }
        //非同层库位间，尽量不要移库
        if (!"".equals(fromFloor) && !"".equals(toFloor)) {
            String status="";

            /*if (!fromFloor.equals(toFloor)) {
                return this.fail("非同层库位间不能移库！");
            } else {*/
            WmsLocationStereoscopic searchOb = new WmsLocationStereoscopic();
            searchOb.setLocationCode(wmsMoveStereoscopic.getFromLocationCode());
            //源库位
            //移库-根据库位编码，查询信息
            List<WmsLocationStereoscopic> list = this.wmsHBLocationStereoscopicMapper.getHBTotalRowLocation(searchOb);
            if (list != null && !list.isEmpty()) {
                Iterator var14 = list.iterator();
                while (true) {
                    WmsLocationStereoscopic tmp;
                    if (var14.hasNext()) {
                        tmp = (WmsLocationStereoscopic) var14.next();
                        status = tmp.getUseStatus();
                        if (!wmsMoveStereoscopic.getFromLocationCode().equals(tmp.getLocationCode())) {
                            continue;
                        }
                        //使用状态 0可用1入库中2出库中3占用4异常 5入库未过账，6：出库未过账
                        if (tmp.getUseStatus() != null  && !"5".equals(tmp.getUseStatus()) ) {
                            return this.fail("源库位非入库未过账状态，不能进行移库！");
                        }

                        WmsPallet wmsPallet = new WmsPallet();
                        wmsPallet.setPalletCode(tmp.getPalletCode());
                        List<WmsPallet> palletList = this.wmsPalletMapper.queryByAny(wmsPallet);
                        if (palletList == null || palletList.isEmpty()) {
                            return this.fail("源库位上的托盘信息有误，不能进行移库！");
                        }

                        WmsPallet ptmp = (WmsPallet) palletList.get(0);
                        if (ptmp.getLockBy() != null && !"".equals(ptmp.getLockBy())) {
                            return this.fail("源库位上的托盘被锁定，不能进行移库！");
                        }

                        moveWmsPallet = ptmp;

                    }
                    //判断此巷道是否在短巷道内，如果在短巷道
                    int  shelvesNumber = list.get(0).getShelvesNumber();
                    List<Integer> shelvesNumberList = Arrays.asList(1,3,13,15,25,27,37,39);
                    //如果是短巷道，查询短巷道阻隔sql语句
                    List<WmsLocationStereoscopic> LocationPasslist=null;
                    //大号处，小号位置是否有货位阻挡 / 小号处，大号位置是否有货位阻挡
                    if(shelvesNumberList.contains(shelvesNumber)){
                        log.info("短巷道：");
                        LocationPasslist = this.wmsHBLocationStereoscopicMapper.getLocationPassDuanDao(list.get(0));
                    }else {
                        log.info("非短巷道：");
                        //判断向道是否被阻隔,有数据即被阻隔
                        LocationPasslist = this.wmsHBLocationStereoscopicMapper.getLocationPass(list.get(0));
                    }

                    if (LocationPasslist != null && LocationPasslist.size() > 0) {
                        return this.fail("源库位的移库通道不通畅，不能进行移库！");
                    }
                    //目标位
                    WmsLocationStereoscopic searchOb2 = new WmsLocationStereoscopic();
                    searchOb2.setLocationCode(wmsMoveStereoscopic.getToLocationCode());
                    List<WmsLocationStereoscopic> list2 = this.wmsHBLocationStereoscopicMapper.getHBTotalRowLocation(searchOb2);
                    if (list2 != null && !list2.isEmpty()) {
                        Iterator var33 = list2.iterator();
                        while (var33.hasNext()) {
                            tmp = (WmsLocationStereoscopic) var33.next();
                            if (wmsMoveStereoscopic.getToLocationCode().equals(tmp.getLocationCode())) {
                                if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                                    return this.fail("目的库位非可用状态，不能启动移库！");
                                }

                                if (tmp.getPalletCode() != null && !"".equals(tmp.getPalletCode())) {
                                    return this.fail("目的库位非可用状态，不能启动移库！");
                                }
                                break;
                            }
                        }

                        //判断此巷道是否在短巷道内，如果在短巷道
                        int  shelvesNumber2 = list2.get(0).getShelvesNumber();
                        //如果是短巷道，查询短巷道阻隔sql语句
                        List<WmsLocationStereoscopic> LocationPasslist2=null;
                        //大号处，小号位置是否有货位阻挡 / 小号处，大号位置是否有货位阻挡
                        if(shelvesNumberList.contains(shelvesNumber2)){
                            log.info("短巷道：");
                            LocationPasslist2 = this.wmsHBLocationStereoscopicMapper.getLocationPassDuanDao(list2.get(0));
                        }else {
                            log.info("非短巷道：");
                            //判断向道是否被阻隔,有数据即被阻隔
                            LocationPasslist2 = this.wmsHBLocationStereoscopicMapper.getLocationPass(list2.get(0));
                        }
                        if (LocationPasslist2 != null && LocationPasslist2.size() > 0) {
                            log.info("目的库位的移库通道不通畅，不能进行移库！");
                            return this.fail("目的库位的移库通道不通畅，不能进行移库！");
                        }
                        if (wmsMoveStereoscopic.getMoveNo() != null && !"".equals(wmsMoveStereoscopic.getMoveNo())) {
                            WmsMoveStereoscopic updateOb = new WmsMoveStereoscopic();
                            updateOb.setMoveNo(wmsMoveStereoscopic.getMoveNo());
                            updateOb.setLastModifiedBy(wmsMoveStereoscopic.getCreateBy());
                            updateOb.setGmtModified(now);
                            updateOb.setUserDefined3(status);
                            this.wmsMoveStereoscopicMapper.updateByMoveNo(updateOb);
                        } else {
                            moveNo = this.wmsCommonService.getOrderNoByType("MS");
                            wmsMoveStereoscopic.setMoveNo(moveNo);
                        }
                        wmsMoveStereoscopic.setMoveNo(moveNo);
                        long taskId = this.wmsCommonService.getTaskIds(Constant.TaskType.NORMAL_MOVE, 1)[0];
                        wmsMoveStereoscopic.setMoveId(String.valueOf(taskId));
                        wmsMoveStereoscopic.setMoveStatus("1");
                        wmsMoveStereoscopic.setAreaCode(moveWmsPallet.getAreaCode());
                        wmsMoveStereoscopic.setPalletCode(moveWmsPallet.getPalletCode());
                        wmsMoveStereoscopic.setGoodsCode(moveWmsPallet.getGoodsCode());
                        wmsMoveStereoscopic.setGoodsName(moveWmsPallet.getGoodsName());
                        wmsMoveStereoscopic.setBatchNo(moveWmsPallet.getBatchNo());
                        wmsMoveStereoscopic.setAmount(moveWmsPallet.getAmount());
                        //备注
                        wmsMoveStereoscopic.setUserDefined1(wmsMoveStereoscopic.getUserDefined1());
                        wmsMoveStereoscopic.setUserDefined3(status);
                        wmsMoveStereoscopic.setGmtCreate(now);
                        wmsMoveStereoscopic.setActiveFlag("1");
                        this.wmsMoveStereoscopicMapper.create(wmsMoveStereoscopic);
                        //移库修改入库任务数据
                        WmsTaskExecutionLog wmsTaskExecutionLog =new WmsTaskExecutionLog();
                        wmsTaskExecutionLog.setPalletCode(moveWmsPallet.getPalletCode());
                        wmsTaskExecutionLog.setGoodsCode(moveWmsPallet.getGoodsCode());
                        wmsTaskExecutionLog.setBatchNo(moveWmsPallet.getBatchNo());
                        wmsTaskExecutionLog.setTaskStatus("3");
                        List<WmsTaskExecutionLog> taskList = wmsTaskExecutionLogMapper.selReceipt(wmsTaskExecutionLog);
                        if(taskList!=null && !taskList.isEmpty()) {
                            taskList.get(0).setLocationCode(wmsMoveStereoscopic.getToLocationCode());
                            wmsTaskExecutionLogMapper.updateByTaskId(wmsTaskExecutionLog);
                        }
                        return this.success();
                    } else {
                        return this.fail("目标库位信息有误，不能启动移库！");
                    }
                }

            }else{
                return this.fail("源库位信息有误，不能启动移库！");
            }
        } else {
            return this.fail("层数异常，不能移库");
        }

    }

    /**
     *功能描述:  - 启动移库任务
     * @params
     * @return com.penghaisoft.framework.util.Resp
     */
    @Transactional
    public Resp locationLockYk(WmsMoveStereoscopic condition) {
        Date now = new Date();
        Resp resp = new Resp();
        WmsMoveStereoscopic wmsMoveStereoscopic = new WmsMoveStereoscopic();
        WmsMoveStereoscopic conditionSearch = new WmsMoveStereoscopic();
        conditionSearch.setMoveId(condition.getMoveId());
        conditionSearch.setActiveFlag("1");
        List<WmsMoveStereoscopic> wmsMoveStereoscopicList = this.wmsMoveStereoscopicMapper.queryByAny(conditionSearch);
        if (wmsMoveStereoscopicList != null && !wmsMoveStereoscopicList.isEmpty()) {
            wmsMoveStereoscopic = (WmsMoveStereoscopic)wmsMoveStereoscopicList.get(0);
        }
        String fromFloor = wmsMoveStereoscopic.getFromLocationCode().substring(wmsMoveStereoscopic.getFromLocationCode().length()-2,wmsMoveStereoscopic.getFromLocationCode().length());
        String toFloor = wmsMoveStereoscopic.getToLocationCode().substring(wmsMoveStereoscopic.getToLocationCode().length()-2,wmsMoveStereoscopic.getToLocationCode().length());

        //非同层库位间，尽量不要移库
        if (!"".equals(fromFloor) && !"".equals(toFloor)) {
            WmsLocationStereoscopic searchOb = new WmsLocationStereoscopic();
            searchOb.setLocationCode(wmsMoveStereoscopic.getFromLocationCode());
            //源库位
            //移库-根据库位编码，查询信息
            List<WmsLocationStereoscopic> list = this.wmsHBLocationStereoscopicMapper.getHBTotalRowLocation(searchOb);
            if (list != null && !list.isEmpty()) {
                Iterator var14 = list.iterator();
                while (true) {
                    WmsLocationStereoscopic tmp;
                    if (var14.hasNext()) {
                        tmp = (WmsLocationStereoscopic) var14.next();
                        if (!wmsMoveStereoscopic.getFromLocationCode().equals(tmp.getLocationCode())) {
                            continue;
                        }
                        //使用状态 0可用1入库中2出库中3占用4异常
                        if (tmp.getUseStatus() != null && !"3".equals(tmp.getUseStatus())  && !"5".equals(tmp.getUseStatus())) {
                            return this.fail("源库位非占用状态，不能进行移库！");
                        }

                        WmsPallet wmsPallet = new WmsPallet();
                        wmsPallet.setPalletCode(tmp.getPalletCode());
                        List<WmsPallet> palletList = this.wmsPalletMapper.queryByAny(wmsPallet);
                        if (palletList == null || palletList.isEmpty()) {
                            return this.fail("源库位上的托盘信息有误，不能进行移库！");
                        }

                        WmsPallet ptmp = (WmsPallet) palletList.get(0);
                        if (ptmp.getLockBy() != null && !"".equals(ptmp.getLockBy())) {
                            return this.fail("源库位上的托盘被锁定，不能进行移库！");
                        }
                    }
                    //判断此巷道是否在短巷道内，如果在短巷道
                    int  shelvesNumber = list.get(0).getShelvesNumber();
                    List<Integer> shelvesNumberList = Arrays.asList(1,3,13,15,25,27,37,39);
                    //如果是短巷道，查询短巷道阻隔sql语句
                    List<WmsLocationStereoscopic> LocationPasslist=null;
                    if(shelvesNumberList.contains(shelvesNumber)){
                        log.info("短巷道：");
                        LocationPasslist = this.wmsHBLocationStereoscopicMapper.getLocationPassDuanDao(list.get(0));
                    }else {
                        log.info("非短巷道：");
                        //判断向道是否被阻隔,有数据即被阻隔
                        LocationPasslist = this.wmsHBLocationStereoscopicMapper.getLocationPass(list.get(0));
                    }
                    //大号处，小号位置是否有货位阻挡 / 小号处，大号位置是否有货位阻挡
                    if (LocationPasslist != null && LocationPasslist.size() > 0) {
                        return this.fail("源库位的移库通道不通畅，不能进行移库！");
                    }
                    //目标位
                    WmsLocationStereoscopic searchOb2 = new WmsLocationStereoscopic();
                    searchOb2.setLocationCode(wmsMoveStereoscopic.getToLocationCode());
                    List<WmsLocationStereoscopic> list2 = this.wmsHBLocationStereoscopicMapper.getHBTotalRowLocation(searchOb2);
                    if (list2 != null && !list2.isEmpty()) {
                        Iterator var33 = list2.iterator();
                        while (var33.hasNext()) {
                            tmp = (WmsLocationStereoscopic) var33.next();
                            if (wmsMoveStereoscopic.getToLocationCode().equals(tmp.getLocationCode())) {
                                if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                                    return this.fail("目的库位非可用状态，不能启动移库！");
                                }

                                if (tmp.getPalletCode() != null && !"".equals(tmp.getPalletCode())) {
                                    return this.fail("目的库位非可用状态，不能启动移库！");
                                }
                                break;
                            }
                        }
                        //判断此巷道是否在短巷道内，如果在短巷道
                        int  shelvesNumber2 = list2.get(0).getShelvesNumber();
                        //如果是短巷道，查询短巷道阻隔sql语句
                        List<WmsLocationStereoscopic> LocationPasslist2=null;
                        if(shelvesNumberList.contains(shelvesNumber)){
                            log.info("短巷道：");
                            LocationPasslist2 = this.wmsHBLocationStereoscopicMapper.getLocationPassDuanDao(list2.get(0));
                        }else {
                            log.info("非短巷道：");
                            //判断向道是否被阻隔,有数据即被阻隔
                            LocationPasslist2 = this.wmsHBLocationStereoscopicMapper.getLocationPass(list2.get(0));
                        }
                        //大号处，小号位置是否有货位阻挡 / 小号处，大号位置是否有货位阻挡
                        if (LocationPasslist2 != null && LocationPasslist2.size() > 0) {
                            return this.fail("目的库位的移库通道不通畅，不能进行移库！");
                        }

                        List<String> outLocationList = new ArrayList();
                        outLocationList.add(wmsMoveStereoscopic.getFromLocationCode());
                        List<String> inLocationList = new ArrayList();
                        inLocationList.add(wmsMoveStereoscopic.getToLocationCode());
                        WmsLocationStereoscopic updateOb = new WmsLocationStereoscopic();
                        updateOb.setLastModifiedBy("wcs-move");
                        updateOb.setGmtModified(now);
                        updateOb.setUseStatus("2");
                        updateOb.setLocationCodeList(outLocationList);
                        this.wmsLocationStereoscopicMapper.updateByLocationCodeBatch(updateOb);
                        updateOb.setUseStatus("1");
                        updateOb.setLocationCodeList(inLocationList);
                        this.wmsLocationStereoscopicMapper.updateByLocationCodeBatch(updateOb);

                        //现场添加-启动移库任务-更改移库状态
                        conditionSearch.setMoveStatus("2");
                        wmsMoveStereoscopicMapper.updateBySelect(conditionSearch);
                        resp.setData(wmsMoveStereoscopic);
                        resp.setCode(RESULT.SUCCESS.code);
                        return resp;
                    } else {
                        return this.fail("目标库位信息有误，不能启动移库！");
                    }
                }

            }else{
                return this.fail("源库位信息有误，不能启动移库！");
            }
        } else {
            return this.fail("层数异常，不能移库");
        }
    }

    /**
     * 功能描述: 移库
     *
     * @return com.penghaisoft.framework.util.Resp
     * @params
     */
    @Transactional
    public Resp reverseYk(WmsMoveStereoscopic wmsMoveStereoscopic) {
        List<String> outLocationList = new ArrayList();
        List<String> inLocationList = new ArrayList();
        outLocationList.add(wmsMoveStereoscopic.getFromLocationCode());
        inLocationList.add(wmsMoveStereoscopic.getToLocationCode());
        WmsLocationStereoscopic updateOb = new WmsLocationStereoscopic();
        updateOb.setLastModifiedBy(wmsMoveStereoscopic.getCreateBy());
        updateOb.setGmtModified(new Date());
        updateOb.setUseStatus("3");
        updateOb.setLocationCodeList(outLocationList);
        this.wmsLocationStereoscopicMapper.updateByLocationCodeBatch(updateOb);
        updateOb.setUseStatus("0");
        updateOb.setLocationCodeList(inLocationList);
        this.wmsLocationStereoscopicMapper.updateByLocationCodeBatch(updateOb);
        return this.success();
    }



    /**
     *功能描述: 检查并调取wcs
     * @author zhangxin
     * @date 2022/8/8
     * @params
     * @return com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic
     */
    @Override
    public void checkAndSend(){
        //检查是否有出库任务
        log.info("checkAndSend - 检查是否有出库任务");
        //根据状态查询数据
        WmsOrderOutStereoscopic condition =new WmsOrderOutStereoscopic();
        condition.setOrderStatus("2");
        List<WmsOrderOutStereoscopic> WmsOrderOutStereoscopicList =this.wmsOrderOutStereoscopicMapper.queryByAny(condition);
        //立库出库表，不为空，否则有出库启动状态。
        if(WmsOrderOutStereoscopicList!=null &&WmsOrderOutStereoscopicList.size()>0){
           n: for(WmsOrderOutStereoscopic wmsOrderOutStereoscopic : WmsOrderOutStereoscopicList){
                //根据出库单的单号，查询任务执行日志，查看是否存在执行中的状态，若没有，调wcs接口，下发出库任务。
                String orderNo = wmsOrderOutStereoscopic.getOrderNo();
                WmsTaskExecutionLog wmsTaskExecutionLog =new WmsTaskExecutionLog();
                wmsTaskExecutionLog.setOrderNo(orderNo);
                wmsTaskExecutionLog.setTaskStatus("2");
                //出库任务
                List<WmsTaskExecutionLog> wmsTaskExecutionLogList =this.wmsTaskExecutionLogMapper.queryByAny(wmsTaskExecutionLog);

               List statuslist =new ArrayList();
               statuslist.add("10");
               statuslist.add("50");
               wmsTaskExecutionLog.setIdList(statuslist);
               //入库任务
               List<WmsTaskExecutionLog> receiptList = wmsTaskExecutionLogService.selReceipt(wmsTaskExecutionLog);
                if(wmsTaskExecutionLogList!=null &&wmsTaskExecutionLogList.size()>0){
                    //有正在执行的任务。 跳出循环
                    log.info("出库 - 有正在执行的出库任务!");
                    break n;
                }else if(receiptList!=null && receiptList.size()>0) {
                    log.info("出库 - 有正在执行的入库任务!");
                    break n;
                }else{
                    //没有正在执行的任务，查找出库的无障碍的库位,下发wcs出库任务。
                    wmsTaskExecutionLog.setTaskStatus("1");
                    List<WmsTaskExecutionLog> wmsTasklogStatusByOne =this.wmsTaskExecutionLogMapper.queryByAny(wmsTaskExecutionLog);
                    if(wmsTasklogStatusByOne!=null &&wmsTasklogStatusByOne.size()>0){
                        //标识，是否存在循环完，仍然有向道被阻隔，出不出来的货物。
                        boolean flag = true;
                      k:  for (WmsTaskExecutionLog wmsTaskExecutionLog1:wmsTasklogStatusByOne){
                            //任务号
                            Long taskId =wmsTaskExecutionLog1.getTaskId();
                            //托盘号
                            String palletCode = wmsTaskExecutionLog1.getPalletCode();
                            //根据托盘号，查找库位。
                            WmsLocationStereoscopic wmsLocationStereoscopic1 =new WmsLocationStereoscopic();
                            wmsLocationStereoscopic1.setPalletCode(palletCode);
                            List<WmsLocationStereoscopic> wmsLocationStereoscopicList = this.wmsLocationStereoscopicMapper.queryByAny(wmsLocationStereoscopic1);
                            //一个托盘只能有一个货位。
                            if(wmsLocationStereoscopicList!=null &&wmsLocationStereoscopicList.size()>0) {
                                //判断此巷道是否在短巷道内，如果在短巷道
                                int  shelvesNumber = wmsLocationStereoscopicList.get(0).getShelvesNumber();
                                List<Integer> shelvesNumberList = Arrays.asList(1,3,13,15,25,27,37,39);
                                //如果是短巷道，查询短巷道阻隔sql语句
                                List<WmsLocationStereoscopic> LocationStereoscopicList=null;
                                if(shelvesNumberList.contains(shelvesNumber)){
                                    log.info("短巷道：");
                                    LocationStereoscopicList = this.wmsHBLocationStereoscopicMapper.getLocationPassDuanDao(wmsLocationStereoscopicList.get(0));
                                }else {
                                    log.info("非短巷道：");
                                    //判断向道是否被阻隔,有数据即被阻隔
                                    LocationStereoscopicList = this.wmsHBLocationStereoscopicMapper.getLocationPass(wmsLocationStereoscopicList.get(0));
                                }
                                if (LocationStereoscopicList==null || LocationStereoscopicList.size() == 0) {
                                    WmsPallet wmsPallet =new WmsPallet();
                                    wmsPallet.setLockBy(String.valueOf(taskId));
                                    wmsPallet.setPalletCode(wmsLocationStereoscopicList.get(0).getPalletCode());
                                    //根据托盘码锁定托盘
                                    wmsPalletMapper.updateByPalletCode(wmsPallet);
                                    wmsLocationStereoscopic1.setUseStatus("2");
                                    wmsLocationStereoscopic1.setLocationCode(wmsLocationStereoscopicList.get(0).getLocationCode());
                                    wmsLocationStereoscopic1.setLastModifiedBy("wms");
                                    wmsLocationStereoscopic1.setGmtModified(new Date());
                                    this.wmsLocationStereoscopicMapper.updateByLocationCode(wmsLocationStereoscopic1);
                                    //可以出库
                                    flag = false;
                                    log.info("查到被锁定的可出库的库位：" + wmsLocationStereoscopicList.get(0).getLocationCode());
                                    log.info("存在出库启动数据和正常出库的库位：============调wcs启动出库任务！");
                                    //TODO 调WCS，启动出库任务
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.put("taskId", taskId);
                                    jsonObject.put("containerId", palletCode);
                                    jsonObject.put("floor", wmsLocationStereoscopicList.get(0).getFloorNumber());
                                    jsonObject.put("taskType", "1");
                                    jsonObject.put("priority", "2001");
                                    jsonObject.put("locIdFrom", wmsLocationStereoscopicList.get(0).getLocationCode());
                                    jsonObject.put("locIdTo", "90");
                                    jsonObject.put("rgvId", "");
                                    JSONObject jSONObject = sLWCSService.taskReceive(jsonObject);
                                    // 模拟返回
//                                    String str="{ \"ret\": true, \"msg\": \"操作成功\"}";
//                                    JSONObject jSONObject = JSONObject.parseObject(str);

                                    if (jSONObject != null) {
                                        //如果调用成功
                                        if (Boolean.valueOf(jSONObject.get("ret").toString())) {
                                            //将任务执行日志状态，修改为正在执行中。
                                            wmsTaskExecutionLog1.setTaskStatus("2");
                                            this.wmsTaskExecutionLogMapper.updateByTaskId(wmsTaskExecutionLog1);
                                        }
                                    } else {
                                        //如果异常，记得将出库状态正在执行，修改为4：异常。
                                        wmsTaskExecutionLog1.setTaskStatus("4");
                                        this.wmsTaskExecutionLogMapper.updateByTaskId(wmsTaskExecutionLog1);
                                    }
                                    break k;
                                }
                            }
                        }
                        //可出库的都循环完了，剩下的需要移库后出库。
                        if(flag==true){
                           l: for (WmsTaskExecutionLog wmsTaskExecutionLog2:wmsTasklogStatusByOne){
                                //查到正在出库状态为2的库位，根据库位，查询出阻挡货物出入的库位
                                //TODO 移库
                                Integer floor =  Integer.parseInt(wmsTaskExecutionLog2.getLocationCode().substring(1,2));
                                Integer layer =  Integer.parseInt(wmsTaskExecutionLog2.getLocationCode().substring(4,6));
                                Integer column =  Integer.parseInt(wmsTaskExecutionLog2.getLocationCode().substring(8,10));
                                List<Integer> floorList =new ArrayList<>();
                                floorList.add(floor);
                                WmsLocationStereoscopic wmsLocationStereoscopic =new WmsLocationStereoscopic();
                                wmsLocationStereoscopic.setLocationCode(wmsTaskExecutionLog2.getLocationCode());
                                wmsLocationStereoscopic.setGoodsCode(wmsTaskExecutionLog2.getGoodsCode());
                                wmsLocationStereoscopic.setBatchNo(wmsTaskExecutionLog2.getBatchNo());
                                wmsLocationStereoscopic.setFloorNumberList(floorList);
                                wmsLocationStereoscopic.setLayerNumber(layer);
                                wmsLocationStereoscopic.setColumnNumber(column);
                                //查找是否有货位阻碍通道
                               //判断此巷道是否在短巷道内，如果在短巷道
                               int  shelvesNumber = wmsLocationStereoscopic.getShelvesNumber();
                               List<Integer> shelvesNumberList = Arrays.asList(1,3,13,15,25,27,37,39);
                               //如果是短巷道，查询短巷道阻隔sql语句
                               List<WmsLocationStereoscopic> wmsLocationStereoscopicList=null;
                               if(shelvesNumberList.contains(shelvesNumber)){
                                   log.info("短巷道：");
                                   wmsLocationStereoscopicList = this.wmsHBLocationStereoscopicMapper.getLocationPassDuanDao(wmsLocationStereoscopic);
                               }else {
                                   log.info("非短巷道：");
                                   //判断向道是否被阻隔,有数据即被阻隔
                                   wmsLocationStereoscopicList = this.wmsHBLocationStereoscopicMapper.getLocationPass(wmsLocationStereoscopic);
                               }
                                if(wmsLocationStereoscopicList!=null && wmsLocationStereoscopicList.size()>0) {
                                    //根据阻挡货位的库位，查找是否有移库任务。
                                    for(int i=0;i< wmsLocationStereoscopicList.size();i++){
                                        WmsTaskExecutionLog wmsTaskExecutionLog1=new WmsTaskExecutionLog();
                                        //库位编号
                                        wmsTaskExecutionLog1.setLocationCode(wmsLocationStereoscopicList.get(i).getLocationCode());
                                        wmsTaskExecutionLog1.setGoodsCode(wmsLocationStereoscopicList.get(i).getGoodsCode());
                                        wmsTaskExecutionLog1.setBatchNo(wmsLocationStereoscopicList.get(i).getBatchNo());
                                        wmsTaskExecutionLog1.setTaskStatus("2");

                                        List<WmsTaskExecutionLog> WmsTaskExecutionLogList = this.wmsTaskExecutionLogMapper.queryByAny(wmsTaskExecutionLog1);
                                        if(WmsTaskExecutionLogList!=null &&WmsTaskExecutionLogList.size()>0){
                                            log.info("存在正在移库的任务！");
                                            break l;
                                        }else{
                                            //TODO 启动移库任务
                                            //查询所有可用库位
                                            //立库库位信息表-实体类
                                            WmsLocationStereoscopic searchOb = new WmsLocationStereoscopic();
                                            searchOb.setGoodsCode(wmsLocationStereoscopicList.get(i).getGoodsCode());
                                            searchOb.setBatchNo(wmsLocationStereoscopicList.get(i).getBatchNo());
                                            //的4个缓存位
                                            List<String> locationCodeList = new ArrayList<>();
                                            locationCodeList.add(Constant.StereoscopicInfo.YIKU_WEI_1);
                                            locationCodeList.add(Constant.StereoscopicInfo.YIKU_WEI_2);
                                            locationCodeList.add(Constant.StereoscopicInfo.YIKU_WEI_3);
                                            //获取每一层的固定的3个不可分配的库位编码
                                            searchOb.setLocationCodeList(locationCodeList);
                                            //-查询所有可用货位，4个缓存位除外
                                            List<WmsLocationStereoscopic> listRight = this.wmsHBLocationStereoscopicMapper.getLocationInfoBylocationCodeList(searchOb);

                                            //根据商品编码，批次号，查找同一批次可放货物的空向道。获取库位。
                                            WmsLocationStereoscopic  wmsLocationStereoscopic1 = new WmsLocationStereoscopic();
                                            wmsLocationStereoscopic1.setGoodsCode(wmsLocationStereoscopicList.get(i).getGoodsCode());
                                            wmsLocationStereoscopic1.setBatchNo(wmsLocationStereoscopicList.get(i).getBatchNo());
                                            List<Integer> floorList1 = new ArrayList<>();
                                            floorList1.add(wmsLocationStereoscopicList.get(i).getFloorNumber());
                                            wmsLocationStereoscopic1.setFloorNumberList(floorList1);
                                            List<WmsLocationStereoscopic> zhanyong_list =  this.wmsHBLocationStereoscopicMapper.getLayerByGoodsAndBatch(wmsLocationStereoscopic1);
                                            //找到此层的那一行，此行的空货位。
                                            List<WmsLocationStereoscopic> newList=new ArrayList<>();
                                            if((listRight!=null &&listRight.size()>0) ){
                                                if(zhanyong_list!=null &&zhanyong_list.size()>0) {
                                                    for (int n = 0; n < zhanyong_list.size(); n++) {
                                                        for (WmsLocationStereoscopic wmsLocationStereoscopic3 : listRight) {
                                                            if (zhanyong_list.get(n).getLayerNumber().equals(wmsLocationStereoscopic3.getLayerNumber())) {
                                                                newList.add(wmsLocationStereoscopic3);
                                                            }
                                                        }
                                                    }
                                                }else{
                                                    //同层，没有同商品的同批次
                                                    for (WmsLocationStereoscopic wmsLocationStereoscopic3 : listRight) {
                                                        newList.add(wmsLocationStereoscopic3);
                                                    }
                                                }
                                            }else {
                                                //TODO 同层，库位全满。启用备用库位
                                                List<String> beiyong_list = new ArrayList<>();

                                                beiyong_list.add(Constant.StereoscopicInfo.YIKU_WEI_1);
                                                beiyong_list.add(Constant.StereoscopicInfo.YIKU_WEI_2);
                                                beiyong_list.add(Constant.StereoscopicInfo.YIKU_WEI_3);
                                                List<Integer> floor_beiyong_list =new ArrayList<>();
                                                floor_beiyong_list.add(wmsLocationStereoscopicList.get(i).getFloorNumber());
                                                WmsLocationStereoscopic wmsLocationStereoscopic_beiyong =new WmsLocationStereoscopic();
                                                wmsLocationStereoscopic_beiyong.setLocationCodeList(beiyong_list);
                                                wmsLocationStereoscopic_beiyong.setFloorNumberList(floor_beiyong_list);
                                                //查询备用库位的可用库位
                                                newList = this.wmsHBLocationStereoscopicMapper.getBeiYongLocation(wmsLocationStereoscopic_beiyong);
                                            }

                                            WmsMoveStereoscopic wmsMoveStereoscopic =new WmsMoveStereoscopic();
                                            long taskId = this.wmsCommonService.getTaskIds(Constant.TaskType.NORMAL_MOVE, 1)[0];
                                            if(newList!=null &&newList.size()>0){
                                                //目标货位
                                                String locationCode = newList.get(0).getLocationCode();
                                                //拼接移库实体类
                                                wmsMoveStereoscopic.setTaskId(this.wmsCommonService.getTaskIds(TaskType.NORMAL_MOVE, 10)[0]);
                                                wmsMoveStereoscopic.setGoodsCode(wmsLocationStereoscopicList.get(i).getGoodsCode());
                                                wmsMoveStereoscopic.setPalletCode(wmsLocationStereoscopicList.get(i).getPalletCode());
                                                wmsMoveStereoscopic.setFromLocationCode(wmsLocationStereoscopicList.get(i).getLocationCode());
                                                wmsMoveStereoscopic.setToLocationCode(locationCode);
                                                wmsMoveStereoscopic.setAmount(wmsLocationStereoscopicList.get(i).getAmount());
                                                wmsMoveStereoscopic.setBatchNo(wmsLocationStereoscopicList.get(i).getBatchNo());
                                                wmsMoveStereoscopic.setCreateBy("wcs_move");

                                                WmsPallet wmsPallet =new WmsPallet();
                                                wmsPallet.setLockBy(String.valueOf(taskId));
                                                wmsPallet.setPalletCode(wmsLocationStereoscopicList.get(i).getPalletCode());
                                                //根据托盘码锁定托盘
                                                wmsPalletMapper.updateByPalletCode(wmsPallet);
                                                wmsLocationStereoscopic1.setUseStatus("2");
                                                wmsLocationStereoscopic1.setLocationCode(wmsLocationStereoscopicList.get(i).getLocationCode());
                                                wmsLocationStereoscopic1.setLastModifiedBy("wms");
                                                wmsLocationStereoscopic1.setGmtModified(new Date());
                                                this.wmsLocationStereoscopicMapper.updateByLocationCode(wmsLocationStereoscopic1);

                                                JSONObject jsonObject = new JSONObject();

                                                Map map = new HashMap<>();
                                                //组号
                                                jsonObject.put("groupId", ConstantUtil.UUID_GroupId());
                                                //下发时间
                                                jsonObject.put("msgTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                                                //优先级
                                                jsonObject.put("priorityCode", "");
                                                //仓库编码
                                                jsonObject.put("warehouse", "L-NH01");
                                                //任务单号
                                                map.put("taskId", taskId);
                                                //任务类型
                                                map.put("taskType", "2");
                                                //任务起点
                                                map.put("startNode", wmsLocationStereoscopicList.get(i).getLocationCode());
                                                //任务终点
                                                map.put("endNode", locationCode);
                                                //托盘编号
                                                map.put("barCode", wmsLocationStereoscopicList.get(i).getPalletCode());
                                                map.put("order", 1);
                                                ArrayList swiperList = new ArrayList();
                                                swiperList.add(map);
                                                //tasks
                                                jsonObject.put("tasks", JSONArray.parseArray(JSONObject.toJSONString(swiperList)));
                                                try {
                                                    //调WCS出库任务
                                                    log.info("---------------移库，调用wcs的任务接收接口 : " + jsonObject);
                                                    JSONObject returnJsonObject = sLWCSService.taskReceive(jsonObject);
                                                     /*JSONObject returnJsonObject = new JSONObject();
                                                     returnJsonObject.put("returnStatus",0);*/
                                                    //returnJsonObject.put("returnStatus",1);
                                                    log.info("---------------移库，调用wcs的任务接收接口 -结束: " + returnJsonObject.toString());
                                                    //接收成功
                                                    if (returnJsonObject.getInteger("returnStatus") == 0) {
                                                        log.info("移库，调用wcs的任务接收接口-成功返回！");
                                                        this.wmsMoveStereoscopicService.startYkTask(wmsMoveStereoscopic);
                                                    } else {
                                                        log.info("同层 出库-移库， 调wcs接口失败！");
                                                        this.reverseYk(wmsMoveStereoscopic);
                                                    }
                                                } catch (Exception e) {
                                                    log.info("同层 出库-移库， 调wcs接口异常！");
                                                    this.reverseYk(wmsMoveStereoscopic);
                                                }
                                            }
                                        }
                                    }

                                }

                            }
                        }

                        break n;
                    }else{
                        //没有正在出库的任务，也没有创建状态的任务，即 所有出库任务已经完成
                        log.info("任务表中已经没有可执行的任务了！");
                        WmsOrderOutStereoscopic WmsOrderOutStereoscopic =new WmsOrderOutStereoscopic();
                        WmsOrderOutStereoscopic.setOrderNo(orderNo);
                        WmsOrderOutStereoscopic.setOrderStatus("3");
                        WmsOrderOutStereoscopic.setGmtOver(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                        WmsOrderOutStereoscopic.setGmtModified(new Date());
                        WmsOrderOutStereoscopic.setLastModifiedBy("管理员");
                        wmsOrderOutStereoscopicMapper.updateByOrderNo(WmsOrderOutStereoscopic);
                    }

                }

            }
        }else {
            log.info("checkAndSend - 检查数据为空，没有出库任务！");
        }
    }
    /**
     *功能描述:
     * 盘点出库
     * @params
     * @return boolean
     */
    @Override
    public void panDianAndSend(){

    }
    /**
     *功能描述:
     * 检查立库出库单，状态为2：启动
     * 任务执行日志，状态是否存在 2：执行中 的数据
     * 如果WmsLocationStereoscopic为空，则，没有出库启动的数据，或者没有通道正常出库的库位。
     * @author zhangxin
     * @date 2022/8/8
     * @params
     * @return com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic
     */
    @Override
    public WmsLocationStereoscopic checkOutAndTask(){
        WmsLocationStereoscopic wmsLocationStereoscopic = new WmsLocationStereoscopic();
        //根据状态查询数据
        WmsOrderOutStereoscopic condition =new WmsOrderOutStereoscopic();
        condition.setOrderStatus("2");
        List<WmsOrderOutStereoscopic> WmsOrderOutStereoscopicList =this.wmsOrderOutStereoscopicMapper.queryByAny(condition);
        //如果为空，没有出库启动状态的数据。
        if(WmsOrderOutStereoscopicList!=null &&WmsOrderOutStereoscopicList.size()>0){
            for(WmsOrderOutStereoscopic wmsOrderOutStereoscopic : WmsOrderOutStereoscopicList){
                //根据出库单的单号，查询任务执行日志，查看是否存在执行中的状态，若没有，调wcs接口，下发出库任务。
                String orderNo = wmsOrderOutStereoscopic.getOrderNo();
                WmsTaskExecutionLog wmsTaskExecutionLog =new WmsTaskExecutionLog();
                wmsTaskExecutionLog.setOrderNo(orderNo);
                wmsTaskExecutionLog.setTaskStatus("2");
                List<WmsTaskExecutionLog> wmsTaskExecutionLogList =this.wmsTaskExecutionLogMapper.queryByAny(wmsTaskExecutionLog);
                if(wmsTaskExecutionLogList!=null &&wmsTaskExecutionLogList.size()>0){
                    //有正在执行的任务。
                    break;
                }else {
                    //没有正在执行的任务，查找出库的无障碍的库位,下发wcs出库任务。
                    k: for(WmsTaskExecutionLog wmsTaskExecutionLog1 :wmsTaskExecutionLogList){
                        //托盘号
                        String palletCode = wmsTaskExecutionLog1.getPalletCode();
                        //根据托盘号，查找库位。
                        WmsLocationStereoscopic wmsLocationStereoscopic1 =new WmsLocationStereoscopic();
                        wmsLocationStereoscopic1.setPalletCode(palletCode);
                        List<WmsLocationStereoscopic> wmsLocationStereoscopicList = this.wmsLocationStereoscopicMapper.queryByAny(wmsLocationStereoscopic1);
                        //一个托盘只能有一个货位。
                        if(wmsLocationStereoscopicList!=null &&wmsLocationStereoscopicList.size()>0){
                            //判断此巷道是否在短巷道内，如果在短巷道
                            int  shelvesNumber = wmsLocationStereoscopicList.get(0).getShelvesNumber();
                            List<Integer> shelvesNumberList = Arrays.asList(1,3,13,15,25,27,37,39);
                            //如果是短巷道，查询短巷道阻隔sql语句
                            List<WmsLocationStereoscopic> LocationStereoscopicList=null;
                            if(shelvesNumberList.contains(shelvesNumber)){
                                log.info("短巷道：");
                                LocationStereoscopicList = this.wmsHBLocationStereoscopicMapper.getLocationPassDuanDao(wmsLocationStereoscopicList.get(0));
                            }else {
                                log.info("非短巷道：");
                                //判断向道是否被阻隔,有数据即被阻隔
                                LocationStereoscopicList = this.wmsHBLocationStereoscopicMapper.getLocationPass(wmsLocationStereoscopicList.get(0));
                            }
                            //判断向道是否被阻隔,有数据即被阻隔

                            if (LocationStereoscopicList == null || LocationStereoscopicList.size()==0) {
                                wmsLocationStereoscopic = wmsLocationStereoscopicList.get(0);
                                log.info("查到被锁定的可出库的库位：" + wmsLocationStereoscopic.getLocationCode());
                                //将任务执行日志状态，修改为正在执行中。
                                wmsTaskExecutionLog1.setTaskStatus("2");
                                this.wmsTaskExecutionLogMapper.update(wmsTaskExecutionLog1);
                                break k;
                            }
                        }
                    }
                }

            }
        }
        return wmsLocationStereoscopic;
    }
    /**
     * 功能描述:
     * 根据指点的托盘号，检查 库位
     *
     * @return boolean
     * @author zhangxin
     * @date 2022/8/8
     * @params
     */
    @Override
    public WmsLocationStereoscopic checkByPalletCode(String palletCode){
        WmsLocationStereoscopic wmsLocationStereoscopic = new WmsLocationStereoscopic();
        //根据托盘号，查找库位。
        WmsLocationStereoscopic wmsLocationStereoscopic1 =new WmsLocationStereoscopic();
        wmsLocationStereoscopic1.setPalletCode(palletCode);
        List<WmsLocationStereoscopic> wmsLocationStereoscopicList = this.wmsLocationStereoscopicMapper.queryByAny(wmsLocationStereoscopic1);
        //一个托盘只能有一个货位。
        if(wmsLocationStereoscopicList!=null &&wmsLocationStereoscopicList.size()>0){
            //判断向道是否被阻隔,有数据即被阻隔
            //判断此巷道是否在短巷道内，如果在短巷道
            int  shelvesNumber = wmsLocationStereoscopicList.get(0).getShelvesNumber();
            List<Integer> shelvesNumberList = Arrays.asList(1,3,13,15,25,27,37,39);
            //如果是短巷道，查询短巷道阻隔sql语句
            List<WmsLocationStereoscopic> LocationStereoscopicList=null;
            if(shelvesNumberList.contains(shelvesNumber)){
                log.info("短巷道：");
                LocationStereoscopicList = this.wmsHBLocationStereoscopicMapper.getLocationPassDuanDao(wmsLocationStereoscopicList.get(0));
            }else {
                log.info("非短巷道：");
                //判断向道是否被阻隔,有数据即被阻隔
                LocationStereoscopicList = this.wmsHBLocationStereoscopicMapper.getLocationPass(wmsLocationStereoscopicList.get(0));
            }
            if (LocationStereoscopicList ==null  && LocationStereoscopicList.size() == 0 ) {
                wmsLocationStereoscopic = wmsLocationStereoscopicList.get(0);
                log.info("查到被锁定的可出库的库位：" + wmsLocationStereoscopic.getLocationCode());
            }
        }
        return wmsLocationStereoscopic;
    }


    //===================================现场修改接口=======================
    /**
     *功能描述: 定时任务
     * 入库优先，
     * 查询是否有出库任务正在执行，将创建状态为1的，修改成7，暂停下发出库任务。
     * 优先下发入库任务。
     * 如果没有入库任务，再启动出库任务，一次最多下发三个出库任务。（只有三个小车）
     * 入库一个一个的下发
     * @params
     * @return
     */
    @Override
    public void checkReceiptIssue() {
        //判断是否存在移库任务
        WmsTaskExecutionLog yiku_task =new WmsTaskExecutionLog();
        List yikuStatus=new ArrayList();
        //移库，出库只有2、4状态
        yikuStatus.add("2");
        yikuStatus.add("4");
        yiku_task.setTaskStatusList(yikuStatus);
        yiku_task.setTaskType(String.valueOf(TaskType.NORMAL_MOVE.getTaskType()));
        yiku_task.setActiveFlag("1");
        List<WmsTaskExecutionLog> yikulist = wmsTaskExecutionLogService.selReceipt(yiku_task);
        if(yikulist!=null &&yikulist.size()>0){
            //存在移库任务
            //判断是否存在正在执行的出库任务
            WmsTaskExecutionLog wmsTaskExecutionLog1 = new WmsTaskExecutionLog();
            List chukuStatus =new ArrayList();
            chukuStatus.add("2");
            chukuStatus.add("4");
            wmsTaskExecutionLog1.setTaskStatusList(chukuStatus);
            wmsTaskExecutionLog1.setTaskType(String.valueOf(TaskType.STRAIGHT_OUT.getTaskType()));
            wmsTaskExecutionLog1.setActiveFlag("1");
            List<WmsTaskExecutionLog> list = wmsTaskExecutionLogService.selReceipt(wmsTaskExecutionLog1);
            //盘点出库
            wmsTaskExecutionLog1.setTaskType(String.valueOf(TaskType.CHECK_OUT.getTaskType()));
            List<WmsTaskExecutionLog> pandianlist = wmsTaskExecutionLogService.selReceipt(wmsTaskExecutionLog1);
            if (list==null || list.isEmpty() ||pandianlist==null ||pandianlist.isEmpty()) {
                //是否有正在入库的任务
                WmsTaskExecutionLog rukuTask =new WmsTaskExecutionLog();
                List taskStatuslist =new ArrayList();
                taskStatuslist.add("2");//正在执行中
                taskStatuslist.add("4");//异常上报
                taskStatuslist.add("9");//呼叫agv失败，持续呼叫中
                rukuTask.setTaskStatusList(taskStatuslist);
                List list1 = new ArrayList();
                list1.add("10");
                list1.add("50");
                rukuTask.setIdList(list1);
                List<WmsTaskExecutionLog> rukuTaskList = wmsTaskExecutionLogService.selReceipt(rukuTask);
                if(rukuTaskList==null ||rukuTaskList.size()==0){
                    // 可下发成品入库
                    log.info("存在移库任务，可下发成品入库任务");
                    //查询是否存在成品入库任务
                    WmsTaskExecutionLog wmsTaskExecutionLog2 = new WmsTaskExecutionLog();
                    wmsTaskExecutionLog2.setTaskStatus("1");
                    wmsTaskExecutionLog2.setUserDefined5("AGV");
                    List taskTypeList =new ArrayList();
                    taskTypeList.add(String.valueOf(Constant.TaskType.NH_PRODUCT_IN.getTaskType()));
                    wmsTaskExecutionLog2.setIdList(taskTypeList);
                    List<WmsTaskExecutionLog>  receiptList= wmsTaskExecutionLogService.selReceipt(wmsTaskExecutionLog2);
                    List<WmsTaskExecutionLog> receiptList1 =new ArrayList<>();
					if (!receiptList.isEmpty() && receiptList.size() > 0) {
                        for (int i = 0; i < receiptList.size(); i++) {
                            //判断是否已经分配货位，如果分配货位了，且小于2条，可下发第3条。
                            if (receiptList.get(i).getLocationCode() != null && !"".equals(receiptList.get(i).getLocationCode())) {
                                receiptList1.add(receiptList.get(i));
                            }
                        }
                    }
					if (!receiptList1.isEmpty() && receiptList1.size() > 0) {
                    	for (int i = 0; i < receiptList1.size(); i++) {
                        	if (i < 2) {
                    /*if(!receiptList1.isEmpty() &&receiptList1.size()>0){
                        for(int i = 0; i <1;i++){*/
                                JSONObject jsonObject1 = new JSONObject();
                                jsonObject1.put("taskId", receiptList1.get(i).getTaskId());
                                jsonObject1.put("startPoint", receiptList1.get(i).getUserDefined5());
                                jsonObject1.put("endPoint", String.valueOf(Constant.StereoscopicInfo.FLOOR_ONE_OUT_TO_ADDRESS));
                                log.info("调agv 执行下发-开始！");
                                //TODO 调agv 执行下发
                                JSONObject jsonObject = wmsNHAgvService.executeIssue(jsonObject1);
                                // 模拟返回 现场
//                                JSONObject jsonObject = new JSONObject();
//                                jsonObject.put("isSuccessful", "true");
                                log.info("调agv 执行下发-结束！");
                                if (jsonObject.getString("isSuccessful") == "true") {
                                    //任务下发成功，将任务修改为正在执行中。
                                    receiptList1.get(i).setTaskStatus("2");
                                    wmsTaskExecutionLogService.updateByTaskId(receiptList1.get(i));
                                }
                            }
                        }
                    }
                }
            }
        }else {
            //判断是否存在正在执行的出库任务/盘点出库任务
            WmsTaskExecutionLog wmsTaskExecutionLog1 = new WmsTaskExecutionLog();
            List taskStatuslist =new ArrayList();
            taskStatuslist.add("2");
            taskStatuslist.add("4");
            wmsTaskExecutionLog1.setTaskStatusList(taskStatuslist);
            List list1 = new ArrayList();
            list1.add(String.valueOf(TaskType.STRAIGHT_OUT.getTaskType()));
            list1.add(String.valueOf(TaskType.CHECK_OUT.getTaskType()));
            wmsTaskExecutionLog1.setIdList(list1);
            List<WmsTaskExecutionLog> list = wmsTaskExecutionLogService.selReceipt(wmsTaskExecutionLog1);
            if (list==null || list.size()==0) { //没有正在执行的出库任务/盘点出库任务
                //查询正在执行或异常的入库任务
                taskStatuslist =new ArrayList();
                taskStatuslist.add("2");//正在执行中
                taskStatuslist.add("4");//异常上报
                taskStatuslist.add("9");//呼叫agv失败，持续呼叫中
                wmsTaskExecutionLog1.setTaskStatusList(taskStatuslist);
                list1 = new ArrayList();
                list1.add("10");
                list1.add("50");
                wmsTaskExecutionLog1.setIdList(list1);
                //有入库任务 且未分配货位
                List<WmsTaskExecutionLog> receiptList2 = wmsTaskExecutionLogService.selReceipt(wmsTaskExecutionLog1);
                List<WmsTaskExecutionLog> receiptList =new ArrayList<>();
                if(receiptList2!=null && receiptList2.size()>0){
                    for(int i=0;i<receiptList2.size();i++ ){
                        //判断是否已经分配货位，如果分配货位了，且小于2条，可下发第3条。
                        if (receiptList2.get(i).getLocationCode() != null && !"".equals(receiptList2.get(i).getLocationCode())) {
                            receiptList.add(receiptList2.get(i));
                        }
                    }
                }
                // 不存在 或 存在正在执行的入库任务 任务小于3条
                if (receiptList==null || receiptList.size()==0 ||(receiptList!=null && receiptList.size()<3)) {
                //不存在入库任务的时候，，下发出库任务
               /* if (receiptList==null || receiptList.size()==0){*/
                    //查询是否有待入库的 agv 任务
                    wmsTaskExecutionLog1.setTaskStatusList(null);
                    wmsTaskExecutionLog1.setTaskStatus("1");
                    wmsTaskExecutionLog1.setUserDefined5("AGV");
                    wmsTaskExecutionLog1.setUserDefined4("");
                    List<WmsTaskExecutionLog> receiptList1 = wmsTaskExecutionLogService.selReceipt(wmsTaskExecutionLog1);
                    //查询agv 入库任务
                    /*if (receiptList1!=null && receiptList1.size() > 0) {
                        for (int i = 0; i < receiptList1.size(); i++) {
                            if (i < 2) {*/
                    if(!receiptList1.isEmpty() &&receiptList1.size()>0){
                        for(int i = 0; i <1;i++){
                                JSONObject jsonObject1 = new JSONObject();
                                jsonObject1.put("taskId", receiptList1.get(i).getTaskId());
                                jsonObject1.put("startPoint", receiptList1.get(i).getUserDefined5());
                                jsonObject1.put("endPoint", String.valueOf(Constant.StereoscopicInfo.FLOOR_ONE_OUT_TO_ADDRESS));
                                log.info("调agv 执行下发-开始！" +jsonObject1);
                                //TODO 调agv 执行下发
                                JSONObject jsonObject = wmsNHAgvService.executeIssue(jsonObject1);
                                // 模拟返回 现场
//                                JSONObject jsonObject =new JSONObject();
//                                jsonObject.put("isSuccessful","true");
                                log.info("调agv 执行下发-结束！");
                                if (jsonObject.getString("isSuccessful") == "true") {
                                    //任务下发成功，将任务修改为正在执行中。
                                    receiptList1.get(i).setTaskStatus("2");
                                    wmsTaskExecutionLogService.updateByTaskId(receiptList1.get(i));
                                }
                            }
//                        }
                    }
                    if(receiptList1==null || receiptList1.size() == 0){
                        //没有正在执行的出库任务，也没有待入库的AGV 入库任务，查询是否存在 PDA入库任务
                        taskStatuslist =new ArrayList();
                        taskStatuslist.add("1");
                        taskStatuslist.add("2");
                        wmsTaskExecutionLog1.setTaskStatusList(taskStatuslist);
                        wmsTaskExecutionLog1.setUserDefined4("PDA");
                        wmsTaskExecutionLog1.setUserDefined5("");
                        List<WmsTaskExecutionLog> pda_receiptList = wmsTaskExecutionLogService.selReceipt(wmsTaskExecutionLog1);
                        if(receiptList2.size()==0 && receiptList.size()==0 &&( pda_receiptList==null || pda_receiptList.size()==0 )) {
                            //没有出库任务，没有生成正在入库任务的任务且未分配货位，没有待入库的AGV入库任务，也没有待入库的PDA入库任务，查询暂停的出库任务
                            WmsTaskExecutionLog shenhe = new WmsTaskExecutionLog();
                            shenhe.setTaskStatus("8");
                            shenhe.setTaskType(String.valueOf(TaskType.STRAIGHT_OUT.getTaskType()));
                            shenhe.setUserDefined3("0");
                            List<WmsTaskExecutionLog> zantingList = wmsTaskExecutionLogService.queryByAny(shenhe);
                            //存在暂停出库任务，启动出库任务。
                            if (!zantingList.isEmpty() && zantingList.size() > 0) {
                                for (WmsTaskExecutionLog zanting : zantingList) {
                                    zanting.setTaskStatus("7");
                                    wmsTaskExecutionLogService.updateByTaskId(zanting);
                                    WmsOrderOutStereoscopic wmsOrderOutStereoscopic =new WmsOrderOutStereoscopic();
                                    wmsOrderOutStereoscopic.setOrderStatus("2");
                                    wmsOrderOutStereoscopic.setOrderNo(zanting.getOrderNo());
                                    wmsOrderOutStereoscopicMapper.updateByOrderNo(wmsOrderOutStereoscopic);
                                }
                            }
                            //启动出库任务
                            qiDongChuKu();
                        }
                    }
                }
            } else {
                wmsTaskExecutionLog1.setTaskStatusList(null);
                //有正在执行的出库任务，查是否有入库任务，有的话，停掉所有的待出库任务（ 查询是否还有审核通过，但未出库的任务）
                wmsTaskExecutionLog1.setTaskStatus("1");
                list1 = new ArrayList();
                list1.add("10");
                list1.add("50");
                wmsTaskExecutionLog1.setIdList(list1);
                List<WmsTaskExecutionLog> receiptList = wmsTaskExecutionLogService.selReceipt(wmsTaskExecutionLog1);
                if (!receiptList.isEmpty() && receiptList.size() > 0) {
                    WmsTaskExecutionLog shenhe = new WmsTaskExecutionLog();
                    //7待出库的任务
                    shenhe.setTaskStatus("7");
                    shenhe.setTaskType(String.valueOf(TaskType.STRAIGHT_OUT.getTaskType()));
                    shenhe.setUserDefined3("0");
                    List<WmsTaskExecutionLog> shenheList = wmsTaskExecutionLogService.queryByAny(shenhe);
                    if (!shenheList.isEmpty() && shenheList.size() > 0) {
                        for (WmsTaskExecutionLog wmsTaskExecutionLog : shenheList) {
                            //暂停出库任务
                            wmsTaskExecutionLog.setTaskStatus("8");
                            wmsTaskExecutionLogService.updateByTaskId(wmsTaskExecutionLog);
                        }
                    }
                } else {
                    //有正在执行的出库任务，但没有要执行的入库任务。
                    if (list.size() < 3) {
                        qiDongChuKu();
                    }
                }
            }

        }
        //异常呼叫agv失败 重新呼叫agv
        WmsTaskExecutionLog agvError=new WmsTaskExecutionLog();
        agvError.setTaskStatus("9");
        List<WmsTaskExecutionLog> agvErrorList = wmsTaskExecutionLogMapper.queryByAny(agvError);
        if(agvErrorList!=null && agvErrorList.size()>0){
            log.info("入库异常-呼叫agv失败，重新呼叫agv -开始！");
            for(int p =0;p<agvErrorList.size();p++ ){
                JSONObject jsonObject =new JSONObject();
                jsonObject.put("taskId",agvErrorList.get(p).getTaskId());
                jsonObject.put("warning",agvErrorList.get(p).getErrorMsg());
                jsonObject.put("agvErr",true);//异常呼叫agv失败，不调取LED
                interfaceForNHService.abnormal(jsonObject);
            }
            log.info("入库异常-呼叫agv失败，重新呼叫agv -结束！");
        }
    }

    /**
     *功能描述: 启动出库任务
     * 诺华有3两小车，可以一次下发 3个任务。
     * @params
     * @return void
     */
    private void qiDongChuKu(){
        //检查是否有出库任务
        log.info("qiDongChuKu - 检查是否有出库任务");
        //根据状态查询数据
        WmsOrderOutStereoscopic condition =new WmsOrderOutStereoscopic();
        condition.setOrderStatus("2");
        condition.setActiveFlag("1");
        List<WmsOrderOutStereoscopic> WmsOrderOutStereoscopicList =this.wmsOrderOutStereoscopicMapper.queryByAny(condition);
        //立库出库表，不为空，否则有出库启动状态。
        if(WmsOrderOutStereoscopicList!=null &&WmsOrderOutStereoscopicList.size()>0){
            n: for(WmsOrderOutStereoscopic wmsOrderOutStereoscopic : WmsOrderOutStereoscopicList){
                //根据出库单的单号，查询任务执行日志，查看是否存在执行中的状态，若没有，调wcs接口，下发出库任务。
                String orderNo = wmsOrderOutStereoscopic.getOrderNo();
                WmsTaskExecutionLog wmsTaskExecutionLog =new WmsTaskExecutionLog();
                wmsTaskExecutionLog.setOrderNo(orderNo);
                wmsTaskExecutionLog.setTaskStatus("2");
                wmsTaskExecutionLog.setActiveFlag("1");
                List<WmsTaskExecutionLog> wmsTaskExecutionLogList =this.wmsTaskExecutionLogMapper.queryByAny(wmsTaskExecutionLog);
                if(wmsTaskExecutionLogList!=null &&wmsTaskExecutionLogList.size()>3){
                    //有正在执行的任务。 跳出循环
                    log.info("出库 - 有正在执行的任务 "+wmsTaskExecutionLogList.size()+" 条!");
                    break n;
                }else {
                    log.info("正在执行的任务："+wmsTaskExecutionLogList.size() +"条");
                    int number = wmsTaskExecutionLogList.size();
                    //没有正在执行的任务，查找出库的无障碍的库位,下发wcs出库任务。
                    wmsTaskExecutionLog.setTaskStatus("7");
                    wmsTaskExecutionLog.setUserDefined3("0");
                    wmsTaskExecutionLog.setUserDefined2("50");
                    wmsTaskExecutionLog.setActiveFlag("1");
                    //log.info("原材料出库优先于成品出库");
                    List<WmsTaskExecutionLog> wmsTasklogStatusByOne =this.wmsTaskExecutionLogMapper.queryByAny(wmsTaskExecutionLog);
                    log.info("原材料出库 : "+ wmsTasklogStatusByOne.size());
                    if(wmsTasklogStatusByOne==null || wmsTasklogStatusByOne.isEmpty()){
                        wmsTaskExecutionLog.setUserDefined2("10");
                        //log.info("原材料出库优先于成品出库,没有原材料出库！");
                        wmsTasklogStatusByOne =this.wmsTaskExecutionLogMapper.queryByAny(wmsTaskExecutionLog);
                        log.info("成品出库："+ wmsTasklogStatusByOne.size());
                        if(wmsTasklogStatusByOne==null || wmsTasklogStatusByOne.isEmpty()){
                            wmsTaskExecutionLog.setUserDefined2("");
                            //log.info("原材料出库优先于成品出库,没有原材料出库！");
                            wmsTasklogStatusByOne =this.wmsTaskExecutionLogMapper.queryByAny(wmsTaskExecutionLog);
                            log.info("盘点出库：" + wmsTasklogStatusByOne.size());
                        }
                    }
                    if(wmsTasklogStatusByOne!=null &&wmsTasklogStatusByOne.size()>0){
                       /* //将查到信息 进行排序，
                        List mixList = new ArrayList();
                        List maxList = new ArrayList();
                        for (WmsTaskExecutionLog w : wmsTasklogStatusByOne) {
                            int number1 = Integer.parseInt(w.getLocationCode().substring(0, 2));
                            if (number1 < 15) {
                                mixList.add(w);
                            } else {
                                maxList.add(w);
                            }
                        }
                        ListIterator<Integer> li = maxList.listIterator();
                        // 将游标定位到集合的结尾
                        while (li.hasNext()) {
                            li.next();
                        }
                        // 迭代器遍历hasPrevious()方法用于反向遍历的时候判断是否还有下一个元素
                        while (li.hasPrevious()) {
                            mixList.add(li.previous());
                        }
                        wmsTasklogStatusByOne = new ArrayList<>();
                        wmsTasklogStatusByOne.addAll(mixList);*/
                        //标识，是否存在循环完，仍然有向道被阻隔，出不出来的货物。
                        boolean flag = true;
                        int con =0;
                        k:  for (WmsTaskExecutionLog wmsTaskExecutionLog1:wmsTasklogStatusByOne){
                            //任务号
                            Long taskId =wmsTaskExecutionLog1.getTaskId();
                            //托盘号
                            String palletCode = wmsTaskExecutionLog1.getPalletCode();
                            //根据托盘号，查找库位。
                            WmsLocationStereoscopic wmsLocationStereoscopic1 =new WmsLocationStereoscopic();
                            wmsLocationStereoscopic1.setPalletCode(palletCode);
                            List<WmsLocationStereoscopic> wmsLocationStereoscopicList = this.wmsLocationStereoscopicMapper.queryByAny(wmsLocationStereoscopic1);
                            //一个托盘只能有一个货位。
                            if(wmsLocationStereoscopicList!=null &&wmsLocationStereoscopicList.size()>0) {
                                //判断此巷道是否在短巷道内，如果在短巷道
                                int  shelvesNumber = wmsLocationStereoscopicList.get(0).getShelvesNumber();
                                List<Integer> shelvesNumberList = Arrays.asList(1,3,13,15,25,27,37,39);
                                //如果是短巷道，查询短巷道阻隔sql语句
                                List<WmsLocationStereoscopic> LocationStereoscopicList=null;
                                if(shelvesNumberList.contains(shelvesNumber)){
                                    LocationStereoscopicList = this.wmsHBLocationStereoscopicMapper.getLocationPassDuanDao(wmsLocationStereoscopicList.get(0));
                                }else {
                                    //判断向道是否被阻隔,有数据即被阻隔
                                    LocationStereoscopicList = this.wmsHBLocationStereoscopicMapper.getLocationPass(wmsLocationStereoscopicList.get(0));
                                }
                                //判断向道是否被阻隔,有数据即被阻隔
                                if (LocationStereoscopicList==null || LocationStereoscopicList.size() == 0) {
                                    WmsPallet wmsPallet =new WmsPallet();
                                    wmsPallet.setLockBy(String.valueOf(taskId));
                                    wmsPallet.setPalletCode(wmsLocationStereoscopicList.get(0).getPalletCode());
                                    //根据托盘码锁定托盘
                                    wmsPalletMapper.updateByPalletCode(wmsPallet);
                                    wmsLocationStereoscopic1.setUseStatus("2");
                                    wmsLocationStereoscopic1.setLocationCode(wmsLocationStereoscopicList.get(0).getLocationCode());
                                    wmsLocationStereoscopic1.setLastModifiedBy("wms");
                                    wmsLocationStereoscopic1.setGmtModified(new Date());
                                    this.wmsLocationStereoscopicMapper.updateByLocationCode(wmsLocationStereoscopic1);
                                    //可以出库
                                    flag = false;
                                    log.info("查到被锁定的可出库的库位：" + wmsLocationStereoscopicList.get(0).getLocationCode());
                                    JSONObject jsonObject = new JSONObject();

                                    Map map = new HashMap<>();
                                    //组号
                                    jsonObject.put("groupId", ConstantUtil.UUID_GroupId());
                                    //下发时间
                                    jsonObject.put("msgTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                                    //优先级
                                    jsonObject.put("priorityCode", "");
                                    //仓库编码
                                    jsonObject.put("warehouse", "L-NH01");
                                    //任务单号
                                    map.put("taskId", taskId);
                                    //任务类型
                                    map.put("taskType", "1");
                                    //任务起点
                                    map.put("startNode", wmsLocationStereoscopicList.get(0).getLocationCode());
                                    //任务终点
                                    map.put("endNode", Constant.StereoscopicInfo.FLOOR_ONE_OUT_TO_ADDRESS);
                                    //托盘编号
                                    map.put("barCode",wmsLocationStereoscopicList.get(0).getPalletCode());
                                    map.put("order", con++);
                                    ArrayList swiperList = new ArrayList();
                                    swiperList.add(map);
                                    //tasks
                                    jsonObject.put("tasks", JSONArray.parseArray(JSONObject.toJSONString(swiperList)));

                                    log.info("存在出库启动数据和正常出库的库位：============调wcs启动出库任务！");
                                    log.info("出库开始！"+jsonObject);
                                    JSONObject jSONObject = sLWCSService.taskReceive(jsonObject);
                                    // 模拟返回 现场
//                                    String str="{ \"returnStatus\":\"0\", \"ret\": true, \"msg\": \"操作成功\"}";
//                                    JSONObject jSONObject = JSONObject.parseObject(str);
                                    log.info("出库结束！");
                                    if (jSONObject != null) {
                                        //如果调用成功
                                        if ("0".equals(jSONObject.getString("returnStatus"))) {
                                            //任务下发次数
                                            number++;
                                            //将任务执行日志状态，修改为正在执行中。
                                            wmsTaskExecutionLog1.setTaskStatus("2");
                                            this.wmsTaskExecutionLogMapper.updateByTaskId(wmsTaskExecutionLog1);
                                        }
                                    }
                                    /*else {
                                        //如果异常，记得将出库状态正在执行，修改为4：异常。
                                        wmsTaskExecutionLog1.setTaskStatus("4");
                                        this.wmsTaskExecutionLogMapper.updateByTaskId(wmsTaskExecutionLog1);
                                    }*/
                                    //下发了三次，跳出循环
                                    if(number>3) {
                                        break n;
                                    }
                                }else{
                                    //移库
                                    //YiKu(LocationStereoscopicList);
                                }
                            }
                        }
                        //可出库的都循环完了，剩下的需要移库后出库。
                        /*if(flag==true){
                            l: for (WmsTaskExecutionLog wmsTaskExecutionLog2:wmsTasklogStatusByOne){
                                //查到正在出库状态为2的库位，根据库位，查询出阻挡货物出入的库位
                                //TODO 移库
                                Integer floor =  Integer.parseInt(wmsTaskExecutionLog2.getLocationCode().substring(1,2));
                                Integer layer =  Integer.parseInt(wmsTaskExecutionLog2.getLocationCode().substring(4,6));
                                Integer column =  Integer.parseInt(wmsTaskExecutionLog2.getLocationCode().substring(8,10));
                                List<Integer> floorList =new ArrayList<>();
                                floorList.add(floor);
                                WmsLocationStereoscopic wmsLocationStereoscopic =new WmsLocationStereoscopic();
                                wmsLocationStereoscopic.setLocationCode(wmsTaskExecutionLog2.getLocationCode());
                                wmsLocationStereoscopic.setGoodsCode(wmsTaskExecutionLog2.getGoodsCode());
                                wmsLocationStereoscopic.setBatchNo(wmsTaskExecutionLog2.getBatchNo());
                                wmsLocationStereoscopic.setFloorNumberList(floorList);
                                wmsLocationStereoscopic.setLayerNumber(layer);
                                wmsLocationStereoscopic.setColumnNumber(column);
                                //查找是否有货位阻碍通道
                                List<WmsLocationStereoscopic> wmsLocationStereoscopicList = this.wmsHBLocationStereoscopicMapper.getLocationPass(wmsLocationStereoscopic);
                                if(wmsLocationStereoscopicList!=null && wmsLocationStereoscopicList.size()>0) {
                                    //根据阻挡货位的库位，查找是否有移库任务。
                                    for(int i=0;i< wmsLocationStereoscopicList.size();i++){
                                        WmsTaskExecutionLog wmsTaskExecutionLog1=new WmsTaskExecutionLog();
                                        //库位编号
                                        wmsTaskExecutionLog1.setLocationCode(wmsLocationStereoscopicList.get(i).getLocationCode());
                                        wmsTaskExecutionLog1.setGoodsCode(wmsLocationStereoscopicList.get(i).getGoodsCode());
                                        wmsTaskExecutionLog1.setBatchNo(wmsLocationStereoscopicList.get(i).getBatchNo());
                                        wmsTaskExecutionLog1.setTaskStatus("2");

                                        List<WmsTaskExecutionLog> WmsTaskExecutionLogList = this.wmsTaskExecutionLogMapper.queryByAny(wmsTaskExecutionLog1);
                                        if(WmsTaskExecutionLogList!=null &&WmsTaskExecutionLogList.size()>0){
                                            log.info("存在正在移库的任务！");
                                            break l;
                                        }else{
                                            //TODO 启动移库任务
                                            log.info("启动移库任务!");
                                            //查询所有可用库位
                                            //立库库位信息表-实体类
                                            WmsLocationStereoscopic searchOb = new WmsLocationStereoscopic();
                                            searchOb.setGoodsCode(wmsLocationStereoscopicList.get(i).getGoodsCode());
                                            searchOb.setBatchNo(wmsLocationStereoscopicList.get(i).getBatchNo());
                                            //的4个缓存位
                                            List<String> locationCodeList = new ArrayList<>();
                                            locationCodeList.add(Constant.StereoscopicInfo.YIKU_WEI_1);
                                            locationCodeList.add(Constant.StereoscopicInfo.YIKU_WEI_2);
                                            locationCodeList.add(Constant.StereoscopicInfo.YIKU_WEI_3);
                                            //获取每一层的固定的3个不可分配的库位编码
                                            searchOb.setLocationCodeList(locationCodeList);
                                            //-查询所有可用货位，4个缓存位除外
                                            List<WmsLocationStereoscopic> listRight = this.wmsHBLocationStereoscopicMapper.getLocationInfoBylocationCodeList(searchOb);

                                            //根据商品编码，批次号，查找同一批次可放货物的空向道。获取库位。
                                            WmsLocationStereoscopic  wmsLocationStereoscopic1 = new WmsLocationStereoscopic();
                                            wmsLocationStereoscopic1.setGoodsCode(wmsLocationStereoscopicList.get(i).getGoodsCode());
                                            wmsLocationStereoscopic1.setBatchNo(wmsLocationStereoscopicList.get(i).getBatchNo());
                                            List<Integer> floorList1 = new ArrayList<>();
                                            floorList1.add(wmsLocationStereoscopicList.get(i).getFloorNumber());
                                            wmsLocationStereoscopic1.setFloorNumberList(floorList1);
                                            List<WmsLocationStereoscopic> zhanyong_list =  this.wmsHBLocationStereoscopicMapper.getLayerByGoodsAndBatch(wmsLocationStereoscopic1);
                                            //找到此层的那一行，此行的空货位。
                                            List<WmsLocationStereoscopic> newList=new ArrayList<>();
                                            if((listRight!=null &&listRight.size()>0) ){
                                                if(zhanyong_list!=null &&zhanyong_list.size()>0) {
                                                    for (int n = 0; n < zhanyong_list.size(); n++) {
                                                        for (WmsLocationStereoscopic wmsLocationStereoscopic3 : listRight) {
                                                            if (zhanyong_list.get(n).getLayerNumber().equals(wmsLocationStereoscopic3.getLayerNumber())) {
                                                                newList.add(wmsLocationStereoscopic3);
                                                            }
                                                        }
                                                    }
                                                }else{
                                                    //同层，没有同商品的同批次
                                                    for (WmsLocationStereoscopic wmsLocationStereoscopic3 : listRight) {
                                                        newList.add(wmsLocationStereoscopic3);
                                                    }
                                                }
                                            }else {
                                                //TODO 同层，库位全满。启用备用库位
                                                List<String> beiyong_list = new ArrayList<>();

                                                beiyong_list.add(Constant.StereoscopicInfo.YIKU_WEI_1);
                                                beiyong_list.add(Constant.StereoscopicInfo.YIKU_WEI_2);
                                                beiyong_list.add(Constant.StereoscopicInfo.YIKU_WEI_3);
                                                List<Integer> floor_beiyong_list =new ArrayList<>();
                                                floor_beiyong_list.add(wmsLocationStereoscopicList.get(i).getFloorNumber());
                                                WmsLocationStereoscopic wmsLocationStereoscopic_beiyong =new WmsLocationStereoscopic();
                                                wmsLocationStereoscopic_beiyong.setLocationCodeList(beiyong_list);
                                                wmsLocationStereoscopic_beiyong.setFloorNumberList(floor_beiyong_list);
                                                //查询备用库位的可用库位
                                                newList = this.wmsHBLocationStereoscopicMapper.getBeiYongLocation(wmsLocationStereoscopic_beiyong);
                                            }

                                            WmsMoveStereoscopic wmsMoveStereoscopic =new WmsMoveStereoscopic();
                                            long taskId = this.wmsCommonService.getTaskIds(Constant.TaskType.NORMAL_MOVE, 1)[0];
                                            if(newList!=null &&newList.size()>0){
                                                //目标货位
                                                String locationCode = newList.get(0).getLocationCode();
                                                //拼接移库实体类
                                                wmsMoveStereoscopic.setTaskId(this.wmsCommonService.getTaskIds(TaskType.NORMAL_MOVE, 10)[0]);
                                                wmsMoveStereoscopic.setGoodsCode(wmsLocationStereoscopicList.get(i).getGoodsCode());
                                                wmsMoveStereoscopic.setPalletCode(wmsLocationStereoscopicList.get(i).getPalletCode());
                                                wmsMoveStereoscopic.setFromLocationCode(wmsLocationStereoscopicList.get(i).getLocationCode());
                                                wmsMoveStereoscopic.setToLocationCode(locationCode);
                                                wmsMoveStereoscopic.setAmount(wmsLocationStereoscopicList.get(i).getAmount());
                                                wmsMoveStereoscopic.setBatchNo(wmsLocationStereoscopicList.get(i).getBatchNo());
                                                wmsMoveStereoscopic.setCreateBy("wcs_move");

                                                WmsPallet wmsPallet =new WmsPallet();
                                                wmsPallet.setLockBy(String.valueOf(taskId));
                                                wmsPallet.setPalletCode( wmsLocationStereoscopicList.get(i).getPalletCode());
                                                //根据托盘码锁定托盘
                                                wmsPalletMapper.updateByPalletCode(wmsPallet);
                                                wmsLocationStereoscopic1.setUseStatus("2");
                                                wmsLocationStereoscopic1.setLocationCode(wmsLocationStereoscopicList.get(i).getLocationCode());
                                                wmsLocationStereoscopic1.setLastModifiedBy("wms");
                                                wmsLocationStereoscopic1.setGmtModified(new Date());
                                                this.wmsLocationStereoscopicMapper.updateByLocationCode(wmsLocationStereoscopic1);
                                                //TODO 调 移库接口
                                                JSONObject jsonObject = new JSONObject();

                                                Map map = new HashMap<>();
                                                //组号
                                                jsonObject.put("groupId", ConstantUtil.UUID_GroupId());
                                                //下发时间
                                                jsonObject.put("msgTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                                                //优先级
                                                jsonObject.put("priorityCode", "");
                                                //仓库编码
                                                jsonObject.put("warehouse", "L-NH01");
                                                //任务单号
                                                map.put("taskId", taskId);
                                                //任务类型
                                                map.put("taskType", "2");
                                                //任务起点
                                                map.put("startNode", wmsLocationStereoscopicList.get(i).getLocationCode());
                                                //任务终点
                                                map.put("endNode", locationCode);
                                                //托盘编号
                                                map.put("barCode", wmsLocationStereoscopicList.get(i).getPalletCode());
                                                map.put("order", 1);
                                                ArrayList swiperList = new ArrayList();
                                                swiperList.add(map);
                                                //tasks
                                                jsonObject.put("tasks", JSONArray.parseArray(JSONObject.toJSONString(swiperList)));

                                                try {
                                                    //调WCS出库任务
                                                    log.info("---------------移库，调用wcs的任务接收接口 : " + jsonObject);
                                                    JSONObject returnJsonObject = sLWCSService.taskReceive(jsonObject);
                                                     *//*JSONObject returnJsonObject = new JSONObject();
                                                     returnJsonObject.put("returnStatus",0);*//*
                                                    //returnJsonObject.put("returnStatus",1);
                                                    log.info("---------------移库，调用wcs的任务接收接口 -结束: " + returnJsonObject.toString());
                                                    //接收成功
                                                    if (returnJsonObject.getInteger("returnStatus") == 0) {
                                                        log.info("移库，调用wcs的任务接收接口-成功返回！");
                                                        this.wmsMoveStereoscopicService.startYkTask(wmsMoveStereoscopic);
                                                    } else {
                                                        log.info("同层 出库-移库， 调wcs接口失败！");
                                                        this.reverseYk(wmsMoveStereoscopic);
                                                    }
                                                } catch (Exception e) {
                                                    log.info("同层 出库-移库， 调wcs接口异常！");
                                                    this.reverseYk(wmsMoveStereoscopic);
                                                }
                                                if(number>=3){
                                                    break n;
                                                }
                                            }
                                        }
                                    }

                                }
                               *//* //没有正在出库的任务，也没有创建状态的任务，即 所有出库任务已经完成
                                log.info("任务表中已经没有可执行的任务了！");
                                WmsOrderOutStereoscopic WmsOrderOutStereoscopic =new WmsOrderOutStereoscopic();
                                WmsOrderOutStereoscopic.setOrderNo(orderNo);
                                WmsOrderOutStereoscopic.setOrderStatus("3");
                                WmsOrderOutStereoscopic.setGmtOver(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                                WmsOrderOutStereoscopic.setGmtModified(new Date());
                                WmsOrderOutStereoscopic.setLastModifiedBy("管理员");
                                wmsOrderOutStereoscopicMapper.updateByOrderNo(WmsOrderOutStereoscopic);
                                break n;*//*
                            }
                        }*/

                    } else{
                        Integer num =0;
                        //根据订单号，查询任务表，是否都已经是完成状态
                        WmsTaskExecutionLog wmsTaskionLog =new WmsTaskExecutionLog();
                        wmsTaskionLog.setOrderNo(orderNo);
                        wmsTaskionLog.setActiveFlag("1");
                        List<WmsTaskExecutionLog> wmsTaskLogList =this.wmsTaskExecutionLogMapper.queryByAny(wmsTaskionLog);
                        if(wmsTaskLogList!=null && wmsTaskLogList.size()>0) {
                            for (WmsTaskExecutionLog wmsTasklog:wmsTaskLogList){
                                if("3".equals(wmsTasklog.getTaskStatus())){
                                    num++;
                                }
                            }
                            if(num.equals(wmsTaskLogList.size())) {
                                //没有正在出库的任务，也没有创建状态的任务，即 所有出库任务已经完成
                                log.info("任务表中已经没有可执行的任务了！");
                                WmsOrderOutStereoscopic WmsOrderOutStereoscopic = new WmsOrderOutStereoscopic();
                                WmsOrderOutStereoscopic.setOrderNo(orderNo);
                                WmsOrderOutStereoscopic.setOrderStatus("3");
                                WmsOrderOutStereoscopic.setGmtOver(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                                WmsOrderOutStereoscopic.setGmtModified(new Date());
                                WmsOrderOutStereoscopic.setLastModifiedBy("管理员");
                                wmsOrderOutStereoscopicMapper.updateByOrderNo(WmsOrderOutStereoscopic);
                                //将已出库的完成任务作废
                                WmsTaskExecutionLog wmsTaskLog = new WmsTaskExecutionLog();
                                wmsTaskLog.setOrderNo(orderNo);
                                wmsTaskLog.setTaskStatus("3");
                                wmsTaskLog.setTaskType(String.valueOf(Constant.TaskType.STRAIGHT_OUT.getTaskType()));
                                List<WmsTaskExecutionLog> list = wmsTaskExecutionLogMapper.queryByAny(wmsTaskLog);
                                if (list != null && list.size() > 0) {
                                    for (WmsTaskExecutionLog wmsTaskExecutionLog1 : list) {
                                        wmsTaskExecutionLog1.setActiveFlag("0");
                                        wmsTaskExecutionLogMapper.updateByTaskId(wmsTaskExecutionLog1);
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }else {
            log.info("qiDongChuKu - 检查数据为空，没有出库任务！");
        }
    }
    /**
     *功能描述: 移库任务
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    private ResponseResult YiKu(List<WmsLocationStereoscopic> locationStereoscopicList) {
        log.info("启动移库任务！");
        //目标货位
        List huowei_mubiao =new ArrayList();
        Integer number = 0;
        List list =new ArrayList();
        for(WmsLocationStereoscopic wmsLocationStereoscopicList :locationStereoscopicList){
            WmsLocationStereoscopic wmsLocationStereoscopic =new WmsLocationStereoscopic();
            wmsLocationStereoscopic.setShelvesNumber(wmsLocationStereoscopicList.getShelvesNumber());
            wmsLocationStereoscopic.setGoodsCode(wmsLocationStereoscopicList.getGoodsCode());
            wmsLocationStereoscopic.setBatchNo(wmsLocationStereoscopicList.getBatchNo());
            list.add(wmsLocationStereoscopicList.getFloorNumber());
            wmsLocationStereoscopic.setFloorNumberList(list);
            //同一批次的商品，
            List<WmsLocationStereoscopic> tongpi_Location= wmsHBLocationStereoscopicMapper.getLocationCodeByUseStatus(wmsLocationStereoscopic);
            if(tongpi_Location!=null && tongpi_Location.size()>0) {
                k: for (WmsLocationStereoscopic tongpi :tongpi_Location) {
                    if(wmsLocationStereoscopic.getShelvesNumber()!=tongpi.getShelvesNumber()){
                        if(tongpi.getLayerNumber()>15){
                            //查询同巷道最里面的数据
                            List<WmsLocationStereoscopic> l = this.wmsHBLocationStereoscopicMapper.getxiangdaoMax(tongpi);
                            for(WmsLocationStereoscopic ll :l){
                                huowei_mubiao.add(ll.getLocationCode());
                                number++;
                                if (number >= locationStereoscopicList.size()) {
                                    break k;
                                }
                            }
                        }else {
                            huowei_mubiao.add(tongpi.getLocationCode());
                            number++;
                            if (number >= locationStereoscopicList.size()) {
                                break k;
                            }
                        }
                    }
                }
            }else if(number<locationStereoscopicList.size()){
                //查询空巷道信息
                List<WmsLocationStereoscopic> kongxiangdaoList =  wmsHBLocationStereoscopicMapper.getKongXiangDao(wmsLocationStereoscopic);
                if(kongxiangdaoList!=null &&kongxiangdaoList.size()>0){
                    if (kongxiangdaoList.get(0).getLayerNumber() > 15) {
                        log.info("移库，列大于15，查找此行，最里面的库位！");
                        //当y大于15的时候，从数字大的排序
                        List lisnew = this.wmsHBLocationStereoscopicMapper.getLocationInfoByFloorMax(wmsLocationStereoscopic);
                        if (lisnew != null && lisnew.size() > 0) {
                            for (int i=0 ;i<lisnew.size();i++) {
                                String locationCode = ((WmsLocationStereoscopic) lisnew.get(i)).getLocationCode();
                                huowei_mubiao.add(locationCode);
                                number++;
                                if(number<=locationStereoscopicList.size()) {
                                    break;
                                }
                            }
                        }
                    } else {
                        List lisnew =  wmsHBLocationStereoscopicMapper.getLocationInfoByFloor(wmsLocationStereoscopic);
                        //推荐的库位小于15 查询此行，最小的库位
                        if (lisnew != null && lisnew.size() > 0) {
                            log.info("推荐的库位，列小于15，查找此行，最里面的库位！");
                            for (int i=0 ;i<lisnew.size();i++) {
                                String locationCode = ((WmsLocationStereoscopic) lisnew.get(i)).getLocationCode();
                                huowei_mubiao.add(locationCode);
                                number++;
                                if(number<=locationStereoscopicList.size()) {
                                    break;
                                }
                            }
                        }
                    }
                }else{
                    //查询全货位
                    WmsLocationStereoscopic searchOb = new WmsLocationStereoscopic();
                    searchOb.setGoodsCode(wmsLocationStereoscopic.getGoodsCode());
                    searchOb.setBatchNo(wmsLocationStereoscopic.getBatchNo());
                    //的4个缓存位
                    List<String> locationCodeList = new ArrayList<>();
                    locationCodeList.add(Constant.StereoscopicInfo.YIKU_WEI_1);
                    locationCodeList.add(Constant.StereoscopicInfo.YIKU_WEI_2);
                    locationCodeList.add(Constant.StereoscopicInfo.YIKU_WEI_3);
                    //获取每一层的固定的3个不可分配的库位编码
                    searchOb.setLocationCodeList(locationCodeList);
                    //-查询所有可用货位，3个缓存位除外
                    List<WmsLocationStereoscopic> listRight = this.wmsHBLocationStereoscopicMapper.getLocationInfoBylocationCodeList(searchOb);
                    if(listRight!=null &&listRight.size()>0) {
                        for (int i=0 ;i<listRight.size();i++) {
                            String locationCode = listRight.get(i).getLocationCode();
                            huowei_mubiao.add(locationCode);
                            number++;
                            if(number>=locationStereoscopicList.size()) {
                                break;
                            }
                        }
                    }
                }
            }
            List<WmsMoveStereoscopic> WmsMoveStereoscopicList =new ArrayList();
            WmsMoveStereoscopic wmsMoveStereoscopic = new WmsMoveStereoscopic();

            if(huowei_mubiao!=null && huowei_mubiao.size()>0){
                JSONObject jsonObject = new JSONObject();

                //组号
                jsonObject.put("groupId", ConstantUtil.UUID_GroupId());
                //下发时间
                jsonObject.put("msgTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                //优先级
                jsonObject.put("priorityCode", "");
                //仓库编码
                jsonObject.put("warehouse", "L-NH01");
                ArrayList swiperList = new ArrayList();
                for(int i =0;i<huowei_mubiao.size();i++) {
                    Map map = new HashMap<>();
                    long taskId = this.wmsCommonService.getTaskIds(Constant.TaskType.NORMAL_MOVE, 1)[0];
                    //任务单号
                    map.put("taskId", taskId);
                    //任务类型
                    map.put("taskType", "2");
                    //任务起点
                    map.put("startNode", locationStereoscopicList.get(i).getLocationCode());
                    //任务终点
                    map.put("endNode", huowei_mubiao.get(i));
                    //托盘编号
                    map.put("barCode", locationStereoscopicList.get(i).getPalletCode());
                    map.put("order", 1);
                    swiperList.add(map);
                    //拼接移库实体类
                    wmsMoveStereoscopic.setTaskId(taskId);
                    wmsMoveStereoscopic.setGoodsCode(locationStereoscopicList.get(i).getGoodsCode());
                    wmsMoveStereoscopic.setPalletCode(locationStereoscopicList.get(i).getPalletCode());
                    wmsMoveStereoscopic.setFromLocationCode(locationStereoscopicList.get(i).getLocationCode());
                    wmsMoveStereoscopic.setToLocationCode(huowei_mubiao.get(i).toString());
                    wmsMoveStereoscopic.setAmount(locationStereoscopicList.get(i).getAmount());
                    wmsMoveStereoscopic.setBatchNo(locationStereoscopicList.get(i).getBatchNo());
                    wmsMoveStereoscopic.setCreateBy("wcs_move");
                    wmsMoveStereoscopic.setUserDefined1(locationStereoscopicList.get(i).getUserDefined1());
                    wmsMoveStereoscopic.setUserDefined2(locationStereoscopicList.get(i).getUserDefined2());
                    wmsMoveStereoscopic.setUserDefined3(locationStereoscopicList.get(i).getUserDefined3());
                    wmsMoveStereoscopic.setUserDefined4(locationStereoscopicList.get(i).getUserDefined4());
                    wmsMoveStereoscopic.setUserDefined5(locationStereoscopicList.get(i).getUserDefined5());
                    WmsMoveStereoscopicList.add(wmsMoveStereoscopic);

                    WmsPallet wmsPallet =new WmsPallet();
                    wmsPallet.setLockBy(String.valueOf(taskId));
                    wmsPallet.setPalletCode(locationStereoscopicList.get(i).getPalletCode());
                    //根据托盘码锁定托盘
                    wmsPalletMapper.updateByPalletCode(wmsPallet);
                    WmsLocationStereoscopic wmsLocationStereoscopic1 =new WmsLocationStereoscopic();
                    wmsLocationStereoscopic1.setLocationCode(locationStereoscopicList.get(i).getLocationCode());
                    wmsLocationStereoscopic1.setUseStatus("2");
                    wmsLocationStereoscopic1.setLastModifiedBy("wms");
                    wmsLocationStereoscopic1.setGmtModified(new Date());
                    this.wmsLocationStereoscopicMapper.updateByLocationCode(wmsLocationStereoscopic1);

                }
                //tasks
                jsonObject.put("tasks", JSONArray.parseArray(JSONObject.toJSONString(swiperList)));
                try {
                    //调WCS出库任务
                    log.info("--------移库，调用wcs的任务接收接口 : -------" + jsonObject);
                    JSONObject returnJsonObject = sLWCSService.taskReceive(jsonObject);
                    //模拟返回
//                     JSONObject returnJsonObject = new JSONObject();
//                     returnJsonObject.put("returnStatus",0);
                    //returnJsonObject.put("returnStatus",1);
                    log.info("---------------移库，调用wcs的任务接收接口 -结束: " + returnJsonObject.toString());
                    //接收成功
                    if (returnJsonObject.getInteger("returnStatus") == 0) {
                        log.info("移库，调用wcs的任务接收接口-成功返回！");
                        if(WmsMoveStereoscopicList!=null &&WmsMoveStereoscopicList.size()>0) {
                            for (WmsMoveStereoscopic wmsMoveStereoscopicNew:WmsMoveStereoscopicList) {
                                this.wmsMoveStereoscopicService.startYkTask(wmsMoveStereoscopicNew);
                            }
                        }
                        log.info("修改当前");
                        return new ResponseResult(Constant.RESULT.SUCCESS.code, "移库任务:请求成功！", (Object) null);
                    } else {
                        log.info("同层 出库-移库， 调wcs接口失败！");
                        if(WmsMoveStereoscopicList!=null &&WmsMoveStereoscopicList.size()>0) {
                            for (WmsMoveStereoscopic wmsMoveStereoscopicNew:WmsMoveStereoscopicList) {
                                this.reverseYk(wmsMoveStereoscopicNew);
                            }
                        }

                        return new ResponseResult(Constant.RESULT.FAILED.code, "移库任务:请求失败！" + returnJsonObject.getString("returnInfo"), (Object) null);
                    }
                } catch (Exception e) {
                    log.info("同层 出库-移库， 调wcs接口异常！");
                    if(WmsMoveStereoscopicList!=null &&WmsMoveStereoscopicList.size()>0) {
                        for (WmsMoveStereoscopic wmsMoveStereoscopicNew:WmsMoveStereoscopicList) {
                            this.reverseYk(wmsMoveStereoscopicNew);
                        }
                    }
                    return new ResponseResult(Constant.RESULT.FAILED.code, "移库任务:请求失败！" + e.toString(), (Object) null);
                }
            }
        }
        return new ResponseResult(Constant.RESULT.SUCCESS.code, "移库成功！", (Object) null);
    }

    /**
     *功能描述: PDA手动入库，有出库任务，出库任务完成后，给用户一个LED提示。
     * @params
     * @return void
     */
    @Override
    public void checkShouDongRuKu(){
        //判断是否存在移库任务
        WmsTaskExecutionLog yiku_task =new WmsTaskExecutionLog();
        List yikuStatus =new ArrayList();
        yikuStatus.add("2");
        yikuStatus.add("4");
        yiku_task.setTaskStatusList(yikuStatus);
        yiku_task.setTaskType(String.valueOf(TaskType.NORMAL_MOVE.getTaskType()));
        yiku_task.setActiveFlag("1");
        List<WmsTaskExecutionLog> yikulist = wmsTaskExecutionLogService.selReceipt(yiku_task);
        if(yikulist!=null &&yikulist.size()>0){
            log.info("存在正在移库的任务！");
        }else {
            //判断是否存在正在执行的出库任务
            WmsTaskExecutionLog wmsTaskExecutionLog1 = new WmsTaskExecutionLog();
            List chukuStatus =new ArrayList();
            chukuStatus.add("2");
            chukuStatus.add("4");
            wmsTaskExecutionLog1.setTaskStatusList(chukuStatus);
            wmsTaskExecutionLog1.setTaskType(String.valueOf(TaskType.STRAIGHT_OUT.getTaskType()));
            wmsTaskExecutionLog1.setActiveFlag("1");
            List<WmsTaskExecutionLog> list = wmsTaskExecutionLogService.selReceipt(wmsTaskExecutionLog1);
            if (list==null ||list.size()==0) { //没有正在执行的出库任务
                List rukuStatus =new ArrayList();
                rukuStatus.add("2");
                rukuStatus.add("4");
                wmsTaskExecutionLog1.setTaskStatusList(rukuStatus);
                wmsTaskExecutionLog1.setTaskType(null);
                List list1 = new ArrayList();
                list1.add("10");
                list1.add("50");
                wmsTaskExecutionLog1.setIdList(list1);
                List<WmsTaskExecutionLog> receiptList = wmsTaskExecutionLogService.selReceipt(wmsTaskExecutionLog1);
                if(receiptList!=null &&receiptList.size()>0){
                    for(WmsTaskExecutionLog wmsTaskExecutionLog:receiptList){
                        if(wmsTaskExecutionLog.getUserDefined4()!=null && !wmsTaskExecutionLog.getUserDefined4().isEmpty()){
                            log.info("存在手动入库的任务！");
                        }
                    }
                }
            }else {
                log.info("存在正在出库的任务！");
            }
        }
    }
}
