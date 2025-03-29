package com.sinker.lookworld.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sinker.lookworld.dao.PermissionDao;
import com.sinker.lookworld.dao.RoleDao;
import com.sinker.lookworld.model.Permission;
import com.sinker.lookworld.model.Role;
import com.sinker.lookworld.model.search.RoleAddEdit;
import com.sinker.lookworld.service.PermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.service.impl
 * Author: sinker
 * CreateTime: 2025-03-21  11:14
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */

@Service
public class PermServiceImpl implements PermService {
    private PermissionDao permissionDao;
    @Autowired
    public void setPermissionDao(PermissionDao permissionDao) {
        this.permissionDao = permissionDao;
    }
    private RoleDao roleDao;
    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    //sin 查询权限列表
    @Override
    public List<Permission> getPermList(Page<?> pi) {
        try (Page<?> __= PageHelper.startPage(pi.getPageNum(),pi.getPageSize())){
            return this.permissionDao.getPermList();
        }
    }

    //sin 新增权限
    @Override
    public boolean addPerm(Permission permission) {
        return this.permissionDao.addPerm(permission)>0;
    }

    //sin 修改权限
    @Override
    public boolean updatePermById(Permission permission) {
        return this.permissionDao.updatePermById(permission)>0;
    }

    //sin 删除权限
    @Override
    public boolean deletePermByIds(Integer[] ids) {
        return this.permissionDao.deletePermByIds(ids)>0;
    }

    //sin 查询角色列表
    @Override
    public List<Role> getRoleList() {
        return  this.roleDao.getRoleList();
    }


    //sin 修改角色权限
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateRole(RoleAddEdit role) {
        if(role!=null){
            //sin 删除旧的权限
            this.roleDao.deleteRolePermByRoleId(role.getId());
        }
        //sin 新增新的权限
        assert role != null;
        return this.roleDao.addRolePerm(role.getId(), List.of(role.getPermissions()))==role.getPermissions().length;
    }

    //sin 添加角色权限 事务
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addRole(RoleAddEdit role) {
        if(role!=null){
            //sin 新增角色
            this.roleDao.addRole(role);
        }
        //sin 新增角色权限
        assert role!= null;
        return this.roleDao.addRolePerm(role.getId(), List.of(role.getPermissions()))==role.getPermissions().length;
    }
}
