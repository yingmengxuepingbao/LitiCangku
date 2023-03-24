package com.penghaisoft.pda.basic.dao;

import com.penghaisoft.pda.basic.model.Resources;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourcesMapper {

    Resources selectByPrimaryKey(Integer id);

    List<Resources> selectHandUserResources(@Param("userId") Integer userId);
}