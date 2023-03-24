//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.expose;

public class WcsTransOb {
    private String areaCode;
    private String orderNo;
    private long taskId;
    private String palletCode;
    private String batch;
    private String materialCode;
    private String weight;
    private String taskType;
    private String taskStatus;
    private String msg;
    private String inAddress;
    private Integer amount;
    private String status;

    public WcsTransOb() {
    }

    public String getAreaCode() {
        return this.areaCode;
    }

    public String getOrderNo() {
        return this.orderNo;
    }

    public long getTaskId() {
        return this.taskId;
    }

    public String getPalletCode() {
        return this.palletCode;
    }

    public String getTaskType() {
        return this.taskType;
    }

    public String getTaskStatus() {
        return this.taskStatus;
    }

    public String getMsg() {
        return this.msg;
    }

    public String getInAddress() {
        return this.inAddress;
    }

    public Integer getAmount() {
        return this.amount;
    }

    public String getStatus() {
        return this.status;
    }

    public void setAreaCode(final String areaCode) {
        this.areaCode = areaCode;
    }

    public void setOrderNo(final String orderNo) {
        this.orderNo = orderNo;
    }

    public void setTaskId(final long taskId) {
        this.taskId = taskId;
    }

    public void setPalletCode(final String palletCode) {
        this.palletCode = palletCode;
    }

    public void setTaskType(final String taskType) {
        this.taskType = taskType;
    }

    public void setTaskStatus(final String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    public void setInAddress(final String inAddress) {
        this.inAddress = inAddress;
    }

    public void setAmount(final Integer amount) {
        this.amount = amount;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof WcsTransOb)) {
            return false;
        } else {
            WcsTransOb other = (WcsTransOb)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label123: {
                    Object this$areaCode = this.getAreaCode();
                    Object other$areaCode = other.getAreaCode();
                    if (this$areaCode == null) {
                        if (other$areaCode == null) {
                            break label123;
                        }
                    } else if (this$areaCode.equals(other$areaCode)) {
                        break label123;
                    }

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

                if (this.getTaskId() != other.getTaskId()) {
                    return false;
                } else {
                    label108: {
                        Object this$palletCode = this.getPalletCode();
                        Object other$palletCode = other.getPalletCode();
                        if (this$palletCode == null) {
                            if (other$palletCode == null) {
                                break label108;
                            }
                        } else if (this$palletCode.equals(other$palletCode)) {
                            break label108;
                        }

                        return false;
                    }

                    Object this$taskType = this.getTaskType();
                    Object other$taskType = other.getTaskType();
                    if (this$taskType == null) {
                        if (other$taskType != null) {
                            return false;
                        }
                    } else if (!this$taskType.equals(other$taskType)) {
                        return false;
                    }

                    Object this$taskStatus = this.getTaskStatus();
                    Object other$taskStatus = other.getTaskStatus();
                    if (this$taskStatus == null) {
                        if (other$taskStatus != null) {
                            return false;
                        }
                    } else if (!this$taskStatus.equals(other$taskStatus)) {
                        return false;
                    }

                    label87: {
                        Object this$msg = this.getMsg();
                        Object other$msg = other.getMsg();
                        if (this$msg == null) {
                            if (other$msg == null) {
                                break label87;
                            }
                        } else if (this$msg.equals(other$msg)) {
                            break label87;
                        }

                        return false;
                    }

                    label80: {
                        Object this$inAddress = this.getInAddress();
                        Object other$inAddress = other.getInAddress();
                        if (this$inAddress == null) {
                            if (other$inAddress == null) {
                                break label80;
                            }
                        } else if (this$inAddress.equals(other$inAddress)) {
                            break label80;
                        }

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

                    Object this$status = this.getStatus();
                    Object other$status = other.getStatus();
                    if (this$status == null) {
                        if (other$status != null) {
                            return false;
                        }
                    } else if (!this$status.equals(other$status)) {
                        return false;
                    }

                    return true;
                }
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof WcsTransOb;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $areaCode = this.getAreaCode();
        result = result * 59 + ($areaCode == null ? 43 : $areaCode.hashCode());
        Object $orderNo = this.getOrderNo();
        result = result * 59 + ($orderNo == null ? 43 : $orderNo.hashCode());
        long $taskId = this.getTaskId();
        result = result * 59 + (int)($taskId >>> 32 ^ $taskId);
        Object $palletCode = this.getPalletCode();
        result = result * 59 + ($palletCode == null ? 43 : $palletCode.hashCode());
        Object $taskType = this.getTaskType();
        result = result * 59 + ($taskType == null ? 43 : $taskType.hashCode());
        Object $taskStatus = this.getTaskStatus();
        result = result * 59 + ($taskStatus == null ? 43 : $taskStatus.hashCode());
        Object $msg = this.getMsg();
        result = result * 59 + ($msg == null ? 43 : $msg.hashCode());
        Object $inAddress = this.getInAddress();
        result = result * 59 + ($inAddress == null ? 43 : $inAddress.hashCode());
        Object $amount = this.getAmount();
        result = result * 59 + ($amount == null ? 43 : $amount.hashCode());
        Object $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "WcsTransOb{" +
                "areaCode='" + areaCode + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", taskId=" + taskId +
                ", palletCode='" + palletCode + '\'' +
                ", batch='" + batch + '\'' +
                ", materialCode='" + materialCode + '\'' +
                ", weight='" + weight + '\'' +
                ", taskType='" + taskType + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                ", msg='" + msg + '\'' +
                ", inAddress='" + inAddress + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                '}';
    }
}
