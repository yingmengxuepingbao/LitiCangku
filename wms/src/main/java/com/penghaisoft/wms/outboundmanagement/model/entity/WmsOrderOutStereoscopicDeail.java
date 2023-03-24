//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.outboundmanagement.model.entity;

import com.penghaisoft.wms.storagemanagement.model.entity.BaseEntity;
import java.util.List;

public class WmsOrderOutStereoscopicDeail extends BaseEntity {
    private List<String> orderOutDetailIdList;
    private List<String> orderNoList;
    private String orderOutDetailId;
    private String orderOutId;
    private String orderNo;
    private String goodsName;
    private String goodsCode;
    private String batchNo;
    private Integer planAmount;
    private String planAmountMoreRealAmount;
    private Integer realAmount;
    private Integer realAmountAdd;
    private Integer planPalletAmount;
    private Integer realPalletAmount;
    private String orderType;
    private String orderStatus;
    private String outAddress;
    private String remark1;

    public WmsOrderOutStereoscopicDeail() {
    }

    public List<String> getOrderOutDetailIdList() {
        return this.orderOutDetailIdList;
    }

    public List<String> getOrderNoList() {
        return this.orderNoList;
    }

    public String getOrderOutDetailId() {
        return this.orderOutDetailId;
    }

    public String getOrderOutId() {
        return this.orderOutId;
    }

    public String getOrderNo() {
        return this.orderNo;
    }

    public String getGoodsName() {
        return this.goodsName;
    }

    public String getGoodsCode() {
        return this.goodsCode;
    }

    public String getBatchNo() {
        return this.batchNo;
    }

    public Integer getPlanAmount() {
        return this.planAmount;
    }

    public String getPlanAmountMoreRealAmount() {
        return this.planAmountMoreRealAmount;
    }

    public Integer getRealAmount() {
        return this.realAmount;
    }

    public Integer getRealAmountAdd() {
        return this.realAmountAdd;
    }

    public Integer getPlanPalletAmount() {
        return this.planPalletAmount;
    }

    public Integer getRealPalletAmount() {
        return this.realPalletAmount;
    }

    public String getOrderType() {
        return this.orderType;
    }

    public String getOrderStatus() {
        return this.orderStatus;
    }

    public String getOutAddress() {
        return this.outAddress;
    }

    public String getRemark1() {
        return this.remark1;
    }

    public void setOrderOutDetailIdList(final List<String> orderOutDetailIdList) {
        this.orderOutDetailIdList = orderOutDetailIdList;
    }

    public void setOrderNoList(final List<String> orderNoList) {
        this.orderNoList = orderNoList;
    }

    public void setOrderOutDetailId(final String orderOutDetailId) {
        this.orderOutDetailId = orderOutDetailId;
    }

    public void setOrderOutId(final String orderOutId) {
        this.orderOutId = orderOutId;
    }

    public void setOrderNo(final String orderNo) {
        this.orderNo = orderNo;
    }

    public void setGoodsName(final String goodsName) {
        this.goodsName = goodsName;
    }

