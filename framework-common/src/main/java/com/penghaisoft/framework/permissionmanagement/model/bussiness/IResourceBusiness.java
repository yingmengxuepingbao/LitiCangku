package com.penghaisoft.framework.permissionmanagement.model.bussiness;

import com.penghaisoft.framework.permissionmanagement.model.entity.Resources;

import java.util.List;

/**
 * 资源管理接口
 */
public interface IResourceBusiness {

    /**
     * 获取所有菜单树形结构
     **/
    List<Resources> getAllResourcesStructure();

    /**
     * 获取所有菜单
     **/
    List<Resources> getAllResources();

    /**
     * 获取菜单图标列表
     * @param projectName
     * @return
     */
    List<String> getAllMenuIcons(String projectName);

    /**
     * 根据资源id获取资源
     *
     * @param resourceId
     * @return
     */
    Resources selectByPrimaryKey(Integer resourceId);

    /**
     * 添加资源
     *
     * @return
     */
    int addResource(Integer parentId, Integer level, Integer sequence, String title, String resCode, String description, String icon, String resType, String url);

    /**
     * 修改资源
     *
     * @return
     */
    boolean updateResource(Integer id, Integer parentId, Integer level, Integer sequence, String title, String resCode, String description, String icon, String resType, String url);

    /**
     * 删除资源
     *
     * @param id
     * @return
     */
    boolean deleteResource(Integer id);

    /**
     * 获取错误码
     */
    String getLastErrorCode();

    /**
     * 获取错误信息
     */
    String getLastErrorMessage();


}
