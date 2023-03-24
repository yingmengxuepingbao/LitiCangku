//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.contrller;

import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictItem;
import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wms.basicmanagement.model.business.IWmsLocationStereoscopicService;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopicTran;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsStoragePlaneService;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsStoragePlane;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping({"/location"})
public class WmsLocationStereoscopicController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WmsLocationStereoscopicController.class);
    @Autowired
    private IWmsLocationStereoscopicService wmsLocationStereoscopicService;
    @Autowired
    private IWmsStoragePlaneService wmsStoragePlaneService;
    @Autowired
    private IUserBusiness userBusiness;

    public WmsLocationStereoscopicController() {
    }

    @PostMapping({"c"})
    public ResponseResult create(WmsLocationStereoscopic wmsLocationStereoscopic) {
        wmsLocationStereoscopic.setActiveFlag("1");
        wmsLocationStereoscopic.setLocationId(CommonUtils.getUUID());
        wmsLocationStereoscopic.setCreateBy(this.userBusiness.getCurrentUser().getNickname());
        wmsLocationStereoscopic.setGmtCreate(new Date());
        wmsLocationStereoscopic.setUseStatus("0");
        Resp resp = this.wmsLocationStereoscopicService.create(wmsLocationStereoscopic);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"delete"})
    public ResponseResult delete(WmsLocationStereoscopic wmsLocationStereoscopic) {
        Resp resp = this.wmsLocationStereoscopicService.delete(wmsLocationStereoscopic);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"list"})
    public ResponseResult list(@RequestParam(name = "currentPage",defaultValue = "1") int page, @RequestParam(name = "numberOnePage",defaultValue = "10") int rows, WmsLocationStereoscopic wmsLocationStereoscopic) {
        if (wmsLocationStereoscopic.getOrderBy() == null) {
            wmsLocationStereoscopic.setOrderBy("gmt_create desc");
        }

        Pager<WmsLocationStereoscopic> resp = this.wmsLocationStereoscopicService.findListByCondition(page, rows, wmsLocationStereoscopic);
        if (resp.getRecords().size() > 0) {
            Iterator var5 = resp.getRecords().iterator();

            while(var5.hasNext()) {
                WmsLocationStereoscopic location = (WmsLocationStereoscopic)var5.next();
                if (location.getUseStatus() != null) {
                    if (location.getUseStatus().equals("0")) {
                        location.setUseStatus("可用");
                    } else if (location.getUseStatus().equals("1")) {
                        location.setUseStatus("入库中");
                    } else if (location.getUseStatus().equals("2")) {
                        location.setUseStatus("出库中");
                    } else if (location.getUseStatus().equals("3")) {
                        location.setUseStatus("占用");
                    } else if (location.getUseStatus().equals("4")) {
                        location.setUseStatus("异常");
                    }
                }

                if (location.getAllowMix() != null) {
                    if (location.getAllowMix().equals("1")) {
                        location.setAllowMix("允许");
                    } else if (location.getAllowMix().equals("0")) {
                        location.setAllowMix("不允许");
                    }
                }
            }
        }

        Map<String, Object> dateMap = new HashMap();
        dateMap.put("infoList", resp.getRecords());
        dateMap.put("totalNumber", resp.getTotalCount());
        ResponseResult result = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, dateMap);
        return result;
    }

    @GetMapping({"r/{id}"})
    public ResponseResult queryById(@PathVariable String id) {
        WmsLocationStereoscopic wmsLocationStereoscopic = this.wmsLocationStereoscopicService.findById(id);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, wmsLocationStereoscopic);
    }

    @PostMapping({"u"})
    public ResponseResult update(WmsLocationStereoscopic wmsLocationStereoscopic) {
        wmsLocationStereoscopic.setLastModifiedBy(this.userBusiness.getCurrentUser().getNickname());
        wmsLocationStereoscopic.setGmtModified(new Date());
        Resp resp = this.wmsLocationStereoscopicService.update(wmsLocationStereoscopic);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @GetMapping({"getlocationCodeAll"})
    public ResponseResult getlocationCodeAll() {
        List<BaseDictItem> baseDictItemList = this.wmsLocationStereoscopicService.getlocationCodeAll();
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, baseDictItemList);
    }

    @GetMapping({"getUseAbleAmount"})
    public ResponseResult getUseAbleAmount(WmsLocationStereoscopic condition) {
        Integer useAbleAmount = 0;
        WmsLocationStereoscopic wmsLocationStereoscopic = this.wmsLocationStereoscopicService.getUseAbleAmount(condition);
        if (wmsLocationStereoscopic != null && wmsLocationStereoscopic.getUseAbleAmount() != null) {
            useAbleAmount = wmsLocationStereoscopic.getUseAbleAmount();
        }

        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, useAbleAmount);
    }
    /**
     *功能描述: 获取商品的可用重量
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @GetMapping({"getHBUseAbleAmount"})
    public ResponseResult getHBUseAbleAmount(WmsLocationStereoscopic condition) {
        Integer useAbleAmount = 0;
        WmsLocationStereoscopic wmsLocationStereoscopic = this.wmsLocationStereoscopicService.getHBUseAbleAmount(condition);
        if (wmsLocationStereoscopic != null && wmsLocationStereoscopic.getUseAbleAmount() != null) {
            useAbleAmount = wmsLocationStereoscopic.getUseAbleAmount();
        }

        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, useAbleAmount);
    }

    @GetMapping({"getLocationCodeList"})
    public ResponseResult getLocationCodeList(WmsLocationStereoscopic condition) {
        WmsLocationStereoscopic wmsLocationStereoscopic = new WmsLocationStereoscopic();
        wmsLocationStereoscopic.setActiveFlag("1");
        wmsLocationStereoscopic.setUseStatus(condition.getUseStatus());
        List<WmsLocationStereoscopic> list = this.wmsLocationStereoscopicService.queryByAny(wmsLocationStereoscopic);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, list);
    }

    @GetMapping({"getLocationCodeList/all"})
    public ResponseResult getLocationCodeListAll(WmsLocationStereoscopic condition) {
        WmsLocationStereoscopic wmsLocationStereoscopic = new WmsLocationStereoscopic();
        wmsLocationStereoscopic.setActiveFlag("1");
        wmsLocationStereoscopic.setUseStatus(condition.getUseStatus());
        wmsLocationStereoscopic.setAreaCode(condition.getAreaCode());
        List<WmsLocationStereoscopic> stereoscopicList = this.wmsLocationStereoscopicService.queryByAny(wmsLocationStereoscopic);
        if (stereoscopicList != null && !stereoscopicList.isEmpty()) {
            return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, stereoscopicList);
        } else {
            List<WmsStoragePlane> planeList = this.wmsStoragePlaneService.findAllLocation((WmsStoragePlane)null);
            return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, planeList);
        }
    }
    /**
     *功能描述: 库位批量上传
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @PostMapping({"upload/location"})
    public ResponseResult uploadLocation(@RequestParam("upfile") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return new ResponseResult(RESULT.FAILED.code, "文件不存在", (Object)null);
        } else {
            ArrayList rtMsgList = new ArrayList();

            Object book;
            try {
                book = new XSSFWorkbook(file.getInputStream());
            } catch (Exception var25) {
                var25.printStackTrace();

                try {
                    book = new HSSFWorkbook(file.getInputStream());
                } catch (Exception var24) {
                    var24.printStackTrace();
                    return new ResponseResult(RESULT.FAILED.code, "上传文件格式错误,请重新下载模板！", (Object)null);
                }
            }

            Date now = new Date();
            String loginName = this.userBusiness.getCurrentUser().getNickname();
            Sheet sheet = ((Workbook)book).getSheetAt(0);
            HashSet<String> parCodeSet = new HashSet();
            System.out.println(sheet.getLastRowNum());
            if (sheet.getLastRowNum() < 1) {
                return new ResponseResult(RESULT.FAILED.code, "请填写上传文件数据", (Object)null);
            } else {
                sheet.getRow(1).getCell(0).setCellType(1);
                if (sheet.getRow(1).getCell(0) == null) {
                    String var10000 = "";
                } else {
                    sheet.getRow(1).getCell(0).getStringCellValue().replaceAll(" ", "");
                }

                List<WmsLocationStereoscopic> insertList = new ArrayList();
                Iterator var10 = sheet.iterator();

                while(true) {
                    while(true) {
                        Row row;
                        do {
                            if (!var10.hasNext()) {
                                if (rtMsgList.size() > 0) {
                                    return new ResponseResult(RESULT.FAILED.code, "EXCEL明细错误！", rtMsgList);
                                }

                                if (!insertList.isEmpty()) {
                                    List<WmsLocationStereoscopic> list = this.wmsLocationStereoscopicService.queryBatch(insertList);
                                    if (list != null && !list.isEmpty()) {
                                        return new ResponseResult(RESULT.FAILED.code, "EXCEL中库位编码存在已经插入系统记录！", (Object)null);
                                    }

                                    this.wmsLocationStereoscopicService.batchInsertLocation(insertList);
                                    return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, (Object)null);
                                }

                                return new ResponseResult(RESULT.FAILED.code, "无可用信息导入！", (Object)null);
                            }

                            row = (Row)var10.next();
                        } while(row.getRowNum() == 0);

                        if (row.getCell(0) != null && !row.getCell(0).equals("")) {
                            row.getCell(0).setCellType(1);
                        }

                        if (row.getCell(1) != null && !row.getCell(1).equals("")) {
                            row.getCell(1).setCellType(1);
                        }

                        if (row.getCell(2) != null && !row.getCell(2).equals("")) {
                            row.getCell(2).setCellType(1);
                        }

                        if (row.getCell(3) != null && !row.getCell(3).equals("")) {
                            row.getCell(3).setCellType(1);
                        }

                        if (row.getCell(4) != null && !row.getCell(4).equals("")) {
                            row.getCell(4).setCellType(1);
                        }

                        if (row.getCell(5) != null && !row.getCell(5).equals("")) {
                            row.getCell(5).setCellType(1);
                        }

                        if (row.getCell(6) != null && !row.getCell(6).equals("")) {
                            row.getCell(6).setCellType(1);
                        }

                        String warehouseCode = row.getCell(0) == null ? "" : row.getCell(0).getStringCellValue().replaceAll(" ", "");
                        String areaCode = row.getCell(1) == null ? "" : row.getCell(1).getStringCellValue().replaceAll(" ", "");
                        String locationCode = row.getCell(2) == null ? "" : row.getCell(2).getStringCellValue().replaceAll(" ", "");
                        String locationDesc = row.getCell(3) == null ? "" : row.getCell(3).getStringCellValue().replaceAll(" ", "");
                        String locationAttr = row.getCell(4) == null ? "" : row.getCell(3).getStringCellValue().replaceAll(" ", "");
                        String inSeq = row.getCell(5) == null ? "" : row.getCell(3).getStringCellValue().replaceAll(" ", "");
                        String outSeq = row.getCell(6) == null ? "" : row.getCell(3).getStringCellValue().replaceAll(" ", "");
                        Integer rowNum = row.getRowNum();
                        if ("".equals(warehouseCode)) {
                            rtMsgList.add("第" + rowNum + "行仓库编号为空！");
                        } else if ("".equals(areaCode)) {
                            rtMsgList.add("第" + rowNum + "行库区编号为空！");
                        } else if ("".equals(locationCode)) {
                            rtMsgList.add("第" + rowNum + "行库位编号为空！");
                        } else if (parCodeSet.contains(locationCode)) {
                            rtMsgList.add("第" + rowNum + "行库位重复！");
                        } else {
                            parCodeSet.add(locationCode);
                            /*if (locationCode.length() != 5) {
                                rtMsgList.add("第" + rowNum + "行库位编号长度不为5位！");
                            }*/
                            if (locationCode.length() != 6) {
                                rtMsgList.add("第" + rowNum + "行库位编号长度不为10位！");
                            } else if (!locationCode.matches("[0-9]+")) {
                                rtMsgList.add("第" + rowNum + "行库位编号不为纯数字！");
                            } else {
                               /* String depth = locationCode.substring(4, 5);
                                if (!"1".equals(depth) && !"2".equals(depth)) {
                                    rtMsgList.add("第" + rowNum + "行库位编号末位不为1或2！");
                                } else {

                                */
                                    WmsLocationStereoscopic locationStereoscopic = new WmsLocationStereoscopic();
                                    locationStereoscopic.setLocationId(CommonUtils.getUUID());
                                    locationStereoscopic.setWarehouseCode(warehouseCode);
                                    locationStereoscopic.setAreaCode(areaCode);
                                    locationStereoscopic.setLocationCode(locationCode);
                                    locationStereoscopic.setLocationDesc(locationDesc);
                                    locationStereoscopic.setLocationAttr(locationAttr);
                                    if ("".equals(inSeq)) {
                                        locationStereoscopic.setInSeq(0);
                                    } else {
                                        locationStereoscopic.setInSeq(Integer.parseInt(inSeq));
                                    }

                                    if ("".equals(outSeq)) {
                                        locationStereoscopic.setOutSeq(0);
                                    } else {
                                        locationStereoscopic.setOutSeq(Integer.parseInt(outSeq));
                                    }

                                    locationStereoscopic.setUseStatus("0");
                                    locationStereoscopic.setAllowMix("0");
                                   // int shelvesNumber = Integer.parseInt(locationCode.substring(0, 1));
                                   // locationStereoscopic.setShelvesNumber(shelvesNumber);
                                    //层数
                                    int floorNumber = Integer.parseInt(locationCode.substring(4,locationCode.length()));
                                    locationStereoscopic.setFloorNumber(floorNumber);
                                //  int layerNumber = Integer.parseInt(locationCode.substring(3, 4));
                                    //行数
                                    int layerNumber = Integer.parseInt(locationCode.substring(0,2));
                                    //列数
                                    int columnNumber = Integer.parseInt(locationCode.substring(2,4));
                                //  int columnNumber = Integer.parseInt(locationCode.substring(1, 3));

                                    locationStereoscopic.setColumnNumber(columnNumber);

                                    locationStereoscopic.setLayerNumber(layerNumber);
                                    locationStereoscopic.setGmtCreate(now);
                                    locationStereoscopic.setCreateBy(loginName);
                                    locationStereoscopic.setActiveFlag("1");
                                    insertList.add(locationStereoscopic);
                               // }
                            }
                        }
                    }
                }
            }
        }
    }

    @GetMapping({"notEmptyLocationList"})
    public ResponseResult getNotEmptyLocationList() {
        List<WmsLocationStereoscopic> list = this.wmsLocationStereoscopicService.queryNotEmptyLocationList();
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, list);
    }

    /**
     * 读取库位状态信息
     * @return
     */
    @PostMapping({"allLocationList"})
    public ResponseResult getAllLocationList() {
        List<WmsLocationStereoscopic> list = this.wmsLocationStereoscopicService.getAllLocationList();
        List<WmsLocationStereoscopicTran> tranList = new ArrayList();
        Iterator var3 = list.iterator();

        while(var3.hasNext()) {
            WmsLocationStereoscopic tmp = (WmsLocationStereoscopic)var3.next();
            WmsLocationStereoscopicTran ob = new WmsLocationStereoscopicTran();

            try {
                BeanUtils.copyProperties(tmp, ob);
                tranList.add(ob);
            } catch (Exception var7) {
            }
        }
//        log.info(tranList.toString());
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, tranList);
    }

    /**
     * 读取单个货位的详细信息
     * @param locationCode
     * @return
     */
    @GetMapping({"detail/{locationCode}"})
    public ResponseResult queryByLocationCode(@PathVariable String locationCode) {
        log.info("===============读取单个货位的详细信息-货位编码："+locationCode);
        WmsLocationStereoscopic wmsLocationStereoscopic = this.wmsLocationStereoscopicService.findByLocationCode(locationCode);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, wmsLocationStereoscopic);
    }

    @PostMapping({"forbidden"})
    public ResponseResult forbidden(String ids) {
        Resp resp = this.wmsLocationStereoscopicService.forbiddenLocation(ids);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"openFlag"})
    public ResponseResult openFlag(String ids) {
        Resp resp = this.wmsLocationStereoscopicService.openFlagLocation(ids);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }
}
