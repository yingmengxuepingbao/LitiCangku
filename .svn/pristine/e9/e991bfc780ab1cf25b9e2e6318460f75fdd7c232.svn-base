//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.expose;

import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictItem;
import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.constant.Constant.TaskType;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.wms.basicmanagement.model.business.IWmsWarehouseAreaService;
import com.penghaisoft.wms.common.service.IWmsCommonService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/expose"})
public class ExposeTestController {
    private static final Logger log = LoggerFactory.getLogger(ExposeTestController.class);
    @Autowired
    private IWmsCommonService wmsCommonService;
    @Autowired
    private IWmsWarehouseAreaService wmsWarehouseAreaService;

    public ExposeTestController() {
    }

    @GetMapping({"taskids/{size}"})
    public ResponseResult getTaskIdsTest(@PathVariable("size") int size) {
        long[] taskIds = this.wmsCommonService.getTaskIds(TaskType.PRODUCT_IN, size);

        for(int i = 0; i < taskIds.length; ++i) {
            System.out.println(taskIds[i]);
        }

        ResponseResult result = new ResponseResult("1", "", (Object)null);
        return result;
    }

    @GetMapping({"orderno/{type}"})
    public ResponseResult getTaskIdsTest(@PathVariable("type") String type) {
        String orderNo = this.wmsCommonService.getOrderNoByType(type);
        System.out.println(orderNo);
        ResponseResult result = new ResponseResult("1", "", (Object)null);
        return result;
    }

    @CrossOrigin
    @GetMapping({"getAreaCodeAll"})
    public ResponseResult getAreaCodeAll() {
        List<BaseDictItem> baseDictItemList = this.wmsWarehouseAreaService.getAreaCodeAll();
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, baseDictItemList);
    }

    private static void genLocationStereoscopicSql() {
        String sql1 = "INSERT INTO wms_location_stereoscopic (`location_id`,`location_code`,`shelves_number`,`column_number`,`layer_number` ) VALUES";
        String deep = "1";
        int id = 100;

        for(int shelfId = 1; shelfId <= 2; ++shelfId) {
            for(int row = 1; row <= 5; ++row) {
                for(int j = 1; j <= 23; ++j) {
                    String colNum = "";
                    if (j < 10) {
                        colNum = "0" + j;
                    } else {
                        colNum = String.valueOf(j);
                    }

                    String locationCode = shelfId + colNum + row + deep;
                    String insertSql = sql1 + "('" + id + "','" + locationCode + "'," + shelfId + "," + j + "," + row + ");";
                    ++id;
                    System.out.println(insertSql);
                }
            }
        }

    }

    private static void genPalletSql() {
        String sql1 = "INSERT INTO `bj_wms`.`wms_pallet`(`pallet_id`, `pallet_code`) VALUES";

        for(int i = 1; i <= 15; ++i) {
            String palletCode = "AA";
            if (i < 10) {
                palletCode = palletCode + "000" + i;
            } else if (i >= 10 && i < 100) {
                palletCode = palletCode + "00" + i;
            }

            String insertSql = sql1 + "('" + i + "', '" + palletCode + "');";
            System.out.println(insertSql);
        }

    }

    private static void genWcsLocationSql() {
        String sql1 = "INSERT INTO `wcs_location_real` (`location_id`,`row`,`col`,`shelf_id`,`stacker_id`,`direction`) VALUES";
        String deep = "1";
        int id = 100;

        for(int shelfId = 1; shelfId <= 2; ++shelfId) {
            for(int row = 1; row <= 5; ++row) {
                for(int j = 1; j <= 23; ++j) {
                    String colNum = "";
                    if (j < 10) {
                        (new StringBuilder()).append("0").append(j).toString();
                    } else {
                        colNum = String.valueOf(j);
                    }

                    int locationCode = shelfId * 10000 + j * 100 + row * 10 + 1;
                    String insertSql = sql1 + "(" + trans(locationCode) + "," + trans(row) + "," + trans(j) + "," + trans(shelfId) + "," + 1 + "," + (shelfId == 1 ? 1 : 0) + ");";
                    ++id;
                    System.out.println(insertSql);
                }
            }
        }

    }

    private static String trans(Object obj) {
        if (obj instanceof String) {
            return "'" + obj + "'";
        } else {
            return obj instanceof Integer ? String.valueOf(obj) : obj.toString();
        }
    }

    public static void main(String[] args) {
        genWcsLocationSql();
    }
}
