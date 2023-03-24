//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.expose;

import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.ExcelExpUtil;
import com.penghaisoft.wms.storagemanagement.model.business.*;
import com.penghaisoft.wms.storagemanagement.model.entity.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/excel"})
public class ExcelExportController {
    private static final Logger log = LoggerFactory.getLogger(ExcelExportController.class);
    @Autowired
    private IWmsInTemporaryService wmsInTemporaryService;
    @Autowired
    private IWmsTaskExecutionLogService wmsTaskExecutionLogService;
    @Autowired
    private IWmsPalletService wmsPalletService;
    @Autowired
    private IWmsStoragePlaneService wmsStoragePlaneService;
    @Autowired
    private IWmsOutTemporaryService wmsOutTemporaryService;

    public ExcelExportController() {
    }

    @GetMapping({"locationstereoscopic/template"})
    public void locationStereoscopicTemplate(HttpServletRequest request, HttpServletResponse response) {
        List<Object> dataList = new ArrayList();
        List<Map<String, String>> columnList = ExcelExpUtil.genColumnList(new String[]{"所属仓库", "所属库区", "库位编码", "库位描述", "库位属性", "上架次序", "取货次序"}, new String[]{"warehouseCode", "areaCode", "locationCode", "locationDesc", "locationAttr", "inSeq", "outSeq"});
        this.writeData(dataList, columnList, "立库库位", response);
    }
    /**
     *功能描述: 商品信息
     * @params
     * @return void
     */
    @GetMapping({"goods/template"})
    public void goodsTemplate(HttpServletRequest request, HttpServletResponse response) {
        List<Object> dataList = new ArrayList();
        List<Map<String, String>> columnList = ExcelExpUtil.genColumnList(new String[]{"物料编码","物料名称", "物料描述", "毛重", "单位"}, new String[]{"goodsCode","goodsName", "goodsDesc", "grossWeight", "unit"});
        this.writeData(dataList, columnList, "商品信息", response);
    }

    @GetMapping({"intemporary"})
    public void exportInTemporaryExcel(String goodsName, String goodsCode, HttpServletResponse response) {
        WmsInTemporary wmsInTemporary = new WmsInTemporary();
        wmsInTemporary.setGoodsCode(goodsCode);
        wmsInTemporary.setGoodsName(goodsName);
        List<WmsInTemporary> dataList = this.wmsInTemporaryService.findExportListByCondition(wmsInTemporary);
        if (dataList.size() > 0) {
            List<Map<String, String>> columnList = ExcelExpUtil.genColumnList(new String[]{"商品编码", "商品名称", "数量"}, new String[]{"goodsCode", "goodsName", "amount"});
            this.writeData(dataList, columnList, "收货暂存区库存", response);
        }

    }

    @GetMapping({"unshelves"})
    public void exportUnshelvesExcel(String palletCode, String goodsCode, String batchNo, HttpServletResponse response) {
        WmsPallet wmsPallet = new WmsPallet();
        wmsPallet.setActiveFlag("1");
        wmsPallet.setLocationCodeIsNull("null");
        wmsPallet.setGoodsCodeIsNotNull("notnull");
        List<WmsPallet> dataList = this.wmsPalletService.findExportUnshelvesListByCondition(wmsPallet);
        if (dataList.size() > 0) {
            List<Map<String, String>> columnList = ExcelExpUtil.genColumnList(new String[]{"托盘编码", "商品编码", "商品描述", "批次", "数量", "是否有箱码", "箱码"}, new String[]{"palletCode", "goodsCode", "goodsName", "batchNo", "amount", "hasBoxCode", "boxBarcodeList"});
            this.writeData(dataList, columnList, "收货未上架库存", response);
        }

    }

