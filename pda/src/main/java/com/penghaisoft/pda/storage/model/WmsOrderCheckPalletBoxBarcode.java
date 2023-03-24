package com.penghaisoft.pda.storage.model;

import java.util.Date;

public class WmsOrderCheckPalletBoxBarcode {
    private Integer checkPalletBoxBarcodeId;

    private String orderNo;

    private String diffFlag;

    private String palletCode;

    private String boxBarcode;

    private String realBoxBarcode;

    private String remark;

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

    public Integer getCheckPalletBoxBarcodeId() {
        return checkPalletBoxBarcodeId;
    }

    public void setCheckPalletBoxBarcodeId(Integer checkPalletBoxBarcodeId) {
        this.checkPalletBoxBarcodeId = checkPalletBoxBarcodeId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getDiffFlag() {
        return diffFlag;
    }

    public void setDiffFlag(String diffFlag) {
        this.diffFlag = diffFlag == null ? null : diffFlag.trim();
    }

    public String getPalletCode() {
        return palletCode;
    }

    public void setPalletCode(String palletCode) {
        this.palletCode = palletCode == null ? null : palletCode.trim();
    }

    public String getBoxBarcode() {
        return boxBarcode;
    }

    public void setBoxBarcode(String boxBarcode) {
        this.boxBarcode = boxBarcode == null ? null : boxBarcode.trim();
    }

    public String getRealBoxBarcode() {
        return realBoxBarcode;
    }

    public void setRealBoxBarcode(String realBoxBarcode) {
        this.realBoxBarcode = realBoxBarcode == null ? null : realBoxBarcode.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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