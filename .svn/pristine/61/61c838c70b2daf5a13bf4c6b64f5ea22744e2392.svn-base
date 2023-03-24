//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.reportmanagement.model.dto;

import com.penghaisoft.wms.storagemanagement.model.entity.WmsPallet;
import java.util.List;

public class StorageStatisticCount {
    private Integer isUsedLocCount;
    private Integer notUsedLocCount;
    private List<WmsPallet> longGoodsInfo;

    public StorageStatisticCount() {
    }

    public Integer getIsUsedLocCount() {
        return this.isUsedLocCount;
    }

    public Integer getNotUsedLocCount() {
        return this.notUsedLocCount;
    }

    public List<WmsPallet> getLongGoodsInfo() {
        return this.longGoodsInfo;
    }

    public void setIsUsedLocCount(final Integer isUsedLocCount) {
        this.isUsedLocCount = isUsedLocCount;
    }

    public void setNotUsedLocCount(final Integer notUsedLocCount) {
        this.notUsedLocCount = notUsedLocCount;
    }

    public void setLongGoodsInfo(final List<WmsPallet> longGoodsInfo) {
        this.longGoodsInfo = longGoodsInfo;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof StorageStatisticCount)) {
            return false;
        } else {
            StorageStatisticCount other = (StorageStatisticCount)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47: {
                    Object this$isUsedLocCount = this.getIsUsedLocCount();
                    Object other$isUsedLocCount = other.getIsUsedLocCount();
                    if (this$isUsedLocCount == null) {
                        if (other$isUsedLocCount == null) {
                            break label47;
                        }
                    } else if (this$isUsedLocCount.equals(other$isUsedLocCount)) {
                        break label47;
                    }

                    return false;
                }

                Object this$notUsedLocCount = this.getNotUsedLocCount();
                Object other$notUsedLocCount = other.getNotUsedLocCount();
                if (this$notUsedLocCount == null) {
                    if (other$notUsedLocCount != null) {
                        return false;
                    }
                } else if (!this$notUsedLocCount.equals(other$notUsedLocCount)) {
                    return false;
                }

                Object this$longGoodsInfo = this.getLongGoodsInfo();
                Object other$longGoodsInfo = other.getLongGoodsInfo();
                if (this$longGoodsInfo == null) {
                    if (other$longGoodsInfo != null) {
                        return false;
                    }
                } else if (!this$longGoodsInfo.equals(other$longGoodsInfo)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof StorageStatisticCount;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $isUsedLocCount = this.getIsUsedLocCount();
        result = result * 59 + ($isUsedLocCount == null ? 43 : $isUsedLocCount.hashCode());
        Object $notUsedLocCount = this.getNotUsedLocCount();
        result = result * 59 + ($notUsedLocCount == null ? 43 : $notUsedLocCount.hashCode());
        Object $longGoodsInfo = this.getLongGoodsInfo();
        result = result * 59 + ($longGoodsInfo == null ? 43 : $longGoodsInfo.hashCode());
        return result;
    }

    public String toString() {
        return "StorageStatisticCount(isUsedLocCount=" + this.getIsUsedLocCount() + ", notUsedLocCount=" + this.getNotUsedLocCount() + ", longGoodsInfo=" + this.getLongGoodsInfo() + ")";
    }
}
