package com.legend.web.service.impl;

import com.legend.web.bean.User;
import com.legend.web.dao.UserDao;
import com.legend.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xlj
 * @date 2021/4/29
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public Integer insertUser(User user) {
        return userDao.insertUser(user);
    }
}
