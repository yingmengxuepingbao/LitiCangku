package com.penghaisoft.pda.inwarehouse.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 
 * @author
 * 
 */
@Data
public class WmsOrderIn {

	private String orderInId;//  orderInId
	private String orderNo;//  单据编号
	private String orderItem;//  orderItem
	private String orderType;//  单据类型 1采购 2手工 3委外加工
	private String orderStatus;//  订单状态1创建2入库中3完成
	private String goodsName;//  商品名称
	private String goodsCode;//  商品编码
	private Integer planAmount;//  计划数量
	private Integer realAmount;//  实际数量
	private String userDefined1;//  用户自定义1
	private String userDefined2;//  用户自定义2
	private String userDefined3;//  用户自定义3
	private String userDefined4;//  用户自定义4
	private String userDefined5;//  用户自定义5


	private String remark;
	private String createBy;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date gmtCreate;
	private String lastModifiedBy;// 最后更新人
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date gmtModified;
	private String activeFlag;


}

