//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.entity;

import com.alibaba.fastjson.annotation.JSONField;
import java.io.Serializable;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @JSONField(
            serialize = true
    )
    protected String orderBy;
    @JSONField(
            serialize = true
    )
    protected String createBy;
    @JSONField(
            serialize = true
    )
    protected Date gmtCreate;
    @JSONField(
            serialize = true
    )
    protected String gmtCreateStr;
    @JSONField(
            serialize = true
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    protected Date gmtCreateMax;
    @JSONField(
            serialize = true
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    protected Date gmtCreateMin;
    @JSONField(
            serialize = true
    )
    protected String lastModifiedBy;
    @JSONField(
            serialize = true
    )
    protected Date gmtModified;
    @JSONField(
            serialize = true
    )
    protected String gmtModifiedStr;
    @JSONField(
            serialize = true
    )
    protected Date gmtModifiedMax;
    @JSONField(
            serialize = true
    )
    protected Date gmtModifiedMin;
    protected String activeFlag;
    @JSONField(
            serialize = true
    )
    protected String remark;
    @JSONField(
            serialize = true
    )
    protected String userDefined1;
    @JSONField(
            serialize = true
    )
    protected String userDefined2;
    @JSONField(
            serialize = true
    )
    protected String userDefined3;
    @JSONField(
            serialize = true
    )
    protected String userDefined4;
    @JSONField(
            serialize = true
    )
    protected String userDefined5;

    public BaseEntity() {
    }

    public String getOrderBy() {
        return this.orderBy;
    }

    public String getCreateBy() {
        return this.createBy;
    }

    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    public String getGmtCreateStr() {
        return this.gmtCreateStr;
    }

    public Date getGmtCreateMax() {
        return this.gmtCreateMax;
    }

    public Date getGmtCreateMin() {
        return this.gmtCreateMin;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Date getGmtModified() {
        return this.gmtModified;
    }

    public String getGmtModifiedStr() {
        return this.gmtModifiedStr;
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

    public String getRemark() {
        return this.remark;
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

    public void setOrderBy(final String orderBy) {
        this.orderBy = orderBy;
    }

    public void setCreateBy(final String createBy) {
        this.createBy = createBy;
    }

    public void setGmtCreate(final Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public void setGmtCreateStr(final String gmtCreateStr) {
        this.gmtCreateStr = gmtCreateStr;
    }

    public void setGmtCreateMax(final Date gmtCreateMax) {
        this.gmtCreateMax = gmtCreateMax;
    }

    public void setGmtCreateMin(final Date gmtCreateMin) {
        this.gmtCreateMin = gmtCreateMin;
    }

    public void setLastModifiedBy(final String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public void setGmtModified(final Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public void setGmtModifiedStr(final String gmtModifiedStr) {
        this.gmtModifiedStr = gmtModifiedStr;
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

    public void setRemark(final String remark) {
        this.remark = remark;
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

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BaseEntity)) {
            return false;
        } else {
            BaseEntity other = (BaseEntity)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$orderBy = this.getOrderBy();
                Object other$orderBy = other.getOrderBy();
                if (this$orderBy == null) {
                    if (other$orderBy != null) {
                        return false;
                    }
                } else if (!this$orderBy.equals(other$orderBy)) {
                    return false;
                }

                Object this$createBy = this.getCreateBy();
                Object other$createBy = other.getCreateBy();
                if (this$createBy == null) {
                    if (other$createBy != null) {
                        return false;
                    }
                } else if (!this$createBy.equals(other$createBy)) {
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

                label206: {
                    Object this$gmtCreateStr = this.getGmtCreateStr();
                    Object other$gmtCreateStr = other.getGmtCreateStr();
                    if (this$gmtCreateStr == null) {
                        if (other$gmtCreateStr == null) {
                            break label206;
                        }
                    } else if (this$gmtCreateStr.equals(other$gmtCreateStr)) {
                        break label206;
                    }

                    return false;
                }

                label199: {
                    Object this$gmtCreateMax = this.getGmtCreateMax();
                    Object other$gmtCreateMax = other.getGmtCreateMax();
                    if (this$gmtCreateMax == null) {
                        if (other$gmtCreateMax == null) {
                            break label199;
                        }
                    } else if (this$gmtCreateMax.equals(other$gmtCreateMax)) {
                        break label199;
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

                label185: {
                    Object this$lastModifiedBy = this.getLastModifiedBy();
                    Object other$lastModifiedBy = other.getLastModifiedBy();
                    if (this$lastModifiedBy == null) {
                        if (other$lastModifiedBy == null) {
                            break label185;
                        }
                    } else if (this$lastModifiedBy.equals(other$lastModifiedBy)) {
                        break label185;
                    }

                    return false;
                }

                label178: {
                    Object this$gmtModified = this.getGmtModified();
                    Object other$gmtModified = other.getGmtModified();
                    if (this$gmtModified == null) {
                        if (other$gmtModified == null) {
                            break label178;
                        }
                    } else if (this$gmtModified.equals(other$gmtModified)) {
                        break label178;
                    }

                    return false;
                }

                Object this$gmtModifiedStr = this.getGmtModifiedStr();
                Object other$gmtModifiedStr = other.getGmtModifiedStr();
                if (this$gmtModifiedStr == null) {
                    if (other$gmtModifiedStr != null) {
                        return false;
                    }
                } else if (!this$gmtModifiedStr.equals(other$gmtModifiedStr)) {
                    return false;
                }

                Object this$gmtModifiedMax = this.getGmtModifiedMax();
                Object other$gmtModifiedMax = other.getGmtModifiedMax();
                if (this$gmtModifiedMax == null) {
                    if (other$gmtModifiedMax != null) {
                        return false;
                    }
                } else if (!this$gmtModifiedMax.equals(other$gmtModifiedMax)) {
                    return false;
                }

                label157: {
                    Object this$gmtModifiedMin = this.getGmtModifiedMin();
                    Object other$gmtModifiedMin = other.getGmtModifiedMin();
                    if (this$gmtModifiedMin == null) {
                        if (other$gmtModifiedMin == null) {
                            break label157;
                        }
                    } else if (this$gmtModifiedMin.equals(other$gmtModifiedMin)) {
                        break label157;
                    }

                    return false;
                }

                label150: {
                    Object this$activeFlag = this.getActiveFlag();
                    Object other$activeFlag = other.getActiveFlag();
                    if (this$activeFlag == null) {
                        if (other$activeFlag == null) {
                            break label150;
                        }
                    } else if (this$activeFlag.equals(other$activeFlag)) {
                        break label150;
                    }

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

                label136: {
                    Object this$userDefined1 = this.getUserDefined1();
                    Object other$userDefined1 = other.getUserDefined1();
                    if (this$userDefined1 == null) {
                        if (other$userDefined1 == null) {
                            break label136;
                        }
                    } else if (this$userDefined1.equals(other$userDefined1)) {
                        break label136;
                    }

                    return false;
                }

                Object this$userDefined2 = this.getUserDefined2();
                Object other$userDefined2 = other.getUserDefined2();
                if (this$userDefined2 == null) {
                    if (other$userDefined2 != null) {
                        return false;
                    }
                } else if (!this$userDefined2.equals(other$userDefined2)) {
                    return false;
                }

                label122: {
                    Object this$userDefined3 = this.getUserDefined3();
                    Object other$userDefined3 = other.getUserDefined3();
                    if (this$userDefined3 == null) {
                        if (other$userDefined3 == null) {
                            break label122;
                        }
                    } else if (this$userDefined3.equals(other$userDefined3)) {
                        break label122;
                    }

                    return false;
                }

                Object this$userDefined4 = this.getUserDefined4();
                Object other$userDefined4 = other.getUserDefined4();
                if (this$userDefined4 == null) {
                    if (other$userDefined4 != null) {
                        return false;
                    }
                } else if (!this$userDefined4.equals(other$userDefined4)) {
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

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BaseEntity;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $orderBy = this.getOrderBy();
        result = result * 59 + ($orderBy == null ? 43 : $orderBy.hashCode());
        Object $createBy = this.getCreateBy();
        result = result * 59 + ($createBy == null ? 43 : $createBy.hashCode());
        Object $gmtCreate = this.getGmtCreate();
        result = result * 59 + ($gmtCreate == null ? 43 : $gmtCreate.hashCode());
        Object $gmtCreateStr = this.getGmtCreateStr();
        result = result * 59 + ($gmtCreateStr == null ? 43 : $gmtCreateStr.hashCode());
        Object $gmtCreateMax = this.getGmtCreateMax();
        result = result * 59 + ($gmtCreateMax == null ? 43 : $gmtCreateMax.hashCode());
        Object $gmtCreateMin = this.getGmtCreateMin();
        result = result * 59 + ($gmtCreateMin == null ? 43 : $gmtCreateMin.hashCode());
        Object $lastModifiedBy = this.getLastModifiedBy();
        result = result * 59 + ($lastModifiedBy == null ? 43 : $lastModifiedBy.hashCode());
        Object $gmtModified = this.getGmtModified();
        result = result * 59 + ($gmtModified == null ? 43 : $gmtModified.hashCode());
        Object $gmtModifiedStr = this.getGmtModifiedStr();
        result = result * 59 + ($gmtModifiedStr == null ? 43 : $gmtModifiedStr.hashCode());
        Object $gmtModifiedMax = this.getGmtModifiedMax();
        result = result * 59 + ($gmtModifiedMax == null ? 43 : $gmtModifiedMax.hashCode());
        Object $gmtModifiedMin = this.getGmtModifiedMin();
        result = result * 59 + ($gmtModifiedMin == null ? 43 : $gmtModifiedMin.hashCode());
        Object $activeFlag = this.getActiveFlag();
        result = result * 59 + ($activeFlag == null ? 43 : $activeFlag.hashCode());
        Object $remark = this.getRemark();
        result = result * 59 + ($remark == null ? 43 : $remark.hashCode());
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
        return result;
    }

    public String toString() {
        return "BaseEntity(orderBy=" + this.getOrderBy() + ", createBy=" + this.getCreateBy() + ", gmtCreate=" + this.getGmtCreate() + ", gmtCreateStr=" + this.getGmtCreateStr() + ", gmtCreateMax=" + this.getGmtCreateMax() + ", gmtCreateMin=" + this.getGmtCreateMin() + ", lastModifiedBy=" + this.getLastModifiedBy() + ", gmtModified=" + this.getGmtModified() + ", gmtModifiedStr=" + this.getGmtModifiedStr() + ", gmtModifiedMax=" + this.getGmtModifiedMax() + ", gmtModifiedMin=" + this.getGmtModifiedMin() + ", activeFlag=" + this.getActiveFlag() + ", remark=" + this.getRemark() + ", userDefined1=" + this.getUserDefined1() + ", userDefined2=" + this.getUserDefined2() + ", userDefined3=" + this.getUserDefined3() + ", userDefined4=" + this.getUserDefined4() + ", userDefined5=" + this.getUserDefined5() + ")";
    }
}
