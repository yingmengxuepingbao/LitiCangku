package com.penghaisoft.pda.outwarehouse.model;

import java.util.List;

public class PalletDTO {
    private String palletCode;

    private List<String> barcodeList;

    private Integer palletScanAmount;

    public String getPalletCode() {
        return palletCode;
    }

    public void setPalletCode(String palletCode) {
        this.palletCode = palletCode;
    }

    public List<String> getBarcodeList() {
        return barcodeList;
    }

    public void setBarcodeList(List<String> barcodeList) {
        this.barcodeList = barcodeList;
    }

    public Integer getPalletScanAmount() {
        return palletScanAmount;
    }

    public void setPalletScanAmount(Integer palletScanAmount) {
        this.palletScanAmount = palletScanAmount;
    }
}
