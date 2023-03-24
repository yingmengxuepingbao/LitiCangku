package com.penghaisoft.wcs.jobmanagement.model.entity;

import java.util.Date;

public class WcsJobExecutionSummary extends WcsJobExecutionSummaryKey {
    private Integer jobInterval;

    private String orderBy;// 排序

    private Integer totalCount;

    private Integer errorCount;

    private Integer totalElapsedTime;

    private Integer minElapsedTime;

    private Integer maxElapsedTime;

    private Integer avgElapsedTime;//平均执行时间

    private String userDefined1;

    private String userDefined2;

    private String userDefined3;

    private String userDefined4;

    private String userDefined5;

    private Date gmtCreate;
    private Date gmtCreateMin;
    private Date gmtCreateMax;

    private Date gmtModified;

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getAvgElapsedTime() {
        return avgElapsedTime;
    }

    public void setAvgElapsedTime(Integer avgElapsedTime) {
        this.avgElapsedTime = avgElapsedTime;
    }

    public Integer getJobInterval() {
        return jobInterval;
    }

    public void setJobInterval(Integer jobInterval) {
        this.jobInterval = jobInterval;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    public Integer getTotalElapsedTime() {
        return totalElapsedTime;
    }

    public void setTotalElapsedTime(Integer totalElapsedTime) {
        this.totalElapsedTime = totalElapsedTime;
    }

    public Integer getMinElapsedTime() {
        return minElapsedTime;
    }

    public void setMinElapsedTime(Integer minElapsedTime) {
        this.minElapsedTime = minElapsedTime;
    }

    public Integer getMaxElapsedTime() {
        return maxElapsedTime;
    }

    public void setMaxElapsedTime(Integer maxElapsedTime) {
        this.maxElapsedTime = maxElapsedTime;
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

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Date getGmtCreateMin() {
        return gmtCreateMin;
    }

    public void setGmtCreateMin(Date gmtCreateMin) {
        this.gmtCreateMin = gmtCreateMin;
    }

    public Date getGmtCreateMax() {
        return gmtCreateMax;
    }

    public void setGmtCreateMax(Date gmtCreateMax) {
        this.gmtCreateMax = gmtCreateMax;
    }
}