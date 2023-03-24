//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.model.entity;

import java.util.Date;
import java.util.List;

public class WmsLocationStereoscopic {
    private List<String> locationIdList;
    private List<String> locationCodeList;
    private String deepLocationFlag = "2";
    private String shallowLocationFlag = "1";
    private String goodsCode;
    private String goodsName;
    private String batchNo;
    private Integer amount;
    private String lockBy;
    private Integer useAbleAmount;
    List<WmsMoveTask> wmsMoveTaskList;
    List<WmsOutTask> wmsOutTaskList;
    private String outAddress;
    private String stacker;
    private Integer stackerRunTaskAmount;
    private String locationId;
    private String warehouseCode;
    private String areaCode;
    private String locationCode;
    private String locationAttr;
    private String locationDesc;
    private String palletCode;
    private String palletCodeNull;
    private Integer inSeq;
    private Integer outSeq;
    private Integer floorNumber;
    private List<Integer> floorNumberList;
    private Integer shelvesNumber;
    private List<Integer> shelvesNumberList;
    private Integer layerNumber;
    private Integer columnNumber;
    private String useStatus;
    private String allowMix;
    private String userDefined1;
    private String userDefined2;
    private String userDefined3;
    private String userDefined4;
    private String userDefined5;
    private int startNumber;
    private int numberOnePage;
    private String orderBy;
    private String createBy;
    private String remark;
    private Date gmtCreate;
    private Date gmtCreateMin;
    private Date gmtCreateMax;
    private String lastModifiedBy;
    private Date gmtModified;
    private Date gmtModifiedMax;
    private Date gmtModifiedMin;
    private String activeFlag;
    private Integer zhanYongNumber;

    public WmsLocationStereoscopic() {
    }

    public List<String> getLocationIdList() {
        return this.locationIdList;
    }

    public List<String> getLocationCodeList() {
        return this.locationCodeList;
    }

    public String getDeepLocationFlag() {
        return this.deepLocationFlag;
    }

    public String getShallowLocationFlag() {
        return this.shallowLocationFlag;
    }

    public String getGoodsCode() {
        return this.goodsCode;
    }

    public String getGoodsName() {
        return this.goodsName;
    }

    public String getBatchNo() {
        return this.batchNo;
    }

    public Integer getAmount() {
        return this.amount;
    }

    public String getLockBy() {
        return this.lockBy;
    }

    public Integer getUseAbleAmount() {
        return this.useAbleAmount;
    }

    public List<WmsMoveTask> getWmsMoveTaskList() {
        return this.wmsMoveTaskList;
    }

    public List<WmsOutTask> getWmsOutTaskList() {
        return this.wmsOutTaskList;
    }

    public String getOutAddress() {
        return this.outAddress;
    }

    public String getStacker() {
        return this.stacker;
    }

    public Integer getStackerRunTaskAmount() {
        return this.stackerRunTaskAmount;
    }

    public String getLocationId() {
        return this.locationId;
    }

    public String getWarehouseCode() {
        return this.warehouseCode;
    }

    public String getAreaCode() {
        return this.areaCode;
    }

    public String getLocationCode() {
        return this.locationCode;
    }

    public String getLocationAttr() {
        return this.locationAttr;
    }

    public String getLocationDesc() {
        return this.locationDesc;
    }

    public String getPalletCode() {
        return this.palletCode;
    }

    public String getPalletCodeNull() {
        return this.palletCodeNull;
    }

    public Integer getInSeq() {
        return this.inSeq;
    }

    public Integer getOutSeq() {
        return this.outSeq;
    }

    public Integer getFloorNumber() {
        return this.floorNumber;
    }

    public List<Integer> getFloorNumberList() {
        return this.floorNumberList;
    }

    public Integer getShelvesNumber() {
        return this.shelvesNumber;
    }

    public List<Integer> getShelvesNumberList() {
        return this.shelvesNumberList;
    }

    public Integer getLayerNumber() {
        return this.layerNumber;
    }

    public Integer getColumnNumber() {
        return this.columnNumber;
    }

    public String getUseStatus() {
        return this.useStatus;
    }

    public String getAllowMix() {
        return this.allowMix;
    }

    public String getUserDefined1() {
        return this.userDefined1;
    }

    public String getUserDefined2() {
        return this.userDefined2;
    }

    public String getUserDefined3() {
        return this.userDefined3;
    }

    public String getUserDefined4() {
        return this.userDefined4;
    }

    public String getUserDefined5() {
        return this.userDefined5;
    }

    public int getStartNumber() {
        return this.startNumber;
    }

    public int getNumberOnePage() {
        return this.numberOnePage;
    }

    public String getOrderBy() {
        return this.orderBy;
    }

    public String getCreateBy() {
        return this.createBy;
    }

