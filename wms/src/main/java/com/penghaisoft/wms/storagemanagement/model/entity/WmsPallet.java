//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.entity;

import java.util.Date;
import java.util.List;

public class WmsPallet {
    private List<String> palletIdList;
    private List<String> palletCodeList;
    private long taskId;
    private String palletId;
    private String palletCode;
    private String goodsCode;
    private String goodsCodeIsNotNull;
    private String goodsCodeNull;
    private String goodsName;
    private Integer amount;
    private Integer number;
    private String amountNull;
    private String batchNo;
    private String batchNoNull;
    private String warehouseCode;
    private String areaCode;
    private String locationCode;
    private String locationCodeNull;
    private String locationCodeIsNull;
    private String lockBy;
    private String lockByNull;
    private String lockByIsNull;
    private String channelLocation;
    private String channelLocationNull;
    private String userDefined1;
    private String userDefined2;
    private String userDefined3;
    private String userDefined3Null;
    private String userDefined4;
    private String userDefined5;
    private String boxBarcodeList;
    private List<String> boxList;
    private String hasBoxCode;
    private String orderBy;
    private int startNumber;
    private int numberOnePage;
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

    public WmsPallet() {
    }

    public List<String> getPalletIdList() {
        return this.palletIdList;
    }

    public List<String> getPalletCodeList() {
        return this.palletCodeList;
    }

    public long getTaskId() {
        return this.taskId;
    }

    public String getPalletId() {
        return this.palletId;
    }

    public String getPalletCode() {
        return this.palletCode;
    }

    public String getGoodsCode() {
        return this.goodsCode;
    }

    public String getGoodsCodeIsNotNull() {
        return this.goodsCodeIsNotNull;
    }

    public String getGoodsCodeNull() {
        return this.goodsCodeNull;
    }

    public String getGoodsName() {
        return this.goodsName;
    }

    public Integer getAmount() {
        return this.amount;
    }

    public Integer getNumber() {
        return this.number;
    }

    public String getAmountNull() {
        return this.amountNull;
    }

    public String getBatchNo() {
        return this.batchNo;
    }