    public void setGoodsCode(final String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public void setBatchNo(final String batchNo) {
        this.batchNo = batchNo;
    }

    public void setPlanAmount(final Integer planAmount) {
        this.planAmount = planAmount;
    }

    public void setPlanAmountMoreRealAmount(final String planAmountMoreRealAmount) {
        this.planAmountMoreRealAmount = planAmountMoreRealAmount;
    }

    public void setRealAmount(final Integer realAmount) {
        this.realAmount = realAmount;
    }

    public void setRealAmountAdd(final Integer realAmountAdd) {
        this.realAmountAdd = realAmountAdd;
    }

    public void setPlanPalletAmount(final Integer planPalletAmount) {
        this.planPalletAmount = planPalletAmount;
    }

    public void setRealPalletAmount(final Integer realPalletAmount) {
        this.realPalletAmount = realPalletAmount;
    }

    public void setOrderType(final String orderType) {
        this.orderType = orderType;
    }

    public void setOrderStatus(final String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setOutAddress(final String outAddress) {
        this.outAddress = outAddress;
    }

    public void setRemark1(final String remark1) {
        this.remark1 = remark1;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof WmsOrderOutStereoscopicDeail)) {
            return false;
        } else {
            WmsOrderOutStereoscopicDeail other = (WmsOrderOutStereoscopicDeail)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$orderOutDetailIdList = this.getOrderOutDetailIdList();
                Object other$orderOutDetailIdList = other.getOrderOutDetailIdList();
                if (this$orderOutDetailIdList == null) {
                    if (other$orderOutDetailIdList != null) {
                        return false;
                    }
                } else if (!this$orderOutDetailIdList.equals(other$orderOutDetailIdList)) {
                    return false;
                }

                Object this$orderNoList = this.getOrderNoList();
                Object other$orderNoList = other.getOrderNoList();
                if (this$orderNoList == null) {
                    if (other$orderNoList != null) {
                        return false;
                    }
                } else if (!this$orderNoList.equals(other$orderNoList)) {
                    return false;
                }

                Object this$orderOutDetailId = this.getOrderOutDetailId();
                Object other$orderOutDetailId = other.getOrderOutDetailId();
                if (this$orderOutDetailId == null) {
                    if (other$orderOutDetailId != null) {
                        return false;
                    }
                } else if (!this$orderOutDetailId.equals(other$orderOutDetailId)) {
                    return false;
                }

                label206: {
                    Object this$orderOutId = this.getOrderOutId();
                    Object other$orderOutId = other.getOrderOutId();
                    if (this$orderOutId == null) {
                        if (other$orderOutId == null) {
                            break label206;
                        }
                    } else if (this$orderOutId.equals(other$orderOutId)) {
                        break label206;
                    }

                    return false;
                }

                label199: {
                    Object this$orderNo = this.getOrderNo();
                    Object other$orderNo = other.getOrderNo();
                    if (this$orderNo == null) {
                        if (other$orderNo == null) {
                            break label199;
                        }
                    } else if (this$orderNo.equals(other$orderNo)) {
                        break label199;
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

                label185: {
                    Object this$goodsCode = this.getGoodsCode();
                    Object other$goodsCode = other.getGoodsCode();
                    if (this$goodsCode == null) {
                        if (other$goodsCode == null) {
                            break label185;
                        }
                    } else if (this$goodsCode.equals(other$goodsCode)) {
                        break label185;
                    }

                    return false;
                }

                label178: {
                    Object this$batchNo = this.getBatchNo();
                    Object other$batchNo = other.getBatchNo();
                    if (this$batchNo == null) {
                        if (other$batchNo == null) {
                            break label178;
                        }
                    } else if (this$batchNo.equals(other$batchNo)) {
                        break label178;
                    }

                    return false;
                }

                Object this$planAmount = this.getPlanAmount();
                Object other$planAmount = other.getPlanAmount();
                if (this$planAmount == null) {
                    if (other$planAmount != null) {
                        return false;
                    }
                } else if (!this$planAmount.equals(other$planAmount)) {
                    return false;
                }

                Object this$planAmountMoreRealAmount = this.getPlanAmountMoreRealAmount();
                Object other$planAmountMoreRealAmount = other.getPlanAmountMoreRealAmount();
                if (this$planAmountMoreRealAmount == null) {
                    if (other$planAmountMoreRealAmount != null) {
                        return false;
                    }
                } else if (!this$planAmountMoreRealAmount.equals(other$planAmountMoreRealAmount)) {
                    return false;
                }

                label157: {
                    Object this$realAmount = this.getRealAmount();
                    Object other$realAmount = other.getRealAmount();
                    if (this$realAmount == null) {
                        if (other$realAmount == null) {
                            break label157;
                        }
                    } else if (this$realAmount.equals(other$realAmount)) {
                        break label157;
                    }

                    return false;
                }

                label150: {
                    Object this$realAmountAdd = this.getRealAmountAdd();
                    Object other$realAmountAdd = other.getRealAmountAdd();
                    if (this$realAmountAdd == null) {
                        if (other$realAmountAdd == null) {
                            break label150;
                        }
                    } else if (this$realAmountAdd.equals(other$realAmountAdd)) {
                        break label150;
                    }

                    return false;
                }

                Object this$planPalletAmount = this.getPlanPalletAmount();
                Object other$planPalletAmount = other.getPlanPalletAmount();
                if (this$planPalletAmount == null) {
                    if (other$planPalletAmount != null) {
                        return false;
                    }
                } else if (!this$planPalletAmount.equals(other$planPalletAmount)) {
                    return false;
                }

                label136: {
                    Object this$realPalletAmount = this.getRealPalletAmount();
                    Object other$realPalletAmount = other.getRealPalletAmount();
                    if (this$realPalletAmount == null) {
                        if (other$realPalletAmount == null) {
                            break label136;
                        }
                    } else if (this$realPalletAmount.equals(other$realPalletAmount)) {
                        break label136;
                    }

                    return false;
                }

                Object this$orderType = this.getOrderType();
                Object other$orderType = other.getOrderType();
                if (this$orderType == null) {
                    if (other$orderType != null) {
                        return false;
                    }
                } else if (!this$orderType.equals(other$orderType)) {
                    return false;
                }

                label122: {
                    Object this$orderStatus = this.getOrderStatus();
                    Object other$orderStatus = other.getOrderStatus();
                    if (this$orderStatus == null) {
                        if (other$orderStatus == null) {
                            break label122;
                        }
                    } else if (this$orderStatus.equals(other$orderStatus)) {
                        break label122;
                    }

                    return false;
                }

                Object this$outAddress = this.getOutAddress();
                Object other$outAddress = other.getOutAddress();
                if (this$outAddress == null) {
                    if (other$outAddress != null) {
                        return false;
                    }
                } else if (!this$outAddress.equals(other$outAddress)) {
                    return false;
                }

                Object this$remark1 = this.getRemark1();
                Object other$remark1 = other.getRemark1();
                if (this$remark1 == null) {
                    if (other$remark1 != null) {
                        return false;
                    }
                } else if (!this$remark1.equals(other$remark1)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof WmsOrderOutStereoscopicDeail;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $orderOutDetailIdList = this.getOrderOutDetailIdList();
        result = result * 59 + ($orderOutDetailIdList == null ? 43 : $orderOutDetailIdList.hashCode());
        Object $orderNoList = this.getOrderNoList();
        result = result * 59 + ($orderNoList == null ? 43 : $orderNoList.hashCode());
        Object $orderOutDetailId = this.getOrderOutDetailId();
        result = result * 59 + ($orderOutDetailId == null ? 43 : $orderOutDetailId.hashCode());
        Object $orderOutId = this.getOrderOutId();
        result = result * 59 + ($orderOutId == null ? 43 : $orderOutId.hashCode());
        Object $orderNo = this.getOrderNo();
        result = result * 59 + ($orderNo == null ? 43 : $orderNo.hashCode());
        Object $goodsName = this.getGoodsName();
        result = result * 59 + ($goodsName == null ? 43 : $goodsName.hashCode());
        Object $goodsCode = this.getGoodsCode();
        result = result * 59 + ($goodsCode == null ? 43 : $goodsCode.hashCode());
        Object $batchNo = this.getBatchNo();
        result = result * 59 + ($batchNo == null ? 43 : $batchNo.hashCode());
        Object $planAmount = this.getPlanAmount();
        result = result * 59 + ($planAmount == null ? 43 : $planAmount.hashCode());
        Object $planAmountMoreRealAmount = this.getPlanAmountMoreRealAmount();
        result = result * 59 + ($planAmountMoreRealAmount == null ? 43 : $planAmountMoreRealAmount.hashCode());
        Object $realAmount = this.getRealAmount();
        result = result * 59 + ($realAmount == null ? 43 : $realAmount.hashCode());
        Object $realAmountAdd = this.getRealAmountAdd();
        result = result * 59 + ($realAmountAdd == null ? 43 : $realAmountAdd.hashCode());
        Object $planPalletAmount = this.getPlanPalletAmount();
        result = result * 59 + ($planPalletAmount == null ? 43 : $planPalletAmount.hashCode());
        Object $realPalletAmount = this.getRealPalletAmount();
        result = result * 59 + ($realPalletAmount == null ? 43 : $realPalletAmount.hashCode());
        Object $orderType = this.getOrderType();
        result = result * 59 + ($orderType == null ? 43 : $orderType.hashCode());
        Object $orderStatus = this.getOrderStatus();
        result = result * 59 + ($orderStatus == null ? 43 : $orderStatus.hashCode());
        Object $outAddress = this.getOutAddress();
        result = result * 59 + ($outAddress == null ? 43 : $outAddress.hashCode());
        Object $remark1 = this.getRemark1();
        result = result * 59 + ($remark1 == null ? 43 : $remark1.hashCode());
        return result;
    }

    public String toString() {
        return "WmsOrderOutStereoscopicDeail(orderOutDetailIdList=" + this.getOrderOutDetailIdList() + ", orderNoList=" + this.getOrderNoList() + ", orderOutDetailId=" + this.getOrderOutDetailId() + ", orderOutId=" + this.getOrderOutId() + ", orderNo=" + this.getOrderNo() + ", goodsName=" + this.getGoodsName() + ", goodsCode=" + this.getGoodsCode() + ", batchNo=" + this.getBatchNo() + ", planAmount=" + this.getPlanAmount() + ", planAmountMoreRealAmount=" + this.getPlanAmountMoreRealAmount() + ", realAmount=" + this.getRealAmount() + ", realAmountAdd=" + this.getRealAmountAdd() + ", planPalletAmount=" + this.getPlanPalletAmount() + ", realPalletAmount=" + this.getRealPalletAmount() + ", orderType=" + this.getOrderType() + ", orderStatus=" + this.getOrderStatus() + ", outAddress=" + this.getOutAddress() + ", remark1=" + this.getRemark1() + ")";
    }
}
