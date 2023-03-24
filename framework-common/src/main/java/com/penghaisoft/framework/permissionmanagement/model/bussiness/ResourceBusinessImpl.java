package com.penghaisoft.framework.permissionmanagement.model.bussiness;


import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.permissionmanagement.model.dao.ResourcesMapper;
import com.penghaisoft.framework.permissionmanagement.model.dao.RoleResMapper;
import com.penghaisoft.framework.permissionmanagement.model.entity.Resources;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.penghaisoft.framework.constant.Constant.ConfigInfo.MENU_FILE_REPORT_PATH;

/**
 * 资源管理业务
 */
@Slf4j
@Service
public class ResourceBusinessImpl implements IResourceBusiness {

    @Autowired
    private ResourcesMapper resourcesMapper;
    @Autowired
    private RoleResMapper roleResMapper;

    //保存错误码
    private List<String> errorCodes = new ArrayList<>();
    //保存错误描述
    private List<String> errorMessages = new ArrayList<>();

    //菜单id全局变量
    private int resourceId;

    /**
     * 根据资源id查询资源
     *
     * @param resourceId
     * @return
     */
    public Resources selectByPrimaryKey(Integer resourceId) {
        return resourcesMapper.selectByPrimaryKey(resourceId);
    }

    /**
     * 更新资源
     *
     * @param id          id
     * @param parentId    父id
     * @param level       级别
     * @param sequence    顺序
     * @param title       名称
     * @param resCode     资源code
     * @param description 描述
     * @param icon        图标地址
     * @param resType     资源类型 0菜单1按钮2外部链接3报表
     * @param url
     * @return
     */
    @Override
    public boolean updateResource(Integer id, Integer parentId, Integer level, Integer sequence, String title, String resCode, String description, String icon, String resType, String url) {
        boolean result = false;
        Resources resources = resourcesMapper.selectByPrimaryKey(id);
        if (resources == null) {//资源不存在
            errorCodes.add(Constant.RESULT.MENUMANAGEMENT_MENU_EMPTY.code);
            errorMessages.add(Constant.RESULT.MENUMANAGEMENT_MENU_EMPTY.message);
        } else {//修改资源
            resources = new Resources(id, parentId, level, sequence, title, resCode, description, icon, resType, url);
            resourcesMapper.updateByPrimaryKey(resources);
            result = true;

        }
        return result;
    }

    /**
     * 删除资源
     *
     * @param id
     * @return
     */
    @Override
    public boolean deleteResource(Integer id) {
        boolean result = false;

        if (id == null || id <= 0) {
            errorCodes.add(Constant.RESULT.MENUMANAGEMENT_MENU_ID_ERROR.code);
            errorMessages.add(Constant.RESULT.MENUMANAGEMENT_MENU_ID_ERROR.message);
        } else {
            Resources resource = new Resources();
            resource.setId(id);
            resource = resourcesMapper.selectByPrimaryKey(id);
            if (resource == null) {//资源不存在
                errorCodes.add(Constant.RESULT.MENUMANAGEMENT_MENU_EMPTY.code);
                errorMessages.add(Constant.RESULT.MENUMANAGEMENT_MENU_EMPTY.message);
            } else {//删除资源 删除资源和角色关系
                resourcesMapper.deleteByPrimaryKey(id);
                roleResMapper.deleteByRoleIdOrResourceId(null, id);
                result = true;
            }
        }

        return result;
    }

    /**
     * 新增资源
     *
     * @param parentId
     * @param level
     * @param sequence
     * @param title
     * @param resCode
     * @param description
     * @param icon
     * @param resType
     * @param url
     * @return
     */
    public int addResource(Integer parentId, Integer level, Integer sequence, String title, String resCode, String description, String icon, String resType, String url) {
        Resources resources = new Resources(0, parentId, level, sequence, title, resCode, description, icon, resType, url);
        resourcesMapper.insertResource(resources);
        int result = resources.getId();
        if (result < 0) {
            //数据保存有异常
            errorCodes.add(Constant.RESULT.MENUMANAGEMENT_PARAMETER_ERROR.code);
            errorMessages.add(Constant.RESULT.MENUMANAGEMENT_PARAMETER_ERROR.message);
        }
        return result;
    }

