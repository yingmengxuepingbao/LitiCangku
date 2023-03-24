//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.model.entity;

import java.util.Date;
import java.util.List;

public class WmsPlatform {
    private List<String> platformIdList;
    private String platformId;
    private String platformCode;
    private String platformName;
    private String sysAddress;
    private String warehouseCode;
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

    public WmsPlatform() {
    }

    public List<String> getPlatformIdList() {
        return this.platformIdList;
    }

    public String getPlatformId() {
        return this.platformId;
    }

    public String getPlatformCode() {
        return this.platformCode;
    }

    public String getPlatformName() {
        return this.platformName;
    }

    public String getSysAddress() {
        return this.sysAddress;
    }

    public String getWarehouseCode() {
        return this.warehouseCode;
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

    public void setPlatformIdList(final List<String> platformIdList) {
        this.platformIdList = platformIdList;
    }

    public void setPlatformId(final String platformId) {
        this.platformId = platformId;
    }

    public void setPlatformCode(final String platformCode) {
        this.platformCode = platformCode;
    }

    public void setPlatformName(final String platformName) {
        this.platformName = platformName;
    }

    public void setSysAddress(final String sysAddress) {
        this.sysAddress = sysAddress;
    }

    public void setWarehouseCode(final String warehouseCode) {
        this.warehouseCode = warehouseCode;
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
        } else if (!(o instanceof WmsPlatform)) {
            return false;
        } else {
            WmsPlatform other = (WmsPlatform)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label271: {
                    Object this$platformIdList = this.getPlatformIdList();
                    Object other$platformIdList = other.getPlatformIdList();
                    if (this$platformIdList == null) {
                        if (other$platformIdList == null) {
                            break label271;
                        }
                    } else if (this$platformIdList.equals(other$platformIdList)) {
                        break label271;
                    }

                    return false;
                }

                Object this$platformId = this.getPlatformId();
                Object other$platformId = other.getPlatformId();
                if (this$platformId == null) {
                    if (other$platformId != null) {
                        return false;
                    }
                } else if (!this$platformId.equals(other$platformId)) {
                    return false;
                }

                Object this$platformCode = this.getPlatformCode();
                Object other$platformCode = other.getPlatformCode();
                if (this$platformCode == null) {
                    if (other$platformCode != null) {
                        return false;
                    }
                } else if (!this$platformCode.equals(other$platformCode)) {
                    return false;
                }

                label250: {
                    Object this$platformName = this.getPlatformName();
                    Object other$platformName = other.getPlatformName();
                    if (this$platformName == null) {
                        if (other$platformName == null) {
                            break label250;
                        }
                    } else if (this$platformName.equals(other$platformName)) {
                        break label250;
                    }

                    return false;
                }

                label243: {
                    Object this$sysAddress = this.getSysAddress();
                    Object other$sysAddress = other.getSysAddress();
                    if (this$sysAddress == null) {
                        if (other$sysAddress == null) {
                            break label243;
                        }
                    } else if (this$sysAddress.equals(other$sysAddress)) {
                        break label243;
                    }

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

                Object this$userDefined1 = this.getUserDefined1();
                Object other$userDefined1 = other.getUserDefined1();
                if (this$userDefined1 == null) {
                    if (other$userDefined1 != null) {
                        return false;
                    }
                } else if (!this$userDefined1.equals(other$userDefined1)) {
                    return false;
                }

                label222: {
                    Object this$userDefined2 = this.getUserDefined2();
                    Object other$userDefined2 = other.getUserDefined2();
                    if (this$userDefined2 == null) {
                        if (other$userDefined2 == null) {
                            break label222;
                        }
                    } else if (this$userDefined2.equals(other$userDefined2)) {
                        break label222;
                    }

                    return false;
                }

                label215: {
                    Object this$userDefined3 = this.getUserDefined3();
                    Object other$userDefined3 = other.getUserDefined3();
                    if (this$userDefined3 == null) {
                        if (other$userDefined3 == null) {
                            break label215;
                        }
                    } else if (this$userDefined3.equals(other$userDefined3)) {
                        break label215;
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

                label201: {
                    Object this$userDefined5 = this.getUserDefined5();
                    Object other$userDefined5 = other.getUserDefined5();
                    if (this$userDefined5 == null) {
                        if (other$userDefined5 == null) {
                            break label201;
                        }
                    } else if (this$userDefined5.equals(other$userDefined5)) {
                        break label201;
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

                    label184: {
                        Object this$remark = this.getRemark();
                        Object other$remark = other.getRemark();
                        if (this$remark == null) {
                            if (other$remark == null) {
                                break label184;
                            }
                        } else if (this$remark.equals(other$remark)) {
                            break label184;
                        }

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

                    label170: {
                        Object this$gmtCreateMin = this.getGmtCreateMin();
                        Object other$gmtCreateMin = other.getGmtCreateMin();
                        if (this$gmtCreateMin == null) {
                            if (other$gmtCreateMin == null) {
                                break label170;
                            }
                        } else if (this$gmtCreateMin.equals(other$gmtCreateMin)) {
                            break label170;
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

                    label156: {
                        Object this$lastModifiedBy = this.getLastModifiedBy();
                        Object other$lastModifiedBy = other.getLastModifiedBy();
                        if (this$lastModifiedBy == null) {
                            if (other$lastModifiedBy == null) {
                                break label156;
                            }
                        } else if (this$lastModifiedBy.equals(other$lastModifiedBy)) {
                            break label156;
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

                    label142: {
                        Object this$gmtModifiedMax = this.getGmtModifiedMax();
                        Object other$gmtModifiedMax = other.getGmtModifiedMax();
                        if (this$gmtModifiedMax == null) {
                            if (other$gmtModifiedMax == null) {
                                break label142;
                            }
                        } else if (this$gmtModifiedMax.equals(other$gmtModifiedMax)) {
                            break label142;
                        }

                        return false;
                    }

                    label135: {
                        Object this$gmtModifiedMin = this.getGmtModifiedMin();
                        Object other$gmtModifiedMin = other.getGmtModifiedMin();
                        if (this$gmtModifiedMin == null) {
                            if (other$gmtModifiedMin == null) {
                                break label135;
                            }
                        } else if (this$gmtModifiedMin.equals(other$gmtModifiedMin)) {
                            break label135;
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
        return other instanceof WmsPlatform;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $platformIdList = this.getPlatformIdList();
        result = result * 59 + ($platformIdList == null ? 43 : $platformIdList.hashCode());
        Object $platformId = this.getPlatformId();
        result = result * 59 + ($platformId == null ? 43 : $platformId.hashCode());
        Object $platformCode = this.getPlatformCode();
        result = result * 59 + ($platformCode == null ? 43 : $platformCode.hashCode());
        Object $platformName = this.getPlatformName();
        result = result * 59 + ($platformName == null ? 43 : $platformName.hashCode());
        Object $sysAddress = this.getSysAddress();
        result = result * 59 + ($sysAddress == null ? 43 : $sysAddress.hashCode());
        Object $warehouseCode = this.getWarehouseCode();
        result = result * 59 + ($warehouseCode == null ? 43 : $warehouseCode.hashCode());
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
        return "WmsPlatform(platformIdList=" + this.getPlatformIdList() + ", platformId=" + this.getPlatformId() + ", platformCode=" + this.getPlatformCode() + ", platformName=" + this.getPlatformName() + ", sysAddress=" + this.getSysAddress() + ", warehouseCode=" + this.getWarehouseCode() + ", userDefined1=" + this.getUserDefined1() + ", userDefined2=" + this.getUserDefined2() + ", userDefined3=" + this.getUserDefined3() + ", userDefined4=" + this.getUserDefined4() + ", userDefined5=" + this.getUserDefined5() + ", startNumber=" + this.getStartNumber() + ", numberOnePage=" + this.getNumberOnePage() + ", createBy=" + this.getCreateBy() + ", remark=" + this.getRemark() + ", gmtCreate=" + this.getGmtCreate() + ", gmtCreateMin=" + this.getGmtCreateMin() + ", gmtCreateMax=" + this.getGmtCreateMax() + ", lastModifiedBy=" + this.getLastModifiedBy() + ", gmtModified=" + this.getGmtModified() + ", gmtModifiedMax=" + this.getGmtModifiedMax() + ", gmtModifiedMin=" + this.getGmtModifiedMin() + ", activeFlag=" + this.getActiveFlag() + ")";
    }
}
