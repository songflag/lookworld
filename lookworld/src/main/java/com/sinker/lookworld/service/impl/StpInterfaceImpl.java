package com.sinker.lookworld.service.impl;


import cn.dev33.satoken.stp.StpInterface;
import com.sinker.lookworld.model.Permission;
import com.sinker.lookworld.model.Role;
import com.sinker.lookworld.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.service.impl
 * Author: sinker
 * CreateTime: 2025-03-12  15:24
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */
@Service
public class StpInterfaceImpl implements StpInterface {
    private RoleService roleService;
    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    //sin 获取权限验证
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 本 list 仅做模拟，实际项目中要根据具体业务逻辑来查询权限
        List<String> list = new ArrayList<String>();
        List<Role> roles = this.roleService.getRolesById(Integer.valueOf(loginId.toString()));
        List<Integer> roleIds = roles.stream().map(Role::getId).toList();
        List<Permission> perms = this.roleService.getPermsByRoleIds(roleIds);
        for (Permission prem:perms){
            list.add(prem.getName());
        }
        return list;
    }
    /**
     * sin 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // 本 list 仅做模拟，实际项目中要根据具体业务逻辑来查询角色
        List<Role> list = this.roleService.getRolesById(Integer.valueOf(loginId.toString()));
        return list.stream().map(Role::getName).toList();
    }
}
