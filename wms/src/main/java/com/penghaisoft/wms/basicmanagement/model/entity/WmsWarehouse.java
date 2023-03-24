//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.model.entity;

import java.util.Date;
import java.util.List;

public class WmsWarehouse {
    private List<String> warehouseIdList;
    private String warehouseId;
    private String warehouseName;
    private String warehouseCode;
    private String warehouseAttr;
    private String warehouseAddr;
    private Integer warehouseCapacity;
    private String factoryName;
    private String address;
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

    public WmsWarehouse() {
    }

    public List<String> getWarehouseIdList() {
        return this.warehouseIdList;
    }

    public String getWarehouseId() {
        return this.warehouseId;
    }

    public String getWarehouseName() {
        return this.warehouseName;
    }

    public String getWarehouseCode() {
        return this.warehouseCode;
    }

    public String getWarehouseAttr() {
        return this.warehouseAttr;
    }

    public String getWarehouseAddr() {
        return this.warehouseAddr;
    }

    public Integer getWarehouseCapacity() {
        return this.warehouseCapacity;
    }

    public String getFactoryName() {
        return this.factoryName;
    }

    public String getAddress() {
        return this.address;
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

    public void setWarehouseIdList(final List<String> warehouseIdList) {
        this.warehouseIdList = warehouseIdList;
    }

    public void setWarehouseId(final String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public void setWarehouseName(final String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public void setWarehouseCode(final String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public void setWarehouseAttr(final String warehouseAttr) {
        this.warehouseAttr = warehouseAttr;
    }

    public void setWarehouseAddr(final String warehouseAddr) {
        this.warehouseAddr = warehouseAddr;
    }

    public void setWarehouseCapacity(final Integer warehouseCapacity) {
        this.warehouseCapacity = warehouseCapacity;
    }

    public void setFactoryName(final String factoryName) {
        this.factoryName = factoryName;
    }

    public void setAddress(final String address) {
        this.address = address;
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

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof WmsWarehouse)) {
            return false;
        } else {
            WmsWarehouse other = (WmsWarehouse)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$warehouseIdList = this.getWarehouseIdList();
                Object other$warehouseIdList = other.getWarehouseIdList();
                if (this$warehouseIdList == null) {
                    if (other$warehouseIdList != null) {
                        return false;
                    }
                } else if (!this$warehouseIdList.equals(other$warehouseIdList)) {
                    return false;
                }

                Object this$warehouseId = this.getWarehouseId();
                Object other$warehouseId = other.getWarehouseId();
                if (this$warehouseId == null) {
                    if (other$warehouseId != null) {
                        return false;
                    }
                } else if (!this$warehouseId.equals(other$warehouseId)) {
                    return false;
                }

                Object this$warehouseName = this.getWarehouseName();
                Object other$warehouseName = other.getWarehouseName();
                if (this$warehouseName == null) {
                    if (other$warehouseName != null) {
                        return false;
                    }
                } else if (!this$warehouseName.equals(other$warehouseName)) {
                    return false;
                }

                label286: {
                    Object this$warehouseCode = this.getWarehouseCode();
                    Object other$warehouseCode = other.getWarehouseCode();
                    if (this$warehouseCode == null) {
                        if (other$warehouseCode == null) {
                            break label286;
                        }
                    } else if (this$warehouseCode.equals(other$warehouseCode)) {
                        break label286;
                    }

                    return false;
                }

                label279: {
                    Object this$warehouseAttr = this.getWarehouseAttr();
                    Object other$warehouseAttr = other.getWarehouseAttr();
                    if (this$warehouseAttr == null) {
                        if (other$warehouseAttr == null) {
                            break label279;
                        }
                    } else if (this$warehouseAttr.equals(other$warehouseAttr)) {
                        break label279;
                    }

                    return false;
                }

                Object this$warehouseAddr = this.getWarehouseAddr();
                Object other$warehouseAddr = other.getWarehouseAddr();
                if (this$warehouseAddr == null) {
                    if (other$warehouseAddr != null) {
                        return false;
                    }
                } else if (!this$warehouseAddr.equals(other$warehouseAddr)) {
                    return false;
                }

                label265: {
                    Object this$warehouseCapacity = this.getWarehouseCapacity();
                    Object other$warehouseCapacity = other.getWarehouseCapacity();
                    if (this$warehouseCapacity == null) {
                        if (other$warehouseCapacity == null) {
                            break label265;
                        }
                    } else if (this$warehouseCapacity.equals(other$warehouseCapacity)) {
                        break label265;
                    }

                    return false;
                }

                label258: {
                    Object this$factoryName = this.getFactoryName();
                    Object other$factoryName = other.getFactoryName();
                    if (this$factoryName == null) {
                        if (other$factoryName == null) {
                            break label258;
                        }
                    } else if (this$factoryName.equals(other$factoryName)) {
                        break label258;
                    }

                    return false;
                }

                Object this$address = this.getAddress();
                Object other$address = other.getAddress();
                if (this$address == null) {
                    if (other$address != null) {
                        return false;
                    }
                } else if (!this$address.equals(other$address)) {
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

                label237: {
                    Object this$userDefined2 = this.getUserDefined2();
                    Object other$userDefined2 = other.getUserDefined2();
                    if (this$userDefined2 == null) {
                        if (other$userDefined2 == null) {
                            break label237;
                        }
                    } else if (this$userDefined2.equals(other$userDefined2)) {
                        break label237;
                    }

                    return false;
                }

                label230: {
                    Object this$userDefined3 = this.getUserDefined3();
                    Object other$userDefined3 = other.getUserDefined3();
                    if (this$userDefined3 == null) {
                        if (other$userDefined3 == null) {
                            break label230;
                        }
                    } else if (this$userDefined3.equals(other$userDefined3)) {
                        break label230;
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

                label216: {
                    Object this$userDefined5 = this.getUserDefined5();
                    Object other$userDefined5 = other.getUserDefined5();
                    if (this$userDefined5 == null) {
                        if (other$userDefined5 == null) {
                            break label216;
                        }
                    } else if (this$userDefined5.equals(other$userDefined5)) {
                        break label216;
                    }

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

                    label199: {
                        Object this$remark = this.getRemark();
                        Object other$remark = other.getRemark();
                        if (this$remark == null) {
                            if (other$remark == null) {
                                break label199;
                            }
                        } else if (this$remark.equals(other$remark)) {
                            break label199;
                        }

                        return false;
                    }

                    label192: {
                        Object this$gmtCreate = this.getGmtCreate();
                        Object other$gmtCreate = other.getGmtCreate();
                        if (this$gmtCreate == null) {
                            if (other$gmtCreate == null) {
                                break label192;
                            }
                        } else if (this$gmtCreate.equals(other$gmtCreate)) {
                            break label192;
                        }

                        return false;
                    }

                    label185: {
                        Object this$gmtCreateMin = this.getGmtCreateMin();
                        Object other$gmtCreateMin = other.getGmtCreateMin();
                        if (this$gmtCreateMin == null) {
                            if (other$gmtCreateMin == null) {
                                break label185;
                            }
                        } else if (this$gmtCreateMin.equals(other$gmtCreateMin)) {
                            break label185;
                        }

                        return false;
                    }

                    Object this$gmtCreateMax = this.getGmtCreateMax();
                    Object other$gmtCreateMax = other.getGmtCreateMax();
                    if (this$gmtCreateMax == null) {
                        if (other$gmtCreateMax != null) {
                            return false;
                        }
                    } else if (!this$gmtCreateMax.equals(other$gmtCreateMax)) {
                        return false;
                    }

                    label171: {
                        Object this$lastModifiedBy = this.getLastModifiedBy();
                        Object other$lastModifiedBy = other.getLastModifiedBy();
                        if (this$lastModifiedBy == null) {
                            if (other$lastModifiedBy == null) {
                                break label171;
                            }
                        } else if (this$lastModifiedBy.equals(other$lastModifiedBy)) {
                            break label171;
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

                    label157: {
                        Object this$gmtModifiedMax = this.getGmtModifiedMax();
                        Object other$gmtModifiedMax = other.getGmtModifiedMax();
                        if (this$gmtModifiedMax == null) {
                            if (other$gmtModifiedMax == null) {
                                break label157;
                            }
                        } else if (this$gmtModifiedMax.equals(other$gmtModifiedMax)) {
                            break label157;
                        }

                        return false;
                    }

                    label150: {
                        Object this$gmtModifiedMin = this.getGmtModifiedMin();
                        Object other$gmtModifiedMin = other.getGmtModifiedMin();
                        if (this$gmtModifiedMin == null) {
                            if (other$gmtModifiedMin == null) {
                                break label150;
                            }
                        } else if (this$gmtModifiedMin.equals(other$gmtModifiedMin)) {
                            break label150;
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

                    return true;
                }
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof WmsWarehouse;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $warehouseIdList = this.getWarehouseIdList();
        result = result * 59 + ($warehouseIdList == null ? 43 : $warehouseIdList.hashCode());
        Object $warehouseId = this.getWarehouseId();
        result = result * 59 + ($warehouseId == null ? 43 : $warehouseId.hashCode());
        Object $warehouseName = this.getWarehouseName();
        result = result * 59 + ($warehouseName == null ? 43 : $warehouseName.hashCode());
        Object $warehouseCode = this.getWarehouseCode();
        result = result * 59 + ($warehouseCode == null ? 43 : $warehouseCode.hashCode());
        Object $warehouseAttr = this.getWarehouseAttr();
        result = result * 59 + ($warehouseAttr == null ? 43 : $warehouseAttr.hashCode());
        Object $warehouseAddr = this.getWarehouseAddr();
        result = result * 59 + ($warehouseAddr == null ? 43 : $warehouseAddr.hashCode());
        Object $warehouseCapacity = this.getWarehouseCapacity();
        result = result * 59 + ($warehouseCapacity == null ? 43 : $warehouseCapacity.hashCode());
        Object $factoryName = this.getFactoryName();
        result = result * 59 + ($factoryName == null ? 43 : $factoryName.hashCode());
        Object $address = this.getAddress();
        result = result * 59 + ($address == null ? 43 : $address.hashCode());
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
        return result;
    }

    public String toString() {
        return "WmsWarehouse(warehouseIdList=" + this.getWarehouseIdList() + ", warehouseId=" + this.getWarehouseId() + ", warehouseName=" + this.getWarehouseName() + ", warehouseCode=" + this.getWarehouseCode() + ", warehouseAttr=" + this.getWarehouseAttr() + ", warehouseAddr=" + this.getWarehouseAddr() + ", warehouseCapacity=" + this.getWarehouseCapacity() + ", factoryName=" + this.getFactoryName() + ", address=" + this.getAddress() + ", userDefined1=" + this.getUserDefined1() + ", userDefined2=" + this.getUserDefined2() + ", userDefined3=" + this.getUserDefined3() + ", userDefined4=" + this.getUserDefined4() + ", userDefined5=" + this.getUserDefined5() + ", startNumber=" + this.getStartNumber() + ", numberOnePage=" + this.getNumberOnePage() + ", createBy=" + this.getCreateBy() + ", remark=" + this.getRemark() + ", gmtCreate=" + this.getGmtCreate() + ", gmtCreateMin=" + this.getGmtCreateMin() + ", gmtCreateMax=" + this.getGmtCreateMax() + ", lastModifiedBy=" + this.getLastModifiedBy() + ", gmtModified=" + this.getGmtModified() + ", gmtModifiedMax=" + this.getGmtModifiedMax() + ", gmtModifiedMin=" + this.getGmtModifiedMin() + ", activeFlag=" + this.getActiveFlag() + ")";
    }
}
