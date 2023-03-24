//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.reportmanagement.model.dto;

public class InOutSummaryDto {
    private String day;
    private Integer count;

    public InOutSummaryDto() {
    }

    public String getDay() {
        return this.day;
    }

    public Integer getCount() {
        return this.count;
    }

    public void setDay(final String day) {
        this.day = day;
    }

    public void setCount(final Integer count) {
        this.count = count;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof InOutSummaryDto)) {
            return false;
        } else {
            InOutSummaryDto other = (InOutSummaryDto)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$day = this.getDay();
                Object other$day = other.getDay();
                if (this$day == null) {
                    if (other$day != null) {
                        return false;
                    }
                } else if (!this$day.equals(other$day)) {
                    return false;
                }

                Object this$count = this.getCount();
                Object other$count = other.getCount();
                if (this$count == null) {
                    if (other$count != null) {
                        return false;
                    }
                } else if (!this$count.equals(other$count)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof InOutSummaryDto;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $day = this.getDay();
        result = result * 59 + ($day == null ? 43 : $day.hashCode());
        Object $count = this.getCount();
        result = result * 59 + ($count == null ? 43 : $count.hashCode());
        return result;
    }

    public String toString() {
        return "InOutSummaryDto(day=" + this.getDay() + ", count=" + this.getCount() + ")";
    }
}
