package com.penghaisoft.pda.storage.service;

import com.penghaisoft.pda.common.Resp;
import com.penghaisoft.pda.storage.model.WmsPallet;

import java.util.List;

/**
 * <p>
 * 业务接口类
 * </p>
 *
 * @author
 * @createDate
 **/
public interface WmsLocationStereoscopicService {
    public List<WmsPallet> queryWmsPallet(String palletCode);

    public String queryRecommendLocationCodeCheck(String palletCode);

    public Resp queryRecommendLocationCode(String palletCode);

    public Resp revertLocationStatus0(String locationCode);

    public Resp revertLocationStatus0New(String locationCode, String palletId);
}
