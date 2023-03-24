package com.penghaisoft.pda.basic.service.impl;

import com.penghaisoft.pda.basic.dao.WmsAddressRealRelaMapper;
import com.penghaisoft.pda.basic.model.WmsAddressRealRela;
import com.penghaisoft.pda.basic.service.IWmsAddressRealRelaService;
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
@Service("wmsAddressRealRelaService")
public class WmsAddressRealRelaServiceImpl implements IWmsAddressRealRelaService {
    @Resource
    private WmsAddressRealRelaMapper wmsAddressRealRelaMapper;

    @Override
    public List<WmsAddressRealRela> queryOutAddress(WmsAddressRealRela condition) {
        return wmsAddressRealRelaMapper.queryOutAddress(condition);
    }

    @Override
    public List<WmsAddressRealRela> queryByAddressCode(WmsAddressRealRela condition) {
        return wmsAddressRealRelaMapper.queryByAddressCode(condition);
    }
}
