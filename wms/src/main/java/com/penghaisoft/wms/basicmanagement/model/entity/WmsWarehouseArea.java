//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.model.entity;

import java.util.Date;
import java.util.List;

public class WmsWarehouseArea {
    private List<String> areaIdList;
    private String areaId;
    private String warehouseCode;
    private String areaName;
    private String areaCode;
    private String areaType;
    private String areaAttr;
    private Integer maxCapacity;
    private String areaDesc;
    private String userDefined1;
    private String userDefined2;
    private String userDefined3;
    private String userDefined4;
    private String userDefined5;
    private int startNumber;
    private int numberOnePage;
    private String createBy;
    private String remark;
    private Date gmtCreate;
    private Date gmtCreateMin;
    private Date gmtCreateMax;
    private String lastModifiedBy;
    private Date gmtModified;
    private Date gmtModifiedMax;
    private Date gmtModifiedMin;
    private String activeFlag;
    private String attrShow;

    public WmsWarehouseArea() {
    }

    public List<String> getAreaIdList() {
        return this.areaIdList;
    }

    public String getAreaId() {
        return this.areaId;
    }

    public String getWarehouseCode() {
        return this.warehouseCode;
    }

    public String getAreaName() {
        return this.areaName;
    }

    public String getAreaCode() {
        return this.areaCode;
    }

    public String getAreaType() {
        return this.areaType;
    }

    public String getAreaAttr() {
        return this.areaAttr;
    }

    public Integer getMaxCapacity() {
        return this.maxCapacity;
    }

