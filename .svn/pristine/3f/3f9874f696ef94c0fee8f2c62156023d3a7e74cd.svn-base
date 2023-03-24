package com.penghaisoft.framework.permissionmanagement.model.bussiness;

public interface IPostManagementBusiness {

    /**
     * 新增部门下的岗位
     * @param departmentId
     * @param roleId
     * @param postName
     * @param postCode
     * @return
     */
    boolean addPost(Integer departmentId,Integer roleId, String postName, String postCode);

    /**
     * 删除岗位
     * @param departmentId 部门ID
     * @param postId
     * @return
     */
    boolean deletePost(Integer departmentId,Integer postId);

    /**
     * 获取最近一条错误码
     * @return 角色业务逻辑执行过程中抛出的最后一个错误的错误码
     */
    String getLastErrorCode();

    /**
     * 获取最近一条错误信息
     * @return 角色业务逻辑执行过程中抛出的最后一个错误的错误信息
     */
    String getLastErrorMessage();

}
