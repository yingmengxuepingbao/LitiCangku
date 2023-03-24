//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.entity;

import java.util.List;

public class WmsTaskExecutionLog extends BaseEntity {
    private List<String> idList;
    private String isCross;
    private String status;
    private String msg;
    private Integer id;
    private Long taskId;
    private String orderNo;
    private String taskType;
    private String taskStatus;
    private List taskStatusList;
    private String inAddress;
    private String outAddress;
    private String goodsCode;
    private String batchNo;
    private String warehouseCode;
    private String areaCode;
    private String locationCode;
    private String palletCode;
    private String errorMsg;
    private Integer amount;
    private String gmtCreateMin1;
    private String gmtCreateMax1;
    private List taskTypeList;

    public WmsTaskExecutionLog() {
    }

    public List<String> getIdList() {
        return this.idList;
    }

    public String getIsCross() {
        return this.isCross;
    }

    public String getStatus() {
        return this.status;
    }

    public String getMsg() {
        return this.msg;
    }

    public Integer getId() {
        return this.id;
    }

    public Long getTaskId() {
        return this.taskId;
    }

    public String getOrderNo() {
        return this.orderNo;
    }

    public String getTaskType() {
        return this.taskType;
    }

    public String getTaskStatus() {
        return this.taskStatus;
    }

    public String getInAddress() {
        return this.inAddress;
    }

    public String getOutAddress() {
        return this.outAddress;
    }

    public String getGoodsCode() {
        return this.goodsCode;
    }

