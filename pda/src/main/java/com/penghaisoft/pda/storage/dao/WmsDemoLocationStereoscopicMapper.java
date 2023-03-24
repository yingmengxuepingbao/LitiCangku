package com.penghaisoft.pda.storage.dao;

import com.penghaisoft.pda.storage.model.WmsLocationStereoscopic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 宝博实验仓库的库位推荐操作
 */
public interface WmsDemoLocationStereoscopicMapper {

	WmsLocationStereoscopic getSimpleEmptyLocation();


	List<WmsLocationStereoscopic> queryBaoboOutLocationCode(@Param("entity") WmsLocationStereoscopic seachOb);

}