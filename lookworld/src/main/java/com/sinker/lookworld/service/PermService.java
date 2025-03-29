package com.sinker.lookworld.service;


import com.github.pagehelper.Page;
import com.sinker.lookworld.model.Permission;
import com.sinker.lookworld.model.Role;
import com.sinker.lookworld.model.search.RoleAddEdit;

import java.util.List;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.service
 * Author: sinker
 * CreateTime: 2025-03-21  11:13
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */

public interface PermService {

    List<Permission> getPermList(Page<?> pi);

    boolean addPerm(Permission permission);

    boolean updatePermById(Permission permission);

    boolean deletePermByIds(Integer[] ids);

    List<Role> getRoleList();

    boolean updateRole(RoleAddEdit role);

    boolean addRole(RoleAddEdit role);

}
