package com.sinker.lookworld.service.impl;


import com.sinker.lookworld.dao.RoleDao;
import com.sinker.lookworld.model.Permission;
import com.sinker.lookworld.model.Role;
import com.sinker.lookworld.service.RoleService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.service.impl
 * Author: sinker
 * CreateTime: 2025-03-12  15:27
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */
@Service
public class RoleServiceImpl implements RoleService {
    private RoleDao roleDao;
    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    //sin 通过用户id获取用户的角色
    @Override
    public List<Role> getRolesById(Integer id) {
        return this.roleDao.getRolesByUserId(id);
    }

    //sin 获取所有的权限信息
    @Override
    public List<Permission> getPermissions() {
        return this.roleDao.getAllPerm();
    }

    //sin 通过角色id获取该角色所拥有的权限
    @Override
    public List<Permission> getPermsByRoleIds(List<Integer> roleIds) {
        return this.roleDao.getPermsByRoleIds(roleIds);
    }
}