    public String getRemark() {
        return this.remark;
    }

    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    public Date getGmtCreateMin() {
        return this.gmtCreateMin;
    }

    public Date getGmtCreateMax() {
        return this.gmtCreateMax;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Date getGmtModified() {
        return this.gmtModified;
    }

    public Date getGmtModifiedMax() {
        return this.gmtModifiedMax;
    }

    public Date getGmtModifiedMin() {
        return this.gmtModifiedMin;
    }

    public String getActiveFlag() {
        return this.activeFlag;
    }

    public void setLocationIdList(final List<String> locationIdList) {
        this.locationIdList = locationIdList;
    }

    public void setLocationCodeList(final List<String> locationCodeList) {
        this.locationCodeList = locationCodeList;
    }

    public void setDeepLocationFlag(final String deepLocationFlag) {
        this.deepLocationFlag = deepLocationFlag;
    }

    public void setShallowLocationFlag(final String shallowLocationFlag) {
        this.shallowLocationFlag = shallowLocationFlag;
    }

    public void setGoodsCode(final String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public void setGoodsName(final String goodsName) {
        this.goodsName = goodsName;
    }

    public void setBatchNo(final String batchNo) {
        this.batchNo = batchNo;
    }

    public void setAmount(final Integer amount) {
        this.amount = amount;
    }

    public void setLockBy(final String lockBy) {
        this.lockBy = lockBy;
    }

    public void setUseAbleAmount(final Integer useAbleAmount) {
        this.useAbleAmount = useAbleAmount;
    }

    public void setWmsMoveTaskList(final List<WmsMoveTask> wmsMoveTaskList) {
        this.wmsMoveTaskList = wmsMoveTaskList;
    }

    public void setWmsOutTaskList(final List<WmsOutTask> wmsOutTaskList) {
        this.wmsOutTaskList = wmsOutTaskList;
    }

    public void setOutAddress(final String outAddress) {
        this.outAddress = outAddress;
    }

    public void setStacker(final String stacker) {
        this.stacker = stacker;
    }

    public void setStackerRunTaskAmount(final Integer stackerRunTaskAmount) {
        this.stackerRunTaskAmount = stackerRunTaskAmount;
    }

    public void setLocationId(final String locationId) {
        this.locationId = locationId;
    }

    public void setWarehouseCode(final String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public void setAreaCode(final String areaCode) {
        this.areaCode = areaCode;
    }

    public void setLocationCode(final String locationCode) {
        this.locationCode = locationCode;
    }

    public void setLocationAttr(final String locationAttr) {
        this.locationAttr = locationAttr;
    }

    public void setLocationDesc(final String locationDesc) {
        this.locationDesc = locationDesc;
    }

    public void setPalletCode(final String palletCode) {
        this.palletCode = palletCode;
    }

    public void setPalletCodeNull(final String palletCodeNull) {
        this.palletCodeNull = palletCodeNull;
    }

    public void setInSeq(final Integer inSeq) {
        this.inSeq = inSeq;
    }

    public void setOutSeq(final Integer outSeq) {
        this.outSeq = outSeq;
    }

    public void setFloorNumber(final Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public void setFloorNumberList(final List<Integer> floorNumberList) {
        this.floorNumberList = floorNumberList;
    }

    public void setShelvesNumber(final Integer shelvesNumber) {
        this.shelvesNumber = shelvesNumber;
    }

    public void setShelvesNumberList(final List<Integer> shelvesNumberList) {
        this.shelvesNumberList = shelvesNumberList;
    }

    public void setLayerNumber(final Integer layerNumber) {
        this.layerNumber = layerNumber;
    }

    public void setColumnNumber(final Integer columnNumber) {
        this.columnNumber = columnNumber;
    }

    public void setUseStatus(final String useStatus) {
        this.useStatus = useStatus;
    }

    public void setAllowMix(final String allowMix) {
        this.allowMix = allowMix;
    }

    public void setUserDefined1(final String userDefined1) {
        this.userDefined1 = userDefined1;
    }

    public void setUserDefined2(final String userDefined2) {
        this.userDefined2 = userDefined2;
    }

    public void setUserDefined3(final String userDefined3) {
        this.userDefined3 = userDefined3;
    }

    public void setUserDefined4(final String userDefined4) {
        this.userDefined4 = userDefined4;
    }

    public void setUserDefined5(final String userDefined5) {
        this.userDefined5 = userDefined5;
    }

    public void setStartNumber(final int startNumber) {
        this.startNumber = startNumber;
    }

    public void setNumberOnePage(final int numberOnePage) {
        this.numberOnePage = numberOnePage;
    }

    public void setOrderBy(final String orderBy) {
        this.orderBy = orderBy;
    }

    public void setCreateBy(final String createBy) {
        this.createBy = createBy;
    }

    public void setRemark(final String remark) {
        this.remark = remark;
    }

    public void setGmtCreate(final Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public void setGmtCreateMin(final Date gmtCreateMin) {
        this.gmtCreateMin = gmtCreateMin;
    }

    public void setGmtCreateMax(final Date gmtCreateMax) {
        this.gmtCreateMax = gmtCreateMax;
    }

    public void setLastModifiedBy(final String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public void setGmtModified(final Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public void setGmtModifiedMax(final Date gmtModifiedMax) {
        this.gmtModifiedMax = gmtModifiedMax;
    }

    public void setGmtModifiedMin(final Date gmtModifiedMin) {
        this.gmtModifiedMin = gmtModifiedMin;
    }

    public void setActiveFlag(final String activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Integer getZhanYongNumber() {
        return zhanYongNumber;
    }

    public void setZhanYongNumber(Integer zhanYongNumber) {
        this.zhanYongNumber = zhanYongNumber;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof WmsLocationStereoscopic)) {
            return false;
        } else {
            WmsLocationStereoscopic other = (WmsLocationStereoscopic)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label607: {
                    Object this$locationIdList = this.getLocationIdList();
                    Object other$locationIdList = other.getLocationIdList();
                    if (this$locationIdList == null) {
                        if (other$locationIdList == null) {
                            break label607;
                        }
                    } else if (this$locationIdList.equals(other$locationIdList)) {
                        break label607;
                    }

                    return false;
                }

                Object this$locationCodeList = this.getLocationCodeList();
                Object other$locationCodeList = other.getLocationCodeList();
                if (this$locationCodeList == null) {
                    if (other$locationCodeList != null) {
                        return false;
                    }
                } else if (!this$locationCodeList.equals(other$locationCodeList)) {
                    return false;
                }

                Object this$deepLocationFlag = this.getDeepLocationFlag();
                Object other$deepLocationFlag = other.getDeepLocationFlag();
                if (this$deepLocationFlag == null) {
                    if (other$deepLocationFlag != null) {
                        return false;
                    }
                } else if (!this$deepLocationFlag.equals(other$deepLocationFlag)) {
                    return false;
                }

                label586: {
                    Object this$shallowLocationFlag = this.getShallowLocationFlag();
                    Object other$shallowLocationFlag = other.getShallowLocationFlag();
                    if (this$shallowLocationFlag == null) {
                        if (other$shallowLocationFlag == null) {
                            break label586;
                        }
                    } else if (this$shallowLocationFlag.equals(other$shallowLocationFlag)) {
                        break label586;
                    }

                    return false;
                }

                label579: {
                    Object this$goodsCode = this.getGoodsCode();
                    Object other$goodsCode = other.getGoodsCode();
                    if (this$goodsCode == null) {
                        if (other$goodsCode == null) {
                            break label579;
                        }
                    } else if (this$goodsCode.equals(other$goodsCode)) {
                        break label579;
                    }

                    return false;
                }

                Object this$goodsName = this.getGoodsName();
                Object other$goodsName = other.getGoodsName();
                if (this$goodsName == null) {
                    if (other$goodsName != null) {
                        return false;
                    }
                } else if (!this$goodsName.equals(other$goodsName)) {
                    return false;
                }

                Object this$batchNo = this.getBatchNo();
                Object other$batchNo = other.getBatchNo();
                if (this$batchNo == null) {
                    if (other$batchNo != null) {
                        return false;
                    }
                } else if (!this$batchNo.equals(other$batchNo)) {
                    return false;
                }

                label558: {
                    Object this$amount = this.getAmount();
                    Object other$amount = other.getAmount();
                    if (this$amount == null) {
                        if (other$amount == null) {
                            break label558;
                        }
                    } else if (this$amount.equals(other$amount)) {
                        break label558;
                    }

                    return false;
                }

                label551: {
                    Object this$lockBy = this.getLockBy();
                    Object other$lockBy = other.getLockBy();
                    if (this$lockBy == null) {
                        if (other$lockBy == null) {
                            break label551;
                        }
                    } else if (this$lockBy.equals(other$lockBy)) {
                        break label551;
                    }

                    return false;
                }

                Object this$useAbleAmount = this.getUseAbleAmount();
                Object other$useAbleAmount = other.getUseAbleAmount();
                if (this$useAbleAmount == null) {
                    if (other$useAbleAmount != null) {
                        return false;
                    }
                } else if (!this$useAbleAmount.equals(other$useAbleAmount)) {
                    return false;
                }

                label537: {
                    Object this$wmsMoveTaskList = this.getWmsMoveTaskList();
                    Object other$wmsMoveTaskList = other.getWmsMoveTaskList();
                    if (this$wmsMoveTaskList == null) {
                        if (other$wmsMoveTaskList == null) {
                            break label537;
                        }
                    } else if (this$wmsMoveTaskList.equals(other$wmsMoveTaskList)) {
                        break label537;
                    }

                    return false;
                }

                Object this$wmsOutTaskList = this.getWmsOutTaskList();
                Object other$wmsOutTaskList = other.getWmsOutTaskList();
                if (this$wmsOutTaskList == null) {
                    if (other$wmsOutTaskList != null) {
                        return false;
                    }
                } else if (!this$wmsOutTaskList.equals(other$wmsOutTaskList)) {
                    return false;
                }

                label523: {
                    Object this$outAddress = this.getOutAddress();
                    Object other$outAddress = other.getOutAddress();
                    if (this$outAddress == null) {
                        if (other$outAddress == null) {
                            break label523;
                        }
                    } else if (this$outAddress.equals(other$outAddress)) {
                        break label523;
                    }

                    return false;
                }

                Object this$stacker = this.getStacker();
                Object other$stacker = other.getStacker();
                if (this$stacker == null) {
                    if (other$stacker != null) {
                        return false;
                    }
                } else if (!this$stacker.equals(other$stacker)) {
                    return false;
                }

                Object this$stackerRunTaskAmount = this.getStackerRunTaskAmount();
                Object other$stackerRunTaskAmount = other.getStackerRunTaskAmount();
                if (this$stackerRunTaskAmount == null) {
                    if (other$stackerRunTaskAmount != null) {
                        return false;
                    }
                } else if (!this$stackerRunTaskAmount.equals(other$stackerRunTaskAmount)) {
                    return false;
                }

                label502: {
                    Object this$locationId = this.getLocationId();
                    Object other$locationId = other.getLocationId();
                    if (this$locationId == null) {
                        if (other$locationId == null) {
                            break label502;
                        }
                    } else if (this$locationId.equals(other$locationId)) {
                        break label502;
                    }

                    return false;
                }

                label495: {
                    Object this$warehouseCode = this.getWarehouseCode();
                    Object other$warehouseCode = other.getWarehouseCode();
                    if (this$warehouseCode == null) {
                        if (other$warehouseCode == null) {
                            break label495;
                        }
                    } else if (this$warehouseCode.equals(other$warehouseCode)) {
                        break label495;
                    }

                    return false;
                }

                Object this$areaCode = this.getAreaCode();
                Object other$areaCode = other.getAreaCode();
                if (this$areaCode == null) {
                    if (other$areaCode != null) {
                        return false;
                    }
                } else if (!this$areaCode.equals(other$areaCode)) {
                    return false;
                }

                Object this$locationCode = this.getLocationCode();
                Object other$locationCode = other.getLocationCode();
                if (this$locationCode == null) {
                    if (other$locationCode != null) {
                        return false;
                    }
                } else if (!this$locationCode.equals(other$locationCode)) {
                    return false;
                }

                label474: {
                    Object this$locationAttr = this.getLocationAttr();
                    Object other$locationAttr = other.getLocationAttr();
                    if (this$locationAttr == null) {
                        if (other$locationAttr == null) {
                            break label474;
                        }
                    } else if (this$locationAttr.equals(other$locationAttr)) {
                        break label474;
                    }

                    return false;
                }

                label467: {
                    Object this$locationDesc = this.getLocationDesc();
                    Object other$locationDesc = other.getLocationDesc();
                    if (this$locationDesc == null) {
                        if (other$locationDesc == null) {
                            break label467;
                        }
                    } else if (this$locationDesc.equals(other$locationDesc)) {
                        break label467;
                    }

                    return false;
                }

                Object this$palletCode = this.getPalletCode();
                Object other$palletCode = other.getPalletCode();
                if (this$palletCode == null) {
                    if (other$palletCode != null) {
                        return false;
                    }
                } else if (!this$palletCode.equals(other$palletCode)) {
                    return false;
                }

                Object this$palletCodeNull = this.getPalletCodeNull();
                Object other$palletCodeNull = other.getPalletCodeNull();
                if (this$palletCodeNull == null) {
                    if (other$palletCodeNull != null) {
                        return false;
                    }
                } else if (!this$palletCodeNull.equals(other$palletCodeNull)) {
                    return false;
                }

                label446: {
                    Object this$inSeq = this.getInSeq();
                    Object other$inSeq = other.getInSeq();
                    if (this$inSeq == null) {
                        if (other$inSeq == null) {
                            break label446;
                        }
                    } else if (this$inSeq.equals(other$inSeq)) {
                        break label446;
                    }

                    return false;
                }

                label439: {
                    Object this$outSeq = this.getOutSeq();
                    Object other$outSeq = other.getOutSeq();
                    if (this$outSeq == null) {
                        if (other$outSeq == null) {
                            break label439;
                        }
                    } else if (this$outSeq.equals(other$outSeq)) {
                        break label439;
                    }

                    return false;
                }

                Object this$floorNumber = this.getFloorNumber();
                Object other$floorNumber = other.getFloorNumber();
                if (this$floorNumber == null) {
                    if (other$floorNumber != null) {
                        return false;
                    }
                } else if (!this$floorNumber.equals(other$floorNumber)) {
                    return false;
                }

                label425: {
                    Object this$floorNumberList = this.getFloorNumberList();
                    Object other$floorNumberList = other.getFloorNumberList();
                    if (this$floorNumberList == null) {
                        if (other$floorNumberList == null) {
                            break label425;
                        }
                    } else if (this$floorNumberList.equals(other$floorNumberList)) {
                        break label425;
                    }

                    return false;
                }

                Object this$shelvesNumber = this.getShelvesNumber();
                Object other$shelvesNumber = other.getShelvesNumber();
                if (this$shelvesNumber == null) {
                    if (other$shelvesNumber != null) {
                        return false;
                    }
                } else if (!this$shelvesNumber.equals(other$shelvesNumber)) {
                    return false;
                }

                label411: {
                    Object this$shelvesNumberList = this.getShelvesNumberList();
                    Object other$shelvesNumberList = other.getShelvesNumberList();
                    if (this$shelvesNumberList == null) {
                        if (other$shelvesNumberList == null) {
                            break label411;
                        }
                    } else if (this$shelvesNumberList.equals(other$shelvesNumberList)) {
                        break label411;
                    }

                    return false;
                }

                Object this$layerNumber = this.getLayerNumber();
                Object other$layerNumber = other.getLayerNumber();
                if (this$layerNumber == null) {
                    if (other$layerNumber != null) {
                        return false;
                    }
                } else if (!this$layerNumber.equals(other$layerNumber)) {
                    return false;
                }

                Object this$columnNumber = this.getColumnNumber();
                Object other$columnNumber = other.getColumnNumber();
                if (this$columnNumber == null) {
                    if (other$columnNumber != null) {
                        return false;
                    }
                } else if (!this$columnNumber.equals(other$columnNumber)) {
                    return false;
                }

                label390: {
                    Object this$useStatus = this.getUseStatus();
                    Object other$useStatus = other.getUseStatus();
                    if (this$useStatus == null) {
                        if (other$useStatus == null) {
                            break label390;
                        }
                    } else if (this$useStatus.equals(other$useStatus)) {
                        break label390;
                    }

                    return false;
                }

                label383: {
                    Object this$allowMix = this.getAllowMix();
                    Object other$allowMix = other.getAllowMix();
                    if (this$allowMix == null) {
                        if (other$allowMix == null) {
                            break label383;
                        }
                    } else if (this$allowMix.equals(other$allowMix)) {
                        break label383;
                    }

                    return false;
                }

                Object this$userDefined1 = this.getUserDefined1();
                Object other$userDefined1 = other.getUserDefined1();
                if (this$userDefined1 == null) {
                    if (other$userDefined1 != null) {
                        return false;
                    }
                } else if (!this$userDefined1.equals(other$userDefined1)) {
                    return false;
                }

                Object this$userDefined2 = this.getUserDefined2();
                Object other$userDefined2 = other.getUserDefined2();
                if (this$userDefined2 == null) {
                    if (other$userDefined2 != null) {
                        return false;
                    }
                } else if (!this$userDefined2.equals(other$userDefined2)) {
                    return false;
                }

                label362: {
                    Object this$userDefined3 = this.getUserDefined3();
                    Object other$userDefined3 = other.getUserDefined3();
                    if (this$userDefined3 == null) {
                        if (other$userDefined3 == null) {
                            break label362;
                        }
                    } else if (this$userDefined3.equals(other$userDefined3)) {
                        break label362;
                    }

                    return false;
                }

                label355: {
                    Object this$userDefined4 = this.getUserDefined4();
                    Object other$userDefined4 = other.getUserDefined4();
                    if (this$userDefined4 == null) {
                        if (other$userDefined4 == null) {
                            break label355;
                        }
                    } else if (this$userDefined4.equals(other$userDefined4)) {
                        break label355;
                    }

                    return false;
                }

                Object this$userDefined5 = this.getUserDefined5();
                Object other$userDefined5 = other.getUserDefined5();
                if (this$userDefined5 == null) {
                    if (other$userDefined5 != null) {
                        return false;
                    }
                } else if (!this$userDefined5.equals(other$userDefined5)) {
                    return false;
                }

                if (this.getStartNumber() != other.getStartNumber()) {
                    return false;
                } else if (this.getNumberOnePage() != other.getNumberOnePage()) {
                    return false;
                } else {
                    label338: {
                        Object this$orderBy = this.getOrderBy();
                        Object other$orderBy = other.getOrderBy();
                        if (this$orderBy == null) {
                            if (other$orderBy == null) {
                                break label338;
                            }
                        } else if (this$orderBy.equals(other$orderBy)) {
                            break label338;
                        }

                        return false;
                    }

                    Object this$createBy = this.getCreateBy();
                    Object other$createBy = other.getCreateBy();
                    if (this$createBy == null) {
                        if (other$createBy != null) {
                            return false;
                        }
                    } else if (!this$createBy.equals(other$createBy)) {
                        return false;
                    }

                    Object this$remark = this.getRemark();
                    Object other$remark = other.getRemark();
                    if (this$remark == null) {
                        if (other$remark != null) {
                            return false;
                        }
                    } else if (!this$remark.equals(other$remark)) {
                        return false;
                    }

                    label317: {
                        Object this$gmtCreate = this.getGmtCreate();
                        Object other$gmtCreate = other.getGmtCreate();
                        if (this$gmtCreate == null) {
                            if (other$gmtCreate == null) {
                                break label317;
                            }
                        } else if (this$gmtCreate.equals(other$gmtCreate)) {
                            break label317;
                        }

                        return false;
                    }

                    label310: {
                        Object this$gmtCreateMin = this.getGmtCreateMin();
                        Object other$gmtCreateMin = other.getGmtCreateMin();
                        if (this$gmtCreateMin == null) {
                            if (other$gmtCreateMin == null) {
                                break label310;
                            }
                        } else if (this$gmtCreateMin.equals(other$gmtCreateMin)) {
                            break label310;
                        }

                        return false;
                    }

                    Object this$gmtCreateMax = this.getGmtCreateMax();
                    Object other$gmtCreateMax = other.getGmtCreateMax();
                    if (this$gmtCreateMax == null) {
                        if (other$gmtCreateMax != null) {
                            return false;
                        }
                    } else if (!this$gmtCreateMax.equals(other$gmtCreateMax)) {
                        return false;
                    }

                    label296: {
                        Object this$lastModifiedBy = this.getLastModifiedBy();
                        Object other$lastModifiedBy = other.getLastModifiedBy();
                        if (this$lastModifiedBy == null) {
                            if (other$lastModifiedBy == null) {
                                break label296;
                            }
                        } else if (this$lastModifiedBy.equals(other$lastModifiedBy)) {
                            break label296;
                        }

                        return false;
                    }

                    Object this$gmtModified = this.getGmtModified();
                    Object other$gmtModified = other.getGmtModified();
                    if (this$gmtModified == null) {
                        if (other$gmtModified != null) {
                            return false;
                        }
                    } else if (!this$gmtModified.equals(other$gmtModified)) {
                        return false;
                    }

                    label282: {
                        Object this$gmtModifiedMax = this.getGmtModifiedMax();
                        Object other$gmtModifiedMax = other.getGmtModifiedMax();
                        if (this$gmtModifiedMax == null) {
                            if (other$gmtModifiedMax == null) {
                                break label282;
                            }
                        } else if (this$gmtModifiedMax.equals(other$gmtModifiedMax)) {
                            break label282;
                        }

                        return false;
                    }

                    Object this$gmtModifiedMin = this.getGmtModifiedMin();
                    Object other$gmtModifiedMin = other.getGmtModifiedMin();
                    if (this$gmtModifiedMin == null) {
                        if (other$gmtModifiedMin != null) {
                            return false;
                        }
                    } else if (!this$gmtModifiedMin.equals(other$gmtModifiedMin)) {
                        return false;
                    }

                    Object this$activeFlag = this.getActiveFlag();
                    Object other$activeFlag = other.getActiveFlag();
                    if (this$activeFlag == null) {
                        if (other$activeFlag == null) {
                            return true;
                        }
                    } else if (this$activeFlag.equals(other$activeFlag)) {
                        return true;
                    }

                    return false;
                }
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof WmsLocationStereoscopic;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $locationIdList = this.getLocationIdList();
        result = result * 59 + ($locationIdList == null ? 43 : $locationIdList.hashCode());
        Object $locationCodeList = this.getLocationCodeList();
        result = result * 59 + ($locationCodeList == null ? 43 : $locationCodeList.hashCode());
        Object $deepLocationFlag = this.getDeepLocationFlag();
        result = result * 59 + ($deepLocationFlag == null ? 43 : $deepLocationFlag.hashCode());
        Object $shallowLocationFlag = this.getShallowLocationFlag();
        result = result * 59 + ($shallowLocationFlag == null ? 43 : $shallowLocationFlag.hashCode());
        Object $goodsCode = this.getGoodsCode();
        result = result * 59 + ($goodsCode == null ? 43 : $goodsCode.hashCode());
        Object $goodsName = this.getGoodsName();
        result = result * 59 + ($goodsName == null ? 43 : $goodsName.hashCode());
        Object $batchNo = this.getBatchNo();
        result = result * 59 + ($batchNo == null ? 43 : $batchNo.hashCode());
        Object $amount = this.getAmount();
        result = result * 59 + ($amount == null ? 43 : $amount.hashCode());
        Object $lockBy = this.getLockBy();
        result = result * 59 + ($lockBy == null ? 43 : $lockBy.hashCode());
        Object $useAbleAmount = this.getUseAbleAmount();
        result = result * 59 + ($useAbleAmount == null ? 43 : $useAbleAmount.hashCode());
        Object $wmsMoveTaskList = this.getWmsMoveTaskList();
        result = result * 59 + ($wmsMoveTaskList == null ? 43 : $wmsMoveTaskList.hashCode());
        Object $wmsOutTaskList = this.getWmsOutTaskList();
        result = result * 59 + ($wmsOutTaskList == null ? 43 : $wmsOutTaskList.hashCode());
        Object $outAddress = this.getOutAddress();
        result = result * 59 + ($outAddress == null ? 43 : $outAddress.hashCode());
        Object $stacker = this.getStacker();
        result = result * 59 + ($stacker == null ? 43 : $stacker.hashCode());
        Object $stackerRunTaskAmount = this.getStackerRunTaskAmount();
        result = result * 59 + ($stackerRunTaskAmount == null ? 43 : $stackerRunTaskAmount.hashCode());
        Object $locationId = this.getLocationId();
        result = result * 59 + ($locationId == null ? 43 : $locationId.hashCode());
        Object $warehouseCode = this.getWarehouseCode();
        result = result * 59 + ($warehouseCode == null ? 43 : $warehouseCode.hashCode());
        Object $areaCode = this.getAreaCode();
        result = result * 59 + ($areaCode == null ? 43 : $areaCode.hashCode());
        Object $locationCode = this.getLocationCode();
        result = result * 59 + ($locationCode == null ? 43 : $locationCode.hashCode());
        Object $locationAttr = this.getLocationAttr();
        result = result * 59 + ($locationAttr == null ? 43 : $locationAttr.hashCode());
        Object $locationDesc = this.getLocationDesc();
        result = result * 59 + ($locationDesc == null ? 43 : $locationDesc.hashCode());
        Object $palletCode = this.getPalletCode();
        result = result * 59 + ($palletCode == null ? 43 : $palletCode.hashCode());
        Object $palletCodeNull = this.getPalletCodeNull();
        result = result * 59 + ($palletCodeNull == null ? 43 : $palletCodeNull.hashCode());
        Object $inSeq = this.getInSeq();
        result = result * 59 + ($inSeq == null ? 43 : $inSeq.hashCode());
        Object $outSeq = this.getOutSeq();
        result = result * 59 + ($outSeq == null ? 43 : $outSeq.hashCode());
        Object $floorNumber = this.getFloorNumber();
        result = result * 59 + ($floorNumber == null ? 43 : $floorNumber.hashCode());
        Object $floorNumberList = this.getFloorNumberList();
        result = result * 59 + ($floorNumberList == null ? 43 : $floorNumberList.hashCode());
        Object $shelvesNumber = this.getShelvesNumber();
        result = result * 59 + ($shelvesNumber == null ? 43 : $shelvesNumber.hashCode());
        Object $shelvesNumberList = this.getShelvesNumberList();
        result = result * 59 + ($shelvesNumberList == null ? 43 : $shelvesNumberList.hashCode());
        Object $layerNumber = this.getLayerNumber();
        result = result * 59 + ($layerNumber == null ? 43 : $layerNumber.hashCode());
        Object $columnNumber = this.getColumnNumber();
        result = result * 59 + ($columnNumber == null ? 43 : $columnNumber.hashCode());
        Object $useStatus = this.getUseStatus();
        result = result * 59 + ($useStatus == null ? 43 : $useStatus.hashCode());
        Object $allowMix = this.getAllowMix();
        result = result * 59 + ($allowMix == null ? 43 : $allowMix.hashCode());
        Object $userDefined1 = this.getUserDefined1();
        result = result * 59 + ($userDefined1 == null ? 43 : $userDefined1.hashCode());
        Object $userDefined2 = this.getUserDefined2();
        result = result * 59 + ($userDefined2 == null ? 43 : $userDefined2.hashCode());
        Object $userDefined3 = this.getUserDefined3();
        result = result * 59 + ($userDefined3 == null ? 43 : $userDefined3.hashCode());
        Object $userDefined4 = this.getUserDefined4();
        result = result * 59 + ($userDefined4 == null ? 43 : $userDefined4.hashCode());
        Object $userDefined5 = this.getUserDefined5();
        result = result * 59 + ($userDefined5 == null ? 43 : $userDefined5.hashCode());
        result = result * 59 + this.getStartNumber();
        result = result * 59 + this.getNumberOnePage();
        Object $orderBy = this.getOrderBy();
        result = result * 59 + ($orderBy == null ? 43 : $orderBy.hashCode());
        Object $createBy = this.getCreateBy();
        result = result * 59 + ($createBy == null ? 43 : $createBy.hashCode());
        Object $remark = this.getRemark();
        result = result * 59 + ($remark == null ? 43 : $remark.hashCode());
        Object $gmtCreate = this.getGmtCreate();
        result = result * 59 + ($gmtCreate == null ? 43 : $gmtCreate.hashCode());
        Object $gmtCreateMin = this.getGmtCreateMin();
        result = result * 59 + ($gmtCreateMin == null ? 43 : $gmtCreateMin.hashCode());
        Object $gmtCreateMax = this.getGmtCreateMax();
        result = result * 59 + ($gmtCreateMax == null ? 43 : $gmtCreateMax.hashCode());
        Object $lastModifiedBy = this.getLastModifiedBy();
        result = result * 59 + ($lastModifiedBy == null ? 43 : $lastModifiedBy.hashCode());
        Object $gmtModified = this.getGmtModified();
        result = result * 59 + ($gmtModified == null ? 43 : $gmtModified.hashCode());
        Object $gmtModifiedMax = this.getGmtModifiedMax();
        result = result * 59 + ($gmtModifiedMax == null ? 43 : $gmtModifiedMax.hashCode());
        Object $gmtModifiedMin = this.getGmtModifiedMin();
        result = result * 59 + ($gmtModifiedMin == null ? 43 : $gmtModifiedMin.hashCode());
        Object $activeFlag = this.getActiveFlag();
        result = result * 59 + ($activeFlag == null ? 43 : $activeFlag.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "WmsLocationStereoscopic{" +
                "locationIdList=" + locationIdList +
                ", locationCodeList=" + locationCodeList +
                ", deepLocationFlag='" + deepLocationFlag + '\'' +
                ", shallowLocationFlag='" + shallowLocationFlag + '\'' +
                ", goodsCode='" + goodsCode + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", batchNo='" + batchNo + '\'' +
                ", amount=" + amount +
                ", lockBy='" + lockBy + '\'' +
                ", useAbleAmount=" + useAbleAmount +
                ", wmsMoveTaskList=" + wmsMoveTaskList +
                ", wmsOutTaskList=" + wmsOutTaskList +
                ", outAddress='" + outAddress + '\'' +
                ", stacker='" + stacker + '\'' +
                ", stackerRunTaskAmount=" + stackerRunTaskAmount +
                ", locationId='" + locationId + '\'' +
                ", warehouseCode='" + warehouseCode + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", locationCode='" + locationCode + '\'' +
                ", locationAttr='" + locationAttr + '\'' +
                ", locationDesc='" + locationDesc + '\'' +
                ", palletCode='" + palletCode + '\'' +
                ", palletCodeNull='" + palletCodeNull + '\'' +
                ", inSeq=" + inSeq +
                ", outSeq=" + outSeq +
                ", floorNumber=" + floorNumber +
                ", floorNumberList=" + floorNumberList +
                ", shelvesNumber=" + shelvesNumber +
                ", shelvesNumberList=" + shelvesNumberList +
                ", layerNumber=" + layerNumber +
                ", columnNumber=" + columnNumber +
                ", useStatus='" + useStatus + '\'' +
                ", allowMix='" + allowMix + '\'' +
                ", userDefined1='" + userDefined1 + '\'' +
                ", userDefined2='" + userDefined2 + '\'' +
                ", userDefined3='" + userDefined3 + '\'' +
                ", userDefined4='" + userDefined4 + '\'' +
                ", userDefined5='" + userDefined5 + '\'' +
                ", startNumber=" + startNumber +
                ", numberOnePage=" + numberOnePage +
                ", orderBy='" + orderBy + '\'' +
                ", createBy='" + createBy + '\'' +
                ", remark='" + remark + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtCreateMin=" + gmtCreateMin +
                ", gmtCreateMax=" + gmtCreateMax +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", gmtModified=" + gmtModified +
                ", gmtModifiedMax=" + gmtModifiedMax +
                ", gmtModifiedMin=" + gmtModifiedMin +
                ", activeFlag='" + activeFlag + '\'' +
                ", zhanYongNumber=" + zhanYongNumber +
                '}';
    }
}
