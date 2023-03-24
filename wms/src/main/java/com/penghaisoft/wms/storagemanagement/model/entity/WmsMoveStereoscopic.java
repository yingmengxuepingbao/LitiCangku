//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.entity;

import java.util.List;

public class WmsMoveStereoscopic extends BaseEntity {
    private List<String> moveIdList;
    private String moveId;
    private String moveNo;
    private String moveStatus;
    private String areaCode;
    private String palletCode;
    private String goodsCode;
    private String goodsName;
    private Integer amount;
    private String batchNo;
    private String fromLocationCode;
    private String toLocationCode;
    private String outChannelLocationCode;
    private String inChannelLocationCode;
    private String moveResult;
    private String errorMsg;
    private long taskId;

    public WmsMoveStereoscopic() {
    }

    public List<String> getMoveIdList() {
        return this.moveIdList;
    }

    public String getMoveId() {
        return this.moveId;
    }

    public String getMoveNo() {
        return this.moveNo;
    }

    public String getMoveStatus() {
        return this.moveStatus;
    }

    public String getAreaCode() {
        return this.areaCode;
    }

    public String getPalletCode() {
        return this.palletCode;
    }

    public String getGoodsCode() {
        return this.goodsCode;
    }

    public String getGoodsName() {
        return this.goodsName;
    }

    public Integer getAmount() {
        return this.amount;
    }

    public String getBatchNo() {
        return this.batchNo;
    }

    public String getFromLocationCode() {
        return this.fromLocationCode;
    }

    public String getToLocationCode() {
        return this.toLocationCode;
    }

    public String getOutChannelLocationCode() {
        return this.outChannelLocationCode;
    }

    public String getInChannelLocationCode() {
        return this.inChannelLocationCode;
    }

