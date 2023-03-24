package com.penghaisoft.pda.storage.model;

public class WmsBoxBarcodeKey {
    private String palletCode;

    private String boxBarcode;

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
}