package com.penghaisoft.pda.basic.service;

import com.penghaisoft.pda.basic.model.WmsAddressRealRela;

import java.util.List;

/**
 * <p>
 * 业务接口类
 * </p>
 *
 * @author
 * @createDate
 **/
public interface IWmsAddressRealRelaService {
    public List<WmsAddressRealRela> queryOutAddress(WmsAddressRealRela t);

    public List<WmsAddressRealRela> queryByAddressCode(WmsAddressRealRela t);
}
