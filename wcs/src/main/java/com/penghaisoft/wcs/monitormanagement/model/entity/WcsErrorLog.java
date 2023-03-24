package com.penghaisoft.wcs.monitormanagement.model.entity;

import java.util.Date;

public class WcsErrorLog {
    private Integer id;

    private String orderBy;// 排序

    private Short deviceId;

    private String deviceType;

    private String errType;

    private Short errCode;

    private Short level;

    private String levelStr;

    private String faultSource;

    private String instruction;

    private String description;

    private String userDefined1;

    private String userDefined2;

    private String userDefined3;

    private String userDefined4;

    private String userDefined5;

    private Date gmtCreate;
    private Date gmtCreateMin;
    private Date gmtCreateMax;

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getLevelStr() {
        return levelStr;
    }

    public void setLevelStr(String levelStr) {
        this.levelStr = levelStr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Short deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType == null ? null : deviceType.trim();
    }

    public String getErrType() {
        return errType;
    }

    public void setErrType(String errType) {
        this.errType = errType == null ? null : errType.trim();
    }

    public Short getErrCode() {
        return errCode;
    }

    public void setErrCode(Short errCode) {
        this.errCode = errCode;
    }

    public Short getLevel() {
        return level;
    }

    public void setLevel(Short level) {
        this.level = level;
    }

    public String getFaultSource() {
        return faultSource;
    }

    public void setFaultSource(String faultSource) {
        this.faultSource = faultSource == null ? null : faultSource.trim();
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction == null ? null : instruction.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtCreateMax() {
        return gmtCreateMax;
    }

    public void setGmtCreateMax(Date gmtCreateMax) {
        this.gmtCreateMax = gmtCreateMax;
    }

    public Date getGmtCreateMin() {
        return gmtCreateMin;
    }

    public void setGmtCreateMin(Date gmtCreateMin) {
        this.gmtCreateMin = gmtCreateMin;
    }
}