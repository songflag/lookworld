package com.sinker.lookworld.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sinker.lookworld.dao.PermissionDao;
import com.sinker.lookworld.dao.RoleDao;
import com.sinker.lookworld.dao.UserDao;
import com.sinker.lookworld.model.User;
import com.sinker.lookworld.model.search.UserEditPW;
import com.sinker.lookworld.model.search.UserRegister;
import com.sinker.lookworld.model.search.UserSearch;
import com.sinker.lookworld.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.service.impl
 * Author: sinker
 * CreateTime: 2025-03-04  17:26
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */

@Service
@CacheConfig(cacheNames ="c.s.l.service.impl.UserServiceImpl" )
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    private RoleDao roleDao;
    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    //sin获取所有的用户信息
    @Override
    public List<User> getUserAll(UserSearch userSearch, Page<?> p) {
        //sin 分页查询用户信息
        try (Page<?> __ = PageHelper.startPage(p.getPageNum(),p.getPageSize())){
            return this.userDao.getUserAll(userSearch);
        }
    }

    //sin删除用户
    @Override
    public boolean deleteByIds(Integer[] ids) {
        return this.userDao.deleteByIds(ids)>0;
    }

    //sin 添加用户
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addUser(User user) {
        //todo使用密码加密算法
        user.setCreatedAt(LocalDateTime.now());
        int count = this.userDao.addUser(user);
        if(user.getRole()!=null){
            //sin 添加用户角色
            this.roleDao.addUserRole(user.getId(),user.getRole().getId());
        }else {
            //sin 默认添加普通用户
            this.roleDao.addUserRole(user.getId(),2);
        }
        return count>0;
    }

    //sin 修改用户
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateUser(User user) {
        if(user.getRole()!=null){
            //sin 修改用户角色
            this.roleDao.updateUserRole(user.getId(),user.getRole().getId());
        }
        user.setUpdatedAt(LocalDateTime.now());
        return this.userDao.updateUser(user)>0;
    }

    //sin 通过account获取用户信息
    @Override
    public User getUserByAccount(String account) {
        return this.userDao.getUserByAccount(account);
    }

    //sin 修改用户的密码
    @Override
    public boolean updatePWByAccount(UserEditPW user) {
        return this.userDao.updatePW(user)>0;
    }

    //sin 用户注册    注册用户默认添加普通用户
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean registerUser(UserRegister user) {
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        int count = this.userDao.registerUser(user);
        if(count>0){
            //sin 默认添加普通用户
            this.roleDao.addUserRole(user.getId(),2);
        }
        return count>0;
    }
}