    public String getAreaDesc() {
        return this.areaDesc;
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

    public String getAttrShow() {
        return this.attrShow;
    }

    public void setAreaIdList(final List<String> areaIdList) {
        this.areaIdList = areaIdList;
    }

    public void setAreaId(final String areaId) {
        this.areaId = areaId;
    }

    public void setWarehouseCode(final String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public void setAreaName(final String areaName) {
        this.areaName = areaName;
    }

    public void setAreaCode(final String areaCode) {
        this.areaCode = areaCode;
    }

    public void setAreaType(final String areaType) {
        this.areaType = areaType;
    }

    public void setAreaAttr(final String areaAttr) {
        this.areaAttr = areaAttr;
    }

    public void setMaxCapacity(final Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void setAreaDesc(final String areaDesc) {
        this.areaDesc = areaDesc;
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

    public void setAttrShow(final String attrShow) {
        this.attrShow = attrShow;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof WmsWarehouseArea)) {
            return false;
        } else {
            WmsWarehouseArea other = (WmsWarehouseArea)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label319: {
                    Object this$areaIdList = this.getAreaIdList();
                    Object other$areaIdList = other.getAreaIdList();
                    if (this$areaIdList == null) {
                        if (other$areaIdList == null) {
                            break label319;
                        }
                    } else if (this$areaIdList.equals(other$areaIdList)) {
                        break label319;
                    }

                    return false;
                }

                Object this$areaId = this.getAreaId();
                Object other$areaId = other.getAreaId();
                if (this$areaId == null) {
                    if (other$areaId != null) {
                        return false;
                    }
                } else if (!this$areaId.equals(other$areaId)) {
                    return false;
                }

                Object this$warehouseCode = this.getWarehouseCode();
                Object other$warehouseCode = other.getWarehouseCode();
                if (this$warehouseCode == null) {
                    if (other$warehouseCode != null) {
                        return false;
                    }
                } else if (!this$warehouseCode.equals(other$warehouseCode)) {
                    return false;
                }

                label298: {
                    Object this$areaName = this.getAreaName();
                    Object other$areaName = other.getAreaName();
                    if (this$areaName == null) {
                        if (other$areaName == null) {
                            break label298;
                        }
                    } else if (this$areaName.equals(other$areaName)) {
                        break label298;
                    }

                    return false;
                }

                label291: {
                    Object this$areaCode = this.getAreaCode();
                    Object other$areaCode = other.getAreaCode();
                    if (this$areaCode == null) {
                        if (other$areaCode == null) {
                            break label291;
                        }
                    } else if (this$areaCode.equals(other$areaCode)) {
                        break label291;
                    }

                    return false;
                }

                Object this$areaType = this.getAreaType();
                Object other$areaType = other.getAreaType();
                if (this$areaType == null) {
                    if (other$areaType != null) {
                        return false;
                    }
                } else if (!this$areaType.equals(other$areaType)) {
                    return false;
                }

                Object this$areaAttr = this.getAreaAttr();
                Object other$areaAttr = other.getAreaAttr();
                if (this$areaAttr == null) {
                    if (other$areaAttr != null) {
                        return false;
                    }
                } else if (!this$areaAttr.equals(other$areaAttr)) {
                    return false;
                }

                label270: {
                    Object this$maxCapacity = this.getMaxCapacity();
                    Object other$maxCapacity = other.getMaxCapacity();
                    if (this$maxCapacity == null) {
                        if (other$maxCapacity == null) {
                            break label270;
                        }
                    } else if (this$maxCapacity.equals(other$maxCapacity)) {
                        break label270;
                    }

                    return false;
                }

                label263: {
                    Object this$areaDesc = this.getAreaDesc();
                    Object other$areaDesc = other.getAreaDesc();
                    if (this$areaDesc == null) {
                        if (other$areaDesc == null) {
                            break label263;
                        }
                    } else if (this$areaDesc.equals(other$areaDesc)) {
                        break label263;
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

                label249: {
                    Object this$userDefined2 = this.getUserDefined2();
                    Object other$userDefined2 = other.getUserDefined2();
                    if (this$userDefined2 == null) {
                        if (other$userDefined2 == null) {
                            break label249;
                        }
                    } else if (this$userDefined2.equals(other$userDefined2)) {
                        break label249;
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

                label235: {
                    Object this$userDefined4 = this.getUserDefined4();
                    Object other$userDefined4 = other.getUserDefined4();
                    if (this$userDefined4 == null) {
                        if (other$userDefined4 == null) {
                            break label235;
                        }
                    } else if (this$userDefined4.equals(other$userDefined4)) {
                        break label235;
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

                if (this.getStartNumber() != other.getStartNumber()) {
                    return false;
                } else if (this.getNumberOnePage() != other.getNumberOnePage()) {
                    return false;
                } else {
                    label218: {
                        Object this$createBy = this.getCreateBy();
                        Object other$createBy = other.getCreateBy();
                        if (this$createBy == null) {
                            if (other$createBy == null) {
                                break label218;
                            }
                        } else if (this$createBy.equals(other$createBy)) {
                            break label218;
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

                    label204: {
                        Object this$gmtCreate = this.getGmtCreate();
                        Object other$gmtCreate = other.getGmtCreate();
                        if (this$gmtCreate == null) {
                            if (other$gmtCreate == null) {
                                break label204;
                            }
                        } else if (this$gmtCreate.equals(other$gmtCreate)) {
                            break label204;
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

                    label190: {
                        Object this$gmtCreateMax = this.getGmtCreateMax();
                        Object other$gmtCreateMax = other.getGmtCreateMax();
                        if (this$gmtCreateMax == null) {
                            if (other$gmtCreateMax == null) {
                                break label190;
                            }
                        } else if (this$gmtCreateMax.equals(other$gmtCreateMax)) {
                            break label190;
                        }

                        return false;
                    }

                    label183: {
                        Object this$lastModifiedBy = this.getLastModifiedBy();
                        Object other$lastModifiedBy = other.getLastModifiedBy();
                        if (this$lastModifiedBy == null) {
                            if (other$lastModifiedBy == null) {
                                break label183;
                            }
                        } else if (this$lastModifiedBy.equals(other$lastModifiedBy)) {
                            break label183;
                        }

                        return false;
                    }

                    Object this$gmtModified = this.getGmtModified();
                    Object other$gmtModified = other.getGmtModified();
                    if (this$gmtModified == null) {
                        if (other$gmtModified != null) {
                            return false;
                        }
                    } else if (!this$gmtModified.equals(other$gmtModified)) {
                        return false;
                    }

                    label169: {
                        Object this$gmtModifiedMax = this.getGmtModifiedMax();
                        Object other$gmtModifiedMax = other.getGmtModifiedMax();
                        if (this$gmtModifiedMax == null) {
                            if (other$gmtModifiedMax == null) {
                                break label169;
                            }
                        } else if (this$gmtModifiedMax.equals(other$gmtModifiedMax)) {
                            break label169;
                        }

                        return false;
                    }

                    label162: {
                        Object this$gmtModifiedMin = this.getGmtModifiedMin();
                        Object other$gmtModifiedMin = other.getGmtModifiedMin();
                        if (this$gmtModifiedMin == null) {
                            if (other$gmtModifiedMin == null) {
                                break label162;
                            }
                        } else if (this$gmtModifiedMin.equals(other$gmtModifiedMin)) {
                            break label162;
                        }

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

                    Object this$attrShow = this.getAttrShow();
                    Object other$attrShow = other.getAttrShow();
                    if (this$attrShow == null) {
                        if (other$attrShow != null) {
                            return false;
                        }
                    } else if (!this$attrShow.equals(other$attrShow)) {
                        return false;
                    }

                    return true;
                }
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof WmsWarehouseArea;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $areaIdList = this.getAreaIdList();
        result = result * 59 + ($areaIdList == null ? 43 : $areaIdList.hashCode());
        Object $areaId = this.getAreaId();
        result = result * 59 + ($areaId == null ? 43 : $areaId.hashCode());
        Object $warehouseCode = this.getWarehouseCode();
        result = result * 59 + ($warehouseCode == null ? 43 : $warehouseCode.hashCode());
        Object $areaName = this.getAreaName();
        result = result * 59 + ($areaName == null ? 43 : $areaName.hashCode());
        Object $areaCode = this.getAreaCode();
        result = result * 59 + ($areaCode == null ? 43 : $areaCode.hashCode());
        Object $areaType = this.getAreaType();
        result = result * 59 + ($areaType == null ? 43 : $areaType.hashCode());
        Object $areaAttr = this.getAreaAttr();
        result = result * 59 + ($areaAttr == null ? 43 : $areaAttr.hashCode());
        Object $maxCapacity = this.getMaxCapacity();
        result = result * 59 + ($maxCapacity == null ? 43 : $maxCapacity.hashCode());
        Object $areaDesc = this.getAreaDesc();
        result = result * 59 + ($areaDesc == null ? 43 : $areaDesc.hashCode());
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
        Object $attrShow = this.getAttrShow();
        result = result * 59 + ($attrShow == null ? 43 : $attrShow.hashCode());
        return result;
    }

    public String toString() {
        return "WmsWarehouseArea(areaIdList=" + this.getAreaIdList() + ", areaId=" + this.getAreaId() + ", warehouseCode=" + this.getWarehouseCode() + ", areaName=" + this.getAreaName() + ", areaCode=" + this.getAreaCode() + ", areaType=" + this.getAreaType() + ", areaAttr=" + this.getAreaAttr() + ", maxCapacity=" + this.getMaxCapacity() + ", areaDesc=" + this.getAreaDesc() + ", userDefined1=" + this.getUserDefined1() + ", userDefined2=" + this.getUserDefined2() + ", userDefined3=" + this.getUserDefined3() + ", userDefined4=" + this.getUserDefined4() + ", userDefined5=" + this.getUserDefined5() + ", startNumber=" + this.getStartNumber() + ", numberOnePage=" + this.getNumberOnePage() + ", createBy=" + this.getCreateBy() + ", remark=" + this.getRemark() + ", gmtCreate=" + this.getGmtCreate() + ", gmtCreateMin=" + this.getGmtCreateMin() + ", gmtCreateMax=" + this.getGmtCreateMax() + ", lastModifiedBy=" + this.getLastModifiedBy() + ", gmtModified=" + this.getGmtModified() + ", gmtModifiedMax=" + this.getGmtModifiedMax() + ", gmtModifiedMin=" + this.getGmtModifiedMin() + ", activeFlag=" + this.getActiveFlag() + ", attrShow=" + this.getAttrShow() + ")";
    }
}
