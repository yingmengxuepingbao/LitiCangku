//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.nuohua.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.constant.Constant.RecommendLocationEnum;
import com.penghaisoft.framework.constant.Constant.TaskType;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.usermanagement.model.entity.User;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic;
import com.penghaisoft.wms.common.service.IWmsCommonService;
import com.penghaisoft.wms.nuohua.service.DifferentBusinessNHFactory;
import com.penghaisoft.wms.nuohua.service.DifferentBusinessNHService;
import com.penghaisoft.wms.nuohua.service.SLWCSService;
import com.penghaisoft.wms.nuohua.service.WcsForNHService;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsMoveStereoscopicService;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsMoveStereoscopic;
import com.penghaisoft.wms.util.ConstantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 *功能描述: 移库
 * @author zhangxin
 * @date 2022/7/21
 * @params
 * @return
 */
@RestController
@RequestMapping({"/wmsHBMoveStereoscopic"})
public class WmsNHMoveStereoscopicController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WmsNHMoveStereoscopicController.class);
    @Autowired
    private IWmsMoveStereoscopicService wmsMoveStereoscopicService;
    @Autowired
    private DifferentBusinessNHFactory differentBusinessNHFactory;
    @Autowired
    private IUserBusiness userBusiness;
    @Autowired
    private IWmsCommonService wmsCommonService;
    @Autowired
    private SLWCSService sLWCSService;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${notice.other-sys-addr.pallet-move}")
    private String noticePalletMoveAddr;
    @Value("${applyfactory}")
    private String applyfactory;

    //调WCS接口
    @Autowired
    private WcsForNHService wcsForNHService;


    public WmsNHMoveStereoscopicController() {
    }

    @PostMapping({"c"})
    public ResponseResult create(WmsMoveStereoscopic wmsMoveStereoscopic) {
        Resp resp = this.wmsMoveStereoscopicService.create(wmsMoveStereoscopic);
        ResponseResult responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, resp);
        return responseResult;
    }

    @PostMapping({"d"})
    public ResponseResult delete(WmsMoveStereoscopic wmsMoveStereoscopic) {
        wmsMoveStereoscopic.setLastModifiedBy(this.userBusiness.getCurrentUser().getNickname());
        wmsMoveStereoscopic.setGmtModified(new Date());
        Resp resp = this.wmsMoveStereoscopicService.delete(wmsMoveStereoscopic);
        ResponseResult responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, resp);
        return responseResult;
    }

    @PostMapping({"list"})
    public ResponseResult list(@RequestParam(name = "page",defaultValue = "1") int page, @RequestParam(name = "rows",defaultValue = "10") int rows, WmsMoveStereoscopic wmsMoveStereoscopic) {
        wmsMoveStereoscopic.setActiveFlag("1");
        Pager<WmsMoveStereoscopic> result = this.wmsMoveStereoscopicService.findListByCondition(page, rows, wmsMoveStereoscopic);
        ResponseResult responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, result);
        return responseResult;
    }
    /**
     *功能描述: 创建批量移库任务：确保最多只占两个巷道，
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @PostMapping({"createYkTaskList"})
    public ResponseResult createYkTaskList(WmsMoveStereoscopic wmsMoveStereoscopic) {
        log.info("创建批量移库任务:开始");
        Resp resp = this.wmsMoveStereoscopicService.createYkTaskList(wmsMoveStereoscopic);
        log.info("创建批量移库任务:结束");
        ResponseResult responseResult = new ResponseResult(resp.getCode(), resp.getMsg(), (Object) null);
        return responseResult;
    }
    @PostMapping({"create/edit"})
    public ResponseResult createOrEditYkTask(WmsMoveStereoscopic wmsMoveStereoscopic) {
        wmsMoveStereoscopic.setCreateBy(this.userBusiness.getCurrentUser().getNickname());
        DifferentBusinessNHService differentBusinessNHService = this.differentBusinessNHFactory.getDifferentBusinessNHService(RecommendLocationEnum.getServiceNameByCode(this.applyfactory));
        Resp resp = differentBusinessNHService.createOrEditYk(wmsMoveStereoscopic);
        ResponseResult responseResult = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return responseResult;
    }

    /**
     *功能描述: 启动移库任务
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @PostMapping({"start"})
    public ResponseResult startYkTask(WmsMoveStereoscopic wmsMoveStereoscopic) {
        User currentUser = this.userBusiness.getCurrentUser();
        String loginName = currentUser.getNickname();
        wmsMoveStereoscopic.setCreateBy(loginName);
        DifferentBusinessNHService differentBusinessNHService = this.differentBusinessNHFactory.getDifferentBusinessNHService(RecommendLocationEnum.getServiceNameByCode(this.applyfactory));
        //检查此库位是否可移动
        Resp resp = differentBusinessNHService.locationLockYk(wmsMoveStereoscopic);
        if (RESULT.FAILED.code.equals(resp.getCode())) {
            return new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        } else {
            WmsMoveStereoscopic wmsMoveStereoscopicNew = (WmsMoveStereoscopic)resp.getData();
            long taskId = Long.valueOf(wmsMoveStereoscopicNew.getMoveId());
            try {
                JSONObject jSONObject =new JSONObject();
                //根据指定的托盘号，检查
                WmsLocationStereoscopic wmsLocationStereoscopic = differentBusinessNHService.checkByPalletCode(wmsMoveStereoscopicNew.getPalletCode());
                 if(wmsLocationStereoscopic != null ) {
                    JSONObject jsonObject = new JSONObject();
                    Map map =new HashMap<>();
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
                    map.put("startNode", wmsMoveStereoscopicNew.getFromLocationCode());
                    //任务终点
                    map.put("endNode", wmsMoveStereoscopicNew.getToLocationCode());
                    //托盘编号
                    map.put("barCode", wmsMoveStereoscopicNew.getPalletCode());
                    map.put("order", 1);
                    ArrayList swiperList = new ArrayList();
                    swiperList.add(map);
                    //tasks
                    jsonObject.put("tasks", JSONArray.parseArray(JSONObject.toJSONString(swiperList)));
                     try {
                         //调WCS出库任务
                         log.info("---------------移库，调用wcs的任务接收接口 : " + jsonObject);
                         JSONObject returnJsonObject = sLWCSService.taskReceive(jsonObject);
                         // 模拟返回 现场
//                         JSONObject returnJsonObject = new JSONObject();
//                         returnJsonObject.put("returnStatus",0);
                         //returnJsonObject.put("returnStatus",1);
                         log.info("---------------移库，调用wcs的任务接收接口 -结束: " + returnJsonObject.toString());
                         //接收成功
                         if ( returnJsonObject.getInteger("returnStatus") ==0) {
                             log.info("移库，调用wcs的任务接收接口-成功返回！");
                             this.wmsMoveStereoscopicService.startYkTask(wmsMoveStereoscopicNew);
                             return new ResponseResult(RESULT.SUCCESS.code, "移库任务:" + taskId + "请求成功！",(Object)null);
                         } else {
                             differentBusinessNHService.reverseYk(wmsMoveStereoscopicNew);
                             return new ResponseResult(RESULT.FAILED.code, "移库任务:" + taskId + "请求失败！"+returnJsonObject.getString("returnInfo"),(Object)null);
                         }
                     }catch (Exception e){
                         return new ResponseResult(RESULT.FAILED.code, "移库任务:" + taskId + "请求失败！"+e.toString(),(Object)null);
                     }
                }else {
                    differentBusinessNHService.reverseYk(wmsMoveStereoscopicNew);
                    return new ResponseResult(RESULT.FAILED.code, "移库单号(" + wmsMoveStereoscopicNew.getMoveNo() + ")启动失败，此库位不可移动！", (Object)null);
                }

     /*     try {
                wmsMoveStereoscopicNew.setTaskId(taskId);
                WmsMoveTask wmsMoveTask = new WmsMoveTask();
                wmsMoveTask.setTaskId(taskId);
                wmsMoveTask.setTaskType(String.valueOf(TaskType.NORMAL_MOVE));
                wmsMoveTask.setFromLocation(wmsMoveStereoscopicNew.getFromLocationCode());
                wmsMoveTask.setTargetLocation(wmsMoveStereoscopicNew.getToLocationCode());
                wmsMoveTask.setPalletCode(wmsMoveStereoscopicNew.getPalletCode());
                wmsMoveTask.setOperator(loginNameWithAccount);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<WmsMoveTask> request = new HttpEntity(wmsMoveTask, headers);
                ResponseEntity<JSONObject> wcsResp = this.restTemplate.postForEntity(this.noticePalletMoveAddr, request, JSONObject.class, new Object[0]);
                if (wcsResp.getStatusCodeValue() != 200) {
                    log.error("调wcs接口失败！");
                    differentBusinessHBService.reverseYk(wmsMoveStereoscopicNew);
                    return new ResponseResult(RESULT.FAILED.code, "移库单号(" + wmsMoveStereoscopicNew.getMoveNo() + ")启动失败，调用WCS出库接口失败;", (Object)null);
                }

                JSONObject noticeResult = (JSONObject)wcsResp.getBody();
                if (!noticeResult.getString("code").equals("1")) {
                    log.error("调wcs接口失败：" + noticeResult.getString("message"));
                    differentBusinessHBService.reverseYk(wmsMoveStereoscopicNew);
                    return new ResponseResult(RESULT.FAILED.code, "移库单号(" + wmsMoveStereoscopicNew.getMoveNo() + ")启动失败，调用WCS出库接口失败;", (Object)null);
                }
                 log.info("调wcs接口成功！");
                this.wmsMoveStereoscopicService.startYkTask(wmsMoveStereoscopicNew);
*/

            } catch (Exception var16) {
                differentBusinessNHService.reverseYk(wmsMoveStereoscopicNew);
                return new ResponseResult(RESULT.FAILED.code, "移库单号(" + wmsMoveStereoscopicNew.getMoveNo() + ")启动失败，调用WCS出库接口失败;", (Object)null);
            }
        }
    }
}