//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.business;

import com.penghaisoft.framework.util.Resp;

public interface IWmsSendLedDataService {
    Resp sendLEDOut(long taskId, String palletCode, String status, Integer boxAmount);
}