    @GetMapping({"plane"})
    public void exportPlaneStorageExcel(String locationCode, String goodsCode, String batchNo, HttpServletResponse response) {
        WmsStoragePlane wmsStoragePlane = new WmsStoragePlane();
        wmsStoragePlane.setLocationCode(locationCode);
        wmsStoragePlane.setGoodsCode(goodsCode);
        wmsStoragePlane.setBatchNo(batchNo);
        wmsStoragePlane.setActiveFlag("1");
        List<WmsStoragePlane> dataList = this.wmsStoragePlaneService.findExportListByCondition(wmsStoragePlane);
        if (dataList.size() > 0) {
            List<Map<String, String>> columnList = ExcelExpUtil.genColumnList(new String[]{"所属仓库", "所属库区", "库位编码", "库位描述", "商品编码", "商品描述", "批次", "可用数量", "冻结数量"}, new String[]{"warehouseCode", "areaCode", "locationCode", "locationDesc", "goodsCode", "goodsName", "batchNo", "availableAmount", "lockAmount"});
            this.writeData(dataList, columnList, "平库库存", response);
        }

    }

    @GetMapping({"stereoscopic"})
    public void exportStereoscopicStorageExcel(String goodsCode, String batchNo, HttpServletResponse response) {
        WmsPallet wmsPallet = new WmsPallet();
        wmsPallet.setGoodsCode(goodsCode);
        wmsPallet.setBatchNo(batchNo);
        wmsPallet.setActiveFlag("1");
        wmsPallet.setLockByIsNull("notnull");
        List<WmsPallet> dataList = this.wmsPalletService.findExportStereoscopicListHz(wmsPallet);
        if (dataList.size() > 0) {
            List<Map<String, String>> columnList = ExcelExpUtil.genColumnList(new String[]{"商品编码", "商品描述", "批次号", "托盘数", "箱数"}, new String[]{"goodsCode", "goodsName", "batchNo", "number", "amount"});
            this.writeData(dataList, columnList, "立库库存", response);
        }

    }

    @GetMapping({"outtemporary"})
    public void exportOutTemporaryExcel(String goodsName, String goodsCode, HttpServletResponse response) {
        WmsOutTemporary outTemporary = new WmsOutTemporary();
        outTemporary.setGoodsCode(goodsCode);
        outTemporary.setActiveFlag("1");
        List<WmsOutTemporary> dataList = this.wmsOutTemporaryService.findExportListByCondition(outTemporary);
        if (dataList.size() > 0) {
            List<Map<String, String>> columnList = ExcelExpUtil.genColumnList(new String[]{"商品编码", "商品名称", "数量"}, new String[]{"goodsCode", "goodsName", "amount"});
            this.writeData(dataList, columnList, "发货暂存区库存", response);
        }

    }
    /**
     *功能描述: 进出周转汇总
     * @params
     * @return void
     */
    @GetMapping({"turnover"})
    public void exportTurnoverExcel(String goodsCode, String batchNo,String gmtCreateMax,String gmtCreateMin, HttpServletResponse response) {
        WmsTaskExecutionLog wmsTaskExecutionLog = new WmsTaskExecutionLog();
        wmsTaskExecutionLog.setGoodsCode(goodsCode);
        wmsTaskExecutionLog.setBatchNo(batchNo);
        wmsTaskExecutionLog.setGmtCreateMax1(gmtCreateMax);
        wmsTaskExecutionLog.setGmtCreateMin1(gmtCreateMin);
        List<TurnoverDTO> dataList = this.wmsTaskExecutionLogService.shaiXuancountInAndOut(wmsTaskExecutionLog);
        if (dataList.size() > 0) {
            List<Map<String, String>> columnList = ExcelExpUtil.genColumnList(new String[]{"商品编码", "商品名称", "批次号", "入库托盘统计", "入库箱数统计", "出库托盘统计", "出库箱数统计", "返库托盘统计", "返库箱数统计"}, new String[]{"goodsCode", "goodsName", "batchNo", "inBoundCount", "inBoundBoxCount", "outBoundCount", "outBoundBoxCount", "outInBoundCount", "outInBoundBoxCount"});
            this.writeData(dataList, columnList, "进出周转汇总", response);
        }

    }


    private void writeData(List dataList, List<Map<String, String>> columnList, String fileName, HttpServletResponse response) {
        SXSSFWorkbook sw = ExcelExpUtil.generateWorkbook(dataList, columnList);
        OutputStream out = null;
        String sdfDate = CommonUtils.getCurrentYmdDate();
        fileName = fileName + "-" + sdfDate + ".xlsx";

        try {
            out = response.getOutputStream();
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
            sw.write(out);
        } catch (Exception var17) {
            var17.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException var16) {
                var16.printStackTrace();
            }

        }

    }
}
