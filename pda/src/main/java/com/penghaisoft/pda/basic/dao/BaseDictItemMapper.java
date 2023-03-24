package com.penghaisoft.pda.basic.dao;

import com.penghaisoft.pda.basic.model.BaseDictItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseDictItemMapper {
    int deleteByPrimaryKey(String dictItemId);

    int insert(BaseDictItem record);

    int insertSelective(BaseDictItem record);

    BaseDictItem selectByPrimaryKey(String dictItemId);

    int updateByPrimaryKeySelective(BaseDictItem record);

    int updateByPrimaryKey(BaseDictItem record);

    List<BaseDictItem> selectByDicTypeCode(String dicTypeCode);
}