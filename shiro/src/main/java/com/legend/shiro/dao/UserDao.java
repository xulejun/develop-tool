package com.legend.shiro.dao;

import com.legend.shiro.pojo.Permission;
import com.legend.shiro.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface UserDao {
    void save(User user);

    // 根据用户名查找该用户所有信息
    User findByUserName(String username);

    // 根据用户名查询所有角色
    User findRoleByUserName(String username);

    // 根绝角色id查询权限集合
    List<Permission> findPermissionByRoleId(String id);
}
