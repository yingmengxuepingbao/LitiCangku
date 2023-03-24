//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.logmanagement.model.entity;

import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class WmsMoveLog {
    private List<String> moveLogIdList;
    private String moveLogId;
    private String palletCode;
    private String goodsCode;
    private Integer amount;
    private String batchNo;
    private String warehouseCode;
    private String areaCode;
    private String fromLocationCode;
    private String toLocationCode;
    private String moveResult;
    private String errorMsg;
    private String userDefined1;
    private String userDefined2;
    private String userDefined3;
    private String userDefined4;
    private String userDefined5;
    private String orderBy;
    private int startNumber;
    private int numberOnePage;
    private String createBy;
    private String remark;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date gmtCreate;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date gmtCreateMin;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date gmtCreateMax;
    private String lastModifiedBy;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date gmtModified;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date gmtModifiedMax;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date gmtModifiedMin;
    private String activeFlag;

    public WmsMoveLog() {
    }

    public List<String> getMoveLogIdList() {
        return this.moveLogIdList;
    }

    public String getMoveLogId() {
        return this.moveLogId;
    }

    public String getPalletCode() {
        return this.palletCode;
    }

    public String getGoodsCode() {
        return this.goodsCode;
    }

    public Integer getAmount() {
        return this.amount;
    }

    public String getBatchNo() {
        return this.batchNo;
    }

    public String getWarehouseCode() {
        return this.warehouseCode;
    }

    public String getAreaCode() {
        return this.areaCode;
    }

    public String getFromLocationCode() {
        return this.fromLocationCode;
    }

    public String getToLocationCode() {
        return this.toLocationCode;
    }

    public String getMoveResult() {
        return this.moveResult;
    }

    public String getErrorMsg() {
        return this.errorMsg;
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

    public void setMoveLogIdList(final List<String> moveLogIdList) {
        this.moveLogIdList = moveLogIdList;
    }

    public void setMoveLogId(final String moveLogId) {
        this.moveLogId = moveLogId;
    }

    public void setPalletCode(final String palletCode) {
        this.palletCode = palletCode;
    }

    public void setGoodsCode(final String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public void setAmount(final Integer amount) {
        this.amount = amount;
    }

    public void setBatchNo(final String batchNo) {
        this.batchNo = batchNo;
    }

    public void setWarehouseCode(final String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public void setAreaCode(final String areaCode) {
        this.areaCode = areaCode;
    }

    public void setFromLocationCode(final String fromLocationCode) {
        this.fromLocationCode = fromLocationCode;
    }

    public void setToLocationCode(final String toLocationCode) {
        this.toLocationCode = toLocationCode;
    }

    public void setMoveResult(final String moveResult) {
        this.moveResult = moveResult;
    }

    public void setErrorMsg(final String errorMsg) {
        this.errorMsg = errorMsg;
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
        } else if (!(o instanceof WmsMoveLog)) {
            return false;
        } else {
            WmsMoveLog other = (WmsMoveLog)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$moveLogIdList = this.getMoveLogIdList();
                Object other$moveLogIdList = other.getMoveLogIdList();
                if (this$moveLogIdList == null) {
                    if (other$moveLogIdList != null) {
                        return false;
                    }
                } else if (!this$moveLogIdList.equals(other$moveLogIdList)) {
                    return false;
                }

                Object this$moveLogId = this.getMoveLogId();
                Object other$moveLogId = other.getMoveLogId();
                if (this$moveLogId == null) {
                    if (other$moveLogId != null) {
                        return false;
                    }
                } else if (!this$moveLogId.equals(other$moveLogId)) {
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

                label334: {
                    Object this$goodsCode = this.getGoodsCode();
                    Object other$goodsCode = other.getGoodsCode();
                    if (this$goodsCode == null) {
                        if (other$goodsCode == null) {
                            break label334;
                        }
                    } else if (this$goodsCode.equals(other$goodsCode)) {
                        break label334;
                    }

                    return false;
                }

                label327: {
                    Object this$amount = this.getAmount();
                    Object other$amount = other.getAmount();
                    if (this$amount == null) {
                        if (other$amount == null) {
                            break label327;
                        }
                    } else if (this$amount.equals(other$amount)) {
                        break label327;
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

                label313: {
                    Object this$warehouseCode = this.getWarehouseCode();
                    Object other$warehouseCode = other.getWarehouseCode();
                    if (this$warehouseCode == null) {
                        if (other$warehouseCode == null) {
                            break label313;
                        }
                    } else if (this$warehouseCode.equals(other$warehouseCode)) {
                        break label313;
                    }

                    return false;
                }

                label306: {
                    Object this$areaCode = this.getAreaCode();
                    Object other$areaCode = other.getAreaCode();
                    if (this$areaCode == null) {
                        if (other$areaCode == null) {
                            break label306;
                        }
                    } else if (this$areaCode.equals(other$areaCode)) {
                        break label306;
                    }

                    return false;
                }

                Object this$fromLocationCode = this.getFromLocationCode();
                Object other$fromLocationCode = other.getFromLocationCode();
                if (this$fromLocationCode == null) {
                    if (other$fromLocationCode != null) {
                        return false;
                    }
                } else if (!this$fromLocationCode.equals(other$fromLocationCode)) {
                    return false;
                }

                Object this$toLocationCode = this.getToLocationCode();
                Object other$toLocationCode = other.getToLocationCode();
                if (this$toLocationCode == null) {
                    if (other$toLocationCode != null) {
                        return false;
                    }
                } else if (!this$toLocationCode.equals(other$toLocationCode)) {
                    return false;
                }

                label285: {
                    Object this$moveResult = this.getMoveResult();
                    Object other$moveResult = other.getMoveResult();
                    if (this$moveResult == null) {
                        if (other$moveResult == null) {
                            break label285;
                        }
                    } else if (this$moveResult.equals(other$moveResult)) {
                        break label285;
                    }

                    return false;
                }

                label278: {
                    Object this$errorMsg = this.getErrorMsg();
                    Object other$errorMsg = other.getErrorMsg();
                    if (this$errorMsg == null) {
                        if (other$errorMsg == null) {
                            break label278;
                        }
                    } else if (this$errorMsg.equals(other$errorMsg)) {
                        break label278;
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

                label264: {
                    Object this$userDefined2 = this.getUserDefined2();
                    Object other$userDefined2 = other.getUserDefined2();
                    if (this$userDefined2 == null) {
                        if (other$userDefined2 == null) {
                            break label264;
                        }
                    } else if (this$userDefined2.equals(other$userDefined2)) {
                        break label264;
                    }

                    return false;
                }

                Object this$userDefined3 = this.getUserDefined3();
                Object other$userDefined3 = other.getUserDefined3();
                if (this$userDefined3 == null) {
                    if (other$userDefined3 != null) {
                        return false;
                    }
                } else if (!this$userDefined3.equals(other$userDefined3)) {
                    return false;
                }

                label250: {
                    Object this$userDefined4 = this.getUserDefined4();
                    Object other$userDefined4 = other.getUserDefined4();
                    if (this$userDefined4 == null) {
                        if (other$userDefined4 == null) {
                            break label250;
                        }
                    } else if (this$userDefined4.equals(other$userDefined4)) {
                        break label250;
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

                    label219: {
                        Object this$remark = this.getRemark();
                        Object other$remark = other.getRemark();
                        if (this$remark == null) {
                            if (other$remark == null) {
                                break label219;
                            }
                        } else if (this$remark.equals(other$remark)) {
                            break label219;
                        }

                        return false;
                    }

                    Object this$gmtCreate = this.getGmtCreate();
                    Object other$gmtCreate = other.getGmtCreate();
                    if (this$gmtCreate == null) {
                        if (other$gmtCreate != null) {
                            return false;
                        }
                    } else if (!this$gmtCreate.equals(other$gmtCreate)) {
                        return false;
                    }

                    label205: {
                        Object this$gmtCreateMin = this.getGmtCreateMin();
                        Object other$gmtCreateMin = other.getGmtCreateMin();
                        if (this$gmtCreateMin == null) {
                            if (other$gmtCreateMin == null) {
                                break label205;
                            }
                        } else if (this$gmtCreateMin.equals(other$gmtCreateMin)) {
                            break label205;
                        }

                        return false;
                    }

                    label198: {
                        Object this$gmtCreateMax = this.getGmtCreateMax();
                        Object other$gmtCreateMax = other.getGmtCreateMax();
                        if (this$gmtCreateMax == null) {
                            if (other$gmtCreateMax == null) {
                                break label198;
                            }
                        } else if (this$gmtCreateMax.equals(other$gmtCreateMax)) {
                            break label198;
                        }

                        return false;
                    }

                    Object this$lastModifiedBy = this.getLastModifiedBy();
                    Object other$lastModifiedBy = other.getLastModifiedBy();
                    if (this$lastModifiedBy == null) {
                        if (other$lastModifiedBy != null) {
                            return false;
                        }
                    } else if (!this$lastModifiedBy.equals(other$lastModifiedBy)) {
                        return false;
                    }

                    label184: {
                        Object this$gmtModified = this.getGmtModified();
                        Object other$gmtModified = other.getGmtModified();
                        if (this$gmtModified == null) {
                            if (other$gmtModified == null) {
                                break label184;
                            }
                        } else if (this$gmtModified.equals(other$gmtModified)) {
                            break label184;
                        }

                        return false;
                    }

                    label177: {
                        Object this$gmtModifiedMax = this.getGmtModifiedMax();
                        Object other$gmtModifiedMax = other.getGmtModifiedMax();
                        if (this$gmtModifiedMax == null) {
                            if (other$gmtModifiedMax == null) {
                                break label177;
                            }
                        } else if (this$gmtModifiedMax.equals(other$gmtModifiedMax)) {
                            break label177;
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

    protected boolean canEqual(final Object other) {
        return other instanceof WmsMoveLog;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $moveLogIdList = this.getMoveLogIdList();
        result = result * 59 + ($moveLogIdList == null ? 43 : $moveLogIdList.hashCode());
        Object $moveLogId = this.getMoveLogId();
        result = result * 59 + ($moveLogId == null ? 43 : $moveLogId.hashCode());
        Object $palletCode = this.getPalletCode();
        result = result * 59 + ($palletCode == null ? 43 : $palletCode.hashCode());
        Object $goodsCode = this.getGoodsCode();
        result = result * 59 + ($goodsCode == null ? 43 : $goodsCode.hashCode());
        Object $amount = this.getAmount();
        result = result * 59 + ($amount == null ? 43 : $amount.hashCode());
        Object $batchNo = this.getBatchNo();
        result = result * 59 + ($batchNo == null ? 43 : $batchNo.hashCode());
        Object $warehouseCode = this.getWarehouseCode();
        result = result * 59 + ($warehouseCode == null ? 43 : $warehouseCode.hashCode());
        Object $areaCode = this.getAreaCode();
        result = result * 59 + ($areaCode == null ? 43 : $areaCode.hashCode());
        Object $fromLocationCode = this.getFromLocationCode();
        result = result * 59 + ($fromLocationCode == null ? 43 : $fromLocationCode.hashCode());
        Object $toLocationCode = this.getToLocationCode();
        result = result * 59 + ($toLocationCode == null ? 43 : $toLocationCode.hashCode());
        Object $moveResult = this.getMoveResult();
        result = result * 59 + ($moveResult == null ? 43 : $moveResult.hashCode());
        Object $errorMsg = this.getErrorMsg();
        result = result * 59 + ($errorMsg == null ? 43 : $errorMsg.hashCode());
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
        return "WmsMoveLog(moveLogIdList=" + this.getMoveLogIdList() + ", moveLogId=" + this.getMoveLogId() + ", palletCode=" + this.getPalletCode() + ", goodsCode=" + this.getGoodsCode() + ", amount=" + this.getAmount() + ", batchNo=" + this.getBatchNo() + ", warehouseCode=" + this.getWarehouseCode() + ", areaCode=" + this.getAreaCode() + ", fromLocationCode=" + this.getFromLocationCode() + ", toLocationCode=" + this.getToLocationCode() + ", moveResult=" + this.getMoveResult() + ", errorMsg=" + this.getErrorMsg() + ", userDefined1=" + this.getUserDefined1() + ", userDefined2=" + this.getUserDefined2() + ", userDefined3=" + this.getUserDefined3() + ", userDefined4=" + this.getUserDefined4() + ", userDefined5=" + this.getUserDefined5() + ", orderBy=" + this.getOrderBy() + ", startNumber=" + this.getStartNumber() + ", numberOnePage=" + this.getNumberOnePage() + ", createBy=" + this.getCreateBy() + ", remark=" + this.getRemark() + ", gmtCreate=" + this.getGmtCreate() + ", gmtCreateMin=" + this.getGmtCreateMin() + ", gmtCreateMax=" + this.getGmtCreateMax() + ", lastModifiedBy=" + this.getLastModifiedBy() + ", gmtModified=" + this.getGmtModified() + ", gmtModifiedMax=" + this.getGmtModifiedMax() + ", gmtModifiedMin=" + this.getGmtModifiedMin() + ", activeFlag=" + this.getActiveFlag() + ")";
    }
}
