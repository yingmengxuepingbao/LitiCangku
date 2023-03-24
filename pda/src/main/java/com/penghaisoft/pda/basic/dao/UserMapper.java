package com.penghaisoft.pda.basic.dao;

import com.penghaisoft.pda.basic.model.User;
import org.springframework.stereotype.Repository;

/**
 * 用户数据访问Mapper
 *
 * @author 刘立华
 * @Date 2017-08-25
 */
@Repository
public interface UserMapper {

    /**
     * 查询一条用户信息
     *
     * @param user：User实体
     * @return 用户信息
     */
    User selectUser(User user);

}