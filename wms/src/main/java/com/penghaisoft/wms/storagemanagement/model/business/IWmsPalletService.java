//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.business;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsPallet;
import java.util.List;

public interface IWmsPalletService {
    Resp create(WmsPallet wmsPallet);

    Resp delete(WmsPallet wmsPallet);

    Pager<WmsPallet> findListByCondition(int page, int rows, WmsPallet condition);

    WmsPallet findById(String id);

    Resp update(WmsPallet wmsPallet);

    List<WmsPallet> queryByLocationCode(String locationCode);

    Pager<WmsPallet> findStereoscopicList(int page, int rows, WmsPallet condition);

    Pager<WmsPallet> findStereoscopicListHz(int page, int rows, WmsPallet condition);

    List<WmsPallet> queryByAny(WmsPallet wmsPallet);

    Resp updateByPalletCode(WmsPallet wmsPallet);

    Resp relieve(WmsPallet wmsPallet);

    Resp palletizerBind(WmsPallet wmsPallet);

    List<WmsPallet> findExportUnshelvesListByCondition(WmsPallet wmsPallet);

    List<WmsPallet> findExportStereoscopicListHz(WmsPallet wmsPallet);
}
