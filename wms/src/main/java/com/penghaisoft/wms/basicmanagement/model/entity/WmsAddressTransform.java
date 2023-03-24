//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.model.entity;

import com.penghaisoft.wms.storagemanagement.model.entity.BaseEntity;
import java.util.List;

public class WmsAddressTransform extends BaseEntity {
    private List<String> transformIdList;
    private Integer transformId;
    private String addressType;
    private Integer relaId;

    public WmsAddressTransform() {
    }

    public List<String> getTransformIdList() {
        return this.transformIdList;
    }

    public Integer getTransformId() {
        return this.transformId;
    }

    public String getAddressType() {
        return this.addressType;
    }

    public Integer getRelaId() {
        return this.relaId;
    }

    public void setTransformIdList(final List<String> transformIdList) {
        this.transformIdList = transformIdList;
    }

    public void setTransformId(final Integer transformId) {
        this.transformId = transformId;
    }

    public void setAddressType(final String addressType) {
        this.addressType = addressType;
    }

    public void setRelaId(final Integer relaId) {
        this.relaId = relaId;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof WmsAddressTransform)) {
            return false;
        } else {
            WmsAddressTransform other = (WmsAddressTransform)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label59: {
                    Object this$transformIdList = this.getTransformIdList();
                    Object other$transformIdList = other.getTransformIdList();
                    if (this$transformIdList == null) {
                        if (other$transformIdList == null) {
                            break label59;
                        }
                    } else if (this$transformIdList.equals(other$transformIdList)) {
                        break label59;
                    }

                    return false;
                }

                Object this$transformId = this.getTransformId();
                Object other$transformId = other.getTransformId();
                if (this$transformId == null) {
                    if (other$transformId != null) {
                        return false;
                    }
                } else if (!this$transformId.equals(other$transformId)) {
                    return false;
                }

                Object this$addressType = this.getAddressType();
                Object other$addressType = other.getAddressType();
                if (this$addressType == null) {
                    if (other$addressType != null) {
                        return false;
                    }
                } else if (!this$addressType.equals(other$addressType)) {
                    return false;
                }

                Object this$relaId = this.getRelaId();
                Object other$relaId = other.getRelaId();
                if (this$relaId == null) {
                    if (other$relaId != null) {
                        return false;
                    }
                } else if (!this$relaId.equals(other$relaId)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof WmsAddressTransform;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $transformIdList = this.getTransformIdList();
        result = result * 59 + ($transformIdList == null ? 43 : $transformIdList.hashCode());
        Object $transformId = this.getTransformId();
        result = result * 59 + ($transformId == null ? 43 : $transformId.hashCode());
        Object $addressType = this.getAddressType();
        result = result * 59 + ($addressType == null ? 43 : $addressType.hashCode());
        Object $relaId = this.getRelaId();
        result = result * 59 + ($relaId == null ? 43 : $relaId.hashCode());
        return result;
    }

    public String toString() {
        return "WmsAddressTransform(transformIdList=" + this.getTransformIdList() + ", transformId=" + this.getTransformId() + ", addressType=" + this.getAddressType() + ", relaId=" + this.getRelaId() + ")";
    }
}
