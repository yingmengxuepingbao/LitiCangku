//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.model.entity;

import java.util.Date;
import java.util.List;

public class WmsPlaneLocation {
    private List<String> locationIdList;
    private Integer locationId;
    private String warehouseCode;
    private String areaCode;
    private String locationCode;
    private String locationName;
    private String userDefined1;
    private String userDefined2;
    private String userDefined3;
    private String userDefined4;
    private String userDefined5;
    private String orderBy;
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

    public WmsPlaneLocation() {
    }

    public List<String> getLocationIdList() {
        return this.locationIdList;
    }

    public Integer getLocationId() {
        return this.locationId;
    }

    public String getWarehouseCode() {
        return this.warehouseCode;
    }

    public String getAreaCode() {
        return this.areaCode;
    }

    public String getLocationCode() {
        return this.locationCode;
    }

    public String getLocationName() {
        return this.locationName;
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

    public String getOrderBy() {
        return this.orderBy;
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

    public void setLocationIdList(final List<String> locationIdList) {
        this.locationIdList = locationIdList;
    }

    public void setLocationId(final Integer locationId) {
        this.locationId = locationId;
    }

    public void setWarehouseCode(final String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public void setAreaCode(final String areaCode) {
        this.areaCode = areaCode;
    }

    public void setLocationCode(final String locationCode) {
        this.locationCode = locationCode;
    }

    public void setLocationName(final String locationName) {
        this.locationName = locationName;
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

    public void setOrderBy(final String orderBy) {
        this.orderBy = orderBy;
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

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof WmsPlaneLocation)) {
            return false;
        } else {
            WmsPlaneLocation other = (WmsPlaneLocation)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label283: {
                    Object this$locationIdList = this.getLocationIdList();
                    Object other$locationIdList = other.getLocationIdList();
                    if (this$locationIdList == null) {
                        if (other$locationIdList == null) {
                            break label283;
                        }
                    } else if (this$locationIdList.equals(other$locationIdList)) {
                        break label283;
                    }

                    return false;
                }

                Object this$locationId = this.getLocationId();
                Object other$locationId = other.getLocationId();
                if (this$locationId == null) {
                    if (other$locationId != null) {
                        return false;
                    }
                } else if (!this$locationId.equals(other$locationId)) {
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

                label262: {
                    Object this$areaCode = this.getAreaCode();
                    Object other$areaCode = other.getAreaCode();
                    if (this$areaCode == null) {
                        if (other$areaCode == null) {
                            break label262;
                        }
                    } else if (this$areaCode.equals(other$areaCode)) {
                        break label262;
                    }

                    return false;
                }

                label255: {
                    Object this$locationCode = this.getLocationCode();
                    Object other$locationCode = other.getLocationCode();
                    if (this$locationCode == null) {
                        if (other$locationCode == null) {
                            break label255;
                        }
                    } else if (this$locationCode.equals(other$locationCode)) {
                        break label255;
                    }

                    return false;
                }

                label248: {
                    Object this$locationName = this.getLocationName();
                    Object other$locationName = other.getLocationName();
                    if (this$locationName == null) {
                        if (other$locationName == null) {
                            break label248;
                        }
                    } else if (this$locationName.equals(other$locationName)) {
                        break label248;
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

                label234: {
                    Object this$userDefined2 = this.getUserDefined2();
                    Object other$userDefined2 = other.getUserDefined2();
                    if (this$userDefined2 == null) {
                        if (other$userDefined2 == null) {
                            break label234;
                        }
                    } else if (this$userDefined2.equals(other$userDefined2)) {
                        break label234;
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

                label220: {
                    Object this$userDefined4 = this.getUserDefined4();
                    Object other$userDefined4 = other.getUserDefined4();
                    if (this$userDefined4 == null) {
                        if (other$userDefined4 == null) {
                            break label220;
                        }
                    } else if (this$userDefined4.equals(other$userDefined4)) {
                        break label220;
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

                Object this$orderBy = this.getOrderBy();
                Object other$orderBy = other.getOrderBy();
                if (this$orderBy == null) {
                    if (other$orderBy != null) {
                        return false;
                    }
                } else if (!this$orderBy.equals(other$orderBy)) {
                    return false;
                }

                if (this.getStartNumber() != other.getStartNumber()) {
                    return false;
                } else if (this.getNumberOnePage() != other.getNumberOnePage()) {
                    return false;
                } else {
                    Object this$createBy = this.getCreateBy();
                    Object other$createBy = other.getCreateBy();
                    if (this$createBy == null) {
                        if (other$createBy != null) {
                            return false;
                        }
                    } else if (!this$createBy.equals(other$createBy)) {
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

                    label182: {
                        Object this$gmtCreate = this.getGmtCreate();
                        Object other$gmtCreate = other.getGmtCreate();
                        if (this$gmtCreate == null) {
                            if (other$gmtCreate == null) {
                                break label182;
                            }
                        } else if (this$gmtCreate.equals(other$gmtCreate)) {
                            break label182;
                        }

                        return false;
                    }

                    label175: {
                        Object this$gmtCreateMin = this.getGmtCreateMin();
                        Object other$gmtCreateMin = other.getGmtCreateMin();
                        if (this$gmtCreateMin == null) {
                            if (other$gmtCreateMin == null) {
                                break label175;
                            }
                        } else if (this$gmtCreateMin.equals(other$gmtCreateMin)) {
                            break label175;
                        }

                        return false;
                    }

                    label168: {
                        Object this$gmtCreateMax = this.getGmtCreateMax();
                        Object other$gmtCreateMax = other.getGmtCreateMax();
                        if (this$gmtCreateMax == null) {
                            if (other$gmtCreateMax == null) {
                                break label168;
                            }
                        } else if (this$gmtCreateMax.equals(other$gmtCreateMax)) {
                            break label168;
                        }

                        return false;
                    }

                    Object this$lastModifiedBy = this.getLastModifiedBy();
                    Object other$lastModifiedBy = other.getLastModifiedBy();
                    if (this$lastModifiedBy == null) {
                        if (other$lastModifiedBy != null) {
                            return false;
                        }
                    } else if (!this$lastModifiedBy.equals(other$lastModifiedBy)) {
                        return false;
                    }

                    label154: {
                        Object this$gmtModified = this.getGmtModified();
                        Object other$gmtModified = other.getGmtModified();
                        if (this$gmtModified == null) {
                            if (other$gmtModified == null) {
                                break label154;
                            }
                        } else if (this$gmtModified.equals(other$gmtModified)) {
                            break label154;
                        }

                        return false;
                    }

                    label147: {
                        Object this$gmtModifiedMax = this.getGmtModifiedMax();
                        Object other$gmtModifiedMax = other.getGmtModifiedMax();
                        if (this$gmtModifiedMax == null) {
                            if (other$gmtModifiedMax == null) {
                                break label147;
                            }
                        } else if (this$gmtModifiedMax.equals(other$gmtModifiedMax)) {
                            break label147;
                        }

                        return false;
                    }

                    Object this$gmtModifiedMin = this.getGmtModifiedMin();
                    Object other$gmtModifiedMin = other.getGmtModifiedMin();
                    if (this$gmtModifiedMin == null) {
                        if (other$gmtModifiedMin != null) {
                            return false;
                        }
                    } else if (!this$gmtModifiedMin.equals(other$gmtModifiedMin)) {
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

                    return true;
                }
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof WmsPlaneLocation;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $locationIdList = this.getLocationIdList();
        result = result * 59 + ($locationIdList == null ? 43 : $locationIdList.hashCode());
        Object $locationId = this.getLocationId();
        result = result * 59 + ($locationId == null ? 43 : $locationId.hashCode());
        Object $warehouseCode = this.getWarehouseCode();
        result = result * 59 + ($warehouseCode == null ? 43 : $warehouseCode.hashCode());
        Object $areaCode = this.getAreaCode();
        result = result * 59 + ($areaCode == null ? 43 : $areaCode.hashCode());
        Object $locationCode = this.getLocationCode();
        result = result * 59 + ($locationCode == null ? 43 : $locationCode.hashCode());
        Object $locationName = this.getLocationName();
        result = result * 59 + ($locationName == null ? 43 : $locationName.hashCode());
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
        Object $orderBy = this.getOrderBy();
        result = result * 59 + ($orderBy == null ? 43 : $orderBy.hashCode());
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
        return result;
    }

    public String toString() {
        return "WmsPlaneLocation(locationIdList=" + this.getLocationIdList() + ", locationId=" + this.getLocationId() + ", warehouseCode=" + this.getWarehouseCode() + ", areaCode=" + this.getAreaCode() + ", locationCode=" + this.getLocationCode() + ", locationName=" + this.getLocationName() + ", userDefined1=" + this.getUserDefined1() + ", userDefined2=" + this.getUserDefined2() + ", userDefined3=" + this.getUserDefined3() + ", userDefined4=" + this.getUserDefined4() + ", userDefined5=" + this.getUserDefined5() + ", orderBy=" + this.getOrderBy() + ", startNumber=" + this.getStartNumber() + ", numberOnePage=" + this.getNumberOnePage() + ", createBy=" + this.getCreateBy() + ", remark=" + this.getRemark() + ", gmtCreate=" + this.getGmtCreate() + ", gmtCreateMin=" + this.getGmtCreateMin() + ", gmtCreateMax=" + this.getGmtCreateMax() + ", lastModifiedBy=" + this.getLastModifiedBy() + ", gmtModified=" + this.getGmtModified() + ", gmtModifiedMax=" + this.getGmtModifiedMax() + ", gmtModifiedMin=" + this.getGmtModifiedMin() + ", activeFlag=" + this.getActiveFlag() + ")";
    }
}
