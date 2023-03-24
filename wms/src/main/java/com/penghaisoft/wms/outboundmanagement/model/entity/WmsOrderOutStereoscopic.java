//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.outboundmanagement.model.entity;

import com.penghaisoft.wms.storagemanagement.model.entity.BaseEntity;
import java.util.List;

public class WmsOrderOutStereoscopic extends BaseEntity {
    private List<String> orderOutIdList;
    private List<String> orderNoList;
    private List<WmsOrderOutStereoscopicDeail> wmsOrderOutStereoscopicDeailList;
    private String orderOutId;
    private String orderNo;
    private String orderType;
    private String orderStatus;
    private String outAddress;
    private String gmtOver;
    private String gmtRun;
    private String sapOrderNo;


    public String getGmtOver() {
        return gmtOver;
    }

    public void setGmtOver(String gmtOver) {
        this.gmtOver = gmtOver;
    }

    public String getGmtRun() {
        return gmtRun;
    }

    public void setGmtRun(String gmtRun) {
        this.gmtRun = gmtRun;
    }


    public WmsOrderOutStereoscopic() {
    }

    public List<String> getOrderOutIdList() {
        return this.orderOutIdList;
    }

    public List<String> getOrderNoList() {
        return this.orderNoList;
    }

    public List<WmsOrderOutStereoscopicDeail> getWmsOrderOutStereoscopicDeailList() {
        return this.wmsOrderOutStereoscopicDeailList;
    }

    public String getOrderOutId() {
        return this.orderOutId;
    }

    public String getOrderNo() {
        return this.orderNo;
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

    public void setOrderOutIdList(final List<String> orderOutIdList) {
        this.orderOutIdList = orderOutIdList;
    }

    public void setOrderNoList(final List<String> orderNoList) {
        this.orderNoList = orderNoList;
    }

    public void setWmsOrderOutStereoscopicDeailList(final List<WmsOrderOutStereoscopicDeail> wmsOrderOutStereoscopicDeailList) {
        this.wmsOrderOutStereoscopicDeailList = wmsOrderOutStereoscopicDeailList;
    }

    public void setOrderOutId(final String orderOutId) {
        this.orderOutId = orderOutId;
    }

    public void setOrderNo(final String orderNo) {
        this.orderNo = orderNo;
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

    public String getSapOrderNo() {
        return sapOrderNo;
    }

    public void setSapOrderNo(String sapOrderNo) {
        this.sapOrderNo = sapOrderNo;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof WmsOrderOutStereoscopic)) {
            return false;
        } else {
            WmsOrderOutStereoscopic other = (WmsOrderOutStereoscopic)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label107: {
                    Object this$orderOutIdList = this.getOrderOutIdList();
                    Object other$orderOutIdList = other.getOrderOutIdList();
                    if (this$orderOutIdList == null) {
                        if (other$orderOutIdList == null) {
                            break label107;
                        }
                    } else if (this$orderOutIdList.equals(other$orderOutIdList)) {
                        break label107;
                    }

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

                Object this$wmsOrderOutStereoscopicDeailList = this.getWmsOrderOutStereoscopicDeailList();
                Object other$wmsOrderOutStereoscopicDeailList = other.getWmsOrderOutStereoscopicDeailList();
                if (this$wmsOrderOutStereoscopicDeailList == null) {
                    if (other$wmsOrderOutStereoscopicDeailList != null) {
                        return false;
                    }
                } else if (!this$wmsOrderOutStereoscopicDeailList.equals(other$wmsOrderOutStereoscopicDeailList)) {
                    return false;
                }

                label86: {
                    Object this$orderOutId = this.getOrderOutId();
                    Object other$orderOutId = other.getOrderOutId();
                    if (this$orderOutId == null) {
                        if (other$orderOutId == null) {
                            break label86;
                        }
                    } else if (this$orderOutId.equals(other$orderOutId)) {
                        break label86;
                    }

                    return false;
                }

                label79: {
                    Object this$orderNo = this.getOrderNo();
                    Object other$orderNo = other.getOrderNo();
                    if (this$orderNo == null) {
                        if (other$orderNo == null) {
                            break label79;
                        }
                    } else if (this$orderNo.equals(other$orderNo)) {
                        break label79;
                    }

                    return false;
                }

                label72: {
                    Object this$orderType = this.getOrderType();
                    Object other$orderType = other.getOrderType();
                    if (this$orderType == null) {
                        if (other$orderType == null) {
                            break label72;
                        }
                    } else if (this$orderType.equals(other$orderType)) {
                        break label72;
                    }

                    return false;
                }

                Object this$orderStatus = this.getOrderStatus();
                Object other$orderStatus = other.getOrderStatus();
                if (this$orderStatus == null) {
                    if (other$orderStatus != null) {
                        return false;
                    }
                } else if (!this$orderStatus.equals(other$orderStatus)) {
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

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof WmsOrderOutStereoscopic;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $orderOutIdList = this.getOrderOutIdList();
        result = result * 59 + ($orderOutIdList == null ? 43 : $orderOutIdList.hashCode());
        Object $orderNoList = this.getOrderNoList();
        result = result * 59 + ($orderNoList == null ? 43 : $orderNoList.hashCode());
        Object $wmsOrderOutStereoscopicDeailList = this.getWmsOrderOutStereoscopicDeailList();
        result = result * 59 + ($wmsOrderOutStereoscopicDeailList == null ? 43 : $wmsOrderOutStereoscopicDeailList.hashCode());
        Object $orderOutId = this.getOrderOutId();
        result = result * 59 + ($orderOutId == null ? 43 : $orderOutId.hashCode());
        Object $orderNo = this.getOrderNo();
        result = result * 59 + ($orderNo == null ? 43 : $orderNo.hashCode());
        Object $orderType = this.getOrderType();
        result = result * 59 + ($orderType == null ? 43 : $orderType.hashCode());
        Object $orderStatus = this.getOrderStatus();
        result = result * 59 + ($orderStatus == null ? 43 : $orderStatus.hashCode());
        Object $outAddress = this.getOutAddress();
        result = result * 59 + ($outAddress == null ? 43 : $outAddress.hashCode());
        return result;
    }

    public String toString() {
        return "WmsOrderOutStereoscopic(orderOutIdList=" + this.getOrderOutIdList() + ", orderNoList=" + this.getOrderNoList() + ", wmsOrderOutStereoscopicDeailList=" + this.getWmsOrderOutStereoscopicDeailList() + ", orderOutId=" + this.getOrderOutId() + ", orderNo=" + this.getOrderNo() + ", sapOrderNo=" + this.getSapOrderNo() + ", orderType=" + this.getOrderType() + ", orderStatus=" + this.getOrderStatus() + ", outAddress=" + this.getOutAddress() + ")";
    }
}
