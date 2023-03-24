//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.contrller;

import com.penghaisoft.WmsApplication;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wms.basicmanagement.model.business.IWmsLocationStereoscopicService;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {WmsApplication.class}
)
public class WmsLocationStereoscopicInitController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WmsLocationStereoscopicInitController.class);
    @Autowired
    private IWmsLocationStereoscopicService wmsLocationStereoscopicService;

    public WmsLocationStereoscopicInitController() {
    }

    public void init() {
        int floorMax = 4;
        int rowMax = 30;
        int colMax = 28;
        Date now = new Date();
        List<WmsLocationStereoscopic> insertList = new ArrayList();

        for(int floor = 1; floor <= floorMax; ++floor) {
            for(int row = 1; row <= rowMax; ++row) {
                for(int col = 1; col <= colMax; ++col) {
                    if ((1 > row || row > 2 || 4 > col || col > 10) && (row != 3 || 4 > col || col > 7) && (1 > row || row > 7 || 24 > col || col > 28) && (row != 6 && row != 15 && row != 23 || 3 > col || col > 28) && (row != 7 || 24 > col || col > 28) && (row != 14 && row != 16 && row != 30 || 25 > col || col > 28) && (row != 22 && row != 24 || 26 > col || col > 28)) {
                        WmsLocationStereoscopic insertOb = new WmsLocationStereoscopic();
                        insertOb.setLocationId(CommonUtils.getUUID());
                        insertOb.setWarehouseCode("WH-QXF01");
                        insertOb.setAreaCode("L-QXF01");
                        insertOb.setUseStatus("0");
                        insertOb.setFloorNumber(floor);
                        if (col <= 2) {
                            insertOb.setShelvesNumber(1);
                        } else if (3 <= col && col <= 13) {
                            insertOb.setShelvesNumber(2);
                        } else if (14 <= col && col <= 23) {
                            insertOb.setShelvesNumber(3);
                        } else if (24 <= col && col <= 28) {
                            insertOb.setShelvesNumber(4);
                        }

                        insertOb.setLayerNumber(row);
                        insertOb.setColumnNumber(col);
                        insertOb.setCreateBy("init");
                        insertOb.setGmtCreate(now);
                        insertOb.setActiveFlag("1");
                        String rowS = row < 10 ? "0" + String.valueOf(row) : String.valueOf(row);
                        String colS = col < 10 ? "0" + String.valueOf(col) : String.valueOf(col);
                        String locationCode = floor + rowS + colS;
                        insertOb.setLocationCode(locationCode);
                        insertOb.setLocationDesc(locationCode);
                        insertList.add(insertOb);
                    }
                }
            }
        }

        this.wmsLocationStereoscopicService.batchInsertLocation(insertList);
    }

    @Test
    public void initNew() {
        int floorMax = 4;
        int rowMax = 32;
        int colMax = 26;
        Date now = new Date();
        List<WmsLocationStereoscopic> insertList = new ArrayList();

        for(int floor = 1; floor <= floorMax; ++floor) {
            for(int row = 1; row <= rowMax; ++row) {
                for(int col = 1; col <= colMax; ++col) {
                    WmsLocationStereoscopic insertOb = new WmsLocationStereoscopic();
                    insertOb.setLocationId(CommonUtils.getUUID());
                    insertOb.setWarehouseCode("WH-QXF01");
                    insertOb.setAreaCode("L-QXF01");
                    insertOb.setUseStatus("0");
                    insertOb.setActiveFlag("1");
                    if (row == 1 && 4 <= col && col <= 11) {
                        insertOb.setActiveFlag("0");
                    }

                    if (row == 2 && 3 <= col && col <= 12) {
                        insertOb.setActiveFlag("0");
                    }

                    if (row == 3 && 4 <= col && col <= 5) {
                        insertOb.setActiveFlag("0");
                    }

                    if (1 > row || row > 7 || 22 > col || col > 26) {
                        if ((row == 4 || row == 8 || row == 9 || row == 12 || row == 13 || row == 17 || row == 21 || row == 25 || row == 29) && 9 == col) {
                            insertOb.setActiveFlag("0");
                        }

                        if (row == 8 && 25 <= col && col <= 26) {
                            insertOb.setActiveFlag("0");
                        }

                        if ((row == 14 || row == 16 || row == 30 || row == 32) && 23 <= col && col <= 26) {
                            insertOb.setActiveFlag("0");
                        }

                        if ((row == 15 || row == 31) && 22 <= col && col <= 26) {
                            insertOb.setActiveFlag("0");
                        }

                        if ((row == 22 || row == 23 || row == 24) && 25 <= col && col <= 26) {
                            insertOb.setActiveFlag("0");
                        }

                        if (row != 30 && row != 31 && row != 32 || 1 > col || col > 20) {
                            if ((row == 6 || row == 15 || row == 23) && 3 <= col && col <= 26) {
                                insertOb.setActiveFlag("0");
                            }

                            insertOb.setFloorNumber(floor);
                            if (col <= 2) {
                                insertOb.setShelvesNumber(1);
                            } else if (3 <= col && col <= 8) {
                                insertOb.setShelvesNumber(20);
                            } else if (9 <= col && col <= 12) {
                                insertOb.setShelvesNumber(21);
                            } else if (13 <= col && col <= 21) {
                                insertOb.setShelvesNumber(3);
                            } else if (22 <= col && col <= 26) {
                                insertOb.setShelvesNumber(4);
                            }

                            if (row == 3 && 6 <= col && col <= 8) {
                                insertOb.setShelvesNumber(21);
                            }

                            insertOb.setLayerNumber(row);
                            insertOb.setColumnNumber(col);
                            insertOb.setCreateBy("init");
                            insertOb.setGmtCreate(now);
                            String rowS = row < 10 ? "0" + String.valueOf(row) : String.valueOf(row);
                            String colS = col < 10 ? "0" + String.valueOf(col) : String.valueOf(col);
                            String locationCode = floor + rowS + colS;
                            insertOb.setLocationCode(locationCode);
                            insertOb.setLocationDesc(locationCode);
                            insertList.add(insertOb);
                        }
                    }
                }
            }
        }

        this.wmsLocationStereoscopicService.batchInsertLocation(insertList);
    }
}
