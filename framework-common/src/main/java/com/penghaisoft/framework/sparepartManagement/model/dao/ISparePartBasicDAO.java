package com.penghaisoft.framework.sparepartManagement.model.dao;

import com.penghaisoft.framework.sparepartManagement.model.entity.SparePart;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ISparePartBasicDAO {
    /**
     * 查询备件基础信息列表
     *
     * @param sparePart
     * @return
     */
    List<SparePart> getAllSparePart(SparePart sparePart);
}

