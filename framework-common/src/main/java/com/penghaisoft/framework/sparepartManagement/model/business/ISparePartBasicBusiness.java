package com.penghaisoft.framework.sparepartManagement.model.business;


import java.util.List;
import java.util.Map;


public interface ISparePartBasicBusiness {
    /**
     * 查询备件基础信息列表
     *
     * @param queryContent
     * @param status
     * @param orderBy
     * @param currentPage
     * @param numberOnePage
     * @return
     */
    Map<String, Object> getAllSparePart(String queryContent, String status, String orderBy, Integer currentPage, Integer numberOnePage);
}