    public String getBatchNo() {
        return this.batchNo;
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

    public String getPalletCode() {
        return this.palletCode;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public Integer getAmount() {
        return this.amount;
    }

    public void setIdList(final List<String> idList) {
        this.idList = idList;
    }

    public void setIsCross(final String isCross) {
        this.isCross = isCross;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setTaskId(final Long taskId) {
        this.taskId = taskId;
    }

    public void setOrderNo(final String orderNo) {
        this.orderNo = orderNo;
    }

    public void setTaskType(final String taskType) {
        this.taskType = taskType;
    }

    public void setTaskStatus(final String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public void setInAddress(final String inAddress) {
        this.inAddress = inAddress;
    }

    public void setOutAddress(final String outAddress) {
        this.outAddress = outAddress;
    }

    public void setGoodsCode(final String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public void setBatchNo(final String batchNo) {
        this.batchNo = batchNo;
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

    public void setPalletCode(final String palletCode) {
        this.palletCode = palletCode;
    }

    public void setErrorMsg(final String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void setAmount(final Integer amount) {
        this.amount = amount;
    }

    public String getGmtCreateMin1() {
        return gmtCreateMin1;
    }

    public void setGmtCreateMin1(String gmtCreateMin1) {
        this.gmtCreateMin1 = gmtCreateMin1;
    }

    public String getGmtCreateMax1() {
        return gmtCreateMax1;
    }

    public void setGmtCreateMax1(String gmtCreateMax1) {
        this.gmtCreateMax1 = gmtCreateMax1;
    }

    public List getTaskTypeList() {
        return taskTypeList;
    }

    public List getTaskStatusList() {
        return taskStatusList;
    }

    public void setTaskStatusList(List taskStatusList) {
        this.taskStatusList = taskStatusList;
    }

    public void setTaskTypeList(List taskTypeList) {
        this.taskTypeList = taskTypeList;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof WmsTaskExecutionLog)) {
            return false;
        } else {
            WmsTaskExecutionLog other = (WmsTaskExecutionLog)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label239: {
                    Object this$idList = this.getIdList();
                    Object other$idList = other.getIdList();
                    if (this$idList == null) {
                        if (other$idList == null) {
                            break label239;
                        }
                    } else if (this$idList.equals(other$idList)) {
                        break label239;
                    }

                    return false;
                }

                Object this$isCross = this.getIsCross();
                Object other$isCross = other.getIsCross();
                if (this$isCross == null) {
                    if (other$isCross != null) {
                        return false;
                    }
                } else if (!this$isCross.equals(other$isCross)) {
                    return false;
                }

                Object this$status = this.getStatus();
                Object other$status = other.getStatus();
                if (this$status == null) {
                    if (other$status != null) {
                        return false;
                    }
                } else if (!this$status.equals(other$status)) {
                    return false;
                }

                label218: {
                    Object this$msg = this.getMsg();
                    Object other$msg = other.getMsg();
                    if (this$msg == null) {
                        if (other$msg == null) {
                            break label218;
                        }
                    } else if (this$msg.equals(other$msg)) {
                        break label218;
                    }

                    return false;
                }

                label211: {
                    Object this$id = this.getId();
                    Object other$id = other.getId();
                    if (this$id == null) {
                        if (other$id == null) {
                            break label211;
                        }
                    } else if (this$id.equals(other$id)) {
                        break label211;
                    }

                    return false;
                }

                Object this$taskId = this.getTaskId();
                Object other$taskId = other.getTaskId();
                if (this$taskId == null) {
                    if (other$taskId != null) {
                        return false;
                    }
                } else if (!this$taskId.equals(other$taskId)) {
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

                label190: {
                    Object this$taskType = this.getTaskType();
                    Object other$taskType = other.getTaskType();
                    if (this$taskType == null) {
                        if (other$taskType == null) {
                            break label190;
                        }
                    } else if (this$taskType.equals(other$taskType)) {
                        break label190;
                    }

                    return false;
                }

                label183: {
                    Object this$taskStatus = this.getTaskStatus();
                    Object other$taskStatus = other.getTaskStatus();
                    if (this$taskStatus == null) {
                        if (other$taskStatus == null) {
                            break label183;
                        }
                    } else if (this$taskStatus.equals(other$taskStatus)) {
                        break label183;
                    }

                    return false;
                }

                Object this$inAddress = this.getInAddress();
                Object other$inAddress = other.getInAddress();
                if (this$inAddress == null) {
                    if (other$inAddress != null) {
                        return false;
                    }
                } else if (!this$inAddress.equals(other$inAddress)) {
                    return false;
                }

                label169: {
                    Object this$outAddress = this.getOutAddress();
                    Object other$outAddress = other.getOutAddress();
                    if (this$outAddress == null) {
                        if (other$outAddress == null) {
                            break label169;
                        }
                    } else if (this$outAddress.equals(other$outAddress)) {
                        break label169;
                    }

                    return false;
                }

                Object this$goodsCode = this.getGoodsCode();
                Object other$goodsCode = other.getGoodsCode();
                if (this$goodsCode == null) {
                    if (other$goodsCode != null) {
                        return false;
                    }
                } else if (!this$goodsCode.equals(other$goodsCode)) {
                    return false;
                }

                label155: {
                    Object this$batchNo = this.getBatchNo();
                    Object other$batchNo = other.getBatchNo();
                    if (this$batchNo == null) {
                        if (other$batchNo == null) {
                            break label155;
                        }
                    } else if (this$batchNo.equals(other$batchNo)) {
                        break label155;
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

                Object this$areaCode = this.getAreaCode();
                Object other$areaCode = other.getAreaCode();
                if (this$areaCode == null) {
                    if (other$areaCode != null) {
                        return false;
                    }
                } else if (!this$areaCode.equals(other$areaCode)) {
                    return false;
                }

                label134: {
                    Object this$locationCode = this.getLocationCode();
                    Object other$locationCode = other.getLocationCode();
                    if (this$locationCode == null) {
                        if (other$locationCode == null) {
                            break label134;
                        }
                    } else if (this$locationCode.equals(other$locationCode)) {
                        break label134;
                    }

                    return false;
                }

                label127: {
                    Object this$palletCode = this.getPalletCode();
                    Object other$palletCode = other.getPalletCode();
                    if (this$palletCode == null) {
                        if (other$palletCode == null) {
                            break label127;
                        }
                    } else if (this$palletCode.equals(other$palletCode)) {
                        break label127;
                    }

                    return false;
                }

                Object this$errorMsg = this.getErrorMsg();
                Object other$errorMsg = other.getErrorMsg();
                if (this$errorMsg == null) {
                    if (other$errorMsg != null) {
                        return false;
                    }
                } else if (!this$errorMsg.equals(other$errorMsg)) {
                    return false;
                }

                Object this$amount = this.getAmount();
                Object other$amount = other.getAmount();
                if (this$amount == null) {
                    if (other$amount != null) {
                        return false;
                    }
                } else if (!this$amount.equals(other$amount)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof WmsTaskExecutionLog;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $idList = this.getIdList();
        result = result * 59 + ($idList == null ? 43 : $idList.hashCode());
        Object $isCross = this.getIsCross();
        result = result * 59 + ($isCross == null ? 43 : $isCross.hashCode());
        Object $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        Object $msg = this.getMsg();
        result = result * 59 + ($msg == null ? 43 : $msg.hashCode());
        Object $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        Object $taskId = this.getTaskId();
        result = result * 59 + ($taskId == null ? 43 : $taskId.hashCode());
        Object $orderNo = this.getOrderNo();
        result = result * 59 + ($orderNo == null ? 43 : $orderNo.hashCode());
        Object $taskType = this.getTaskType();
        result = result * 59 + ($taskType == null ? 43 : $taskType.hashCode());
        Object $taskStatus = this.getTaskStatus();
        result = result * 59 + ($taskStatus == null ? 43 : $taskStatus.hashCode());
        Object $inAddress = this.getInAddress();
        result = result * 59 + ($inAddress == null ? 43 : $inAddress.hashCode());
        Object $outAddress = this.getOutAddress();
        result = result * 59 + ($outAddress == null ? 43 : $outAddress.hashCode());
        Object $goodsCode = this.getGoodsCode();
        result = result * 59 + ($goodsCode == null ? 43 : $goodsCode.hashCode());
        Object $batchNo = this.getBatchNo();
        result = result * 59 + ($batchNo == null ? 43 : $batchNo.hashCode());
        Object $warehouseCode = this.getWarehouseCode();
        result = result * 59 + ($warehouseCode == null ? 43 : $warehouseCode.hashCode());
        Object $areaCode = this.getAreaCode();
        result = result * 59 + ($areaCode == null ? 43 : $areaCode.hashCode());
        Object $locationCode = this.getLocationCode();
        result = result * 59 + ($locationCode == null ? 43 : $locationCode.hashCode());
        Object $palletCode = this.getPalletCode();
        result = result * 59 + ($palletCode == null ? 43 : $palletCode.hashCode());
        Object $errorMsg = this.getErrorMsg();
        result = result * 59 + ($errorMsg == null ? 43 : $errorMsg.hashCode());
        Object $amount = this.getAmount();
        result = result * 59 + ($amount == null ? 43 : $amount.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "WmsTaskExecutionLog{" +
                "idList=" + idList +
                ", isCross='" + isCross + '\'' +
                ", status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                ", id=" + id +
                ", taskId=" + taskId +
                ", orderNo='" + orderNo + '\'' +
                ", taskType='" + taskType + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                ", taskStatusList=" + taskStatusList +
                ", inAddress='" + inAddress + '\'' +
                ", outAddress='" + outAddress + '\'' +
                ", goodsCode='" + goodsCode + '\'' +
                ", batchNo='" + batchNo + '\'' +
                ", warehouseCode='" + warehouseCode + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", locationCode='" + locationCode + '\'' +
                ", palletCode='" + palletCode + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", amount=" + amount +
                ", gmtCreateMin1='" + gmtCreateMin1 + '\'' +
                ", gmtCreateMax1='" + gmtCreateMax1 + '\'' +
                ", taskTypeList=" + taskTypeList +
                '}';
    }
}