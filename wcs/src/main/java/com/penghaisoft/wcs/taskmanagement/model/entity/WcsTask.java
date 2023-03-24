package com.penghaisoft.wcs.taskmanagement.model.entity;

import java.util.Date;
import java.util.List;

public class WcsTask {
    private Integer id;

    private List<Integer> idList;

    private Long taskId;

    private Long relyTaskId;

    private String taskType;

    private String taskStatus;

    private String palletCode;

    private Integer fromAddress;

    private Integer toAddress;

    private String reportWms;

    private Integer priority;

    private String errorMsg;

    private String userDefined1;

    private String userDefined2;

    private String userDefined3;

    private String userDefined4;

    private String userDefined5;

    private Date gmtStart;

    private String createBy;

    private Date gmtCreate;

    private String lastModifiedBy;

    private Date gmtModified;

    private Date gmtEnd;
    private Date gmtEndMin;
    private Date gmtEndMax;

    private String orderBy;

    private String endBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getRelyTaskId() {
        return relyTaskId;
    }

    public void setRelyTaskId(Long relyTaskId) {
        this.relyTaskId = relyTaskId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType == null ? null : taskType.trim();
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus == null ? null : taskStatus.trim();
    }

    public String getPalletCode() {
        return palletCode;
    }

    public void setPalletCode(String palletCode) {
        this.palletCode = palletCode == null ? null : palletCode.trim();
    }

    public Integer getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(Integer fromAddress) {
        this.fromAddress = fromAddress;
    }

    public Integer getToAddress() {
        return toAddress;
    }

    public void setToAddress(Integer toAddress) {
        this.toAddress = toAddress;
    }

    public String getReportWms() {
        return reportWms;
    }

    public void setReportWms(String reportWms) {
        this.reportWms = reportWms == null ? null : reportWms.trim();
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
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

    public Date getGmtStart() {
        return gmtStart;
    }

    public void setGmtStart(Date gmtStart) {
        this.gmtStart = gmtStart;
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

    public Date getGmtEnd() {
        return gmtEnd;
    }

    public void setGmtEnd(Date gmtEnd) {
        this.gmtEnd = gmtEnd;
    }

    public String getEndBy() {
        return endBy;
    }

    public void setEndBy(String endBy) {
        this.endBy = endBy == null ? null : endBy.trim();
    }

    public List<Integer> getIdList() {
        return idList;
    }

    public void setIdList(List<Integer> idList) {
        this.idList = idList;
    }

    public Date getGmtEndMin() {
        return gmtEndMin;
    }

    public void setGmtEndMin(Date gmtEndMin) {
        this.gmtEndMin = gmtEndMin;
    }

    public Date getGmtEndMax() {
        return gmtEndMax;
    }

    public void setGmtEndMax(Date gmtEndMax) {
        this.gmtEndMax = gmtEndMax;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}