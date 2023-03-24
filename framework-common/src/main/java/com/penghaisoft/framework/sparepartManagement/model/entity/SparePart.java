package com.penghaisoft.framework.sparepartManagement.model.entity;

import com.penghaisoft.framework.entity.PageNumber;

public class SparePart  extends PageNumber {
    private Integer id;
    //备件编码
    private String sparePartCode;
    //备件名称
    private String sparePartName;
    //规格型号
    private String specAndType;
    //生产厂家
    private String manufacturer;
    //是否关键备件 1 是 0 否
    private String isPivotal;
    //是否备件共享 1 是 0 否
    private String isShare;
    //是否强制更换 1 是 0 否
    private String isForceReplace;
    //更换周期
    private Integer cycle;
    //周期单位
    private String unit;
    //安全库存 最小值
    private Integer minStock;
    //安全库存 最大值
    private Integer maxStock;
    //状态 0 待确定 1 在用 2 停用
    private String status;
    //品牌
    private String brand;
    //供应商
    private String supplier;
    //计量单位
    private String calculateUnit;
    //采购周期
    private String purchaseCycle;
    //采购周期单位
    private String purchaseUnit;
    //备件确认人
    private String confirmPerson;
    //备件库存总数量
    private Integer sumStockNumber;
    //查询条件
    private String queryContent;
    //仓库id
    private Integer warehouseId;
    //创建人
    private Integer createUserId;

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getSumStockNumber() {
        return sumStockNumber;
    }

    public void setSumStockNumber(Integer sumStockNumber) {
        this.sumStockNumber = sumStockNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getCalculateUnit() {
        return calculateUnit;
    }

    public void setCalculateUnit(String calculateUnit) {
        this.calculateUnit = calculateUnit;
    }

    public String getPurchaseCycle() {
        return purchaseCycle;
    }

    public void setPurchaseCycle(String purchaseCycle) {
        this.purchaseCycle = purchaseCycle;
    }

    public String getPurchaseUnit() {
        return purchaseUnit;
    }

    public void setPurchaseUnit(String purchaseUnit) {
        this.purchaseUnit = purchaseUnit;
    }

    public String getConfirmPerson() {
        return confirmPerson;
    }

    public void setConfirmPerson(String confirmPerson) {
        this.confirmPerson = confirmPerson;
    }

    public String getQueryContent() {
        return queryContent;
    }

    public void setQueryContent(String queryContent) {
        this.queryContent = queryContent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSparePartCode() {
        return sparePartCode;
    }

    public void setSparePartCode(String sparePartCode) {
        this.sparePartCode = sparePartCode == null ? null : sparePartCode.trim();
    }

    public String getSparePartName() {
        return sparePartName;
    }

    public void setSparePartName(String sparePartName) {
        this.sparePartName = sparePartName == null ? null : sparePartName.trim();
    }

    public String getSpecAndType() {
        return specAndType;
    }

    public void setSpecAndType(String specAndType) {
        this.specAndType = specAndType == null ? null : specAndType.trim();
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer == null ? null : manufacturer.trim();
    }

    public String getIsPivotal() {
        return isPivotal;
    }

    public void setIsPivotal(String isPivotal) {
        this.isPivotal = isPivotal;
    }

    public String getIsShare() {
        return isShare;
    }

    public void setIsShare(String isShare) {
        this.isShare = isShare;
    }

    public String getIsForceReplace() {
        return isForceReplace;
    }

    public void setIsForceReplace(String isForceReplace) {
        this.isForceReplace = isForceReplace ;
    }

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public Integer getMinStock() {
        return minStock;
    }

    public void setMinStock(Integer minStock) {
        this.minStock = minStock;
    }

    public Integer getMaxStock() {
        return maxStock;
    }

    public void setMaxStock(Integer maxStock) {
        this.maxStock = maxStock;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}