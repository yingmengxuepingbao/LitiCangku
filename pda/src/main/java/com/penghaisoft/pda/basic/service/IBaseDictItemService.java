package com.penghaisoft.pda.basic.service;


import com.penghaisoft.pda.basic.model.BaseDictItem;

import java.util.List;

/**
 * <p>
 * 业务接口类
 * </p>
 *
 * @author
 * @createDate
 **/
public interface IBaseDictItemService {
    List<BaseDictItem> getDictTypeByCode(String code);
}
