//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.entity;

import java.util.List;

public class WmsOutTemporaryBoxBarcode extends BaseEntity {
    private List<String> idList;
    private String id;
    private String orderNo;
    private String sendNo;
    private String goodsName;
    private String goodsCode;
    private String batchNo;
    private String boxBarcode;

    public WmsOutTemporaryBoxBarcode() {
    }

    public List<String> getIdList() {
        return this.idList;
    }

    public String getId() {
        return this.id;
    }

    public String getOrderNo() {
        return this.orderNo;
    }

    public String getSendNo() {
        return this.sendNo;
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

    public String getBoxBarcode() {
        return this.boxBarcode;
    }

    public void setIdList(final List<String> idList) {
        this.idList = idList;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public void setOrderNo(final String orderNo) {
        this.orderNo = orderNo;
    }

    public void setSendNo(final String sendNo) {
        this.sendNo = sendNo;
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

    public void setBoxBarcode(final String boxBarcode) {
        this.boxBarcode = boxBarcode;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof WmsOutTemporaryBoxBarcode)) {
            return false;
        } else {
            WmsOutTemporaryBoxBarcode other = (WmsOutTemporaryBoxBarcode)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label107: {
                    Object this$idList = this.getIdList();
                    Object other$idList = other.getIdList();
                    if (this$idList == null) {
                        if (other$idList == null) {
                            break label107;
                        }
                    } else if (this$idList.equals(other$idList)) {
                        break label107;
                    }

                    return false;
                }

                Object this$id = this.getId();
                Object other$id = other.getId();
                if (this$id == null) {
                    if (other$id != null) {
                        return false;
                    }
                } else if (!this$id.equals(other$id)) {
                    return false;
                }

                Object this$orderNo = this.getOrderNo();
                Object other$orderNo = other.getOrderNo();
                if (this$orderNo == null) {
                    if (other$orderNo != null) {
                        return false;
                    }
                } else if (!this$orderNo.equals(other$orderNo)) {
                    return false;
                }

                label86: {
                    Object this$sendNo = this.getSendNo();
                    Object other$sendNo = other.getSendNo();
                    if (this$sendNo == null) {
                        if (other$sendNo == null) {
                            break label86;
                        }
                    } else if (this$sendNo.equals(other$sendNo)) {
                        break label86;
                    }

                    return false;
                }

                label79: {
                    Object this$goodsName = this.getGoodsName();
                    Object other$goodsName = other.getGoodsName();
                    if (this$goodsName == null) {
                        if (other$goodsName == null) {
                            break label79;
                        }
                    } else if (this$goodsName.equals(other$goodsName)) {
                        break label79;
                    }

                    return false;
                }

                label72: {
                    Object this$goodsCode = this.getGoodsCode();
                    Object other$goodsCode = other.getGoodsCode();
                    if (this$goodsCode == null) {
                        if (other$goodsCode == null) {
                            break label72;
                        }
                    } else if (this$goodsCode.equals(other$goodsCode)) {
                        break label72;
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

                Object this$boxBarcode = this.getBoxBarcode();
                Object other$boxBarcode = other.getBoxBarcode();
                if (this$boxBarcode == null) {
                    if (other$boxBarcode != null) {
                        return false;
                    }
                } else if (!this$boxBarcode.equals(other$boxBarcode)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof WmsOutTemporaryBoxBarcode;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $idList = this.getIdList();
        result = result * 59 + ($idList == null ? 43 : $idList.hashCode());
        Object $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        Object $orderNo = this.getOrderNo();
        result = result * 59 + ($orderNo == null ? 43 : $orderNo.hashCode());
        Object $sendNo = this.getSendNo();
        result = result * 59 + ($sendNo == null ? 43 : $sendNo.hashCode());
        Object $goodsName = this.getGoodsName();
        result = result * 59 + ($goodsName == null ? 43 : $goodsName.hashCode());
        Object $goodsCode = this.getGoodsCode();
        result = result * 59 + ($goodsCode == null ? 43 : $goodsCode.hashCode());
        Object $batchNo = this.getBatchNo();
        result = result * 59 + ($batchNo == null ? 43 : $batchNo.hashCode());
        Object $boxBarcode = this.getBoxBarcode();
        result = result * 59 + ($boxBarcode == null ? 43 : $boxBarcode.hashCode());
        return result;
    }

    public String toString() {
        return "WmsOutTemporaryBoxBarcode(idList=" + this.getIdList() + ", id=" + this.getId() + ", orderNo=" + this.getOrderNo() + ", sendNo=" + this.getSendNo() + ", goodsName=" + this.getGoodsName() + ", goodsCode=" + this.getGoodsCode() + ", batchNo=" + this.getBatchNo() + ", boxBarcode=" + this.getBoxBarcode() + ")";
    }
}
