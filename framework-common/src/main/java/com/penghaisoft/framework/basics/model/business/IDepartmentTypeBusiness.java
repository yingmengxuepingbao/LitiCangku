package com.penghaisoft.framework.basics.model.business;

import com.penghaisoft.framework.basics.model.entity.DepartmentType;

import java.util.List;

/**
 * Interface:部门属性信息业务逻辑接口
 *
 * @author 秦超
 * 2018/1/30
 */
public interface IDepartmentTypeBusiness {

    /**
     * 获取部门属性列表信息接口
     * @return 部门属性列表
     * @author 秦超
     * 2018-01-30
     */
    List<DepartmentType> getDepartmentTypes();

    /**
     * 根据id获取部门属性信息接口
     * @return 部门属性列表
     * @author 秦超
     * 2018-01-30
     */
    DepartmentType getDepartmentType(Integer typeId);

    /**
     * 新增部门属性信息接口
     * @param typeName 部门属性名称
     * @return
     * @author 秦超
     * 2018-01-30
     */
    boolean addDepartmentTypes(String typeName);

    /**
     * 编辑修改部门属性信息
     * @param typeId 部门属性id
     * @param typeName 部门属性名称
     * @return
     * @author 秦超
     * 2018-01-30
     */
    boolean editDepartmentTypes(Integer typeId, String typeName);


    /**
     * 删除部门属性信息
     * @param typeId 部门属性id
     * @return
     * @author 秦超
     * 2018-01-30
     */
    boolean deleteDepartmentTypes(Integer typeId);

    /**
     * 查询部门名称是否存在
     * @param typeName 部门属性名称
     * @return
     * @author 秦超
     * 2018-01-30
     */
    boolean checkDepartmentTypeAvailable(Integer typeId, String typeName);

    /**
     * 获取错误码
     * @author 秦超
     * 2017-09-01 15:23:56
     */
    String getErrorCode();
    /**
     * 获取错误信息
     * @author 秦超
     * 2017-09-01 15:24:04
     */
    String getErrorMessage();
}
