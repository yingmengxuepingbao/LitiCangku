//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.entity;

import java.util.List;

public class WmsOutTemporary extends BaseEntity {
    private List<String> idList;
    private String id;
    private String goodsName;
    private String goodsCode;
    private Integer amount;
    private Integer amountAdd;

    public WmsOutTemporary() {
    }

    public List<String> getIdList() {
        return this.idList;
    }

    public String getId() {
        return this.id;
    }

    public String getGoodsName() {
        return this.goodsName;
    }

    public String getGoodsCode() {
        return this.goodsCode;
    }

    public Integer getAmount() {
        return this.amount;
    }

    public Integer getAmountAdd() {
        return this.amountAdd;
    }

    public void setIdList(final List<String> idList) {
        this.idList = idList;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public void setGoodsName(final String goodsName) {
        this.goodsName = goodsName;
    }

    public void setGoodsCode(final String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public void setAmount(final Integer amount) {
        this.amount = amount;
    }

    public void setAmountAdd(final Integer amountAdd) {
        this.amountAdd = amountAdd;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof WmsOutTemporary)) {
            return false;
        } else {
            WmsOutTemporary other = (WmsOutTemporary)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$idList = this.getIdList();
                Object other$idList = other.getIdList();
                if (this$idList == null) {
                    if (other$idList != null) {
                        return false;
                    }
                } else if (!this$idList.equals(other$idList)) {
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

                Object this$goodsName = this.getGoodsName();
                Object other$goodsName = other.getGoodsName();
                if (this$goodsName == null) {
                    if (other$goodsName != null) {
                        return false;
                    }
                } else if (!this$goodsName.equals(other$goodsName)) {
                    return false;
                }

                label62: {
                    Object this$goodsCode = this.getGoodsCode();
                    Object other$goodsCode = other.getGoodsCode();
                    if (this$goodsCode == null) {
                        if (other$goodsCode == null) {
                            break label62;
                        }
                    } else if (this$goodsCode.equals(other$goodsCode)) {
                        break label62;
                    }

                    return false;
                }

                label55: {
                    Object this$amount = this.getAmount();
                    Object other$amount = other.getAmount();
                    if (this$amount == null) {
                        if (other$amount == null) {
                            break label55;
                        }
                    } else if (this$amount.equals(other$amount)) {
                        break label55;
                    }

                    return false;
                }

                Object this$amountAdd = this.getAmountAdd();
                Object other$amountAdd = other.getAmountAdd();
                if (this$amountAdd == null) {
                    if (other$amountAdd != null) {
                        return false;
                    }
                } else if (!this$amountAdd.equals(other$amountAdd)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof WmsOutTemporary;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $idList = this.getIdList();
        result = result * 59 + ($idList == null ? 43 : $idList.hashCode());
        Object $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        Object $goodsName = this.getGoodsName();
        result = result * 59 + ($goodsName == null ? 43 : $goodsName.hashCode());
        Object $goodsCode = this.getGoodsCode();
        result = result * 59 + ($goodsCode == null ? 43 : $goodsCode.hashCode());
        Object $amount = this.getAmount();
        result = result * 59 + ($amount == null ? 43 : $amount.hashCode());
        Object $amountAdd = this.getAmountAdd();
        result = result * 59 + ($amountAdd == null ? 43 : $amountAdd.hashCode());
        return result;
    }

    public String toString() {
        return "WmsOutTemporary(idList=" + this.getIdList() + ", id=" + this.getId() + ", goodsName=" + this.getGoodsName() + ", goodsCode=" + this.getGoodsCode() + ", amount=" + this.getAmount() + ", amountAdd=" + this.getAmountAdd() + ")";
    }
}
