package com.penghaisoft.pda.storage.dao;

import com.penghaisoft.pda.storage.model.WmsSendLog;
import org.springframework.stereotype.Repository;

@Repository
public interface WmsSendLogMapper {

    int insert(WmsSendLog record);

    int insertSelective(WmsSendLog record);
}