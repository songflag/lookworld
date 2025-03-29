package com.sinker.lookworld.dao;


import com.sinker.lookworld.model.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
*BelongsProject: lookworld
*BelongsPackage: com.sinker.lookworld.dao
*Author: sinker
*CreateTime: 2025-03-12  16:12
*Description: 我亦无他,唯手熟耳
*Version: 1.0
*/
@Mapper
public interface PermissionDao {

    List<Permission> getPermList();


    int addPerm(Permission permission);


    int updatePermById(Permission permission);


    int deletePermByIds(Integer[] ids);

}
