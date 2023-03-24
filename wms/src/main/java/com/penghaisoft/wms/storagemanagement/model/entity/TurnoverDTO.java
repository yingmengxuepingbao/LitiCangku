//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.entity;

public class TurnoverDTO {
    String goodsCode;
    String goodsName;
    String batchNo;
    String inBoundCount;
    String outBoundCount;
    Integer inBoundBoxCount;
    Integer outBoundBoxCount;
    String outInBoundCount;
    Integer outInBoundBoxCount;

    public TurnoverDTO() {
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

    public String getInBoundCount() {
        return this.inBoundCount;
    }

    public String getOutBoundCount() {
        return this.outBoundCount;
    }

    public Integer getInBoundBoxCount() {
        return this.inBoundBoxCount;
    }

    public Integer getOutBoundBoxCount() {
        return this.outBoundBoxCount;
    }

    public String getOutInBoundCount() {
        return this.outInBoundCount;
    }

    public Integer getOutInBoundBoxCount() {
        return this.outInBoundBoxCount;
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

    public void setInBoundCount(final String inBoundCount) {
        this.inBoundCount = inBoundCount;
    }

    public void setOutBoundCount(final String outBoundCount) {
        this.outBoundCount = outBoundCount;
    }

    public void setInBoundBoxCount(final Integer inBoundBoxCount) {
        this.inBoundBoxCount = inBoundBoxCount;
    }

    public void setOutBoundBoxCount(final Integer outBoundBoxCount) {
        this.outBoundBoxCount = outBoundBoxCount;
    }

    public void setOutInBoundCount(final String outInBoundCount) {
        this.outInBoundCount = outInBoundCount;
    }

    public void setOutInBoundBoxCount(final Integer outInBoundBoxCount) {
        this.outInBoundBoxCount = outInBoundBoxCount;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof TurnoverDTO)) {
            return false;
        } else {
            TurnoverDTO other = (TurnoverDTO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label119: {
                    Object this$goodsCode = this.getGoodsCode();
                    Object other$goodsCode = other.getGoodsCode();
                    if (this$goodsCode == null) {
                        if (other$goodsCode == null) {
                            break label119;
                        }
                    } else if (this$goodsCode.equals(other$goodsCode)) {
                        break label119;
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

                label105: {
                    Object this$batchNo = this.getBatchNo();
                    Object other$batchNo = other.getBatchNo();
                    if (this$batchNo == null) {
                        if (other$batchNo == null) {
                            break label105;
                        }
                    } else if (this$batchNo.equals(other$batchNo)) {
                        break label105;
                    }

                    return false;
                }

                Object this$inBoundCount = this.getInBoundCount();
                Object other$inBoundCount = other.getInBoundCount();
                if (this$inBoundCount == null) {
                    if (other$inBoundCount != null) {
                        return false;
                    }
                } else if (!this$inBoundCount.equals(other$inBoundCount)) {
                    return false;
                }

                label91: {
                    Object this$outBoundCount = this.getOutBoundCount();
                    Object other$outBoundCount = other.getOutBoundCount();
                    if (this$outBoundCount == null) {
                        if (other$outBoundCount == null) {
                            break label91;
                        }
                    } else if (this$outBoundCount.equals(other$outBoundCount)) {
                        break label91;
                    }

                    return false;
                }

                Object this$inBoundBoxCount = this.getInBoundBoxCount();
                Object other$inBoundBoxCount = other.getInBoundBoxCount();
                if (this$inBoundBoxCount == null) {
                    if (other$inBoundBoxCount != null) {
                        return false;
                    }
                } else if (!this$inBoundBoxCount.equals(other$inBoundBoxCount)) {
                    return false;
                }

                label77: {
                    Object this$outBoundBoxCount = this.getOutBoundBoxCount();
                    Object other$outBoundBoxCount = other.getOutBoundBoxCount();
                    if (this$outBoundBoxCount == null) {
                        if (other$outBoundBoxCount == null) {
                            break label77;
                        }
                    } else if (this$outBoundBoxCount.equals(other$outBoundBoxCount)) {
                        break label77;
                    }

                    return false;
                }

                label70: {
                    Object this$outInBoundCount = this.getOutInBoundCount();
                    Object other$outInBoundCount = other.getOutInBoundCount();
                    if (this$outInBoundCount == null) {
                        if (other$outInBoundCount == null) {
                            break label70;
                        }
                    } else if (this$outInBoundCount.equals(other$outInBoundCount)) {
                        break label70;
                    }

                    return false;
                }

                Object this$outInBoundBoxCount = this.getOutInBoundBoxCount();
                Object other$outInBoundBoxCount = other.getOutInBoundBoxCount();
                if (this$outInBoundBoxCount == null) {
                    if (other$outInBoundBoxCount != null) {
                        return false;
                    }
                } else if (!this$outInBoundBoxCount.equals(other$outInBoundBoxCount)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TurnoverDTO;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $goodsCode = this.getGoodsCode();
        result = result * 59 + ($goodsCode == null ? 43 : $goodsCode.hashCode());
        Object $goodsName = this.getGoodsName();
        result = result * 59 + ($goodsName == null ? 43 : $goodsName.hashCode());
        Object $batchNo = this.getBatchNo();
        result = result * 59 + ($batchNo == null ? 43 : $batchNo.hashCode());
        Object $inBoundCount = this.getInBoundCount();
        result = result * 59 + ($inBoundCount == null ? 43 : $inBoundCount.hashCode());
        Object $outBoundCount = this.getOutBoundCount();
        result = result * 59 + ($outBoundCount == null ? 43 : $outBoundCount.hashCode());
        Object $inBoundBoxCount = this.getInBoundBoxCount();
        result = result * 59 + ($inBoundBoxCount == null ? 43 : $inBoundBoxCount.hashCode());
        Object $outBoundBoxCount = this.getOutBoundBoxCount();
        result = result * 59 + ($outBoundBoxCount == null ? 43 : $outBoundBoxCount.hashCode());
        Object $outInBoundCount = this.getOutInBoundCount();
        result = result * 59 + ($outInBoundCount == null ? 43 : $outInBoundCount.hashCode());
        Object $outInBoundBoxCount = this.getOutInBoundBoxCount();
        result = result * 59 + ($outInBoundBoxCount == null ? 43 : $outInBoundBoxCount.hashCode());
        return result;
    }

    public String toString() {
        return "TurnoverDTO(goodsCode=" + this.getGoodsCode() + ", goodsName=" + this.getGoodsName() + ", batchNo=" + this.getBatchNo() + ", inBoundCount=" + this.getInBoundCount() + ", outBoundCount=" + this.getOutBoundCount() + ", inBoundBoxCount=" + this.getInBoundBoxCount() + ", outBoundBoxCount=" + this.getOutBoundBoxCount() + ", outInBoundCount=" + this.getOutInBoundCount() + ", outInBoundBoxCount=" + this.getOutInBoundBoxCount() + ")";
    }
}
