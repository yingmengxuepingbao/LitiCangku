//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.reportmanagement.model.dto;

import java.util.Arrays;

public class ReportShowData {
    private String[] name;
    private Integer[] inValue;
    private Integer[] outValue;

    public ReportShowData() {
    }

    public String[] getName() {
        return this.name;
    }

    public Integer[] getInValue() {
        return this.inValue;
    }

    public Integer[] getOutValue() {
        return this.outValue;
    }

    public void setName(final String[] name) {
        this.name = name;
    }

    public void setInValue(final Integer[] inValue) {
        this.inValue = inValue;
    }

    public void setOutValue(final Integer[] outValue) {
        this.outValue = outValue;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ReportShowData)) {
            return false;
        } else {
            ReportShowData other = (ReportShowData)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (!Arrays.deepEquals(this.getName(), other.getName())) {
                return false;
            } else if (!Arrays.deepEquals(this.getInValue(), other.getInValue())) {
                return false;
            } else {
                return Arrays.deepEquals(this.getOutValue(), other.getOutValue());
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ReportShowData;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        result = result * 59 + Arrays.deepHashCode(this.getName());
        result = result * 59 + Arrays.deepHashCode(this.getInValue());
        result = result * 59 + Arrays.deepHashCode(this.getOutValue());
        return result;
    }

    public String toString() {
        return "ReportShowData(name=" + Arrays.deepToString(this.getName()) + ", inValue=" + Arrays.deepToString(this.getInValue()) + ", outValue=" + Arrays.deepToString(this.getOutValue()) + ")";
    }
}
