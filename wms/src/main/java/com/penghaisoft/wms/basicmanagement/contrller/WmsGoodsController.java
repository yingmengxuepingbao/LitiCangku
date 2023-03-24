//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.contrller;

import com.penghaisoft.framework.basicdatamanagement.model.business.IBaseDictItemService;
import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictItem;
import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wms.basicmanagement.model.business.IWmsGoodsService;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsGoods;

import java.io.IOException;
import java.util.*;

import com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping({"/goods"})
public class WmsGoodsController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WmsGoodsController.class);
    @Autowired
    private IWmsGoodsService wmsGoodsService;
    @Autowired
    private IUserBusiness userBusiness;
    @Autowired
    private IBaseDictItemService baseDictItemService;

    public WmsGoodsController() {
    }

    @PostMapping({"c"})
    public ResponseResult create(WmsGoods wmsGoods) {
        wmsGoods.setActiveFlag("1");
        wmsGoods.setGoodsId(CommonUtils.getUUID());
        wmsGoods.setCreateBy(this.userBusiness.getCurrentUser().getNickname());
        wmsGoods.setGmtCreate(new Date());
        Resp resp = this.wmsGoodsService.create(wmsGoods);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"delete"})
    public ResponseResult delete(WmsGoods wmsGoods) {
        wmsGoods.setLastModifiedBy(this.userBusiness.getCurrentUser().getNickname());
        wmsGoods.setGmtModified(new Date());
        Resp resp = this.wmsGoodsService.delete(wmsGoods);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"list"})
    public ResponseResult list(@RequestParam(name = "currentPage",defaultValue = "1") int page, @RequestParam(name = "numberOnePage",defaultValue = "10") int rows, WmsGoods wmsGoods) {
        Pager<WmsGoods> resp = this.wmsGoodsService.findListByCondition(page, rows, wmsGoods);
        List<BaseDictItem> baseDictItemList = this.baseDictItemService.getDictTypeByCode("goods_type");
        if (resp.getRecords().size() > 0) {
            Iterator var6 = resp.getRecords().iterator();

            while(var6.hasNext()) {
                WmsGoods wmsGood = (WmsGoods)var6.next();
                Iterator var8 = baseDictItemList.iterator();

                while(var8.hasNext()) {
                    BaseDictItem baseDictItem = (BaseDictItem)var8.next();
                    if (baseDictItem.getDicItemCode().equals(wmsGood.getGoodsAttr())) {
                        wmsGood.setGoodsAttr(baseDictItem.getDicItemName());
                    }
                }

                if (wmsGood.getGoodsType() != null) {
                    if (wmsGood.getGoodsType().equals("1")) {
                        wmsGood.setGoodsType("成品");
                    } else if (wmsGood.getGoodsType().equals("0")) {
                        wmsGood.setGoodsType("包材");
                    } else if (wmsGood.getGoodsType().equals("3")) {
                        wmsGood.setGoodsType("托盘");
                    }
                }

                if (wmsGood.getHasBoxCode() != null) {
                    if (wmsGood.getHasBoxCode().equals("1")) {
                        wmsGood.setHasBoxCode("有");
                    } else if (wmsGood.getHasBoxCode().equals("0")) {
                        wmsGood.setHasBoxCode("无");
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
        WmsGoods wmsGoods = this.wmsGoodsService.findById(id);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, wmsGoods);
    }

    @GetMapping({"queryByCode/{goodsCode}"})
    public ResponseResult queryByCode(@PathVariable String goodsCode) {
        WmsGoods wmsGoods = this.wmsGoodsService.queryByCode(goodsCode);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, wmsGoods);
    }

    @PostMapping({"u"})
    public ResponseResult update(WmsGoods wmsGoods) {
        wmsGoods.setLastModifiedBy(this.userBusiness.getCurrentUser().getNickname());
        wmsGoods.setGmtModified(new Date());
        Resp resp = this.wmsGoodsService.update(wmsGoods);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @GetMapping({"getGoodsCodeAll"})
    public ResponseResult getGoodsCodeAll() {
        List<BaseDictItem> baseDictItemList = this.wmsGoodsService.getGoodsCodeAll();
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, baseDictItemList);
    }
    @GetMapping({"getGoodsCodeAll/batchNo"})
    public ResponseResult getGoodsCodeAllBatchNo() {
        List<WmsGoods> list = this.wmsGoodsService.getGoodsCodeAllBatchNo();
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, list);
    }
    /**
     *功能描述: 获取基础商品列表
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @GetMapping({"getHBGoodsCodeAll/batchNo"})
    public ResponseResult getHBGoodsCodeAllBatchNo() {
        List<WmsGoods> list = this.wmsGoodsService.getGoodsCodeAllBatchNo();
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, list);
    }
    /**
     *功能描述:  根据类型 获取基础商品数据
     * @date 2023/2/4
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @GetMapping({"getHBGoodsCodeAll/batchNoByType"})
    public ResponseResult getAllBatchNoByType( String goodType) {
        List<WmsGoods> list = this.wmsGoodsService.getAllBatchNoByType(goodType);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, list);
    }
    /**
     *功能描述:  根据商品编码，获取批次号
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @GetMapping({"getGoodsCodeAll/getHBBatchNo"})
    public ResponseResult getHBBatchNo(WmsGoods wmsgoods) {
        List<WmsGoods> list = this.wmsGoodsService.getHBBatchNo(wmsgoods);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, list);
    }

    /**
     *功能描述: 商品批量上传
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

                List<WmsGoods> insertList = new ArrayList();
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
                                    List<WmsGoods> list = this.wmsGoodsService.queryBatch(insertList);
                                    if (list != null && !list.isEmpty()) {
                                        return new ResponseResult(RESULT.FAILED.code, "EXCEL中商品编码存在已经插入系统记录！", (Object)null);
                                    }

                                    this.wmsGoodsService.batchInsertLocation(insertList);
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

                        String goodsCode = row.getCell(0) == null ? "" : row.getCell(0).getStringCellValue().replaceAll(" ", "");
                        String goodsName = row.getCell(1) == null ? "" : row.getCell(1).getStringCellValue().replaceAll(" ", "");
                        String goodsDesc = row.getCell(2) == null ? "" : row.getCell(2).getStringCellValue().replaceAll(" ", "");
                        String grossWeight = row.getCell(3) == null ? "0" : row.getCell(3).getStringCellValue().replaceAll(" ", "");
                        String unit = row.getCell(4) == null ? "" : row.getCell(4).getStringCellValue().replaceAll(" ", "");
                        Integer rowNum = row.getRowNum();
                        if ("".equals(goodsCode)) {
                            rtMsgList.add("第" + rowNum + "行商品编码为空！");
                        } else {
                            WmsGoods wmsGoods = new WmsGoods();
                            wmsGoods.setGoodsId(CommonUtils.getUUID());
                            wmsGoods.setGoodsDesc(goodsDesc);
                            wmsGoods.setGoodsName(goodsName);
                            wmsGoods.setGoodsCode(goodsCode);
                            wmsGoods.setUnit(unit);
                            wmsGoods.setGrossWeight(Double.valueOf(grossWeight).intValue());
                            wmsGoods.setGmtCreate(new Date());
                            wmsGoods.setActiveFlag("1");
                            insertList.add(wmsGoods);
                        }
                    }
                }
            }
        }
    }
}