    /**
     * 获取菜单树
     *
     * @return
     */
    @Override
    public List<Resources> getAllResourcesStructure() {

        //优化查询速度2019年4月22日13:39:41
        List<Resources> resourcesStructure = getResourcesStructure(0, resourceListGroupByParentId(this.getAllResources()));

        //2.菜单结构为空，添加返回信息
        if (resourcesStructure.isEmpty()) {
            errorCodes.add(Constant.RESULT.MENUMANAGEMENT_MENU_TABLE_EMPTY.code);
            errorMessages.add(Constant.RESULT.MENUMANAGEMENT_MENU_TABLE_EMPTY.message);

            return new ArrayList<>();
        }

        return resourcesStructure;

    }

    /**
     * @Author 张旭
     * @Description 获取所有菜单
     * @Date 13:13 2018/9/27
     * @Param []
     **/
    @Override
    public List<Resources> getAllResources() {
        List<Resources> resources = resourcesMapper.selectAllResourcesBySequence();
        return resources;
    }

    /**
     * 将菜单列表按照parentId分组
     *
     * @return
     */
    public static Map<Integer, List<Resources>> resourceListGroupByParentId(List<Resources> resouces) {
        Map<Integer, List<Resources>> resourceMap = new HashMap<>();

        for (Resources item : resouces) {
            if (resourceMap.containsKey(item.getParentId())) {
                resourceMap.get(item.getParentId()).add(item);
            } else {
                List<Resources> resourcesList = new ArrayList<>();
                resourcesList.add(item);
                resourceMap.put(item.getParentId(), resourcesList);
            }
        }

        return resourceMap;
    }

    /**
     * 递归获取菜单结构--优化查询速度
     *
     * @param parentId 父级菜单id
     * @return
     * @Author xc
     * 2019年4月22日13:05:29
     */
    private List<Resources> getResourcesStructure(int parentId, Map<Integer, List<Resources>> resourceMap) {
        //1. 声明返回结果对象
        List<Resources> returnMenuStructure = new ArrayList<>();

        //2.1 获取当前级别和父级菜单id的菜单列表
        List<Resources> menuList = resourceMap.get(parentId);
        //2.2 菜单结构转换
        if (null != menuList) {
            for (Resources resource : menuList) {

                //2.2.2 是否有对应的Url信息，有则加入菜单实体
                if (!StringUtils.isEmpty(resource.getUrl())) {
                    String urlContent = resource.getUrl();
                    /*if (urlContent.endsWith(Constant.ConfigInfo.MENU_FILE_FORMAT_CPT) || urlContent.endsWith(Constant.ConfigInfo.MENU_FILE_FORMAT_FRM)) {
                        String[] urlParts = urlContent.split("=");
                        String fileName = urlParts[urlParts.length - 1];
                        String cptUrl = MENU_FILE_REPORT_PATH + fileName;
                        resource.setUrl(cptUrl);
                    } else {*/
                        resource.setUrl(urlContent);
                    /*}*/
                }

                //2.3 递归调用获取下一级菜单
                List<Resources> subMenuStructureList = getResourcesStructure(resource.getId(), resourceMap);

                //2.4 子菜单列表赋值
                if (!subMenuStructureList.isEmpty()) {
                    resource.setSubMenu(subMenuStructureList);
                }

                //2.5 当前菜单结构添加到当前级别菜单列表
                returnMenuStructure.add(resource);
            }
        }
        return returnMenuStructure;
    }

    /**
     * 获取菜单图标列表
     * @param projectName 路径————项目名称
     * @author 秦超
     * 2017-09-30 14:41:44
     */
    @Override
    public List<String> getAllMenuIcons(String projectName) {

        String fileRootPath = ResourceBusinessImpl.class.getClassLoader().getResource(Constant.ConfigInfo.MENU_ICON_REAL_PATH).getPath();

        //2.1 建立当前目录中文件的File对象
        File directory = new File(fileRootPath);
        //2.2 取得代表目录中所有文件的File对象数组
        File[] files = directory.listFiles();
        List<String> allFilesName = null;

        if(files != null && files.length > 0){
            allFilesName = new ArrayList<>();

            for (int i = 0; i < files.length; i++) {
                String filePath = projectName + Constant.ConfigInfo.MENU_ICON_PATH + files[i].getName();

                allFilesName.add(filePath);
            }
        }else{
            errorCodes.add(Constant.RESULT.MENUMANAGEMENT_MENU_ICON_EMPTY.code);
            errorMessages.add(Constant.RESULT.MENUMANAGEMENT_MENU_ICON_EMPTY.message);
        }

        return allFilesName;
    }

    @Override
    public String getLastErrorCode() {
        return null;
    }

    @Override
    public String getLastErrorMessage() {
        return null;
    }

}
