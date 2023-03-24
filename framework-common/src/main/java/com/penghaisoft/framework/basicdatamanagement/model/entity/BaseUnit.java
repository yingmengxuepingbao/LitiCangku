package com.penghaisoft.framework.basicdatamanagement.model.entity;

import java.util.Date;
import java.util.List;

/**
 * 计量单位
 * @author
 * 
 */
public class BaseUnit  {
	
	private List<String> unitIdList;
	private int startNumber; //查询起始位置
	private int numberOnePage; //每页数据条数
	private String unitId;//  unitId
	private String unitCode;//  单位代码
	private String unitZhName;//  单位名称
	private String unitEnName;//  英文名称
	private String unitChange;//  国际度量衡换算值
	private String unitType;//  单位类型
	private String userDefined1;//  用户自定义1
	private String userDefined2;//  用户自定义2
	private String userDefined3;//  用户自定义3
	private String userDefined4;//  用户自定义4
	private String userDefined5;//  用户自定义5
	private String createBy;
	private Date gmtCreate;
	private Date gmtCreateMin;
	private Date gmtCreateMax;
	private String lastModifiedBy;// 最后更新人
	private Date gmtModified;
	private Date gmtModifiedMax;
	private Date gmtModifiedMin;
	private String activeFlag;

	public BaseUnit(int startNumber, String unitId, String unitCode, String unitZhName, String unitEnName, String unitChange, String unitType) {
		this.startNumber = startNumber;
		this.unitId = unitId;
		this.unitCode = unitCode;
		this.unitZhName = unitZhName;
		this.unitEnName = unitEnName;
		this.unitChange = unitChange;
		this.unitType = unitType;
	}

	public BaseUnit() {

	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
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

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Date getGmtModifiedMax() {
		return gmtModifiedMax;
	}

	public void setGmtModifiedMax(Date gmtModifiedMax) {
		this.gmtModifiedMax = gmtModifiedMax;
	}

	public Date getGmtModifiedMin() {
		return gmtModifiedMin;
	}

	public void setGmtModifiedMin(Date gmtModifiedMin) {
		this.gmtModifiedMin = gmtModifiedMin;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}
	public String getUnitId() {
		return this.unitId;
	}

	public void setUnitId(String value) {
		this.unitId = value;
	}
	public String getUnitCode() {
		return this.unitCode;
	}

	public void setUnitCode(String value) {
		this.unitCode = value;
	}
	public String getUnitZhName() {
		return this.unitZhName;
	}

	public void setUnitZhName(String value) {
		this.unitZhName = value;
	}
	public String getUnitEnName() {
		return this.unitEnName;
	}

	public void setUnitEnName(String value) {
		this.unitEnName = value;
	}
	public String getUnitChange() {
		return this.unitChange;
	}

	public void setUnitChange(String value) {
		this.unitChange = value;
	}
	public String getUnitType() {
		return this.unitType;
	}

	public void setUnitType(String value) {
		this.unitType = value;
	}
	public String getUserDefined1() {
		return this.userDefined1;
	}

	public void setUserDefined1(String value) {
		this.userDefined1 = value;
	}
	public String getUserDefined2() {
		return this.userDefined2;
	}

	public void setUserDefined2(String value) {
		this.userDefined2 = value;
	}
	public String getUserDefined3() {
		return this.userDefined3;
	}

	public void setUserDefined3(String value) {
		this.userDefined3 = value;
	}
	public String getUserDefined4() {
		return this.userDefined4;
	}

	public void setUserDefined4(String value) {
		this.userDefined4 = value;
	}
	public String getUserDefined5() {
		return this.userDefined5;
	}

	public void setUserDefined5(String value) {
		this.userDefined5 = value;
	}

	public int getStartNumber() {
		return startNumber;
	}

	public void setStartNumber(int startNumber) {
		this.startNumber = startNumber;
	}

	public int getNumberOnePage() {
		return numberOnePage;
	}

	public void setNumberOnePage(int numberOnePage) {
		this.numberOnePage = numberOnePage;
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
		this.lastModifiedBy = lastModifiedBy;
	}
}

