package com.penghaisoft.wcs.jobmanagement.model.entity;

public class WcsJobExecutionSummaryKey {
    private String jobName;

    private Integer summaryHour;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName == null ? null : jobName.trim();
    }

    public Integer getSummaryHour() {
        return summaryHour;
    }

    public void setSummaryHour(Integer summaryHour) {
        this.summaryHour = summaryHour;
    }
}