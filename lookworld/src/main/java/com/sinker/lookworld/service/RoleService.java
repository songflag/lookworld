package com.sinker.lookworld.service;


import com.sinker.lookworld.model.Permission;
import com.sinker.lookworld.model.Role;

import java.util.List;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.service
 * Author: sinker
 * CreateTime: 2025-03-12  15:27
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */

public interface RoleService {
    List<Role> getRolesById(Integer id);

    List<Permission> getPermissions();

    List<Permission> getPermsByRoleIds(List<Integer> roleIds);

}
