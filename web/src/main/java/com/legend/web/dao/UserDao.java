package com.legend.web.dao;

import com.legend.common.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author xlj
 * @date 2021/4/29
 */
@Mapper
public interface UserDao {
    int insertUser(@Param("user") User user);

    User queryByUsername(@Param("username") String username);
}
