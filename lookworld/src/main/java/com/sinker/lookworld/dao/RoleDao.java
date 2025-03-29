package com.sinker.lookworld.dao;


import com.sinker.lookworld.model.Permission;
import com.sinker.lookworld.model.Role;
import com.sinker.lookworld.model.search.RoleAddEdit;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.dao
 * Author: sinker
 * CreateTime: 2025-03-12  15:26
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */
@Mapper
public interface RoleDao {

    List<Role> getRolesByUserId(Integer id);

    List<Permission> getAllPerm();

    List<Permission> getPermsByRoleIds(List<Integer> roleIds);

    List<Role> getRoleList();


    void deleteRolePermByRoleId(Integer id);

    int addRolePerm(Integer id, List<Integer> permIds);

    void addRole(RoleAddEdit role);

    @Update(value = "update user_role set role_id = #{roleId} where user_id = #{id}")
    void updateUserRole(Integer id, Integer roleId);

    @Insert(value = "insert into user_role (user_id,role_id,active) values (#{id},#{id1},1)")
    void addUserRole(Integer id, Integer id1);

}