    public String getBatchNoNull() {
        return this.batchNoNull;
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

    public String getLocationCodeNull() {
        return this.locationCodeNull;
    }

    public String getLocationCodeIsNull() {
        return this.locationCodeIsNull;
    }

    public String getLockBy() {
        return this.lockBy;
    }

    public String getLockByNull() {
        return this.lockByNull;
    }

    public String getLockByIsNull() {
        return this.lockByIsNull;
    }

    public String getChannelLocation() {
        return this.channelLocation;
    }

    public String getChannelLocationNull() {
        return this.channelLocationNull;
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

    public String getBoxBarcodeList() {
        return this.boxBarcodeList;
    }

    public List<String> getBoxList() {
        return this.boxList;
    }

    public String getHasBoxCode() {
        return this.hasBoxCode;
    }

    public String getOrderBy() {
        return this.orderBy;
    }

    public int getStartNumber() {
        return this.startNumber;
    }

    public int getNumberOnePage() {
        return this.numberOnePage;
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

    public String getUserDefined3Null() {
        return userDefined3Null;
    }

    public void setUserDefined3Null(String userDefined3Null) {
        this.userDefined3Null = userDefined3Null;
    }

    public void setPalletIdList(final List<String> palletIdList) {
        this.palletIdList = palletIdList;
    }

    public void setPalletCodeList(final List<String> palletCodeList) {
        this.palletCodeList = palletCodeList;
    }

    public void setTaskId(final long taskId) {
        this.taskId = taskId;
    }

    public void setPalletId(final String palletId) {
        this.palletId = palletId;
    }

    public void setPalletCode(final String palletCode) {
        this.palletCode = palletCode;
    }

    public void setGoodsCode(final String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public void setGoodsCodeIsNotNull(final String goodsCodeIsNotNull) {
        this.goodsCodeIsNotNull = goodsCodeIsNotNull;
    }

    public void setGoodsCodeNull(final String goodsCodeNull) {
        this.goodsCodeNull = goodsCodeNull;
    }

    public void setGoodsName(final String goodsName) {
        this.goodsName = goodsName;
    }

    public void setAmount(final Integer amount) {
        this.amount = amount;
    }

    public void setNumber(final Integer number) {
        this.number = number;
    }

    public void setAmountNull(final String amountNull) {
        this.amountNull = amountNull;
    }

    public void setBatchNo(final String batchNo) {
        this.batchNo = batchNo;
    }

    public void setBatchNoNull(final String batchNoNull) {
        this.batchNoNull = batchNoNull;
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

    public void setLocationCodeNull(final String locationCodeNull) {
        this.locationCodeNull = locationCodeNull;
    }

    public void setLocationCodeIsNull(final String locationCodeIsNull) {
        this.locationCodeIsNull = locationCodeIsNull;
    }

    public void setLockBy(final String lockBy) {
        this.lockBy = lockBy;
    }

    public void setLockByNull(final String lockByNull) {
        this.lockByNull = lockByNull;
    }

    public void setLockByIsNull(final String lockByIsNull) {
        this.lockByIsNull = lockByIsNull;
    }

    public void setChannelLocation(final String channelLocation) {
        this.channelLocation = channelLocation;
    }

    public void setChannelLocationNull(final String channelLocationNull) {
        this.channelLocationNull = channelLocationNull;
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

    public void setBoxBarcodeList(final String boxBarcodeList) {
        this.boxBarcodeList = boxBarcodeList;
    }

    public void setBoxList(final List<String> boxList) {
        this.boxList = boxList;
    }

    public void setHasBoxCode(final String hasBoxCode) {
        this.hasBoxCode = hasBoxCode;
    }

    public void setOrderBy(final String orderBy) {
        this.orderBy = orderBy;
    }

    public void setStartNumber(final int startNumber) {
        this.startNumber = startNumber;
    }

    public void setNumberOnePage(final int numberOnePage) {
        this.numberOnePage = numberOnePage;
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

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof WmsPallet)) {
            return false;
        } else {
            WmsPallet other = (WmsPallet)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label527: {
                    Object this$palletIdList = this.getPalletIdList();
                    Object other$palletIdList = other.getPalletIdList();
                    if (this$palletIdList == null) {
                        if (other$palletIdList == null) {
                            break label527;
                        }
                    } else if (this$palletIdList.equals(other$palletIdList)) {
                        break label527;
                    }

                    return false;
                }

                Object this$palletCodeList = this.getPalletCodeList();
                Object other$palletCodeList = other.getPalletCodeList();
                if (this$palletCodeList == null) {
                    if (other$palletCodeList != null) {
                        return false;
                    }
                } else if (!this$palletCodeList.equals(other$palletCodeList)) {
                    return false;
                }

                if (this.getTaskId() != other.getTaskId()) {
                    return false;
                } else {
                    Object this$palletId = this.getPalletId();
                    Object other$palletId = other.getPalletId();
                    if (this$palletId == null) {
                        if (other$palletId != null) {
                            return false;
                        }
                    } else if (!this$palletId.equals(other$palletId)) {
                        return false;
                    }

                    label505: {
                        Object this$palletCode = this.getPalletCode();
                        Object other$palletCode = other.getPalletCode();
                        if (this$palletCode == null) {
                            if (other$palletCode == null) {
                                break label505;
                            }
                        } else if (this$palletCode.equals(other$palletCode)) {
                            break label505;
                        }

                        return false;
                    }

                    Object this$goodsCode = this.getGoodsCode();
                    Object other$goodsCode = other.getGoodsCode();
                    if (this$goodsCode == null) {
                        if (other$goodsCode != null) {
                            return false;
                        }
                    } else if (!this$goodsCode.equals(other$goodsCode)) {
                        return false;
                    }

                    label491: {
                        Object this$goodsCodeIsNotNull = this.getGoodsCodeIsNotNull();
                        Object other$goodsCodeIsNotNull = other.getGoodsCodeIsNotNull();
                        if (this$goodsCodeIsNotNull == null) {
                            if (other$goodsCodeIsNotNull == null) {
                                break label491;
                            }
                        } else if (this$goodsCodeIsNotNull.equals(other$goodsCodeIsNotNull)) {
                            break label491;
                        }

                        return false;
                    }

                    Object this$goodsCodeNull = this.getGoodsCodeNull();
                    Object other$goodsCodeNull = other.getGoodsCodeNull();
                    if (this$goodsCodeNull == null) {
                        if (other$goodsCodeNull != null) {
                            return false;
                        }
                    } else if (!this$goodsCodeNull.equals(other$goodsCodeNull)) {
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

                    label470: {
                        Object this$amount = this.getAmount();
                        Object other$amount = other.getAmount();
                        if (this$amount == null) {
                            if (other$amount == null) {
                                break label470;
                            }
                        } else if (this$amount.equals(other$amount)) {
                            break label470;
                        }

                        return false;
                    }

                    label463: {
                        Object this$number = this.getNumber();
                        Object other$number = other.getNumber();
                        if (this$number == null) {
                            if (other$number == null) {
                                break label463;
                            }
                        } else if (this$number.equals(other$number)) {
                            break label463;
                        }

                        return false;
                    }

                    label456: {
                        Object this$amountNull = this.getAmountNull();
                        Object other$amountNull = other.getAmountNull();
                        if (this$amountNull == null) {
                            if (other$amountNull == null) {
                                break label456;
                            }
                        } else if (this$amountNull.equals(other$amountNull)) {
                            break label456;
                        }

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

                    label442: {
                        Object this$batchNoNull = this.getBatchNoNull();
                        Object other$batchNoNull = other.getBatchNoNull();
                        if (this$batchNoNull == null) {
                            if (other$batchNoNull == null) {
                                break label442;
                            }
                        } else if (this$batchNoNull.equals(other$batchNoNull)) {
                            break label442;
                        }

                        return false;
                    }

                    label435: {
                        Object this$warehouseCode = this.getWarehouseCode();
                        Object other$warehouseCode = other.getWarehouseCode();
                        if (this$warehouseCode == null) {
                            if (other$warehouseCode == null) {
                                break label435;
                            }
                        } else if (this$warehouseCode.equals(other$warehouseCode)) {
                            break label435;
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

                    label414: {
                        Object this$locationCodeNull = this.getLocationCodeNull();
                        Object other$locationCodeNull = other.getLocationCodeNull();
                        if (this$locationCodeNull == null) {
                            if (other$locationCodeNull == null) {
                                break label414;
                            }
                        } else if (this$locationCodeNull.equals(other$locationCodeNull)) {
                            break label414;
                        }

                        return false;
                    }

                    label407: {
                        Object this$locationCodeIsNull = this.getLocationCodeIsNull();
                        Object other$locationCodeIsNull = other.getLocationCodeIsNull();
                        if (this$locationCodeIsNull == null) {
                            if (other$locationCodeIsNull == null) {
                                break label407;
                            }
                        } else if (this$locationCodeIsNull.equals(other$locationCodeIsNull)) {
                            break label407;
                        }

                        return false;
                    }

                    Object this$lockBy = this.getLockBy();
                    Object other$lockBy = other.getLockBy();
                    if (this$lockBy == null) {
                        if (other$lockBy != null) {
                            return false;
                        }
                    } else if (!this$lockBy.equals(other$lockBy)) {
                        return false;
                    }

                    label393: {
                        Object this$lockByNull = this.getLockByNull();
                        Object other$lockByNull = other.getLockByNull();
                        if (this$lockByNull == null) {
                            if (other$lockByNull == null) {
                                break label393;
                            }
                        } else if (this$lockByNull.equals(other$lockByNull)) {
                            break label393;
                        }

                        return false;
                    }

                    Object this$lockByIsNull = this.getLockByIsNull();
                    Object other$lockByIsNull = other.getLockByIsNull();
                    if (this$lockByIsNull == null) {
                        if (other$lockByIsNull != null) {
                            return false;
                        }
                    } else if (!this$lockByIsNull.equals(other$lockByIsNull)) {
                        return false;
                    }

                    label379: {
                        Object this$channelLocation = this.getChannelLocation();
                        Object other$channelLocation = other.getChannelLocation();
                        if (this$channelLocation == null) {
                            if (other$channelLocation == null) {
                                break label379;
                            }
                        } else if (this$channelLocation.equals(other$channelLocation)) {
                            break label379;
                        }

                        return false;
                    }

                    Object this$channelLocationNull = this.getChannelLocationNull();
                    Object other$channelLocationNull = other.getChannelLocationNull();
                    if (this$channelLocationNull == null) {
                        if (other$channelLocationNull != null) {
                            return false;
                        }
                    } else if (!this$channelLocationNull.equals(other$channelLocationNull)) {
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

                    label358: {
                        Object this$userDefined2 = this.getUserDefined2();
                        Object other$userDefined2 = other.getUserDefined2();
                        if (this$userDefined2 == null) {
                            if (other$userDefined2 == null) {
                                break label358;
                            }
                        } else if (this$userDefined2.equals(other$userDefined2)) {
                            break label358;
                        }

                        return false;
                    }

                    label351: {
                        Object this$userDefined3 = this.getUserDefined3();
                        Object other$userDefined3 = other.getUserDefined3();
                        if (this$userDefined3 == null) {
                            if (other$userDefined3 == null) {
                                break label351;
                            }
                        } else if (this$userDefined3.equals(other$userDefined3)) {
                            break label351;
                        }

                        return false;
                    }

                    label344: {
                        Object this$userDefined4 = this.getUserDefined4();
                        Object other$userDefined4 = other.getUserDefined4();
                        if (this$userDefined4 == null) {
                            if (other$userDefined4 == null) {
                                break label344;
                            }
                        } else if (this$userDefined4.equals(other$userDefined4)) {
                            break label344;
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

                    label330: {
                        Object this$boxBarcodeList = this.getBoxBarcodeList();
                        Object other$boxBarcodeList = other.getBoxBarcodeList();
                        if (this$boxBarcodeList == null) {
                            if (other$boxBarcodeList == null) {
                                break label330;
                            }
                        } else if (this$boxBarcodeList.equals(other$boxBarcodeList)) {
                            break label330;
                        }

                        return false;
                    }

                    label323: {
                        Object this$boxList = this.getBoxList();
                        Object other$boxList = other.getBoxList();
                        if (this$boxList == null) {
                            if (other$boxList == null) {
                                break label323;
                            }
                        } else if (this$boxList.equals(other$boxList)) {
                            break label323;
                        }

                        return false;
                    }

                    Object this$hasBoxCode = this.getHasBoxCode();
                    Object other$hasBoxCode = other.getHasBoxCode();
                    if (this$hasBoxCode == null) {
                        if (other$hasBoxCode != null) {
                            return false;
                        }
                    } else if (!this$hasBoxCode.equals(other$hasBoxCode)) {
                        return false;
                    }

                    Object this$orderBy = this.getOrderBy();
                    Object other$orderBy = other.getOrderBy();
                    if (this$orderBy == null) {
                        if (other$orderBy != null) {
                            return false;
                        }
                    } else if (!this$orderBy.equals(other$orderBy)) {
                        return false;
                    }

                    if (this.getStartNumber() != other.getStartNumber()) {
                        return false;
                    } else if (this.getNumberOnePage() != other.getNumberOnePage()) {
                        return false;
                    } else {
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

                        label285: {
                            Object this$gmtCreate = this.getGmtCreate();
                            Object other$gmtCreate = other.getGmtCreate();
                            if (this$gmtCreate == null) {
                                if (other$gmtCreate == null) {
                                    break label285;
                                }
                            } else if (this$gmtCreate.equals(other$gmtCreate)) {
                                break label285;
                            }

                            return false;
                        }

                        Object this$gmtCreateMin = this.getGmtCreateMin();
                        Object other$gmtCreateMin = other.getGmtCreateMin();
                        if (this$gmtCreateMin == null) {
                            if (other$gmtCreateMin != null) {
                                return false;
                            }
                        } else if (!this$gmtCreateMin.equals(other$gmtCreateMin)) {
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

                        label264: {
                            Object this$lastModifiedBy = this.getLastModifiedBy();
                            Object other$lastModifiedBy = other.getLastModifiedBy();
                            if (this$lastModifiedBy == null) {
                                if (other$lastModifiedBy == null) {
                                    break label264;
                                }
                            } else if (this$lastModifiedBy.equals(other$lastModifiedBy)) {
                                break label264;
                            }

                            return false;
                        }

                        label257: {
                            Object this$gmtModified = this.getGmtModified();
                            Object other$gmtModified = other.getGmtModified();
                            if (this$gmtModified == null) {
                                if (other$gmtModified == null) {
                                    break label257;
                                }
                            } else if (this$gmtModified.equals(other$gmtModified)) {
                                break label257;
                            }

                            return false;
                        }

                        label250: {
                            Object this$gmtModifiedMax = this.getGmtModifiedMax();
                            Object other$gmtModifiedMax = other.getGmtModifiedMax();
                            if (this$gmtModifiedMax == null) {
                                if (other$gmtModifiedMax == null) {
                                    break label250;
                                }
                            } else if (this$gmtModifiedMax.equals(other$gmtModifiedMax)) {
                                break label250;
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
                            if (other$activeFlag != null) {
                                return false;
                            }
                        } else if (!this$activeFlag.equals(other$activeFlag)) {
                            return false;
                        }

                        return true;
                    }
                }
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof WmsPallet;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $palletIdList = this.getPalletIdList();
        result = result * 59 + ($palletIdList == null ? 43 : $palletIdList.hashCode());
        Object $palletCodeList = this.getPalletCodeList();
        result = result * 59 + ($palletCodeList == null ? 43 : $palletCodeList.hashCode());
        long $taskId = this.getTaskId();
        result = result * 59 + (int)($taskId >>> 32 ^ $taskId);
        Object $palletId = this.getPalletId();
        result = result * 59 + ($palletId == null ? 43 : $palletId.hashCode());
        Object $palletCode = this.getPalletCode();
        result = result * 59 + ($palletCode == null ? 43 : $palletCode.hashCode());
        Object $goodsCode = this.getGoodsCode();
        result = result * 59 + ($goodsCode == null ? 43 : $goodsCode.hashCode());
        Object $goodsCodeIsNotNull = this.getGoodsCodeIsNotNull();
        result = result * 59 + ($goodsCodeIsNotNull == null ? 43 : $goodsCodeIsNotNull.hashCode());
        Object $goodsCodeNull = this.getGoodsCodeNull();
        result = result * 59 + ($goodsCodeNull == null ? 43 : $goodsCodeNull.hashCode());
        Object $goodsName = this.getGoodsName();
        result = result * 59 + ($goodsName == null ? 43 : $goodsName.hashCode());
        Object $amount = this.getAmount();
        result = result * 59 + ($amount == null ? 43 : $amount.hashCode());
        Object $number = this.getNumber();
        result = result * 59 + ($number == null ? 43 : $number.hashCode());
        Object $amountNull = this.getAmountNull();
        result = result * 59 + ($amountNull == null ? 43 : $amountNull.hashCode());
        Object $batchNo = this.getBatchNo();
        result = result * 59 + ($batchNo == null ? 43 : $batchNo.hashCode());
        Object $batchNoNull = this.getBatchNoNull();
        result = result * 59 + ($batchNoNull == null ? 43 : $batchNoNull.hashCode());
        Object $warehouseCode = this.getWarehouseCode();
        result = result * 59 + ($warehouseCode == null ? 43 : $warehouseCode.hashCode());
        Object $areaCode = this.getAreaCode();
        result = result * 59 + ($areaCode == null ? 43 : $areaCode.hashCode());
        Object $locationCode = this.getLocationCode();
        result = result * 59 + ($locationCode == null ? 43 : $locationCode.hashCode());
        Object $locationCodeNull = this.getLocationCodeNull();
        result = result * 59 + ($locationCodeNull == null ? 43 : $locationCodeNull.hashCode());
        Object $locationCodeIsNull = this.getLocationCodeIsNull();
        result = result * 59 + ($locationCodeIsNull == null ? 43 : $locationCodeIsNull.hashCode());
        Object $lockBy = this.getLockBy();
        result = result * 59 + ($lockBy == null ? 43 : $lockBy.hashCode());
        Object $lockByNull = this.getLockByNull();
        result = result * 59 + ($lockByNull == null ? 43 : $lockByNull.hashCode());
        Object $lockByIsNull = this.getLockByIsNull();
        result = result * 59 + ($lockByIsNull == null ? 43 : $lockByIsNull.hashCode());
        Object $channelLocation = this.getChannelLocation();
        result = result * 59 + ($channelLocation == null ? 43 : $channelLocation.hashCode());
        Object $channelLocationNull = this.getChannelLocationNull();
        result = result * 59 + ($channelLocationNull == null ? 43 : $channelLocationNull.hashCode());
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
        Object $boxBarcodeList = this.getBoxBarcodeList();
        result = result * 59 + ($boxBarcodeList == null ? 43 : $boxBarcodeList.hashCode());
        Object $boxList = this.getBoxList();
        result = result * 59 + ($boxList == null ? 43 : $boxList.hashCode());
        Object $hasBoxCode = this.getHasBoxCode();
        result = result * 59 + ($hasBoxCode == null ? 43 : $hasBoxCode.hashCode());
        Object $orderBy = this.getOrderBy();
        result = result * 59 + ($orderBy == null ? 43 : $orderBy.hashCode());
        result = result * 59 + this.getStartNumber();
        result = result * 59 + this.getNumberOnePage();
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

    public String toString() {
        return "WmsPallet(palletIdList=" + this.getPalletIdList() + ", palletCodeList=" + this.getPalletCodeList() + ", taskId=" + this.getTaskId() + ", palletId=" + this.getPalletId() + ", palletCode=" + this.getPalletCode() + ", goodsCode=" + this.getGoodsCode() + ", goodsCodeIsNotNull=" + this.getGoodsCodeIsNotNull() + ", goodsCodeNull=" + this.getGoodsCodeNull() + ", goodsName=" + this.getGoodsName() + ", amount=" + this.getAmount() + ", number=" + this.getNumber() + ", amountNull=" + this.getAmountNull() + ", batchNo=" + this.getBatchNo() + ", batchNoNull=" + this.getBatchNoNull() + ", warehouseCode=" + this.getWarehouseCode() + ", areaCode=" + this.getAreaCode() + ", locationCode=" + this.getLocationCode() + ", locationCodeNull=" + this.getLocationCodeNull() + ", locationCodeIsNull=" + this.getLocationCodeIsNull() + ", lockBy=" + this.getLockBy() + ", lockByNull=" + this.getLockByNull() + ", lockByIsNull=" + this.getLockByIsNull() + ", channelLocation=" + this.getChannelLocation() + ", channelLocationNull=" + this.getChannelLocationNull() + ", userDefined1=" + this.getUserDefined1() + ", userDefined2=" + this.getUserDefined2() + ", userDefined3=" + this.getUserDefined3()+ ", userDefined3Null=" + this.getUserDefined3Null() + ", userDefined4=" + this.getUserDefined4() + ", userDefined5=" + this.getUserDefined5() + ", boxBarcodeList=" + this.getBoxBarcodeList() + ", boxList=" + this.getBoxList() + ", hasBoxCode=" + this.getHasBoxCode() + ", orderBy=" + this.getOrderBy() + ", startNumber=" + this.getStartNumber() + ", numberOnePage=" + this.getNumberOnePage() + ", createBy=" + this.getCreateBy() + ", remark=" + this.getRemark() + ", gmtCreate=" + this.getGmtCreate() + ", gmtCreateMin=" + this.getGmtCreateMin() + ", gmtCreateMax=" + this.getGmtCreateMax() + ", lastModifiedBy=" + this.getLastModifiedBy() + ", gmtModified=" + this.getGmtModified() + ", gmtModifiedMax=" + this.getGmtModifiedMax() + ", gmtModifiedMin=" + this.getGmtModifiedMin() + ", activeFlag=" + this.getActiveFlag() + ")";
    }
}