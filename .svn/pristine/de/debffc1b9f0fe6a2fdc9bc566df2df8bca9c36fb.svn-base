package com.penghaisoft.pda.basic.service.impl;

import com.penghaisoft.pda.basic.dao.BaseDictItemMapper;
import com.penghaisoft.pda.basic.model.BaseDictItem;
import com.penghaisoft.pda.basic.service.IBaseDictItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p>
 * 业务逻辑实现类
 * </p>
 *
 * @author
 * @createDate
 **/
@Service("baseDictItemService")
public class BaseDictItemServiceImpl implements IBaseDictItemService {
    @Resource
    private BaseDictItemMapper baseDictItemMapper;

    @Override
    public List<BaseDictItem> getDictTypeByCode(String code) {
        List<BaseDictItem> list = baseDictItemMapper.selectByDicTypeCode(code);
        return list;
    }

}
