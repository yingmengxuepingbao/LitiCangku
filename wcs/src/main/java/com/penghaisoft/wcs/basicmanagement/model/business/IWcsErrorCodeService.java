package com.penghaisoft.wcs.basicmanagement.model.business;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsErrorCode;

import java.util.List;

/**
 * <p>
 * 业务接口类
 * </p>
 *
 * @author
 * @createDate
 **/
public interface IWcsErrorCodeService {

    public Resp create(WcsErrorCode wcsErrorCode);

    public Resp delete(WcsErrorCode wcsErrorCode);

    public Pager<WcsErrorCode> findListByCondition(int page, int rows, WcsErrorCode condition);

    public WcsErrorCode findById(String id);

    public Resp update(WcsErrorCode wcsErrorCode);

    public List<WcsErrorCode> queryByAny(WcsErrorCode condition);
}
