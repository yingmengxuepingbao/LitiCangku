//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.outboundmanagement.controller;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.basicdatamanagement.model.business.IBaseDictItemService;
import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictItem;
import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.constant.Constant.RecommendLocationEnum;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.usermanagement.model.entity.User;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wms.basicmanagement.model.business.IDifferentBusinessService;
import com.penghaisoft.wms.basicmanagement.model.business.impl.DifferentBusinessFactory;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsOutTask;
import com.penghaisoft.wms.outboundmanagement.model.business.IWmsOrderOutStereoscopicDeailService;
import com.penghaisoft.wms.outboundmanagement.model.business.IWmsOrderOutStereoscopicService;
import com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutStereoscopic;
import com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutStereoscopicDeail;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping({"/wmsOrderOutStereoscopic"})
public class WmsOrderOutStereoscopicController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WmsOrderOutStereoscopicController.class);
    @Autowired
    private IWmsOrderOutStereoscopicService wmsOrderOutStereoscopicService;
    @Autowired
    private IWmsOrderOutStereoscopicDeailService wmsOrderOutStereoscopicDeailService;
    @Autowired
    private DifferentBusinessFactory differentBusinessFactory;
    @Autowired
    private IBaseDictItemService baseDictItemService;
    @Autowired
    private IUserBusiness userBusiness;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${notice.other-sys-addr.pallet-out}")
    private String noticePalletOutAddr;
    @Value("${applyfactory}")
    private String applyfactory;

    public WmsOrderOutStereoscopicController() {
    }

    @PostMapping({"c"})
    public ResponseResult create(WmsOrderOutStereoscopic wmsOrderOutStereoscopic) {
        wmsOrderOutStereoscopic.setActiveFlag("1");
        wmsOrderOutStereoscopic.setOrderOutId(CommonUtils.getUUID());
        wmsOrderOutStereoscopic.setCreateBy(this.userBusiness.getCurrentUser().getNickname());
        wmsOrderOutStereoscopic.setGmtCreate(new Date());
        Resp resp = this.wmsOrderOutStereoscopicService.create(wmsOrderOutStereoscopic);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }
    /**
     *功能描述: 出库任务单 - 删除
     * @author zhangxin
     * @date 2023/2/4
     * @params  
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @PostMapping({"delete"})
    public ResponseResult delete(WmsOrderOutStereoscopic wmsOrderOutStereoscopic) {
        wmsOrderOutStereoscopic.setLastModifiedBy(this.userBusiness.getCurrentUser().getNickname());
        wmsOrderOutStereoscopic.setGmtModified(new Date());

        Resp resp = this.wmsOrderOutStereoscopicService.delete(wmsOrderOutStereoscopic);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"list"})
    public ResponseResult list(@RequestParam(name = "page",defaultValue = "1") int page, @RequestParam(name = "rows",defaultValue = "10") int rows, WmsOrderOutStereoscopic wmsOrderOutStereoscopic) {
        wmsOrderOutStereoscopic.setActiveFlag("1");
        if (wmsOrderOutStereoscopic.getOrderBy() == null || "".equals(wmsOrderOutStereoscopic.getOrderBy())) {
            wmsOrderOutStereoscopic.setOrderBy("gmt_create desc");
        }

        Pager<WmsOrderOutStereoscopic> pager = this.wmsOrderOutStereoscopicService.findListByCondition(page, rows, wmsOrderOutStereoscopic);
        List<BaseDictItem> baseDictItemList = this.baseDictItemService.getDictTypeByCode("order_out_type");
        if (pager.getRecords() != null && !pager.getRecords().isEmpty() && baseDictItemList != null && !baseDictItemList.isEmpty()) {
            Iterator var6 = pager.getRecords().iterator();

            while(var6.hasNext()) {
                WmsOrderOutStereoscopic tmp = (WmsOrderOutStereoscopic)var6.next();
                Iterator var8 = baseDictItemList.iterator();

                while(var8.hasNext()) {
                    BaseDictItem baseDictItem = (BaseDictItem)var8.next();
                    if (null != tmp.getOrderType() && tmp.getOrderType().equals(baseDictItem.getDicItemCode())) {
                        tmp.setOrderType(baseDictItem.getDicItemName());
                    }
                }
                if (null != tmp.getOrderType() && tmp.getOrderType().equals("26")) {
                    tmp.setOrderType("人工出库");
                }
            }
        }
        ResponseResult result = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, pager);
        return result;
    }

    @GetMapping({"r/{id}"})
    public ResponseResult queryById(@PathVariable String id) {
        WmsOrderOutStereoscopic wmsOrderOutStereoscopic = this.wmsOrderOutStereoscopicService.findById(id);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, wmsOrderOutStereoscopic);
    }

    @PostMapping({"u"})
    public ResponseResult update(@RequestBody WmsOrderOutStereoscopic wmsOrderOutStereoscopic) {
        Resp resp = this.wmsOrderOutStereoscopicService.createAndEditDeal(wmsOrderOutStereoscopic, this.userBusiness.getCurrentUser().getNickname());
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), resp);
        return result;
    }
    /**
     *功能描述: 新增需求：同批次出库，已经存在的出库单 未执行完，不允许新建出库单
     * @author zhangxin
     * @date 2023/2/22
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @PostMapping({"sel"})
    public ResponseResult sel(@RequestBody WmsOrderOutStereoscopic wmsOrderOutStereoscopic) {
        List<WmsOrderOutStereoscopicDeail>  resultList=new ArrayList<>();
        List<WmsOrderOutStereoscopicDeail> list= wmsOrderOutStereoscopic.getWmsOrderOutStereoscopicDeailList();
        for(WmsOrderOutStereoscopicDeail wmsOrderOutStereoscopicDeail :list){
            wmsOrderOutStereoscopicDeail.setGoodsName("");
            wmsOrderOutStereoscopicDeail.setActiveFlag("1");
            wmsOrderOutStereoscopicDeail.setPlanAmount(null);
            List<WmsOrderOutStereoscopicDeail>  list_new =  this.wmsOrderOutStereoscopicDeailService.queryByAny(wmsOrderOutStereoscopicDeail);
            if(list_new!=null && list_new.size()>0){
                for (WmsOrderOutStereoscopicDeail wmsOrderOutStereoscopicDeail_new : list_new){
                    WmsOrderOutStereoscopic wmsOrderOutStereoscopic_new =new WmsOrderOutStereoscopic();
                    wmsOrderOutStereoscopic_new.setOrderNo(wmsOrderOutStereoscopicDeail_new.getOrderNo());
                    wmsOrderOutStereoscopic_new.setOrderStatus("2");
                    wmsOrderOutStereoscopic_new.setActiveFlag("1");
                    wmsOrderOutStereoscopic_new.setUserDefined2(wmsOrderOutStereoscopicDeail_new.getUserDefined3());
                    List<WmsOrderOutStereoscopic> list1=  this.wmsOrderOutStereoscopicService.queryByAny(wmsOrderOutStereoscopic_new);
                    wmsOrderOutStereoscopic_new.setOrderStatus("1");
                    List<WmsOrderOutStereoscopic> list2=  this.wmsOrderOutStereoscopicService.queryByAny(wmsOrderOutStereoscopic_new);
                    if((list1!=null && list1.size()>0)|| (list2!=null &&list2.size()>0) ) {
                        resultList.add(wmsOrderOutStereoscopicDeail_new);
                    }
                }
            }
        }
        //存在相同数据
        if(resultList!=null && resultList.size()>0){
            return new ResponseResult(RESULT.FAILED.code, "已存在【商品和批次】未完成的单据，请将单据处理后再重新创建单据！", (Object)null);
        }else {

            return new ResponseResult(RESULT.SUCCESS.code, "允许创建单据！", (Object)null);
        }
    }

    @PostMapping({"batch/start"})
    public ResponseResult batchStart(@RequestBody WmsOrderOutStereoscopic wmsOrderOutStereoscopic) {
        User currentUser = this.userBusiness.getCurrentUser();
        String loginName = currentUser.getNickname();
        String loginNameWithAccount = currentUser.getNickname() + "(" + currentUser.getAccount() + ")";
        Resp resp = this.wmsOrderOutStereoscopicService.checkStatusAmount(wmsOrderOutStereoscopic);
        if (RESULT.FAILED.code.equals(resp.getCode())) {
            ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
            return result;
        } else {
            IDifferentBusinessService differentBusinessService = this.differentBusinessFactory.getIDifferentBusinessService(RecommendLocationEnum.getServiceNameByCode(this.applyfactory));
            List<String> orderNoList = wmsOrderOutStereoscopic.getOrderNoList();
            StringBuffer sb = new StringBuffer("");
            Iterator var10 = orderNoList.iterator();

            while(true) {
                String orderNo;
                LinkedList wmsOutTaskList;
                String targetAddress;
                List detailList;
                do {
                    do {
                        if (!var10.hasNext()) {
                            return new ResponseResult(RESULT.SUCCESS.code, sb.toString(), null);
                        }
                        orderNo = (String)var10.next();
                        wmsOutTaskList = new LinkedList();
                        targetAddress = "";
                        WmsOrderOutStereoscopicDeail searchDetailOb = new WmsOrderOutStereoscopicDeail();
                        searchDetailOb.setOrderNo(orderNo);
                        searchDetailOb.setActiveFlag("1");
                        detailList = this.wmsOrderOutStereoscopicDeailService.queryByAny(searchDetailOb);
                    } while(detailList == null);
                } while(detailList.isEmpty());

                targetAddress = ((WmsOrderOutStereoscopicDeail)detailList.get(0)).getOutAddress();
                Iterator var16 = detailList.iterator();

                while(var16.hasNext()) {
                    WmsOrderOutStereoscopicDeail tmp = (WmsOrderOutStereoscopicDeail)var16.next();
                    resp = differentBusinessService.queryOutRecommendLocationCode(tmp.getGoodsCode(), tmp.getBatchNo(), tmp.getPlanAmount(), targetAddress, loginNameWithAccount);
                    if (resp != null && RESULT.FAILED.code.equals(resp.getCode())) {
                        differentBusinessService.revertOut((List)null, wmsOutTaskList, orderNo);
                        sb.append("出库单(" + orderNo + ")启动失败：" + resp.getMsg() + ";");
                        return new ResponseResult(RESULT.FAILED.code, sb.toString(), (Object)null);
                    }

                    WmsLocationStereoscopic returnData = (WmsLocationStereoscopic)resp.getData();
                    wmsOutTaskList.addAll(returnData.getWmsOutTaskList());
                    if (returnData.getWmsOutTaskList() != null && !returnData.getWmsOutTaskList().isEmpty()) {
                        WmsOrderOutStereoscopicDeail updateOb = new WmsOrderOutStereoscopicDeail();
                        updateOb.setOrderOutDetailId(tmp.getOrderOutDetailId());
                        updateOb.setActiveFlag("1");
                        updateOb.setPlanPalletAmount(returnData.getWmsOutTaskList().size());
                        this.wmsOrderOutStereoscopicDeailService.update(updateOb);
                    }
                }

                try {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<List<WmsOutTask>> request = new HttpEntity(wmsOutTaskList, headers);
                    ResponseEntity<JSONObject> wcsResp = this.restTemplate.postForEntity(this.noticePalletOutAddr, request, JSONObject.class, new Object[0]);
                    if (wcsResp.getStatusCodeValue() != 200) {
                        log.error("调wcs接口失败！");
                        differentBusinessService.revertOut((List)null, wmsOutTaskList, orderNo);
                        sb.append("出库单号(" + orderNo + ")启动失败，获取推荐库位成功，调用WCS出库接口失败;");
                        return new ResponseResult(RESULT.FAILED.code, sb.toString(), (Object)null);
                    }

                    JSONObject noticeResult = (JSONObject)wcsResp.getBody();
                    if (!noticeResult.getString("code").equals("1")) {
                        log.error("调wcs接口失败：" + noticeResult.getString("message"));
                        differentBusinessService.revertOut((List)null, wmsOutTaskList, orderNo);
                        sb.append("出库单号(" + orderNo + ")启动失败，获取推荐库位成功，调用WCS出库接口失败;");
                        return new ResponseResult(RESULT.FAILED.code, sb.toString(), (Object)null);
                    }

                    log.info("调wcs接口成功！");
                    this.wmsOrderOutStereoscopicService.startOrderNo((List)null, wmsOutTaskList, orderNo, loginName);
                    sb.append("出库单(" + orderNo + ")启动成功;");
                } catch (Exception var20) {
                    var20.printStackTrace();
                    differentBusinessService.revertOut((List)null, wmsOutTaskList, orderNo);
                    sb.append("出库单(" + orderNo + ")启动失败，获取推荐库位成功，调用WCS出库接口失败;");
                    return new ResponseResult(RESULT.FAILED.code, sb.toString(), (Object)null);
                }
            }
        }
    }

    @PostMapping({"batch/merge"})
    public ResponseResult batchMerge(@RequestBody WmsOrderOutStereoscopic wmsOrderOutStereoscopic) {
        List<String> outAddressSet = new ArrayList();
        List<WmsOrderOutStereoscopic> list = this.wmsOrderOutStereoscopicService.queryByAny(wmsOrderOutStereoscopic);
        ResponseResult result;
        if (list != null && !list.isEmpty()) {
            Iterator var5 = list.iterator();

            while(var5.hasNext()) {
                WmsOrderOutStereoscopic tmp = (WmsOrderOutStereoscopic)var5.next();
                if (tmp.getOrderStatus() != null && !"1".equals(tmp.getOrderStatus())) {
                    result = new ResponseResult(RESULT.FAILED.code, "出库单包含状态非创建的记录！", (Object)null);
                    return result;
                }

                if (tmp.getOutAddress() == null || "".equals(tmp.getOutAddress())) {
                    result = new ResponseResult(RESULT.FAILED.code, "出库单中出库口数据异常！", (Object)null);
                    return result;
                }

                if (!outAddressSet.contains(tmp.getOutAddress())) {
                    outAddressSet.add(tmp.getOutAddress());
                }
            }
        }

        if (outAddressSet.size() > 1) {
            result = new ResponseResult(RESULT.FAILED.code, "出库单中包含不同的出库口记录，不能合并！", (Object)null);
            return result;
        } else {
            Resp resp = this.wmsOrderOutStereoscopicService.batchMerge(wmsOrderOutStereoscopic, (String)outAddressSet.get(0), this.userBusiness.getCurrentUser().getNickname());
            result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
            return result;
        }
    }
}
