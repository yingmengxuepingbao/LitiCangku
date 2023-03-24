package com.penghaisoft.framework.permissionmanagement.model.dao;

import com.penghaisoft.framework.permissionmanagement.model.entity.Resources;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourcesMapper {

    /**
     * 根据主键删除id
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Resources record);

    /**
     * 根据主键搜索资源
     *
     * @param id
     * @return
     */
    Resources selectByPrimaryKey(Integer id);


    int updateByPrimaryKeySelective(Resources record);

    /**
     * 根据id修改资源
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(Resources record);

    List<Resources> selectUserResources(@Param("userId") String userId);

    /**
     * 删除所有资源
     *
     * @return
     */
    boolean deleteAll();

    /**
     * 获取所有菜单
     **/
    List<Resources> selectAllResources();

    /**
     * 获取所有菜单按顺序
     **/
    List<Resources> selectAllResourcesBySequence();

    /**
     * 新增资源
     *
     * @param resources
     * @return
     */
    int insertResource(Resources resources);


}