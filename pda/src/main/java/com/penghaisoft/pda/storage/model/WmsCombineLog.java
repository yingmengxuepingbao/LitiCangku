package com.penghaisoft.pda.storage.model;

import java.util.Date;

public class WmsCombineLog {
    private String combineLogId;

    private String combineTaskId;

    private String fromPalletCode;

    private String toPalletCode;

    private String goodsCode;

    private Integer fromAmount;

    private String batchNo;

    private String warehouseCode;

    private String areaCode;

    private String fromLocationCode;

    private String toLocationCode;

    private String moveResult;

    private String errorMsg;

    private String userDefined1;

    private String userDefined2;

    private String userDefined3;

    private String userDefined4;

    private String userDefined5;

    private String createBy;

    private Date gmtCreate;

    private String lastModifiedBy;

    private Date gmtModified;

    private String activeFlag;

    public String getCombineLogId() {
        return combineLogId;
    }

    public void setCombineLogId(String combineLogId) {
        this.combineLogId = combineLogId == null ? null : combineLogId.trim();
    }

    public String getCombineTaskId() {
        return combineTaskId;
    }

    public void setCombineTaskId(String combineTaskId) {
        this.combineTaskId = combineTaskId == null ? null : combineTaskId.trim();
    }

    public String getFromPalletCode() {
        return fromPalletCode;
    }

    public void setFromPalletCode(String fromPalletCode) {
        this.fromPalletCode = fromPalletCode == null ? null : fromPalletCode.trim();
    }

    public String getToPalletCode() {
        return toPalletCode;
    }

    public void setToPalletCode(String toPalletCode) {
        this.toPalletCode = toPalletCode == null ? null : toPalletCode.trim();
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode == null ? null : goodsCode.trim();
    }

    public Integer getFromAmount() {
        return fromAmount;
    }

    public void setFromAmount(Integer fromAmount) {
        this.fromAmount = fromAmount;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo == null ? null : batchNo.trim();
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    public String getFromLocationCode() {
        return fromLocationCode;
    }

    public void setFromLocationCode(String fromLocationCode) {
        this.fromLocationCode = fromLocationCode == null ? null : fromLocationCode.trim();
    }

    public String getToLocationCode() {
        return toLocationCode;
    }

    public void setToLocationCode(String toLocationCode) {
        this.toLocationCode = toLocationCode == null ? null : toLocationCode.trim();
    }

    public String getMoveResult() {
        return moveResult;
    }

    public void setMoveResult(String moveResult) {
        this.moveResult = moveResult == null ? null : moveResult.trim();
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg == null ? null : errorMsg.trim();
    }

    public String getUserDefined1() {
        return userDefined1;
    }

    public void setUserDefined1(String userDefined1) {
        this.userDefined1 = userDefined1 == null ? null : userDefined1.trim();
    }

    public String getUserDefined2() {
        return userDefined2;
    }

    public void setUserDefined2(String userDefined2) {
        this.userDefined2 = userDefined2 == null ? null : userDefined2.trim();
    }

    public String getUserDefined3() {
        return userDefined3;
    }

    public void setUserDefined3(String userDefined3) {
        this.userDefined3 = userDefined3 == null ? null : userDefined3.trim();
    }

    public String getUserDefined4() {
        return userDefined4;
    }

    public void setUserDefined4(String userDefined4) {
        this.userDefined4 = userDefined4 == null ? null : userDefined4.trim();
    }

    public String getUserDefined5() {
        return userDefined5;
    }

    public void setUserDefined5(String userDefined5) {
        this.userDefined5 = userDefined5 == null ? null : userDefined5.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy == null ? null : lastModifiedBy.trim();
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(String activeFlag) {
        this.activeFlag = activeFlag == null ? null : activeFlag.trim();
    }
}