    public String getMoveResult() {
        return this.moveResult;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public long getTaskId() {
        return this.taskId;
    }

    public void setMoveIdList(final List<String> moveIdList) {
        this.moveIdList = moveIdList;
    }

    public void setMoveId(final String moveId) {
        this.moveId = moveId;
    }

    public void setMoveNo(final String moveNo) {
        this.moveNo = moveNo;
    }

    public void setMoveStatus(final String moveStatus) {
        this.moveStatus = moveStatus;
    }

    public void setAreaCode(final String areaCode) {
        this.areaCode = areaCode;
    }

    public void setPalletCode(final String palletCode) {
        this.palletCode = palletCode;
    }

    public void setGoodsCode(final String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public void setGoodsName(final String goodsName) {
        this.goodsName = goodsName;
    }

    public void setAmount(final Integer amount) {
        this.amount = amount;
    }

    public void setBatchNo(final String batchNo) {
        this.batchNo = batchNo;
    }

    public void setFromLocationCode(final String fromLocationCode) {
        this.fromLocationCode = fromLocationCode;
    }

    public void setToLocationCode(final String toLocationCode) {
        this.toLocationCode = toLocationCode;
    }

    public void setOutChannelLocationCode(final String outChannelLocationCode) {
        this.outChannelLocationCode = outChannelLocationCode;
    }

    public void setInChannelLocationCode(final String inChannelLocationCode) {
        this.inChannelLocationCode = inChannelLocationCode;
    }

    public void setMoveResult(final String moveResult) {
        this.moveResult = moveResult;
    }

    public void setErrorMsg(final String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void setTaskId(final long taskId) {
        this.taskId = taskId;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof WmsMoveStereoscopic)) {
            return false;
        } else {
            WmsMoveStereoscopic other = (WmsMoveStereoscopic)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label207: {
                    Object this$moveIdList = this.getMoveIdList();
                    Object other$moveIdList = other.getMoveIdList();
                    if (this$moveIdList == null) {
                        if (other$moveIdList == null) {
                            break label207;
                        }
                    } else if (this$moveIdList.equals(other$moveIdList)) {
                        break label207;
                    }

                    return false;
                }

                Object this$moveId = this.getMoveId();
                Object other$moveId = other.getMoveId();
                if (this$moveId == null) {
                    if (other$moveId != null) {
                        return false;
                    }
                } else if (!this$moveId.equals(other$moveId)) {
                    return false;
                }

                Object this$moveNo = this.getMoveNo();
                Object other$moveNo = other.getMoveNo();
                if (this$moveNo == null) {
                    if (other$moveNo != null) {
                        return false;
                    }
                } else if (!this$moveNo.equals(other$moveNo)) {
                    return false;
                }

                label186: {
                    Object this$moveStatus = this.getMoveStatus();
                    Object other$moveStatus = other.getMoveStatus();
                    if (this$moveStatus == null) {
                        if (other$moveStatus == null) {
                            break label186;
                        }
                    } else if (this$moveStatus.equals(other$moveStatus)) {
                        break label186;
                    }

                    return false;
                }

                label179: {
                    Object this$areaCode = this.getAreaCode();
                    Object other$areaCode = other.getAreaCode();
                    if (this$areaCode == null) {
                        if (other$areaCode == null) {
                            break label179;
                        }
                    } else if (this$areaCode.equals(other$areaCode)) {
                        break label179;
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

                Object this$goodsCode = this.getGoodsCode();
                Object other$goodsCode = other.getGoodsCode();
                if (this$goodsCode == null) {
                    if (other$goodsCode != null) {
                        return false;
                    }
                } else if (!this$goodsCode.equals(other$goodsCode)) {
                    return false;
                }

                label158: {
                    Object this$goodsName = this.getGoodsName();
                    Object other$goodsName = other.getGoodsName();
                    if (this$goodsName == null) {
                        if (other$goodsName == null) {
                            break label158;
                        }
                    } else if (this$goodsName.equals(other$goodsName)) {
                        break label158;
                    }

                    return false;
                }

                label151: {
                    Object this$amount = this.getAmount();
                    Object other$amount = other.getAmount();
                    if (this$amount == null) {
                        if (other$amount == null) {
                            break label151;
                        }
                    } else if (this$amount.equals(other$amount)) {
                        break label151;
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

                label137: {
                    Object this$fromLocationCode = this.getFromLocationCode();
                    Object other$fromLocationCode = other.getFromLocationCode();
                    if (this$fromLocationCode == null) {
                        if (other$fromLocationCode == null) {
                            break label137;
                        }
                    } else if (this$fromLocationCode.equals(other$fromLocationCode)) {
                        break label137;
                    }

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

                label123: {
                    Object this$outChannelLocationCode = this.getOutChannelLocationCode();
                    Object other$outChannelLocationCode = other.getOutChannelLocationCode();
                    if (this$outChannelLocationCode == null) {
                        if (other$outChannelLocationCode == null) {
                            break label123;
                        }
                    } else if (this$outChannelLocationCode.equals(other$outChannelLocationCode)) {
                        break label123;
                    }

                    return false;
                }

                Object this$inChannelLocationCode = this.getInChannelLocationCode();
                Object other$inChannelLocationCode = other.getInChannelLocationCode();
                if (this$inChannelLocationCode == null) {
                    if (other$inChannelLocationCode != null) {
                        return false;
                    }
                } else if (!this$inChannelLocationCode.equals(other$inChannelLocationCode)) {
                    return false;
                }

                Object this$moveResult = this.getMoveResult();
                Object other$moveResult = other.getMoveResult();
                if (this$moveResult == null) {
                    if (other$moveResult != null) {
                        return false;
                    }
                } else if (!this$moveResult.equals(other$moveResult)) {
                    return false;
                }

                Object this$errorMsg = this.getErrorMsg();
                Object other$errorMsg = other.getErrorMsg();
                if (this$errorMsg == null) {
                    if (other$errorMsg != null) {
                        return false;
                    }
                } else if (!this$errorMsg.equals(other$errorMsg)) {
                    return false;
                }

                if (this.getTaskId() != other.getTaskId()) {
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof WmsMoveStereoscopic;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $moveIdList = this.getMoveIdList();
        result = result * 59 + ($moveIdList == null ? 43 : $moveIdList.hashCode());
        Object $moveId = this.getMoveId();
        result = result * 59 + ($moveId == null ? 43 : $moveId.hashCode());
        Object $moveNo = this.getMoveNo();
        result = result * 59 + ($moveNo == null ? 43 : $moveNo.hashCode());
        Object $moveStatus = this.getMoveStatus();
        result = result * 59 + ($moveStatus == null ? 43 : $moveStatus.hashCode());
        Object $areaCode = this.getAreaCode();
        result = result * 59 + ($areaCode == null ? 43 : $areaCode.hashCode());
        Object $palletCode = this.getPalletCode();
        result = result * 59 + ($palletCode == null ? 43 : $palletCode.hashCode());
        Object $goodsCode = this.getGoodsCode();
        result = result * 59 + ($goodsCode == null ? 43 : $goodsCode.hashCode());
        Object $goodsName = this.getGoodsName();
        result = result * 59 + ($goodsName == null ? 43 : $goodsName.hashCode());
        Object $amount = this.getAmount();
        result = result * 59 + ($amount == null ? 43 : $amount.hashCode());
        Object $batchNo = this.getBatchNo();
        result = result * 59 + ($batchNo == null ? 43 : $batchNo.hashCode());
        Object $fromLocationCode = this.getFromLocationCode();
        result = result * 59 + ($fromLocationCode == null ? 43 : $fromLocationCode.hashCode());
        Object $toLocationCode = this.getToLocationCode();
        result = result * 59 + ($toLocationCode == null ? 43 : $toLocationCode.hashCode());
        Object $outChannelLocationCode = this.getOutChannelLocationCode();
        result = result * 59 + ($outChannelLocationCode == null ? 43 : $outChannelLocationCode.hashCode());
        Object $inChannelLocationCode = this.getInChannelLocationCode();
        result = result * 59 + ($inChannelLocationCode == null ? 43 : $inChannelLocationCode.hashCode());
        Object $moveResult = this.getMoveResult();
        result = result * 59 + ($moveResult == null ? 43 : $moveResult.hashCode());
        Object $errorMsg = this.getErrorMsg();
        result = result * 59 + ($errorMsg == null ? 43 : $errorMsg.hashCode());
        long $taskId = this.getTaskId();
        result = result * 59 + (int)($taskId >>> 32 ^ $taskId);
        return result;
    }

    public String toString() {
        return "WmsMoveStereoscopic(moveIdList=" + this.getMoveIdList() + ", moveId=" + this.getMoveId() + ", moveNo=" + this.getMoveNo() + ", moveStatus=" + this.getMoveStatus() + ", areaCode=" + this.getAreaCode() + ", palletCode=" + this.getPalletCode() + ", goodsCode=" + this.getGoodsCode() + ", goodsName=" + this.getGoodsName() + ", amount=" + this.getAmount() + ", batchNo=" + this.getBatchNo() + ", fromLocationCode=" + this.getFromLocationCode() + ", toLocationCode=" + this.getToLocationCode() + ", outChannelLocationCode=" + this.getOutChannelLocationCode() + ", inChannelLocationCode=" + this.getInChannelLocationCode() + ", moveResult=" + this.getMoveResult() + ", errorMsg=" + this.getErrorMsg() + ", taskId=" + this.getTaskId() + ")";
    }
}
